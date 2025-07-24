package com.suven.framework.common.excel;



import com.suven.framework.common.excel.format.Formater;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ColumnBox {

    private final List<Column> columnList = new ArrayList<>();

    private ColumnBox() {}

    public static ColumnBox create(){
        return new ColumnBox();
    }

    /**
     *
     * @param key 属性名称
     * @param title 表头
     */
    public ColumnBox add(String key, String title) {
        return this.add(new Column(key, title));
    }

    /**
     *
     * @param key 属性名称
     * @param title 表头
     * @param formater 格式化
     */
    public ColumnBox add(String key, String title, Formater formater) {
        return this.add(new Column(key, title, formater));
    }

    public ColumnBox add(Column column){
        column.setIndex(columnList.size());
        columnList.add(column);
        return this;
    }

    public ColumnBox expand(ColumnBox columnBox){
        columnBox.forEach(this::add);
        return this;
    }

    public void forEach(Consumer<Column> consumer){
        columnList.forEach(consumer);
    }

}
