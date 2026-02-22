package com.suven.framework.upload.controller;

import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.api.RequestMethodEnum;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.HttpRequestByUserIdPageVo;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.upload.service.FileUploadProcessService;
import com.suven.framework.upload.vo.request.FileUploadRequestVo;
import com.suven.framework.upload.vo.response.FileUploadProcessResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 文件上传业务流程控制器
 * 
 * 功能：
 * 1. 文件上传（执行完整的业务流程）
 * 2. 查询处理进度
 * 3. 重试失败记录
 * 4. 查询操作记录
 * 
 * 完整业务流程：
 * 1. 公司企业申请业务功能（通过 CompanyBusinessFunctionWebController）
 * 2. 配置字段映射（通过 CompanyBusinessFunctionWebController）
 * 3. 激活业务功能（通过 CompanyBusinessFunctionWebController）
 * 4. 上传文件（本控制器）
 * 5. 系统自动：解析文件 -> 数据映射 -> 数据校验 -> 保存记录 -> 回调业务服务
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@ApiDoc(
    group = DocumentConst.Sys.SYS_DOC_GROUP,
    groupDesc = DocumentConst.Sys.SYS_DOC_DES,
    module = "文件上传业务流程模块",
    isApp = true
)
@RestController
@Slf4j
@Validated
public class FileUploadProcessWebController {

    @Autowired
    private FileUploadProcessService fileUploadProcessService;

    /**
     * URL 命令常量接口
     */
    public interface UrlCommand {
        /** 上传文件（执行完整业务流程） */
        String UPLOAD_FILE = "/saas/upload/process";
        /** 获取处理进度 */
        String GET_PROGRESS = "/saas/upload/progress";
        /** 重试失败记录 */
        String RETRY_FAILED = "/saas/upload/retry";
        /** 批量重试失败记录 */
        String BATCH_RETRY_FAILED = "/saas/upload/batchRetry";
    }

    /**
     * 上传文件 - 执行完整业务流程
     * 
     * 完整业务流程：
     * 1. 校验业务功能配置
     * 2. 创建操作记录
     * 3. 解析文件
     * 4. 数据映射转换
     * 5. 数据校验
     * 6. 保存解释记录
     * 7. 回调业务服务
     * 
     * @param file 上传的文件
     * @param requestVo 上传请求参数
     * @return FileUploadProcessResponseVo 处理结果
     */
    @ApiDoc(
        value = "上传文件（执行完整业务流程）",
        description = "上传文件并执行完整的业务流程：解析->映射->校验->保存->回调",
        request = FileUploadRequestVo.class,
        response = FileUploadProcessResponseVo.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE)
    public FileUploadProcessResponseVo uploadFile(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute FileUploadRequestVo requestVo) {
        
        log.info("上传文件开始: businessUniqueCode={}, fileName={}, size={}", 
                requestVo.getBusinessUniqueCode(), 
                file != null ? file.getOriginalFilename() : "null",
                file != null ? file.getSize() : 0);

        // 参数校验
        if (file == null || file.isEmpty()) {
            log.error("上传文件为空");
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "上传文件不能为空");
        }

        if (StringUtils.isBlank(requestVo.getBusinessUniqueCode())) {
            log.error("业务唯一码为空");
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "业务唯一码不能为空");
        }

        try {
            // 执行完整的文件上传业务流程
            FileUploadProcessResponseVo responseVo = fileUploadProcessService.processUpload(
                    requestVo, 
                    file.getInputStream(), 
                    file.getOriginalFilename());

            log.info("上传文件完成: success={}, message={}", 
                    responseVo.isSuccess(), responseVo.getMessage());

            return responseVo;

        } catch (Exception e) {
            log.error("上传文件异常", e);
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_UNKOWNN_FAIL, "上传文件失败: " + e.getMessage());
        }
    }

    /**
     * 获取处理进度
     * 
     * @param operationRecordId 操作记录ID
     * @return Map<String, Object> 进度信息
     */
    @ApiDoc(
        value = "获取处理进度",
        description = "根据操作记录ID查询文件处理进度",
        request = Long.class,
        response = Map.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.GET_PROGRESS)
    public Map<String, Object> getProcessProgress(
            @RequestParam("operationRecordId") Long operationRecordId) {
        
        log.info("获取处理进度: operationRecordId={}", operationRecordId);

        if (operationRecordId == null || operationRecordId <= 0) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "操作记录ID无效");
        }

        return fileUploadProcessService.getProcessProgress(operationRecordId);
    }

    /**
     * 重试失败记录
     * 
     * @param idVo 操作记录ID
     * @return FileUploadProcessResponseVo 处理结果
     */
    @ApiDoc(
        value = "重试失败记录",
        description = "重新处理指定操作记录下失败的解释记录",
        request = HttpRequestByIdVo.class,
        response = FileUploadProcessResponseVo.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.RETRY_FAILED)
    public FileUploadProcessResponseVo retryFailedRecords(
            @Validated @RequestBody HttpRequestByIdVo idVo) {
        
        log.info("重试失败记录: operationRecordId={}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "操作记录ID无效");
        }

        return fileUploadProcessService.retryFailedRecords(idVo.getId());
    }

    /**
     * 批量重试失败记录
     * 
     * @param pageVo 分页参数（使用id字段作为操作记录ID）
     * @return List<FileUploadProcessResponseVo> 处理结果列表
     */
    @ApiDoc(
        value = "批量重试失败记录",
        description = "批量重新处理失败的解释记录（暂不支持）",
        request = HttpRequestByUserIdPageVo.class,
        response = List.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.BATCH_RETRY_FAILED)
    public String batchRetryFailedRecords(
            @Validated @RequestBody HttpRequestByUserIdPageVo pageVo) {
        
        log.info("批量重试失败记录: operationRecordId={}", pageVo.getId());

        if (pageVo.getId() == null || pageVo.getId() <= 0) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "操作记录ID无效");
        }

        // 批量重试的实现可以根据业务需求进行扩展
        // 暂时返回提示信息
        return "批量重试功能暂未实现，请使用单个重试接口";
    }
}
