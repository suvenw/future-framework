package com.suven.framework.http.api;

import java.util.List;

/**
 * dao基础类
 *
 * @author dongxie
 **/
public interface IBaseExcelData {


    /**
     * excel导入数据 保存到数据的方法
     * @param list
     */
  default void saveData(List<Object> list){}



}