package com.suven.framework.common.excel.format;

public class StringFormater implements Formater {

    private static volatile StringFormater instance;

    public static StringFormater getInstance() {
        if (instance == null) {
            synchronized (StringFormater.class) {
                if (instance == null) {
                    instance = new StringFormater();
                }
            }
        }
        return instance;
    }

    @Override
    public String format(Object o) {
        if(o == null){
            return "";
        }
        return String.valueOf(o);
    }
}