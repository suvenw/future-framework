/**
 * Copyright(c)  XXX-Inc.All Rights Reserved.
 */
package com.suven.framework.http.validator;

import com.suven.framework.common.enums.TokenMsgCodeEnum;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.redis.RedisClientServer;
import com.suven.framework.core.redis.RedisKeys;
import com.suven.framework.core.redis.RedisShortKeyUtil;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.interceptor.HandlerValidator;
import com.suven.framework.http.message.HttpRequestPostMessage;
import com.suven.framework.http.message.ParameterMessage;
import com.suven.framework.util.tool.TopStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.suven.framework.common.enums.SysResultCodeEnum.*;


/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author suven.wang@ XXX.net
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: (说明) token 校验实现类
 */


@Component("userTokenHandlerValidator")
public class UserTokenHandlerValidator extends ValidatorCache<Boolean> implements HandlerValidator {
    private Logger logger = LoggerFactory.getLogger(getClass());
   @Autowired(required = false)
    private RedisClientServer redisClientServer;


    private final static int DURATION_DEFAULT = 3;

//    public UserTokenHandlerValidator(RedisClient redisClient){
//        this.redisClient = redisClient;
//    }
    /**
     *
     * 抽象设置临时缓存时间值
     * @return
     */
    @Override
    protected  int refreshAfterWriteTimeSeconds(){

        return LOADING_CACHE_DEFAULT_MINUTES * DURATION_DEFAULT;
    }





    @Override
    protected Boolean validatorDataSource() {
        try {
//            HttpRequestPostMessage message = ParameterMessage.getRequestMessage();
//            String codeKey = RedisShortKeyUtil.formatKey(RedisKeys.OAUTH_USER_ID_TOKEN_MAP,message.getUserId());
//            long expire = System.currentTimeMillis();
//            Map<String, String> result = redisClusterServer.getMapCacheAndDelExpire(codeKey, expire);
////            String result = redisClusterServer.get(codeKey);
//            logger.info("------validatorCache from redis ,codeKey:[{}], result:[{}] , message.getAccessToken[{}]",codeKey,result,message.getAccessToken());
//            if (null != result && result.containsKey(message.getAccessToken())) {
//                return true;
//            }
            return true;
        }catch (Exception e){
            logger.error("UserTokenHandlerValidator validator Exception:[{}] ",e);
        }
        return false;
    }

    @Override
    protected String cacheKey() {
        HttpRequestPostMessage message = ParameterMessage.getRequestMessage();
        String cacheKey = TopStringUtils.toStringBuilder(ValidatorEnum.TOKEN.name(),message.getUserId()+"",
                message.getAccessToken()).toString();
        return cacheKey;
    }

    @Override
    protected boolean cacheValidatorValue(Boolean validatorValue ){

        if(null == validatorValue){
            return validatorDataSource();
        }
        return false;
    }

    /**
     * 验证结果数据,成功返回御前, 失败返回false
     *
     * @param googleCache
     * @param parameter
     * @return
     */
    @Override
    public boolean validatorFromData(boolean googleCache, Object parameter) {
            boolean validator = false;
            if (googleCache){
                validator =  validatorCache();
            }else {
                validator = validatorDataSource();
            }
            if (validator){//正常登陆,返回true
                return true;
            }//异常,抛出异常提示
            getTokenError();
            return false;
    }

    public void getTokenError(){
        HttpRequestPostMessage message = ParameterMessage.getRequestMessage();
        String codeKey = RedisShortKeyUtil.formatKey(RedisKeys.USER_LOGIN_ERROR_CHECK,message.getAccessToken());
        String result = redisClientServer.get(codeKey);
        logger.error("======获取token信息：" + codeKey);
        if(StringUtils.isBlank(result)){
            if(validatorRefresToken()) {
                logger.error("======获取result信息：" + result);
                throw new SystemRuntimeException(SYS_AUTH_ACCESS_TOKEN_FAIL);
            }
            throw new SystemRuntimeException(SYS_AUTH_REFRES_TOKEN_FAIL);
        }

        if(result.equals(TokenMsgCodeEnum.RESET_PASSWORD.getIndexString())){
            throw new SystemRuntimeException(SYS_AUTH_RESET_PASSWORD_FAIL);
        }
        if(result.equals(TokenMsgCodeEnum.DEVICE_LOGIN.getIndexString())){
            throw new SystemRuntimeException(SYS_AUTH_DEVICE_LOGIN_FAIL);
        }
    }

    public Boolean validatorRefresToken() {
        try {
            HttpRequestPostMessage message = ParameterMessage.getRequestMessage();
            String codeKey = RedisShortKeyUtil.formatKey(RedisKeys.OAUTH_USER_REFRESH_TOKEN_MAP,message.getUserId());
            String result = redisClientServer.get(codeKey);
            logger.info("------RefresToken from redis ,codeKey:[{}], result:[{}] ",codeKey,result);
            if(ObjectTrue.isNotEmpty(result) ){
                return true;
            }
        }catch (Exception e){
            logger.error("UserTokenHandlerValidator validator Exception:[{}] ",e);
        }
        return false;
    }

    /**
     * 验证结果数据,成功返回御前, 失败返回false
     *
     * @param googleCache
     * @return
     */
//    @Override
//    public boolean validatorFromData(boolean googleCache,) {
//        if (googleCache){
//          return   validatorCache();
//        }
//        return validatorDataSource();
//    }


}
