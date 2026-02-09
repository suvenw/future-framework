package com.suven.framework.upload.controller;

import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.enums.RequestMethodEnum;
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
import com.suven.framework.util.exception.CodeEnum;
import com.suven.framework.util.exception.ExceptionFactory;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * SaaS平台文件上传下载控制器
 * 
 * 编码规范：
 * 1. 类必须以 Controller 结尾
 * 2. 必须使用 @RestController 注解
 * 3. 必须使用 @Slf4j 记录日志
 * 4. 必须使用 @Validated 开启参数校验
 * 5. 依赖注入必须使用 @Autowired
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@ApiDoc(
    group = DocumentConst.Sys.SYS_DOC_GROUP,
    groupDesc = DocumentConst.Sys.SYS_DOC_DES,
    module = "SaaS平台文件上传下载模块",
    isApp = true
)
@RestController
@Slf4j
@Validated
public class SaaSFileWebController {

    @Autowired
    private SaaSFileFacade saaSFileFacade;

    /**
     * URL 命令常量接口
     * 规范：全大写，下划线分隔，描述性名称
     */
    public interface UrlCommand {
        String SAAS_FILE_PAGE_LIST = "/saas/file/pageList";
        String SAAS_FILE_INFO = "/saas/file/info";
        String SAAS_FILE_UPLOAD = "/saas/file/upload";
        String SAAS_FILE_DOWNLOAD = "/saas/file/download";
        String SAAS_FILE_GENERATE = "/saas/file/generate";
        String SAAS_FILE_DELETE = "/saas/file/delete";
        String SAAS_FILE_TEMP_URL = "/saas/file/tempUrl";
    }

    /**
     * 分页获取文件列表
     */
    @ApiDoc(
        value = "分页获取文件列表",
        description = "根据条件分页获取SaaS平台文件列表",
        request = SaaSFileQueryRequestVo.class,
        response = SaaSFileShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.SAAS_FILE_PAGE_LIST)
    public PageResult<SaaSFileShowResponseVo> pageList(
            @Valid SaaSFileQueryRequestVo queryRequestVo) {
        
        log.info("SaaS文件分页查询, 参数: {}", queryRequestVo);
        
        SaaSFileRequestDto requestDto = convertToRequestDto(queryRequestVo);
        Pager pager = new Pager(queryRequestVo.getPageNo(), queryRequestVo.getPageSize());
        pager.toParamObject(requestDto);
        
        PageResult<SaaSFileResponseDto> result = saaSFileFacade.getSaaSFileService()
                .queryFilePage(requestDto, pager);
        
        if (ObjectTrue.isEmpty(result) || ObjectTrue.isEmpty(result.getList())) {
            log.info("SaaS文件分页查询完成, 无数据");
            return new PageResult<>();
        }
        
        PageResult<SaaSFileShowResponseVo> voResult = result
                .convertBuild(SaaSFileShowResponseVo.class);
        log.info("SaaS文件分页查询完成, 总数: {}", result.getTotal());
        return voResult;
    }

    /**
     * 获取文件详情
     */
    @ApiDoc(
        value = "获取文件详情",
        description = "根据文件ID获取SaaS平台文件详情",
        request = HttpRequestByIdVo.class,
        response = SaaSFileShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.SAAS_FILE_INFO)
    public SaaSFileShowResponseVo detail(@Valid HttpRequestByIdVo idRequestVo) {
        
        log.info("SaaS文件详情查询, ID: {}", idRequestVo.getId());
        
        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("SaaS文件详情查询参数错误, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.PARAM_IS_INVALID);
        }
        
        SaaSFileResponseDto result = saaSFileFacade.getSaaSFileService()
                .getFileDetail(idRequestVo.getId());
        
        if (result == null) {
            log.warn("SaaS文件不存在, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.DATA_NOT_FOUND);
        }
        
        SaaSFileShowResponseVo vo = SaaSFileShowResponseVo.build()
                .clone(result);
        log.info("SaaS文件详情查询成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 上传文件
     */
    @ApiDoc(
        value = "上传文件",
        description = "上传文件到SaaS平台存储",
        request = SaaSFileUploadRequestVo.class,
        response = SaaSFileUploadResponseVo.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.SAAS_FILE_UPLOAD)
    public SaaSFileUploadResponseVo upload(
            @Valid SaaSFileUploadRequestVo uploadRequestVo,
            @RequestParam("file") MultipartFile file) {
        
        log.info("SaaS文件上传开始, 文件名: {}, 大小: {}", 
                file.getOriginalFilename(), file.getSize());
        
        if (file.isEmpty()) {
            log.warn("SaaS文件上传失败, 文件为空");
            throw ExceptionFactory.sysException(CodeEnum.FILE_IS_EMPTY);
        }
        
        SaaSFileRequestDto requestDto = convertToRequestDto(uploadRequestVo);
        SaaSFileResponseDto result = saaSFileFacade.getSaaSFileService()
                .uploadFile(requestDto, file);
        
        SaaSFileUploadResponseVo vo = SaaSFileUploadResponseVo.build()
                .clone(result);
        log.info("SaaS文件上传成功, 文件ID: {}", result.getFileUploadStorageId());
        return vo;
    }

    /**
     * 下载文件
     */
    @ApiDoc(
        value = "下载文件",
        description = "获取SaaS平台文件下载信息或临时URL",
        request = SaaSFileDownloadRequestVo.class,
        response = SaaSFileDownloadResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.SAAS_FILE_DOWNLOAD)
    public SaaSFileDownloadResponseVo download(
            @Valid SaaSFileDownloadRequestVo downloadRequestVo) {
        
        log.info("SaaS文件下载开始, 文件ID: {}", downloadRequestVo.getFileUploadStorageId());
        
        if (downloadRequestVo.getFileUploadStorageId() <= 0) {
            log.warn("SaaS文件下载参数错误, ID: {}", downloadRequestVo.getFileUploadStorageId());
            throw ExceptionFactory.sysException(CodeEnum.PARAM_IS_INVALID);
        }
        
        SaaSFileRequestDto requestDto = convertToRequestDto(downloadRequestVo);
        SaaSFileResponseDto result = saaSFileFacade.getSaaSFileService()
                .downloadFile(requestDto);
        
        SaaSFileDownloadResponseVo vo = SaaSFileDownloadResponseVo.build()
                .clone(result);
        
        // 设置临时URL信息
        if (downloadRequestVo.getGenerateTempUrl() == 1) {
            String tempUrl = saaSFileFacade.getSaaSFileService()
                    .generateTempUrl(
                            downloadRequestVo.getFileUploadStorageId(),
                            downloadRequestVo.getExpirationTime() > 0 ? downloadRequestVo.getExpirationTime() : 3600
                    );
            vo.setTempAccessUrl(tempUrl);
        }
        
        log.info("SaaS文件下载成功, 文件ID: {}", downloadRequestVo.getFileUploadStorageId());
        return vo;
    }

    /**
     * 生成大数据文件(调用第三方接口)
     */
    @ApiDoc(
        value = "生成大数据文件",
        description = "调用第三方接口生成大数据文件",
        request = SaaSFileGenerateRequestVo.class,
        response = SaaSFileGenerateResponseVo.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.SAAS_FILE_GENERATE)
    public SaaSFileGenerateResponseVo generate(
            @Valid SaaSFileGenerateRequestVo generateRequestVo) {
        
        log.info("SaaS大数据文件生成开始, API地址: {}", generateRequestVo.getThirdPartyApiUrl());
        
        if (StringUtils.isBlank(generateRequestVo.getThirdPartyApiUrl())) {
            log.warn("SaaS大数据文件生成失败, API地址为空");
            throw ExceptionFactory.sysException(CodeEnum.PARAM_IS_INVALID);
        }
        
        SaaSFileRequestDto requestDto = convertToRequestDto(generateRequestVo);
        SaaSFileResponseDto result = saaSFileFacade.getSaaSFileService()
                .generateFile(requestDto);
        
        SaaSFileGenerateResponseVo vo = SaaSFileGenerateResponseVo.build()
                .clone(result);
        vo.setGenerateStatus(result.getGenerateStatus());
        vo.setProgressPercent(result.getProgressPercent());
        vo.setErrorMessage(result.getErrorMessage());
        
        log.info("SaaS大数据文件生成完成, 文件ID: {}", result.getFileUploadStorageId());
        return vo;
    }

    /**
     * 删除文件
     */
    @ApiDoc(
        value = "删除文件",
        description = "根据文件ID删除SaaS平台文件",
        request = HttpRequestByIdListVo.class,
        response = Integer.class,
        method = RequestMethodEnum.DELETE
    )
    @PostMapping(value = UrlCommand.SAAS_FILE_DELETE)
    public int delete(@Valid HttpRequestByIdListVo idRequestVo) {
        
        log.info("SaaS文件删除开始, ID列表: {}", idRequestVo.getIdList());
        
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("SaaS文件删除参数错误, ID列表为空");
            throw ExceptionFactory.sysException(CodeEnum.PARAM_IS_INVALID);
        }
        
        int result = saaSFileFacade.getSaaSFileService()
                .deleteFiles(idRequestVo.getIdList());
        log.info("SaaS文件删除完成, 删除数量: {}", result);
        return result;
    }

    /**
     * 转换上传请求VO到DTO
     */
    private SaaSFileRequestDto convertToRequestDto(SaaSFileUploadRequestVo vo) {
        SaaSFileRequestDto dto = new SaaSFileRequestDto();
        dto.setAppId(vo.getAppId());
        dto.setClientId(vo.getClientId());
        dto.setUseBusinessId(vo.getUseBusinessId());
        dto.setFileProductName(vo.getFileProductName());
        dto.setFileBusinessName(vo.getFileBusinessName());
        dto.setInterpretData(vo.getInterpretData());
        dto.setIdempotent(vo.getIdempotent());
        return dto;
    }

    /**
     * 转换下载请求VO到DTO
     */
    private SaaSFileRequestDto convertToRequestDto(SaaSFileDownloadRequestVo vo) {
        SaaSFileRequestDto dto = new SaaSFileRequestDto();
        dto.setAppId(vo.getAppId());
        dto.setClientId(vo.getClientId());
        dto.setFileUploadStorageId(vo.getFileUploadStorageId());
        dto.setGenerateTempUrl(vo.getGenerateTempUrl());
        dto.setExpirationTime(vo.getExpirationTime() > 0 ? vo.getExpirationTime() : 3600);
        return dto;
    }

    /**
     * 转换生成请求VO到DTO
     */
    private SaaSFileRequestDto convertToRequestDto(SaaSFileGenerateRequestVo vo) {
        SaaSFileRequestDto dto = new SaaSFileRequestDto();
        dto.setAppId(vo.getAppId());
        dto.setClientId(vo.getClientId());
        dto.setUseBusinessId(vo.getUseBusinessId());
        dto.setFileProductName(vo.getFileProductName());
        dto.setFileBusinessName(vo.getFileBusinessName());
        dto.setThirdPartyApiUrl(vo.getThirdPartyApiUrl());
        dto.setThirdPartyApiMethod(vo.getThirdPartyApiMethod());
        dto.setThirdPartyApiHeaders(vo.getThirdPartyApiHeaders());
        dto.setThirdPartyApiParams(vo.getThirdPartyApiParams());
        dto.setFileTypeForGenerate(vo.getFileType());
        dto.setFileNamePrefix(vo.getFileNamePrefix());
        dto.setInterpretData(vo.getInterpretData());
        dto.setTimeout(vo.getTimeout() > 0 ? vo.getTimeout() : 300);
        return dto;
    }

    /**
     * 转换查询请求VO到DTO
     */
    private SaaSFileRequestDto convertToRequestDto(SaaSFileQueryRequestVo vo) {
        SaaSFileRequestDto dto = new SaaSFileRequestDto();
        dto.setAppId(vo.getAppId());
        dto.setClientId(vo.getClientId());
        dto.setUseBusinessId(vo.getUseBusinessId());
        dto.setFileProductName(vo.getFileProductName());
        dto.setFileBusinessName(vo.getFileBusinessName());
        dto.setFileSourceName(vo.getFileSourceName());
        dto.setFileType(vo.getFileType());
        dto.setFileMd5(vo.getFileMd5());
        dto.setCreateDateStart(vo.getCreateDateStart());
        dto.setCreateDateEnd(vo.getCreateDateEnd());
        return dto;
    }
}
