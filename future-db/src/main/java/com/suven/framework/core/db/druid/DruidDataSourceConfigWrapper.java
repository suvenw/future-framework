/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.suven.framework.core.db.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Title: DruidDataSourceConfigWrapper.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) DruidDataSource 数据库聚群，数据库配置实现与对象初始化；；
 *  数据库连接池的相结合配置文件类型定义参数；
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
@ConfigurationProperties(prefix = DatasourceConfiguration.SPRING_DATA_SOURCE)
public class DruidDataSourceConfigWrapper implements InitializingBean {

    private Map<String,String> druid;

    private Logger logger = LoggerFactory.getLogger(DruidDataSourceConfigWrapper.class);

    public DruidDataSourceConfigWrapper(){
        logger.info("DruidDataSourceInitWrapper to spring.datasource.druid by constructor method from logger info ");
    };

    @Override
    public void afterPropertiesSet() {
        logger.warn("afterPropertiesSet druid toString[{}]", JSON.toJSONString(druid));
    }

    public DruidDataSource convertDruidDataSource(DataSourceConnectionInfo connectionInfo){
        DruidDataSource dataSource =  new DruidDataSource();
        try {
            DruidDataSourceFactory.config(dataSource, druid);
            this.initWrapper(dataSource,connectionInfo);
        }catch (Exception e){
           logger.info("DruidDataSourceConfigWrapper convertDruidDataSource error",e);
        }
        return dataSource;
    }

    public Map<String, String> getDruid() {
        return druid;
    }

    public void setDruid(Map<String, String> druid) {
       Map<String,String>  map = new LinkedHashMap<>();
        druid.forEach((k,v)->{
            String key = convert(k);
            map.put(key, v);
        });
        this.druid = map;
    }
    private void initWrapper(DruidDataSource dataSource ,DataSourceConnectionInfo dataSourceConnectionInfo) {
        if(dataSource == null || dataSourceConnectionInfo == null ){
            return;
        }
        dataSource.setUrl(dataSourceConnectionInfo.getUrl());
        dataSource.setUsername( dataSourceConnectionInfo.getUsername());
        dataSource.setPassword(dataSourceConnectionInfo.getPassword());
        String driverClass = dataSourceConnectionInfo.getDriverClassName();
        if (null != driverClass && !"".equals(driverClass)){
            dataSource.setDriverClassName(driverClass);
        }

    }
    private String convert(String key){
        if (null == key){
            return null;
        }
        String paramKey = key;
       final String chars = "-";
       if(paramKey.contains(chars)){
           paramKey = WordUtils.capitalize(key, new char[]{'-'})
                   .replaceAll(chars, "" )
                   .trim();
       }
        paramKey =  WordUtils.uncapitalize(paramKey);
        return paramKey;
    }

}
