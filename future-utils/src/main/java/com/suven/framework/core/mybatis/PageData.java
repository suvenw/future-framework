package com.suven.framework.core.mybatis;

import java.util.List;


/**
 * @Title: PageData.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) mybatis-plus PageData 分页抽象类
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

public class PageData<T> {
    private final List<T> data;

    private final int count;

    private final int code;

    private final String msg;

    public PageData(List<T> data, int count) {
        this.data = data;
        this.count = count;
        this.code = 0;
        this.msg = "";
    }

    public int getCount() {
        return count;
    }

    public List<T> getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
