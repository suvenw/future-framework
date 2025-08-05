package com.suven.framework.sys.service;


import com.suven.framework.common.enums.SystemMsgCodeEnum;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.redis.RedisClientServer;
import com.suven.framework.core.redis.RedisKeys;
import com.suven.framework.core.redis.RedisShortKeyUtil;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.http.shiro.OAuth2Token;
import com.suven.framework.http.validator.JwtUtil;
import com.suven.framework.sys.dto.enums.SysUserDepartQueryEnum;
import com.suven.framework.sys.dto.enums.SysUserQueryEnum;
import com.suven.framework.sys.dto.enums.SysUserRoleQueryEnum;
import com.suven.framework.sys.dto.request.SysUserDepartRequestDto;
import com.suven.framework.sys.dto.request.SysUserLoginRequestDto;
import com.suven.framework.sys.dto.request.SysUserRequestDto;
import com.suven.framework.sys.dto.request.SysUserRoleRequestDto;
import com.suven.framework.sys.dto.response.*;
import com.suven.framework.sys.utils.SystemParamConstant;
import com.suven.framework.util.crypt.CryptUtil;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @ClassName: SysUserServiceImpl.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:09:37
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户表 RPC业务接口逻辑实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/

@Service
public class SysUserLoginServiceImpl implements SysUserLoginService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService  sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysUserDepartService  sysUserDepartService;

    @Autowired
    private SysDepartService sysDepartService;

    @Autowired
    private RedisClientServer redisClusterServer;



    /**
     * 保存用户表同时更新数据库和缓存的实现方法
     * @param sysUserLoginRequestDto SysUserLoginRequestDto
     * @return
     */
    @Override
    public SysLoginResponseDto loginSysUser(SysUserLoginRequestDto sysUserLoginRequestDto) {
        IResultCodeEnum msgEnum = null;
//        msgEnum =  this.checkCode(sysUserLoginRequestDto);
        if (msgEnum != null) {
            throw new SystemRuntimeException( msgEnum);
        }
        SysUserRequestDto sysUserRequestDto = SysUserRequestDto.build().toUsername(sysUserLoginRequestDto.getUsername());
        SysUserResponseDto dto = sysUserService.getSysUserByOne(SysUserQueryEnum.USER_NAME_OR_PHONE,sysUserRequestDto);
        if(dto == null){
            throw new SystemRuntimeException( SystemMsgCodeEnum.SYS_USER_FAIL);
        }
        msgEnum = this.checkUserIsEffective(dto, sysUserLoginRequestDto);
        if (msgEnum != null) {
            throw new SystemRuntimeException( msgEnum);
        }
        SysLoginResponseDto responseVo = this.getUserInfoAndSysDepart(dto);
        return responseVo;
    }

    private IResultCodeEnum checkCode(SysUserLoginRequestDto sysUserRequestDto){
        Object checkCode = redisClusterServer.get(sysUserRequestDto.getCheckKey());
        if(checkCode == null) {
            return SystemMsgCodeEnum.SYS_ERROR_500.clone(SystemParamConstant.Sys.SYS_CAPTCHA_CODE_ERROR);
        }
        if(!checkCode.equals(sysUserRequestDto.getCaptcha())) {
            return SystemMsgCodeEnum.SYS_ERROR_500.clone(SystemParamConstant.Sys.SYS_CAPTCHA_CODE_ERROR);
        }
        /** 验证码使用后,立即删除 **/
        redisClusterServer.delete(sysUserRequestDto.getCheckKey());
        return null;

    }

    private IResultCodeEnum checkUserIsEffective(SysUserResponseDto dto, SysUserLoginRequestDto sysUserRequestVo) {

        if (dto == null ) {
            return SystemMsgCodeEnum.SYS_USER_FAIL;
        } if (dto != null && dto.getStatus() == 2) {
            return SystemMsgCodeEnum.SYS_USER_BAND_FAIL;
        }
        String userPassword = sysUserRequestVo.getPassword();
//        userPassword = CryptUtil.encryptPassword(sysUserRequestVo.getPassword());
        /** todo 若前端密码使用aes加密,则注释下面这行代码,如果是原文时,先加密码,再解密,这是不规范的,方便测试 */
        userPassword = CryptUtil.encryptPassword(userPassword);
        /** todo 若前端密码使用aes加密,则注释上面这行代码,如果是原文时,先加密码,再解密,再解密,这是不规范的,方便测试  */


        userPassword = coverPassword(userPassword,dto.getSalt());
        String sysPassword = dto.getPassword();
        if (!userPassword.equals(sysPassword)) {
            return SystemMsgCodeEnum.SYS_USER_PWD_FAIL;
        }

        //不存在角色，不让该用户登录
        SysUserRoleRequestDto sysUserRoleRequestDto = SysUserRoleRequestDto.build().toUserId(dto.getId());
        List<SysUserRoleResponseDto>  sysUserRoleResponseDtoList =
                sysUserRoleService.getSysUserRoleListByQuery(sysUserRoleRequestDto,SysUserRoleQueryEnum.USER_ID);
        if (ObjectTrue.isEmpty(sysUserRoleResponseDtoList)) {
            return SystemMsgCodeEnum.SYS_USER_FAIL;
        }
        return null;
    }

    //aes解密
    private static String coverPassword(String sourcePassword,String Salt) {
        String decryptPassword = CryptUtil.decryptPassword(sourcePassword );
        String password = CryptUtil.md5(decryptPassword+Salt) ;
        return password;
    }

    /**
     * 用户信息
     *
     * @param sysUser
     * @return
     */
    public SysLoginResponseDto getUserInfoAndSysDepart(SysUserResponseDto sysUser)  {

        SysLoginResponseDto result = null;
        // 	生成token
//        SysUserTokenResponseDto tokenResponseDto = SysUserTokenResponseDto.build().clone(sysUser);
//        String token = JwtUtil.createToken(JSON.toJSONString(tokenResponseDto));
        String token = null;
        try {
            token = OAuth2Token.refreshToken();
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }
        ;
        long current = System.currentTimeMillis() + JwtUtil.JWT_EXPIRE_TIME;
        //shiro
        String key = RedisShortKeyUtil.formatKey(RedisKeys.USER_LOGIN_TOKEN,  token );
        boolean re = redisClusterServer.hmset(key,sysUser.getUsername(), String.valueOf(current));

        // 获取用户部门信息
        SysUserDepartRequestDto requestDto = SysUserDepartRequestDto.build().toUserId(sysUser.getId());
        List<SysUserDepartResponseDto> departDtoList= sysUserDepartService.getSysUserDepartListByQuery(requestDto, SysUserDepartQueryEnum.USER_ID);

        int multi_depart = 0 ;
        if(null == departDtoList || departDtoList.isEmpty()){
            result = setSysLoginResponseDto(sysUser, token, multi_depart, new ArrayList<>());
            return result;
        }
        List<Long> departIdList =  departDtoList.stream().map(obj ->obj.getDepId()).collect(Collectors.toList());

        List<SysDepartResponseDto> sysDepartDtoList=  sysDepartService.getSysDepartByIdList(departIdList);
        if(null != sysDepartDtoList &&  sysDepartDtoList.size() == 1){
//			sysUserService.updateUserDepart(userName, departs.get(0).getOrgCode());
            multi_depart = 1;
        } else  if(null != sysDepartDtoList &&  sysDepartDtoList.size() > 1){
            multi_depart = 2;
        }

        result = setSysLoginResponseDto(sysUser, token, multi_depart, sysDepartDtoList);

        return result;
    }

    private SysLoginResponseDto setSysLoginResponseDto(SysUserResponseDto sysUser,  String token, int multi_depart, List<SysDepartResponseDto> sysDepartDtoList) {
        SysLoginResponseDto result = SysLoginResponseDto.build();
        result.setDeparts(sysDepartDtoList);
        result.setToken(token);
        result.setUserInfo(sysUser);
        result.setMulti_depart(multi_depart);
        return result;
    }


}
