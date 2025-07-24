package com.suven.framework.core.db.druid;

import com.suven.framework.core.db.DataSourceTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public  class DruidDatasourceGroup  implements Serializable {

        private String name = "master";
        private boolean target =  false;

        private DataSourceConnectionInfo master = new DataSourceConnectionInfo();
        private List<DataSourceConnectionInfo> slave = new ArrayList<>();
        private List<String> slaveModuleDatasourceNameList = new ArrayList<>();


        public DataSourceConnectionInfo getMaster() {
            return master;
        }
        public List<DataSourceConnectionInfo> getSlave() {
            return slave;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setMaster(DataSourceConnectionInfo master) {
            this.master = master;
        }

        public void setSlave(List<DataSourceConnectionInfo> slave) {
            this.slave = slave;
            addDatasourceNameList();
        }


        public List<String> getSlaveModuleDatasourceNameList() {
            return slaveModuleDatasourceNameList;
        }

        public boolean isTarget() {
            return target;
        }

        public void setTarget(boolean target) {
            this.target = target;
        }

    private void addDatasourceNameList(){
            if(null != slave) {
                for (int index = 0; index < slave.size(); index++) {
                    String moduleName = name;
                    String slaveName = DataSourceTypeEnum.SLAVE.name().toLowerCase();
                    String dataSourceSlaveName = StringUtils.join(Arrays.asList(moduleName, slaveName, index), "_");
                    slaveModuleDatasourceNameList.add(dataSourceSlaveName);
                }
            }
        }
    }