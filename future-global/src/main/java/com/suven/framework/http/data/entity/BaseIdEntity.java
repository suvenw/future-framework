package com.suven.framework.http.data.entity;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.api.IBaseApi;
import com.suven.framework.http.api.IBeanClone;


/**
 * @author Joven.wang
 * @version V1.0
 * Title: BaseEnter.java
 * date 2016年2月20日
 * Description: TODO(说明)
 * 提供 POJO Bean 对应的对象间,具有相同的方法的属性值,动态拷贝实现,但不支持builder类性的Bean对象属性拷贝;
 * 提供基本的主健id, 创建时间(createDate) ,修改时间(modifyDate) 的基础实体类
 */
public class BaseIdEntity implements IBaseApi, IBeanClone {


    private static final long serialVersionUID = -5102197522565173276L;

    @ApiDesc(value =  "对应的业务主键值")
    private long id; //表主键id;



    public BaseIdEntity() {
        super();
    }



    @Override
    public Long getId() {
        return id;
    }

    public String getEsId(){
        return String.valueOf(id);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public  <T extends IBaseApi > T toId(Long id) {
        this.id = id;
        return(T) this;
    }


}
