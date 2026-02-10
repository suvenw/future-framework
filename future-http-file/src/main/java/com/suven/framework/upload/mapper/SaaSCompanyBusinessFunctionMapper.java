package com.suven.framework.upload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suven.framework.upload.entity.SaaSCompanyBusinessFunction;
import org.apache.ibatis.annotations.Mapper;

/**
 * SaaS公司业务功能信息Mapper
 *
 * 对应表: saas_company_business_function
 */
@Mapper
public interface SaaSCompanyBusinessFunctionMapper extends BaseMapper<SaaSCompanyBusinessFunction> {
}

