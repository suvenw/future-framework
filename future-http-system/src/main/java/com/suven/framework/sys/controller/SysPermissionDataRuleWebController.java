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
import com.suven.framework.http.data.vo.ResponseResultPageVo;
import com.suven.framework.http.handler.OutputSystem;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.util.excel.ExcelUtils;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.common.enums.SysResultCodeEnum;


import com.suven.framework.sys.service.SysPermissionDataRuleService;
import com.suven.framework.sys.vo.request.SysPermissionDataRuleQueryRequestVo;
import com.suven.framework.sys.vo.request.SysPermissionDataRuleAddRequestVo;
import com.suven.framework.sys.vo.response.SysPermissionDataRuleShowResponseVo;
import com.suven.framework.sys.vo.response.SysPermissionDataRuleResponseVo;

import com.suven.framework.sys.dto.request.SysPermissionDataRuleRequestDto;
import com.suven.framework.sys.dto.response.SysPermissionDataRuleResponseDto;
import com.suven.framework.sys.dto.enums.SysPermissionDataRuleQueryEnum;


/**
 * @ClassName: SysPermissionDataRuleWebController.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:35
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 菜单权限规则表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * @RequestMapping("/sys/sysPermissionDataRule")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "菜单权限规则表模块"
)
public class SysPermissionDataRuleWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());





    @Autowired
    private SysPermissionDataRuleService  sysPermissionDataRuleService;

    /**
     * @Title: 跳转到菜单权限规则表主界面
     * @return 字符串url
     * @author suven
     * @date 2022-02-28 16:10:35
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.sys_sysPermissionDataRule_index,method = RequestMethod.GET)
    //@RequiresPermissions("sys:permissiondatarule:list")
    public String index(){
        return "sys/sysPermissionDataRule_index";
    }


    /**
     * @Title: 获取菜单权限规则表分页信息
     * Description:sysPermissionDataRuleQueryRequestVo @{Link SysPermissionDataRuleQueryRequestVo}
     * @param
     * @return  ResponseResultPageVo 对象 List<SysPermissionDataRuleShowResponseVo>
     * @throw
     * @author suven
     * @date 2022-02-28 16:10:35
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取菜单权限规则表分页信息",
            request = SysPermissionDataRuleQueryRequestVo.class,
            response = SysPermissionDataRuleShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPermissionDataRule_list,method = RequestMethod.GET)
    //@RequiresPermissions("sys:permissiondatarule:list")
    public   void   list( OutputSystem out, SysPermissionDataRuleQueryRequestVo sysPermissionDataRuleQueryRequestVo){
            SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto = SysPermissionDataRuleRequestDto.build( ).clone(sysPermissionDataRuleQueryRequestVo);

        Pager page =  Pager.build().toPageSize(sysPermissionDataRuleQueryRequestVo.getPageSize()).toPageNo(sysPermissionDataRuleQueryRequestVo.getPageNo());
        page.toParamObject(sysPermissionDataRuleRequestDto );
         SysPermissionDataRuleQueryEnum queryEnum =  SysPermissionDataRuleQueryEnum.DESC_ID;
        ResponseResultPageVo<SysPermissionDataRuleResponseDto> resultList = sysPermissionDataRuleService.getSysPermissionDataRuleByNextPage(page,queryEnum);
        if(null == resultList || resultList.getList().isEmpty() ){
            out.write( new ResponseResultPageVo());
            return ;
        }

        List<SysPermissionDataRuleShowResponseVo> listVo = IterableConvert.convertList(resultList.getList(),SysPermissionDataRuleShowResponseVo.class);
        ResponseResultPageVo result = new ResponseResultPageVo()
                .setResult(listVo,page.getSize(),resultList.getTotal())
                .toPageIndex(resultList.getPageIndex());
        out.write( result);
    }

/**
     * @Title: 根据条件查谒菜单权限规则表分页信息
     * Description:sysPermissionDataRuleQueryRequestVo @{Link SysPermissionDataRuleQueryRequestVo}
     * @param
     * @return   ResponseResultPageVo 对象 List<SysPermissionDataRuleShowResponseVo>
     * @author suven
     * @date 2022-02-28 16:10:35
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取菜单权限规则表分页信息",
            request = SysPermissionDataRuleQueryRequestVo.class,
            response = SysPermissionDataRuleShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPermissionDataRule_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("sys:permissiondatarule:query")
    public   void   queryList( OutputSystem out, SysPermissionDataRuleQueryRequestVo sysPermissionDataRuleQueryRequestVo){
            SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto = SysPermissionDataRuleRequestDto.build( ).clone(sysPermissionDataRuleQueryRequestVo);

        Pager page =  Pager.build().toPageSize(sysPermissionDataRuleQueryRequestVo.getPageSize()).toPageNo(sysPermissionDataRuleQueryRequestVo.getPageNo());
        page.toParamObject(sysPermissionDataRuleRequestDto );
        SysPermissionDataRuleQueryEnum queryEnum =  SysPermissionDataRuleQueryEnum.DESC_ID;
        List<SysPermissionDataRuleResponseDto> resultList = sysPermissionDataRuleService.getSysPermissionDataRuleListByQuery(page,queryEnum);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList());
            return ;
        }

        List<SysPermissionDataRuleShowResponseVo> listVo = IterableConvert.convertList(resultList,SysPermissionDataRuleShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * @Title: 新增菜单权限规则表信息
     * Description:sysPermissionDataRuleAddRequestVo @{Link SysPermissionDataRuleAddRequestVo}
     * @param sysPermissionDataRuleAddRequestVo 对象
     * @return long类型id
     * @author suven
     * @date 2022-02-28 16:10:35
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增菜单权限规则表信息",
            request = SysPermissionDataRuleAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPermissionDataRule_add,method = RequestMethod.POST)
    //@RequiresPermissions("sys:permissiondatarule:add")
    public  void  add(OutputSystem out, SysPermissionDataRuleAddRequestVo sysPermissionDataRuleAddRequestVo){

            SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto =  SysPermissionDataRuleRequestDto.build().clone(sysPermissionDataRuleAddRequestVo);

            //sysPermissionDataRuleRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            SysPermissionDataRuleResponseDto sysPermissionDataRuleresponseDto =  sysPermissionDataRuleService.saveSysPermissionDataRule(sysPermissionDataRuleRequestDto);
        if(sysPermissionDataRuleresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( sysPermissionDataRuleresponseDto.getId());
    }
    /**
     * @Title: 修改菜单权限规则表信息
     * Description:sysPermissionDataRuleAddRequestVo @{Link SysPermissionDataRuleAddRequestVo}
     * @param  sysPermissionDataRuleAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven
     * @date 2022-02-28 16:10:35
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改菜单权限规则表信息",
            request = SysPermissionDataRuleAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPermissionDataRule_modify , method = RequestMethod.POST)
    //@RequiresPermissions("sys:permissiondatarule:modify")
    public  void  modify(OutputSystem out,SysPermissionDataRuleAddRequestVo sysPermissionDataRuleAddRequestVo){

            SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto =  SysPermissionDataRuleRequestDto.build().clone(sysPermissionDataRuleAddRequestVo);

        if(sysPermissionDataRuleRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  sysPermissionDataRuleService.updateSysPermissionDataRule(sysPermissionDataRuleRequestDto);
        out.write(result);
    }

    /**
     * @Title: 查看菜单权限规则表信息
     * Description:sysPermissionDataRuleRequestVo @{Link SysPermissionDataRuleRequestVo}
     * @param
     * @return  SysPermissionDataRuleResponseVo  对象
     * @author suven
     * @date 2022-02-28 16:10:35
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看菜单权限规则表信息",
            request = HttpRequestByIdVo.class,
            response = SysPermissionDataRuleShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPermissionDataRule_detail,method = RequestMethod.GET)
    //@RequiresPermissions("sys:permissiondatarule:list")
    public void detail(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysPermissionDataRuleResponseDto sysPermissionDataRuleResponseDto = sysPermissionDataRuleService.getSysPermissionDataRuleById(idRequestVo.getId());
            SysPermissionDataRuleShowResponseVo vo =  SysPermissionDataRuleShowResponseVo.build().clone(sysPermissionDataRuleResponseDto);
        out.write(vo);
    }



    /**
     * @Title: 跳转菜单权限规则表编辑界面
     * Description:id @{Link Long}
     * @param
     * @return SysPermissionDataRuleShowResponseVo 对象
     * @author suven
     * @date 2022-02-28 16:10:35
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看菜单权限规则表信息",
            request = HttpRequestByIdVo.class,
            response = SysPermissionDataRuleShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPermissionDataRule_edit , method = RequestMethod.GET)
    //@RequiresPermissions("sys:permissiondatarule:modify")
    public void edit(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysPermissionDataRuleResponseDto sysPermissionDataRuleResponseDto = sysPermissionDataRuleService.getSysPermissionDataRuleById(idRequestVo.getId());
            SysPermissionDataRuleShowResponseVo vo =  SysPermissionDataRuleShowResponseVo.build().clone(sysPermissionDataRuleResponseDto);
        out.write(vo);

    }




    /**
     * @Title: 跳转菜单权限规则表新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven
     * @date 2022-02-28 16:10:35
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.sys_sysPermissionDataRule_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("sys:permissiondatarule:add")
    public String newInfo(ModelMap modelMap){
        return "sys/sysPermissionDataRule_edit";
    }

    /**
     * @Title: 删除菜单权限规则表信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven
     * @date 2022-02-28 16:10:35
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除菜单权限规则表信息",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPermissionDataRule_del,method = RequestMethod.POST)
    //@RequiresPermissions("sys:permissiondatarule:del")
    public  void  del(OutputSystem out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = sysPermissionDataRuleService.delSysPermissionDataRuleByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * @Title: 导出菜单权限规则表信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven
     * @date 2022-02-28 16:10:35
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出菜单权限规则表信息",
            request = SysPermissionDataRuleQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPermissionDataRule_export,method = RequestMethod.GET)
    //@RequiresPermissions("sys:permissiondatarule:export")
    public void export(HttpServletResponse response, SysPermissionDataRuleQueryRequestVo sysPermissionDataRuleQueryRequestVo){

            SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto = SysPermissionDataRuleRequestDto.build().clone(sysPermissionDataRuleQueryRequestVo);

        Pager page =  Pager.build().toPageSize(sysPermissionDataRuleQueryRequestVo.getPageSize()).toPageNo(sysPermissionDataRuleQueryRequestVo.getPageNo());
        page.toParamObject(sysPermissionDataRuleRequestDto );

        SysPermissionDataRuleQueryEnum queryEnum =  SysPermissionDataRuleQueryEnum.DESC_ID;
        ResponseResultPageVo<SysPermissionDataRuleResponseDto> resultList = sysPermissionDataRuleService.getSysPermissionDataRuleByNextPage(page,queryEnum);
        List<SysPermissionDataRuleResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysPermissionDataRuleResponseVo.class,data,"导出菜单权限规则表信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.sys_sysPermissionDataRule_import, method = RequestMethod.POST)
    //@RequiresPermissions("sys:permissiondatarule:import")
    public void importExcel(OutputSystem out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = sysPermissionDataRuleService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}