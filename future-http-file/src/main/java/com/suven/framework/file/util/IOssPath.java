package com.suven.framework.file.util;


/**
 * 文件业务类型实现枚举
 */
public interface IOssPath {

    /** 业务类型下标,为整型数据 **/
   default int id() {
        return 0;
    }
    /** 业务类型文件名称,为字符数据 **/
    String value();
}
