package com.suven.framework.upload.controller;

import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.api.RequestMethodEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.upload.dto.request.SaaSFileDataQueryRequestDto;
import com.suven.framework.upload.entity.SaaSFileDownloadRecord;
import com.suven.framework.upload.entity.SaaSFileFieldMapping;
import com.suven.framework.upload.service.SaaSFileGenerateService;
import com.suven.framework.upload.vo.request.SaaSFileDownloadQueryRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * SaaS文件生成与下载控制器
 * 
 * 功能：
 * 1. 申请生成文件（同步/异步）
 * 2. 查询生成状态和进度
 * 3. 获取下载URL
 * 4. 分页查询下载记录
 * 5. 管理字段映射
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@ApiDoc(
    group = DocumentConst.Sys.SYS_DOC_GROUP,
    groupDesc = DocumentConst.Sys.SYS_DOC_DES,
    module = "SaaS文件生成与下载模块",
    isApp = true
)
@RestController
@Slf4j
@Validated
public class SaaSFileGenerateWebController {

    @Autowired
    private SaaSFileGenerateService fileGenerateService;

    /**
     * URL命令常量接口
     */
    public interface UrlCommand {
        /** 申请生成文件 */
        String APPLY_GENERATE = "/saas/file/generate/apply";
        /** 同步生成文件 */
        String SYNC_GENERATE = "/saas/file/generate/sync";
        /** 异步生成文件 */
        String ASYNC_GENERATE = "/saas/file/generate/async";
        /** 获取生成状态 */
        String GENERATE_STATUS = "/saas/file/generate/status";
        /** 获取下载URL */
        String DOWNLOAD_URL = "/saas/file/generate/downloadUrl";
        /** 获取生成进度 */
        String GENERATE_PROGRESS = "/saas/file/generate/progress";
        /** 分页查询下载记录 */
        String DOWNLOAD_PAGE_LIST = "/saas/file/generate/pageList";
        /** 根据业务码查询下载记录 */
        String DOWNLOAD_BY_BIZ_CODE = "/saas/file/generate/biz/list";
        /** 取消生成 */
        String CANCEL_GENERATE = "/saas/file/generate/cancel";
        /** 删除下载记录 */
        String DELETE_DOWNLOAD = "/saas/file/generate/delete";
        /** 获取字段映射 */
        String FIELD_MAPPINGS = "/saas/file/generate/fieldMappings";
        /** 获取中文表头 */
        String CHINESE_HEADERS = "/saas/file/generate/headers";
    }

    /**
     * 申请生成文件（异步）
     * 
     * 业务流程：
     * 1. 接收业务方提供的数据查询URL和参数
     * 2. 保存下载申请记录
     * 3. 异步调用业务接口获取数据
     * 4. 将英文字段转换为中文
     * 5. 生成XLS/CSV文件
     * 6. 上传到文件存储服务
     * 7. 更新下载记录的URL
     */
    @ApiDoc(
        value = "申请生成文件",
        description = "异步申请生成文件，返回任务ID用于查询状态",
        request = SaaSFileDataQueryRequestDto.class,
        response = SaaSFileDownloadRecord.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.APPLY_GENERATE)
    public SaaSFileDownloadRecord applyGenerate(
            @RequestBody SaaSFileDataQueryRequestDto requestDto) {
        
        log.info("申请生成文件: businessUniqueCode={}", requestDto.getBusinessUniqueCode());

        if (StringUtils.isBlank(requestDto.getBusinessUniqueCode())) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "业务唯一码不能为空");
        }

        if (StringUtils.isBlank(requestDto.getDataQueryUrl())) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "数据查询URL不能为空");
        }

        return fileGenerateService.applyGenerateFile(requestDto);
    }

    /**
     * 同步生成文件
     * 
     * 适用于小数据量，直接返回下载URL
     */
    @ApiDoc(
        value = "同步生成文件",
        description = "同步生成文件，直接返回文件下载URL（适用于小数据量）",
        request = SaaSFileDataQueryRequestDto.class,
        response = String.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.SYNC_GENERATE)
    public String syncGenerate(@RequestBody SaaSFileDataQueryRequestDto requestDto) {
        log.info("同步生成文件: businessUniqueCode={}", requestDto.getBusinessUniqueCode());

        if (StringUtils.isBlank(requestDto.getBusinessUniqueCode())) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "业务唯一码不能为空");
        }

        return fileGenerateService.syncGenerateFile(requestDto);
    }

    /**
     * 异步生成文件
     */
    @ApiDoc(
        value = "异步生成文件",
        description = "异步生成文件，返回任务ID用于查询状态和进度",
        request = SaaSFileDataQueryRequestDto.class,
        response = Long.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.ASYNC_GENERATE)
    public long asyncGenerate(
            @RequestBody SaaSFileDataQueryRequestDto requestDto,
            @RequestParam(required = false) String callbackUrl) {
        
        log.info("异步生成文件: businessUniqueCode={}", requestDto.getBusinessUniqueCode());

        if (StringUtils.isBlank(requestDto.getBusinessUniqueCode())) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "业务唯一码不能为空");
        }

        return fileGenerateService.asyncGenerateFile(requestDto, callbackUrl);
    }

    /**
     * 获取生成状态
     */
    @ApiDoc(
        value = "获取生成状态",
        description = "根据任务ID查询文件生成状态",
        request = Long.class,
        response = SaaSFileDownloadRecord.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.GENERATE_STATUS)
    public SaaSFileDownloadRecord getGenerateStatus(@RequestParam long taskId) {
        log.info("查询生成状态: taskId={}", taskId);

        if (taskId <= 0) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "任务ID无效");
        }

        SaaSFileDownloadRecord record = fileGenerateService.getGenerateStatus(taskId);
        if (record == null) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_RESULT_IS_NULL, "任务记录不存在");
        }

        return record;
    }

    /**
     * 获取下载URL
     */
    @ApiDoc(
        value = "获取下载URL",
        description = "获取已生成文件的下载URL",
        request = Long.class,
        response = String.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.DOWNLOAD_URL)
    public String getDownloadUrl(@RequestParam long taskId) {
        log.info("获取下载URL: taskId={}", taskId);

        if (taskId <= 0) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "任务ID无效");
        }

        // 增加下载次数
        fileGenerateService.incrementDownloadCount(taskId);

        return fileGenerateService.getDownloadUrl(taskId);
    }

    /**
     * 获取生成进度
     */
    @ApiDoc(
        value = "获取生成进度",
        description = "查询文件生成的实时进度信息",
        request = Long.class,
        response = Map.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.GENERATE_PROGRESS)
    public Map<String, Object> getGenerateProgress(@RequestParam long taskId) {
        log.info("查询生成进度: taskId={}", taskId);

        if (taskId <= 0) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "任务ID无效");
        }

        return fileGenerateService.getGenerateProgress(taskId);
    }

    /**
     * 分页查询下载记录
     */
    @ApiDoc(
        value = "分页查询下载记录",
        description = "根据条件分页查询文件下载记录列表",
        request = SaaSFileDownloadQueryRequestVo.class,
        response = SaaSFileDownloadRecord.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.DOWNLOAD_PAGE_LIST)
    public PageResult<SaaSFileDownloadRecord> pageList(
            @RequestBody SaaSFileDownloadQueryRequestVo requestVo) {
        
        log.info("分页查询下载记录: pageNo={}, pageSize={}", requestVo.getPageNo(), requestVo.getPageSize());

        Pager pager = new Pager(requestVo.getPageNo(), requestVo.getPageSize());
        return fileGenerateService.pageQueryDownloadRecords(requestVo, pager);
    }

    /**
     * 根据业务唯一码查询下载记录
     */
    @ApiDoc(
        value = "根据业务码查询下载记录",
        description = "根据业务唯一码分页查询文件下载记录列表",
        request = String.class,
        response = SaaSFileDownloadRecord.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.DOWNLOAD_BY_BIZ_CODE)
    public PageResult<SaaSFileDownloadRecord> queryByBusinessCode(
            @RequestParam String businessUniqueCode,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        
        log.info("根据业务唯一码查询: businessUniqueCode={}", businessUniqueCode);

        if (StringUtils.isBlank(businessUniqueCode)) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "业务唯一码不能为空");
        }

        Pager pager = new Pager(pageNo, pageSize);
        return fileGenerateService.queryByBusinessCode(businessUniqueCode, pager);
    }

    /**
     * 取消生成任务
     */
    @ApiDoc(
        value = "取消生成任务",
        description = "取消进行中的文件生成任务",
        request = Long.class,
        response = Boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.CANCEL_GENERATE)
    public boolean cancelGenerate(@RequestParam long taskId) {
        log.info("取消生成任务: taskId={}", taskId);

        if (taskId <= 0) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "任务ID无效");
        }

        return fileGenerateService.cancelGenerate(taskId);
    }

    /**
     * 删除下载记录
     */
    @ApiDoc(
        value = "删除下载记录",
        description = "删除文件下载记录（软删除）",
        request = Long.class,
        response = Boolean.class,
        method = RequestMethodEnum.DELETE
    )
    @PostMapping(value = UrlCommand.DELETE_DOWNLOAD)
    public boolean deleteDownload(@RequestParam long taskId) {
        log.info("删除下载记录: taskId={}", taskId);

        if (taskId <= 0) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "任务ID无效");
        }

        return fileGenerateService.deleteDownloadRecord(taskId);
    }

    /**
     * 获取字段映射
     */
    @ApiDoc(
        value = "获取字段映射",
        description = "根据业务唯一码获取英文字段与中文字段的映射关系",
        request = String.class,
        response = SaaSFileFieldMapping.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.FIELD_MAPPINGS)
    public List<SaaSFileFieldMapping> getFieldMappings(@RequestParam String businessUniqueCode) {
        log.info("获取字段映射: businessUniqueCode={}", businessUniqueCode);

        if (StringUtils.isBlank(businessUniqueCode)) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "业务唯一码不能为空");
        }

        return fileGenerateService.getFieldMappings(businessUniqueCode);
    }

    /**
     * 获取中文表头
     */
    @ApiDoc(
        value = "获取中文表头",
        description = "根据业务唯一码生成中文表头列表（按排序号）",
        request = String.class,
        response = String.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.CHINESE_HEADERS)
    public List<String> getChineseHeaders(@RequestParam String businessUniqueCode) {
        log.info("获取中文表头: businessUniqueCode={}", businessUniqueCode);

        if (StringUtils.isBlank(businessUniqueCode)) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "业务唯一码不能为空");
        }

        List<SaaSFileFieldMapping> fieldMappings = fileGenerateService.getFieldMappings(businessUniqueCode);
        return fileGenerateService.generateChineseHeaders(fieldMappings);
    }
}
