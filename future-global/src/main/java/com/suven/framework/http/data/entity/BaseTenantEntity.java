package com.suven.framework.http.data.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.suven.framework.common.enums.TbDeteledEnum;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.api.IBaseApi;
import com.suven.framework.http.api.IBeanClone;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * 基础实体类（包含租户字段）
 * <p>
 * 提供POJO Bean对应对象间相同方法属性的动态拷贝实现，但不支持builder类型的Bean对象属性拷贝；
 * 提供基本的主键id、创建时间、修改时间、租户ID等基础实体类
 * </p>
 *
 * @author Joven.wang
 * @version V1.0
 * @date 2016年2月20日
 */
public class BaseTenantEntity implements IBaseApi, IBeanClone, Serializable {


    private static final long serialVersionUID = -5102197522565173276L;

    /**
     * 对应的业务主键值
     */
    @ApiDesc(value =  "对应的业务主键值")
    @ExcelIgnore
    private Long id;
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
    @TableField(value = "modify_date", fill = FieldFill.INSERT_UPDATE)
    @ExcelProperty("修改时间")
    private LocalDateTime modifyDate;
    /**
     * 租户ID，租户主键值
     */
    @ApiDesc(value =  "租户ID,租户主键值")
    @ExcelIgnore
    private String tenantId;
    /**
     * 删除状态，默认为正常状态
     */
    @ApiDesc(value =  "租户ID,租户主键值")
    @ExcelIgnore
    private int deleted = TbDeteledEnum.NORMAL.index();


    /**
     * 构造函数，自动初始化创建时间和修改时间
     */
    public BaseTenantEntity() {
        super();
        this.modifyDate = this.createDate = LocalDateTime.now();
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

    /**
     * 获取租户ID
     *
     * @return 租户ID
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * 设置租户ID
     *
     * @param tenantId 租户ID
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 获取删除状态
     *
     * @return 删除状态
     */
    public int getDeleted() {
        return deleted;
    }

    /**
     * 设置删除状态
     *
     * @param deleted 删除状态
     */
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取修改时间
     *
     * @return 修改时间
     */
    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    /**
     * 设置修改时间
     *
     * @param modifyDate 修改时间
     */
    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }
}
