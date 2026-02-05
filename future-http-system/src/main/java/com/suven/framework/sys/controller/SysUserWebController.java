package com.suven.framework.sys.controller;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.sys.dto.request.SysUserRequestDto;
import com.suven.framework.sys.dto.request.SysUserDepartRequestDto;
import com.suven.framework.sys.dto.response.SysUserResponseDto;
import com.suven.framework.sys.dto.enums.SysUserQueryEnum;
import com.suven.framework.sys.facade.SysRoleFacade;
import com.suven.framework.sys.facade.SysUserFacade;
import com.suven.framework.sys.service.SysUserDepartService;
import com.suven.framework.sys.service.SysUserRoleService;
import com.suven.framework.sys.service.SysUserService;
import com.suven.framework.sys.vo.request.SysUserAddRequestVo;
import com.suven.framework.sys.vo.request.SysUserDepartIdsRequestVo;
import com.suven.framework.sys.vo.request.SysUserLoginRequestVo;
import com.suven.framework.sys.vo.request.SysUserQueryRequestVo;
import com.suven.framework.sys.vo.request.SysUserRoleIdsRequestVo;
import com.suven.framework.sys.vo.request.SysUserRoleRequestVo;
import com.suven.framework.sys.vo.request.SysUserTokenRequestVo;
import com.suven.framework.sys.vo.request.SysUserUpdatePwdRequestVo;
import com.suven.framework.sys.vo.response.LoginCodeResponseVo;
import com.suven.framework.sys.vo.response.SysDataLogShowResponseVo;
import com.suven.framework.sys.vo.response.SysUserShowResponseVo;
import com.suven.framework.sys.vo.response.SysUserResponseVo;
import com.suven.framework.util.excel.ExcelUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: SysUserWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:09:37
 * @version 版本: v2.0.0
 * <pre>
 *
 *  Description: 用户表接口控制器
 *
 *  接口规范：
 *  1. 所有接口必须使用 @ApiDoc 注解
 *  2. 请求方式必须明确指定 (GET/POST/PUT/DELETE)
 *  3. 接口URL必须在 UrlCommand 中统一定义
 *  4. 返回结果必须使用统一的响应格式
 *  5. 必须使用 @Validated 开启参数校验
 *  6. 必须使用 @Slf4j 记录日志
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *    v2.0.0         suven    2026-02-05   重构:统一接口规范
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysUser")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc = DocumentConst.Sys.SYS_DOC_DES,
        module = "用户表模块"
)
@Controller
@Slf4j
@Validated
public class SysUserWebController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserFacade sysUserFacade;

    @Autowired
    private SysRoleFacade sysRoleFacade;

    @Autowired
    private SysUserDepartService sysUserDepartService;

    @Autowired
    private SysUserRoleService userRoleService;

    /**
     * URL 命令常量接口
     * 规范：全大写，下划线分隔，描述性名称
     */
    public interface UrlCommand {
        String SYS_USER_INDEX = "/sys/sysUser/index";
        String SYS_USER_PAGE_LIST = "/sys/sysUser/pageList";
        String SYS_USER_LIST = "/sys/sysUser/list";
        String SYS_USER_QUERY_LIST = "/sys/sysUser/queryList";
        String SYS_USER_INFO = "/sys/sysUser/info";
        String SYS_USER_CREATE = "/sys/sysUser/create";
        String SYS_USER_UPDATE = "/sys/sysUser/update";
        String SYS_USER_DELETE = "/sys/sysUser/delete";
        String SYS_USER_EDIT = "/sys/sysUser/edit";
        String SYS_USER_NEW_INFO = "/sys/sysUser/newInfo";
        String SYS_USER_EXPORT = "/sys/sysUser/export";
        String SYS_USER_IMPORT = "/sys/sysUser/import";
        String SYS_USER_LOGIN = "/sys/sysUser/login";
        String SYS_USER_LOGOUT = "/sys/logout";
        String SYS_USER_CHECK_TOKEN = "/sys/sysUser/checkToken";
        String SYS_USER_GET_CHECK_CODE = "/sys/getCheckCode";
        String SYS_USER_GET_RANDOM_IMAGE = "/sys/getRandomImage";
        String SYS_USER_TURN_ON = "/sys/user/turnOn";
        String SYS_USER_TURN_OFF = "/sys/user/turnOff";
        String SYS_USER_ROLE = "/sys/user/role";
        String SYS_USER_DEPART = "/sys/user/depart";
        String SYS_USER_USER_ROLE_LIST = "/sys/user/userRoleList";
        String SYS_USER_ADD_SYS_USER_ROLE = "/sys/user/addSysUserRole";
        String SYS_USER_DELETE_USER_ROLE = "/sys/user/deleteUserRole";
        String SYS_USER_DEL_DEPART = "/sys/user/delDepart";
        String SYS_USER_EDIT_SYS_DEPART = "/sys/user/editSysDepart";
        String SYS_USER_UPDATE_PASSWORD = "/sys/user/updatePassword";
        String SYS_USER_FROZEN_BATCH = "/sys/user/frozenBatch";
        String SYS_USER_HANDLE_MUTED = "/sys/user/handleMuted";
    }

    /**
     * 跳转到用户表主界面
     *
     * @return 字符串url
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @GetMapping(value = UrlCommand.SYS_USER_INDEX)
    public String index() {
        log.info("跳转用户表主界面");
        return "sys/sysUser_index";
    }

    /**
     * 分页获取用户表信息
     *
     * @param sysUserQueryRequestVo 查询请求参数
     * @return PageResult<SysUserShowResponseVo> 分页响应结果
     * @author suven
     * date 2022-02-28 16:09:37
     *
     * 接口规则：
     * 1. 分页参数必须使用 Pager 包装
     * 2. 必须指定排序枚举
     * 3. 必须记录操作日志
     */
    @ApiDoc(
            value = "分页获取用户表信息",
            description = "根据查询条件分页获取用户表列表",
            request = SysUserQueryRequestVo.class,
            response = SysUserShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.SYS_USER_PAGE_LIST)
    public PageResult<SysUserShowResponseVo> pageList(
            @Valid SysUserQueryRequestVo sysUserQueryRequestVo) {

        log.info("分页查询用户表, 参数: {}", sysUserQueryRequestVo);

        SysUserRequestDto sysUserRequestDto = SysUserRequestDto.build().clone(sysUserQueryRequestVo);

        Pager<SysUserRequestDto> pager = new Pager<>(
                sysUserQueryRequestVo.getPageNo(),
                sysUserQueryRequestVo.getPageSize()
        );
        pager.toPageSize(sysUserQueryRequestVo.getPageSize())
                .toPageNo(sysUserQueryRequestVo.getPageNo())
                .toParamObject(sysUserRequestDto);

        SysUserQueryEnum queryEnum = SysUserQueryEnum.DESC_ID;
        PageResult<SysUserResponseDto> resultList = sysUserService.getSysUserByNextPage(pager, queryEnum);

        if (resultList == null || resultList.getList().isEmpty()) {
            log.info("分页查询用户表完成, 无数据");
            return new PageResult<>();
        }

        PageResult<SysUserShowResponseVo> result = resultList.convertBuild(SysUserShowResponseVo.class);
        log.info("分页查询用户表完成, 总数: {}", result.getTotal());
        return result;
    }

    /**
     * 根据条件查询用户表信息
     *
     * @param sysUserQueryRequestVo 查询请求参数
     * @return List<SysUserShowResponseVo> 响应结果列表
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "根据条件查询用户表信息",
            description = "根据查询条件获取用户表列表",
            request = SysUserQueryRequestVo.class,
            response = SysUserShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.SYS_USER_QUERY_LIST)
    public List<SysUserShowResponseVo> queryList(
            @Valid SysUserQueryRequestVo sysUserQueryRequestVo) {

        log.info("根据条件查询用户表, 参数: {}", sysUserQueryRequestVo);

        SysUserRequestDto sysUserRequestDto = SysUserRequestDto.build().clone(sysUserQueryRequestVo);

        Pager<SysUserRequestDto> pager = Pager.of();
        pager.toPageSize(sysUserQueryRequestVo.getPageSize())
                .toPageNo(sysUserQueryRequestVo.getPageNo())
                .toParamObject(sysUserRequestDto);

        SysUserQueryEnum queryEnum = SysUserQueryEnum.DESC_ID;
        List<SysUserResponseDto> resultList = sysUserService.getSysUserListByQuery(pager, queryEnum);

        if (resultList == null || resultList.isEmpty()) {
            log.info("根据条件查询用户表完成, 无数据");
            return new ArrayList<>();
        }

        List<SysUserShowResponseVo> listVo = IterableConvert.convertList(resultList, SysUserShowResponseVo.class);
        log.info("根据条件查询用户表完成, 数量: {}", listVo.size());
        return listVo;
    }

    /**
     * 新增用户表信息
     *
     * @param sysUserAddRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "新增用户表信息",
            description = "新增用户表记录",
            request = SysUserAddRequestVo.class,
            response = Long.class
    )
    @PostMapping(value = UrlCommand.SYS_USER_CREATE)
    public Long create(@Valid SysUserAddRequestVo sysUserAddRequestVo) {

        log.info("新增用户表, 参数: {}", sysUserAddRequestVo);

        // 检查用户名和手机号是否已存在
        SysUserRequestDto requestDto = SysUserRequestDto.build()
                .toUsername(sysUserAddRequestVo.getUsername())
                .toPhone(sysUserAddRequestVo.getPhone());
        SysUserResponseDto dto = sysUserService.getSysUserByOne(SysUserQueryEnum.USER_NAME_OR_PHONE, requestDto);
        if (dto != null) {
            log.warn("新增用户表失败, 用户名或手机号已存在");
            throw new RuntimeException("用户名或手机号已存在");
        }

        SysUserRequestDto sysUserRequestDto = SysUserRequestDto.build().clone(sysUserAddRequestVo);
        SysUserResponseDto sysUserresponseDto = sysUserService.saveSysUser(sysUserRequestDto);

        if (sysUserresponseDto == null) {
            log.warn("新增用户表失败");
            throw new RuntimeException("新增失败");
        }

        // 关联角色
        if (!ObjectTrue.isEmpty(sysUserAddRequestVo.getRoleIds())) {
            userRoleService.editRole(sysUserresponseDto.getId(), sysUserAddRequestVo.getRoleIds());
        }

        log.info("新增用户表成功, ID: {}", sysUserresponseDto.getId());
        return sysUserresponseDto.getId();
    }

    /**
     * 修改用户表信息
     *
     * @param sysUserAddRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "修改用户表信息",
            description = "根据ID修改用户表记录",
            request = SysUserAddRequestVo.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.SYS_USER_UPDATE)
    public boolean update(@Valid SysUserAddRequestVo sysUserAddRequestVo) {

        log.info("修改用户表, 参数: {}", sysUserAddRequestVo);

        if (sysUserAddRequestVo.getId() == null || sysUserAddRequestVo.getId() <= 0) {
            log.warn("修改用户表参数错误, ID: {}", sysUserAddRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        // 检查用户名和手机号是否已存在
        SysUserRequestDto requestDto = SysUserRequestDto.build()
                .toUsername(sysUserAddRequestVo.getUsername())
                .toPhone(sysUserAddRequestVo.getPhone());
        SysUserResponseDto dto = sysUserService.getSysUserByOne(SysUserQueryEnum.USER_NAME_OR_PHONE, requestDto);
        if (dto != null && dto.getId() != sysUserAddRequestVo.getId()) {
            log.warn("修改用户表失败, 用户名或手机号已存在");
            throw new RuntimeException("用户名或手机号已存在");
        }

        SysUserRequestDto sysUserRequestDto = SysUserRequestDto.build().clone(sysUserAddRequestVo);
        sysUserRequestDto.setBirthday(sysUserAddRequestVo.getBirthday());

        // 关联角色
        if (!ObjectTrue.isEmpty(sysUserAddRequestVo.getRoleIds())) {
            userRoleService.editRole(sysUserAddRequestVo.getId(), sysUserAddRequestVo.getRoleIds());
        } else {
            userRoleService.deleteAll(sysUserAddRequestVo.getId());
        }

        boolean result = sysUserService.updateSysUser(sysUserRequestDto);
        log.info("修改用户表完成, ID: {}, 结果: {}", sysUserAddRequestVo.getId(), result);
        return result;
    }

    /**
     * 查看用户表详情
     *
     * @param idRequestVo ID请求参数
     * @return SysUserShowResponseVo 详情响应结果
     * @author suven
     * date 2022-02-28 16:09:37
     *
     * 接口规则：
     * 1. ID参数必须校验非空
     * 2. 必须处理数据不存在情况
     * 3. 必须记录查询日志
     */
    @ApiDoc(
            value = "查看用户表详情",
            description = "根据ID获取用户表详细信息",
            request = HttpRequestByIdVo.class,
            response = SysUserShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.SYS_USER_INFO)
    public SysUserShowResponseVo info(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("查询用户表详情, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询用户表详情参数错误, ID: {}", idRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        SysUserResponseDto sysUserResponseDto = sysUserService.getSysUserById(idRequestVo.getId());

        if (sysUserResponseDto == null) {
            log.warn("用户表不存在, ID: {}", idRequestVo.getId());
            throw new RuntimeException("数据不存在");
        }

        SysUserShowResponseVo vo = SysUserShowResponseVo.build().clone(sysUserResponseDto);
        log.info("查询用户表详情成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 跳转用户表编辑页面
     *
     * @param idRequestVo ID请求参数
     * @return SysUserShowResponseVo 编辑页面数据
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "跳转编辑页面",
            description = "获取用户表编辑页面数据",
            request = HttpRequestByIdVo.class,
            response = SysUserShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.SYS_USER_EDIT)
    public SysUserShowResponseVo edit(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("跳转编辑页面, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("跳转编辑页面参数错误, ID: {}", idRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        SysUserResponseDto sysUserResponseDto = sysUserService.getSysUserById(idRequestVo.getId());

        if (sysUserResponseDto == null) {
            log.warn("用户表不存在, ID: {}", idRequestVo.getId());
            throw new RuntimeException("数据不存在");
        }

        SysUserShowResponseVo vo = SysUserShowResponseVo.build().clone(sysUserResponseDto);
        log.info("跳转编辑页面成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 跳转用户表新增编辑界面
     *
     * @return 页面路径
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @GetMapping(value = UrlCommand.SYS_USER_NEW_INFO)
    public String newInfo(ModelMap modelMap) {
        log.info("跳转新增编辑页面");
        return "sys/sysUser_edit";
    }

    /**
     * 删除用户表信息
     *
     * @param idRequestVo ID请求参数
     * @return int 删除数量
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "删除用户表信息",
            description = "根据ID列表删除用户表记录",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @PostMapping(value = UrlCommand.SYS_USER_DELETE)
    public int delete(@Valid HttpRequestByIdListVo idRequestVo) {

        log.info("删除用户表, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除用户表参数错误, ID列表为空");
            throw new RuntimeException("ID列表参数错误");
        }

        int result = sysUserService.delSysUserByIds(idRequestVo.getIdList());
        log.info("删除用户表完成, 删除数量: {}", result);
        return result;
    }

    /**
     * 导出用户表信息
     *
     * @param response 响应流
     * @param sysUserQueryRequestVo 查询参数
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "导出用户表信息",
            description = "导出用户表数据到Excel文件",
            request = SysUserQueryRequestVo.class,
            response = boolean.class
    )
    @GetMapping(value = UrlCommand.SYS_USER_EXPORT)
    public void export(HttpServletResponse response,
                      @Valid SysUserQueryRequestVo sysUserQueryRequestVo) {

        log.info("导出用户表, 参数: {}", sysUserQueryRequestVo);

        SysUserRequestDto sysUserRequestDto = SysUserRequestDto.build().clone(sysUserQueryRequestVo);

        Pager<SysUserRequestDto> pager = Pager.of();
        pager.toPageSize(sysUserQueryRequestVo.getPageSize())
                .toPageNo(sysUserQueryRequestVo.getPageNo())
                .toParamObject(sysUserRequestDto);

        SysUserQueryEnum queryEnum = SysUserQueryEnum.DESC_ID;
        PageResult<SysUserResponseDto> resultList = sysUserService.getSysUserByNextPage(pager, queryEnum);
        List<SysUserResponseDto> data = resultList.getList();

        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysUserResponseVo.class, data, "导出用户表信息");
            log.info("导出用户表完成, 数据量: {}", data.size());
        } catch (Exception e) {
            log.error("导出用户表失败", e);
        }
    }

    /**
     * 通过Excel导入数据
     *
     * @param file 上传文件
     * @return boolean 导入结果
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "导入用户表数据",
            description = "通过Excel文件导入用户表数据",
            request = MultipartFile.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.SYS_USER_IMPORT)
    public boolean importExcel(@RequestParam("file") MultipartFile file) {

        log.info("导入用户表, 文件名: {}", file.getOriginalFilename());

        try {
            boolean result = sysUserService.saveData(file.getInputStream());
            log.info("导入用户表完成, 结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("导入用户表失败", e);
            throw new RuntimeException("导入失败");
        }
    }

    /**
     * 用户登录
     *
     * @param sysUserLoginRequestVo 登录参数
     * @return 登录结果
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "用户登录",
            description = "用户登录接口",
            request = SysUserLoginRequestVo.class,
            response = SysDataLogShowResponseVo.class
    )
    @PostMapping(value = UrlCommand.SYS_USER_LOGIN)
    public Object login(@Valid SysUserLoginRequestVo sysUserLoginRequestVo) {

        log.info("用户登录, 用户名: {}", sysUserLoginRequestVo.getUsername());

        Object result = sysUserFacade.userLogin(sysUserLoginRequestVo);
        if (result == null) {
            log.warn("用户登录失败, 用户名: {}", sysUserLoginRequestVo.getUsername());
            return null;
        }

        log.info("用户登录成功, 用户名: {}", sysUserLoginRequestVo.getUsername());
        return result;
    }

    /**
     * 检验token
     *
     * @param sysUserTokenRequestVo token参数
     * @return boolean 检验结果
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "检验token",
            description = "检验用户token是否有效",
            request = SysUserTokenRequestVo.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.SYS_USER_CHECK_TOKEN)
    public boolean checkToken(@Valid SysUserTokenRequestVo sysUserTokenRequestVo) {

        log.info("检验token, 用户名: {}", sysUserTokenRequestVo.getUsername());

        boolean result = sysUserFacade.checkToken(sysUserTokenRequestVo.getUsername(), sysUserTokenRequestVo.getToken());
        log.info("检验token完成, 用户名: {}, 结果: {}", sysUserTokenRequestVo.getUsername(), result);
        return result;
    }

    /**
     * 退出登录
     *
     * @param request 请求
     * @return 退出结果
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @PostMapping(value = UrlCommand.SYS_USER_LOGOUT)
    public Object logout(HttpServletRequest request) {

        log.info("用户退出登录");

        Object msgEnum = sysUserFacade.logout(request);
        log.info("用户退出登录完成");
        return msgEnum;
    }

    /**
     * 获取校验码
     *
     * @return 校验码
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "获取校验码",
            description = "获取登录校验码",
            request = Object.class,
            response = LoginCodeResponseVo.class
    )
    @GetMapping(value = UrlCommand.SYS_USER_GET_CHECK_CODE)
    public LoginCodeResponseVo getCheckCode() {

        log.info("获取校验码");

        LoginCodeResponseVo vo = sysUserFacade.getCheckCode();
        log.info("获取校验码完成");
        return vo;
    }

    /**
     * 获取后台生成图形验证码
     *
     * @return 图形验证码
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "获取图形验证码",
            description = "获取登录图形验证码",
            request = Object.class,
            response = LoginCodeResponseVo.class
    )
    @GetMapping(value = UrlCommand.SYS_USER_GET_RANDOM_IMAGE)
    public LoginCodeResponseVo getRandomImage() {

        log.info("获取图形验证码");

        LoginCodeResponseVo vo = sysUserFacade.getCheckCodeImage();
        log.info("获取图形验证码完成");
        return vo;
    }

    /**
     * 启用/上架用户表信息
     *
     * @param idRequestVo ID请求参数
     * @return boolean 操作结果
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "启用用户",
            description = "启用/上架用户表信息",
            request = HttpRequestByIdListVo.class,
            response = boolean.class
    )
    @GetMapping(value = UrlCommand.SYS_USER_TURN_ON)
    public boolean turnOn(@Valid HttpRequestByIdListVo idRequestVo) {

        log.info("启用用户, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("启用用户参数错误, ID列表为空");
            throw new RuntimeException("ID列表参数错误");
        }

        boolean result = sysUserService.turnOn(idRequestVo.getIdList());
        log.info("启用用户完成, 结果: {}", result);
        return result;
    }

    /**
     * 下架用户表信息
     *
     * @param idRequestVo ID请求参数
     * @return boolean 操作结果
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "下架用户",
            description = "下架用户表信息",
            request = HttpRequestByIdListVo.class,
            response = boolean.class
    )
    @GetMapping(value = UrlCommand.SYS_USER_TURN_OFF)
    public boolean turnOff(@Valid HttpRequestByIdListVo idRequestVo) {

        log.info("下架用户, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo == null || idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("下架用户参数错误, ID列表为空");
            throw new RuntimeException("ID列表参数错误");
        }

        boolean result = sysUserService.turnOff(idRequestVo.getIdList());
        log.info("下架用户完成, 结果: {}", result);
        return result;
    }

    /**
     * 查询用户角色信息
     *
     * @param userIdVo 用户ID参数
     * @return List<String> 角色信息
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "查询用户角色信息",
            description = "根据用户ID查询用户角色信息",
            request = Object.class,
            response = String.class
    )
    @GetMapping(value = UrlCommand.SYS_USER_ROLE)
    public Object queryUserRole(@Valid Object userIdVo) {

        log.info("查询用户角色信息");

        // 注意：这里需要根据实际参数类型进行调整
        Object voList = null;
        log.info("查询用户角色信息完成");
        return voList;
    }

    /**
     * 查询用户部门信息
     *
     * @param userDepartRequestVo 用户部门查询参数
     * @return PageResult<SysUserResponseDto> 用户部门列表
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "查询用户部门信息",
            description = "根据部门ID查询用户列表",
            request = SysUserDepartIdsRequestVo.class,
            response = SysUserResponseDto.class
    )
    @GetMapping(value = UrlCommand.SYS_USER_DEPART)
    public PageResult<SysUserResponseDto> getUserDepartList(
            @Valid SysUserDepartIdsRequestVo userDepartRequestVo) {

        log.info("查询用户部门信息, 部门ID: {}", userDepartRequestVo.getDepId());

        PageResult<SysUserResponseDto> list = sysUserService.getUserByDepIdPage(userDepartRequestVo.getDepId());
        log.info("查询用户部门信息完成, 数量: {}", list.getTotal());
        return list;
    }

    /**
     * 查询角色用户信息
     *
     * @param sysUserRoleRequestVo 角色用户查询参数
     * @return PageResult<SysUserResponseDto> 角色用户列表
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @GetMapping(value = UrlCommand.SYS_USER_USER_ROLE_LIST)
    public Object userRoleList(@Valid SysUserRoleRequestVo sysUserRoleRequestVo) {

        log.info("查询角色用户信息, 角色ID: {}", sysUserRoleRequestVo.getRoleId());

        Pager<SysUserResponseDto> pager = Pager.of();
        pager.toPageSize(sysUserRoleRequestVo.getPageSize())
                .toPageNo(sysUserRoleRequestVo.getPageNo());

        PageResult<SysUserResponseDto> dtos = sysUserService.getSysUserRoleId(
                pager, sysUserRoleRequestVo.getRoleId(), sysUserRoleRequestVo.getUsername());

        log.info("查询角色用户信息完成, 数量: {}", dtos.getTotal());
        return dtos;
    }

    /**
     * 批量绑定用户与角色关系接口
     *
     * @param userDepartRequestVo 绑定参数
     * @return Boolean 绑定结果
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "批量绑定用户角色",
            description = "指定角色批量绑定用户关系接口",
            request = SysUserRoleIdsRequestVo.class,
            response = Boolean.class
    )
    @PostMapping(value = UrlCommand.SYS_USER_ADD_SYS_USER_ROLE)
    public Boolean addSysUserRole(@Valid SysUserRoleIdsRequestVo userDepartRequestVo) {

        log.info("批量绑定用户角色, 角色ID: {}", userDepartRequestVo.getRoleId());

        Boolean isFlag = sysUserFacade.addSysUserRole(userDepartRequestVo);
        log.info("批量绑定用户角色完成, 结果: {}", isFlag);
        return isFlag;
    }

    /**
     * 删除角色用户
     *
     * @param userDepartRequestVo 删除参数
     * @return Boolean 删除结果
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "删除角色用户",
            description = "删除角色用户关系",
            request = SysUserRoleIdsRequestVo.class,
            response = Boolean.class
    )
    @PostMapping(value = UrlCommand.SYS_USER_DELETE_USER_ROLE)
    public Boolean deleteUserRole(@Valid SysUserRoleIdsRequestVo userDepartRequestVo) {

        log.info("删除角色用户, 角色ID: {}", userDepartRequestVo.getRoleId());

        Boolean isFlag = sysUserFacade.deleteUserRole(userDepartRequestVo);
        log.info("删除角色用户完成, 结果: {}", isFlag);
        return isFlag;
    }

    /**
     * 删除用户部门
     *
     * @param userDepartRequestVo 删除参数
     * @return Boolean 删除结果
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "删除用户部门",
            description = "删除用户部门关系",
            request = SysUserDepartIdsRequestVo.class,
            response = Boolean.class
    )
    @PostMapping(value = UrlCommand.SYS_USER_DEL_DEPART)
    public Boolean deleteUserInDepart(@Valid SysUserDepartIdsRequestVo userDepartRequestVo) {

        log.info("删除用户部门, 用户ID列表: {}", userDepartRequestVo.getUserIdList());

        Boolean isFlag = sysUserFacade.deleteUserInDepart(userDepartRequestVo);
        log.info("删除用户部门完成, 结果: {}", isFlag);
        return isFlag;
    }

    /**
     * 添加部门人员
     *
     * @param userDepartRequestVo 添加参数
     * @return Boolean 添加结果
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "添加部门人员",
            description = "向部门添加人员",
            request = SysUserDepartIdsRequestVo.class,
            response = Boolean.class
    )
    @PostMapping(value = UrlCommand.SYS_USER_EDIT_SYS_DEPART)
    public Boolean editSysDepartWithUser(@Valid SysUserDepartIdsRequestVo userDepartRequestVo) {

        log.info("添加部门人员, 部门ID: {}", userDepartRequestVo.getDepId());

        SysUserDepartRequestDto dto = SysUserDepartRequestDto.build()
                .toDepId(userDepartRequestVo.getDepId())
                .toUserIdList(userDepartRequestVo.getUserIdList());

        Boolean isFlag = sysUserDepartService.editSysDepartWithUser(dto);
        log.info("添加部门人员完成, 结果: {}", isFlag);
        return isFlag;
    }

    /**
     * 修改密码
     *
     * @param userUpdatePwdRequestVo 修改密码参数
     * @return boolean 修改结果
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @PostMapping(value = UrlCommand.SYS_USER_UPDATE_PASSWORD)
    public boolean updatePassword(@Valid SysUserUpdatePwdRequestVo userUpdatePwdRequestVo) {

        log.info("修改密码, 用户名: {}", userUpdatePwdRequestVo.getUsername());

        Object msgEnum = sysUserFacade.updatePassword(userUpdatePwdRequestVo);
        log.info("修改密码完成");
        return true;
    }

    /**
     * 冻结用户
     *
     * @param idRequestVo 冻结参数
     * @return boolean 冻结结果
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "冻结用户",
            description = "批量冻结用户",
            request = Object.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.SYS_USER_FROZEN_BATCH)
    public boolean frozenBatch(@Valid Object idRequestVo) {

        log.info("冻结用户");

        // 注意：这里需要根据实际参数类型进行调整
        boolean result = false;
        log.info("冻结用户完成, 结果: {}", result);
        return result;
    }

    /**
     * 禁言/解禁用户
     *
     * @param statusReqVo 状态参数
     * @return boolean 操作结果
     * @author suven
     * date 2022-02-28 16:09:37
     */
    @ApiDoc(
            value = "禁言/解禁用户",
            description = "禁言或解禁用户",
            request = Object.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.SYS_USER_HANDLE_MUTED)
    public boolean handleMuted(@Valid Object statusReqVo) {

        log.info("禁言/解禁用户");

        // 注意：这里需要根据实际参数类型进行调整
        boolean result = false;
        log.info("禁言/解禁用户完成, 结果: {}", result);
        return result;
    }
}
