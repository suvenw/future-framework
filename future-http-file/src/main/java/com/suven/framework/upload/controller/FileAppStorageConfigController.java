package com.suven.framework.upload.controller;

import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.upload.dto.enums.FileAppStorageConfigQueryEnum;
import com.suven.framework.upload.dto.request.FileAppStorageConfigRequestDto;
import com.suven.framework.upload.dto.response.FileAppStorageConfigResponseDto;
import com.suven.framework.upload.facade.FileAppStorageConfigFacade;
import com.suven.framework.upload.service.FileAppStorageConfigService;
import com.suven.framework.upload.vo.request.FileAppStorageConfigRequestVo;
import com.suven.framework.upload.vo.response.FileAppStorageConfigResponseVo;
 
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 文件应用存储配置APP接口控制器
 *
 * 规范：
 * 1. 所有接口必须使用 @ApiDoc 注解
 * 2. 请求方式必须明确指定 (GET/POST/PUT/DELETE)
 * 3. 接口URL必须在 UrlCommand 中统一定义
 * 4. 返回结果使用统一的 PageResult / VO 类型
 * 5. 使用 @Validated 开启参数校验
 * 6. 使用 @Slf4j 记录日志
 */
@ApiDoc(
        group = DocumentConst.Api.API_DOC_GROUP,
        groupDesc = DocumentConst.Api.API_DOC_DES,
        module = "文件应用存储配置APP接口",
        isApp = true
)
@Controller
@Slf4j
@Validated
public class FileAppStorageConfigController {

    @Autowired
    private FileAppStorageConfigFacade fileAppStorageConfigFacade;

    @Autowired
    private FileAppStorageConfigService fileAppStorageConfigService;

    /**
     * URL 命令常量接口
     */
    public interface UrlCommand {
        String UPLOAD_FILE_APP_STORAGE_CONFIG_PAGE_LIST = "/upload/fileappstorageconfig/pageList";
        String UPLOAD_FILE_APP_STORAGE_CONFIG_INFO = "/upload/fileappstorageconfig/info";
    }

    /**
     * 分页获取文件应用存储配置信息（APP）
     */
    @ApiDoc(
            value = "分页获取文件应用存储配置信息(APP)",
            description = "根据查询条件分页获取文件应用存储配置列表(APP)",
            request = FileAppStorageConfigRequestVo.class,
            response = FileAppStorageConfigResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_APP_STORAGE_CONFIG_PAGE_LIST)
    public PageResult<FileAppStorageConfigResponseVo> pageList(
            @Valid FileAppStorageConfigRequestVo requestVo) {

        log.info("APP 分页查询文件应用存储配置, 参数: {}", requestVo);

        FileAppStorageConfigRequestDto dto = FileAppStorageConfigRequestDto.build()
                .clone(requestVo);

        Pager<FileAppStorageConfigRequestDto> pager = Pager.of(
                requestVo.getPageNo(),
                requestVo.getPageSize()
        );
        pager.toParamObject(dto);

        PageResult<FileAppStorageConfigResponseDto> resultList =
                fileAppStorageConfigService.getFileAppStorageConfigByNextPage(
                        FileAppStorageConfigQueryEnum.DESC_ID, pager);

        if (ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())) {
            log.info("APP 分页查询文件应用存储配置完成, 无数据");
            return new PageResult<>();
        }

        PageResult<FileAppStorageConfigResponseVo> result =
                resultList.convertBuild(FileAppStorageConfigResponseVo.class);
        log.info("APP 分页查询文件应用存储配置完成, 总数: {}", result.getTotal());
        return result;
    }

    /**
     * 查看文件应用存储配置详情（APP）
     */
    @ApiDoc(
            value = "查看文件应用存储配置详情(APP)",
            description = "根据ID获取文件应用存储配置详情(APP)",
            request = HttpRequestByIdVo.class,
            response = FileAppStorageConfigResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_APP_STORAGE_CONFIG_INFO)
    public FileAppStorageConfigResponseVo info( @Validated HttpRequestByIdVo idVo) {

        log.info("APP 查询文件应用存储配置详情, ID: {}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            log.warn("APP 查询文件应用存储配置详情参数错误, ID: {}", idVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileAppStorageConfigResponseDto dto =
                fileAppStorageConfigService.getFileAppStorageConfigById(idVo.getId());

        if (dto == null) {
            log.warn("APP 文件应用存储配置不存在, ID: {}", idVo.getId());
            throw new RuntimeException("数据不存在");
        }

        FileAppStorageConfigResponseVo vo = FileAppStorageConfigResponseVo.build()
                .clone(dto);
        log.info("APP 查询文件应用存储配置详情成功, ID: {}", idVo.getId());
        return vo;
    }
}

