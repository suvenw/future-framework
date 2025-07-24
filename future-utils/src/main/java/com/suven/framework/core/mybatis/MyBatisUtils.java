package com.suven.framework.core.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suven.framework.common.enums.QueryTypeEnum;

/**
 * @Author dongxie
 * @Description //TODO
 * @CreateDate 2019-12-12  17:03
 **/
public class MyBatisUtils {


    /**
     * 根据规则走不同的查询
     * @param queryWrapper QueryWrapper
     * @param name         字段名字
     * @param queryType    查询规则
     * @param value        查询条件值
     */
    public static void addEasyQuery(QueryWrapper<?> queryWrapper, String name, QueryTypeEnum queryType, Object value) {
        if (value == null || queryType == null) {
            return;
        }
        switch (queryType) {
            case EQ:
                queryWrapper.eq(name, value);
                break;
            case NOT_EQ:
                queryWrapper.notIn(name, value);
                break;
            case GT:
                queryWrapper.gt(name, value);
                break;
            case GT_EQ:
                queryWrapper.ge(name, value);
                break;
            case LT:
                queryWrapper.lt(name, value);
                break;
            case LT_EQ:
                queryWrapper.le(name, value);
                break;
            case LIKE:
                queryWrapper.like(name, value);
                break;
            case LEFT_LIKE:
                queryWrapper.likeLeft(name, value);
                break;
            case RIGHT_LIKE:
                queryWrapper.likeRight(name, value);
                break;
            default:
//                log.info("--查询规则未匹配到---");
                break;
        }
    }
}
