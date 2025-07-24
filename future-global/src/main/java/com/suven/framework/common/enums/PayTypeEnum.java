package com.suven.framework.common.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Joven.wang
 * @version V1.0
 * @Title: GlobalServiceInfo.java
 * @date 2019年2月20日
 * Description: TODO(说明)
 * 订单支付类型属性值枚举类, 包括:1.支付宝支付 ,2.银联支付, 3.微信支付, 4.财付通支付, 5.钱包支付
 */
public enum PayTypeEnum {

    PAY_TYPE_ALI_PAY(1,"支付宝支付"),//
    PAY_TYPE_UNIONPAY_PAY(2,"银联支付"),
    PAY_TYPE_WE_CHAT_PAY(3,"微信支付"),
    PAY_TYPE_TENGXUN_PAY(4,"财付通支付"),
    PAY_TYPE_WALLET_PAY(5,"钱包支付")
    ;

    private final int index;
    private final String desc;

    private static Map<Integer, PayTypeEnum> tbTypeMap = new HashMap<>();
    static {

        for (PayTypeEnum enums : values()) {
            tbTypeMap.put(enums.index, enums);
        }
    }

    PayTypeEnum(int index,String  desc) {
        this.index = index;
        this.desc = desc;
    }

    public int getIndex() {
        return index;
    }

    public String getDesc(){
        return desc;
    }

    public static PayTypeEnum getKey(int index) {
        return tbTypeMap.get(index);
    }

    public static String getDescByIndex(int index) {
        PayTypeEnum payTypeEnum =  tbTypeMap.get(index);
        if (null == payTypeEnum){
            return null;
        }
        return payTypeEnum.desc;
    }
}
