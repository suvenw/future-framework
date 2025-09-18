package com.suven.framework.core.db;

import com.suven.framework.core.db.ext.DSClassAnnoExplain;

public interface DataSourceHolderService<T> {


      void masterDataSource(Class<T> entityClass);

    void slaveDataSource(Class<T> entityClass);

}
