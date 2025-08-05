package com.suven.framework.sys.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
// import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;


import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.http.handler.OutputSystem;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.util.excel.ExcelUtils;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.common.enums.SysResultCodeEnum;


import com.suven.framework.sys.service.SysRolePermissionService;
import com.suven.framework.sys.vo.request.SysRolePermissionQueryRequestVo;
import com.suven.framework.sys.vo.request.SysRolePermissionAddRequestVo;
import com.suven.framework.sys.vo.response.SysRolePermissionShowResponseVo;
import com.suven.framework.sys.vo.response.SysRolePermissionResponseVo;

import com.suven.framework.sys.dto.request.SysRolePermissionRequestDto;
import com.suven.framework.sys.dto.response.SysRolePermissionResponseDto;
import com.suven.framework.sys.dto.enums.SysRolePermissionQueryEnum;


/**
 * @ClassName: SysRolePermissionWebController.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:49
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 角色权限表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * @RequestMapping("/sys/sysRolePermission")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "角色权限表模块"
)
public class SysRolePermissionWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());






    @Autowired
    private SysRolePermissionService  sysRolePermissionService;

    /**
     * @Title: 跳转到角色权限表主界面
     * @return 字符串url
     * @author suven
     * @date 2022-02-28 16:10:49
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.sys_sysRolePermission_index,method = RequestMethod.GET)
    //@RequiresPermissions("sys:rolepermission:list")
    public String index(){
        return "sys/sysRolePermission_index";
    }


    /**
     * @Title: 获取角色权限表分页信息
     * Description:sysRolePermissionQueryRequestVo @{Link SysRolePermissionQueryRequestVo}
     * @param
     * @return  ResponseResultList 对象 List<SysRolePermissionShowResponseVo>
     * @throw
     * @author suven
     * @date 2022-02-28 16:10:49
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取角色权限表分页信息",
            request = SysRolePermissionQueryRequestVo.class,
            response = SysRolePermissionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysRolePermission_list,method = RequestMethod.GET)
    //@RequiresPermissions("sys:rolepermission:list")
    public   void   list( OutputSystem out, SysRolePermissionQueryRequestVo sysRolePermissionQueryRequestVo){
            SysRolePermissionRequestDto sysRolePermissionRequestDto = SysRolePermissionRequestDto.build( ).clone(sysRolePermissionQueryRequestVo);

        Pager page =  Pager.build().toPageSize(sysRolePermissionQueryRequestVo.getPageSize()).toPageNo(sysRolePermissionQueryRequestVo.getPageNo());
        page.toParamObject(sysRolePermissionRequestDto );
         SysRolePermissionQueryEnum queryEnum =  SysRolePermissionQueryEnum.DESC_ID;
        ResponseResultList<SysRolePermissionResponseDto> resultList = sysRolePermissionService.getSysRolePermissionByNextPage(page,queryEnum);
        if(null == resultList || resultList.getList().isEmpty() ){
            out.write( ResponseResultList.build());
            return ;
        }

        List<SysRolePermissionShowResponseVo> listVo = IterableConvert.convertList(resultList.getList(),SysRolePermissionShowResponseVo.class);
        ResponseResultList result = ResponseResultList.build()
                .setResult(listVo,page.getSize(),resultList.getTotal())
                .toPageIndex(resultList.getPageIndex());
        out.write( result);
    }

/**
     * @Title: 根据条件查谒角色权限表分页信息
     * Description:sysRolePermissionQueryRequestVo @{Link SysRolePermissionQueryRequestVo}
     * @param
     * @return   ResponseResultList 对象 List<SysRolePermissionShowResponseVo>
     * @author suven
     * @date 2022-02-28 16:10:49
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取角色权限表分页信息",
            request = SysRolePermissionQueryRequestVo.class,
            response = SysRolePermissionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysRolePermission_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("sys:rolepermission:list")
    public   void   queryList( OutputSystem out, SysRolePermissionQueryRequestVo sysRolePermissionQueryRequestVo){
            SysRolePermissionRequestDto sysRolePermissionRequestDto = SysRolePermissionRequestDto.build( ).clone(sysRolePermissionQueryRequestVo);

        Pager page =  Pager.build().toPageSize(sysRolePermissionQueryRequestVo.getPageSize()).toPageNo(sysRolePermissionQueryRequestVo.getPageNo());
        page.toParamObject(sysRolePermissionRequestDto );
        SysRolePermissionQueryEnum queryEnum =  SysRolePermissionQueryEnum.DESC_ID;
        List<SysRolePermissionResponseDto> resultList = sysRolePermissionService.getSysRolePermissionListByQuery(page,queryEnum);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList());
            return ;
        }

        List<SysRolePermissionShowResponseVo> listVo = IterableConvert.convertList(resultList,SysRolePermissionShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * @Title: 新增角色权限表信息
     * Description:sysRolePermissionAddRequestVo @{Link SysRolePermissionAddRequestVo}
     * @param sysRolePermissionAddRequestVo 对象
     * @return long类型id
     * @author suven
     * @date 2022-02-28 16:10:49
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增角色权限表信息",
            request = SysRolePermissionAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.sys_sysRolePermission_add,method = RequestMethod.POST)
    //@RequiresPermissions("sys:rolepermission:add")
    public  void  add(OutputSystem out, SysRolePermissionAddRequestVo sysRolePermissionAddRequestVo){

            SysRolePermissionRequestDto sysRolePermissionRequestDto =  SysRolePermissionRequestDto.build().clone(sysRolePermissionAddRequestVo);

            //sysRolePermissionRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            SysRolePermissionResponseDto sysRolePermissionresponseDto =  sysRolePermissionService.saveSysRolePermission(sysRolePermissionRequestDto);
        if(sysRolePermissionresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( sysRolePermissionresponseDto.getId());
    }
    /**
     * @Title: 修改角色权限表信息
     * Description:sysRolePermissionAddRequestVo @{Link SysRolePermissionAddRequestVo}
     * @param  sysRolePermissionAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven
     * @date 2022-02-28 16:10:49
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改角色权限表信息",
            request = SysRolePermissionAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysRolePermission_modify , method = RequestMethod.POST)
    //@RequiresPermissions("sys:rolepermission:modify")
    public  void  modify(OutputSystem out,SysRolePermissionAddRequestVo sysRolePermissionAddRequestVo){

            SysRolePermissionRequestDto sysRolePermissionRequestDto =  SysRolePermissionRequestDto.build().clone(sysRolePermissionAddRequestVo);

        if(sysRolePermissionRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  sysRolePermissionService.updateSysRolePermission(sysRolePermissionRequestDto);
        out.write(result);
    }

    /**
     * @Title: 查看角色权限表信息
     * Description:sysRolePermissionRequestVo @{Link SysRolePermissionRequestVo}
     * @param
     * @return  SysRolePermissionResponseVo  对象
     * @author suven
     * @date 2022-02-28 16:10:49
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看角色权限表信息",
            request = HttpRequestByIdVo.class,
            response = SysRolePermissionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysRolePermission_detail,method = RequestMethod.GET)
    //@RequiresPermissions("sys:rolepermission:list")
    public void detail(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysRolePermissionResponseDto sysRolePermissionResponseDto = sysRolePermissionService.getSysRolePermissionById(idRequestVo.getId());
            SysRolePermissionShowResponseVo vo =  SysRolePermissionShowResponseVo.build().clone(sysRolePermissionResponseDto);
        out.write(vo);
    }



    /**
     * @Title: 跳转角色权限表编辑界面
     * Description:id @{Link Long}
     * @param
     * @return SysRolePermissionShowResponseVo 对象
     * @author suven
     * @date 2022-02-28 16:10:49
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看角色权限表信息",
            request = HttpRequestByIdVo.class,
            response = SysRolePermissionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysRolePermission_edit , method = RequestMethod.GET)
    //@RequiresPermissions("sys:rolepermission:modify")
    public void edit(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysRolePermissionResponseDto sysRolePermissionResponseDto = sysRolePermissionService.getSysRolePermissionById(idRequestVo.getId());
            SysRolePermissionShowResponseVo vo =  SysRolePermissionShowResponseVo.build().clone(sysRolePermissionResponseDto);
        out.write(vo);

    }




    /**
     * @Title: 跳转角色权限表新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven
     * @date 2022-02-28 16:10:49
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.sys_sysRolePermission_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("sys:rolepermission:add")
    public String newInfo(ModelMap modelMap){
        return "sys/sysRolePermission_edit";
    }

    /**
     * @Title: 删除角色权限表信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven
     * @date 2022-02-28 16:10:49
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除角色权限表信息",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @RequestMapping(value = UrlCommand.sys_sysRolePermission_del,method = RequestMethod.POST)
    //@RequiresPermissions("sys:rolepermission:del")
    public  void  del(OutputSystem out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = sysRolePermissionService.delSysRolePermissionByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * @Title: 导出角色权限表信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven
     * @date 2022-02-28 16:10:49
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出角色权限表信息",
            request = SysRolePermissionQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysRolePermission_export,method = RequestMethod.GET)
    //@RequiresPermissions("sys:rolepermission:export")
    public void export(HttpServletResponse response, SysRolePermissionQueryRequestVo sysRolePermissionQueryRequestVo){

            SysRolePermissionRequestDto sysRolePermissionRequestDto = SysRolePermissionRequestDto.build().clone(sysRolePermissionQueryRequestVo);

        Pager page =  Pager.build().toPageSize(sysRolePermissionQueryRequestVo.getPageSize()).toPageNo(sysRolePermissionQueryRequestVo.getPageNo());
        page.toParamObject(sysRolePermissionRequestDto );

        SysRolePermissionQueryEnum queryEnum =  SysRolePermissionQueryEnum.DESC_ID;
        ResponseResultList<SysRolePermissionResponseDto> resultList = sysRolePermissionService.getSysRolePermissionByNextPage(page,queryEnum);
        List<SysRolePermissionResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysRolePermissionResponseVo.class,data,"导出角色权限表信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.sys_sysRolePermission_import, method = RequestMethod.POST)
    //@RequiresPermissions("sys:rolepermission:import")
    public void importExcel(OutputSystem out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = sysRolePermissionService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}