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
import com.suven.framework.upload.dto.enums.FileInterpretRecordQueryEnum;
import com.suven.framework.upload.entity.FileInterpretRecord;
import com.suven.framework.upload.mapper.FileInterpretRecordMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * SaaS文件解释记录Repository
 * 
 * 统一封装对 saas_file_interpret_record 表的查询逻辑，
 * 避免在 Service 中直接拼装 MyBatis 查询条件。
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
        Wrapper<FileInterpretRecord> wrapper = builderQueryEnum(
                FileInterpretRecordQueryEnum.BY_FILE_UPLOAD_ID_DESC,
                FileInterpretRecord.build().toFileUploadId(fileUploadId)
        );
        return this.getListByQuery(wrapper);
    }

    /**
     * 分页查询解释记录（根据文件上传ID和状态）
     * 
     * @param fileUploadId 文件上传ID
     * @param status 状态（可选）
     * @param pager 分页参数
     * @return 分页结果
     */
    public PageResult<FileInterpretRecord> getByFileUploadIdAndStatus(long fileUploadId, String status, Pager<FileInterpretRecord> pager) {
        if (fileUploadId <= 0) {
            return new PageResult<>();
        }
        FileInterpretRecord queryObject = FileInterpretRecord.build().toFileUploadId(fileUploadId).toBusinessStatus(status);
        // 如果 status 不为空，可以通过扩展字段传递，或者直接在 builderQueryEnum 中处理
        // 这里先使用枚举方式，status 通过其他方式传递
        Wrapper<FileInterpretRecord> wrapper = builderQueryEnum(
                FileInterpretRecordQueryEnum.BY_FILE_UPLOAD_ID_AND_STATUS_PENDING_DESC,
                queryObject
        );
        PageResult<FileInterpretRecord> pageResult = this.getListByPage(pager,wrapper);
        return pageResult;
    }

    /**
     * 根据业务功能ID查询解释记录
     * 
     * @param businessFunctionId 业务功能ID
     * @return 解释记录列表
     */
    public List<FileInterpretRecord> getByBusinessFunctionId(long businessFunctionId) {
        if (businessFunctionId <= 0) {
            return new ArrayList<>();
        }
        Wrapper<FileInterpretRecord> wrapper = builderQueryEnum(
                FileInterpretRecordQueryEnum.BY_BUSINESS_FUNCTION_ID_DESC,
                FileInterpretRecord.build().toBusinessFunctionId(businessFunctionId)
        );
        return this.getListByQuery(wrapper);
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
        Wrapper<FileInterpretRecord> wrapper = builderQueryEnum(
                FileInterpretRecordQueryEnum.BY_BUSINESS_UNIQUE_CODE_DESC,
                FileInterpretRecord.build().toBusinessUniqueCode(businessUniqueCode)
        );
        return this.getListByQuery(wrapper);
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
        // 注意：表结构中有 interpret_key 字段，但实体类可能没有，使用字符串字段名查询
        FileInterpretRecord queryObject = FileInterpretRecord.build()
                .toExtField1(interpretKey); // 使用 extField1 临时存储 interpretKey 用于查询
        Wrapper<FileInterpretRecord> wrapper = builderQueryEnum(
                FileInterpretRecordQueryEnum.BY_INTERPRET_KEY,
                queryObject
        );
        return this.getOne(wrapper);
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
        // 注意：表结构中有 interpret_key 字段，但实体类可能没有，使用字符串字段名查询
        FileInterpretRecord queryObject = FileInterpretRecord.build()
                .toBusinessUniqueCode(businessUniqueCode)
                .toExtField1(interpretKey); // 使用 extField1 临时存储 interpretKey 用于查询
        Wrapper<FileInterpretRecord> wrapper = builderQueryEnum(
                FileInterpretRecordQueryEnum.BY_BUSINESS_UNIQUE_CODE_AND_INTERPRET_KEY,
                queryObject
        );
        return this.getOne(wrapper);
    }

    /**
     * 通过分页获取FileInterpretRecord信息实现查找缓存和数据库的方法
     * 
     * @param pager 分页查询对象
     * @param queryWrapper 查询条件对象
     * @return 返回表对象列表
     * @author suven  作者
     * date 2026-02-11 创建时间
     */
    public PageResult<FileInterpretRecord> getListByPage(Pager<FileInterpretRecord> pager, Wrapper<FileInterpretRecord> queryWrapper) {
        PageResult<FileInterpretRecord> pageVo = new PageResult<>();
        if (queryWrapper == null) {
            queryWrapper = new QueryWrapper<>();
        }
        Page<FileInterpretRecord> iPage = new Page<>(pager.getPageNo(), pager.getPageSize());
        iPage.setSearchCount(pager.isSearchCount());
        IPage<FileInterpretRecord> page = super.page(iPage, queryWrapper);
        if (ObjectTrue.isEmpty(page) || ObjectTrue.isEmpty(page.getRecords())) {
            return pageVo;
        }
        pageVo.of(page.getRecords(), pager.getPageSize(), page.getTotal());
        return pageVo;
    }

    /**
     * 通过分页获取FileInterpretRecord信息实现查找缓存和数据库的方法
     * 
     * @param queryWrapper QueryWrapper 表查询条件信息
     * @return 返回列表对象
     * @author suven  作者
     * date 2026-02-11 创建时间
     */
    public List<FileInterpretRecord> getListByQuery(Wrapper<FileInterpretRecord> queryWrapper) {
        List<FileInterpretRecord> resDtoList = new ArrayList<>();
        if (ObjectTrue.isEmpty(queryWrapper)) {
            queryWrapper = new QueryWrapper<>();
        }
        List<FileInterpretRecord> list = super.list(queryWrapper);
        if (ObjectTrue.isEmpty(list)) {
            return resDtoList;
        }
        return list;
    }

    /**
     * 通过枚举实现FileInterpretRecord不同数据库的条件查询的逻辑实现的方法
     * 
     * @param queryEnum 查询枚举
     * @param queryObject 参数对象实现
     * @return 返回查询条件对象
     * @author suven  作者
     * date 2026-02-11 创建时间
     */
    public Wrapper<FileInterpretRecord> builderQueryEnum(FileInterpretRecordQueryEnum queryEnum, Object queryObject) {
        QueryWrapper<FileInterpretRecord> queryWrapper = new QueryWrapper<>();
        if (ObjectTrue.isEmpty(queryEnum)) {
            AssertEx.error(new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
        }
        if (ObjectTrue.isEmpty(queryObject)) {
            AssertEx.error(new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
        }
        FileInterpretRecord record = FileInterpretRecord.build().clone(queryObject);
        switch (queryEnum) {
            case DESC_ID: {
                queryWrapper.eq("deleted", 0);
                queryWrapper.orderByDesc("id");
                break;
            }
            case BY_FILE_UPLOAD_ID_DESC: {
                queryWrapper.lambda()
                        .eq(FileInterpretRecord::getFileUploadId, record.getFileUploadId())
                        .eq(FileInterpretRecord::getDeleted, 0)
                        .orderByDesc(FileInterpretRecord::getId);
                break;
            }
            case BY_FILE_UPLOAD_ID_AND_STATUS_PENDING_DESC: {
                queryWrapper.lambda()
                        .eq(FileInterpretRecord::getFileUploadId, record.getFileUploadId())
                        .eq(FileInterpretRecord::getDeleted, 0)
                        .eq(FileInterpretRecord::getBusinessStatus, "PENDING")
                        .orderByDesc(FileInterpretRecord::getId);
                // 注意：如果 status 参数需要动态传入，可以通过 record 的扩展字段或添加新字段
                break;
            }
            case BY_BUSINESS_FUNCTION_ID_DESC: {
                queryWrapper.lambda()
                        .eq(FileInterpretRecord::getBusinessFunctionId, record.getBusinessFunctionId())
                        .eq(FileInterpretRecord::getDeleted, 0)
                        .orderByDesc(FileInterpretRecord::getId);
                break;
            }
            case BY_BUSINESS_UNIQUE_CODE_DESC: {
                queryWrapper.lambda()
                        .eq(FileInterpretRecord::getBusinessUniqueCode, record.getBusinessUniqueCode())
                        .eq(FileInterpretRecord::getDeleted, 0)
                        .orderByDesc(FileInterpretRecord::getId);
                break;
            }
            case BY_BUSINESS_UNIQUE_CODE_AND_INTERPRET_KEY: {
                queryWrapper.lambda()
                        .eq(FileInterpretRecord::getBusinessUniqueCode, record.getBusinessUniqueCode())
                        .eq(FileInterpretRecord::getDeleted, 0);
                // 注意：表结构中有 interpret_key 字段，但实体类可能没有，使用字符串字段名查询
                // 通过 extField1 临时传递 interpretKey 值
                if (ObjectTrue.isNotEmpty(record.getExtField1())) {
                    queryWrapper.eq("interpret_key", record.getExtField1());
                }
                break;
            }
            case BY_INTERPRET_KEY: {
                queryWrapper.eq("deleted", 0);
                // 注意：表结构中有 interpret_key 字段，但实体类可能没有，使用字符串字段名查询
                // 通过 extField1 临时传递 interpretKey 值
                if (ObjectTrue.isNotEmpty(record.getExtField1())) {
                    queryWrapper.eq("interpret_key", record.getExtField1());
                }
                break;
            }
            default:
                break;
        }
        return queryWrapper;
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
