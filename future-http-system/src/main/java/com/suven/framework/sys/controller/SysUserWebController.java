package com.suven.framework.sys.controller;


import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.api.RequestMethodEnum;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;

import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.exception.ExceptionFactory;
import com.suven.framework.sys.dto.request.SysUserDepartRequestDto;
import com.suven.framework.sys.dto.request.SysUserRequestDto;
import com.suven.framework.sys.dto.response.SysUserResponseDto;
import com.suven.framework.sys.dto.enums.SysUserQueryEnum;
import com.suven.framework.sys.facade.SysRoleFacade;
import com.suven.framework.sys.facade.SysUserFacade;
import com.suven.framework.sys.service.SysUserDepartService;
import com.suven.framework.sys.service.SysUserRoleService;
import com.suven.framework.sys.service.SysUserService;
import com.suven.framework.sys.vo.request.SysUserAddRequestVo;
import com.suven.framework.sys.vo.request.SysUserLoginRequestVo;
import com.suven.framework.sys.vo.request.SysUserQueryRequestVo;
import com.suven.framework.sys.vo.request.SysUserRoleIdsRequestVo;
import com.suven.framework.sys.vo.request.SysUserRoleRequestVo;
import com.suven.framework.sys.vo.request.SysUserTokenRequestVo;
import com.suven.framework.sys.vo.request.SysUserUpdatePwdRequestVo;
import com.suven.framework.sys.vo.request.SysUserDepartIdsRequestVo;
import com.suven.framework.sys.vo.response.LoginCodeResponseVo;
import com.suven.framework.sys.vo.response.SysUserShowResponseVo;
import com.suven.framework.sys.vo.response.SysUserResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;


/**
 * ClassName: SysUserWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:09:37
 * @version 版本: v2.0.0
 * <pre>
 *
 *  Description: 用户表接口控制器
 *
 *  接口规范：
 *  1. 所有接口必须使用 @ApiDoc 注解
 *  2. 请求方式必须明确指定 (GET/POST/PUT/DELETE)
 *  3. 接口URL必须在 UrlCommand 中统一定义
 *  4. 返回结果必须使用统一的响应格式
 *  5. 必须使用 @Validated 开启参数校验
 *  6. 必须使用 @Slf4j 记录日志
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *    v2.0.0         suven    2026-02-05   重构:统一接口规范
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysUser")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
@RestController
@Slf4j
@Validated
@ApiDoc(
    group = DocumentConst.Sys.SYS_DOC_GROUP,
    groupDesc = DocumentConst.Sys.SYS_DOC_DES,
    module = "用户表模块",
    isApp = true
)
public class SysUserWebController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserFacade sysUserFacade;

    @Autowired
    private SysRoleFacade sysRoleFacade;

    @Autowired
    private SysUserDepartService sysUserDepartService;

    @Autowired
    private SysUserRoleService userRoleService;

    /**
     * 分页获取用户表信息
     * 根据查询条件分页获取用户表列表
     * @param sysUserQueryRequestVo 查询请求参数
     * @return PageResult<SysUserShowResponseVo> 分页响应结果
     * @author suven
     * @date 2025-08-18
     *
     * 接口规则：
     * 1. 分页参数必须使用 Pager 包装
     * 2. 必须指定排序枚举
     * 3. 必须记录操作日志
     * 4. 必须进行参数校验
     */
    @ApiDoc(
        value = "分页获取用户表信息",
        description = "根据查询条件分页获取用户表列表",
        request = SysUserQueryRequestVo.class,
        response = SysUserShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysUser_list)
    public PageResult<SysUserShowResponseVo> pageList(
            @Validated  SysUserQueryRequestVo sysUserQueryRequestVo) {

        log.info("分页查询用户表, 参数: {}", sysUserQueryRequestVo);

        Pager<SysUserRequestDto> pager = new Pager<>(
                sysUserQueryRequestVo.getPageNo(),
                sysUserQueryRequestVo.getPageSize()
        );
        SysUserRequestDto requestDto = SysUserRequestDto.build().clone(sysUserQueryRequestVo);
        pager.toParamObject(requestDto);

        PageResult<SysUserResponseDto> pageResult = sysUserService
                .getSysUserByNextPage(pager, SysUserQueryEnum.DESC_ID);

        log.info("分页查询用户表完成, 总数: {}", pageResult.getTotal());
        return pageResult.convertBuild(SysUserShowResponseVo.class);
    }

    /**
     * 查看用户表详情
     * 根据ID获取用户表详细信息
     * @param idRequestVo ID请求参数
     * @return SysUserShowResponseVo 详情响应结果
     * @author suven
     * @date 2025-08-18
     *
     * 接口规则：
     * 1. ID参数必须校验非空
     * 2. 必须处理数据不存在情况
     * 3. 必须记录查询日志
     */
    @ApiDoc(
        value = "查看用户表详情",
        description = "根据ID获取用户表详细信息",
        request = HttpRequestByIdVo.class,
        response = SysUserShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysUser_detail)
    public SysUserShowResponseVo detail( @Validated  HttpRequestByIdVo idRequestVo) {

        log.info("查询用户表详情, ID: {}", idRequestVo.getId());

        // 参数校验
        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询用户表详情参数错误, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysUserResponseDto responseDto = sysUserService.getSysUserById(idRequestVo.getId());

        if (responseDto == null) {
            log.warn("用户表不存在, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        log.info("查询用户表详情成功, ID: {}", idRequestVo.getId());
        return SysUserShowResponseVo.build().clone(responseDto);
    }

    /**
     * 新增用户表信息
     * 创建新的用户表记录
     * @param sysUserAddRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "新增用户表信息",
        description = "新增用户表记录",
        request = SysUserAddRequestVo.class,
        response = Long.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysUser_add)
    public Long create( @Validated  SysUserAddRequestVo sysUserAddRequestVo) {

        log.info("新增用户表, 参数: {}", sysUserAddRequestVo);

        SysUserRequestDto requestDto = SysUserRequestDto.build().clone(sysUserAddRequestVo);
        SysUserResponseDto responseDto = sysUserService.saveSysUser(requestDto);

        if (responseDto == null) {
            log.error("新增用户表失败");
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
        }

        log.info("新增用户表成功, ID: {}", responseDto.getId());
        return responseDto.getId();
    }

    /**
     * 修改用户表信息
     * 根据ID更新用户表信息
     * @param sysUserAddRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "修改用户表信息",
        description = "根据ID修改用户表记录",
        request = SysUserAddRequestVo.class,
        response = boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysUser_modify)
    public boolean update( @Validated  SysUserAddRequestVo sysUserAddRequestVo) {

        log.info("修改用户表, 参数: {}", sysUserAddRequestVo);

        if (sysUserAddRequestVo.getId() == null || sysUserAddRequestVo.getId() <= 0) {
            log.warn("修改用户表参数错误, ID: {}", sysUserAddRequestVo.getId());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysUserRequestDto requestDto = SysUserRequestDto.build().clone(sysUserAddRequestVo);
        boolean result = sysUserService.updateSysUser(requestDto);

        log.info("修改用户表完成, ID: {}, 结果: {}", sysUserAddRequestVo.getId(), result);
        return result;
    }

    /**
     * 删除用户表信息
     * 根据ID列表批量删除用户表记录
     * @param idRequestVo ID列表请求参数
     * @return Integer 删除数量
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "删除用户表信息",
        description = "根据ID列表删除用户表记录",
        request = HttpRequestByIdListVo.class,
        response = Integer.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysUser_del)
    public Integer delete( @Validated  HttpRequestByIdListVo idRequestVo) {

        log.info("删除用户表, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除用户表参数错误, ID列表为空");
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        int result = sysUserService.delSysUserByIds(idRequestVo.getIdList());

        log.info("删除用户表完成, 删除数量: {}", result);
        return result;
    }

    /**
     * 用户登录
     * @param sysUserLoginRequestVo 登录参数
     * @return 登录结果
     * @author suven
     */
    @ApiDoc(
        value = "用户登录",
        description = "用户登录接口",
        request = SysUserLoginRequestVo.class,
        response = LoginCodeResponseVo.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysUser_login)
    public Object login( @Validated  SysUserLoginRequestVo sysUserLoginRequestVo) {

        log.info("用户登录, 用户名: {}", sysUserLoginRequestVo.getUsername());

        Object result = sysUserFacade.userLogin(sysUserLoginRequestVo);

        if (result == null) {
            log.warn("用户登录失败, 用户名: {}", sysUserLoginRequestVo.getUsername());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_USER_FAIL);
        }

        log.info("用户登录成功, 用户名: {}", sysUserLoginRequestVo.getUsername());
        return result;
    }

    /**
     * 检验token
     * @param sysUserTokenRequestVo token参数
     * @return boolean 检验结果
     * @author suven
     */
    @ApiDoc(
        value = "检验token",
        description = "检验用户token是否有效",
        request = SysUserTokenRequestVo.class,
        response = boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysUser_checkToken)
    public boolean checkToken( @Validated  SysUserTokenRequestVo sysUserTokenRequestVo) {

        log.info("检验token, 用户名: {}", sysUserTokenRequestVo.getUsername());

        boolean result = sysUserFacade.checkToken(sysUserTokenRequestVo.getUsername(), sysUserTokenRequestVo.getToken());

        log.info("检验token完成, 用户名: {}, 结果: {}", sysUserTokenRequestVo.getUsername(), result);
        return result;
    }

    /**
     * 退出登录
     * @param request 请求
     * @return 退出结果
     * @author suven
     */
    @ApiDoc(
        value = "退出登录",
        description = "用户退出登录",
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_logout)
    public Object logout(HttpServletRequest request) {

        log.info("用户退出登录");

        Object result = sysUserFacade.logout(request);

        log.info("用户退出登录完成");
        return result;
    }

    /**
     * 获取校验码
     * @return 校验码
     * @author suven
     */
    @ApiDoc(
        value = "获取校验码",
        description = "获取登录校验码",
        response = LoginCodeResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_get_check_code)
    public LoginCodeResponseVo getCheckCode() {

        log.info("获取校验码");

        LoginCodeResponseVo vo = sysUserFacade.getCheckCode();

        log.info("获取校验码完成");
        return vo;
    }

    /**
     * 获取图形验证码
     * @return 图形验证码
     * @author suven
     */
    @ApiDoc(
        value = "获取图形验证码",
        description = "获取登录图形验证码",
        response = LoginCodeResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_get_random_image)
    public LoginCodeResponseVo getRandomImage() {

        log.info("获取图形验证码");

        LoginCodeResponseVo vo = sysUserFacade.getCheckCodeImage();

        log.info("获取图形验证码完成");
        return vo;
    }

    /**
     * 修改密码
     * @param userUpdatePwdRequestVo 修改密码参数
     * @return boolean 修改结果
     * @author suven
     */
    @ApiDoc(
        value = "修改密码",
        description = "用户修改密码",
        request = SysUserUpdatePwdRequestVo.class,
        response = boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_user_updatePassword)
    public boolean updatePassword( @Validated  SysUserUpdatePwdRequestVo userUpdatePwdRequestVo) {

        log.info("修改密码, 用户名: {}", userUpdatePwdRequestVo.getUsername());

        Object result = sysUserFacade.updatePassword(userUpdatePwdRequestVo);

        log.info("修改密码完成");
        return result != null;
    }

    /**
     * 批量绑定用户与角色关系
     * @param userRoleIdsRequestVo 绑定参数
     * @return Boolean 绑定结果
     * @author suven
     */
    @ApiDoc(
        value = "批量绑定用户角色",
        description = "指定角色批量绑定用户关系",
        request = SysUserRoleIdsRequestVo.class,
        response = Boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_user_addSysUserRole)
    public Boolean addSysUserRole( @Validated  SysUserRoleIdsRequestVo userRoleIdsRequestVo) {

        log.info("批量绑定用户角色, 角色ID: {}", userRoleIdsRequestVo.getRoleId());

        Boolean result = sysUserFacade.addSysUserRole(userRoleIdsRequestVo);

        log.info("批量绑定用户角色完成, 结果: {}", result);
        return result;
    }

    /**
     * 删除角色用户关系
     * @param userRoleIdsRequestVo 删除参数
     * @return Boolean 删除结果
     * @author suven
     */
    @ApiDoc(
        value = "删除角色用户",
        description = "删除角色用户关系",
        request = SysUserRoleIdsRequestVo.class,
        response = Boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_user_deleteUserRoleBatch)
    public Boolean deleteUserRole( @Validated  SysUserRoleIdsRequestVo userRoleIdsRequestVo) {

        log.info("删除角色用户, 角色ID: {}", userRoleIdsRequestVo.getRoleId());

        Boolean result = sysUserFacade.deleteUserRole(userRoleIdsRequestVo);

        log.info("删除角色用户完成, 结果: {}", result);
        return result;
    }

    /**
     * 删除用户部门关系
     * @param userDepartIdsRequestVo 删除参数
     * @return Boolean 删除结果
     * @author suven
     */
    @ApiDoc(
        value = "删除用户部门",
        description = "删除用户部门关系",
        request = SysUserDepartIdsRequestVo.class,
        response = Boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_user_del_depart)
    public Boolean deleteUserInDepart( @Validated  SysUserDepartIdsRequestVo userDepartIdsRequestVo) {

        log.info("删除用户部门, 用户ID列表: {}", userDepartIdsRequestVo.getUserIdList());

        Boolean result = sysUserFacade.deleteUserInDepart(userDepartIdsRequestVo);

        log.info("删除用户部门完成, 结果: {}", result);
        return result;
    }

    /**
     * 添加部门人员
     * @param userDepartIdsRequestVo 添加参数
     * @return Boolean 添加结果
     * @author suven
     */
    @ApiDoc(
        value = "添加部门人员",
        description = "向部门添加人员",
        request = SysUserDepartIdsRequestVo.class,
        response = Boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_user_editSysDepart)
    public Boolean editSysDepartWithUser( @Validated  SysUserDepartIdsRequestVo userDepartIdsRequestVo) {

        log.info("添加部门人员, 部门ID: {}", userDepartIdsRequestVo.getDepId());

        SysUserDepartRequestDto dto = SysUserDepartRequestDto.build()
                .toDepId(userDepartIdsRequestVo.getDepId())
                .toUserIdList(userDepartIdsRequestVo.getUserIdList());

        Boolean result = sysUserDepartService.editSysDepartWithUser(dto);

        log.info("添加部门人员完成, 结果: {}", result);
        return result;
    }

}
