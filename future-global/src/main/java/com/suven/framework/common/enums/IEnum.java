package com.suven.framework.common.enums;

import java.io.Serializable;

/**
 * @author 作者 : suven
 * CreateDate创建时间: 2023-11-30
 * @version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 **/
public interface IEnum<ID extends Serializable,Value> {

    /**
     * 枚举索引编号,枚举的默认编号为int类型
     * @return
     */
    ID getIndex();

    /**
     * 业务自定义的枚举字符串
     * @return
     */
    Value getValue();
    /**
     * 业务自定义的枚举字符串的描述说明
     * @return
     */
    String getDesc();



}
