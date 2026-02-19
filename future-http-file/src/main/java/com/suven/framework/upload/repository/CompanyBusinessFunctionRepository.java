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
import com.suven.framework.upload.dto.enums.CompanyBusinessFunctionQueryEnum;
import com.suven.framework.upload.entity.CompanyBusinessFunction;
import com.suven.framework.upload.mapper.CompanyBusinessFunctionMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * SaaS公司业务功能信息Repository
 *
 * 统一封装对 saas_company_business_function 表的查询逻辑，
 * 避免在 Service 中直接拼装 MyBatis 查询条件。
 *
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-11
 */
@Repository("companyBusinessFunctionRepository")
public class CompanyBusinessFunctionRepository extends AbstractMyBatisRepository<CompanyBusinessFunctionMapper, CompanyBusinessFunction> {

    /**
     * 根据ID查询配置
     *
     * @param id 记录ID
     * @return 配置记录
     */
    public CompanyBusinessFunction getById(long id) {
        if (id <= 0) {
            return null;
        }
        return this.baseMapper.selectById(id);
    }

    /**
     * 根据业务唯一码查询配置
     *
     * @param businessUniqueCode 业务唯一码
     * @return 配置记录，可能为 null
     */
    public CompanyBusinessFunction getByBusinessUniqueCode(String businessUniqueCode) {
        if (ObjectTrue.isEmpty(businessUniqueCode)) {
            return null;
        }
        Wrapper<CompanyBusinessFunction> wrapper = builderQueryEnum(
                CompanyBusinessFunctionQueryEnum.BY_BUSINESS_UNIQUE_CODE,
                CompanyBusinessFunction.build().toBusinessUniqueCode(businessUniqueCode)
        );
        return this.getOne(wrapper);
    }

    /**
     * 根据公司ID查询配置列表
     *
     * @param companyId 公司ID
     * @return 配置记录列表
     */
    public List<CompanyBusinessFunction> getByCompanyId(String companyId) {
        if (ObjectTrue.isEmpty(companyId)) {
            return new ArrayList<>();
        }
        Wrapper<CompanyBusinessFunction> wrapper = builderQueryEnum(
                CompanyBusinessFunctionQueryEnum.BY_COMPANY_ID_DESC,
                CompanyBusinessFunction.build().toCompanyId(companyId)
        );
        return this.getListByQuery(wrapper);
    }

    /**
     * 根据平台类型查询配置列表
     *
     * @param platformType 平台类型
     * @return 配置记录列表
     */
    public List<CompanyBusinessFunction> getByPlatformType(String platformType) {
        if (ObjectTrue.isEmpty(platformType)) {
            return new ArrayList<>();
        }
        Wrapper<CompanyBusinessFunction> wrapper = builderQueryEnum(
                CompanyBusinessFunctionQueryEnum.BY_PLATFORM_TYPE_DESC,
                CompanyBusinessFunction.build().toPlatformType(platformType)
        );
        return this.getListByQuery(wrapper);
    }

    /**
     * 根据功能类型查询配置列表
     *
     * @param functionType 功能类型
     * @return 配置记录列表
     */
    public List<CompanyBusinessFunction> getByFunctionType(String functionType) {
        if (ObjectTrue.isEmpty(functionType)) {
            return new ArrayList<>();
        }
        Wrapper<CompanyBusinessFunction> wrapper = builderQueryEnum(
                CompanyBusinessFunctionQueryEnum.BY_FUNCTION_TYPE_DESC,
                CompanyBusinessFunction.build().toFunctionType(functionType)
        );
        return this.getListByQuery(wrapper);
    }

    /**
     * 根据状态查询配置列表
     *
     * @param status 状态
     * @param pager 分页参数
     * @return 分页结果
     */
    public PageResult<CompanyBusinessFunction> getByStatus(String status, Pager<CompanyBusinessFunction> pager) {
        if (ObjectTrue.isEmpty(status)) {
            return new PageResult<>();
        }
        Wrapper<CompanyBusinessFunction> wrapper = builderQueryEnum(
                CompanyBusinessFunctionQueryEnum.BY_STATUS_DESC,
                CompanyBusinessFunction.build().toStatus(status)
        );
        return this.getListByPage(pager, wrapper);
    }

    /**
     * 通过分页获取CompanyBusinessFunction信息实现查找缓存和数据库的方法
     * 
     * @param pager 分页查询对象
     * @param queryWrapper 查询条件对象
     * @return 返回表对象列表
     * @author suven  作者
     * date 2026-02-11 创建时间
     */
    public PageResult<CompanyBusinessFunction> getListByPage(Pager<CompanyBusinessFunction> pager, Wrapper<CompanyBusinessFunction> queryWrapper) {
        PageResult<CompanyBusinessFunction> pageVo = new PageResult<>();
        if (queryWrapper == null) {
            queryWrapper = new QueryWrapper<>();
        }
        Page<CompanyBusinessFunction> iPage = new Page<>(pager.getPageNo(), pager.getPageSize());
        iPage.setSearchCount(pager.isSearchCount());
        IPage<CompanyBusinessFunction> page = super.page(iPage, queryWrapper);
        if (ObjectTrue.isEmpty(page) || ObjectTrue.isEmpty(page.getRecords())) {
            return pageVo;
        }
        pageVo.of(page.getRecords(), pager.getPageSize(), page.getTotal());
        return pageVo;
    }

    /**
     * 通过分页获取CompanyBusinessFunction信息实现查找缓存和数据库的方法
     * 
     * @param queryWrapper QueryWrapper 表查询条件信息
     * @return 返回列表对象
     * @author suven  作者
     * date 2026-02-11 创建时间
     */
    public List<CompanyBusinessFunction> getListByQuery(Wrapper<CompanyBusinessFunction> queryWrapper) {
        List<CompanyBusinessFunction> resDtoList = new ArrayList<>();
        if (ObjectTrue.isEmpty(queryWrapper)) {
            queryWrapper = new QueryWrapper<>();
        }
        List<CompanyBusinessFunction> list = super.list(queryWrapper);
        if (ObjectTrue.isEmpty(list)) {
            return resDtoList;
        }
        return list;
    }

    /**
     * 通过枚举实现CompanyBusinessFunction不同数据库的条件查询的逻辑实现的方法
     * 
     * @param queryEnum 查询枚举
     * @param queryObject 参数对象实现
     * @return 返回查询条件对象
     * @author suven  作者
     * date 2026-02-11 创建时间
     */
    public Wrapper<CompanyBusinessFunction> builderQueryEnum(CompanyBusinessFunctionQueryEnum queryEnum, Object queryObject) {
        QueryWrapper<CompanyBusinessFunction> queryWrapper = new QueryWrapper<>();
        if (ObjectTrue.isEmpty(queryEnum)) {
            AssertEx.error(new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
        }
        if (ObjectTrue.isEmpty(queryObject)) {
            AssertEx.error(new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
        }
        CompanyBusinessFunction record = CompanyBusinessFunction.build().clone(queryObject);
        switch (queryEnum) {
            case DESC_ID: {
        queryWrapper.eq("deleted", 0);
                queryWrapper.orderByDesc("id");
                break;
            }
            case BY_BUSINESS_UNIQUE_CODE: {
                queryWrapper.lambda()
                        .eq(CompanyBusinessFunction::getBusinessUniqueCode, record.getBusinessUniqueCode())
                        .eq(CompanyBusinessFunction::getDeleted, 0);
                break;
            }
            case BY_COMPANY_ID_DESC: {
                queryWrapper.lambda()
                        .eq(CompanyBusinessFunction::getCompanyId, record.getCompanyId())
                        .eq(CompanyBusinessFunction::getDeleted, 0)
                        .orderByDesc(CompanyBusinessFunction::getId);
                break;
            }
            case BY_PLATFORM_TYPE_DESC: {
                queryWrapper.lambda()
                        .eq(CompanyBusinessFunction::getPlatformType, record.getPlatformType())
                        .eq(CompanyBusinessFunction::getDeleted, 0)
                        .orderByDesc(CompanyBusinessFunction::getId);
                break;
            }
            case BY_FUNCTION_TYPE_DESC: {
                queryWrapper.lambda()
                        .eq(CompanyBusinessFunction::getFunctionType, record.getFunctionType())
                        .eq(CompanyBusinessFunction::getDeleted, 0)
                        .orderByDesc(CompanyBusinessFunction::getId);
                break;
            }
            case BY_STATUS_DESC: {
                queryWrapper.lambda()
                        .eq(CompanyBusinessFunction::getStatus, record.getStatus())
                        .eq(CompanyBusinessFunction::getDeleted, 0)
                        .orderByDesc(CompanyBusinessFunction::getId);
                break;
            }
            default:
                break;
        }
        return queryWrapper;
    }
}
