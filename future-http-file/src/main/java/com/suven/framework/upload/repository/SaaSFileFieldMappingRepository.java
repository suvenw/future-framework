package com.suven.framework.upload.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.AssertEx;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.mybatis.AbstractMyBatisRepository;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.upload.dto.enums.SaaSFileFieldMappingQueryEnum;
import com.suven.framework.upload.entity.SaaSFileFieldMapping;
import com.suven.framework.upload.mapper.SaaSFileFieldMappingMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * SaaS 文件字段映射 Repository
 *
 * 统一封装对 saas_file_field_mapping 表的查询逻辑，
 * 避免在 Service 中直接拼装 MyBatis 查询条件。
 *
 * @author suven
 * @version v1.0.0
 * date 创建时间: 2026-02-11
 */
@Repository("saaSFileFieldMappingRepository")
public class SaaSFileFieldMappingRepository extends AbstractMyBatisRepository<SaaSFileFieldMappingMapper, SaaSFileFieldMapping> {

    /**
     * 根据操作记录 ID 查询字段映射列表
     *
     * @param operationRecordId 操作记录 ID
     * @return 字段映射列表（按 sort_order 升序）
     */
    public List<SaaSFileFieldMapping> getByOperationRecordId(long operationRecordId) {
        if (operationRecordId <= 0) {
            return new ArrayList<>();
        }
        Wrapper<SaaSFileFieldMapping> wrapper = builderQueryEnum(
                SaaSFileFieldMappingQueryEnum.BY_OPERATION_ID_ORDER_BY_SORT,
                SaaSFileFieldMapping.build().toOperationRecordId(operationRecordId)
        );
        return this.list(wrapper);
    }

    /**
     * 通过枚举实现不同查询条件的统一构建
     *
     * @param queryEnum   查询枚举
     * @param queryObject 查询参数对象（可为实体或 VO）
     * @return Wrapper 查询条件
     */
    public Wrapper<SaaSFileFieldMapping> builderQueryEnum(SaaSFileFieldMappingQueryEnum queryEnum, Object queryObject) {
        QueryWrapper<SaaSFileFieldMapping> queryWrapper = new QueryWrapper<>();
        if (ObjectTrue.isEmpty(queryEnum)) {
            AssertEx.error(new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
        }
        if (ObjectTrue.isEmpty(queryObject)) {
            AssertEx.error(new SystemRuntimeException(SysResultCodeEnum.SYS_RESPONSE_QUERY_IS_NULL));
        }
        SaaSFileFieldMapping mapping = SaaSFileFieldMapping.build().clone(queryObject);
        switch (queryEnum) {
            case BY_OPERATION_ID_ORDER_BY_SORT: {
                queryWrapper.eq("operation_record_id", mapping.getOperationRecordId());
                queryWrapper.eq("deleted", 0);
                queryWrapper.orderByAsc("sort_order");
                break;
            }
            default:
                break;
        }
        return queryWrapper;
    }
}

