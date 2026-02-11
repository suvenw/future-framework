package com.suven.framework.upload.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.mybatis.AbstractMyBatisRepository;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.upload.entity.SaaSFileUpload;
import com.suven.framework.upload.mapper.SaaSFileUploadMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * SaaS文件上传Repository
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Repository("saaSFileUploadRepository")
public class SaaSFileUploadRepository extends AbstractMyBatisRepository<SaaSFileUploadMapper, SaaSFileUpload> {

    /**
     * 根据ID查询文件上传记录
     * 
     * @param id 记录ID
     * @return 文件上传记录
     */
    public SaaSFileUpload getById(long id) {
        if (id <= 0) {
            return null;
        }
        return this.baseMapper.selectById(id);
    }

    /**
     * 根据业务功能ID查询文件上传记录
     * 
     * @param businessFunctionId 业务功能ID
     * @return 文件上传记录列表
     */
    public List<SaaSFileUpload> getByBusinessFunctionId(long businessFunctionId) {
        if (businessFunctionId <= 0) {
            return new ArrayList<>();
        }
        QueryWrapper<SaaSFileUpload> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_function_id", businessFunctionId);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        return this.list(queryWrapper);
    }

    /**
     * 根据业务唯一码查询文件上传记录
     * 
     * @param businessUniqueCode 业务唯一码
     * @return 文件上传记录列表
     */
    public List<SaaSFileUpload> getByBusinessUniqueCode(String businessUniqueCode) {
        if (ObjectTrue.isEmpty(businessUniqueCode)) {
            return new ArrayList<>();
        }
        QueryWrapper<SaaSFileUpload> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_unique_code", businessUniqueCode);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        return this.list(queryWrapper);
    }

    /**
     * 根据解释标识查询文件上传记录
     * 
     * @param interpretFlag 是否需要解释
     * @param pager 分页参数
     * @return 文件上传记录列表
     */
    public List<SaaSFileUpload> getByInterpretFlag(int interpretFlag, Pager pager) {
        QueryWrapper<SaaSFileUpload> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("interpret_flag", interpretFlag);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        
        Page<SaaSFileUpload> page = new Page<>(pager.getPageNo(), pager.getPageSize());
        page.setSearchCount(pager.isSearchCount());
        IPage<SaaSFileUpload> pageResult = this.page(page, queryWrapper);
        pager.setTotal(pageResult.getTotal());
        return pageResult.getRecords();
    }

    /**
     * 根据解释状态查询文件上传记录
     * 
     * @param interpretStatus 解释状态
     * @param pager 分页参数
     * @return 文件上传记录列表
     */
    public List<SaaSFileUpload> getByInterpretStatus(String interpretStatus, Pager pager) {
        if (ObjectTrue.isEmpty(interpretStatus)) {
            return new ArrayList<>();
        }
        QueryWrapper<SaaSFileUpload> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("interpret_status", interpretStatus);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        
        Page<SaaSFileUpload> page = new Page<>(pager.getPageNo(), pager.getPageSize());
        page.setSearchCount(pager.isSearchCount());
        IPage<SaaSFileUpload> pageResult = this.page(page, queryWrapper);
        pager.setTotal(pageResult.getTotal());
        return pageResult.getRecords();
    }

    /**
     * 根据处理状态查询文件上传记录
     * 
     * @param status 处理状态
     * @param pager 分页参数
     * @return 文件上传记录列表
     */
    public List<SaaSFileUpload> getByStatus(String status, Pager pager) {
        if (ObjectTrue.isEmpty(status)) {
            return new ArrayList<>();
        }
        QueryWrapper<SaaSFileUpload> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", status);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        
        Page<SaaSFileUpload> page = new Page<>(pager.getPageNo(), pager.getPageSize());
        page.setSearchCount(pager.isSearchCount());
        IPage<SaaSFileUpload> pageResult = this.page(page, queryWrapper);
        pager.setTotal(pageResult.getTotal());
        return pageResult.getRecords();
    }

    /**
     * 根据上传批次号查询文件上传记录
     * 
     * @param uploadBatchNo 上传批次号
     * @return 文件上传记录列表
     */
    public List<SaaSFileUpload> getByUploadBatchNo(String uploadBatchNo) {
        if (ObjectTrue.isEmpty(uploadBatchNo)) {
            return new ArrayList<>();
        }
        QueryWrapper<SaaSFileUpload> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("upload_batch_no", uploadBatchNo);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        return this.list(queryWrapper);
    }

    /**
     * 更新文件上传记录
     * 
     * @param record 文件上传记录
     * @return 是否成功
     */
    public boolean updateFileUpload(SaaSFileUpload record) {
        if (record == null || record.getId() <= 0) {
            return false;
        }
        return this.updateById(record);
    }

    /**
     * 批量更新文件上传记录
     * 
     * @param records 文件上传记录列表
     * @return 是否成功
     */
    public boolean updateBatchFileUploads(List<SaaSFileUpload> records) {
        if (ObjectTrue.isEmpty(records)) {
            return false;
        }
        return this.updateBatchById(records);
    }

    /**
     * 根据业务唯一码和解释标识查询文件上传记录
     * 
     * @param businessUniqueCode 业务唯一码
     * @param interpretKey 解释标识
     * @return 文件上传记录
     */
    public SaaSFileUpload getByBusinessUniqueCodeAndInterpretKey(String businessUniqueCode, String interpretKey) {
        if (ObjectTrue.isEmpty(businessUniqueCode) || ObjectTrue.isEmpty(interpretKey)) {
            return null;
        }
        QueryWrapper<SaaSFileUpload> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_unique_code", businessUniqueCode);
        queryWrapper.eq("interpret_key", interpretKey);
        queryWrapper.eq("deleted", 0);
        return this.getOne(queryWrapper);
    }
}
