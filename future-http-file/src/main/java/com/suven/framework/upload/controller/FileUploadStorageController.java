package com.suven.framework.upload.controller;

import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.upload.dto.enums.FileUploadStorageQueryEnum;
import com.suven.framework.upload.dto.request.FileUploadStorageRequestDto;
import com.suven.framework.upload.dto.response.FileUploadStorageResponseDto;
import com.suven.framework.upload.facade.FileUploadStorageFacade;
import com.suven.framework.upload.service.FileUploadStorageService;
import com.suven.framework.upload.vo.request.FileUploadStorageRequestVo;
import com.suven.framework.upload.vo.response.FileUploadStorageResponseVo;
 
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 文件上传存储配置 APP 接口控制器
 */
@ApiDoc(
        group = DocumentConst.Api.API_DOC_GROUP,
        groupDesc = DocumentConst.Api.API_DOC_DES,
        module = "文件上传存储配置APP接口",
        isApp = true
)
@Controller
@Slf4j
@Validated
public class FileUploadStorageController {

    @Autowired
    private FileUploadStorageFacade fileUploadStorageFacade;

    @Autowired
    private FileUploadStorageService fileUploadStorageService;

    /**
     * URL 命令常量接口
     */
    public interface UrlCommand {
        String UPLOAD_FILE_UPLOAD_STORAGE_PAGE_LIST = "/upload/fileuploadstorage/pageList";
        String UPLOAD_FILE_UPLOAD_STORAGE_INFO = "/upload/fileuploadstorage/info";
    }

    /**
     * 分页获取文件上传存储配置信息（APP）
     */
    @ApiDoc(
            value = "分页获取文件上传存储配置信息(APP)",
            description = "根据查询条件分页获取文件上传存储配置列表(APP)",
            request = FileUploadStorageRequestVo.class,
            response = FileUploadStorageResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_STORAGE_PAGE_LIST)
    public PageResult<FileUploadStorageResponseVo> pageList(
            @Validated FileUploadStorageRequestVo requestVo) {

        log.info("APP 分页查询文件上传存储配置, 参数: {}", requestVo);

        FileUploadStorageRequestDto dto = FileUploadStorageRequestDto.build()
                .clone(requestVo);

        Pager<FileUploadStorageRequestDto> pager = new Pager<>(
                requestVo.getPageNo(),
                requestVo.getPageSize()
        );
        pager.toParamObject(dto);

        PageResult<FileUploadStorageResponseDto> resultList =
                fileUploadStorageService.getFileUploadStorageByNextPage(
                        FileUploadStorageQueryEnum.DESC_ID, pager);

        if (ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())) {
            log.info("APP 分页查询文件上传存储配置完成, 无数据");
            return new PageResult<>();
        }

        PageResult<FileUploadStorageResponseVo> result =
            resultList.convertBuild(FileUploadStorageResponseVo.class);
        log.info("APP 分页查询文件上传存储配置完成, 总数: {}", result.getTotal());
        return result;
    }

    /**
     * 查看文件上传存储配置详情（APP）
     */
    @ApiDoc(
            value = "查看文件上传存储配置详情(APP)",
            description = "根据ID获取文件上传存储配置详情(APP)",
            request = HttpRequestByIdVo.class,
            response = FileUploadStorageResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_STORAGE_INFO)
    public FileUploadStorageResponseVo info( @Validated HttpRequestByIdVo idVo) {

        log.info("APP 查询文件上传存储配置详情, ID: {}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            log.warn("APP 查询文件上传存储配置详情参数错误, ID: {}", idVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileUploadStorageResponseDto dto =
                fileUploadStorageService.getFileUploadStorageById(idVo.getId());

        if (dto == null) {
            log.warn("APP 文件上传存储配置不存在, ID: {}", idVo.getId());
            throw new RuntimeException("数据不存在");
        }

        FileUploadStorageResponseVo vo = FileUploadStorageResponseVo.build()
                .clone(dto);
        log.info("APP 查询文件上传存储配置详情成功, ID: {}", idVo.getId());
        return vo;
    }
}

