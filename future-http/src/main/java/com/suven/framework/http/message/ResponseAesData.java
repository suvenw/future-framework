package com.suven.framework.http.message;


import com.suven.framework.common.enums.NumberEnum;
import com.suven.framework.http.api.IAesData;
import com.suven.framework.http.data.entity.BaseIdEntity;
import com.suven.framework.util.crypt.CryptUtil;
import com.suven.framework.util.random.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public  class ResponseAesData extends BaseIdEntity implements IAesData {

    private List<String> list;

    private Logger logger = LoggerFactory.getLogger(getClass());
    {

    /** aes 对数据返回 data 进行加密功能实现**/
//        String randomStr = 	RandomUtils.verifyCode(32, -1);
//        String key = randomStr.substring(0,16);
////        String value = randomStr.substring(16);
        String key =  RandomUtils.verifyCode(16,-1);
        String value = RandomUtils.verifyCode(16,0);
        String sign = CryptUtil.aesPassEncrypt( value,key);
        list = Arrays.asList(key,value,sign);
        logger.info("===== ResponseAesData ========: " +list.toString());
    }


    public String conventAesUrl(String url){
        int dataType = ParameterMessage.getRequestRemote().getDataType();
        boolean isurl = dataType == 2 || dataType == 3;
        if(isurl && null != list && list.size() == NumberEnum.THREE.id()){
            String aesEncryptKey = findAesValue();
            String sign = CryptUtil.aesPassEncrypt( url,aesEncryptKey);
            return sign;
        }
        return url;
    }


    @Override
    public String findAesKey(){
        return list.get(NumberEnum.ZERO.id());
    }
    @Override
    public String findAesValue(){
        return list.get(NumberEnum.ONE.id());
    }
    @Override
    public String findAesSign(){
        return list.get(NumberEnum.TWO.id());
    }

    public static void main(String[] args) throws Exception{
       String aesKey =  CryptUtil.aesDecrypt("BE2C4CAC293C7A1DA2BAEA806622E12685AF7DD3DB484852189D09A3F3EF9DAE588E7159F8DA52B4B23F9CDF5F565B50FEA6714AC7C64C33D537E25D4FB4AA4A" ,"LPXJLBGVAVORUOKN");
       System.out.println("=========:" +aesKey);
    }

}
