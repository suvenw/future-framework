package com.suven.framework.common.excel.format;

import java.text.SimpleDateFormat;

public class DateFormater implements Formater {

    private String pattern;

    public DateFormater(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String format(Object o) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(this.pattern);
        return dateFormat.format(o);
    }
}
