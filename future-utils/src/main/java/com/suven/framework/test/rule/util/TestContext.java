package com.suven.framework.test.rule.util;

import com.suven.framework.test.rule.annotation.Remote;
import com.suven.framework.test.rule.annotation.UserAnno;


/**
 * @author alex.chin
 */
public class TestContext {

    private static ThreadLocal<RemoteVO> remote = new ThreadLocal<>();
    private static ThreadLocal<UserVO> userAnno = new ThreadLocal<>();
    public static RemoteVO getRemoteVO() {
    	RemoteVO remoteVO = remote.get();
    	if(remoteVO == null) {
    		remoteVO = TestContext.RemoteVO.valueOf(false, null);
    	}
        return remoteVO;
    }

    public static void setRemote(RemoteVO remoteVO) {
        remote.set(remoteVO);
    }

    public static class RemoteVO {
        public boolean isRemote = false;
        public Remote remote;

        public static RemoteVO valueOf(boolean isRemote, Remote remote) {
            RemoteVO vo = new RemoteVO();
            vo.isRemote = isRemote;
            vo.remote = remote;
            return vo;
        }
    }
    
    public static UserVO getUserAnno() {
		return userAnno.get();
	}

	public static void setUserAnno(UserVO userVO) {
		userAnno.set(userVO);
	}

	public static class UserVO {
        public boolean hasUser = false;
        public UserAnno userAnno;

        public static UserVO valueOf(boolean hasUser, UserAnno userAnno) {
        	UserVO vo = new UserVO();
            vo.hasUser = hasUser;
            vo.userAnno = userAnno;
            return vo;
        }
    }
}

