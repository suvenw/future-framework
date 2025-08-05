package com.suven.framework.sys.controller;



import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.sys.vo.request.SysRoleQueryCheckRequestVo;
// import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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


import com.suven.framework.sys.service.SysRoleService;
import com.suven.framework.sys.vo.request.SysRoleQueryRequestVo;
import com.suven.framework.sys.vo.request.SysRoleAddRequestVo;
import com.suven.framework.sys.vo.response.SysRoleShowResponseVo;
import com.suven.framework.sys.vo.response.SysRoleResponseVo;

import com.suven.framework.sys.dto.request.SysRoleRequestDto;
import com.suven.framework.sys.dto.response.SysRoleResponseDto;
import com.suven.framework.sys.dto.enums.SysRoleQueryEnum;


/**
 * @ClassName: SysRoleWebController.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:43
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 角色表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * @RequestMapping("/sys/sysRole")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "角色表模块"
)
public class SysRoleWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());





    @Autowired
    private SysRoleService  sysRoleService;


    /**
     * @Title: 跳转到角色表主界面
     * @return 字符串url
     * @author suven
     * @date 2022-02-28 16:10:43
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.sys_sysRole_index,method = RequestMethod.GET)
    //@RequiresPermissions("sys:role:list")
    public String index(){
        return "sys/sysRole_index";
    }


    /**
     * @Title: 获取角色表分页信息
     * Description:sysRoleQueryRequestVo @{Link SysRoleQueryRequestVo}
     * @param
     * @return  ResponseResultPageVo 对象 List<SysRoleShowResponseVo>
     * @throw
     * @author suven
     * @date 2022-02-28 16:10:43
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取角色表分页信息",
            request = SysRoleQueryRequestVo.class,
            response = SysRoleShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysRole_list,method = RequestMethod.GET)
    //@RequiresPermissions("sys:role:list")
    public   void   list( OutputSystem out, SysRoleQueryRequestVo sysRoleQueryRequestVo){
            SysRoleRequestDto sysRoleRequestDto = SysRoleRequestDto.build( ).clone(sysRoleQueryRequestVo);

        Pager page =  Pager.build().toPageSize(sysRoleQueryRequestVo.getPageSize()).toPageNo(sysRoleQueryRequestVo.getPageNo());
        page.toParamObject(sysRoleRequestDto );
         SysRoleQueryEnum queryEnum =  SysRoleQueryEnum.DESC_ID;
        ResponseResultPageVo<SysRoleResponseDto> resultList = sysRoleService.getSysRoleByNextPage(page,queryEnum);
        if(null == resultList || resultList.getList().isEmpty() ){
            out.write( new ResponseResultPageVo());
            return ;
        }

        List<SysRoleShowResponseVo> listVo = IterableConvert.convertList(resultList.getList(),SysRoleShowResponseVo.class);
        ResponseResultPageVo result = new ResponseResultPageVo()
                .setResult(listVo,page.getSize(),resultList.getTotal())
                .toPageIndex(resultList.getPageIndex());
        out.write( result);
    }

/**
     * @Title: 根据条件查谒角色表分页信息
     * Description:sysRoleQueryRequestVo @{Link SysRoleQueryRequestVo}
     * @param
     * @return   ResponseResultPageVo 对象 List<SysRoleShowResponseVo>
     * @author suven
     * @date 2022-02-28 16:10:43
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取角色表分页信息",
            request = SysRoleQueryRequestVo.class,
            response = SysRoleShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysRole_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("sys:role:list")
    public   void   queryList( OutputSystem out, SysRoleQueryRequestVo sysRoleQueryRequestVo){
            SysRoleRequestDto sysRoleRequestDto = SysRoleRequestDto.build( ).clone(sysRoleQueryRequestVo);

        Pager page =  Pager.build().toPageSize(sysRoleQueryRequestVo.getPageSize()).toPageNo(sysRoleQueryRequestVo.getPageNo());
        page.toParamObject(sysRoleRequestDto );
        SysRoleQueryEnum queryEnum =  SysRoleQueryEnum.DESC_ID;
        List<SysRoleResponseDto> resultList = sysRoleService.getSysRoleListByQuery(page,queryEnum);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList<>());
            return ;
        }

        List<SysRoleShowResponseVo> listVo = IterableConvert.convertList(resultList,SysRoleShowResponseVo.class);

        out.write( listVo);
    }


    /**
     * @Title: 新增角色表信息
     * Description:sysRoleAddRequestVo @{Link SysRoleAddRequestVo}
     * @param sysRoleAddRequestVo 对象
     * @return long类型id
     * @author suven
     * @date 2022-02-28 16:10:43
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增角色表信息",
            request = SysRoleAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.sys_sysRole_add,method = RequestMethod.POST)
    //@RequiresPermissions("sys:role:add")
    public  void  add(OutputSystem out, SysRoleAddRequestVo sysRoleAddRequestVo){

            SysRoleRequestDto sysRoleRequestDto =  SysRoleRequestDto.build().clone(sysRoleAddRequestVo);

            //sysRoleRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            SysRoleResponseDto sysRoleresponseDto =  sysRoleService.saveSysRole(sysRoleRequestDto);
        if(sysRoleresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( sysRoleresponseDto.getId());
    }
    /**
     * @Title: 修改角色表信息
     * Description:sysRoleAddRequestVo @{Link SysRoleAddRequestVo}
     * @param  sysRoleAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven
     * @date 2022-02-28 16:10:43
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改角色表信息",
            request = SysRoleAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysRole_modify , method = RequestMethod.POST)
    //@RequiresPermissions("sys:role:modify")
    public  void  modify(OutputSystem out,SysRoleAddRequestVo sysRoleAddRequestVo){

            SysRoleRequestDto sysRoleRequestDto =  SysRoleRequestDto.build().clone(sysRoleAddRequestVo);

        if(sysRoleRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  sysRoleService.updateSysRole(sysRoleRequestDto);
        out.write(result);
    }

    /**
     * @Title: 查看角色表信息
     * Description:sysRoleRequestVo @{Link SysRoleRequestVo}
     * @param
     * @return  SysRoleResponseVo  对象
     * @author suven
     * @date 2022-02-28 16:10:43
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看角色表信息",
            request = HttpRequestByIdVo.class,
            response = SysRoleShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysRole_detail,method = RequestMethod.GET)
    //@RequiresPermissions("sys:role:list")
    public void detail(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysRoleResponseDto sysRoleResponseDto = sysRoleService.getSysRoleById(idRequestVo.getId());
            SysRoleShowResponseVo vo =  SysRoleShowResponseVo.build().clone(sysRoleResponseDto);
        out.write(vo);
    }



    /**
     * @Title: 跳转角色表编辑界面
     * Description:id @{Link Long}
     * @param
     * @return SysRoleShowResponseVo 对象
     * @author suven
     * @date 2022-02-28 16:10:43
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看角色表信息",
            request = HttpRequestByIdVo.class,
            response = SysRoleShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysRole_edit , method = RequestMethod.GET)
    //@RequiresPermissions("sys:role:list")
    public void edit(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysRoleResponseDto sysRoleResponseDto = sysRoleService.getSysRoleById(idRequestVo.getId());
            SysRoleShowResponseVo vo =  SysRoleShowResponseVo.build().clone(sysRoleResponseDto);
        out.write(vo);

    }




    /**
     * @Title: 跳转角色表新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven
     * @date 2022-02-28 16:10:43
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.sys_sysRole_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("sys:role:add")
    public String newInfo(ModelMap modelMap){
        return "sys/sysRole_edit";
    }

    /**
     * @Title: 删除角色表信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven
     * @date 2022-02-28 16:10:43
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除角色表信息",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @RequestMapping(value = UrlCommand.sys_sysRole_del,method = RequestMethod.POST)
    //@RequiresPermissions("sys:role:del")
    public  void  del(OutputSystem out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = sysRoleService.delSysRoleByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * @Title: 导出角色表信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven
     * @date 2022-02-28 16:10:43
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出角色表信息",
            request = SysRoleQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysRole_export,method = RequestMethod.GET)
    //@RequiresPermissions("sys:role:export")
    public void export(HttpServletResponse response, SysRoleQueryRequestVo sysRoleQueryRequestVo){

            SysRoleRequestDto sysRoleRequestDto = SysRoleRequestDto.build().clone(sysRoleQueryRequestVo);

        Pager page =  Pager.build().toPageSize(sysRoleQueryRequestVo.getPageSize()).toPageNo(sysRoleQueryRequestVo.getPageNo());
        page.toParamObject(sysRoleRequestDto );

        SysRoleQueryEnum queryEnum =  SysRoleQueryEnum.DESC_ID;
        ResponseResultPageVo<SysRoleResponseDto> resultList = sysRoleService.getSysRoleByNextPage(page,queryEnum);
        List<SysRoleResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysRoleResponseVo.class,data,"导出角色表信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.sys_sysRole_import, method = RequestMethod.POST)
    //@RequiresPermissions("sys:role:import")
    public void importExcel(OutputSystem out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = sysRoleService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 校验角色编码唯一
     */
    @ApiDoc(
            value = "校验角色编码唯一",
            request = SysRoleQueryCheckRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysRole_checkRoleCode,method = RequestMethod.GET)
    //@RequiresPermissions("sys:role:list")
    public  void  checkRoleCode(OutputSystem out, SysRoleQueryCheckRequestVo idRequestVo){
        logger.info("--验证角色编码是否唯一---id:"+idRequestVo.getId()+"--roleCode:"+idRequestVo.getRoleCode());
        SysRoleResponseDto role = null;
        if(idRequestVo.getId() > 0) {
            role = sysRoleService.getSysRoleById(idRequestVo.getId());
        }
        SysRoleRequestDto sysRoleRequestDto = SysRoleRequestDto.build().toRoleCode(idRequestVo.getRoleCode());
        SysRoleResponseDto newRole =  sysRoleService.getSysRoleByOne(SysRoleQueryEnum.ROLE_CODE,sysRoleRequestDto);
        if(newRole != null) {
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_USER_ROLE_EXISTS);
        }
        out.write(true);
    }

}