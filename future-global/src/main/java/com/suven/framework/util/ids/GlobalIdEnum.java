package com.suven.framework.util.ids;


import com.suven.framework.util.bean.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public enum GlobalIdEnum implements IGlobalId {


        USER(1, 1, "用户"),
        OAUTH(2,1,"验证"),
        RESOURCE(3,1,"资源"),
        PAY(4,1,"支付"),
        ASSETS(5,1,"资产"),
        PAD(6,1,"设备"),
        ORDER(7,1,"订单"),
        ACTIVITY(8,1,"活动"),
        DESKTOP(9,1,"桌面"),
        CONFIG(10,1,"配置"),
        TASK(11,1,"定时任务"),
        MQ(12,1,"定时任务"),
        THREE(13,1,"第三方服务"),
        MARKET(14,1,"应用市场"),
        LOG(15,1,"日志"),
        DEPOT(16,1,"资源库")
        ;
        private int workerId;//最大到32
        private int dataCenterId;//最大到32
        private String projectName;

        private static Logger logger = LoggerFactory.getLogger(GlobalIdEnum.class);

        GlobalIdEnum(int workerId, int dataCenterId, String projectName) {
            this.workerId = workerId;
            this.dataCenterId = dataCenterId;
            this.projectName = projectName;
        }

        public int getWorkerId() {
            return workerId;
        }

        public int getDataCenterId() {
            return dataCenterId;
        }

        public String getProjectName() {
            return projectName;
        }

        private static final Map<Integer, IGlobalId> CLIENT_TYPE_ENUM_MAP = new HashMap<>();

    static {
        List<Enum> list  = EnumUtils.getEnumListByInterfaceClass(IGlobalId.class);
        if(list != null && !list.isEmpty()){
            checkExist(list);
        }



    }




    private static void checkExist(List<Enum > list){
        Set<Integer> checkCodeSet = new HashSet<>();
        if(list != null && !list.isEmpty()){
            for(Enum msg : list){
                IGlobalId imsg = (IGlobalId)msg;
                CLIENT_TYPE_ENUM_MAP.put(imsg.getWorkerId(), imsg);
                if(checkCodeSet.contains(imsg.getWorkerId())){
                    logger.error(" IGlobalId is type double,IGlobalId :[{}] workerId :[{}] and dataCenterId :[{}] is exist ",imsg,imsg.getWorkerId(),imsg.getDataCenterId());
                    throw new RuntimeException(" IGlobalId is type double, IGlobalId :" + imsg + " workerId : " + imsg.getWorkerId()+" and "+ imsg.getDataCenterId() +"is exist ");
                }
                checkCodeSet.add(imsg.getWorkerId());
            }
            checkCodeSet = null;
        }

    }
    public static IGlobalId getClientTypeEnum(long workerId) {
        return CLIENT_TYPE_ENUM_MAP.get(workerId);
    }
        
}