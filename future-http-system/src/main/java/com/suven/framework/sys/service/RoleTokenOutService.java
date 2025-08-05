//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.suven.framework.sys.service;

import com.suven.framework.core.redis.RedisClientServer;
import com.suven.framework.core.redis.RedisShortKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleTokenOutService {
    @Autowired
    private RedisClientServer redisClusterServer;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public RoleTokenOutService() {
    }

    public boolean delUserTokenInRedisByUserIds(long userId) {
        String codeKey = RedisShortKeyUtil.formatKey(new Object[]{"oauth_user_id_token:", userId});
        String refreshKey = RedisShortKeyUtil.formatKey(new Object[]{"oauth_user_refresh_token:", userId});
        this.redisClusterServer.delete(new String[]{codeKey, refreshKey});
        this.logger.info("delUserTokenInRedisByUserIds accessToken: [{}] , refreshToken: [{}] ", codeKey, refreshKey);
        return true;
    }

    public boolean delUserTokenInRedisByUserIds(List<Long> userIds) {
        List<String> tokenList = new ArrayList<>();
        List<String> refreshList = new ArrayList<>();
        if (null != userIds && !userIds.isEmpty()) {
            userIds.forEach((userId) -> {
                String codeKey = RedisShortKeyUtil.formatKey(new Object[]{"oauth_user_id_token:", userId});
                String refreshKey = RedisShortKeyUtil.formatKey(new Object[]{"oauth_user_refresh_token:", userId});
                tokenList.add(codeKey);
                refreshList.add(refreshKey);
            });
            this.redisClusterServer.delete((String[])tokenList.toArray(new String[0]));
            this.redisClusterServer.delete((String[])refreshList.toArray(new String[0]));
            this.logger.info("delUserTokenInRedisByUserIds AccessTokenList: [{}] , refreshTokenList: [{}] ", tokenList.toString(), refreshList.toString());
        }

        return true;
    }
}
