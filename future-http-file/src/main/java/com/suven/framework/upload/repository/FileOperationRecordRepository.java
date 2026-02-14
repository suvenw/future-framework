package com.suven.framework.upload.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.mybatis.AbstractMyBatisRepository;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.upload.entity.FileOperationRecord;
import com.suven.framework.upload.mapper.FileOperationRecordMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * SaaS文件操作记录Repository
 *
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-09
 */
@Repository("fileOperationRecordRepository")
public class FileOperationRecordRepository extends AbstractMyBatisRepository<FileOperationRecordMapper, FileOperationRecord> {

    /**
     * 根据ID查询操作记录
     *
     * @param id 记录ID
     * @return 操作记录
     */
    public FileOperationRecord getById(long id) {
        if (id <= 0) {
            return null;
        }
        return this.baseMapper.selectById(id);
    }

    /**
     * 根据文件存储ID查询操作记录
     *
     * @param fileUploadStorageId 文件存储ID
     * @return 操作记录列表
     */
    public List<FileOperationRecord> getByFileUploadStorageId(long fileUploadStorageId) {
        if (fileUploadStorageId <= 0) {
            return new ArrayList<>();
        }
        QueryWrapper<FileOperationRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_upload_storage_id", fileUploadStorageId);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");
        return this.list(queryWrapper);
    }

    /**
     * 根据状态查询操作记录
     *
     * @param status 状态
     * @param pager 分页参数
     * @return 操作记录列表
     */
    public List<FileOperationRecord> getByStatus(String status, Pager pager) {
        if (ObjectTrue.isEmpty(status)) {
            return new ArrayList<>();
        }
        QueryWrapper<FileOperationRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", status);
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("id");

        Page<FileOperationRecord> page = new Page<>(pager.getPageNo(), pager.getPageSize());
        page.setSearchCount(pager.isSearchCount());
        IPage<FileOperationRecord> pageResult = this.page(page, queryWrapper);
        pager.setTotal(pageResult.getTotal());
        return pageResult.getRecords();
    }

    /**
     * 更新操作记录
     *
     * @param record 操作记录
     * @return 是否成功
     */
    public boolean updateOperationRecord(FileOperationRecord record) {
        if (record == null || record.getId() <= 0) {
            return false;
        }
        return this.updateById(record);
    }

    /**
     * 批量更新操作记录
     *
     * @param records 操作记录列表
     * @return 是否成功
     */
    public boolean updateBatchOperationRecords(List<FileOperationRecord> records) {
        if (ObjectTrue.isEmpty(records)) {
            return false;
        }
        return this.updateBatchById(records);
    }
}
