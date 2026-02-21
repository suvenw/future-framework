package com.suven.framework.upload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.db.ext.DS;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.upload.entity.CompanyBusinessFunction;
import com.suven.framework.upload.entity.DataSourceModuleName;
import com.suven.framework.upload.repository.CompanyBusinessFunctionRepository;
import com.suven.framework.upload.service.CompanyBusinessFunctionService;
import com.suven.framework.upload.vo.request.CompanyBusinessFunctionRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * SaaS公司业务功能信息服务实现
 *
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Slf4j
@Service
@DS(DataSourceModuleName.module_name_file)
public class CompanyBusinessFunctionServiceImpl implements CompanyBusinessFunctionService {

    @Autowired
    private CompanyBusinessFunctionRepository businessFunctionRepository;

    @Override
    public CompanyBusinessFunction getByBusinessUniqueCode(String businessUniqueCode) {
        log.debug("根据业务唯一码查询: businessUniqueCode={}", businessUniqueCode);
        
        if (ObjectTrue.isEmpty(businessUniqueCode)) {
            return null;
        }

        LambdaQueryWrapper<CompanyBusinessFunction> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CompanyBusinessFunction::getBusinessUniqueCode, businessUniqueCode);
        queryWrapper.eq(CompanyBusinessFunction::getDeleted, 0);
        
        return businessFunctionRepository.getOne(queryWrapper);
    }

    @Override
    public CompanyBusinessFunction getById(long id) {
        log.debug("根据ID查询: id={}", id);
        return businessFunctionRepository.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompanyBusinessFunction createBusinessFunction(CompanyBusinessFunctionRequestVo requestVo) {
        log.info("创建业务功能: companyId={}, businessCode={}", 
                requestVo.getCompanyId(), requestVo.getBusinessCode());

        CompanyBusinessFunction function = new CompanyBusinessFunction();
        function.setCompanyId(requestVo.getCompanyId());
        function.setCompanyName(requestVo.getCompanyName());
        function.setPlatformType(requestVo.getPlatformType());
        function.setBusinessCode(requestVo.getBusinessCode());
        function.setBusinessName(requestVo.getBusinessName());
        function.setFunctionType(requestVo.getFunctionType());
        function.setCallbackUrl(requestVo.getCallbackUrl());
        function.setQueryUrl(requestVo.getQueryUrl());
        function.setAccessMethod(requestVo.getAccessMethod());
        function.setRemark(requestVo.getRemark());
        
        // 生成业务唯一码
        String uniqueCode = generateBusinessUniqueCode(requestVo.getCompanyId(), requestVo.getBusinessCode());
        function.setBusinessUniqueCode(uniqueCode);
        
        // 初始状态为待激活
        function.setStatus("PENDING");
        function.setCreateDate(LocalDateTime.now());
        function.setModifyDate(LocalDateTime.now());
        function.setDeleted(0);
        
        businessFunctionRepository.save(function);
        
        log.info("创建业务功能成功: id={}, businessUniqueCode={}", function.getId(), uniqueCode);
        return function;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompanyBusinessFunction updateBusinessFunction(CompanyBusinessFunctionRequestVo requestVo) {
        log.info("更新业务功能: id={}", requestVo.getId());

        CompanyBusinessFunction function = businessFunctionRepository.getById(requestVo.getId());
        if (function == null) {
            throw new RuntimeException("业务功能不存在: " + requestVo.getId());
        }

        // 更新可修改字段
        if (ObjectTrue.isNotEmpty(requestVo.getCompanyName())) {
            function.setCompanyName(requestVo.getCompanyName());
        }
        if (ObjectTrue.isNotEmpty(requestVo.getPlatformType())) {
            function.setPlatformType(requestVo.getPlatformType());
        }
        if (ObjectTrue.isNotEmpty(requestVo.getBusinessName())) {
            function.setBusinessName(requestVo.getBusinessName());
        }
        if (ObjectTrue.isNotEmpty(requestVo.getFunctionType())) {
            function.setFunctionType(requestVo.getFunctionType());
        }
        if (ObjectTrue.isNotEmpty(requestVo.getCallbackUrl())) {
            function.setCallbackUrl(requestVo.getCallbackUrl());
        }
        if (ObjectTrue.isNotEmpty(requestVo.getQueryUrl())) {
            function.setQueryUrl(requestVo.getQueryUrl());
        }
        if (ObjectTrue.isNotEmpty(requestVo.getAccessMethod())) {
            function.setAccessMethod(requestVo.getAccessMethod());
        }
        if (ObjectTrue.isNotEmpty(requestVo.getRemark())) {
            function.setRemark(requestVo.getRemark());
        }
        
        function.setModifyDate(LocalDateTime.now());
        businessFunctionRepository.updateById(function);
        
        log.info("更新业务功能成功: id={}", function.getId());
        return function;
    }

    @Override
    public PageResult<CompanyBusinessFunction> pageQuery(Pager<CompanyBusinessFunctionRequestVo> pager) {
        log.debug("分页查询业务功能: pageNo={}, pageSize={}", pager.getPageNo(), pager.getPageSize());

        CompanyBusinessFunctionRequestVo param = pager.getParamObject();
        LambdaQueryWrapper<CompanyBusinessFunction> queryWrapper = new LambdaQueryWrapper<>();
        
        // 构建查询条件
        if (param != null) {
            if (ObjectTrue.isNotEmpty(param.getCompanyId())) {
                queryWrapper.eq(CompanyBusinessFunction::getCompanyId, param.getCompanyId());
            }
            if (ObjectTrue.isNotEmpty(param.getPlatformType())) {
                queryWrapper.eq(CompanyBusinessFunction::getPlatformType, param.getPlatformType());
            }
            if (ObjectTrue.isNotEmpty(param.getBusinessCode())) {
                queryWrapper.like(CompanyBusinessFunction::getBusinessCode, param.getBusinessCode());
            }
            if (ObjectTrue.isNotEmpty(param.getBusinessName())) {
                queryWrapper.like(CompanyBusinessFunction::getBusinessName, param.getBusinessName());
            }
            if (ObjectTrue.isNotEmpty(param.getFunctionType())) {
                queryWrapper.eq(CompanyBusinessFunction::getFunctionType, param.getFunctionType());
            }
            if (ObjectTrue.isNotEmpty(param.getStatus())) {
                queryWrapper.eq(CompanyBusinessFunction::getStatus, param.getStatus());
            }
            if (ObjectTrue.isNotEmpty(param.getBusinessUniqueCode())) {
                queryWrapper.eq(CompanyBusinessFunction::getBusinessUniqueCode, param.getBusinessUniqueCode());
            }
            if (param.getCreateDateStart() != null) {
                queryWrapper.ge(CompanyBusinessFunction::getCreateDate, param.getCreateDateStart());
            }
            if (param.getCreateDateEnd() != null) {
                queryWrapper.le(CompanyBusinessFunction::getCreateDate, param.getCreateDateEnd());
            }
        }
        
        queryWrapper.eq(CompanyBusinessFunction::getDeleted, 0);
        queryWrapper.orderByDesc(CompanyBusinessFunction::getId);
        
        return businessFunctionRepository.getListByPage(
                pager.clonePager(CompanyBusinessFunction.class), queryWrapper);
    }

    @Override
    public List<CompanyBusinessFunction> getByCompanyId(String companyId) {
        log.debug("根据公司ID查询: companyId={}", companyId);

        LambdaQueryWrapper<CompanyBusinessFunction> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CompanyBusinessFunction::getCompanyId, companyId);
        queryWrapper.eq(CompanyBusinessFunction::getDeleted, 0);
        queryWrapper.orderByDesc(CompanyBusinessFunction::getId);
        
        return businessFunctionRepository.list(queryWrapper);
    }

    @Override
    public boolean existsByBusinessCode(String companyId, String businessCode) {
        LambdaQueryWrapper<CompanyBusinessFunction> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CompanyBusinessFunction::getCompanyId, companyId);
        queryWrapper.eq(CompanyBusinessFunction::getBusinessCode, businessCode);
        queryWrapper.eq(CompanyBusinessFunction::getDeleted, 0);
        
        return businessFunctionRepository.count(queryWrapper) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean activateBusinessFunction(long id) {
        log.info("激活业务功能: id={}", id);

        CompanyBusinessFunction function = businessFunctionRepository.getById(id);
        if (function == null) {
            log.warn("业务功能不存在: id={}", id);
            return false;
        }

        function.setStatus("ACTIVE");
        function.setModifyDate(LocalDateTime.now());
        
        return businessFunctionRepository.updateById(function);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deactivateBusinessFunction(long id) {
        log.info("停用业务功能: id={}", id);

        CompanyBusinessFunction function = businessFunctionRepository.getById(id);
        if (function == null) {
            log.warn("业务功能不存在: id={}", id);
            return false;
        }

        function.setStatus("INACTIVE");
        function.setModifyDate(LocalDateTime.now());
        
        return businessFunctionRepository.updateById(function);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBusinessFunction(long id) {
        log.info("删除业务功能: id={}", id);

        CompanyBusinessFunction function = businessFunctionRepository.getById(id);
        if (function == null) {
            log.warn("业务功能不存在: id={}", id);
            return false;
        }

        function.setDeleted(1);
        function.setModifyDate(LocalDateTime.now());
        
        return businessFunctionRepository.updateById(function);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDeleteBusinessFunction(List<Long> idList) {
        log.info("批量删除业务功能: count={}", idList.size());

        int count = 0;
        for (Long id : idList) {
            if (deleteBusinessFunction(id)) {
                count++;
            }
        }
        
        return count;
    }

    @Override
    public String generateBusinessUniqueCode(String companyId, String businessCode) {
        // 生成规则: BIZ_{companyId}_{businessCode}_{timestamp}_{random}
        String timestamp = String.valueOf(System.currentTimeMillis());
        String random = UUID.randomUUID().toString().substring(0, 8);
        return String.format("BIZ_%s_%s_%s_%s", companyId, businessCode, timestamp, random);
    }
}
