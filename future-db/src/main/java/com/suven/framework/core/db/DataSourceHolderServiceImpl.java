package com.suven.framework.core.db;

import com.alibaba.druid.support.json.JSONUtils;
import com.suven.framework.core.db.ext.DSClassAnnoExplain;
import com.suven.framework.http.api.IBaseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component

public class DataSourceHolderServiceImpl<T extends IBaseApi>  implements DataSourceHolderService<T> {

    private  static Logger logger = LoggerFactory.getLogger(DataSourceHolderServiceImpl.class);
    public   void masterDataSource( Class<T> entityClass){
        DataSourceGroup dataSourceGroup = DSClassAnnoExplain.getDataSourceGroupByClass(entityClass);
        if(dataSourceGroup == null){
            return;
        }
        dataSourceGroup.setDataType(DataSourceTypeEnum.MASTER);
        logger.info(" masterDataSource DataSourceGroup[{}]", JSONUtils.toJSONString(dataSourceGroup));
        DataSourceHolder.putDataSource(dataSourceGroup);
    }

    public   void slaveDataSource( Class<T> entityClass){
        DataSourceGroup dataSourceGroup = DSClassAnnoExplain.getDataSourceGroupByClass(entityClass);
        if(dataSourceGroup == null){
            return;
        }
        dataSourceGroup.setDataType(DataSourceTypeEnum.SLAVE);
        logger.info(" slaveDataSource DataSourceGroup[{}]", JSONUtils.toJSONString(dataSourceGroup));
        DataSourceHolder.putDataSource(dataSourceGroup);
    }

}
