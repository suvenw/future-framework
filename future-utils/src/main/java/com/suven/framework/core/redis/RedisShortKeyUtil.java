package com.suven.framework.core.redis;


import com.suven.framework.util.crypt.SignParam;
import com.suven.framework.util.tool.Splitable;

/**
 * @author suven.wang
 * @version  v2.0
 * Description //TODO ${END}$
 * CreateDate2018-10-10  15:58
 *
 **/
public class RedisShortKeyUtil {

    public static String formatKey(Object... args){
        return formatKey(true,args);
    }
    public static String formatKey(Boolean isZero,Object... args){
        StringBuilder builder = new StringBuilder();
        if(args == null || args.length == 0){
            return builder.toString();
        }
        for (int i = 0,s = args.length, j = s-1; i < s ; i++) {
            if((isZero && i == 0) || i == j){
                builder.append(args[i]);
                continue;
            }
            builder.append(args[i]).append(Splitable.ATTRIBUTE_SPLIT);
        }
        return builder.toString();
    }

    /**
     * 通过自定义key,表对象，入参生成rediskey字符串，
     * 再通过md5生成字符串，并返回加密后的前16位字符串
     * eg: table:rediskey:100:200
     * @param prefixKey 自定义rediskey 字符串,允许为空，但注意防止key相同
     * @param classKey 数据表对象的class类,,允许为空，但注意防止key相同
     * @param params 接口需求参数
     * @return
     */

    public static String getMdListForRedisKey(String prefixKey,String classKey, Object ... params){
        StringBuilder builder = new StringBuilder();
        StringBuilder paramsKey = new StringBuilder();
        boolean isPrefix = true;
        if( null != prefixKey){
            builder.append(prefixKey);
            if(prefixKey.lastIndexOf(Splitable.DELIMITER_ARGS) < 0){
                builder.append(Splitable.DELIMITER_ARGS);
            }
        }
        if(null != classKey){
            paramsKey.append(classKey).append(Splitable.ATTRIBUTE_SPLIT);
        }
        if(null != params){
            for (Object param : params) {
                paramsKey.append(param).append(Splitable.ATTRIBUTE_SPLIT);
            }
            paramsKey.deleteCharAt(paramsKey.length()-1);
        }
        String redisKey = SignParam.paramMd5By16( builder.toString());
        return builder.append(redisKey).toString();
    }


}
