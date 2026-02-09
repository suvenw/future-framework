package com.suven.framework.upload.controller;

import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.upload.dto.enums.FileUploadActionWaterQueryEnum;
import com.suven.framework.upload.dto.request.FileUploadActionWaterRequestDto;
import com.suven.framework.upload.dto.response.FileUploadActionWaterResponseDto;
import com.suven.framework.upload.facade.FileUploadActionWaterFacade;
import com.suven.framework.upload.service.FileUploadActionWaterService;
import com.suven.framework.upload.vo.request.FileUploadActionWaterRequestVo;
import com.suven.framework.upload.vo.response.FileUploadActionWaterResponseVo;
 
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 文件上传行为流水 APP 接口控制器
 */
@ApiDoc(
        group = DocumentConst.Api.API_DOC_GROUP,
        groupDesc = DocumentConst.Api.API_DOC_DES,
        module = "文件上传行为流水APP接口",
        isApp = true
)
@Controller
@Slf4j
@Validated
public class FileUploadActionWaterController {

    @Autowired
    private FileUploadActionWaterFacade fileUploadActionWaterFacade;

    @Autowired
    private FileUploadActionWaterService fileUploadActionWaterService;

    /**
     * URL 命令常量接口
     */
    public interface UrlCommand {
        String UPLOAD_FILE_UPLOAD_ACTION_WATER_PAGE_LIST = "/upload/fileuploadactionwater/pageList";
        String UPLOAD_FILE_UPLOAD_ACTION_WATER_INFO = "/upload/fileuploadactionwater/info";
    }

    /**
     * 分页获取文件上传行为流水信息（APP）
     */
    @ApiDoc(
            value = "分页获取文件上传行为流水信息(APP)",
            description = "根据查询条件分页获取文件上传行为流水列表(APP)",
            request = FileUploadActionWaterRequestVo.class,
            response = FileUploadActionWaterResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_ACTION_WATER_PAGE_LIST)
    public PageResult<FileUploadActionWaterResponseVo> pageList(
            @Valid FileUploadActionWaterRequestVo requestVo) {

        log.info("APP 分页查询文件上传行为流水, 参数: {}", requestVo);

        FileUploadActionWaterRequestDto dto = FileUploadActionWaterRequestDto.build()
                .clone(requestVo);

        Pager<FileUploadActionWaterRequestDto> pager = new Pager<>(
                requestVo.getPageNo(),
                requestVo.getPageSize()
        );
        pager.toParamObject(dto);

        FileUploadActionWaterQueryEnum queryEnum = FileUploadActionWaterQueryEnum.DESC_ID;
        PageResult<FileUploadActionWaterResponseDto> resultList =
                fileUploadActionWaterService.getFileUploadActionWaterByNextPage(queryEnum, pager);

        if (ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())) {
            log.info("APP 分页查询文件上传行为流水完成, 无数据");
            return new PageResult<>();
        }

        PageResult<FileUploadActionWaterResponseVo> result =
                resultList.convertBuild(FileUploadActionWaterResponseVo.class);
        log.info("APP 分页查询文件上传行为流水完成, 总数: {}", result.getTotal());
        return result;
    }

    /**
     * 查看文件上传行为流水详情（APP）
     */
    @ApiDoc(
            value = "查看文件上传行为流水详情(APP)",
            description = "根据ID获取文件上传行为流水详情(APP)",
            request = HttpRequestByIdVo.class,
            response = FileUploadActionWaterResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_ACTION_WATER_INFO)
    public FileUploadActionWaterResponseVo info(@Valid HttpRequestByIdVo idVo) {

        log.info("APP 查询文件上传行为流水详情, ID: {}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            log.warn("APP 查询文件上传行为流水详情参数错误, ID: {}", idVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileUploadActionWaterResponseDto dto =
                fileUploadActionWaterService.getFileUploadActionWaterById(idVo.getId());

        if (dto == null) {
            log.warn("APP 文件上传行为流水不存在, ID: {}", idVo.getId());
            throw new RuntimeException("数据不存在");
        }

        FileUploadActionWaterResponseVo vo = FileUploadActionWaterResponseVo.build()
                .clone(dto);
        log.info("APP 查询文件上传行为流水详情成功, ID: {}", idVo.getId());
        return vo;
    }
}

