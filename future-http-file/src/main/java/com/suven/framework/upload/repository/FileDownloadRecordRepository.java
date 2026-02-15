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
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.upload.dto.enums.FileDownloadRecordQueryEnum;
import com.suven.framework.upload.entity.FileDownloadRecord;
import com.suven.framework.upload.mapper.FileDownloadRecordMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * SaaS文件下载记录Repository
 * 
 * 统一封装对 saas_file_download_record 表的查询逻辑，
 * 避免在 Service 中直接拼装 MyBatis 查询条件。
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
        Wrapper<FileDownloadRecord> wrapper = builderQueryEnum(
                FileDownloadRecordQueryEnum.BY_BUSINESS_UNIQUE_CODE_DESC,
                FileDownloadRecord.build().toBusinessUniqueCode(businessUniqueCode)
        );
        return this.getListByQuery(wrapper);
    }

    /**
     * 根据业务功能ID查询下载记录列表
     * 
     * @param businessFunctionId 业务功能ID
     * @param pager 分页参数
     * @return 分页结果
     */
    public PageResult<FileDownloadRecord> getByBusinessFunctionId(long businessFunctionId, Pager<FileDownloadRecord> pager) {
        if (businessFunctionId <= 0) {
            return new PageResult<>();
        }
        Wrapper<FileDownloadRecord> wrapper = builderQueryEnum(
                FileDownloadRecordQueryEnum.BY_BUSINESS_FUNCTION_ID_DESC,
                FileDownloadRecord.build().toBusinessFunctionId(businessFunctionId)
        );
        return this.getListByPage(pager, wrapper);
    }

    /**
     * 根据生成状态查询下载记录
     * 
     * @param status 生成状态
     * @param pager 分页参数
     * @return 分页结果
     */
    public PageResult<FileDownloadRecord> getByGenerateStatus(String status, Pager<FileDownloadRecord> pager) {
        if (ObjectTrue.isEmpty(status)) {
            return new PageResult<>();
        }
        Wrapper<FileDownloadRecord> wrapper = builderQueryEnum(
                FileDownloadRecordQueryEnum.BY_GENERATE_STATUS_DESC,
                FileDownloadRecord.build().toGenerateStatus(status)
        );
        return this.getListByPage(pager, wrapper);
    }

    /**
     * 通过分页获取FileDownloadRecord信息实现查找缓存和数据库的方法
     * 
     * @param pager 分页查询对象
     * @param queryWrapper 查询条件对象
     * @return 返回表对象列表
     * @author suven  作者
     * date 2026-02-11 创建时间
     */
    public PageResult<FileDownloadRecord> getListByPage(Pager<FileDownloadRecord> pager, Wrapper<FileDownloadRecord> queryWrapper) {
        PageResult<FileDownloadRecord> pageVo = new PageResult<>();
        if (queryWrapper == null) {
            queryWrapper = new QueryWrapper<>();
        }
        Page<FileDownloadRecord> iPage = new Page<>(pager.getPageNo(), pager.getPageSize());
        iPage.setSearchCount(pager.isSearchCount());
        IPage<FileDownloadRecord> page = super.page(iPage, queryWrapper);
        if (ObjectTrue.isEmpty(page) || ObjectTrue.isEmpty(page.getRecords())) {
            return pageVo;
        }
        pageVo.of(page.getRecords(), pager.getPageSize(), page.getTotal());
        return pageVo;
    }

    /**
     * 通过分页获取FileDownloadRecord信息实现查找缓存和数据库的方法
     * 
     * @param queryWrapper QueryWrapper 表查询条件信息
     * @return 返回列表对象
     * @author suven  作者
     * date 2026-02-11 创建时间
     */
    public List<FileDownloadRecord> getListByQuery(Wrapper<FileDownloadRecord> queryWrapper) {
        List<FileDownloadRecord> resDtoList = new ArrayList<>();
        if (ObjectTrue.isEmpty(queryWrapper)) {
            queryWrapper = new QueryWrapper<>();
        }
        List<FileDownloadRecord> list = super.list(queryWrapper);
        if (ObjectTrue.isEmpty(list)) {
            return resDtoList;
        }
        return list;
    }

    /**
     * 通过枚举实现FileDownloadRecord不同数据库的条件查询的逻辑实现的方法
     * 
     * @param queryEnum 查询枚举
     * @param queryObject 参数对象实现
     * @return 返回查询条件对象
     * @author suven  作者
     * date 2026-02-11 创建时间
     */
    public Wrapper<FileDownloadRecord> builderQueryEnum(FileDownloadRecordQueryEnum queryEnum, Object queryObject) {
        QueryWrapper<FileDownloadRecord> queryWrapper = new QueryWrapper<>();
        if (ObjectTrue.isEmpty(queryEnum)) {
            AssertEx.error(new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
        }
        if (ObjectTrue.isEmpty(queryObject)) {
            AssertEx.error(new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
        }
        FileDownloadRecord record = FileDownloadRecord.build().clone(queryObject);
        switch (queryEnum) {
            case DESC_ID: {
                queryWrapper.eq("deleted", 0);
                queryWrapper.orderByDesc("id");
                break;
            }
            case BY_BUSINESS_UNIQUE_CODE_DESC: {
                queryWrapper.lambda()
                        .eq(FileDownloadRecord::getBusinessUniqueCode, record.getBusinessUniqueCode())
                        .eq(FileDownloadRecord::getDeleted, 0)
                        .orderByDesc(FileDownloadRecord::getId);
                break;
            }
            case BY_BUSINESS_FUNCTION_ID_DESC: {
                queryWrapper.lambda()
                        .eq(FileDownloadRecord::getBusinessFunctionId, record.getBusinessFunctionId())
                        .eq(FileDownloadRecord::getDeleted, 0)
                        .orderByDesc(FileDownloadRecord::getId);
                break;
            }
            case BY_GENERATE_STATUS_DESC: {
                queryWrapper.lambda()
                        .eq(FileDownloadRecord::getGenerateStatus, record.getGenerateStatus())
                        .eq(FileDownloadRecord::getDeleted, 0)
                        .orderByDesc(FileDownloadRecord::getId);
                break;
            }
            default:
                break;
        }
        return queryWrapper;
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
