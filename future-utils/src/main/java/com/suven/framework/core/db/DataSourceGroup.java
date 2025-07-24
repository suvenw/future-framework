package com.suven.framework.core.db;

import com.suven.framework.core.db.druid.DruidDatasourceGroup;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by joven on 16/8/30.
 * 封装每个项目的数据源对象；
 */

/**
 * @Title: DataDruidConfig.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) 封装每个业务模块的的数据源组成bean实现类；包括信息一主 多从(mater-slave)数据库组或者单个主数据库的对象;
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

public class DataSourceGroup {

    private String groupName; //缓存对应的模块数据组
    private String masterSources; //master key
    private List<String> slaveSources = new ArrayList<>(); //slave集合信息
    private DataSourceTypeEnum dataType = DataSourceTypeEnum.MASTER; // 使用数据库类型: MASTER 或 SLAVE;
    private String dataClient; //当前数据库的值

    public DataSourceGroup(){}


    public DataSourceGroup(String groupName, DataSourceTypeEnum dataType) {
        if(null != groupName){
            this.groupName = groupName;
        }
        if(dataType != null){
            this.dataType = dataType;
        }

    }

    public static DataSourceGroup build(DruidDatasourceGroup group){
        if(group == null){
            return null;
        }
        String moduleName = group.getName();
        String datasourceMasterName = DataSourceTypeEnum.MASTER.name().toLowerCase();
        String datasourceMasterBeanName = StringUtils.join(Arrays.asList(moduleName, datasourceMasterName), "_");

        DataSourceGroup dGroup =  new DataSourceGroup();
        dGroup.setGroupName(moduleName);
        dGroup.setMasterSources(datasourceMasterBeanName);
        if(group.getSlave() != null && !group.getSlave().isEmpty()){
            dGroup.setSlaveSources(group.getSlaveModuleDatasourceNameList());
        }

       return dGroup;
    }
    public String getMasterSources() {
        return masterSources;
    }

    public void setMasterSources(String masterSources) {
        this.masterSources = masterSources;
    }

    public List<String> getSlaveSources() {
        return slaveSources;
    }

    public void setSlaveSources(List<String> slaveSources) {
        this.slaveSources = slaveSources;
    }

    public void setSlaveSources(String slaveKey) {
        if(this.slaveSources == null){
            slaveSources = new ArrayList<>();
        }
        this.slaveSources.add(slaveKey);
    }


    public String getGroupName() {
        return groupName;
    }

    public DataSourceGroup setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public DataSourceTypeEnum getDataType() {
        if(dataType == null){
            dataType = DataSourceTypeEnum.MASTER;
        }
        return dataType;
    }

    public DataSourceGroup setDataType(DataSourceTypeEnum dataType) {
        if(null != dataType){
            this.dataType = dataType;
        }
        return this;
    }

    public String getDataClient() {
        return dataClient;
    }

    public void setDataClient(String dataClient) {
        this.dataClient = dataClient;
    }


}
