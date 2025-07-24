package com.suven.framework.util.validate;


import java.io.Serializable;
import java.util.Map;

public interface ValidatorService {

    /**
     * Map key 为当前平台对应的渠道的最新一条记录;
     * key: platform_channel_forceUpdate
     * @return
     */
    Map<String, Serializable> initLastAndUpdateVersionMap();

}
