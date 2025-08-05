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


import com.suven.framework.sys.service.SysDepartRoleUserService;
import com.suven.framework.sys.vo.request.SysDepartRoleUserQueryRequestVo;
import com.suven.framework.sys.vo.request.SysDepartRoleUserAddRequestVo;
import com.suven.framework.sys.vo.response.SysDepartRoleUserShowResponseVo;
import com.suven.framework.sys.vo.response.SysDepartRoleUserResponseVo;

import com.suven.framework.sys.dto.request.SysDepartRoleUserRequestDto;
import com.suven.framework.sys.dto.response.SysDepartRoleUserResponseDto;
import com.suven.framework.sys.dto.enums.SysDepartRoleUserQueryEnum;


/**
 * @ClassName: SysDepartRoleUserWebController.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:14:21
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门角色用户表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * @RequestMapping("/sys/sysDepartRoleUser")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "部门角色用户表模块"
)
public class SysDepartRoleUserWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());






    @Autowired
    private SysDepartRoleUserService  sysDepartRoleUserService;

    /**
     * @Title: 跳转到部门角色用户表主界面
     * @return 字符串url
     * @author suven
     * @date 2022-02-28 16:14:21
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.sys_sysDepartRoleUser_index,method = RequestMethod.GET)
    //@RequiresPermissions("sys:departroleuser:list")
    public String index(){
        return "sys/sysDepartRoleUser_index";
    }


    /**
     * @Title: 获取部门角色用户表分页信息
     * Description:sysDepartRoleUserQueryRequestVo @{Link SysDepartRoleUserQueryRequestVo}
     * @param
     * @return  ResponseResultPageVo 对象 List<SysDepartRoleUserShowResponseVo>
     * @throw
     * @author suven
     * @date 2022-02-28 16:14:21
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取部门角色用户表分页信息",
            request = SysDepartRoleUserQueryRequestVo.class,
            response = SysDepartRoleUserShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartRoleUser_list,method = RequestMethod.GET)
    //@RequiresPermissions("sys:departroleuser:list")
    public   void   list( OutputSystem out, SysDepartRoleUserQueryRequestVo sysDepartRoleUserQueryRequestVo){
            SysDepartRoleUserRequestDto sysDepartRoleUserRequestDto = SysDepartRoleUserRequestDto.build( ).clone(sysDepartRoleUserQueryRequestVo);

        Pager page =  Pager.build().toPageSize(sysDepartRoleUserQueryRequestVo.getPageSize()).toPageNo(sysDepartRoleUserQueryRequestVo.getPageNo());
        page.toParamObject(sysDepartRoleUserRequestDto );
         SysDepartRoleUserQueryEnum queryEnum =  SysDepartRoleUserQueryEnum.DESC_ID;
        ResponseResultPageVo<SysDepartRoleUserResponseDto> resultList = sysDepartRoleUserService.getSysDepartRoleUserByNextPage(page,queryEnum);
        if(null == resultList || resultList.getList().isEmpty() ){
            out.write( new ResponseResultPageVo());
            return ;
        }

        List<SysDepartRoleUserShowResponseVo> listVo = IterableConvert.convertList(resultList.getList(),SysDepartRoleUserShowResponseVo.class);
        ResponseResultPageVo result = new ResponseResultPageVo()
                .setResult(listVo,page.getSize(),resultList.getTotal())
                .toPageIndex(resultList.getPageIndex());
        out.write( result);
    }

/**
     * @Title: 根据条件查谒部门角色用户表分页信息
     * Description:sysDepartRoleUserQueryRequestVo @{Link SysDepartRoleUserQueryRequestVo}
     * @param
     * @return   ResponseResultPageVo 对象 List<SysDepartRoleUserShowResponseVo>
     * @author suven
     * @date 2022-02-28 16:14:21
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取部门角色用户表分页信息",
            request = SysDepartRoleUserQueryRequestVo.class,
            response = SysDepartRoleUserShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartRoleUser_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("sys:departroleuser:query")
    public   void   queryList( OutputSystem out, SysDepartRoleUserQueryRequestVo sysDepartRoleUserQueryRequestVo){
            SysDepartRoleUserRequestDto sysDepartRoleUserRequestDto = SysDepartRoleUserRequestDto.build( ).clone(sysDepartRoleUserQueryRequestVo);

        Pager page =  Pager.build().toPageSize(sysDepartRoleUserQueryRequestVo.getPageSize()).toPageNo(sysDepartRoleUserQueryRequestVo.getPageNo());
        page.toParamObject(sysDepartRoleUserRequestDto );
        SysDepartRoleUserQueryEnum queryEnum =  SysDepartRoleUserQueryEnum.DESC_ID;
        List<SysDepartRoleUserResponseDto> resultList = sysDepartRoleUserService.getSysDepartRoleUserListByQuery(page,queryEnum);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList<>());
            return ;
        }

        List<SysDepartRoleUserShowResponseVo> listVo = IterableConvert.convertList(resultList,SysDepartRoleUserShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * @Title: 新增部门角色用户表信息
     * Description:sysDepartRoleUserAddRequestVo @{Link SysDepartRoleUserAddRequestVo}
     * @param sysDepartRoleUserAddRequestVo 对象
     * @return long类型id
     * @author suven
     * @date 2022-02-28 16:14:21
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增部门角色用户表信息",
            request = SysDepartRoleUserAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartRoleUser_add,method = RequestMethod.POST)
    //@RequiresPermissions("sys:departroleuser:add")
    public  void  add(OutputSystem out, SysDepartRoleUserAddRequestVo sysDepartRoleUserAddRequestVo){

            SysDepartRoleUserRequestDto sysDepartRoleUserRequestDto =  SysDepartRoleUserRequestDto.build().clone(sysDepartRoleUserAddRequestVo);

            //sysDepartRoleUserRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            SysDepartRoleUserResponseDto sysDepartRoleUserresponseDto =  sysDepartRoleUserService.saveSysDepartRoleUser(sysDepartRoleUserRequestDto);
        if(sysDepartRoleUserresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( sysDepartRoleUserresponseDto.getId());
    }
    /**
     * @Title: 修改部门角色用户表信息
     * Description:sysDepartRoleUserAddRequestVo @{Link SysDepartRoleUserAddRequestVo}
     * @param  sysDepartRoleUserAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven
     * @date 2022-02-28 16:14:21
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改部门角色用户表信息",
            request = SysDepartRoleUserAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartRoleUser_modify , method = RequestMethod.POST)
    //@RequiresPermissions("sys:departroleuser:modify")
    public  void  modify(OutputSystem out,SysDepartRoleUserAddRequestVo sysDepartRoleUserAddRequestVo){

            SysDepartRoleUserRequestDto sysDepartRoleUserRequestDto =  SysDepartRoleUserRequestDto.build().clone(sysDepartRoleUserAddRequestVo);

        if(sysDepartRoleUserRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  sysDepartRoleUserService.updateSysDepartRoleUser(sysDepartRoleUserRequestDto);
        out.write(result);
    }

    /**
     * @Title: 查看部门角色用户表信息
     * Description:sysDepartRoleUserRequestVo @{Link SysDepartRoleUserRequestVo}
     * @param
     * @return  SysDepartRoleUserResponseVo  对象
     * @author suven
     * @date 2022-02-28 16:14:21
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看部门角色用户表信息",
            request = HttpRequestByIdVo.class,
            response = SysDepartRoleUserShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartRoleUser_detail,method = RequestMethod.GET)
    //@RequiresPermissions("sys:departroleuser:list")
    public void detail(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysDepartRoleUserResponseDto sysDepartRoleUserResponseDto = sysDepartRoleUserService.getSysDepartRoleUserById(idRequestVo.getId());
            SysDepartRoleUserShowResponseVo vo =  SysDepartRoleUserShowResponseVo.build().clone(sysDepartRoleUserResponseDto);
        out.write(vo);
    }



    /**
     * @Title: 跳转部门角色用户表编辑界面
     * Description:id @{Link Long}
     * @param
     * @return SysDepartRoleUserShowResponseVo 对象
     * @author suven
     * @date 2022-02-28 16:14:21
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看部门角色用户表信息",
            request = HttpRequestByIdVo.class,
            response = SysDepartRoleUserShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartRoleUser_edit , method = RequestMethod.GET)
    //@RequiresPermissions("sys:departroleuser:modify")
    public void edit(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysDepartRoleUserResponseDto sysDepartRoleUserResponseDto = sysDepartRoleUserService.getSysDepartRoleUserById(idRequestVo.getId());
            SysDepartRoleUserShowResponseVo vo =  SysDepartRoleUserShowResponseVo.build().clone(sysDepartRoleUserResponseDto);
        out.write(vo);

    }




    /**
     * @Title: 跳转部门角色用户表新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven
     * @date 2022-02-28 16:14:21
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.sys_sysDepartRoleUser_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("sys:departroleuser:add")
    public String newInfo(ModelMap modelMap){
        return "sys/sysDepartRoleUser_edit";
    }

    /**
     * @Title: 删除部门角色用户表信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven
     * @date 2022-02-28 16:14:21
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除部门角色用户表信息",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartRoleUser_del,method = RequestMethod.POST)
    //@RequiresPermissions("sys:departroleuser:del")
    public  void  del(OutputSystem out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = sysDepartRoleUserService.delSysDepartRoleUserByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * @Title: 导出部门角色用户表信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven
     * @date 2022-02-28 16:14:21
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出部门角色用户表信息",
            request = SysDepartRoleUserQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepartRoleUser_export,method = RequestMethod.GET)
    //@RequiresPermissions("sys:departroleuser:export")
    public void export(HttpServletResponse response, SysDepartRoleUserQueryRequestVo sysDepartRoleUserQueryRequestVo){

            SysDepartRoleUserRequestDto sysDepartRoleUserRequestDto = SysDepartRoleUserRequestDto.build().clone(sysDepartRoleUserQueryRequestVo);

        Pager page =  Pager.build().toPageSize(sysDepartRoleUserQueryRequestVo.getPageSize()).toPageNo(sysDepartRoleUserQueryRequestVo.getPageNo());
        page.toParamObject(sysDepartRoleUserRequestDto );

        SysDepartRoleUserQueryEnum queryEnum =  SysDepartRoleUserQueryEnum.DESC_ID;
        ResponseResultPageVo<SysDepartRoleUserResponseDto> resultList = sysDepartRoleUserService.getSysDepartRoleUserByNextPage(page,queryEnum);
        List<SysDepartRoleUserResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysDepartRoleUserResponseVo.class,data,"导出部门角色用户表信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.sys_sysDepartRoleUser_import, method = RequestMethod.POST)
    //@RequiresPermissions("sys:departroleuser:import")
    public void importExcel(OutputSystem out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = sysDepartRoleUserService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}