package com.suven.framework.upload.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.AssertEx;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.mybatis.AbstractMyBatisRepository;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.upload.entity.SaaSFileInterpretRecord;
import com.suven.framework.upload.mapper.SaaSFileInterpretRecordMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * SaaS文件解释记录Repository
 * 
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Repository("saaSFileInterpretRecordRepository")
public class SaaSFileInterpretRecordRepository extends AbstractMyBatisRepository<SaaSFileInterpretRecordMapper, SaaSFileInterpretRecord> {

    /**
     * 根据操作记录ID查询解释记录列表
     * 
     * @param operationRecordId 操作记录ID
     * @return 解释记录列表
     */
    public List<SaaSFileInterpretRecord> getByOperationRecordId(long operationRecordId) {
        if (operationRecordId <= 0) {
            return new ArrayList<>();
        }
        QueryWrapper<SaaSFileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("operation_record_id", operationRecordId);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        return this.list(queryWrapper);
    }

    /**
     * 分页查询解释记录
     * 
     * @param operationRecordId 操作记录ID
     * @param status 状态
     * @param pager 分页参数
     * @return 分页结果
     */
    public List<SaaSFileInterpretRecord> getByOperationRecordIdAndStatus(long operationRecordId, String status, Pager pager) {
        if (operationRecordId <= 0) {
            return new ArrayList<>();
        }
        QueryWrapper<SaaSFileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("operation_record_id", operationRecordId);
        if (ObjectTrue.isNotEmpty(status)) {
            queryWrapper.eq("interpret_status", status);
        }
        queryWrapper.eq("deleted", 0);
        queryWrapper.eq("business_process_status", "PENDING");
        queryWrapper.orderByDesc("id");
        
        Page<SaaSFileInterpretRecord> page = new Page<>(pager.getPageNo(), pager.getPageSize());
        page.setSearchCount(pager.isSearchCount());
        IPage<SaaSFileInterpretRecord> pageResult = this.page(page, queryWrapper);
        pager.setTotal(pageResult.getTotal());
        return pageResult.getRecords();
    }

    /**
     * 根据业务唯一码查询解释记录
     * 
     * @param businessUniqueCode 业务唯一码
     * @return 解释记录列表
     */
    public List<SaaSFileInterpretRecord> getByBusinessUniqueCode(String businessUniqueCode) {
        if (ObjectTrue.isEmpty(businessUniqueCode)) {
            return new ArrayList<>();
        }
        QueryWrapper<SaaSFileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_unique_code", businessUniqueCode);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        return this.list(queryWrapper);
    }

    /**
     * 更新解释记录
     * 
     * @param interpretRecord 解释记录
     * @return 是否成功
     */
    public boolean updateInterpretRecord(SaaSFileInterpretRecord interpretRecord) {
        if (interpretRecord == null || interpretRecord.getId() <= 0) {
            return false;
        }
        return this.updateById(interpretRecord);
    }

    /**
     * 批量更新解释记录
     * 
     * @param interpretRecords 解释记录列表
     * @return 是否成功
     */
    public boolean updateBatchInterpretRecords(List<SaaSFileInterpretRecord> interpretRecords) {
        if (ObjectTrue.isEmpty(interpretRecords)) {
            return false;
        }
        return this.updateBatchById(interpretRecords);
    }
}
