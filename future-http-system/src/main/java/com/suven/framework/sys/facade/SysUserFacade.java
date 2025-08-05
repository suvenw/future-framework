package com.suven.framework.sys.facade;

import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.common.enums.SystemMsgCodeEnum;
import com.suven.framework.common.util.Constant;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.redis.RedisClientServer;
import com.suven.framework.core.redis.RedisKeys;
import com.suven.framework.core.redis.RedisShortKeyUtil;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.ResponseResultPageVo;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.sys.dto.enums.SysUserQueryEnum;
import com.suven.framework.sys.dto.request.SysUserLoginRequestDto;
import com.suven.framework.sys.dto.request.SysUserRequestDto;
import com.suven.framework.sys.dto.response.SysDepartResponseDto;
import com.suven.framework.sys.dto.response.SysLoginResponseDto;
import com.suven.framework.sys.dto.response.SysUserDepartResponseDto;
import com.suven.framework.sys.dto.response.SysUserResponseDto;
import com.suven.framework.sys.service.*;
import com.suven.framework.sys.utils.SystemParamConstant;
import com.suven.framework.sys.vo.request.SysUserDepartIdsRequestVo;
import com.suven.framework.sys.vo.request.SysUserLoginRequestVo;
import com.suven.framework.sys.vo.request.SysUserRoleIdsRequestVo;
import com.suven.framework.sys.vo.request.SysUserUpdatePwdRequestVo;
import com.suven.framework.sys.vo.response.*;
import com.suven.framework.util.crypt.CryptUtil;
import com.suven.framework.util.random.RandImageUtil;
import com.suven.framework.util.random.RandomUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.suven.framework.common.enums.SysResultCodeEnum.SYS_AUTH_ACCESS_TOKEN_FAIL;


/**   
 * Title: SysUserFacade.java
 * @author xxx.xxx
 * date   2019-10-18 12:35:25
 * @version V1.0  
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Description: TODO(说明) 对象缓存统一模板实现类;
 */
 
@Component
public class SysUserFacade {


	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysUserService  sysUserService;

	@Autowired
	private SysDepartService sysDepartService;

	@Autowired
	private RedisClientServer redisClusterServer;

	@Autowired
	private SysUserDepartService sysUserDepartService;

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysUserLoginService sysUserLoginService;




	/**
	 * 用户登陆
	 * @param sysUserLoginRequestVo
	 * @return
	 */
	public SysLoginResponseVo userLogin(SysUserLoginRequestVo sysUserLoginRequestVo) {
		String code = sysUserLoginRequestVo.getCaptcha();
		String lowerCaseCode = code.toLowerCase();
		String key = RedisShortKeyUtil.formatKey(RedisKeys.USER_LOGIN_CHECK, lowerCaseCode);
		String sysCode = redisClusterServer.get(key);
		if(StringUtils.isBlank(sysCode) || !sysCode.equals(code)){
			throw new SystemRuntimeException(SysResultCodeEnum.SYS_LOGIN_CODE_FAIL);
		}

		SysUserLoginRequestDto loginRequestDto = SysUserLoginRequestDto.build().clone(sysUserLoginRequestVo);
		SysLoginResponseDto loginDto =  sysUserLoginService.loginSysUser(loginRequestDto);
		if(loginDto == null ){
			throw new SystemRuntimeException(SysResultCodeEnum.SYS_USER_FAIL);
		}
		List<SysDepartResponseVo> departs  = new ArrayList<>();
		if(null != loginDto.getDeparts() && !loginDto.getDeparts().isEmpty()){
			loginDto.getDeparts().forEach(depart -> {
				SysDepartResponseVo vo = SysDepartResponseVo.build().clone(depart);
				departs.add(vo);
			});
		}
		SysLoginResponseVo result = SysLoginResponseVo.build();
		result.setMulti_depart(loginDto.getMulti_depart());
		result.setToken(loginDto.getToken());
		result.setUserInfo(SysUserResponseVo.build().clone(loginDto.getUserInfo()));
		result.setDeparts(departs);
		return result;

	}



	public SysResultCodeEnum logout(HttpServletRequest request) {
		String token = request.getHeader(Constant.X_ACCESS_TOKEN);
		String codeKey = RedisShortKeyUtil.formatKey(RedisKeys.USER_LOGIN_TOKEN, token);
		long expire = System.currentTimeMillis();
		Map<String, String> result = redisClusterServer.getMapCacheAndDelExpire(codeKey, expire);
		if (result.isEmpty() ) {
			return SysResultCodeEnum.SYS_TOKEN_FAIL;
		}
		redisClusterServer.delete(codeKey);
		return SysResultCodeEnum.SYS_SUCCESS;
//		if(StringUtils.isEmpty(token)) {
//			return SysResultCodeEnum.SYS_LOGOUT_FAIL;
//		}
//		String obj = JwtUtil.getUserInfo(token);
//		if(StringUtils.isEmpty(obj)) {
//			return SysResultCodeEnum.SYS_TOKEN_FAIL;
//		}
//		SysUserResponseVo vo = JSONObject.parseObject(obj,SysUserResponseVo.class);
//		SysUserLoginRequestDto loginRequestDto = SysUserLoginRequestDto.build().clone(vo.getUsername());
//		SysLoginResponseDto dto =  sysUserLoginService.loginSysUser(loginRequestDto);
//		if(dto!=null) {
//			String key = RedisShortKeyUtil.formatKey(RedisKeys.USER_LOGIN_TOKEN, dto.getId());
//			redisClusterServer.hdel(key,token);
//			return SysResultCodeEnum.SYS_SUCCESS;
//		}else {
//			return SysResultCodeEnum.SYS_TOKEN_FAIL;
//		}
	}


	public Boolean deleteUserInDepart(SysUserDepartIdsRequestVo sysUserDepartRequestVo) {
		return sysUserDepartService.deleteUserInDepart(sysUserDepartRequestVo.getDepId(),sysUserDepartRequestVo.getUserIdList());
	}


	/**
	 * 修改密码
	 * @param userUpdatePwdRequestVo
	 * @return
	 */
	public SysResultCodeEnum updatePassword(SysUserUpdatePwdRequestVo userUpdatePwdRequestVo) {
		SysUserRequestDto sysUserRequestDto = SysUserRequestDto.build().toUsername(userUpdatePwdRequestVo.getUsername());
		SysUserResponseDto dto = sysUserService.getSysUserByOne(SysUserQueryEnum.USER_NAME,sysUserRequestDto);
		if (null == dto) {
			return SysResultCodeEnum.SYS_USER_FAIL;
		}
		String md5OldPassword = crypPasswordSalt(userUpdatePwdRequestVo.getOldpassword() , dto.getSalt());
		if (!dto.getPassword().equals(md5OldPassword)) {
			return SysResultCodeEnum.SYS_USER_OLD_PWD_FAIL;
		}
		if (StringUtils.isEmpty(userUpdatePwdRequestVo.getPassword())) {
			return SysResultCodeEnum.SYS_USER_NEW_PWD_FAIL;
		}
		if (userUpdatePwdRequestVo.getPassword().trim().length()<6) {
			return SysResultCodeEnum.SYS_USER_NEW_PWD_LENGTH;
		}
		if (!userUpdatePwdRequestVo.getPassword().equals(userUpdatePwdRequestVo.getConfirmpassword())) {
			return SysResultCodeEnum.SYS_USER_TWO_PWD_FAIL;
		}
		String password = crypPasswordSalt(userUpdatePwdRequestVo.getPassword() , dto.getSalt());

		SysUserRequestDto requestDto = SysUserRequestDto.build().clone(dto);
		requestDto.setPassword(password);
		sysUserService.updateSysUserPassWord(requestDto);
		return SysResultCodeEnum.SYS_SUCCESS;
	}

	/**
	 * 查询用户部门信息by用户id
	 * @param userId
	 * @return
	 */
	public List<DepartTreeResponseVo> getUserDepartList(long userId) {
		List<SysUserDepartResponseDto> userDeparts = sysUserDepartService.getListByUserId(userId);
		if (ObjectTrue.isEmpty(userDeparts)) {
			return new ArrayList<>();
		}
		List<Long> departIds = new ArrayList<>();
		userDeparts.forEach(u ->{
			departIds.add(u.getDepId());
		});
		List<SysDepartResponseDto> departList = sysDepartService.getSysDepartByIdList(departIds);
		if (ObjectTrue.isEmpty(departList)){
			return new ArrayList<>();
		}
		List<DepartTreeResponseVo> departTreeRespVoList = new ArrayList<>();
		departList.forEach(d ->{
			departTreeRespVoList.add(DepartTreeResponseVo.build().convertByUserDepart(d));
		});
		return departTreeRespVoList;
	}

	public Boolean addSysUserRole(SysUserRoleIdsRequestVo userDepartRequestVo) {
		boolean isFlag = sysUserService.addSysUserRole(userDepartRequestVo.getRoleId(),
				userDepartRequestVo.getUserIdList());
		return isFlag;
	}

	public Boolean deleteUserRole(SysUserRoleIdsRequestVo userDepartRequestVo) {
		boolean isFlag = sysUserService.deleteUserRole(userDepartRequestVo.getRoleId(),userDepartRequestVo.getUserIdList());
		return isFlag;
	}

	private static String crypPasswordSalt(String sourcePassword , String salt) {
		String password = CryptUtil.md5(sourcePassword + salt) ;
		return password;
	}

	private static String coverPassword(String sourcePassword , String salt) {
		String decryptPassword = CryptUtil.decryptPassword(sourcePassword);
		String password = CryptUtil.md5(decryptPassword + salt) ;
		return password;
	}

	public ResponseResultPageVo getSysUserList(Pager page) {
		ResponseResultPageVo<SysUserResponseDto> resultList = sysUserService.getSysUserByNextPage(page,SysUserQueryEnum.DESC_ID);
		if (null == resultList || resultList.getList().isEmpty()) {
			return new ResponseResultPageVo<>();
		}
		List<Long> userIds = new ArrayList<>(); //后面处理等级等信息
		List<SysUserResponseVo> listVo = new ArrayList<>();
		resultList.getList().forEach(e ->{
			userIds.add(e.getId());
			listVo.add(SysUserResponseVo.build().clone(e));
		});
		ResponseResultPageVo<SysUserResponseVo> result = new ResponseResultPageVo<>();
		result.toList(listVo)
				.toIsNextPage(resultList.getIsNextPage())
				.toPageIndex(resultList.getPageIndex())
				.toTotal(resultList.getTotal())
				;
		return result;

	}

	/**
	 * 获取随机码,并缓存到redis中;
	 * @return
	 */
	public LoginCodeResponseVo getCheckCode() {
		String code = RandomUtils.randomString(4);
		String key = CryptUtil.md5(code+System.currentTimeMillis());
		key = RedisShortKeyUtil.formatKey(RedisKeys.USER_LOGIN_CHECK, key);
		redisClusterServer.setNx(key,code,Constant.CHECK_CODE_SECOND);
		return LoginCodeResponseVo.build().setKey(key).setCode(code);
	}

	/**
	 * 将验证码生成图片
	 * @return
	 */
	public LoginCodeResponseVo getCheckCodeImage(){
		try {

			String code = RandomUtils.randomString(4);
			String lowerCaseCode = code.toLowerCase();
			String key = RedisShortKeyUtil.formatKey(RedisKeys.USER_LOGIN_CHECK, lowerCaseCode);
			redisClusterServer.setNx(key,code,Constant.CHECK_CODE_SECOND);
			String base64 = RandImageUtil.generate(code);
			LoginCodeResponseVo vo = LoginCodeResponseVo.build().setCode(code).setKey(key).setBase64(base64);
			return vo;
		} catch (Exception e) {
			logger.error("生成图片验证异常 randomImage exception[{}]",e);
			e.printStackTrace();
			throw new SystemRuntimeException(SystemMsgCodeEnum.SYS_ERROR_500
					.clone(SystemParamConstant.Sys.SYS_CAPTCHA_CODE_ERROR));

		}
	}

	/**
	 * 校验用户token信息, 是否有效,有效返回true,否则为false;
	 *
	 * @param username
	 * @param accessToken
	 * @return
	 */
	public boolean checkToken(String username, String accessToken ){
		long current = System.currentTimeMillis();
		//shiro
		String codeKey = RedisShortKeyUtil.formatKey(RedisKeys.USER_LOGIN_TOKEN, accessToken);
		long expire = System.currentTimeMillis();
		Map<String, String> result = redisClusterServer.getMapCacheAndDelExpire(codeKey, expire);
		if (result.isEmpty() ) {
			throw new SystemRuntimeException(SYS_AUTH_ACCESS_TOKEN_FAIL);
		}
		String loginName = result.get(username);
		if(loginName == null){
			throw new SystemRuntimeException(SYS_AUTH_ACCESS_TOKEN_FAIL);
		}
		return true;
	}








}
