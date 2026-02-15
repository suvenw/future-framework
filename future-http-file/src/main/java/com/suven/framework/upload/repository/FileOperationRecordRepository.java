package com.suven.framework.upload.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import com.suven.framework.upload.dto.enums.FileOperationRecordQueryEnum;
import com.suven.framework.upload.entity.FileOperationRecord;
import com.suven.framework.upload.mapper.FileOperationRecordMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * SaaS文件操作记录Repository
 * 
 * 统一封装对 saas_file_operation_record 表的查询逻辑，
 * 避免在 Service 中直接拼装 MyBatis 查询条件。
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
     * 根据文件存储ID查询操作记录列表
     *
     * @param fileUploadStorageId 文件存储ID
     * @return 操作记录列表
     */
    public List<FileOperationRecord> getByFileUploadStorageId(long fileUploadStorageId) {
        if (fileUploadStorageId <= 0) {
            return new ArrayList<>();
        }
        Wrapper<FileOperationRecord> wrapper = builderQueryEnum(
                FileOperationRecordQueryEnum.BY_FILE_UPLOAD_STORAGE_ID_DESC,
                FileOperationRecord.build().toFileUploadStorageId(fileUploadStorageId)
        );
        return this.getListByQuery(wrapper);
    }

    /**
     * 分页查询操作记录（根据状态）
     *
     * @param status 状态
     * @param pager 分页参数
     * @return 分页结果
     */
    public PageResult<FileOperationRecord> getByStatus(String status, Pager<FileOperationRecord> pager) {
        if (ObjectTrue.isEmpty(status)) {
            return new PageResult<>();
        }
        Wrapper<FileOperationRecord> wrapper = builderQueryEnum(
                FileOperationRecordQueryEnum.BY_STATUS_DESC,
                FileOperationRecord.build().toStatus(status)
        );
        return this.getListByPage(pager, wrapper);
    }

    /**
     * 根据公司ID查询操作记录列表
     *
     * @param companyId 公司ID
     * @return 操作记录列表
     */
    public List<FileOperationRecord> getByCompanyId(String companyId) {
        if (ObjectTrue.isEmpty(companyId)) {
            return new ArrayList<>();
        }
        Wrapper<FileOperationRecord> wrapper = builderQueryEnum(
                FileOperationRecordQueryEnum.BY_COMPANY_ID_DESC,
                FileOperationRecord.build().toCompanyId(companyId)
        );
        return this.getListByQuery(wrapper);
    }

    /**
     * 根据应用ID查询操作记录列表
     *
     * @param appId 应用ID
     * @return 操作记录列表
     */
    public List<FileOperationRecord> getByAppId(String appId) {
        if (ObjectTrue.isEmpty(appId)) {
            return new ArrayList<>();
        }
        Wrapper<FileOperationRecord> wrapper = builderQueryEnum(
                FileOperationRecordQueryEnum.BY_APP_ID_DESC,
                FileOperationRecord.build().toAppId(appId)
        );
        return this.getListByQuery(wrapper);
    }

    /**
     * 根据客户端ID查询操作记录列表
     *
     * @param clientId 客户端ID
     * @return 操作记录列表
     */
    public List<FileOperationRecord> getByClientId(String clientId) {
        if (ObjectTrue.isEmpty(clientId)) {
            return new ArrayList<>();
        }
        Wrapper<FileOperationRecord> wrapper = builderQueryEnum(
                FileOperationRecordQueryEnum.BY_CLIENT_ID_DESC,
                FileOperationRecord.build().toClientId(clientId)
        );
        return this.getListByQuery(wrapper);
    }

    /**
     * 根据业务ID查询操作记录列表
     *
     * @param useBusinessId 业务ID
     * @return 操作记录列表
     */
    public List<FileOperationRecord> getByUseBusinessId(long useBusinessId) {
        if (useBusinessId <= 0) {
            return new ArrayList<>();
        }
        Wrapper<FileOperationRecord> wrapper = builderQueryEnum(
                FileOperationRecordQueryEnum.BY_USE_BUSINESS_ID_DESC,
                FileOperationRecord.build().toUseBusinessId(useBusinessId)
        );
        return this.getListByQuery(wrapper);
    }

    /**
     * 根据回调状态查询操作记录列表
     *
     * @param callbackStatus 回调状态
     * @return 操作记录列表
     */
    public List<FileOperationRecord> getByCallbackStatus(String callbackStatus) {
        if (ObjectTrue.isEmpty(callbackStatus)) {
            return new ArrayList<>();
        }
        Wrapper<FileOperationRecord> wrapper = builderQueryEnum(
                FileOperationRecordQueryEnum.BY_CALLBACK_STATUS_DESC,
                FileOperationRecord.build().toCallbackStatus(callbackStatus)
        );
        return this.getListByQuery(wrapper);
    }

    /**
     * 通过分页获取FileOperationRecord信息实现查找缓存和数据库的方法
     * 
     * @param pager 分页查询对象
     * @param queryWrapper 查询条件对象
     * @return 返回表对象列表
     * @author suven  作者
     * date 2026-02-11 创建时间
     */
    public PageResult<FileOperationRecord> getListByPage(Pager<FileOperationRecord> pager, Wrapper<FileOperationRecord> queryWrapper) {
        PageResult<FileOperationRecord> pageVo = new PageResult<>();
        if (queryWrapper == null) {
            queryWrapper = new QueryWrapper<>();
        }
        Page<FileOperationRecord> iPage = new Page<>(pager.getPageNo(), pager.getPageSize());
        iPage.setSearchCount(pager.isSearchCount());
        IPage<FileOperationRecord> page = super.page(iPage, queryWrapper);
        if (ObjectTrue.isEmpty(page) || ObjectTrue.isEmpty(page.getRecords())) {
            return pageVo;
        }
        pageVo.of(page.getRecords(), pager.getPageSize(), page.getTotal());
        return pageVo;
    }

    /**
     * 通过分页获取FileOperationRecord信息实现查找缓存和数据库的方法
     * 
     * @param queryWrapper QueryWrapper 表查询条件信息
     * @return 返回列表对象
     * @author suven  作者
     * date 2026-02-11 创建时间
     */
    public List<FileOperationRecord> getListByQuery(Wrapper<FileOperationRecord> queryWrapper) {
        List<FileOperationRecord> resDtoList = new ArrayList<>();
        if (ObjectTrue.isEmpty(queryWrapper)) {
            queryWrapper = new QueryWrapper<>();
        }
        List<FileOperationRecord> list = super.list(queryWrapper);
        if (ObjectTrue.isEmpty(list)) {
            return resDtoList;
        }
        return list;
    }

    /**
     * 通过枚举实现FileOperationRecord不同数据库的条件查询的逻辑实现的方法
     * 
     * @param queryEnum 查询枚举
     * @param queryObject 参数对象实现
     * @return 返回查询条件对象
     * @author suven  作者
     * date 2026-02-11 创建时间
     */
    public Wrapper<FileOperationRecord> builderQueryEnum(FileOperationRecordQueryEnum queryEnum, FileOperationRecord queryObject) {
        QueryWrapper<FileOperationRecord> queryWrapper = new QueryWrapper<>();
        if (ObjectTrue.isEmpty(queryEnum)) {
            AssertEx.error(new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
        }
        if (ObjectTrue.isEmpty(queryObject)) {
            AssertEx.error(new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
        }
        FileOperationRecord record = FileOperationRecord.build().clone(queryObject);
        switch (queryEnum) {
            case DESC_ID: {
                queryWrapper.eq("deleted", 0);
                queryWrapper.orderByDesc("id");
                break;
            }
            case BY_FILE_UPLOAD_STORAGE_ID_DESC: {
                queryWrapper.lambda()
                        .eq(FileOperationRecord::getFileUploadStorageId, record.getFileUploadStorageId())
                        .eq(FileOperationRecord::getDeleted, 0)
                        .orderByDesc(FileOperationRecord::getId);
                break;
            }
            case BY_STATUS_DESC: {
                queryWrapper.lambda()
                        .eq(FileOperationRecord::getStatus, record.getStatus())
                        .eq(FileOperationRecord::getDeleted, 0)
                        .orderByDesc(FileOperationRecord::getId);
                break;
            }
            case BY_COMPANY_ID_DESC: {
                queryWrapper.lambda()
                        .eq(FileOperationRecord::getCompanyId, record.getCompanyId())
                        .eq(FileOperationRecord::getDeleted, 0)
                        .orderByDesc(FileOperationRecord::getId);
                break;
            }
            case BY_APP_ID_DESC: {
                queryWrapper.lambda()
                        .eq(FileOperationRecord::getAppId, record.getAppId())
                        .eq(FileOperationRecord::getDeleted, 0)
                        .orderByDesc(FileOperationRecord::getId);
                break;
            }
            case BY_CLIENT_ID_DESC: {
                queryWrapper.lambda()
                        .eq(FileOperationRecord::getClientId, record.getClientId())
                        .eq(FileOperationRecord::getDeleted, 0)
                        .orderByDesc(FileOperationRecord::getId);
                break;
            }
            case BY_USE_BUSINESS_ID_DESC: {
                queryWrapper.lambda()
                        .eq(FileOperationRecord::getUseBusinessId, record.getUseBusinessId())
                        .eq(FileOperationRecord::getDeleted, 0)
                        .orderByDesc(FileOperationRecord::getId);
                break;
            }
            case BY_CALLBACK_STATUS_DESC: {
                queryWrapper.lambda()
                        .eq(FileOperationRecord::getCallbackStatus, record.getCallbackStatus())
                        .eq(FileOperationRecord::getDeleted, 0)
                        .orderByDesc(FileOperationRecord::getId);
                break;
            }
            case BY_QUERY_CALLBACK_STATUS_DESC:
                LambdaQueryWrapper<FileOperationRecord> query= queryWrapper.lambda();
                if (ObjectTrue.isNotEmpty(queryObject.getAppId())) {
                    query.eq(FileOperationRecord::getAppId, queryObject.getAppId());
                }
                if (ObjectTrue.isNotEmpty(queryObject.getClientId())) {
                    query.eq(FileOperationRecord::getClientId, Long.parseLong(queryObject.getClientId()));
                }
                if (ObjectTrue.isNotEmpty(queryObject.getFileProductName())) {
                    query.eq(FileOperationRecord::getFileProductName, queryObject.getFileProductName());
                }
                if (ObjectTrue.isNotEmpty(queryObject.getFileBusinessName())) {
                    query.eq(FileOperationRecord::getFileBusinessName, queryObject.getFileBusinessName());
                }
                if (ObjectTrue.isNotEmpty(queryObject.getStatus())) {
                    query.eq(FileOperationRecord::getStatus, queryObject.getStatus());
                }
                if (ObjectTrue.isNotEmpty(queryObject.getFileSourceName())) {
                    query.like(FileOperationRecord::getFileSourceName, queryObject.getFileSourceName());
                }
                query.eq(FileOperationRecord::getDeleted, 0);
                query.orderByDesc(FileOperationRecord::getId);
            default:
                break;
        }
        return queryWrapper;
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
