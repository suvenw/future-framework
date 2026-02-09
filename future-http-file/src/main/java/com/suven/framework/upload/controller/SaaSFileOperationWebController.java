package com.suven.framework.upload.controller;

import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.api.RequestMethodEnum;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.upload.dto.request.SaaSFileInterpretRequestDto;
import com.suven.framework.upload.dto.request.SaaSFileOperationRequestDto;
import com.suven.framework.upload.dto.response.SaaSFileInterpretResponseDto;
import com.suven.framework.upload.dto.response.SaaSFileOperationResponseDto;
import com.suven.framework.upload.facade.SaaSFileFacade;
import com.suven.framework.upload.service.SaaSFileOperationService;
import com.suven.framework.upload.vo.request.SaaSFileCallbackRequestVo;
import com.suven.framework.upload.vo.request.SaaSFileOperationQueryVo;
import com.suven.framework.upload.vo.response.SaaSFileInterpretResponseVo;
import com.suven.framework.upload.vo.response.SaaSFileOperationResponseVo;
 
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SaaS文件业务操作控制器
 * 
 * 功能：
 * 1. 文件操作记录管理
 * 2. 文件解释记录管理
 * 3. 业务回调接口
 * 4. 数据分批查询接口
 * 5. 异常信息回写接口
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@ApiDoc(
    group = DocumentConst.Sys.SYS_DOC_GROUP,
    groupDesc = DocumentConst.Sys.SYS_DOC_DES,
    module = "SaaS文件业务操作模块",
    isApp = true
)
@RestController
@Slf4j
@Validated
public class SaaSFileOperationWebController {

    @Autowired
    private SaaSFileOperationService fileOperationService;

    @Autowired
    private SaaSFileFacade fileFacade;

    /**
     * URL 命令常量接口
     */
    public interface UrlCommand {
        String SAAS_OPERATION_PAGE_LIST = "/saas/operation/pageList";
        String SAAS_OPERATION_INFO = "/saas/operation/info";
        String SAAS_INTERPRET_PAGE_LIST = "/saas/interpret/pageList";
        String SAAS_INTERPRET_INFO = "/saas/interpret/info";
        String SAAS_INTERPRET_PENDING = "/saas/interpret/pending";
        String SAAS_CALLBACK = "/saas/callback";
        String SAAS_CALLBACK_RESULT = "/saas/callback/result";
        String SAAS_OPERATION_DELETE = "/saas/operation/delete";
        String SAAS_INTERPRET_DELETE = "/saas/interpret/delete";
    }

    /**
     * 分页获取操作记录列表
     */
    @ApiDoc(
        value = "分页获取操作记录列表",
        description = "根据条件分页获取SaaS平台文件操作记录",
        request = SaaSFileOperationQueryVo.class,
        response = SaaSFileOperationResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.SAAS_OPERATION_PAGE_LIST)
    public PageResult<SaaSFileOperationResponseVo> pageList(
            @Validated  SaaSFileOperationQueryVo queryRequestVo) {
        
        log.info("SaaS操作记录分页查询, 参数: {}", queryRequestVo);
        
        SaaSFileOperationRequestDto requestDto = convertToRequestDto(queryRequestVo);
        Pager pager = new Pager(queryRequestVo.getPageNo(), queryRequestVo.getPageSize());
        pager.toParamObject(requestDto);
        
        PageResult<SaaSFileOperationResponseDto> result = fileOperationService.queryOperationPage(requestDto, pager);
        
        if (ObjectTrue.isEmpty(result) || ObjectTrue.isEmpty(result.getList())) {
            log.info("SaaS操作记录分页查询完成, 无数据");
            return new PageResult<>();
        }
        
        PageResult<SaaSFileOperationResponseVo> voResult = new PageResult<>();
        voResult.setTotal(result.getTotal());
        voResult.setList(result.getList().stream()
            .map(dto -> SaaSFileOperationResponseVo.build().clone(dto))
            .toList());
        
        log.info("SaaS操作记录分页查询完成, 总数: {}", result.getTotal());
        return voResult;
    }

    /**
     * 获取操作记录详情
     */
    @ApiDoc(
        value = "获取操作记录详情",
        description = "根据ID获取SaaS平台文件操作记录详情",
        request = HttpRequestByIdVo.class,
        response = SaaSFileOperationResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.SAAS_OPERATION_INFO)
    public SaaSFileOperationResponseVo detail( @Validated  HttpRequestByIdVo idRequestVo) {
        
        log.info("SaaS操作记录详情查询, ID: {}", idRequestVo.getId());
        
        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("SaaS操作记录详情查询参数错误, ID: {}", idRequestVo.getId());
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR);
        }
        
        SaaSFileOperationResponseDto result = fileOperationService.getOperationDetail(idRequestVo.getId());
        
        if (result == null) {
            log.warn("SaaS操作记录不存在, ID: {}", idRequestVo.getId());
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_RESULT_IS_NULL);
        }
        
        SaaSFileOperationResponseVo vo = SaaSFileOperationResponseVo.build().clone(result);
        log.info("SaaS操作记录详情查询成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 分页获取解释记录列表
     */
    @ApiDoc(
        value = "分页获取解释记录列表",
        description = "根据操作记录ID分页获取解释记录",
        request = HttpRequestByIdVo.class,
        response = SaaSFileInterpretResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.SAAS_INTERPRET_PAGE_LIST)
    public PageResult<SaaSFileInterpretResponseVo> interpretPageList(
            @Validated  HttpRequestByIdVo idRequestVo,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        
        log.info("SaaS解释记录分页查询, OperationId: {}", idRequestVo.getId());
        
        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("SaaS解释记录分页查询参数错误, ID: {}", idRequestVo.getId());
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR);
        }
        
        Pager pager = new Pager(pageNo, pageSize);
        PageResult<SaaSFileInterpretResponseDto> result = fileOperationService.queryInterpretPage(idRequestVo.getId(), pager);
        
        if (ObjectTrue.isEmpty(result) || ObjectTrue.isEmpty(result.getList())) {
            log.info("SaaS解释记录分页查询完成, 无数据");
            return new PageResult<>();
        }
        
        PageResult<SaaSFileInterpretResponseVo> voResult = new PageResult<>();
        voResult.setTotal(result.getTotal());
        voResult.setList(result.getList().stream()
            .map(dto -> SaaSFileInterpretResponseVo.build().clone(dto))
            .toList());
        
        log.info("SaaS解释记录分页查询完成, 总数: {}", result.getTotal());
        return voResult;
    }

    /**
     * 获取解释记录详情
     */
    @ApiDoc(
        value = "获取解释记录详情",
        description = "根据ID获取解释记录详情",
        request = HttpRequestByIdVo.class,
        response = SaaSFileInterpretResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.SAAS_INTERPRET_INFO)
    public SaaSFileInterpretResponseVo interpretDetail( @Validated  HttpRequestByIdVo idRequestVo) {
        
        log.info("SaaS解释记录详情查询, ID: {}", idRequestVo.getId());
        
        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("SaaS解释记录详情查询参数错误, ID: {}", idRequestVo.getId());
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR);
        }
        
        SaaSFileInterpretResponseDto result = fileOperationService.getInterpretDetail(idRequestVo.getId());
        
        if (result == null) {
            log.warn("SaaS解释记录不存在, ID: {}", idRequestVo.getId());
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_RESULT_IS_NULL);
        }
        
        SaaSFileInterpretResponseVo vo = SaaSFileInterpretResponseVo.build().clone(result);
        log.info("SaaS解释记录详情查询成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 分批查询待处理的解释记录
     * 业务方调用此接口获取待处理的数据
     */
    @ApiDoc(
        value = "分批查询待处理数据",
        description = "分批查询指定操作记录下待处理的解释记录",
        request = HttpRequestByIdVo.class,
        response = SaaSFileInterpretResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.SAAS_INTERPRET_PENDING)
    public PageResult<SaaSFileInterpretResponseVo> queryPendingInterpretRecords(
            @Validated  HttpRequestByIdVo idRequestVo,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "50") int pageSize) {
        
        log.info("SaaS待处理解释记录查询, OperationId: {}, Status: {}", idRequestVo.getId(), status);
        
        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("SaaS待处理解释记录查询参数错误, ID: {}", idRequestVo.getId());
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR);
        }
        
        Pager pager = new Pager(pageNo, pageSize);
        PageResult<SaaSFileInterpretResponseDto> result = fileOperationService.queryPendingInterpretRecords(
            idRequestVo.getId(), status, pager);
        
        if (ObjectTrue.isEmpty(result) || ObjectTrue.isEmpty(result.getList())) {
            log.info("SaaS待处理解释记录查询完成, 无数据");
            return new PageResult<>();
        }
        
        PageResult<SaaSFileInterpretResponseVo> voResult = new PageResult<>();
        voResult.setTotal(result.getTotal());
        voResult.setList(result.getList().stream()
            .map(dto -> SaaSFileInterpretResponseVo.build().clone(dto))
            .toList());
        
        log.info("SaaS待处理解释记录查询完成, 总数: {}", result.getTotal());
        return voResult;
    }

    /**
     * 业务回调接口
     * 通知数据已解释完成
     */
    @ApiDoc(
        value = "业务回调接口",
        description = "业务方回调通知数据已解释完成",
        request = SaaSFileCallbackRequestVo.class,
        response = Boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.SAAS_CALLBACK)
    public boolean callback( @Validated@RequestBody SaaSFileCallbackRequestVo callbackRequest) {
        
        log.info("SaaS业务回调开始, InterpretId: {}, Status: {}", 
            callbackRequest.getInterpretRecordId(), callbackRequest.getBusinessProcessStatus());
        
        if (callbackRequest.getInterpretRecordId() <= 0) {
            log.warn("SaaS业务回调参数错误, InterpretId: {}", callbackRequest.getInterpretRecordId());
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR);
        }
        
        if (StringUtils.isBlank(callbackRequest.getBusinessUniqueCode())) {
            log.warn("SaaS业务回调参数错误, BusinessUniqueCode为空");
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR);
        }
        
        boolean result = fileOperationService.handleBusinessCallback(callbackRequest);
        
        log.info("SaaS业务回调完成, 结果: {}", result);
        return result;
    }

    /**
     * 回写处理结果
     * 记录上传文件的数据异常，用于提醒业务更正上传文件
     */
    @ApiDoc(
        value = "回写处理结果",
        description = "回写业务处理结果和异常信息",
        request = SaaSFileInterpretRequestDto.class,
        response = Boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.SAAS_CALLBACK_RESULT)
    public boolean writeBackResult( @Validated@RequestBody SaaSFileInterpretRequestDto requestDto) {
        
        log.info("SaaS回写处理结果开始, InterpretId: {}", requestDto.getId());
        
        if (requestDto.getId() <= 0) {
            log.warn("SaaS回写处理结果参数错误, InterpretId: {}", requestDto.getId());
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR);
        }
        
        boolean result = fileOperationService.updateInterpretRecord(requestDto);
        
        log.info("SaaS回写处理结果完成, 结果: {}", result);
        return result;
    }

    /**
     * 删除操作记录
     */
    @ApiDoc(
        value = "删除操作记录",
        description = "根据ID删除操作记录",
        request = HttpRequestByIdVo.class,
        response = Boolean.class,
        method = RequestMethodEnum.DELETE
    )
    @PostMapping(value = UrlCommand.SAAS_OPERATION_DELETE)
    public boolean deleteOperation( @Validated  HttpRequestByIdVo idRequestVo) {
        
        log.info("SaaS操作记录删除开始, ID: {}", idRequestVo.getId());
        
        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("SaaS操作记录删除参数错误, ID: {}", idRequestVo.getId());
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR);
        }
        
        boolean result = fileOperationService.deleteOperationRecord(idRequestVo.getId());
        
        log.info("SaaS操作记录删除完成, 结果: {}", result);
        return result;
    }

    /**
     * 批量删除操作记录
     */
    @ApiDoc(
        value = "批量删除操作记录",
        description = "根据ID列表批量删除操作记录",
        request = HttpRequestByIdListVo.class,
        response = Integer.class,
        method = RequestMethodEnum.DELETE
    )
    @PostMapping(value = "/saas/operation/batchDelete")
    public int batchDeleteOperation( @Validated  HttpRequestByIdListVo idRequestVo) {
        
        log.info("SaaS操作记录批量删除开始, ID数量: {}", idRequestVo.getIdList().size());
        
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("SaaS操作记录批量删除参数错误, ID列表为空");
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR);
        }
        
        int count = 0;
        for (Long id : idRequestVo.getIdList()) {
            if (fileOperationService.deleteOperationRecord(id)) {
                count++;
            }
        }
        
        log.info("SaaS操作记录批量删除完成, 删除数量: {}", count);
        return count;
    }

    /**
     * 删除解释记录
     */
    @ApiDoc(
        value = "删除解释记录",
        description = "根据ID删除解释记录",
        request = HttpRequestByIdVo.class,
        response = Boolean.class,
        method = RequestMethodEnum.DELETE
    )
    @PostMapping(value = UrlCommand.SAAS_INTERPRET_DELETE)
    public boolean deleteInterpret( @Validated  HttpRequestByIdVo idRequestVo) {
        
        log.info("SaaS解释记录删除开始, ID: {}", idRequestVo.getId());
        
        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("SaaS解释记录删除参数错误, ID: {}", idRequestVo.getId());
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR);
        }
        
        boolean result = fileOperationService.deleteInterpretRecord(idRequestVo.getId());
        
        log.info("SaaS解释记录删除完成, 结果: {}", result);
        return result;
    }

    // ==================== 私有方法 ====================

    private SaaSFileOperationRequestDto convertToRequestDto(SaaSFileOperationQueryVo vo) {
        SaaSFileOperationRequestDto dto = new SaaSFileOperationRequestDto();
        dto.setAppId(vo.getAppId());
        dto.setClientId(vo.getClientId());
        dto.setFileProductName(vo.getFileProductName());
        dto.setFileBusinessName(vo.getFileBusinessName());
        dto.setStatus(vo.getStatus());
        dto.setFileSourceName(vo.getFileSourceName());
        dto.setCreateDateStart(vo.getCreateDateStart());
        dto.setCreateDateEnd(vo.getCreateDateEnd());
        return dto;
    }
}
