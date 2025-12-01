package com.suven.framework.http.validator;


import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.http.interceptor.HandlerValidator;
import com.suven.framework.http.message.HttpRequestPostMessage;
import com.suven.framework.http.message.ParameterMessage;
import com.suven.framework.util.tool.ReflectionUtilTool;
import com.suven.framework.util.tool.TopStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;


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
 * Description: (说明) 基于 Gaven LoadingCache 缓存实现软件版本号校验实现方法
 */


@Component("versionHandlerValidator")
public class VersionHandlerValidator extends ValidatorCache<Map<String, VersionHandlerVo>>  implements HandlerValidator {


    private Logger logger = LoggerFactory.getLogger(VersionHandlerValidator.class);



    private final static int LOADING_CACHE_DEFAULT_SECONDS = 60*2;

    /**
     * 抽象设置临时缓存时间值
     * @return
     */
    @Override
    protected  int refreshAfterWriteTimeSeconds(){
        return LOADING_CACHE_DEFAULT_SECONDS;
    }

    private boolean serverIsDubbo = false;

    private Map<String, VersionHandlerVo> newVersionVoMap;

    /**
     * 执行rpc 转换结果逻辑实现
     *
     * @return
     */
    @Override
    protected Map<String, VersionHandlerVo> validatorDataSource() {
        try {
            Object object = null;
            if(serverIsDubbo){
//                ReferenceConfig<ValidatorService> referenceConfig = new ReferenceConfig<>();
//                referenceConfig.setRegistry(new RegistryConfig(registryAddress));
//                referenceConfig.setInterface(ValidatorService.class);
//                ValidatorService validatorService = referenceConfig.get();
//                object = ReflectionUtilTool.invokeBeanMethod( validatorService, GlobalConfigConstants.VERSION_HANDLER_METHOD_NAME,null);

            }else {
                object = ReflectionUtilTool.invokeClassMethod(
                        GlobalConfigConstants.VERSION_INFO_SERVICE_NAME,
                        GlobalConfigConstants.VERSION_HANDLER_METHOD_NAME);

            }
            if(null == object ||  !(object instanceof Map)){
                return null;
            }

            return copyMap(object);
        }catch (Exception e){
            logger.error("",e);
        }
        return null;
    }

    /**
     * 拷贝map数据
     * @param object 源数据
     * @return 拷贝数据
     */
    @SuppressWarnings("unchecked")
    private Map<String, VersionHandlerVo> copyMap(Object object){
        if(Objects.isNull(object) ||  !(object instanceof Map)){
            return Collections.emptyMap();
        }

        Map<String,Object> map = (Map<String,Object>)object;
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        Map<String, VersionHandlerVo> versionVoMap = new HashMap<>();
        while(it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            VersionHandlerVo versionVo =  VersionHandlerVo.build().clone(entry.getValue());
            versionVoMap.put(entry.getKey(),versionVo);
        }
        return versionVoMap;
    }

    /**
     * 缓存数据源
     * @param validatorValueMap 缓存数据源
     * @return 缓存结果 boolean
     */
    @Override
    protected boolean cacheValidatorValue(Map<String, VersionHandlerVo> validatorValueMap){
        int currentVersion =   ParameterMessage.getRequestMessage().getVersion();
        newVersionVoMap = validatorValueMap;
        String updateKey = cacheUpdateVersionKey();
        VersionHandlerVo updateVersion  = validatorValueMap.get(updateKey);
        if(Objects.nonNull(updateVersion) && updateVersion.forceUpdate()  && updateVersion.getVersion() >= currentVersion){//强制
            return true;//强制更新
        }
        return false;
    }

    /**
     * 获取最新版本信息
     * platform_channel_forceUpdate
     * @return 版本信息
     */

    public VersionHandlerVo getNewVersionVo(){
        String newVersionKey = cacheNewVersionKey();
        VersionHandlerVo versionVo = newVersionVoMap.get(newVersionKey);
        if (Objects.nonNull(versionVo)){
            return versionVo;
        }
        newVersionKey = cacheNewVersionKey0();
        return newVersionVoMap.get(newVersionKey);
    }


    /** 全量刷新版本信息*/
    @Override
    protected String cacheKey() {
        return ValidatorEnum.VERSION.name();
    }

    /** platform_channel_forceUpdate 强更平台渠道*/
    private String cacheUpdateVersionKey() {
        HttpRequestPostMessage message = ParameterMessage.getRequestMessage();
        if (message == null){
            return null;
        }
        String updateKey =
                message.getPlatform()+"_"+message.getChannel()+"_"+1;
        return updateKey;
    }
    /** platform_channel_forceUpdate 指定渠道*/
    private String cacheNewVersionKey() {
        HttpRequestPostMessage message = ParameterMessage.getRequestMessage();
        String updateKey =
                message.getPlatform()+"_"+message.getChannel()+"_"+0;
        return updateKey;
    }

    /** platform_channel_forceUpdate 官方渠道*/
    private String cacheNewVersionKey0() {
        HttpRequestPostMessage message = ParameterMessage.getRequestMessage();
        String updateKey =
                message.getPlatform()+"_"+0+"_"+0;
        return updateKey;
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
        if (googleCache){
            return validatorCache();
        }else {
            Map<String, VersionHandlerVo>  dataSource = validatorDataSource();
          return   cacheValidatorValue(dataSource);
        }

    }

}
