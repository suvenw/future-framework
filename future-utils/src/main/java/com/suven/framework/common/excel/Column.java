package com.suven.framework.common.excel;


import com.suven.framework.common.excel.format.Formater;
import com.suven.framework.common.excel.format.StringFormater;

public class Column {

    private Integer index = null;

    private String key;

    private String title;

    private Formater formater;

    /**
     *
     * @param key 属性名称
     * @param title 表头
     */
    public Column(String key, String title) {
        this(key, title, StringFormater.getInstance());
    }

    /**
     *
     * @param key 属性名称
     * @param title 表头
     * @param formater 格式化
     */
    public Column(String key, String title, Formater formater) {
        this.key = key;
        this.title = title;
        this.formater = formater;
    }

    public Integer getIndex() {
        if(this.index == null){
            this.index = 0;
        }
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        return this.title;
    }

    public Formater getFormater() {
        return this.formater;
    }
}
