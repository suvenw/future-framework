package com.suven.framework.core.db;


public interface DataSourceHolderService<T> {


      void masterDataSource(Class<T> entityClass);

    void slaveDataSource(Class<T> entityClass);

}
