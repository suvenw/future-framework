package com.suven.framework.upload.controller;

import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.api.RequestMethodEnum;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.upload.dto.request.FileFieldRequestDto;
import com.suven.framework.upload.dto.response.FileFieldResponseDto;
import com.suven.framework.upload.entity.CompanyBusinessFunction;
import com.suven.framework.upload.entity.FileFieldMapping;
import com.suven.framework.upload.service.CompanyBusinessFunctionService;
import com.suven.framework.upload.service.FileFieldMappingService;
import com.suven.framework.upload.vo.request.CompanyBusinessFunctionRequestVo;
import com.suven.framework.upload.vo.response.CompanyBusinessFunctionResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 公司业务功能申请管理控制器
 * 
 * 功能：
 * 1. 公司企业申请业务功能配置
 * 2. 业务字段和映射关系填写
 * 3. 业务功能状态管理
 * 
 * 业务流程：
 * 1. 公司申请业务功能（填写基本信息：业务编码、业务名称、功能类型等）
 * 2. 配置字段映射（定义文件的英文字段、中文字段、数据类型、验证规则等）
 * 3. 激活业务功能（生成业务唯一码，供后续上传使用）
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@ApiDoc(
    group = DocumentConst.Sys.SYS_DOC_GROUP,
    groupDesc = DocumentConst.Sys.SYS_DOC_DES,
    module = "公司业务功能申请管理模块",
    isApp = false
)
@RestController
@Slf4j
@Validated
public class CompanyBusinessFunctionWebController {

    @Autowired
    private CompanyBusinessFunctionService businessFunctionService;

    @Autowired
    private FileFieldMappingService fileFieldMappingService;

    /**
     * URL 命令常量接口
     */
    public interface UrlCommand {
        /** 申请业务功能 */
        String APPLY_BUSINESS_FUNCTION = "/saas/business/apply";
        /** 更新业务功能 */
        String UPDATE_BUSINESS_FUNCTION = "/saas/business/update";
        /** 获取业务功能详情 */
        String GET_BUSINESS_FUNCTION = "/saas/business/info";
        /** 分页查询业务功能列表 */
        String PAGE_LIST_BUSINESS_FUNCTION = "/saas/business/pageList";
        /** 根据业务唯一码查询 */
        String GET_BY_UNIQUE_CODE = "/saas/business/getByCode";
        /** 激活业务功能 */
        String ACTIVATE_BUSINESS_FUNCTION = "/saas/business/activate";
        /** 停用业务功能 */
        String DEACTIVATE_BUSINESS_FUNCTION = "/saas/business/deactivate";
        /** 删除业务功能 */
        String DELETE_BUSINESS_FUNCTION = "/saas/business/delete";
        /** 批量删除业务功能 */
        String BATCH_DELETE_BUSINESS_FUNCTION = "/saas/business/batchDelete";
        
        /** 保存字段映射 */
        String SAVE_FIELD_MAPPINGS = "/saas/business/fieldMappings/save";
        /** 更新字段映射 */
        String UPDATE_FIELD_MAPPING = "/saas/business/fieldMapping/update";
        /** 删除字段映射 */
        String DELETE_FIELD_MAPPING = "/saas/business/fieldMapping/delete";
        /** 获取字段映射列表 */
        String GET_FIELD_MAPPINGS = "/saas/business/fieldMappings/list";
        /** 批量保存字段映射 */
        String BATCH_SAVE_FIELD_MAPPINGS = "/saas/business/fieldMappings/batchSave";
    }

    // ==================== 业务功能申请管理 ====================

    /**
     * 申请业务功能
     * 
     * 公司企业填写业务功能基本信息，申请文件上传/下载能力
     */
    @ApiDoc(
        value = "申请业务功能",
        description = "公司企业申请业务功能配置，包括业务编码、业务名称、功能类型、回调地址等",
        request = CompanyBusinessFunctionRequestVo.class,
        response = CompanyBusinessFunctionResponseVo.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.APPLY_BUSINESS_FUNCTION)
    public CompanyBusinessFunctionResponseVo applyBusinessFunction(
            @Validated @RequestBody CompanyBusinessFunctionRequestVo requestVo) {
        
        log.info("申请业务功能: companyId={}, businessCode={}, businessName={}", 
                requestVo.getCompanyId(), requestVo.getBusinessCode(), requestVo.getBusinessName());

        // 参数校验
        validateApplyRequest(requestVo);

        // 检查业务编码是否已存在
        if (businessFunctionService.existsByBusinessCode(requestVo.getCompanyId(), requestVo.getBusinessCode())) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "业务编码已存在");
        }

        // 创建业务功能配置
        CompanyBusinessFunction function = businessFunctionService.createBusinessFunction(requestVo);
        
        CompanyBusinessFunctionResponseVo responseVo = convertToResponseVo(function);
        log.info("申请业务功能成功: id={}, businessUniqueCode={}", function.getId(), function.getBusinessUniqueCode());
        
        return responseVo;
    }

    /**
     * 更新业务功能
     */
    @ApiDoc(
        value = "更新业务功能",
        description = "更新业务功能配置信息",
        request = CompanyBusinessFunctionRequestVo.class,
        response = CompanyBusinessFunctionResponseVo.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.UPDATE_BUSINESS_FUNCTION)
    public CompanyBusinessFunctionResponseVo updateBusinessFunction(
            @Validated @RequestBody CompanyBusinessFunctionRequestVo requestVo) {
        
        log.info("更新业务功能: id={}", requestVo.getId());

        if (requestVo.getId() == null || requestVo.getId() <= 0) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "ID不能为空");
        }

        CompanyBusinessFunction function = businessFunctionService.updateBusinessFunction(requestVo);
        
        CompanyBusinessFunctionResponseVo responseVo = convertToResponseVo(function);
        log.info("更新业务功能成功: id={}", function.getId());
        
        return responseVo;
    }

    /**
     * 获取业务功能详情
     */
    @ApiDoc(
        value = "获取业务功能详情",
        description = "根据ID获取业务功能配置详情",
        request = HttpRequestByIdVo.class,
        response = CompanyBusinessFunctionResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.GET_BUSINESS_FUNCTION)
    public CompanyBusinessFunctionResponseVo getBusinessFunction(@Validated HttpRequestByIdVo idVo) {
        
        log.info("获取业务功能详情: id={}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "ID不能为空");
        }

        CompanyBusinessFunction function = businessFunctionService.getById(idVo.getId());
        if (function == null) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_RESULT_IS_NULL, "业务功能不存在");
        }

        CompanyBusinessFunctionResponseVo responseVo = convertToResponseVo(function);
        
        // 加载字段映射列表
        List<FileFieldMapping> fieldMappings = fileFieldMappingService.getByBusinessFunctionId(function.getId());
        responseVo.setFieldMappings(convertToFieldResponseList(fieldMappings));
        
        return responseVo;
    }

    /**
     * 分页查询业务功能列表
     */
    @ApiDoc(
        value = "分页查询业务功能列表",
        description = "根据条件分页查询公司业务功能配置列表",
        request = CompanyBusinessFunctionRequestVo.class,
        response = CompanyBusinessFunctionResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.PAGE_LIST_BUSINESS_FUNCTION)
    public PageResult<CompanyBusinessFunctionResponseVo> pageList(
            @Validated CompanyBusinessFunctionRequestVo requestVo,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        
        log.info("分页查询业务功能列表: companyId={}, pageNo={}, pageSize={}", 
                requestVo.getCompanyId(), pageNo, pageSize);

        Pager<CompanyBusinessFunctionRequestVo> pager = new Pager<>(pageNo, pageSize);
        pager.toParamObject(requestVo);

        PageResult<CompanyBusinessFunction> pageResult = businessFunctionService.pageQuery(pager);
        
        PageResult<CompanyBusinessFunctionResponseVo> result = new PageResult<>();
        result.setTotal(pageResult.getTotal());
        result.setList(pageResult.getList().stream()
                .map(this::convertToResponseVo)
                .collect(Collectors.toList()));
        
        return result;
    }

    /**
     * 根据业务唯一码查询业务功能
     */
    @ApiDoc(
        value = "根据业务唯一码查询",
        description = "根据业务唯一码获取业务功能配置",
        request = String.class,
        response = CompanyBusinessFunctionResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.GET_BY_UNIQUE_CODE)
    public CompanyBusinessFunctionResponseVo getByUniqueCode(
            @RequestParam String businessUniqueCode) {
        
        log.info("根据业务唯一码查询: businessUniqueCode={}", businessUniqueCode);

        if (StringUtils.isBlank(businessUniqueCode)) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "业务唯一码不能为空");
        }

        CompanyBusinessFunction function = businessFunctionService.getByBusinessUniqueCode(businessUniqueCode);
        if (function == null) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_RESULT_IS_NULL, "业务功能不存在");
        }

        CompanyBusinessFunctionResponseVo responseVo = convertToResponseVo(function);
        
        // 加载字段映射列表
        List<FileFieldMapping> fieldMappings = fileFieldMappingService.getByBusinessFunctionId(function.getId());
        responseVo.setFieldMappings(convertToFieldResponseList(fieldMappings));
        
        return responseVo;
    }

    /**
     * 激活业务功能
     */
    @ApiDoc(
        value = "激活业务功能",
        description = "激活业务功能，使其可以被使用",
        request = HttpRequestByIdVo.class,
        response = Boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.ACTIVATE_BUSINESS_FUNCTION)
    public boolean activateBusinessFunction(@Validated @RequestBody HttpRequestByIdVo idVo) {
        
        log.info("激活业务功能: id={}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "ID不能为空");
        }

        boolean result = businessFunctionService.activateBusinessFunction(idVo.getId());
        log.info("激活业务功能结果: id={}, result={}", idVo.getId(), result);
        
        return result;
    }

    /**
     * 停用业务功能
     */
    @ApiDoc(
        value = "停用业务功能",
        description = "停用业务功能，暂停使用",
        request = HttpRequestByIdVo.class,
        response = Boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.DEACTIVATE_BUSINESS_FUNCTION)
    public boolean deactivateBusinessFunction(@Validated @RequestBody HttpRequestByIdVo idVo) {
        
        log.info("停用业务功能: id={}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "ID不能为空");
        }

        boolean result = businessFunctionService.deactivateBusinessFunction(idVo.getId());
        log.info("停用业务功能结果: id={}, result={}", idVo.getId(), result);
        
        return result;
    }

    /**
     * 删除业务功能
     */
    @ApiDoc(
        value = "删除业务功能",
        description = "删除业务功能配置（软删除）",
        request = HttpRequestByIdVo.class,
        response = Boolean.class,
        method = RequestMethodEnum.DELETE
    )
    @PostMapping(value = UrlCommand.DELETE_BUSINESS_FUNCTION)
    public boolean deleteBusinessFunction(@Validated @RequestBody HttpRequestByIdVo idVo) {
        
        log.info("删除业务功能: id={}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "ID不能为空");
        }

        boolean result = businessFunctionService.deleteBusinessFunction(idVo.getId());
        log.info("删除业务功能结果: id={}, result={}", idVo.getId(), result);
        
        return result;
    }

    /**
     * 批量删除业务功能
     */
    @ApiDoc(
        value = "批量删除业务功能",
        description = "批量删除业务功能配置（软删除）",
        request = HttpRequestByIdListVo.class,
        response = Integer.class,
        method = RequestMethodEnum.DELETE
    )
    @PostMapping(value = UrlCommand.BATCH_DELETE_BUSINESS_FUNCTION)
    public int batchDeleteBusinessFunction(@Validated @RequestBody HttpRequestByIdListVo idListVo) {
        
        log.info("批量删除业务功能: idList={}", idListVo.getIdList());

        if (ObjectTrue.isEmpty(idListVo.getIdList())) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "ID列表不能为空");
        }

        int count = businessFunctionService.batchDeleteBusinessFunction(idListVo.getIdList());
        log.info("批量删除业务功能完成: count={}", count);
        
        return count;
    }

    // ==================== 字段映射管理 ====================

    /**
     * 保存字段映射
     */
    @ApiDoc(
        value = "保存字段映射",
        description = "为业务功能添加字段映射配置",
        request = FileFieldRequestDto.class,
        response = FileFieldResponseDto.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.SAVE_FIELD_MAPPINGS)
    public FileFieldResponseVo saveFieldMapping(@Validated @RequestBody FileFieldRequestDto requestDto) {
        
        log.info("保存字段映射: businessFunctionId={}, fieldEnglishName={}", 
                requestDto.getBusinessFunctionId(), requestDto.getFieldEnglishName());

        if (requestDto.getBusinessFunctionId() <= 0) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "业务功能ID不能为空");
        }

        FileFieldMapping mapping = fileFieldMappingService.saveFieldMapping(requestDto);
        
        FileFieldResponseVo responseVo = convertToFieldResponseVo(mapping);
        log.info("保存字段映射成功: id={}", mapping.getId());
        
        return responseVo;
    }

    /**
     * 更新字段映射
     */
    @ApiDoc(
        value = "更新字段映射",
        description = "更新字段映射配置",
        request = FileFieldRequestDto.class,
        response = FileFieldResponseDto.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.UPDATE_FIELD_MAPPING)
    public FileFieldResponseVo updateFieldMapping(@Validated @RequestBody FileFieldRequestDto requestDto) {
        
        log.info("更新字段映射: id={}", requestDto.getId());

        if (requestDto.getId() == null || requestDto.getId() <= 0) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "ID不能为空");
        }

        FileFieldMapping mapping = fileFieldMappingService.updateFieldMapping(requestDto);
        
        FileFieldResponseVo responseVo = convertToFieldResponseVo(mapping);
        log.info("更新字段映射成功: id={}", mapping.getId());
        
        return responseVo;
    }

    /**
     * 删除字段映射
     */
    @ApiDoc(
        value = "删除字段映射",
        description = "删除字段映射配置",
        request = HttpRequestByIdVo.class,
        response = Boolean.class,
        method = RequestMethodEnum.DELETE
    )
    @PostMapping(value = UrlCommand.DELETE_FIELD_MAPPING)
    public boolean deleteFieldMapping(@Validated @RequestBody HttpRequestByIdVo idVo) {
        
        log.info("删除字段映射: id={}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "ID不能为空");
        }

        boolean result = fileFieldMappingService.deleteFieldMapping(idVo.getId());
        log.info("删除字段映射结果: id={}, result={}", idVo.getId(), result);
        
        return result;
    }

    /**
     * 获取字段映射列表
     */
    @ApiDoc(
        value = "获取字段映射列表",
        description = "根据业务功能ID获取字段映射列表",
        request = Long.class,
        response = FileFieldResponseDto.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.GET_FIELD_MAPPINGS)
    public List<FileFieldResponseVo> getFieldMappings(
            @RequestParam long businessFunctionId) {
        
        log.info("获取字段映射列表: businessFunctionId={}", businessFunctionId);

        if (businessFunctionId <= 0) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "业务功能ID不能为空");
        }

        List<FileFieldMapping> mappings = fileFieldMappingService.getByBusinessFunctionId(businessFunctionId);
        
        return mappings.stream()
                .map(this::convertToFieldResponseVo)
                .collect(Collectors.toList());
    }

    /**
     * 批量保存字段映射
     */
    @ApiDoc(
        value = "批量保存字段映射",
        description = "批量保存字段映射配置（会先删除该业务功能下的所有字段映射，再保存新的）",
        request = FileFieldRequestDto.class,
        response = Boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.BATCH_SAVE_FIELD_MAPPINGS)
    public boolean batchSaveFieldMappings(
            @RequestParam long businessFunctionId,
            @Validated @RequestBody List<FileFieldRequestDto> requestDtoList) {
        
        log.info("批量保存字段映射: businessFunctionId={}, count={}", 
                businessFunctionId, requestDtoList.size());

        if (businessFunctionId <= 0) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "业务功能ID不能为空");
        }

        if (ObjectTrue.isEmpty(requestDtoList)) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "字段映射列表不能为空");
        }

        boolean result = fileFieldMappingService.batchSaveFieldMappings(businessFunctionId, requestDtoList);
        log.info("批量保存字段映射结果: result={}", result);
        
        return result;
    }

    // ==================== 私有方法 ====================

    private void validateApplyRequest(CompanyBusinessFunctionRequestVo requestVo) {
        if (StringUtils.isBlank(requestVo.getCompanyId())) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "公司ID不能为空");
        }
        if (StringUtils.isBlank(requestVo.getBusinessCode())) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "业务编码不能为空");
        }
        if (StringUtils.isBlank(requestVo.getBusinessName())) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "业务名称不能为空");
        }
        if (StringUtils.isBlank(requestVo.getFunctionType())) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "功能类型不能为空");
        }
        if (StringUtils.isBlank(requestVo.getPlatformType())) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, "平台类型不能为空");
        }
    }

    private CompanyBusinessFunctionResponseVo convertToResponseVo(CompanyBusinessFunction function) {
        if (function == null) {
            return null;
        }
        CompanyBusinessFunctionResponseVo vo = new CompanyBusinessFunctionResponseVo();
        vo.setId(function.getId());
        vo.setCompanyId(function.getCompanyId());
        vo.setCompanyName(function.getCompanyName());
        vo.setPlatformType(function.getPlatformType());
        vo.setBusinessCode(function.getBusinessCode());
        vo.setBusinessName(function.getBusinessName());
        vo.setFunctionType(function.getFunctionType());
        vo.setBusinessUniqueCode(function.getBusinessUniqueCode());
        vo.setCallbackUrl(function.getCallbackUrl());
        vo.setQueryUrl(function.getQueryUrl());
        vo.setAccessMethod(function.getAccessMethod());
        vo.setStatus(function.getStatus());
        vo.setRemark(function.getRemark());
        vo.setCreateDate(function.getCreateDate());
        vo.setModifyDate(function.getModifyDate());
        return vo;
    }

    private FileFieldResponseVo convertToFieldResponseVo(FileFieldMapping mapping) {
        if (mapping == null) {
            return null;
        }
        FileFieldResponseVo vo = new FileFieldResponseVo();
        vo.setId(mapping.getId());
        vo.setBusinessFunctionId(mapping.getBusinessFunctionId());
        vo.setFieldEnglishName(mapping.getFieldEnglishName());
        vo.setFieldChineseName(mapping.getFieldChineseName());
        vo.setSortOrder(mapping.getSortOrder());
        vo.setFieldType(mapping.getFieldType());
        vo.setFieldLength(mapping.getFieldLength());
        vo.setIsPrimaryKey(mapping.getIsPrimaryKey());
        vo.setIsRequired(mapping.getIsRequired());
        vo.setDefaultValue(mapping.getDefaultValue());
        vo.setFieldDescription(mapping.getFieldDescription());
        vo.setValidateRule(mapping.getValidateRule());
        vo.setTransformRule(mapping.getTransformRule());
        vo.setSampleValue(mapping.getSampleValue());
        vo.setStatus(mapping.getStatus());
        vo.setRemark(mapping.getRemark());
        vo.setCreateDate(mapping.getCreateDate());
        vo.setModifyDate(mapping.getModifyDate());
        return vo;
    }

    private List<FileFieldResponseVo> convertToFieldResponseList(List<FileFieldMapping> mappings) {
        if (ObjectTrue.isEmpty(mappings)) {
            return new ArrayList<>();
        }
        return mappings.stream()
                .map(this::convertToFieldResponseVo)
                .collect(Collectors.toList());
    }
}
