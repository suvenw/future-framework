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
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidFilterConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidSpringAopConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidStatViewServletConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidWebStatFilterConfiguration;
import com.alibaba.fastjson.JSON;
import com.suven.framework.core.db.DataSourceTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @author lihengming [89921218@qq.com]
 * DataSourceAutoConfiguration.class,
 *         DruidDataSourceAutoConfigure.class
 */
/** EnableConfigurationProperties 使用指定配合文件生效**/
@Configuration
@ConditionalOnClass(DruidDataSource.class)
@ConditionalOnProperty(name = DatasourceConfiguration.SPRING_DATA_SOURCE_ENABLED, havingValue = "true", matchIfMissing = true)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties({DruidStatProperties.class, DataSourceProperties.class, DruidDataSourceConfigWrapper.class})
@Import({DruidSpringAopConfiguration.class,
        DruidStatViewServletConfiguration.class,
        DruidWebStatFilterConfiguration.class,
        DruidFilterConfiguration.class})
public class DruidDataSourceAutoConfig  implements  InitializingBean {


    private static final Logger logger = LoggerFactory.getLogger(DruidDataSourceAutoConfig.class);



    private ApplicationContext context;
    private DruidDataSourceConfigWrapper druidDataSource;

    public DruidDataSourceAutoConfig(ApplicationContext context, DruidDataSourceConfigWrapper druidDataSource) {
        this.context = context;
        this.druidDataSource = druidDataSource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }


    public DruidDataSource druidDataSourceInit(DataSourceConnectionInfo connectionInfo) {
      return   druidDataSource.convertDruidDataSource(connectionInfo);
    }

    public DruidDataSource initWrapper(DruidDataSource dataSource ,DataSourceConnectionInfo dataSourceConnectionInfo) {
        dataSource.setUrl(dataSourceConnectionInfo.getUrl());
        dataSource.setUsername( dataSourceConnectionInfo.getUsername());
        dataSource.setPassword(dataSourceConnectionInfo.getPassword());
        dataSource.setDriverClassName(dataSourceConnectionInfo.getDriverClassName());
        return dataSource;
    }

    @Bean(name={"dataSource"})
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        logger.info("Init DruidDynamic DruidDataSource");
        DruidDynamicDataSource dataSource = DruidDynamicDataSource.getInstance();
        AtomicBoolean isPrimary = new AtomicBoolean(true);
        String defaultTargetDataSourceName = "";

        Map<String, IDataSourceGroupProperties>  dataSourceGroupPropertiesMap = context.getBeansOfType(IDataSourceGroupProperties.class);
        if (dataSourceGroupPropertiesMap == null){
            return null;
        }
        /** k-v 模块名称-数据库组详情信息类, 检验数据库模块组名,是否重复 **/
        Map<String,DruidDatasourceGroup>  groupMap = this.checkDatasourceModule(dataSourceGroupPropertiesMap);
        for (DruidDatasourceGroup group : groupMap.values()) {

            if(group == null){
                logger.error("DatasourceGroup group is null");
                continue;
            }
            if(group.getMaster().getUrl() == null){
                logger.error("DataSourceGroupProperties.DatasourceGroup group.getMaster().getUrl()is null");
                continue;
            }
            String moduleName = group.getName();
            dataSource.initDataSourceGroup(moduleName,group);
            logger.info("Init DataSourceGroupProperties  ==:" + JSON.toJSONString(group));
            DataSourceConnectionInfo info =   group.getMaster();

            DruidDataSource masterDatasource =  this.druidDataSourceInit(info);

            /**注入到spring bean的名称生成规则；（模块文称+ master）*/
            String datasourceMasterBeanName = this.builderDatasourceBeanName(moduleName, DataSourceTypeEnum.MASTER.name().toLowerCase()) ;

            logger.info("datasourceMasterBeanName == :" + datasourceMasterBeanName);
            dataSource.putTargetDataSources(datasourceMasterBeanName,masterDatasource);


            if("".equals(defaultTargetDataSourceName) || null == defaultTargetDataSourceName){
                defaultTargetDataSourceName = datasourceMasterBeanName;
            }
            if( this.checkDefaultTargetDataSource(group,isPrimary)){
                defaultTargetDataSourceName = datasourceMasterBeanName;
            }

            List<DataSourceConnectionInfo> list = group.getSlave();
            String slaveName =  DataSourceTypeEnum.SLAVE.name().toLowerCase();
            if(null == list || list.isEmpty()){
                continue;
            }
            for(int i = 0; i< list.size();i++){
                /**注入到spring bean的名称生成规则；（模块文称+ _slave + 序列号0,1,2,3...）*/
                String datasourceSlaveBeanName = this.builderDatasourceBeanName(moduleName, slaveName,  i);
                logger.info("datasourceSlaveBeanName == :" + datasourceSlaveBeanName);

                DruidDataSource slaveDatasource =  this.druidDataSourceInit(info);
                dataSource.putTargetDataSources(datasourceSlaveBeanName,slaveDatasource);
            }
        }
        Object  defaultTargetDataSource = dataSource.getTargetDataSources(defaultTargetDataSourceName);

        dataSource.setDefaultTargetDataSource(defaultTargetDataSource);
        dataSource.setTargetDataSources();
        return dataSource;
    }


    public  String builderDatasourceBeanName(Object ... params){
        String datasourceBeanName = StringUtils.join(Arrays.asList(params), "_");
        return datasourceBeanName;
    }

    private boolean checkDefaultTargetDataSource(DruidDatasourceGroup group, AtomicBoolean isPrimary ){
        /** 设置为群组的默认数据库，有且仅有一个，并且为master数据库**/
        if(group.isTarget() && isPrimary.get() ) {
            String datasourceMasterBeanName = this.builderDatasourceBeanName(group.getName(), DataSourceTypeEnum.MASTER.name().toLowerCase()) ;
            logger.info("DataSourceGroupProperties.DatasourceGroup group.getMaster() DefaultTargetDataSource target datasourceMasterBeanName[{}] ",datasourceMasterBeanName);
            isPrimary.set(false);
            return true;
        }
        String datasourceMasterBeanName = this.builderDatasourceBeanName(group.getName(), DataSourceTypeEnum.MASTER.name().toLowerCase()) ;
        logger.error("DataSourceGroupProperties.DatasourceGroup group.getMaster() DefaultTargetDataSource target Repeat datasourceMasterBeanName[{}] ",datasourceMasterBeanName);
        return false;
    }

    private Map<String,DruidDatasourceGroup>  checkDatasourceModule(Map<String, IDataSourceGroupProperties>  dataSourceGroupPropertiesMap){
        /** k-v 模块名称-数据库组详情信息类 **/
        Map<String,DruidDatasourceGroup> druidDatasourceGroupMap = new HashMap<>(64);
        /** k-v 模块名称-数据库配置文件类名称字符串 **/
        Map<String,String> dataSourceModuleNameToPropertiesMap = new HashMap<>(16);
        /** 循环通过spring getBeansOfType 对应map 对象**/
        dataSourceGroupPropertiesMap.keySet().forEach(key->{
            IDataSourceGroupProperties dataSourceGroupProperties =   dataSourceGroupPropertiesMap.get(key);
            /** 如果配置对象 或 数据库模块对象为空时,跳过 **/
            if(null == dataSourceGroupProperties || dataSourceGroupProperties.getDatasourceGroup() == null){
                return;
            }
            /** 获取对象的精细类名称 **/
            Class<?>  dataSourceGroupClass= ClassUtils.getUserClass(dataSourceGroupProperties);
            String propertiesSimpleName = dataSourceGroupClass.getSimpleName();
            /** 过滤 spring 映射实现类 **/
            if(!key.equalsIgnoreCase(propertiesSimpleName)){
                return;
            }
            DruidDatasourceGroup druidDatasourceGroup = dataSourceGroupProperties.getDatasourceGroup();
            if(null == druidDatasourceGroup){
                return;
            }
            String dataSourceModuleName = druidDatasourceGroup.getName();

            DruidDatasourceGroup dbClass =  druidDatasourceGroupMap.get(dataSourceModuleName);
            if(dbClass != null){
                String groupPropertiesName =  dataSourceModuleNameToPropertiesMap.get(dataSourceModuleName);
                StringBuilder sb = new StringBuilder();
                sb.append(" Class DruidDataSourceAutoConfig method dataSource() ");
                sb.append("\n Pass IDataSourceGroupProperties Class convert to  [").append(propertiesSimpleName).append("]");
                sb.append(" ,DruidDatasourceGroup by dataSourceModuleName [").append(dataSourceModuleName).append("] is exist ");
                sb.append("\n from IDataSourceGroupProperties Class  convert to [").append(groupPropertiesName).append("],");
                sb.append(" DruidDatasourceGroup by  dataSourceModuleName[").append(dataSourceModuleName).append("] to Double");
                sb.append("\n  please modify datasource ModuleName value");
                throw new RuntimeException(sb.toString());
            }
            dataSourceModuleNameToPropertiesMap.put(dataSourceModuleName,propertiesSimpleName);
            druidDatasourceGroupMap.put(dataSourceModuleName,druidDatasourceGroup);

        });
        dataSourceModuleNameToPropertiesMap.clear();
        return druidDatasourceGroupMap;
    }



}
