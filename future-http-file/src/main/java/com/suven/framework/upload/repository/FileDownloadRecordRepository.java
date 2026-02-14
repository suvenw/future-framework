package com.suven.framework.upload.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.mybatis.AbstractMyBatisRepository;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.upload.entity.FileDownloadRecord;
import com.suven.framework.upload.entity.FileDownloadRecord;
import com.suven.framework.upload.mapper.FileDownloadRecordMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * SaaS文件下载记录Repository
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Repository("fileDownloadRecordRepository")
public class FileDownloadRecordRepository extends AbstractMyBatisRepository<FileDownloadRecordMapper, FileDownloadRecord> {

    /**
     * 根据ID查询下载记录
     * 
     * @param id 记录ID
     * @return 下载记录
     */
    public FileDownloadRecord getById(long id) {
        if (id <= 0) {
            return null;
        }
        return this.baseMapper.selectById(id);
    }

    /**
     * 根据业务唯一码查询下载记录列表
     * 
     * @param businessUniqueCode 业务唯一码
     * @return 下载记录列表
     */
    public List<FileDownloadRecord> getByBusinessUniqueCode(String businessUniqueCode) {
        if (ObjectTrue.isEmpty(businessUniqueCode)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<FileDownloadRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileDownloadRecord::getBusinessUniqueCode, businessUniqueCode);
        queryWrapper.eq(FileDownloadRecord::getDeleted, 0);
        queryWrapper.orderByDesc(FileDownloadRecord::getId);
        return this.list(queryWrapper);
    }

    /**
     * 根据业务功能ID查询下载记录列表
     * 
     * @param businessFunctionId 业务功能ID
     * @param pager 分页参数
     * @return 下载记录列表
     */
    public List<FileDownloadRecord> getByBusinessFunctionId(long businessFunctionId, Pager pager) {
        if (businessFunctionId <= 0) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<FileDownloadRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileDownloadRecord::getBusinessFunctionId, businessFunctionId);
        queryWrapper.eq(FileDownloadRecord::getDeleted, 0);
        queryWrapper.orderByDesc(FileDownloadRecord::getId);
        
        Page<FileDownloadRecord> page = new Page<>(pager.getPageNo(), pager.getPageSize());
        page.setSearchCount(pager.isSearchCount());
        IPage<FileDownloadRecord> pageResult = this.page(page, queryWrapper);
        pager.setTotal(pageResult.getTotal());
        return pageResult.getRecords();
    }

    /**
     * 根据状态查询下载记录
     * 
     * @param status 生成状态
     * @param pager 分页参数
     * @return 下载记录列表
     */
    public List<FileDownloadRecord> getByGenerateStatus(String status, Pager pager) {
        if (ObjectTrue.isEmpty(status)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<FileDownloadRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileDownloadRecord::getGenerateStatus, status);
        queryWrapper.eq(FileDownloadRecord::getDeleted, 0);
        queryWrapper.orderByDesc(FileDownloadRecord::getId);
        
        Page<FileDownloadRecord> page = new Page<>(pager.getPageNo(), pager.getPageSize());
        page.setSearchCount(pager.isSearchCount());
        IPage<FileDownloadRecord> pageResult = this.page(page, queryWrapper);
        pager.setTotal(pageResult.getTotal());
        return pageResult.getRecords();
    }

    /**
     * 更新下载记录
     * 
     * @param record 下载记录
     * @return 是否成功
     */
    public boolean updateDownloadRecord(FileDownloadRecord record) {
        if (record == null || record.getId() <= 0) {
            return false;
        }
        return this.updateById(record);
    }

    /**
     * 更新下载次数
     * 
     * @param id 下载记录ID
     * @return 是否成功
     */
    public boolean incrementDownloadCount(long id) {
        FileDownloadRecord record = this.getById(id);
        if (record == null) {
            return false;
        }
        record.setDownloadCount(record.getDownloadCount() + 1);
        return this.updateById(record);
    }

    /**
     * 批量更新下载记录
     * 
     * @param records 下载记录列表
     * @return 是否成功
     */
    public boolean updateBatchDownloadRecords(List<FileDownloadRecord> records) {
        if (ObjectTrue.isEmpty(records)) {
            return false;
        }
        return this.updateBatchById(records);
    }
}
