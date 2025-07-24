package com.suven.framework.common.excel.format;

public class StatusFormater implements Formater {

    @Override
    public String format(Object o) {
        if(o == null){
            return null;
        }
        return o.toString().equals("1") ? "是" : "否";
    }

}
