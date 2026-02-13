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
 * 文件解释记录明细Repository
 * 
 * 功能：提供文件解释记录的数据访问接口，支持按文件上传、业务功能、行号等多种查询方式
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Repository("fileInterpretRecordRepository")
public class FileInterpretRecordRepository extends AbstractMyBatisRepository<FileInterpretRecordMapper, FileInterpretRecord> {

    // ==================== 按关联关系查询 ====================

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
        queryWrapper.orderByAsc("row_number");
        return this.list(queryWrapper);
    }

    /**
     * 根据文件上传ID分页查询
     * 
     * @param fileUploadId 文件上传ID
     * @param pager 分页参数
     * @return 分页结果
     */
    public List<FileInterpretRecord> getByFileUploadId(long fileUploadId, Pager pager) {
        if (fileUploadId <= 0) {
            return new ArrayList<>();
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_upload_id", fileUploadId);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByAsc("row_number");
        
        Page<FileInterpretRecord> page = new Page<>(pager.getPageNo(), pager.getPageSize());
        page.setSearchCount(pager.isSearchCount());
        IPage<FileInterpretRecord> pageResult = this.page(page, queryWrapper);
        pager.setTotal(pageResult.getTotal());
        return pageResult.getRecords();
    }

    /**
     * 根据公司业务功能ID查询解释记录
     * 
     * @param businessFunctionId 公司业务功能ID
     * @return 解释记录列表
     */
    public List<FileInterpretRecord> getByBusinessFunctionId(long businessFunctionId) {
        if (businessFunctionId <= 0) {
            return new ArrayList<>();
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_function_id", businessFunctionId);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        return this.list(queryWrapper);
    }

    /**
     * 根据字段映射ID查询解释记录
     * 
     * @param fieldMappingId 字段映射ID
     * @return 解释记录列表
     */
    public List<FileInterpretRecord> getByFieldMappingId(long fieldMappingId) {
        if (fieldMappingId <= 0) {
            return new ArrayList<>();
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("field_mapping_id", fieldMappingId);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        return this.list(queryWrapper);
    }

    // ==================== 按业务信息查询 ====================

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
     * 根据业务唯一码分页查询
     * 
     * @param businessUniqueCode 业务唯一码
     * @param pager 分页参数
     * @return 分页结果
     */
    public List<FileInterpretRecord> getByBusinessUniqueCode(String businessUniqueCode, Pager pager) {
        if (ObjectTrue.isEmpty(businessUniqueCode)) {
            return new ArrayList<>();
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_unique_code", businessUniqueCode);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        
        Page<FileInterpretRecord> page = new Page<>(pager.getPageNo(), pager.getPageSize());
        page.setSearchCount(pager.isSearchCount());
        IPage<FileInterpretRecord> pageResult = this.page(page, queryWrapper);
        pager.setTotal(pageResult.getTotal());
        return pageResult.getRecords();
    }

    // ==================== 按校验状态查询 ====================

    /**
     * 根据校验状态查询解释记录
     * 
     * @param checkStatus 校验状态
     * @return 解释记录列表
     */
    public List<FileInterpretRecord> getByCheckStatus(String checkStatus) {
        if (ObjectTrue.isEmpty(checkStatus)) {
            return new ArrayList<>();
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("check_status", checkStatus);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        return this.list(queryWrapper);
    }

    /**
     * 根据校验状态分页查询
     * 
     * @param checkStatus 校验状态
     * @param pager 分页参数
     * @return 分页结果
     */
    public List<FileInterpretRecord> getByCheckStatus(String checkStatus, Pager pager) {
        if (ObjectTrue.isEmpty(checkStatus)) {
            return new ArrayList<>();
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("check_status", checkStatus);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        
        Page<FileInterpretRecord> page = new Page<>(pager.getPageNo(), pager.getPageSize());
        page.setSearchCount(pager.isSearchCount());
        IPage<FileInterpretRecord> pageResult = this.page(page, queryWrapper);
        pager.setTotal(pageResult.getTotal());
        return pageResult.getRecords();
    }

    /**
     * 查询校验失败的记录
     * 
     * @param fileUploadId 文件上传ID（可选）
     * @return 校验失败的记录列表
     */
    public List<FileInterpretRecord> getInvalidRecords(Long fileUploadId) {
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("check_status", "INVALID");
        queryWrapper.eq("deleted", 0);
        if (fileUploadId != null && fileUploadId > 0) {
            queryWrapper.eq("file_upload_id", fileUploadId);
        }
        queryWrapper.orderByDesc("id");
        return this.list(queryWrapper);
    }

    // ==================== 按处理状态查询 ====================

    /**
     * 根据处理状态查询解释记录
     * 
     * @param processStatus 处理状态
     * @return 解释记录列表
     */
    public List<FileInterpretRecord> getByProcessStatus(String processStatus) {
        if (ObjectTrue.isEmpty(processStatus)) {
            return new ArrayList<>();
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("process_status", processStatus);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        return this.list(queryWrapper);
    }

    /**
     * 根据处理状态分页查询
     * 
     * @param processStatus 处理状态
     * @param pager 分页参数
     * @return 分页结果
     */
    public List<FileInterpretRecord> getByProcessStatus(String processStatus, Pager pager) {
        if (ObjectTrue.isEmpty(processStatus)) {
            return new ArrayList<>();
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("process_status", processStatus);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        
        Page<FileInterpretRecord> page = new Page<>(pager.getPageNo(), pager.getPageSize());
        page.setSearchCount(pager.isSearchCount());
        IPage<FileInterpretRecord> pageResult = this.page(page, queryWrapper);
        pager.setTotal(pageResult.getTotal());
        return pageResult.getRecords();
    }

    /**
     * 查询待处理的记录（业务处理状态为PENDING）
     * 
     * @param fileUploadId 文件上传ID
     * @param pager 分页参数
     * @return 待处理的记录列表
     */
    public List<FileInterpretRecord> getPendingProcessRecords(long fileUploadId, Pager pager) {
        if (fileUploadId <= 0) {
            return new ArrayList<>();
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_upload_id", fileUploadId);
        queryWrapper.eq("business_status", "PENDING");
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByAsc("row_number");
        
        Page<FileInterpretRecord> page = new Page<>(pager.getPageNo(), pager.getPageSize());
        page.setSearchCount(pager.isSearchCount());
        IPage<FileInterpretRecord> pageResult = this.page(page, queryWrapper);
        pager.setTotal(pageResult.getTotal());
        return pageResult.getRecords();
    }

    // ==================== 按业务处理状态查询 ====================

    /**
     * 根据业务处理状态查询解释记录
     * 
     * @param businessStatus 业务处理状态
     * @return 解释记录列表
     */
    public List<FileInterpretRecord> getByBusinessStatus(String businessStatus) {
        if (ObjectTrue.isEmpty(businessStatus)) {
            return new ArrayList<>();
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_status", businessStatus);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        return this.list(queryWrapper);
    }

    /**
     * 根据业务处理状态分页查询
     * 
     * @param businessStatus 业务处理状态
     * @param pager 分页参数
     * @return 分页结果
     */
    public List<FileInterpretRecord> getByBusinessStatus(String businessStatus, Pager pager) {
        if (ObjectTrue.isEmpty(businessStatus)) {
            return new ArrayList<>();
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_status", businessStatus);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        
        Page<FileInterpretRecord> page = new Page<>(pager.getPageNo(), pager.getPageSize());
        page.setSearchCount(pager.isSearchCount());
        IPage<FileInterpretRecord> pageResult = this.page(page, queryWrapper);
        pager.setTotal(pageResult.getTotal());
        return pageResult.getRecords();
    }

    /**
     * 查询业务处理成功的记录
     * 
     * @param fileUploadId 文件上传ID
     * @return 业务处理成功的记录列表
     */
    public List<FileInterpretRecord> getBusinessSuccessRecords(long fileUploadId) {
        if (fileUploadId <= 0) {
            return new ArrayList<>();
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_upload_id", fileUploadId);
        queryWrapper.eq("business_status", "SUCCESS");
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByAsc("row_number");
        return this.list(queryWrapper);
    }

    /**
     * 查询业务处理失败的记录
     * 
     * @param fileUploadId 文件上传ID
     * @return 业务处理失败的记录列表
     */
    public List<FileInterpretRecord> getBusinessFailedRecords(long fileUploadId) {
        if (fileUploadId <= 0) {
            return new ArrayList<>();
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_upload_id", fileUploadId);
        queryWrapper.eq("business_status", "FAILED");
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByAsc("row_number");
        return this.list(queryWrapper);
    }

    // ==================== 按回调状态查询 ====================

    /**
     * 根据回调状态查询解释记录
     * 
     * @param callbackStatus 回调状态
     * @return 解释记录列表
     */
    public List<FileInterpretRecord> getByCallbackStatus(String callbackStatus) {
        if (ObjectTrue.isEmpty(callbackStatus)) {
            return new ArrayList<>();
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("callback_status", callbackStatus);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        return this.list(queryWrapper);
    }

    /**
     * 查询需要回调的记录
     * 
     * @return 需要回调的记录列表
     */
    public List<FileInterpretRecord> getNeedCallbackRecords() {
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("need_callback", 1);
        queryWrapper.eq("callback_status", "PENDING");
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByAsc("id");
        return this.list(queryWrapper);
    }

    // ==================== 按行号查询 ====================

    /**
     * 根据文件上传ID和行号查询解释记录
     * 
     * @param fileUploadId 文件上传ID
     * @param rowNumber 行号
     * @return 解释记录
     */
    public FileInterpretRecord getByFileUploadIdAndRowNumber(long fileUploadId, int rowNumber) {
        if (fileUploadId <= 0 || rowNumber <= 0) {
            return null;
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_upload_id", fileUploadId);
        queryWrapper.eq("row_number", rowNumber);
        queryWrapper.eq("deleted", 0);
        return this.getOne(queryWrapper);
    }

    // ==================== 统计查询 ====================

    /**
     * 统计文件上传的记录数
     * 
     * @param fileUploadId 文件上传ID
     * @return 记录数
     */
    public int countByFileUploadId(long fileUploadId) {
        if (fileUploadId <= 0) {
            return 0;
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_upload_id", fileUploadId);
        queryWrapper.eq("deleted", 0);
        return this.count(queryWrapper);
    }

    /**
     * 统计校验状态数量
     * 
     * @param fileUploadId 文件上传ID
     * @param checkStatus 校验状态
     * @return 数量
     */
    public int countByCheckStatus(long fileUploadId, String checkStatus) {
        if (fileUploadId <= 0 || ObjectTrue.isEmpty(checkStatus)) {
            return 0;
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_upload_id", fileUploadId);
        queryWrapper.eq("check_status", checkStatus);
        queryWrapper.eq("deleted", 0);
        return this.count(queryWrapper);
    }

    /**
     * 统计业务处理状态数量
     * 
     * @param fileUploadId 文件上传ID
     * @param businessStatus 业务处理状态
     * @return 数量
     */
    public int countByBusinessStatus(long fileUploadId, String businessStatus) {
        if (fileUploadId <= 0 || ObjectTrue.isEmpty(businessStatus)) {
            return 0;
        }
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_upload_id", fileUploadId);
        queryWrapper.eq("business_status", businessStatus);
        queryWrapper.eq("deleted", 0);
        return this.count(queryWrapper);
    }

    // ==================== 更新操作 ====================

    /**
     * 更新解释记录
     * 
     * @param record 解释记录
     * @return 是否成功
     */
    public boolean updateInterpretRecord(FileInterpretRecord record) {
        if (record == null || record.getId() <= 0) {
            return false;
        }
        return this.updateById(record);
    }

    /**
     * 批量更新解释记录
     * 
     * @param records 解释记录列表
     * @return 是否成功
     */
    public boolean updateBatchInterpretRecords(List<FileInterpretRecord> records) {
        if (ObjectTrue.isEmpty(records)) {
            return false;
        }
        return this.updateBatchById(records);
    }

    /**
     * 标记记录为处理中
     * 
     * @param id 记录ID
     * @param message 处理消息
     * @return 是否成功
     */
    public boolean markProcessing(long id, String message) {
        if (id <= 0) {
            return false;
        }
        FileInterpretRecord record = new FileInterpretRecord();
        record.setId(id);
        record.setProcessStatus("PROCESSING");
        record.setProcessMessage(message);
        return this.updateById(record);
    }

    /**
     * 标记记录为处理成功
     * 
     * @param id 记录ID
     * @param result 业务结果
     * @return 是否成功
     */
    public boolean markBusinessSuccess(long id, String result) {
        if (id <= 0) {
            return false;
        }
        FileInterpretRecord record = new FileInterpretRecord();
        record.setId(id);
        record.setBusinessStatus("SUCCESS");
        record.setBusinessResult(result);
        record.setProcessStatus("SUCCESS");
        return this.updateById(record);
    }

    /**
     * 标记记录为处理失败
     * 
     * @param id 记录ID
     * @param errorMessage 错误信息
     * @return 是否成功
     */
    public boolean markBusinessFailed(long id, String errorMessage) {
        if (id <= 0) {
            return false;
        }
        FileInterpretRecord record = new FileInterpretRecord();
        record.setId(id);
        record.setBusinessStatus("FAILED");
        record.setBusinessErrorMessage(errorMessage);
        record.setProcessStatus("FAILED");
        return this.updateById(record);
    }

    /**
     * 标记记录为已跳过
     * 
     * @param id 记录ID
     * @param message 跳过原因
     * @return 是否成功
     */
    public boolean markSkipped(long id, String message) {
        if (id <= 0) {
            return false;
        }
        FileInterpretRecord record = new FileInterpretRecord();
        record.setId(id);
        record.setProcessStatus("SKIPPED");
        record.setProcessMessage(message);
        return this.updateById(record);
    }

    /**
     * 增加重试次数
     * 
     * @param id 记录ID
     * @return 是否成功
     */
    public boolean incrementRetryCount(long id) {
        if (id <= 0) {
            return false;
        }
        FileInterpretRecord record = this.baseMapper.selectById(id);
        if (record == null) {
            return false;
        }
        record.setRetryCount(record.getRetryCount() + 1);
        return this.updateById(record);
    }

    /**
     * 更新回调状态
     * 
     * @param id 记录ID
     * @param callbackStatus 回调状态
     * @param callbackResponse 回调响应
     * @return 是否成功
     */
    public boolean updateCallbackStatus(long id, String callbackStatus, String callbackResponse) {
        if (id <= 0) {
            return false;
        }
        FileInterpretRecord record = new FileInterpretRecord();
        record.setId(id);
        record.setCallbackStatus(callbackStatus);
        record.setCallbackResponse(callbackResponse);
        if ("FAILED".equals(callbackStatus)) {
            FileInterpretRecord existing = this.baseMapper.selectById(id);
            record.setCallbackFailCount(existing.getCallbackFailCount() + 1);
        }
        return this.updateById(record);
    }
}
