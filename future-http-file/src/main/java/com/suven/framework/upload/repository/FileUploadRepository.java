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
import com.suven.framework.upload.dto.enums.FileUploadQueryEnum;
import com.suven.framework.upload.entity.FileUpload;
import com.suven.framework.upload.mapper.FileUploadMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * SaaS文件上传Repository
 * 
 * 统一封装对 saas_file_upload 表的查询逻辑，
 * 避免在 Service 中直接拼装 MyBatis 查询条件。
 * 
 * @author suven
 * @version v1.0.0
 * @date 创建时间: 2026-02-11
 */
@Repository("fileUploadRepository")
public class FileUploadRepository extends AbstractMyBatisRepository<FileUploadMapper, FileUpload> {

    /**
     * 根据ID查询文件上传记录
     * 
     * @param id 记录ID
     * @return 文件上传记录
     */
    public FileUpload getById(long id) {
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
    public List<FileUpload> getByBusinessFunctionId(long businessFunctionId) {
        if (businessFunctionId <= 0) {
            return new ArrayList<>();
        }
        Wrapper<FileUpload> wrapper = builderQueryEnum(
                FileUploadQueryEnum.BY_BUSINESS_FUNCTION_ID_DESC,
                FileUpload.build().toBusinessFunctionId(businessFunctionId)
        );
        return this.getListByQuery(wrapper);
    }

    /**
     * 根据业务唯一码查询文件上传记录
     * 
     * @param businessUniqueCode 业务唯一码
     * @return 文件上传记录列表
     */
    public List<FileUpload> getByBusinessUniqueCode(String businessUniqueCode) {
        if (ObjectTrue.isEmpty(businessUniqueCode)) {
            return new ArrayList<>();
        }
        Wrapper<FileUpload> wrapper = builderQueryEnum(
                FileUploadQueryEnum.BY_BUSINESS_UNIQUE_CODE_DESC,
                FileUpload.build().toBusinessUniqueCode(businessUniqueCode)
        );
        return this.getListByQuery(wrapper);
    }

    /**
     * 根据解释标识查询文件上传记录
     * 
     * @param interpretFlag 是否需要解释
     * @param pager 分页参数
     * @return 分页结果
     */
    public PageResult<FileUpload> getByInterpretFlag(int interpretFlag, Pager<FileUpload> pager) {
        Wrapper<FileUpload> wrapper = builderQueryEnum(
                FileUploadQueryEnum.BY_INTERPRET_FLAG_DESC,
                FileUpload.build().toInterpretFlag(interpretFlag)
        );
        return this.getListByPage(pager, wrapper);
    }

    /**
     * 根据解释状态查询文件上传记录
     * 
     * @param interpretStatus 解释状态
     * @param pager 分页参数
     * @return 分页结果
     */
    public PageResult<FileUpload> getByInterpretStatus(String interpretStatus, Pager<FileUpload> pager) {
        if (ObjectTrue.isEmpty(interpretStatus)) {
            return new PageResult<>();
        }
        Wrapper<FileUpload> wrapper = builderQueryEnum(
                FileUploadQueryEnum.BY_INTERPRET_STATUS_DESC,
                FileUpload.build().toInterpretStatus(interpretStatus)
        );
        return this.getListByPage(pager, wrapper);
    }

    /**
     * 根据处理状态查询文件上传记录
     * 
     * @param status 处理状态
     * @param pager 分页参数
     * @return 分页结果
     */
    public PageResult<FileUpload> getByStatus(String status, Pager<FileUpload> pager) {
        if (ObjectTrue.isEmpty(status)) {
            return new PageResult<>();
        }
        Wrapper<FileUpload> wrapper = builderQueryEnum(
                FileUploadQueryEnum.BY_STATUS_DESC,
                FileUpload.build().toStatus(status)
        );
        return this.getListByPage(pager, wrapper);
    }

    /**
     * 根据上传批次号查询文件上传记录
     * 
     * @param uploadBatchNo 上传批次号
     * @return 文件上传记录列表
     */
    public List<FileUpload> getByUploadBatchNo(String uploadBatchNo) {
        if (ObjectTrue.isEmpty(uploadBatchNo)) {
            return new ArrayList<>();
        }
        Wrapper<FileUpload> wrapper = builderQueryEnum(
                FileUploadQueryEnum.BY_UPLOAD_BATCH_NO_DESC,
                FileUpload.build().toUploadBatchNo(uploadBatchNo)
        );
        return this.getListByQuery(wrapper);
    }

    /**
     * 根据业务唯一码和解释标识查询文件上传记录
     * 
     * @param businessUniqueCode 业务唯一码
     * @param interpretKey 解释标识
     * @return 文件上传记录
     */
    public FileUpload getByBusinessUniqueCodeAndInterpretKey(String businessUniqueCode, String interpretKey) {
        if (ObjectTrue.isEmpty(businessUniqueCode) || ObjectTrue.isEmpty(interpretKey)) {
            return null;
        }
        FileUpload queryObject = FileUpload.build()
                .toBusinessUniqueCode(businessUniqueCode)
                .toInterpretKey(interpretKey);
        Wrapper<FileUpload> wrapper = builderQueryEnum(
                FileUploadQueryEnum.BY_BUSINESS_UNIQUE_CODE_AND_INTERPRET_KEY,
                queryObject
        );
        return this.getOne(wrapper);
    }

    /**
     * 通过分页获取FileUpload信息实现查找缓存和数据库的方法
     * 
     * @param pager 分页查询对象
     * @param queryWrapper 查询条件对象
     * @return 返回表对象列表
     * @author suven  作者
     * date 2026-02-11 创建时间
     */
    public PageResult<FileUpload> getListByPage(Pager<FileUpload> pager, Wrapper<FileUpload> queryWrapper) {
        PageResult<FileUpload> pageVo = new PageResult<>();
        if (queryWrapper == null) {
            queryWrapper = new QueryWrapper<>();
        }
        Page<FileUpload> iPage = new Page<>(pager.getPageNo(), pager.getPageSize());
        iPage.setSearchCount(pager.isSearchCount());
        IPage<FileUpload> page = super.page(iPage, queryWrapper);
        if (ObjectTrue.isEmpty(page) || ObjectTrue.isEmpty(page.getRecords())) {
            return pageVo;
        }
        pageVo.of(page.getRecords(), pager.getPageSize(), page.getTotal());
        return pageVo;
    }

    /**
     * 通过分页获取FileUpload信息实现查找缓存和数据库的方法
     * 
     * @param queryWrapper QueryWrapper 表查询条件信息
     * @return 返回列表对象
     * @author suven  作者
     * date 2026-02-11 创建时间
     */
    public List<FileUpload> getListByQuery(Wrapper<FileUpload> queryWrapper) {
        List<FileUpload> resDtoList = new ArrayList<>();
        if (ObjectTrue.isEmpty(queryWrapper)) {
            queryWrapper = new QueryWrapper<>();
        }
        List<FileUpload> list = super.list(queryWrapper);
        if (ObjectTrue.isEmpty(list)) {
            return resDtoList;
        }
        return list;
    }

    /**
     * 通过枚举实现FileUpload不同数据库的条件查询的逻辑实现的方法
     * 
     * @param queryEnum 查询枚举
     * @param queryObject 参数对象实现
     * @return 返回查询条件对象
     * @author suven  作者
     * date 2026-02-11 创建时间
     */
    public Wrapper<FileUpload> builderQueryEnum(FileUploadQueryEnum queryEnum, Object queryObject) {
        QueryWrapper<FileUpload> queryWrapper = new QueryWrapper<>();
        if (ObjectTrue.isEmpty(queryEnum)) {
            AssertEx.error(new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
        }
        if (ObjectTrue.isEmpty(queryObject)) {
            AssertEx.error(new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
        }
        FileUpload record = FileUpload.build().clone(queryObject);
        switch (queryEnum) {
            case DESC_ID: {
                queryWrapper.eq("deleted", 0);
                queryWrapper.orderByDesc("id");
                break;
            }
            case BY_BUSINESS_FUNCTION_ID_DESC: {
                queryWrapper.lambda()
                        .eq(FileUpload::getBusinessFunctionId, record.getBusinessFunctionId())
                        .eq(FileUpload::getDeleted, 0)
                        .orderByDesc(FileUpload::getId);
                break;
            }
            case BY_BUSINESS_UNIQUE_CODE_DESC: {
                queryWrapper.lambda()
                        .eq(FileUpload::getBusinessUniqueCode, record.getBusinessUniqueCode())
                        .eq(FileUpload::getDeleted, 0)
                        .orderByDesc(FileUpload::getId);
                break;
            }
            case BY_UPLOAD_BATCH_NO_DESC: {
                queryWrapper.lambda()
                        .eq(FileUpload::getUploadBatchNo, record.getUploadBatchNo())
                        .eq(FileUpload::getDeleted, 0)
                        .orderByDesc(FileUpload::getId);
                break;
            }
            case BY_INTERPRET_FLAG_DESC: {
                queryWrapper.lambda()
                        .eq(FileUpload::getInterpretFlag, record.getInterpretFlag())
                        .eq(FileUpload::getDeleted, 0)
                        .orderByDesc(FileUpload::getId);
                break;
            }
            case BY_INTERPRET_STATUS_DESC: {
                queryWrapper.lambda()
                        .eq(FileUpload::getInterpretStatus, record.getInterpretStatus())
                        .eq(FileUpload::getDeleted, 0)
                        .orderByDesc(FileUpload::getId);
                break;
            }
            case BY_STATUS_DESC: {
                queryWrapper.lambda()
                        .eq(FileUpload::getStatus, record.getStatus())
                        .eq(FileUpload::getDeleted, 0)
                        .orderByDesc(FileUpload::getId);
                break;
            }
            case BY_BUSINESS_UNIQUE_CODE_AND_INTERPRET_KEY: {
                queryWrapper.lambda()
                        .eq(FileUpload::getBusinessUniqueCode, record.getBusinessUniqueCode())
                        .eq(FileUpload::getInterpretKey, record.getInterpretKey())
                        .eq(FileUpload::getDeleted, 0);
                break;
            }
            default:
                break;
        }
        return queryWrapper;
    }

    /**
     * 更新文件上传记录
     * 
     * @param record 文件上传记录
     * @return 是否成功
     */
    public boolean updateFileUpload(FileUpload record) {
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
    public boolean updateBatchFileUploads(List<FileUpload> records) {
        if (ObjectTrue.isEmpty(records)) {
            return false;
        }
        return this.updateBatchById(records);
    }
}
