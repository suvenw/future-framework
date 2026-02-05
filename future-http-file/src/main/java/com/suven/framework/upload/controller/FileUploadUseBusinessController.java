package com.suven.framework.upload.controller;

import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.upload.dto.enums.FileUploadUseBusinessQueryEnum;
import com.suven.framework.upload.dto.request.FileUploadUseBusinessRequestDto;
import com.suven.framework.upload.dto.response.FileUploadUseBusinessResponseDto;
import com.suven.framework.upload.facade.FileUploadUseBusinessFacade;
import com.suven.framework.upload.service.FileUploadUseBusinessService;
import com.suven.framework.upload.vo.request.FileUploadUseBusinessRequestVo;
import com.suven.framework.upload.vo.response.FileUploadUseBusinessResponseVo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 文件上传业务配置 APP 接口控制器
 */
@ApiDoc(
        group = DocumentConst.Api.API_DOC_GROUP,
        groupDesc = DocumentConst.Api.API_DOC_DES,
        module = "文件上传业务配置APP接口",
        isApp = true
)
@Controller
@Slf4j
@Validated
public class FileUploadUseBusinessController {

    @Autowired
    private FileUploadUseBusinessFacade fileUploadUseBusinessFacade;

    @Autowired
    private FileUploadUseBusinessService fileUploadUseBusinessService;

    /**
     * URL 命令常量接口
     */
    public interface UrlCommand {
        String UPLOAD_FILE_UPLOAD_USE_BUSINESS_PAGE_LIST = "/upload/fileuploadusebusiness/pageList";
        String UPLOAD_FILE_UPLOAD_USE_BUSINESS_INFO = "/upload/fileuploadusebusiness/info";
    }

    /**
     * 分页获取文件上传业务配置信息（APP）
     */
    @ApiDoc(
            value = "分页获取文件上传业务配置信息(APP)",
            description = "根据查询条件分页获取文件上传业务配置列表(APP)",
            request = FileUploadUseBusinessRequestVo.class,
            response = FileUploadUseBusinessResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_USE_BUSINESS_PAGE_LIST)
    public PageResult<FileUploadUseBusinessResponseVo> pageList(
            @Valid FileUploadUseBusinessRequestVo requestVo) {

        log.info("APP 分页查询文件上传业务配置, 参数: {}", requestVo);

        FileUploadUseBusinessRequestDto dto = FileUploadUseBusinessRequestDto.build()
                .clone(requestVo);

        Pager<FileUploadUseBusinessRequestDto> pager = new Pager<>(
                requestVo.getPageNo(),
                requestVo.getPageSize()
        );
        pager.toParamObject(dto);

        FileUploadUseBusinessQueryEnum queryEnum = FileUploadUseBusinessQueryEnum.DESC_ID;
        PageResult<FileUploadUseBusinessResponseDto> resultList =
                fileUploadUseBusinessService.getFileUploadUseBusinessByNextPage(queryEnum, pager);

        if (ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())) {
            log.info("APP 分页查询文件上传业务配置完成, 无数据");
            return new PageResult<>();
        }

        PageResult<FileUploadUseBusinessResponseVo> result =
                resultList.convertBuild(FileUploadUseBusinessResponseVo.class);
        log.info("APP 分页查询文件上传业务配置完成, 总数: {}", result.getTotal());
        return result;
    }

    /**
     * 查看文件上传业务配置详情（APP）
     */
    @ApiDoc(
            value = "查看文件上传业务配置详情(APP)",
            description = "根据ID获取文件上传业务配置详情(APP)",
            request = HttpRequestByIdVo.class,
            response = FileUploadUseBusinessResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_USE_BUSINESS_INFO)
    public FileUploadUseBusinessResponseVo info(@Valid HttpRequestByIdVo idVo) {

        log.info("APP 查询文件上传业务配置详情, ID: {}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            log.warn("APP 查询文件上传业务配置详情参数错误, ID: {}", idVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileUploadUseBusinessResponseDto dto =
                fileUploadUseBusinessService.getFileUploadUseBusinessById(idVo.getId());

        if (dto == null) {
            log.warn("APP 文件上传业务配置不存在, ID: {}", idVo.getId());
            throw new RuntimeException("数据不存在");
        }

        FileUploadUseBusinessResponseVo vo = FileUploadUseBusinessResponseVo.build()
                .clone(dto);
        log.info("APP 查询文件上传业务配置详情成功, ID: {}", idVo.getId());
        return vo;
    }
}

