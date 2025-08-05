package com.suven.framework.http.validator;

import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.redis.RedisClientServer;
import com.suven.framework.core.redis.RedisKeys;
import com.suven.framework.http.interceptor.HandlerValidator;
import com.suven.framework.http.inters.ProjectModuleEnum;
import com.suven.framework.http.message.HttpRequestRemote;
import com.suven.framework.http.message.ParameterMessage;
import com.suven.framework.util.tool.TopStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectMaintainHandlerValidator extends ValidatorCache<Boolean> implements HandlerValidator {
    private Logger logger = LoggerFactory.getLogger(getClass());



    @Autowired(required = false)
    private RedisClientServer redisClusterServer;

    @Override
    protected Boolean validatorDataSource() {

        String url = ParameterMessage.getRequestRemote().getUrl();

        String serverName = ParameterMessage.getRequestRemote().getModule();
        List<String> projectNameList = Arrays.stream(ProjectModuleEnum.values()).filter(e->e.getModule().endsWith(serverName.toUpperCase())).
                map(e->e.getModule()).collect(Collectors.toList());
        if(projectNameList == null || projectNameList.isEmpty()){
            return false;
        }
        if(this.isMaintainModule(projectNameList.get(0))){
            return true;
        }
        return false;
    }

    @Override
    protected String cacheKey() {
        HttpRequestRemote remote = ParameterMessage.getRequestRemote();
        String cacheKey =
                ValidatorEnum.MAINTAIN.name()+"-"+
                remote.getModule();
        return cacheKey;
    }



    /**
     * Title: 验证模块是否维护模式
     * Description:
     * @param
     * @return
     * @throw
     * @author lixiangling
     * date 2018/7/9 19:18
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    private boolean isMaintainModule(String serverProjectName) {
        String projectNames = redisClusterServer.get(RedisKeys.OAUTH_MAINTAIN + "ALL");
        List<String> projectNameList = new ArrayList<>();
        if(ObjectTrue.isNotEmpty(projectNames)) {
            projectNameList.addAll(Arrays.asList(StringUtils.split(projectNames, ",")));
        }
        logger.info("origin serverNames={},request serverName={}",projectNames, serverProjectName);
        if(ObjectTrue.isNotEmpty(projectNameList) && projectNameList.contains(serverProjectName)){
            return true;
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
        if(googleCache){
            return validatorCache();
        }else{
            return validatorDataSource();
        }

    }
}
