package com.suven.framework.http.interceptor;

import com.suven.framework.core.redis.RedisClientServer;
import com.suven.framework.core.redis.RedisKeys;
import com.suven.framework.http.api.IResponseResult;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.message.HttpRequestRemote;
import com.suven.framework.http.message.ParameterMessage;
import com.suven.framework.http.processor.url.Cdn;
import com.suven.framework.util.json.JsonUtils;
import com.suven.framework.util.tool.TopStringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Objects;

/**
 * 接口url 验证
 */
public class RedisCacheHandlerInterceptor extends AbstractHandlerInterceptorAdapter  implements IHandlerInterceptor {
    private static Logger log = LoggerFactory.getLogger(RedisCacheHandlerInterceptor.class);


    public RedisCacheHandlerInterceptor(ApplicationContext application, InterceptorConfigSetting handlerSetting) {
        super(application, handlerSetting);
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return HandlerInterceptorEnum.HANDEL_ORDER_REDIS_CACHE.getIndex();
    }

    /**
     * @return 返回 HandlerValidator 对应 IEnum HandlerInterceptorEnum
     * 其中getValue 对应是 HandlerValidator Bean 的 Spring FactoryBean 对象的key
     */
    @Override
    public String handlerValidatorBeanType() {
        return HandlerInterceptorEnum.HANDEL_ORDER_REDIS_CACHE.getValue();
    }

    /**
     * 通过supportsRunStatus 和 interceptor 提供架构的 preHandle方法,实现开关控制逻辑实现,其实等于preHandle增加开关配置逻辑
     *
     * @param request   HttpServletRequest 网络请求对象
     * @param response  HttpServletResponse 网络返回对象
     * @param handler   Object handler – 选择要执行的处理程序，用于类型和/或实例评估
     * @param validator 获取业务的验证实现接口的对应实现类
     * @return 结果对或错 true/false,为true时,执行下一个验证拦截, 为false 结束运行, 建议抛出异常提示到前端用户
     * @throws Exception 通过异常反馈到前端
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler, HandlerValidator validator) throws Exception {

        if( this.isPostRequestMethod(request)){
            return true;
        }
        try {
            request.setAttribute("cdnAttribute",true);
            HttpRequestRemote remote =  ParameterMessage.getRequestRemote();
            String srvMd5Sign =  TopStringUtils.toStringBuilder(RedisKeys.CDN_RESULT_CACHE_KEY,remote.getUrl(),remote.getSrvMd5Sign());

            boolean resultResponse = validator.validatorFromData(false,srvMd5Sign);
            return resultResponse;
//            String resultResponse = redisClient.get(sb.toString());
//            if(null != resultResponse){
//                request.setAttribute("cdnAttribute",false);
//                Object result =  JsonUtils.parseObject(resultResponse, Object.class);
//                log.info("========== RedisCacheHandlerInterceptor preHandle redisClusterServer data ========== result:[{}] ", result );
//                BaseHttpResponseWrite.build(response).writeResult(result);
////                cdn.set(false);
//                return true;
//            }
//            cdn.set(true);
        }catch (Exception e){
            e.printStackTrace();
            log.error("RedisCacheHandlerInterceptor preHandle redis Exception: [{}]", e);
        }
        return true;

	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws SystemRuntimeException {
       try {
           Object isCdn = request.getAttribute("cdnAttribute");
           if(Objects.equals(isCdn,Boolean.TRUE)){
               IResponseResult resultResponse = (IResponseResult) ParameterMessage.getRedisCacheResponseVo();
               if(resultResponse == null){
                   return;
               }
               HttpRequestRemote remote =  ParameterMessage.getRequestRemote();
               int cdnTime = Cdn.get(remote.getUrl());
               if (cdnTime == 0) {
                   return;
               }


               String srvMd5Sign =  TopStringUtils.toStringBuilder(RedisKeys.CDN_RESULT_CACHE_KEY,remote.getUrl(),remote.getSrvMd5Sign());
               String result =  JsonUtils.toJson(resultResponse);
               RedisClientServer redisClient = getRedisClient();
               if(Objects.nonNull(redisClient)){
                   redisClient.setEx(srvMd5Sign,result,cdnTime);
                   log.info("========== RedisCacheHandlerInterceptor afterCompletion redisClusterServer.setex ========== result:[{}] ,cdnTime :[{}] ", result,cdnTime );
                  }
               }
       }catch (Exception e){
           e.printStackTrace();
           log.error("RedisCacheHandlerInterceptor afterCompletion redis Exception: [{}]", e);
       }


	}

    public RedisClientServer getRedisClient(){
        return getApplicationContext().getBean("redisClientServer",RedisClientServer.class);
    }



}
