package com.suven.framework.http.data.vo;

import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.api.HttpRequestType;

/**
 * Title: HttpRequestByUserIdVo 表单类型 HttpFromRequestVo http 表单请求,参数继承对象
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) http请求头参数VO类
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
public class HttpRequestByUserIdVo extends HttpRequestByIdVo {

    @ApiDesc(value= "用户id")
    private long userId;


    public long getUserId() {
        if(userId == 0){
            userId =  getId();
        }
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * 创建实例的静态方法，支持Builder模式
     * 子类可以重写此方法返回子类类型
     * @return 当前类型的实例
     */
    public static HttpRequestByUserIdVo build() {
        return new HttpRequestByUserIdVo();
    }

    /**
     * 设置ID并返回当前对象，支持链式调用
     * 子类继承时，无需重写此方法，链式调用会自动返回子类类型
     * @param userId 用户 id
     * @return 当前对象（子类类型）
     */
    @SuppressWarnings("unchecked")
    public <T extends HttpRequestType> T toUserId(long userId) {
        this.userId = userId;
        return self();
    }
}
