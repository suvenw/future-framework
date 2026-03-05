package com.suven.framework.http.data.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.api.IBaseApi;
import com.suven.framework.http.api.IBeanClone;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * 基础实体类（包含日期字段）
 * <p>
 * 提供POJO Bean对应对象间相同方法属性的动态拷贝实现，但不支持builder类型的Bean对象属性拷贝；
 * 提供基本的主键id、创建时间(createDate)、修改时间(modifyDate)、创建人、更新人等基础实体类
 * </p>
 *
 * @author Joven.wang
 * @version V1.0
 * @date 2016年2月20日
 */
public class BaseByDateEntity implements IBaseApi, IBeanClone, Serializable {


    private static final long serialVersionUID = -5102197522565173276L;

    /**
     * 对应的业务主键值
     */
    @ApiDesc(value =  "对应的业务主键值")
    @ExcelIgnore
    private long id;
    /**
     * 创建时间
     */
    @ApiDesc(value =  "创建时间")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    @ExcelProperty("创建时间")
    private LocalDateTime createDate;
    /**
     * 修改时间
     */
    @ApiDesc(value =  "修改时间")
    @TableField(value = "last_modified_date", fill = FieldFill.INSERT_UPDATE)
    @ExcelProperty("修改时间")
    private LocalDateTime lastModifiedDate;
    /**
     * 创建人
     */
    @ApiDesc(value =  "创建人")
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    @ExcelProperty("创建人")
    private String createdBy;
    /**
     * 更新人登录名称
     */
    @ApiDesc(value =  "创建人登录名称")
    @TableField(value = "last_modified_by", fill = FieldFill.INSERT_UPDATE)
    @ExcelProperty("更新人登录名称")
    private String lastModifiedBy;


    /**
     * 构造函数，自动初始化创建时间和修改时间
     */
    public BaseByDateEntity() {
        super();
        this.createDate = this.lastModifiedDate = LocalDateTime.now();
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



    public IBaseApi toCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public IBaseApi toLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }


    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
