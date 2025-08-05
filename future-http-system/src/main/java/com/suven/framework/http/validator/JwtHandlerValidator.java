///**
// * Copyright(c)  XXX-Inc.All Rights Reserved.
// */
//package com.suven.framework.http.validator;
//
//import com.suven.framework.core.redis.RedisClientServer;
//import com.suven.framework.core.redis.RedisKeys;
//import com.suven.framework.core.redis.RedisShortKeyUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//
///**
// * <pre>
// * 程序的中文名称。
// * </pre>
// * @author suven.wang@ XXX.net
// * @version 1.00.00
// * <pre>
// * 修改记录
// *    修改后版本:     修改人：  修改日期:     修改内容:
// * </pre>
// * Description: (说明) token 校验实现类
// */
//
//
//@Component("jwtHandlerValidator")
//public class JwtHandlerValidator extends ValidatorCache implements IHandlerValidator {
//	private Logger logger = LoggerFactory.getLogger(getClass());
//
//
//	@Autowired
//	private RedisClientServer redisClusterServer;
//
//    public boolean tokenValidator(String token){
//        return validator(token);
//    }
//
//    protected Boolean validator(String token) {
//        try {
//            if(StringUtils.isBlank(token)) {
//                return false;
//            }
//            long userId = JwtUtil.getUserId(token);
//            String codeKey = RedisShortKeyUtil.formatKey(RedisKeys.USER_LOGIN_TOKEN,userId);
//            long expire = System.currentTimeMillis();
//            Map<String,String> result = redisClusterServer.getMapCacheAndDelExpire(codeKey,expire);
//            if(null != result && result.containsKey(token)){
//                return true;
//            }
//        }catch (Exception e){
//            logger.error("JwtHandlerValidator validator ",e);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean validatorData() {
//        return false;
//    }
//
//    @Override
//    protected Object validator() {
//        return null;
//    }
//
//    /**
//     * 执行rpc 转换结果逻辑实现
//     *
//     * @return
//     */
//    @Override
//    protected Object validatorDataSource() {
//        return null;
//    }
//
//    @Override
//    protected String cacheKey() {
//        return null;
//    }
//}
