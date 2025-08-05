package com.suven.framework.sys.controller;



import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.suven.framework.http.data.vo.ResponseResultPageVo;
import com.suven.framework.http.handler.OutputSystem;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.util.excel.ExcelUtils;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.common.enums.SysResultCodeEnum;


import com.suven.framework.sys.service.SysDepartRolePermissionService;
import com.suven.framework.sys.vo.request.SysDepartRolePermissionQueryRequestVo;
import com.suven.framework.sys.vo.request.SysDepartRolePermissionAddRequestVo;
import com.suven.framework.sys.vo.response.SysDepartRolePermissionShowResponseVo;
import com.suven.framework.sys.vo.response.SysDepartRolePermissionResponseVo;

import com.suven.framework.sys.dto.request.SysDepartRolePermissionRequestDto;
import com.suven.framework.sys.dto.response.SysDepartRolePermissionResponseDto;
import com.suven.framework.sys.dto.enums.SysDepartRolePermissionQueryEnum;


/**
 * @ClassName: SysDepartRolePermissionWebController.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:13:36
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门角色权限表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * @RequestMapping("/sys/sysDepartRolePermission")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "部门角色权限表模块"
)
public class SysDepartRolePermissionWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());





    @Autowired
    private SysDepartRolePermissionService  sysDepartRolePermissionService;

    /**
     * @Title: 跳转到部门角色权限表主界面
     * @return 字符串url
     * @author suven
     * @date 2022-02-28 16:13:36
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.sys_sysDepartRolePermission_index,method = RequestMethod.GET)
    //@RequiresPermissions("sys:departrolepermission:list")
    public String index(){
        return "sys/sysDepartRolePermission_index";
    }


    /**
     * @Title: 获取部门角色权限表分页信息
     * Description:sysDepartRolePermissionQueryRequestVo @{Link SysDepartRolePermissionQueryRequestVo}
     * @param
     * @return  ResponseResultPageVo 对象 List<SysDepartRolePermissionShowResponseVo>
     * @throw
     * @author suven
     * @date 2022-02-28 16:13:36
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取部门角色权限表分页信息",
            request = SysDepartRolePermissionQueryRequestVo.class,
            response = SysDepartRolePermissionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartRolePermission_list,method = RequestMethod.GET)
    //@RequiresPermissions("sys:departrolepermission:list")
    public   void   list( OutputSystem out, SysDepartRolePermissionQueryRequestVo sysDepartRolePermissionQueryRequestVo){
            SysDepartRolePermissionRequestDto sysDepartRolePermissionRequestDto = SysDepartRolePermissionRequestDto.build( ).clone(sysDepartRolePermissionQueryRequestVo);

        Pager page =  Pager.build();
        pager.toPageSize(sysDepartRolePermissionQueryRequestVo.getPageSize()).toPageNo(sysDepartRolePermissionQueryRequestVo.getPageNo());
        page.toParamObject(sysDepartRolePermissionRequestDto );
         SysDepartRolePermissionQueryEnum queryEnum =  SysDepartRolePermissionQueryEnum.DESC_ID;
        ResponseResultPageVo<SysDepartRolePermissionResponseDto> resultList = sysDepartRolePermissionService.getSysDepartRolePermissionByNextPage(page,queryEnum);
        if(null == resultList || resultList.getList().isEmpty() ){
            out.write( new ResponseResultPageVo());
            return ;
        }

        List<SysDepartRolePermissionShowResponseVo> listVo = IterableConvert.convertList(resultList.getList(),SysDepartRolePermissionShowResponseVo.class);
        ResponseResultPageVo result = new ResponseResultPageVo()
                .setResult(listVo,page.getSize(),resultList.getTotal())
                .toPageIndex(resultList.getPageIndex());
        out.write( result);
    }

/**
     * @Title: 根据条件查谒部门角色权限表分页信息
     * Description:sysDepartRolePermissionQueryRequestVo @{Link SysDepartRolePermissionQueryRequestVo}
     * @param
     * @return   ResponseResultPageVo 对象 List<SysDepartRolePermissionShowResponseVo>
     * @author suven
     * @date 2022-02-28 16:13:36
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取部门角色权限表分页信息",
            request = SysDepartRolePermissionQueryRequestVo.class,
            response = SysDepartRolePermissionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartRolePermission_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("sys:departrolepermission:query")
    public   void   queryList( OutputSystem out, SysDepartRolePermissionQueryRequestVo sysDepartRolePermissionQueryRequestVo){
            SysDepartRolePermissionRequestDto sysDepartRolePermissionRequestDto = SysDepartRolePermissionRequestDto.build( ).clone(sysDepartRolePermissionQueryRequestVo);

        Pager page =  Pager.build();
        pager.toPageSize(sysDepartRolePermissionQueryRequestVo.getPageSize()).toPageNo(sysDepartRolePermissionQueryRequestVo.getPageNo());
        page.toParamObject(sysDepartRolePermissionRequestDto );
        SysDepartRolePermissionQueryEnum queryEnum =  SysDepartRolePermissionQueryEnum.DESC_ID;
        List<SysDepartRolePermissionResponseDto> resultList = sysDepartRolePermissionService.getSysDepartRolePermissionListByQuery(page,queryEnum);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList<>());
            return ;
        }

        List<SysDepartRolePermissionShowResponseVo> listVo = IterableConvert.convertList(resultList,SysDepartRolePermissionShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * @Title: 新增部门角色权限表信息
     * Description:sysDepartRolePermissionAddRequestVo @{Link SysDepartRolePermissionAddRequestVo}
     * @param sysDepartRolePermissionAddRequestVo 对象
     * @return long类型id
     * @author suven
     * @date 2022-02-28 16:13:36
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增部门角色权限表信息",
            request = SysDepartRolePermissionAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartRolePermission_add,method = RequestMethod.POST)
    //@RequiresPermissions("sys:departrolepermission:add")
    public  void  add(OutputSystem out, SysDepartRolePermissionAddRequestVo sysDepartRolePermissionAddRequestVo){

            SysDepartRolePermissionRequestDto sysDepartRolePermissionRequestDto =  SysDepartRolePermissionRequestDto.build().clone(sysDepartRolePermissionAddRequestVo);

            //sysDepartRolePermissionRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            SysDepartRolePermissionResponseDto sysDepartRolePermissionresponseDto =  sysDepartRolePermissionService.saveSysDepartRolePermission(sysDepartRolePermissionRequestDto);
        if(sysDepartRolePermissionresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( sysDepartRolePermissionresponseDto.getId());
    }
    /**
     * @Title: 修改部门角色权限表信息
     * Description:sysDepartRolePermissionAddRequestVo @{Link SysDepartRolePermissionAddRequestVo}
     * @param  sysDepartRolePermissionAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven
     * @date 2022-02-28 16:13:36
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改部门角色权限表信息",
            request = SysDepartRolePermissionAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartRolePermission_modify , method = RequestMethod.POST)
    //@RequiresPermissions("sys:departrolepermission:modify")
    public  void  modify(OutputSystem out,SysDepartRolePermissionAddRequestVo sysDepartRolePermissionAddRequestVo){

            SysDepartRolePermissionRequestDto sysDepartRolePermissionRequestDto =  SysDepartRolePermissionRequestDto.build().clone(sysDepartRolePermissionAddRequestVo);

        if(sysDepartRolePermissionRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  sysDepartRolePermissionService.updateSysDepartRolePermission(sysDepartRolePermissionRequestDto);
        out.write(result);
    }

    /**
     * @Title: 查看部门角色权限表信息
     * Description:sysDepartRolePermissionRequestVo @{Link SysDepartRolePermissionRequestVo}
     * @param
     * @return  SysDepartRolePermissionResponseVo  对象
     * @author suven
     * @date 2022-02-28 16:13:36
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看部门角色权限表信息",
            request = HttpRequestByIdVo.class,
            response = SysDepartRolePermissionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartRolePermission_detail,method = RequestMethod.GET)
    //@RequiresPermissions("sys:departrolepermission:list")
    public void detail(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysDepartRolePermissionResponseDto sysDepartRolePermissionResponseDto = sysDepartRolePermissionService.getSysDepartRolePermissionById(idRequestVo.getId());
            SysDepartRolePermissionShowResponseVo vo =  SysDepartRolePermissionShowResponseVo.build().clone(sysDepartRolePermissionResponseDto);
        out.write(vo);
    }



    /**
     * @Title: 跳转部门角色权限表编辑界面
     * Description:id @{Link Long}
     * @param
     * @return SysDepartRolePermissionShowResponseVo 对象
     * @author suven
     * @date 2022-02-28 16:13:36
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看部门角色权限表信息",
            request = HttpRequestByIdVo.class,
            response = SysDepartRolePermissionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartRolePermission_edit , method = RequestMethod.GET)
    //@RequiresPermissions("sys:departrolepermission:modify")
    public void edit(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysDepartRolePermissionResponseDto sysDepartRolePermissionResponseDto = sysDepartRolePermissionService.getSysDepartRolePermissionById(idRequestVo.getId());
            SysDepartRolePermissionShowResponseVo vo =  SysDepartRolePermissionShowResponseVo.build().clone(sysDepartRolePermissionResponseDto);
        out.write(vo);

    }




    /**
     * @Title: 跳转部门角色权限表新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven
     * @date 2022-02-28 16:13:36
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.sys_sysDepartRolePermission_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("sys:departrolepermission:add")
    public String newInfo(ModelMap modelMap){
        return "sys/sysDepartRolePermission_edit";
    }

    /**
     * @Title: 删除部门角色权限表信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven
     * @date 2022-02-28 16:13:36
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除部门角色权限表信息",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartRolePermission_del,method = RequestMethod.POST)
    //@RequiresPermissions("sys:departrolepermission:del")
    public  void  del(OutputSystem out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = sysDepartRolePermissionService.delSysDepartRolePermissionByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * @Title: 导出部门角色权限表信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven
     * @date 2022-02-28 16:13:36
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出部门角色权限表信息",
            request = SysDepartRolePermissionQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartRolePermission_export,method = RequestMethod.GET)
    //@RequiresPermissions("sys:departrolepermission:export")
    public void export(HttpServletResponse response, SysDepartRolePermissionQueryRequestVo sysDepartRolePermissionQueryRequestVo){

            SysDepartRolePermissionRequestDto sysDepartRolePermissionRequestDto = SysDepartRolePermissionRequestDto.build().clone(sysDepartRolePermissionQueryRequestVo);

        Pager page =  Pager.build();
        pager.toPageSize(sysDepartRolePermissionQueryRequestVo.getPageSize()).toPageNo(sysDepartRolePermissionQueryRequestVo.getPageNo());
        page.toParamObject(sysDepartRolePermissionRequestDto );

        SysDepartRolePermissionQueryEnum queryEnum =  SysDepartRolePermissionQueryEnum.DESC_ID;
        ResponseResultPageVo<SysDepartRolePermissionResponseDto> resultList = sysDepartRolePermissionService.getSysDepartRolePermissionByNextPage(page,queryEnum);
        List<SysDepartRolePermissionResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysDepartRolePermissionResponseVo.class,data,"导出部门角色权限表信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.sys_sysDepartRolePermission_import, method = RequestMethod.POST)
    //@RequiresPermissions("sys:departrolepermission:import")
    public void importExcel(OutputSystem out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = sysDepartRolePermissionService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}