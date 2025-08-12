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
import com.suven.framework.http.data.vo.PageResult;
import com.suven.framework.http.handler.OutputSystem;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.util.excel.ExcelUtils;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.common.enums.SysResultCodeEnum;


import com.suven.framework.sys.service.SysDepartPermissionService;
import com.suven.framework.sys.vo.request.SysDepartPermissionQueryRequestVo;
import com.suven.framework.sys.vo.request.SysDepartPermissionAddRequestVo;
import com.suven.framework.sys.vo.response.SysDepartPermissionShowResponseVo;
import com.suven.framework.sys.vo.response.SysDepartPermissionResponseVo;

import com.suven.framework.sys.dto.request.SysDepartPermissionRequestDto;
import com.suven.framework.sys.dto.response.SysDepartPermissionResponseDto;
import com.suven.framework.sys.dto.enums.SysDepartPermissionQueryEnum;


/**
 * ClassName: SysDepartPermissionWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:14:27
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门权限表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysDepartPermission")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "部门权限表模块"
)
public class SysDepartPermissionWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());






    @Autowired
    private SysDepartPermissionService  sysDepartPermissionService;

    /**
     * Title: 跳转到部门权限表主界面
     * @return 字符串url
     * @author suven
     * date 2022-02-28 16:14:27
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.sys_sysDepartPermission_index,method = RequestMethod.GET)
    //@RequiresPermissions("sys:departpermission:list")
    public String index(){
        return "sys/sysDepartPermission_index";
    }


    /**
     * Title: 获取部门权限表分页信息
     * Description:sysDepartPermissionQueryRequestVo @{Link SysDepartPermissionQueryRequestVo}
     * @param
     * @return  PageResult 对象 List<SysDepartPermissionShowResponseVo>
     * @throw
     * @author suven
     * date 2022-02-28 16:14:27
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取部门权限表分页信息",
            request = SysDepartPermissionQueryRequestVo.class,
            response = SysDepartPermissionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartPermission_list,method = RequestMethod.GET)
    //@RequiresPermissions("sys:departpermission:list")
    public   void   list( OutputSystem out, SysDepartPermissionQueryRequestVo sysDepartPermissionQueryRequestVo){
            SysDepartPermissionRequestDto sysDepartPermissionRequestDto = SysDepartPermissionRequestDto.build( ).clone(sysDepartPermissionQueryRequestVo);

        Pager page =  Pager.of();
        page.toPageSize(sysDepartPermissionQueryRequestVo.getPageSize()).toPageNo(sysDepartPermissionQueryRequestVo.getPageNo());
        page.toParamObject(sysDepartPermissionRequestDto );
         SysDepartPermissionQueryEnum queryEnum =  SysDepartPermissionQueryEnum.DESC_ID;
        PageResult<SysDepartPermissionResponseDto> resultList = sysDepartPermissionService.getSysDepartPermissionByNextPage(page,queryEnum);
        if(null == resultList || resultList.getList().isEmpty() ){
            out.write( new PageResult());
            return ;
        }

        List<SysDepartPermissionShowResponseVo> listVo = IterableConvert.convertList(resultList.getList(),SysDepartPermissionShowResponseVo.class);
        PageResult result = new PageResult<>();
        result.of(listVo,page.getSize(),resultList.getTotal())
                .toPageIndex(resultList.getPageIndex());
        out.write( result);
    }

/**
     * Title: 根据条件查谒部门权限表分页信息
     * Description:sysDepartPermissionQueryRequestVo @{Link SysDepartPermissionQueryRequestVo}
     * @param
     * @return   PageResult 对象 List<SysDepartPermissionShowResponseVo>
     * @author suven
     * date 2022-02-28 16:14:27
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取部门权限表分页信息",
            request = SysDepartPermissionQueryRequestVo.class,
            response = SysDepartPermissionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartPermission_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("sys:departpermission:query")
    public   void   queryList( OutputSystem out, SysDepartPermissionQueryRequestVo sysDepartPermissionQueryRequestVo){
            SysDepartPermissionRequestDto sysDepartPermissionRequestDto = SysDepartPermissionRequestDto.build( ).clone(sysDepartPermissionQueryRequestVo);

        Pager page =  Pager.of();
        page.toPageSize(sysDepartPermissionQueryRequestVo.getPageSize()).toPageNo(sysDepartPermissionQueryRequestVo.getPageNo());
        page.toParamObject(sysDepartPermissionRequestDto );
        SysDepartPermissionQueryEnum queryEnum =  SysDepartPermissionQueryEnum.DESC_ID;
        List<SysDepartPermissionResponseDto> resultList = sysDepartPermissionService.getSysDepartPermissionListByQuery(page,queryEnum);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList<>());
            return ;
        }

        List<SysDepartPermissionShowResponseVo> listVo = IterableConvert.convertList(resultList,SysDepartPermissionShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * Title: 新增部门权限表信息
     * Description:sysDepartPermissionAddRequestVo @{Link SysDepartPermissionAddRequestVo}
     * @param sysDepartPermissionAddRequestVo 对象
     * @return long类型id
     * @author suven
     * date 2022-02-28 16:14:27
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增部门权限表信息",
            request = SysDepartPermissionAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartPermission_add,method = RequestMethod.POST)
    //@RequiresPermissions("sys:departpermission:add")
    public  void  add(OutputSystem out, SysDepartPermissionAddRequestVo sysDepartPermissionAddRequestVo){

            SysDepartPermissionRequestDto sysDepartPermissionRequestDto =  SysDepartPermissionRequestDto.build().clone(sysDepartPermissionAddRequestVo);

            //sysDepartPermissionRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            SysDepartPermissionResponseDto sysDepartPermissionresponseDto =  sysDepartPermissionService.saveSysDepartPermission(sysDepartPermissionRequestDto);
        if(sysDepartPermissionresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( sysDepartPermissionresponseDto.getId());
    }
    /**
     * Title: 修改部门权限表信息
     * Description:sysDepartPermissionAddRequestVo @{Link SysDepartPermissionAddRequestVo}
     * @param  sysDepartPermissionAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven
     * date 2022-02-28 16:14:27
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改部门权限表信息",
            request = SysDepartPermissionAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartPermission_modify , method = RequestMethod.POST)
    //@RequiresPermissions("sys:departpermission:modify")
    public  void  modify(OutputSystem out,SysDepartPermissionAddRequestVo sysDepartPermissionAddRequestVo){

            SysDepartPermissionRequestDto sysDepartPermissionRequestDto =  SysDepartPermissionRequestDto.build().clone(sysDepartPermissionAddRequestVo);

        if(sysDepartPermissionRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  sysDepartPermissionService.updateSysDepartPermission(sysDepartPermissionRequestDto);
        out.write(result);
    }

    /**
     * Title: 查看部门权限表信息
     * Description:sysDepartPermissionRequestVo @{Link SysDepartPermissionRequestVo}
     * @param
     * @return  SysDepartPermissionResponseVo  对象
     * @author suven
     * date 2022-02-28 16:14:27
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看部门权限表信息",
            request = HttpRequestByIdVo.class,
            response = SysDepartPermissionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartPermission_detail,method = RequestMethod.GET)
    //@RequiresPermissions("sys:departpermission:list")
    public void detail(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysDepartPermissionResponseDto sysDepartPermissionResponseDto = sysDepartPermissionService.getSysDepartPermissionById(idRequestVo.getId());
            SysDepartPermissionShowResponseVo vo =  SysDepartPermissionShowResponseVo.build().clone(sysDepartPermissionResponseDto);
        out.write(vo);
    }



    /**
     * Title: 跳转部门权限表编辑界面
     * Description:id @{Link Long}
     * @param
     * @return SysDepartPermissionShowResponseVo 对象
     * @author suven
     * date 2022-02-28 16:14:27
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看部门权限表信息",
            request = HttpRequestByIdVo.class,
            response = SysDepartPermissionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartPermission_edit , method = RequestMethod.GET)
    //@RequiresPermissions("sys:departpermission:modify")
    public void edit(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysDepartPermissionResponseDto sysDepartPermissionResponseDto = sysDepartPermissionService.getSysDepartPermissionById(idRequestVo.getId());
            SysDepartPermissionShowResponseVo vo =  SysDepartPermissionShowResponseVo.build().clone(sysDepartPermissionResponseDto);
        out.write(vo);

    }




    /**
     * Title: 跳转部门权限表新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven
     * date 2022-02-28 16:14:27
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.sys_sysDepartPermission_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("sys:departpermission:add")
    public String newInfo(ModelMap modelMap){
        return "sys/sysDepartPermission_edit";
    }

    /**
     * Title: 删除部门权限表信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven
     * date 2022-02-28 16:14:27
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除部门权限表信息",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartPermission_del,method = RequestMethod.POST)
    //@RequiresPermissions("sys:departpermission:del")
    public  void  del(OutputSystem out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = sysDepartPermissionService.delSysDepartPermissionByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * Title: 导出部门权限表信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven
     * date 2022-02-28 16:14:27
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出部门权限表信息",
            request = SysDepartPermissionQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartPermission_export,method = RequestMethod.GET)
    //@RequiresPermissions("sys:departpermission:export")
    public void export(HttpServletResponse response, SysDepartPermissionQueryRequestVo sysDepartPermissionQueryRequestVo){

            SysDepartPermissionRequestDto sysDepartPermissionRequestDto = SysDepartPermissionRequestDto.build().clone(sysDepartPermissionQueryRequestVo);

        Pager page =  Pager.of();
        page.toPageSize(sysDepartPermissionQueryRequestVo.getPageSize()).toPageNo(sysDepartPermissionQueryRequestVo.getPageNo());
        page.toParamObject(sysDepartPermissionRequestDto );

        SysDepartPermissionQueryEnum queryEnum =  SysDepartPermissionQueryEnum.DESC_ID;
        PageResult<SysDepartPermissionResponseDto> resultList = sysDepartPermissionService.getSysDepartPermissionByNextPage(page,queryEnum);
        List<SysDepartPermissionResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysDepartPermissionResponseVo.class,data,"导出部门权限表信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.sys_sysDepartPermission_import, method = RequestMethod.POST)
    //@RequiresPermissions("sys:departpermission:import")
    public void importExcel(OutputSystem out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = sysDepartPermissionService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}