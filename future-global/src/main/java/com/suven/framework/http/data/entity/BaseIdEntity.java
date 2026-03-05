package com.suven.framework.http.data.entity;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.api.IBaseApi;
import com.suven.framework.http.api.IBeanClone;

import java.io.Serializable;


/**
 * 基础实体类（仅包含主键ID）
 * <p>
 * 提供POJO Bean对应对象间相同方法属性的动态拷贝实现，但不支持builder类型的Bean对象属性拷贝；
 * 提供基本的主键id的基础实体类
 * </p>
 *
 * @author Joven.wang
 * @version V1.0
 * @date 2016年2月20日
 */
public class BaseIdEntity implements IBaseApi, IBeanClone, Serializable {


    private static final long serialVersionUID = -5102197522565173276L;

    /**
     * 对应的业务主键值
     */
    @ApiDesc(value =  "对应的业务主键值")
    private long id;



    /**
     * 构造函数
     */
    public BaseIdEntity() {
        super();
    }



    /**
     * 获取主键ID
     *
     * @return 主键ID
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * 获取ES文档ID
     *
     * @return 主键ID的字符串形式
     */
    public String getEsId(){
        return String.valueOf(id);
    }

    /**
     * 设置主键ID（long类型）
     *
     * @param id 主键ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * 设置主键ID（Long类型）
     *
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
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
        this.id = id;
        return(T) this;
    }


}
