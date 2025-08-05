package com.suven.framework.http.shiro;

/**
 * @program: st-software-service
 * Description: 认证
 * @author: xuegao
 * @create: 2019-08-10 15:06
 **/

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.suven.framework.core.redis.RedisClientServer;
import com.suven.framework.core.redis.RedisKeys;
import com.suven.framework.core.redis.RedisShortKeyUtil;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.sys.dto.enums.SysUserQueryEnum;
import com.suven.framework.sys.dto.request.SysUserRequestDto;
import com.suven.framework.sys.dto.response.SysUserResponseDto;
import com.suven.framework.sys.service.SysUserRoleService;
import com.suven.framework.sys.service.SysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.suven.framework.common.enums.SysResultCodeEnum.SYS_AUTH_ACCESS_TOKEN_FAIL;
import static com.suven.framework.common.enums.SysResultCodeEnum.SYS_USER_BAND_FAIL;


@Component
public class OAuth2Realm extends AuthorizingRealm {
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private RedisClientServer redisClusterServer;

	public final static int ACCESS_TOKEN_EXPIRED_MS = 60 * 60 * 1000;
	public final static int ACCESS_PERMISSION_MS =  60 * 60 ;

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof OAuth2Token;
	}

	/**
	 * 授权(验证权限时调用)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		SysUserResponseDto admin = (SysUserResponseDto) principals.getPrimaryPrincipal();
//		String codeKey = RedisShortKeyUtil.formatKey(RedisKeys.AUTH_CODE,  admin.getRoleId());
//		long expire = System.currentTimeMillis();
//		Map<String, String> permissionMap = redisClusterServer.getMapCacheAndDelExpire(codeKey, expire);
//		Set<String> permsSet = getPermission(permissionMap , admin , codeKey);
		Set<String> permsSet = new HashSet<>();
//		String codeKey = RedisShortKeyUtil.formatKey(RedisKeys.OAUTH_PERMISSION,  admin.getId());
//		String permission = redisClusterServer.get(codeKey);
//		if(StringUtils.isNoneBlank(permission)){
//			permsSet = JSONObject.parseObject(permission , Set.class);
//		}else{
			permsSet = sysUserRoleService.getSysPermissionListByUserId(admin.getId());
//			redisClusterServer.setex(codeKey , JSON.toJSON(permsSet).toString() , ACCESS_PERMISSION_MS);
//		}
		authorizationInfo.setStringPermissions(permsSet);
		return authorizationInfo;
	}

//	private Set<String> getPermission(Map<String, String> permissionMap , SysLoginResponseDto admin , String codeKey) {
//		if (!permissionMap.isEmpty() ) {
//			String json = getKey(permissionMap);
//			List<String> list = JSONObject.parseArray( json , String.class);
//			return new HashSet<>(list);
//		}
//		Set<String> permsSet = secPermissionService.getAdminRolePermissions(admin.getRoleId());
//		if(permsSet.isEmpty()){
//			return permsSet;
//		}
//		String json = JSONObject.toJSONString(permsSet);
//		long time = System.currentTimeMillis();
//		long timeDate  = time + ACCESS_PERMISSION_MS;
//		redisClusterServer.hmset(codeKey, json, String.valueOf(timeDate));
//
//		return permsSet;
//	}

	/**
	 * 认证(登录时调用)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String accessToken = (String) token.getPrincipal();
//		SecAdminRequestDto admin = SecAdminRequestDto.build().setToken(accessToken);
//		//根据accessToken，查询用户信息
//		SecAdminResponseDto secAdminResponseDto = secAdminService.getAdminByData(admin);
//		if(secAdminResponseDto == null){
//			throw new IncorrectCredentialsException("token失效，请重新登录");
//		}
		String codeKey = RedisShortKeyUtil.formatKey(RedisKeys.USER_LOGIN_TOKEN, accessToken);
		long expire = System.currentTimeMillis();
		Map<String, String> result = redisClusterServer.getMapCacheAndDelExpire(codeKey, expire);
		//token失效
		if (null == result || result.isEmpty() ) {
			throw new SystemRuntimeException(SYS_AUTH_ACCESS_TOKEN_FAIL);
		}
		String loginName = getKey(result);
		SysUserRequestDto userRequestDto = SysUserRequestDto.build().toUsername(loginName);
		//根据accessToken，查询用户信息
		SysUserResponseDto user = sysUserService.getSysUserByOne(SysUserQueryEnum.USER_NAME, userRequestDto);
		if(user == null){
			throw new SystemRuntimeException(SYS_AUTH_ACCESS_TOKEN_FAIL);
//			throw new IncorrectCredentialsException("token失效，请重新登录");
		}
		//账号锁定
		if (user.getStatus() == 0) {
//			throw new LockedAccountException("账号已被冻结,请联系管理员");
			throw new SystemRuntimeException(SYS_USER_BAND_FAIL);

		}
		//3小时接口失效
		long time = System.currentTimeMillis();
		long timeDate  = time + ACCESS_TOKEN_EXPIRED_MS;
		redisClusterServer.hmset(codeKey, loginName, String.valueOf(timeDate));

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
		return info;
	}

	private static String getKey(Map<String, String> map) {
		String obj = null;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			obj = entry.getKey();
			if (obj != null) {
				break;
			}
		}
		return  obj;
	}

}
