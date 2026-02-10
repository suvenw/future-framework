package com.suven.framework.upload.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.mybatis.AbstractMyBatisRepository;
import com.suven.framework.upload.entity.SaaSCompanyBusinessFunction;
import com.suven.framework.upload.mapper.SaaSCompanyBusinessFunctionMapper;
import org.springframework.stereotype.Repository;

/**
 * SaaS公司业务功能信息Repository
 *
 * 负责 saas_company_business_function 表的读写与简单查询封装。
 */
@Repository("saaSCompanyBusinessFunctionRepository")
public class SaaSCompanyBusinessFunctionRepository extends AbstractMyBatisRepository<SaaSCompanyBusinessFunctionMapper, SaaSCompanyBusinessFunction> {

    /**
     * 根据业务唯一码查询配置
     *
     * @param businessUniqueCode 业务唯一码
     * @return SaaSCompanyBusinessFunction 配置记录，可能为 null
     */
    public SaaSCompanyBusinessFunction getByBusinessUniqueCode(String businessUniqueCode) {
        if (ObjectTrue.isEmpty(businessUniqueCode)) {
            return null;
        }
        QueryWrapper<SaaSCompanyBusinessFunction> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_unique_code", businessUniqueCode);
        queryWrapper.eq("deleted", 0);
        queryWrapper.last("limit 1");
        return this.getOne(queryWrapper);
    }
}

