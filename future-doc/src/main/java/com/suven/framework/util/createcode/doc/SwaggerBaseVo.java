package com.suven.framework.util.createcode.doc;


import com.suven.framework.http.api.ApiDesc;

import java.util.Date;

public class SwaggerBaseVo {

    @ApiDesc(value =  "对应的主键值")
    private long id; //表主键id;
    //	private long globalId; //全局id 用于分表分库使用
    @ApiDesc(value =  "创建时间")
    private Date createDate; //创建时间;
    @ApiDesc(value =  "修改时间")
    private Date modifyDate; //修改时间;

    public static SwaggerBaseVo build(){
        Date date = new Date();
        return new SwaggerBaseVo().setId(1).setCreateDate(date).setModifyDate(date);
    }


    public long getId() {
        return id;
    }

    public SwaggerBaseVo setId(long id) {
        this.id = id;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public SwaggerBaseVo setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public SwaggerBaseVo setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
        return this;
    }
}
