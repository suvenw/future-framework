package com.suven.framework.http.data.entity;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.suven.framework.common.enums.TbStatusEnum;
import com.suven.framework.http.api.IBaseApi;
import com.suven.framework.http.api.IBeanClone;

import java.io.Serializable;


/**
 * @author Joven.wang
 * @version V1.0
 * Title: BaseEnter.java
 * date 2019年2月20日
 * Description: TODO(说明)
 * 提供 POJO Bean 对应的对象间,具有相同的方法的属性值,动态拷贝实现,但不支持builder类性的Bean对象属性拷贝;
 * 提供基本的主健id, 创建时间(createDate) ,修改时间(modifyDate),实体状态属性(status == 1 ),实体排序属性(sort == 0) 的基础实体类
 */
public class BaseStatusEntity extends BaseEntity implements IBaseApi, IBeanClone,Serializable {


    private static final long serialVersionUID = -5102197522565173272L;

    @ExcelIgnore
    private int status = TbStatusEnum.ENABLE.index(); //实体状态属性;



    public BaseStatusEntity() {
        super();
    }

    public static BaseStatusEntity build() {
        return new BaseStatusEntity();
    }



    public static BaseStatusEntity build(long id) {
        BaseStatusEntity build = build();
        build.setId(id);
        return build;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



    public  <T extends IBaseApi > T toId(Long id) {
        super.setId(id);
        return(T) this;
    }
    public <T extends IBaseApi> T toStatus(int status) {
        this.status = status;
        return (T)this;
    }




}
