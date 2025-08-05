package com.suven.framework.sys.controller;



import com.suven.framework.sys.vo.request.SysUserIdQueryRequestVo;
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


import com.suven.framework.sys.service.SysUserRoleService;
import com.suven.framework.sys.vo.request.SysUserRoleQueryRequestVo;
import com.suven.framework.sys.vo.request.SysUserRoleAddRequestVo;
import com.suven.framework.sys.vo.response.SysUserRoleShowResponseVo;
import com.suven.framework.sys.vo.response.SysUserRoleResponseVo;

import com.suven.framework.sys.dto.request.SysUserRoleRequestDto;
import com.suven.framework.sys.dto.response.SysUserRoleResponseDto;
import com.suven.framework.sys.dto.enums.SysUserRoleQueryEnum;


/**
 * ClassName: SysUserRoleWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:11:27
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户角色关系表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysUserRole")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "用户角色关系表模块"
)
public class SysUserRoleWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());






    @Autowired
    private SysUserRoleService  sysUserRoleService;

    /**
     * Title: 跳转到用户角色关系表主界面
     * @return 字符串url
     * @author suven
     * date 2022-02-28 16:11:27
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.sys_sysUserRole_index,method = RequestMethod.GET)
    //@RequiresPermissions("sys:userrole:list")
    public String index(){
        return "sys/sysUserRole_index";
    }


    /**
     * Title: 获取用户角色关系表分页信息
     * Description:sysUserRoleQueryRequestVo @{Link SysUserRoleQueryRequestVo}
     * @param
     * @return  ResponseResultPageVo 对象 List<SysUserRoleShowResponseVo>
     * @throw
     * @author suven
     * date 2022-02-28 16:11:27
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取用户角色关系表分页信息",
            request = SysUserRoleQueryRequestVo.class,
            response = SysUserRoleShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUserRole_list,method = RequestMethod.GET)
    //@RequiresPermissions("sys:userrole:list")
    public   void   list( OutputSystem out, SysUserRoleQueryRequestVo sysUserRoleQueryRequestVo){
            SysUserRoleRequestDto sysUserRoleRequestDto = SysUserRoleRequestDto.build( ).clone(sysUserRoleQueryRequestVo);

        Pager page =  Pager.build();
        pager.toPageSize(sysUserRoleQueryRequestVo.getPageSize()).toPageNo(sysUserRoleQueryRequestVo.getPageNo());
        page.toParamObject(sysUserRoleRequestDto );
         SysUserRoleQueryEnum queryEnum =  SysUserRoleQueryEnum.DESC_ID;
        ResponseResultPageVo<SysUserRoleResponseDto> resultList = sysUserRoleService.getSysUserRoleByNextPage(page,queryEnum);
        if(null == resultList || resultList.getList().isEmpty() ){
            out.write( new ResponseResultPageVo());
            return ;
        }

        List<SysUserRoleShowResponseVo> listVo = IterableConvert.convertList(resultList.getList(),SysUserRoleShowResponseVo.class);
        ResponseResultPageVo result = new ResponseResultPageVo()
                .setResult(listVo,page.getSize(),resultList.getTotal())
                .toPageIndex(resultList.getPageIndex());
        out.write( result);
    }

/**
     * Title: 根据条件查谒用户角色关系表分页信息
     * Description:sysUserRoleQueryRequestVo @{Link SysUserRoleQueryRequestVo}
     * @param
     * @return   ResponseResultPageVo 对象 List<SysUserRoleShowResponseVo>
     * @author suven
     * date 2022-02-28 16:11:27
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取用户角色关系表分页信息",
            request = SysUserIdQueryRequestVo.class,
            response = SysUserRoleShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUserRole_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("sys:userrole:query")
    public   void   queryList( OutputSystem out, SysUserIdQueryRequestVo sysUserRoleQueryRequestVo){
            SysUserRoleRequestDto sysUserRoleRequestDto = SysUserRoleRequestDto.build( ).clone(sysUserRoleQueryRequestVo);

        Pager page =  Pager.build();
        pager.toPageSize(sysUserRoleQueryRequestVo.getPageSize()).toPageNo(sysUserRoleQueryRequestVo.getPageNo());
        page.toParamObject(sysUserRoleRequestDto );
        SysUserRoleQueryEnum queryEnum =  SysUserRoleQueryEnum.USER_ID;
        List<SysUserRoleResponseDto> resultList = sysUserRoleService.getSysUserRoleListByQuery(page,queryEnum);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList<>());
            return ;
        }

        List<SysUserRoleShowResponseVo> listVo = IterableConvert.convertList(resultList,SysUserRoleShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * Title: 新增用户角色关系表信息
     * Description:sysUserRoleAddRequestVo @{Link SysUserRoleAddRequestVo}
     * @param sysUserRoleAddRequestVo 对象
     * @return long类型id
     * @author suven
     * date 2022-02-28 16:11:27
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增用户角色关系表信息",
            request = SysUserRoleAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUserRole_add,method = RequestMethod.POST)
    //@RequiresPermissions("sys:userrole:add")
    public  void  add(OutputSystem out, SysUserRoleAddRequestVo sysUserRoleAddRequestVo){

            SysUserRoleRequestDto sysUserRoleRequestDto =  SysUserRoleRequestDto.build().clone(sysUserRoleAddRequestVo);

            //sysUserRoleRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            SysUserRoleResponseDto sysUserRoleresponseDto =  sysUserRoleService.saveSysUserRole(sysUserRoleRequestDto);
        if(sysUserRoleresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( sysUserRoleresponseDto.getId());
    }
    /**
     * Title: 修改用户角色关系表信息
     * Description:sysUserRoleAddRequestVo @{Link SysUserRoleAddRequestVo}
     * @param  sysUserRoleAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven
     * date 2022-02-28 16:11:27
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改用户角色关系表信息",
            request = SysUserRoleAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUserRole_modify , method = RequestMethod.POST)
    //@RequiresPermissions("sys:userrole:modify")
    public  void  modify(OutputSystem out,SysUserRoleAddRequestVo sysUserRoleAddRequestVo){

            SysUserRoleRequestDto sysUserRoleRequestDto =  SysUserRoleRequestDto.build().clone(sysUserRoleAddRequestVo);

        if(sysUserRoleRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  sysUserRoleService.updateSysUserRole(sysUserRoleRequestDto);
        out.write(result);
    }

    /**
     * Title: 查看用户角色关系表信息
     * Description:sysUserRoleRequestVo @{Link SysUserRoleRequestVo}
     * @param
     * @return  SysUserRoleResponseVo  对象
     * @author suven
     * date 2022-02-28 16:11:27
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看用户角色关系表信息",
            request = HttpRequestByIdVo.class,
            response = SysUserRoleShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUserRole_detail,method = RequestMethod.GET)
    //@RequiresPermissions("sys:userrole:list")
    public void detail(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysUserRoleResponseDto sysUserRoleResponseDto = sysUserRoleService.getSysUserRoleById(idRequestVo.getId());
            SysUserRoleShowResponseVo vo =  SysUserRoleShowResponseVo.build().clone(sysUserRoleResponseDto);
        out.write(vo);
    }



    /**
     * Title: 跳转用户角色关系表编辑界面
     * Description:id @{Link Long}
     * @param
     * @return SysUserRoleShowResponseVo 对象
     * @author suven
     * date 2022-02-28 16:11:27
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看用户角色关系表信息",
            request = HttpRequestByIdVo.class,
            response = SysUserRoleShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUserRole_edit , method = RequestMethod.GET)
    //@RequiresPermissions("sys:userrole:modify")
    public void edit(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysUserRoleResponseDto sysUserRoleResponseDto = sysUserRoleService.getSysUserRoleById(idRequestVo.getId());
            SysUserRoleShowResponseVo vo =  SysUserRoleShowResponseVo.build().clone(sysUserRoleResponseDto);
        out.write(vo);

    }




    /**
     * Title: 跳转用户角色关系表新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven
     * date 2022-02-28 16:11:27
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.sys_sysUserRole_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("sys:userrole:add")
    public String newInfo(ModelMap modelMap){
        return "sys/sysUserRole_edit";
    }

    /**
     * Title: 删除用户角色关系表信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven
     * date 2022-02-28 16:11:27
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除用户角色关系表信息",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUserRole_del,method = RequestMethod.POST)
    //@RequiresPermissions("sys:userrole:del")
    public  void  del(OutputSystem out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = sysUserRoleService.delSysUserRoleByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * Title: 导出用户角色关系表信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven
     * date 2022-02-28 16:11:27
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出用户角色关系表信息",
            request = SysUserRoleQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUserRole_export,method = RequestMethod.GET)
    //@RequiresPermissions("sys:userrole:export")
    public void export(HttpServletResponse response, SysUserRoleQueryRequestVo sysUserRoleQueryRequestVo){

            SysUserRoleRequestDto sysUserRoleRequestDto = SysUserRoleRequestDto.build().clone(sysUserRoleQueryRequestVo);

        Pager page =  Pager.build();
        pager.toPageSize(sysUserRoleQueryRequestVo.getPageSize()).toPageNo(sysUserRoleQueryRequestVo.getPageNo());
        page.toParamObject(sysUserRoleRequestDto );

        SysUserRoleQueryEnum queryEnum =  SysUserRoleQueryEnum.DESC_ID;
        ResponseResultPageVo<SysUserRoleResponseDto> resultList = sysUserRoleService.getSysUserRoleByNextPage(page,queryEnum);
        List<SysUserRoleResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysUserRoleResponseVo.class,data,"导出用户角色关系表信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.sys_sysUserRole_import, method = RequestMethod.POST)
    //@RequiresPermissions("sys:userrole:import")
    public void importExcel(OutputSystem out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = sysUserRoleService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}