package com.suven.framework.core.db.druid;

import com.suven.framework.core.db.DataSourceTypeEnum;

import java.io.Serializable;
import java.util.ArrayList;
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
                    String dataSourceSlaveName = getDatasourceName(moduleName, slaveName, index);
                    slaveModuleDatasourceNameList.add(dataSourceSlaveName);
                }
            }
        }

        private  static String getDatasourceName(String moduleName, String slaveName, int index){
            StringBuilder  sb = new StringBuilder();
            String split = "_";
            sb.append(moduleName).append(split).append(slaveName).append(split).append(index);
            return sb.toString();
        }
    }
