服务端实现逻辑接口规范规则

## 一、接口类编码规范

### 1. **控制器类基础规则**
```java
/**
 * @ClassName: AuthClientDetailsController
 * @Description: 用户平台化申请验证表控制器
 * @Author: suven
 * @Date: 2025-08-18
 * @Version: 1.0
 * 
 * 接口规范：
 * 1. 所有接口必须使用 @ApiDoc 注解
 * 2. 请求方式必须明确指定 (GET/POST/PUT/DELETE)
 * 3. 接口URL必须在 UrlCommand 中统一定义
 * 4. 返回结果必须使用统一的响应格式
 */
@ApiDoc(
    group = DocumentConst.Api.API_DOC_GROUP,
    groupDesc = DocumentConst.Api.API_DOC_DES,
    module = "用户平台化申请验证表模块", 
    isApp = true
)
@RestController
@Slf4j
@Validated
public class AuthClientDetailsController {
    // 控制器代码...
}
```

### 2. **URL 常量定义规范**
```java
public interface UrlCommand {
    String ACTIVITY_AUTH_CLIENT_DETAILS_PAGE_LIST = "/auth/authclientdetails/pageList";
    String ACTIVITY_AUTH_CLIENT_DETAILS_INFO = "/auth/authclientdetails/info";
    String ACTIVITY_AUTH_CLIENT_DETAILS_CREATE = "/auth/authclientdetails/create";
    String ACTIVITY_AUTH_CLIENT_DETAILS_UPDATE = "/auth/authclientdetails/update";
    String ACTIVITY_AUTH_CLIENT_DETAILS_DELETE = "/auth/authclientdetails/delete";
    
    // 命名规范：全大写，下划线分隔，描述性名称
}
```

## 二、接口方法编码规则

### 1. **分页查询接口规范**
```java
import com.suven.framework.common.enums.CodeEnum;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.common.enums.SystemMsgCodeEnum;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.file.util.FileMsgEnum;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.api.RequestMethodEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.exception.ExceptionFactory;
import com.suven.framework.upload.dto.request.SaaSFileRequestDto;
import com.suven.framework.upload.dto.response.SaaSFileResponseDto;
import com.suven.framework.upload.facade.SaaSFileFacade;
import com.suven.framework.upload.vo.request.SaaSFileDownloadRequestVo;
import com.suven.framework.upload.vo.request.SaaSFileGenerateRequestVo;
import com.suven.framework.upload.vo.request.SaaSFileQueryRequestVo;
import com.suven.framework.upload.vo.request.SaaSFileUploadRequestVo;
import com.suven.framework.upload.vo.response.SaaSFileDownloadResponseVo;
import com.suven.framework.upload.vo.response.SaaSFileGenerateResponseVo;
import com.suven.framework.upload.vo.response.SaaSFileShowResponseVo;
import com.suven.framework.upload.vo.response.SaaSFileUploadResponseVo;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
/**
 * @Title: 分页获取用户平台化申请验证表信息
 * @Description: 根据查询条件分页获取用户平台化申请验证表列表
 * @ApiDoc: 
 *   - value: 接口功能描述
 *   - request: 请求参数类
 *   - response: 响应数据类
 * @Param authClientDetailsRequestVo 查询请求参数
 * @Return PageResult<AuthClientDetailsResponseVo> 分页响应结果
 * @Author suven
 * @Date 2025-08-18 15:10:50
 * 
 * 接口规则：
 * 1. 分页参数必须使用 Pager 包装
 * 2. 必须指定排序枚举
 * 3. 必须记录操作日志
 * 4. 必须进行参数校验
 */
@ApiDoc(
    value = "分页获取用户平台化申请验证表信息",
    description = "根据条件分页查询用户平台化申请验证表数据",
    request = AuthClientDetailsRequestVo.class,
    response = AuthClientDetailsResponseVo.class,
    method = RequestMethodEnum.GET
)
@RequestMapping(value = UrlCommand.ACTIVITY_AUTH_CLIENT_DETAILS_PAGE_LIST, method = RequestMethodEnum.GET)
public PageResult<AuthClientDetailsResponseVo> pageList(
        @Validated AuthClientDetailsRequestVo authClientDetailsRequestVo) {
    
    log.info("分页查询用户平台化申请验证表, 参数: {}", authClientDetailsRequestVo);
    
    // 参数校验
    
    Pager<AuthClientDetailsRequestVo> pager = new Pager<>(
        authClientDetailsRequestVo.getPageNo(), 
        authClientDetailsRequestVo.getPageSize()
    );
    pager.toParamObject(authClientDetailsRequestVo);
    
    PageResult<AuthClientDetailsResponseVo> pageResult = authClientDetailsService
        .getAuthClientDetailsByNextPage(pager, AuthClientDetailsQueryEnum.DESC_ID);
    
    log.info("分页查询用户平台化申请验证表完成, 总数: {}", pageResult.getTotal());
    return pageResult;
}
```

### 2. **详情查询接口规范**
```java
/**
 * @Title: 查看用户平台化申请验证表详情
 * @Description: 根据ID获取用户平台化申请验证表详细信息
 * @ApiDoc:
 *   - value: 接口功能描述  
 *   - request: ID请求参数
 *   - response: 详情响应数据
 * @Param idRequestVo ID请求参数
 * @Return AuthClientDetailsResponseVo 详情响应结果
 * 
 * 接口规则：
 * 1. ID参数必须校验非空
 * 2. 必须处理数据不存在情况
 * 3. 必须记录查询日志
 */
@ApiDoc(
    value = "查看用户平台化申请验证表信息",
    description = "根据ID获取用户平台化申请验证表详细信息",
    request = HttpRequestByIdVo.class,
    response = AuthClientDetailsResponseVo.class,
    method = RequestMethodEnum.GET
)
@RequestMapping(value = UrlCommand.ACTIVITY_AUTH_CLIENT_DETAILS_INFO, method = RequestMethodEnum.GET)
public AuthClientDetailsResponseVo detail(@Validated HttpRequestByIdVo idRequestVo) {
    
    log.info("查询用户平台化申请验证表详情, ID: {}", idRequestVo.getId());
    
    // 参数校验
    if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
        log.warn("查询用户平台化申请验证表详情参数错误, ID: {}", idRequestVo.getId());
        throw  ExceptionFactory.sysException( CodeEnum.XXX);
    }
    
    AuthClientDetailsResponseVo authClientDetailsVo = authClientDetailsService
        .getAuthClientDetailsById(idRequestVo.getId());
    
    if (authClientDetailsVo == null) {
        log.warn("用户平台化申请验证表不存在, ID: {}", idRequestVo.getId());
        throw  ExceptionFactory.sysException( CodeEnum.XXX);
    }
    
    log.info("查询用户平台化申请验证表详情成功, ID: {}", idRequestVo.getId());
    return authClientDetailsVo;
}
```

## 三、完整的控制器模板

```java
/**
 * 用户平台化申请验证表接口控制器
 * 
 * 编码规范：
 * 1. 类名必须以 Controller 结尾
 * 2. 必须使用 @RestController 注解
 * 3. 必须使用 @Slf4j 记录日志
 * 4. 必须使用 @Validatedated 开启参数校验
 * 5. 依赖注入必须使用 @Autowired
 */
@ApiDoc(
    group = DocumentConst.Api.API_DOC_GROUP,
    groupDesc = DocumentConst.Api.API_DOC_DES,
    module = "用户平台化申请验证表模块",
    isApp = true
)
@RestController
@Slf4j
@Validated
public class AuthClientDetailsController {

    @Autowired
    private AuthClientDetailsFacade authClientDetailsFacade;

    @Autowired
    private AuthClientDetailsService authClientDetailsService;

    /**
     * URL 命令常量接口
     * 规范：全大写，下划线分隔，描述性名称
     */
    public interface UrlCommand {
        String ACTIVITY_AUTH_CLIENT_DETAILS_PAGE_LIST = "/auth/authclientdetails/pageList";
        String ACTIVITY_AUTH_CLIENT_DETAILS_INFO = "/auth/authclientdetails/info";
        String ACTIVITY_AUTH_CLIENT_DETAILS_CREATE = "/auth/authclientdetails/create";
        String ACTIVITY_AUTH_CLIENT_DETAILS_UPDATE = "/auth/authclientdetails/update"; 
        String ACTIVITY_AUTH_CLIENT_DETAILS_DELETE = "/auth/authclientdetails/delete";
    }

    /**
     * 分页查询接口
     * 规范：GET请求，@Validated参数校验，统一分页响应
     */
    @ApiDoc(
        value = "分页获取用户平台化申请验证表信息",
        description = "根据查询条件分页获取用户平台化申请验证表列表",
        request = AuthClientDetailsRequestVo.class,
        response = AuthClientDetailsResponseVo.class
    )
    @GetMapping(value = UrlCommand.ACTIVITY_AUTH_CLIENT_DETAILS_PAGE_LIST)
    public PageResult<AuthClientDetailsResponseVo> pageList(
            @Validated AuthClientDetailsRequestVo authClientDetailsRequestVo) {
        
        log.info("分页查询用户平台化申请验证表, 参数: {}", authClientDetailsRequestVo);
        
        // 分页参数处理
        Pager<AddressRequestVo> pager = new Pager<>(
                addressRequestVo.getPageNo() ,
                addressRequestVo.getPageSize()
        );
        pager.toParamObject(authClientDetailsRequestVo);
        
        PageResult<AuthClientDetailsResponseVo> pageResult = authClientDetailsService
            .getAuthClientDetailsByNextPage(pager, AuthClientDetailsQueryEnum.DESC_ID);
            
        log.info("分页查询完成, 总数: {}", pageResult.getTotal());
        return pageResult;
    }

    /**
     * 详情查询接口  
     * 规范：GET请求，ID参数校验，统一结果包装
     */
    @ApiDoc(
        value = "查看用户平台化申请验证表信息",
        description = "根据ID获取用户平台化申请验证表详细信息",
        request = HttpRequestByIdVo.class,
        response = AuthClientDetailsResponseVo.class
    )
    @GetMapping(value = UrlCommand.ACTIVITY_AUTH_CLIENT_DETAILS_INFO)
    public AuthClientDetailsResponseVo detail(@Validated HttpRequestByIdVo idRequestVo) {
        
        log.info("查询用户平台化申请验证表详情, ID: {}", idRequestVo.getId());
        
        AuthClientDetailsResponseVo result = authClientDetailsService
            .getAuthClientDetailsById(idRequestVo.getId());
            
        if (result == null) {
            log.warn("用户平台化申请验证表不存在, ID: {}", idRequestVo.getId());
            throw  ExceptionFactory.sysException( CodeEnum.XXXX);
        }
        
        log.info("查询用户平台化申请验证表详情成功, ID: {}", idRequestVo.getId());
        return result;
    }
}
```

## 四、Cursor 规则配置建议

在 `.cursor/rules/interface-rules.mdc` 中配置：

```yaml
rules:
  - name: "interface-controller-rule"
    description: "接口控制器编码规范"
    patterns:
      - "**/*Controller.java"
    constraints:
      - "类必须使用@RestController注解"
      - "必须使用@Slf4j记录日志" 
      - "URL必须在UrlCommand接口中定义"
      - "接口必须使用@ApiDoc注解"
      - "必须使用 @Validated 参数校验"
      - "分页查询必须使用PageResult包装"
      - "单一查询必须使用Result包装"
      - "必须记录操作日志"
```
