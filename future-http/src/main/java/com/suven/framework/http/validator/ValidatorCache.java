package com.suven.framework.http.validator;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.suven.framework.util.constants.PropertiesParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


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
 * Description: (说明) Gaven LoadingCache 缓存实现内存缓存实现抽象类
 */


public abstract class ValidatorCache<V> {

    private Logger logger = LoggerFactory.getLogger(ValidatorCache.class);

    private ListeningExecutorService refreshPools =  MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(20));

    protected String registryAddress =  PropertiesParam.sysServerDubboRegistryAddress();

    protected final static int LOADING_CACHE_DEFAULT_MINUTES = 60 ;//1分钟==60秒,单位是秒

    /**
     * 缓存平台渠道下对应的版本信息
     */
    private LoadingCache<String, V> validatorCache = CacheBuilder.newBuilder()
            .refreshAfterWrite(refreshAfterWriteTimeSeconds(), TimeUnit.SECONDS)
            .build(new CacheLoader<String, V>() {
                @Override
                public V load(String cacheKey) throws Exception {
                    logger.warn("ListenableFuture<Boolean> reload --------- ");
                    return  validatorDataSource();
                }

//                @Override
//                public ListenableFuture<V> reload(String cacheKey, V oldValue) throws Exception {
//                    logger.warn("ListenableFuture<Boolean> reload --------- ");
//                    return refreshPools.submit(new Callable<V>() {
//
//                        @Override
//                        public V call() throws Exception {
//                            logger.warn("ListenableFuture<Boolean> Boolean call +++++++++++");
//                            return  validator();
//                        }
//                    });
//                }
            });


    /**
     * 抽象设置临时缓存时间值
     * @return
     */
    protected  int refreshAfterWriteTimeSeconds(){
        return LOADING_CACHE_DEFAULT_MINUTES;
    }

    /**
     * 执行rpc 转换结果逻辑实现
     * @return
     */

    protected abstract  V validatorDataSource();

    protected abstract String cacheKey();


    /**
     *
     * @param
     * @return
     */
    public  boolean validatorCache(){
        String cacheKey = cacheKey();
       V validatorValue =  validatorCache.getUnchecked(cacheKey);
//       if(null == validatorValue || validatorValue.equals(false) ){wo1
//           invalidateCache();
//           return false;
//        }
       if(Objects.nonNull(validatorValue) && validatorValue instanceof Boolean){
           return (Boolean)validatorValue;

       }
       logger.warn("----------- validatorCache data is null! ");
       return cacheValidatorValue(validatorValue);
    }

    protected boolean cacheValidatorValue(V validatorValue ){

        return false;
    }
    /**
     *
     * @param
     * @return
     */
    protected   void invalidateCache(){
        String cacheKey = cacheKey();
        validatorCache.invalidate(cacheKey);
    }

}
