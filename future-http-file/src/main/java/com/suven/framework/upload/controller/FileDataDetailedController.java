package com.suven.framework.upload.controller;

import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.upload.dto.enums.FileDataDetailedQueryEnum;
import com.suven.framework.upload.dto.request.FileDataDetailedRequestDto;
import com.suven.framework.upload.dto.response.FileDataDetailedResponseDto;
import com.suven.framework.upload.facade.FileDataDetailedFacade;
import com.suven.framework.upload.service.FileDataDetailedService;
import com.suven.framework.upload.vo.request.FileDataDetailedRequestVo;
import com.suven.framework.upload.vo.response.FileDataDetailedResponseVo;
 
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 文件数据明细 APP 接口控制器
 */
@ApiDoc(
        group = DocumentConst.Api.API_DOC_GROUP,
        groupDesc = DocumentConst.Api.API_DOC_DES,
        module = "文件数据明细APP接口",
        isApp = true
)
@Controller
@Slf4j
@Validated
public class FileDataDetailedController {

    @Autowired
    private FileDataDetailedFacade fileDataDetailedFacade;

    @Autowired
    private FileDataDetailedService fileDataDetailedService;

    /**
     * URL 命令常量接口
     */
    public interface UrlCommand {
        String UPLOAD_FILE_DATA_DETAILED_PAGE_LIST = "/upload/filedatadetailed/pageList";
        String UPLOAD_FILE_DATA_DETAILED_INFO = "/upload/filedatadetailed/info";
    }

    /**
     * 分页获取文件数据明细信息（APP）
     */
    @ApiDoc(
            value = "分页获取文件数据明细信息(APP)",
            description = "根据查询条件分页获取文件数据明细列表(APP)",
            request = FileDataDetailedRequestVo.class,
            response = FileDataDetailedResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_DATA_DETAILED_PAGE_LIST)
    public PageResult<FileDataDetailedResponseVo> pageList(
            @Valid FileDataDetailedRequestVo requestVo) {

        log.info("APP 分页查询文件数据明细, 参数: {}", requestVo);

        FileDataDetailedRequestDto dto = FileDataDetailedRequestDto.build()
                .clone(requestVo);

        Pager<FileDataDetailedRequestDto> pager = new Pager<>(
                requestVo.getPageNo(),
                requestVo.getPageSize()
        );
        pager.toParamObject(dto);

        PageResult<FileDataDetailedResponseDto> resultList =
                fileDataDetailedService.getFileDataDetailedByNextPage(
                        FileDataDetailedQueryEnum.DESC_ID, pager);

        if (ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())) {
            log.info("APP 分页查询文件数据明细完成, 无数据");
            return new PageResult<>();
        }

        PageResult<FileDataDetailedResponseVo> result =
                resultList.convertBuild(FileDataDetailedResponseVo.class);
        log.info("APP 分页查询文件数据明细完成, 总数: {}", result.getTotal());
        return result;
    }

    /**
     * 查看文件数据明细详情（APP）
     */
    @ApiDoc(
            value = "查看文件数据明细详情(APP)",
            description = "根据ID获取文件数据明细详情(APP)",
            request = HttpRequestByIdVo.class,
            response = FileDataDetailedResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_DATA_DETAILED_INFO)
    public FileDataDetailedResponseVo info(@Valid HttpRequestByIdVo idVo) {

        log.info("APP 查询文件数据明细详情, ID: {}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            log.warn("APP 查询文件数据明细详情参数错误, ID: {}", idVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileDataDetailedResponseDto dto =
                fileDataDetailedService.getFileDataDetailedById(idVo.getId());

        if (dto == null) {
            log.warn("APP 文件数据明细不存在, ID: {}", idVo.getId());
            throw new RuntimeException("数据不存在");
        }

        FileDataDetailedResponseVo vo = FileDataDetailedResponseVo.build()
                .clone(dto);
        log.info("APP 查询文件数据明细详情成功, ID: {}", idVo.getId());
        return vo;
    }
}

