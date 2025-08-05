package com.suven.framework.sys.controller;


import com.suven.framework.http.data.vo.*;
import com.suven.framework.http.handler.OutputSystem;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.sys.dto.request.SysUserDepartRequestDto;
import com.suven.framework.sys.facade.SysRoleFacade;
import com.suven.framework.sys.facade.SysUserFacade;
import com.suven.framework.sys.service.SysUserDepartService;
import com.suven.framework.sys.service.SysUserRoleService;
import com.suven.framework.sys.vo.request.*;
import com.suven.framework.sys.vo.response.*;
import com.suven.framework.core.ObjectTrue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
// import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import com.suven.framework.core.IterableConvert;
import com.suven.framework.util.excel.ExcelUtils;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.common.enums.SysResultCodeEnum;


import com.suven.framework.sys.service.SysUserService;

import com.suven.framework.sys.dto.request.SysUserRequestDto;
import com.suven.framework.sys.dto.response.SysUserResponseDto;
import com.suven.framework.sys.dto.enums.SysUserQueryEnum;


/**
 * @ClassName: SysUserWebController.java
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:09:37
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * @RequestMapping("/sys/sysUser")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc = DocumentConst.Sys.SYS_DOC_DES,
        module = "用户表模块"
)
public class SysUserWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());


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
     * @return 字符串url
     * @Title: 跳转到用户表主界面
     * @author suven
     * @date 2022-02-28 16:09:37
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.sys_sysUser_index, method = RequestMethod.GET)
    //@RequiresPermissions("sys:user:list")
    public String index() {
        return "sys/sysUser_index";
    }


    /**
     * @param
     * @return ResponseResultList 对象 List<SysUserShowResponseVo>
     * @Title: 获取用户表分页信息
     * Description:sysUserQueryRequestVo @{Link SysUserQueryRequestVo}
     * @throw
     * @author suven
     * @date 2022-02-28 16:09:37
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取用户表分页信息",
            request = SysUserQueryRequestVo.class,
            response = SysUserShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUser_list, method = RequestMethod.GET)
    //@RequiresPermissions("sys:user:list")
    public void list(OutputSystem out, SysUserQueryRequestVo sysUserQueryRequestVo) {
        SysUserRequestDto sysUserRequestDto = SysUserRequestDto.build().clone(sysUserQueryRequestVo);

        Pager page = Pager.build().toPageSize(sysUserQueryRequestVo.getPageSize()).toPageNo(sysUserQueryRequestVo.getPageNo());
        page.toParamObject(sysUserRequestDto);
        SysUserQueryEnum queryEnum = SysUserQueryEnum.DESC_ID;
        ResponseResultList<SysUserResponseDto> resultList = sysUserService.getSysUserByNextPage(page, queryEnum);
        if (null == resultList || resultList.getList().isEmpty()) {
            out.write(ResponseResultList.build());
            return;
        }

        List<SysUserShowResponseVo> listVo = IterableConvert.convertList(resultList.getList(), SysUserShowResponseVo.class);
        ResponseResultList result = ResponseResultList.build()
                .setResult(listVo, page.getSize(), resultList.getTotal())
                .toPageIndex(resultList.getPageIndex());
        out.write(result);
    }

    /**
     * @param
     * @return ResponseResultList 对象 List<SysUserShowResponseVo>
     * @Title: 根据条件查谒用户表分页信息
     * Description:sysUserQueryRequestVo @{Link SysUserQueryRequestVo}
     * @author suven
     * @date 2022-02-28 16:09:37
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取用户表分页信息",
            request = SysUserQueryRequestVo.class,
            response = SysUserShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUser_queryList, method = RequestMethod.GET)
    //@RequiresPermissions("sys:user:list")
    public void queryList(OutputSystem out, SysUserQueryRequestVo sysUserQueryRequestVo) {
        SysUserRequestDto sysUserRequestDto = SysUserRequestDto.build().clone(sysUserQueryRequestVo);

        Pager page = Pager.build().toPageSize(sysUserQueryRequestVo.getPageSize()).toPageNo(sysUserQueryRequestVo.getPageNo());
        page.toParamObject(sysUserRequestDto);
        SysUserQueryEnum queryEnum = SysUserQueryEnum.DESC_ID;
        List<SysUserResponseDto> resultList = sysUserService.getSysUserListByQuery(page, queryEnum);
        if (null == resultList || resultList.isEmpty()) {
            out.write(new ArrayList());
            return;
        }

        List<SysUserShowResponseVo> listVo = IterableConvert.convertList(resultList, SysUserShowResponseVo.class);

        out.write(listVo);
    }


    /**
     * @param sysUserAddRequestVo 对象
     * @return long类型id
     * @Title: 新增用户表信息
     * Description:sysUserAddRequestVo @{Link SysUserAddRequestVo}
     * @author suven
     * @date 2022-02-28 16:09:37
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增用户表信息",
            request = SysUserAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUser_add, method = RequestMethod.POST)
    //@RequiresPermissions("sys:user:add")
    public void add(OutputSystem out, SysUserAddRequestVo sysUserAddRequestVo) {
        SysUserRequestDto requestDto = SysUserRequestDto.build()
                .toUsername(sysUserAddRequestVo.getUsername()).toPhone(sysUserAddRequestVo.getPhone());
        SysUserResponseDto dto = sysUserService.getSysUserByOne(SysUserQueryEnum.USER_NAME_OR_PHONE, requestDto);
        if (dto != null) {
            out.write(SysResultCodeEnum.SYS_USER_NAME_PHONE_EXISTS);
            return;
        }
        SysUserRequestDto sysUserRequestDto = SysUserRequestDto.build().clone(sysUserAddRequestVo);

        //sysUserRequestDto.setStatus(TbStatusEnum.ENABLE.index());
        SysUserResponseDto sysUserresponseDto = sysUserService.saveSysUser(sysUserRequestDto);
        if (sysUserresponseDto == null) {
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }

        if (!ObjectTrue.isEmpty(sysUserAddRequestVo.getRoleIds())) {
            this.userRoleService.editRole(sysUserresponseDto.getId(), sysUserAddRequestVo.getRoleIds());
        }
        out.write(sysUserresponseDto.getId());
    }

    /**
     * @param sysUserAddRequestVo 对象
     * @return boolean 类型1或0;
     * @Title: 修改用户表信息
     * Description:sysUserAddRequestVo @{Link SysUserAddRequestVo}
     * @author suven
     * @date 2022-02-28 16:09:37
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改用户表信息",
            request = SysUserAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUser_modify, method = RequestMethod.POST)
    //@RequiresPermissions("sys:user:modify")
    public void modify(OutputSystem out, SysUserAddRequestVo sysUserAddRequestVo) {
        SysUserRequestDto requestDto = SysUserRequestDto.build()
                .toUsername(sysUserAddRequestVo.getUsername()).toPhone(sysUserAddRequestVo.getPhone());
        SysUserResponseDto dto = sysUserService.getSysUserByOne(SysUserQueryEnum.USER_NAME_OR_PHONE, requestDto);
        if (dto != null) {
            if (dto.getId() != sysUserAddRequestVo.getId()) {
                out.write(SysResultCodeEnum.SYS_USER_NAME_PHONE_EXISTS);
                return;
            }
        }

        SysUserRequestDto sysUserRequestDto = SysUserRequestDto.build().clone(sysUserAddRequestVo);
        sysUserRequestDto.setBirthday(sysUserAddRequestVo.getBirthday());
        if (sysUserRequestDto.getId() == 0L) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        } else {
            if (!ObjectTrue.isEmpty(sysUserAddRequestVo.getRoleIds())) {
                this.userRoleService.editRole(sysUserAddRequestVo.getId(), sysUserAddRequestVo.getRoleIds());
            } else {
                this.userRoleService.deleteAll(sysUserAddRequestVo.getId());
            }

            if (sysUserRequestDto.getId() == 0) {
                out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
                return;
            }
            boolean result = sysUserService.updateSysUser(sysUserRequestDto);
            out.write(result);
        }
    }

    /**
     * @param
     * @return SysUserResponseVo  对象
     * @Title: 查看用户表信息
     * Description:sysUserRequestVo @{Link SysUserRequestVo}
     * @author suven
     * @date 2022-02-28 16:09:37
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看用户表信息",
            request = HttpRequestByIdVo.class,
            response = SysUserShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUser_detail, method = RequestMethod.GET)
    //@RequiresPermissions("sys:user:list")
    public void detail(OutputSystem out, HttpRequestByIdVo idRequestVo) {

        SysUserResponseDto sysUserResponseDto = sysUserService.getSysUserById(idRequestVo.getId());
        SysUserShowResponseVo vo = SysUserShowResponseVo.build().clone(sysUserResponseDto);
        out.write(vo);
    }


    /**
     * @param
     * @return SysUserShowResponseVo 对象
     * @Title: 跳转用户表编辑界面
     * Description:id @{Link Long}
     * @author suven
     * @date 2022-02-28 16:09:37
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看用户表信息",
            request = HttpRequestByIdVo.class,
            response = SysUserShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUser_edit, method = RequestMethod.GET)
    //@RequiresPermissions("sys:user:list")
    public void edit(OutputSystem out, HttpRequestByIdVo idRequestVo) {

        SysUserResponseDto sysUserResponseDto = sysUserService.getSysUserById(idRequestVo.getId());
        SysUserShowResponseVo vo = SysUserShowResponseVo.build().clone(sysUserResponseDto);
        out.write(vo);

    }


    /**
     * @param
     * @return 返回新增加的url
     * @Title: 跳转用户表新增编辑界面
     * Description:id @{Link Long}
     * @author suven
     * @date 2022-02-28 16:09:37
     * --------------------------------------------------------
     * modifyer    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.sys_sysUser_newInfo, method = RequestMethod.GET)
    //@RequiresPermissions("sys:user:add")
    public String newInfo(ModelMap modelMap) {
        return "sys/sysUser_edit";
    }

    /**
     * @param
     * @return boolean 类型1或0;
     * @Title: 删除用户表信息
     * Description:id @{Link Long}
     * @author suven
     * @date 2022-02-28 16:09:37
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除用户表信息",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUser_del, method = RequestMethod.POST)
    //@RequiresPermissions("sys:user:del")
    public void del(OutputSystem out, HttpRequestByIdListVo idRequestVo) {
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        int result = sysUserService.delSysUserByIds(idRequestVo.getIdList());
        out.write(result);
    }


    /**
     * @param
     * @return
     * @Title: 导出用户表信息
     * Description:id @{Link Long}
     * @author suven
     * @date 2022-02-28 16:09:37
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出用户表信息",
            request = SysUserQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUser_export, method = RequestMethod.GET)
    //@RequiresPermissions("sys:user:export")
    public void export(HttpServletResponse response, SysUserQueryRequestVo sysUserQueryRequestVo) {

        SysUserRequestDto sysUserRequestDto = SysUserRequestDto.build().clone(sysUserQueryRequestVo);

        Pager page = Pager.build().toPageSize(sysUserQueryRequestVo.getPageSize()).toPageNo(sysUserQueryRequestVo.getPageNo());
        page.toParamObject(sysUserRequestDto);

        SysUserQueryEnum queryEnum = SysUserQueryEnum.DESC_ID;
        ResponseResultList<SysUserResponseDto> resultList = sysUserService.getSysUserByNextPage(page, queryEnum);
        List<SysUserResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysUserResponseVo.class, data, "导出用户表信息");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


    /**
     * 通过excel导入数据
     *
     * @param out
     * @param files
     */
    @RequestMapping(value = UrlCommand.sys_sysUser_import, method = RequestMethod.POST)
    //@RequiresPermissions("sys:user:import")
    public void importExcel(OutputSystem out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = sysUserService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @ApiDoc(
            value = "登录接口",
            request = SysUserLoginRequestVo.class,
            response = SysDataLogShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUser_login, method = RequestMethod.POST)
//    //@RequiresPermissions("sys:user:login")
    public void login(OutputSystem out, SysUserLoginRequestVo sysUserLoginRequestVo) {
        SysLoginResponseVo result = sysUserFacade.userLogin(sysUserLoginRequestVo);
        if (result == null) {
            out.writeSuccess();
            return;
        }
        out.write(result);
    }


    @ApiDoc(
            value = "检验token",
            request = SysUserTokenRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUser_checkToken, method = RequestMethod.POST)
//    //@RequiresPermissions("sys:user:login")
    public void checkToken(OutputSystem out, SysUserTokenRequestVo sysUserTokenRequestVo) {
        boolean result = sysUserFacade.checkToken(sysUserTokenRequestVo.getUsername(), sysUserTokenRequestVo.getToken());

        out.write(result);
    }

    /**
     * 退出登录
     *
     * @param request
     * @param out
     */
    @RequestMapping(value = UrlCommand.sys_logout, method = RequestMethod.POST)
//    //@RequiresPermissions("sys:user:logout")
    public void logout(HttpServletRequest request, OutputSystem out) {
        IResultCodeEnum msgEnum = sysUserFacade.logout(request);
        out.write(msgEnum);
    }

    /**
     * 获取校验码
     */
    @ApiDoc(
            value = "获取校验码",
            request = RequestParserVo.class,
            response = LoginCodeResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_get_check_code, method = RequestMethod.GET)
//    //@RequiresPermissions("sys:user:getCheckCode")
    public void getCheckCode(OutputSystem out, SysUserDepartIdsRequestVo requestParserVo) {
        LoginCodeResponseVo vo = sysUserFacade.getCheckCode();
        out.write(vo);
    }


    /**
     * 获取后台生成图形验证码 ：有效
     */
    @ApiDoc(
            value = "获取校验码",
            request = RequestParserVo.class,
            response = LoginCodeResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_get_random_image, method = RequestMethod.GET)
//    //@RequiresPermissions("sys:user:getRandomImage")
    public void getRandomImage(OutputSystem out, RequestParserVo requestParserVo) {
        LoginCodeResponseVo vo = sysUserFacade.getCheckCodeImage();
        out.write(vo);
    }


    @ApiDoc(
            value = "启用,上架用户表信息",
            request = HttpRequestByIdVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_user_turnOn, method = RequestMethod.GET)
    //@RequiresPermissions("sys:user:turnOn")
    public void turnOn(OutputSystem out, HttpRequestByIdListVo idRequestVo) {
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result = sysUserService.turnOn(idRequestVo.getIdList());
        out.write(result);
    }


    @ApiDoc(
            value = "下架用户表信息",
            request = HttpRequestByIdVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_user_turnOff, method = RequestMethod.GET)
    //@RequiresPermissions("sys:user:turnOff")
    public void turnOff(OutputSystem out, HttpRequestByIdListVo idRequestVo) {
        if (idRequestVo == null || idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result = sysUserService.turnOff(idRequestVo.getIdList());
        out.write(result);
    }


    /**
     * 查询用户角色信息
     */
    @ApiDoc(
            value = "查询用户角色信息",
            request = HttpRequestByUserIdVo.class,
            response = String.class
    )
    @RequestMapping(value = UrlCommand.sys_user_role, method = RequestMethod.GET)
    //@RequiresPermissions("sys:user:queryUserRole")
    public void queryUserRole(OutputSystem out, HttpRequestByUserIdVo userIdVo) {
        List<UserRoleVo> voList = sysRoleFacade.queryUserRole(userIdVo.getUserId());
        out.write(voList);

    }

    /**
     * 查询用户部门信息
     *
     * @param out
     * @param userDepartRequestVo
     */
    @ApiDoc(
            value = "查询用户角色信息",
            request = SysUserDepartIdsRequestVo.class,
            response = String.class
    )
    @RequestMapping(value = UrlCommand.sys_user_depart, method = RequestMethod.GET)
    //@RequiresPermissions("sys:user:list")
    public void getUserDepartList(OutputSystem out, SysUserDepartIdsRequestVo userDepartRequestVo) {
        ResponseResultList<SysUserResponseDto> list = sysUserService.getUserByDepIdPage(userDepartRequestVo.getDepId());
        out.write(list);
    }

    /**
     * 查询角色用户信息
     *
     * @param out
     * @param sysUserRoleRequestVo
     */
    @RequestMapping(value = UrlCommand.sys_user_userRoleList, method = RequestMethod.GET)
    //@RequiresPermissions("sys:user:queryUserRole")
    public void userRoleList(OutputSystem out, SysUserRoleRequestVo sysUserRoleRequestVo) {
        Pager page = Pager.build().toPageSize(sysUserRoleRequestVo.getPageSize()).toPageNo(sysUserRoleRequestVo.getPageNo());
        ResponseResultList<SysUserResponseDto> dtos = sysUserService.getSysUserRoleId(page, sysUserRoleRequestVo.getRoleId(), sysUserRoleRequestVo.getUsername());
        out.write(dtos);
    }


    /**
     * 批量邦定用户与角色关系接口
     */
    @ApiDoc(
            value = "指定角色批量邦定用户关系接口",
            request = SysUserRoleIdsRequestVo.class,
            response = Boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_user_addSysUserRole, method = RequestMethod.POST)
    //@RequiresPermissions("sys:user:addSysUserRole")
    public void addSysUserRole(OutputSystem out, SysUserRoleIdsRequestVo userDepartRequestVo) {
        Boolean isFlag = sysUserFacade.addSysUserRole(userDepartRequestVo);
        out.write(isFlag);
    }

    /**
     * 删除角色用户
     *
     * @param out
     * @param userDepartRequestVo
     */
    @ApiDoc(
            value = "删除角色用户",
            request = SysUserRoleIdsRequestVo.class,
            response = Boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_user_deleteUserRoleBatch, method = RequestMethod.POST)
    //@RequiresPermissions("sys:user:deleteUserRole")
    public void deleteUserRole(OutputSystem out, SysUserRoleIdsRequestVo userDepartRequestVo) {
        Boolean isFlag = sysUserFacade.deleteUserRole(userDepartRequestVo);
        out.write(isFlag);
    }


    /**
     * 删除用户部门
     */
    @ApiDoc(
            value = "删除用户部门",
            request = SysUserDepartIdsRequestVo.class,
            response = Boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_user_del_depart, method = RequestMethod.POST)
    //@RequiresPermissions("sys:user:deleteUserInDepart")
    public void deleteUserInDepart(OutputSystem out, SysUserDepartIdsRequestVo userDepartRequestVo) {
        Boolean isFlag = sysUserFacade.deleteUserInDepart(userDepartRequestVo);
        out.write(isFlag);
    }

    /**
     * 添加部门人员
     */
    @ApiDoc(
            value = "删除用户部门",
            request = SysUserDepartIdsRequestVo.class,
            response = Boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_user_editSysDepart, method = RequestMethod.POST)
    //@RequiresPermissions("sys:user:editSysDepartWithUser")
    public void editSysDepartWithUser(OutputSystem out, SysUserDepartIdsRequestVo userDepartRequestVo) {
        SysUserDepartRequestDto dto = SysUserDepartRequestDto.build()
                .toDepId(userDepartRequestVo.getDepId())
                .toUserIdList(userDepartRequestVo.getUserIdList());
        Boolean isFlag = sysUserDepartService.editSysDepartWithUser(dto);
        out.write(isFlag);
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = UrlCommand.sys_user_updatePassword, method = RequestMethod.POST)
    //@RequiresPermissions("sys:user:updatePassword")
    public void updatePassword(OutputSystem out, SysUserUpdatePwdRequestVo userUpdatePwdRequestVo) {
        IResultCodeEnum msgEnum = sysUserFacade.updatePassword(userUpdatePwdRequestVo);
        out.write(true);
    }


//    /**
//     * 用户部门列表by用户id
//     *
//     * @param out
//     * @param userIdStr
//     */
//    @RequestMapping(value = "/sys/user/userDepartList", method = RequestMethod.GET)
//    //@RequiresPermissions("sys:user:getUserDepartList")
//    public void getUserDepartList(OutputSystem out, @RequestParam(name = "userId", required = true) String userIdStr) {
//        if (StringUtils.isEmpty(userIdStr)) {
//            out.write(SysResultCodeEnum.SYS_PARAM_CHECK);
//            return;
//        }
//        long userId = Long.valueOf(userIdStr);
//        ResponseResultList result = ResponseResultList.build();
//        List<DepartTreeRespVo> departList = userFacade.getUserDepartList(userId);
//        if (ObjectTrue.isEmpty(departList)) {
//            out.write(SysResultCodeEnum.SYS_USER_DEPART_FIND_FAIL);
//            return;
//        }
//        result.toList(departList);
//        out.write(result);
//    }


    @ApiDoc(
            value = "冻结用户",
            request = HttpRequestStatusVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_user_frozen_batch, method = RequestMethod.POST)
    //@RequiresPermissions("sys:user:frozenBatch")
    public void frozenBatch(OutputSystem out, HttpRequestStatusVo idRequestVo) {
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result = sysUserService.frozenBatch(idRequestVo.getIdList(), idRequestVo.getStatus());
        out.write(result);
    }

    @ApiDoc(
            value = "禁言(解禁)用户",
            request = AllStatusRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_user_handle_muted, method = RequestMethod.POST)
    //@RequiresPermissions("sys:user:handleMuted")
    public void handleMuted(OutputSystem out, AllStatusRequestVo statusReqVo) {
        if (statusReqVo.getId() <= 0) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result = sysUserService.handleMuted(statusReqVo);
        out.write(result);
    }


}