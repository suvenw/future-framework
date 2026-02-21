package com.suven.framework.upload.service;

import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.upload.entity.CompanyBusinessFunction;
import com.suven.framework.upload.vo.request.CompanyBusinessFunctionRequestVo;

import java.util.List;

/**
 * SaaS公司业务功能信息服务接口
 *
 * 负责业务功能配置（saas_company_business_function）的完整管理能力，
 * 包括申请、更新、查询、激活/停用、删除等业务操作。
 */
public interface CompanyBusinessFunctionService {

    /**
     * 根据业务唯一码获取业务功能配置
     *
     * @param businessUniqueCode 业务唯一码
     * @return CompanyBusinessFunction 配置记录，可能为 null
     */
    CompanyBusinessFunction getByBusinessUniqueCode(String businessUniqueCode);

    /**
     * 根据ID获取业务功能配置
     *
     * @param id 业务功能ID
     * @return CompanyBusinessFunction 配置记录，可能为 null
     */
    CompanyBusinessFunction getById(long id);

    /**
     * 创建业务功能配置
     *
     * @param requestVo 申请请求VO
     * @return CompanyBusinessFunction 创建后的配置记录
     */
    CompanyBusinessFunction createBusinessFunction(CompanyBusinessFunctionRequestVo requestVo);

    /**
     * 更新业务功能配置
     *
     * @param requestVo 更新请求VO
     * @return CompanyBusinessFunction 更新后的配置记录
     */
    CompanyBusinessFunction updateBusinessFunction(CompanyBusinessFunctionRequestVo requestVo);

    /**
     * 分页查询业务功能列表
     *
     * @param pager 分页参数
     * @return PageResult<CompanyBusinessFunction> 分页结果
     */
    PageResult<CompanyBusinessFunction> pageQuery(Pager<CompanyBusinessFunctionRequestVo> pager);

    /**
     * 根据公司ID查询业务功能列表
     *
     * @param companyId 公司ID
     * @return List<CompanyBusinessFunction> 配置列表
     */
    List<CompanyBusinessFunction> getByCompanyId(String companyId);

    /**
     * 检查业务编码是否已存在
     *
     * @param companyId 公司ID
     * @param businessCode 业务编码
     * @return boolean true-已存在, false-不存在
     */
    boolean existsByBusinessCode(String companyId, String businessCode);

    /**
     * 激活业务功能
     *
     * @param id 业务功能ID
     * @return boolean 是否成功
     */
    boolean activateBusinessFunction(long id);

    /**
     * 停用业务功能
     *
     * @param id 业务功能ID
     * @return boolean 是否成功
     */
    boolean deactivateBusinessFunction(long id);

    /**
     * 删除业务功能（软删除）
     *
     * @param id 业务功能ID
     * @return boolean 是否成功
     */
    boolean deleteBusinessFunction(long id);

    /**
     * 批量删除业务功能（软删除）
     *
     * @param idList 业务功能ID列表
     * @return int 删除数量
     */
    int batchDeleteBusinessFunction(List<Long> idList);

    /**
     * 生成业务唯一码
     *
     * @param companyId 公司ID
     * @param businessCode 业务编码
     * @return String 业务唯一码
     */
    String generateBusinessUniqueCode(String companyId, String businessCode);
}
