package com.suven.framework.upload.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.mybatis.AbstractMyBatisRepository;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.upload.entity.FileInterpretRecord;
import com.suven.framework.upload.mapper.FileInterpretRecordMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * SaaS文件解释记录Repository
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Repository("fileInterpretRecordRepository")
public class FileInterpretRecordRepository extends AbstractMyBatisRepository<FileInterpretRecordMapper, FileInterpretRecord> {

    /**
     * 根据文件上传ID查询解释记录列表
     * 
     * @param fileUploadId 文件上传ID
     * @return 解释记录列表
     */
    public List<FileInterpretRecord> getByFileUploadId(long fileUploadId) {
        if (fileUploadId <= 0) {
            return new ArrayList<>();
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_upload_id", fileUploadId);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        return this.list(queryWrapper);
    }

    /**
     * 分页查询解释记录
     * 
     * @param fileUploadId 文件上传ID
     * @param status 状态
     * @param pager 分页参数
     * @return 分页结果
     */
    public List<FileInterpretRecord> getByFileUploadIdAndStatus(long fileUploadId, String status, Pager pager) {
        if (fileUploadId <= 0) {
            return new ArrayList<>();
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_upload_id", fileUploadId);
        if (ObjectTrue.isNotEmpty(status)) {
            queryWrapper.eq("interpret_status", status);
        }
        queryWrapper.eq("deleted", 0);
        queryWrapper.eq("business_process_status", "PENDING");
        queryWrapper.orderByDesc("id");
        
        Page<FileInterpretRecord> page = new Page<>(pager.getPageNo(), pager.getPageSize());
        page.setSearchCount(pager.isSearchCount());
        IPage<FileInterpretRecord> pageResult = this.page(page, queryWrapper);
        pager.setTotal(pageResult.getTotal());
        return pageResult.getRecords();
    }

    /**
     * 根据业务唯一码查询解释记录
     * 
     * @param businessUniqueCode 业务唯一码
     * @return 解释记录列表
     */
    public List<FileInterpretRecord> getByBusinessUniqueCode(String businessUniqueCode) {
        if (ObjectTrue.isEmpty(businessUniqueCode)) {
            return new ArrayList<>();
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_unique_code", businessUniqueCode);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        return this.list(queryWrapper);
    }

    /**
     * 根据解释标识查询解释记录
     * 
     * @param interpretKey 解释标识
     * @return 解释记录
     */
    public FileInterpretRecord getByInterpretKey(String interpretKey) {
        if (ObjectTrue.isEmpty(interpretKey)) {
            return null;
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("interpret_key", interpretKey);
        queryWrapper.eq("deleted", 0);
        return this.getOne(queryWrapper);
    }

    /**
     * 根据业务唯一码和解释标识查询解释记录
     * 
     * @param businessUniqueCode 业务唯一码
     * @param interpretKey 解释标识
     * @return 解释记录
     */
    public FileInterpretRecord getByBusinessUniqueCodeAndInterpretKey(String businessUniqueCode, String interpretKey) {
        if (ObjectTrue.isEmpty(businessUniqueCode) || ObjectTrue.isEmpty(interpretKey)) {
            return null;
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_unique_code", businessUniqueCode);
        queryWrapper.eq("interpret_key", interpretKey);
        queryWrapper.eq("deleted", 0);
        return this.getOne(queryWrapper);
    }

    /**
     * 更新解释记录
     * 
     * @param interpretRecord 解释记录
     * @return 是否成功
     */
    public boolean updateInterpretRecord(FileInterpretRecord interpretRecord) {
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
    public boolean updateBatchInterpretRecords(List<FileInterpretRecord> interpretRecords) {
        if (ObjectTrue.isEmpty(interpretRecords)) {
            return false;
        }
        return this.updateBatchById(interpretRecords);
    }
}
