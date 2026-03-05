package com.suven.framework.http.data.entity;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.suven.framework.common.enums.TbStatusEnum;
import com.suven.framework.http.api.IBaseApi;
import com.suven.framework.http.api.IBeanClone;

import java.io.Serializable;


/**
 * 基础实体类（包含状态字段）
 * <p>
 * 提供POJO Bean对应对象间相同方法属性的动态拷贝实现，但不支持builder类型的Bean对象属性拷贝；
 * 提供基本的主键id、创建时间、修改时间、实体状态属性(status)的基础实体类
 * </p>
 *
 * @author Joven.wang
 * @version V1.0
 * @date 2019年2月20日
 */
public class BaseStatusEntity extends BaseEntity implements IBaseApi, IBeanClone,Serializable {


    private static final long serialVersionUID = -5102197522565173272L;

    /**
     * 实体状态属性，默认为启用状态
     */
    @ExcelIgnore
    private int status = TbStatusEnum.ENABLE.index();



    /**
     * 构造函数
     */
    public BaseStatusEntity() {
        super();
    }

    /**
     * 创建实例的静态工厂方法
     *
     * @return BaseStatusEntity实例
     */
    public static BaseStatusEntity build() {
        return new BaseStatusEntity();
    }



    /**
     * 创建实例并设置ID的静态工厂方法
     *
     * @param id 主键ID
     * @return BaseStatusEntity实例
     */
    public static BaseStatusEntity build(long id) {
        BaseStatusEntity build = build();
        build.setId(id);
        return build;
    }



    /**
     * 获取状态
     *
     * @return 状态值
     */
    public int getStatus() {
        return status;
    }

    /**
     * 设置状态（int类型）
     *
     * @param status 状态值
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 设置状态（Integer类型）
     *
     * @param status 状态值
     */
    public void setStatus(Integer status) {
        this.status = status;
    }



    /**
     * 设置主键ID并返回当前对象（链式调用）
     *
     * @param id 主键ID
     * @param <T> 泛型类型
     * @return 当前对象
     */
    @SuppressWarnings("unchecked")
    public <T extends IBaseApi > T toId(Long id) {
        super.setId(id);
        return(T) this;
    }

    /**
     * 设置状态并返回当前对象（链式调用）
     *
     * @param status 状态值
     * @param <T> 泛型类型
     * @return 当前对象
     */
    @SuppressWarnings("unchecked")
    public <T extends IBaseApi> T toStatus(int status) {
        this.status = status;
        return (T)this;
    }





}
