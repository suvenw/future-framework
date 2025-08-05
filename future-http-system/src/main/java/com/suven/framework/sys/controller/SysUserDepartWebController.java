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


import com.suven.framework.sys.service.SysUserDepartService;
import com.suven.framework.sys.vo.request.SysUserDepartQueryRequestVo;
import com.suven.framework.sys.vo.request.SysUserDepartAddRequestVo;
import com.suven.framework.sys.vo.response.SysUserDepartShowResponseVo;
import com.suven.framework.sys.vo.response.SysUserDepartResponseVo;

import com.suven.framework.sys.dto.request.SysUserDepartRequestDto;
import com.suven.framework.sys.dto.response.SysUserDepartResponseDto;
import com.suven.framework.sys.dto.enums.SysUserDepartQueryEnum;


/**
 * ClassName: SysUserDepartWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:14:14
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户部门关系表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysUserDepart")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "用户部门关系表模块"
)
public class SysUserDepartWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());






    @Autowired
    private SysUserDepartService  sysUserDepartService;

    /**
     * Title: 跳转到用户部门关系表主界面
     * @return 字符串url
     * @author suven
     * date 2022-02-28 16:14:14
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.sys_sysUserDepart_index,method = RequestMethod.GET)
    //@RequiresPermissions("sys:userdepart:list")
    public String index(){
        return "sys/sysUserDepart_index";
    }


    /**
     * Title: 获取用户部门关系表分页信息
     * Description:sysUserDepartQueryRequestVo @{Link SysUserDepartQueryRequestVo}
     * @param
     * @return  ResponseResultPageVo 对象 List<SysUserDepartShowResponseVo>
     * @throw
     * @author suven
     * date 2022-02-28 16:14:14
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取用户部门关系表分页信息",
            request = SysUserDepartQueryRequestVo.class,
            response = SysUserDepartShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUserDepart_list,method = RequestMethod.GET)
    //@RequiresPermissions("sys:userdepart:list")
    public   void   list( OutputSystem out, SysUserDepartQueryRequestVo sysUserDepartQueryRequestVo){
            SysUserDepartRequestDto sysUserDepartRequestDto = SysUserDepartRequestDto.build( ).clone(sysUserDepartQueryRequestVo);

        Pager page =  Pager.build();
        pager.toPageSize(sysUserDepartQueryRequestVo.getPageSize()).toPageNo(sysUserDepartQueryRequestVo.getPageNo());
        page.toParamObject(sysUserDepartRequestDto );
         SysUserDepartQueryEnum queryEnum =  SysUserDepartQueryEnum.DESC_ID;
        ResponseResultPageVo<SysUserDepartResponseDto> resultList = sysUserDepartService.getSysUserDepartByNextPage(page,queryEnum);
        if(null == resultList || resultList.getList().isEmpty() ){
            out.write( new ResponseResultPageVo());
            return ;
        }

        List<SysUserDepartShowResponseVo> listVo = IterableConvert.convertList(resultList.getList(),SysUserDepartShowResponseVo.class);
        ResponseResultPageVo result = new ResponseResultPageVo()
                .setResult(listVo,page.getSize(),resultList.getTotal())
                .toPageIndex(resultList.getPageIndex());
        out.write( result);
    }

/**
     * Title: 根据条件查谒用户部门关系表分页信息
     * Description:sysUserDepartQueryRequestVo @{Link SysUserDepartQueryRequestVo}
     * @param
     * @return   ResponseResultPageVo 对象 List<SysUserDepartShowResponseVo>
     * @author suven
     * date 2022-02-28 16:14:14
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取用户部门关系表分页信息",
            request = SysUserDepartQueryRequestVo.class,
            response = SysUserDepartShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUserDepart_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("sys:userdepart:query")
    public   void   queryList( OutputSystem out, SysUserDepartQueryRequestVo sysUserDepartQueryRequestVo){
            SysUserDepartRequestDto sysUserDepartRequestDto = SysUserDepartRequestDto.build( ).clone(sysUserDepartQueryRequestVo);

        Pager page =  Pager.build();
        pager.toPageSize(sysUserDepartQueryRequestVo.getPageSize()).toPageNo(sysUserDepartQueryRequestVo.getPageNo());
        page.toParamObject(sysUserDepartRequestDto );
        SysUserDepartQueryEnum queryEnum =  SysUserDepartQueryEnum.DESC_ID;
        List<SysUserDepartResponseDto> resultList = sysUserDepartService.getSysUserDepartListByQuery(page,queryEnum);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList<>());
            return ;
        }

        List<SysUserDepartShowResponseVo> listVo = IterableConvert.convertList(resultList,SysUserDepartShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * Title: 新增用户部门关系表信息
     * Description:sysUserDepartAddRequestVo @{Link SysUserDepartAddRequestVo}
     * @param sysUserDepartAddRequestVo 对象
     * @return long类型id
     * @author suven
     * date 2022-02-28 16:14:14
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增用户部门关系表信息",
            request = SysUserDepartAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUserDepart_add,method = RequestMethod.POST)
    //@RequiresPermissions("sys:userdepart:add")
    public  void  add(OutputSystem out, SysUserDepartAddRequestVo sysUserDepartAddRequestVo){

            SysUserDepartRequestDto sysUserDepartRequestDto =  SysUserDepartRequestDto.build().clone(sysUserDepartAddRequestVo);

            //sysUserDepartRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            SysUserDepartResponseDto sysUserDepartresponseDto =  sysUserDepartService.saveSysUserDepart(sysUserDepartRequestDto);
        if(sysUserDepartresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( sysUserDepartresponseDto.getId());
    }
    /**
     * Title: 修改用户部门关系表信息
     * Description:sysUserDepartAddRequestVo @{Link SysUserDepartAddRequestVo}
     * @param  sysUserDepartAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven
     * date 2022-02-28 16:14:14
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改用户部门关系表信息",
            request = SysUserDepartAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUserDepart_modify , method = RequestMethod.POST)
    //@RequiresPermissions("sys:userdepart:modify")
    public  void  modify(OutputSystem out,SysUserDepartAddRequestVo sysUserDepartAddRequestVo){

            SysUserDepartRequestDto sysUserDepartRequestDto =  SysUserDepartRequestDto.build().clone(sysUserDepartAddRequestVo);

        if(sysUserDepartRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  sysUserDepartService.updateSysUserDepart(sysUserDepartRequestDto);
        out.write(result);
    }

    /**
     * Title: 查看用户部门关系表信息
     * Description:sysUserDepartRequestVo @{Link SysUserDepartRequestVo}
     * @param
     * @return  SysUserDepartResponseVo  对象
     * @author suven
     * date 2022-02-28 16:14:14
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看用户部门关系表信息",
            request = HttpRequestByIdVo.class,
            response = SysUserDepartShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUserDepart_detail,method = RequestMethod.GET)
    //@RequiresPermissions("sys:userdepart:list")
    public void detail(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysUserDepartResponseDto sysUserDepartResponseDto = sysUserDepartService.getSysUserDepartById(idRequestVo.getId());
            SysUserDepartShowResponseVo vo =  SysUserDepartShowResponseVo.build().clone(sysUserDepartResponseDto);
        out.write(vo);
    }



    /**
     * Title: 跳转用户部门关系表编辑界面
     * Description:id @{Link Long}
     * @param
     * @return SysUserDepartShowResponseVo 对象
     * @author suven
     * date 2022-02-28 16:14:14
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看用户部门关系表信息",
            request = HttpRequestByIdVo.class,
            response = SysUserDepartShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUserDepart_edit , method = RequestMethod.GET)
    //@RequiresPermissions("sys:userdepart:modify")
    public void edit(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysUserDepartResponseDto sysUserDepartResponseDto = sysUserDepartService.getSysUserDepartById(idRequestVo.getId());
            SysUserDepartShowResponseVo vo =  SysUserDepartShowResponseVo.build().clone(sysUserDepartResponseDto);
        out.write(vo);

    }




    /**
     * Title: 跳转用户部门关系表新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven
     * date 2022-02-28 16:14:14
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.sys_sysUserDepart_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("sys:userdepart:add")
    public String newInfo(ModelMap modelMap){
        return "sys/sysUserDepart_edit";
    }

    /**
     * Title: 删除用户部门关系表信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven
     * date 2022-02-28 16:14:14
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除用户部门关系表信息",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUserDepart_del,method = RequestMethod.POST)
    //@RequiresPermissions("sys:userdepart:del")
    public  void  del(OutputSystem out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = sysUserDepartService.delSysUserDepartByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * Title: 导出用户部门关系表信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven
     * date 2022-02-28 16:14:14
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出用户部门关系表信息",
            request = SysUserDepartQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysUserDepart_export,method = RequestMethod.GET)
    //@RequiresPermissions("sys:userdepart:export")
    public void export(HttpServletResponse response, SysUserDepartQueryRequestVo sysUserDepartQueryRequestVo){

            SysUserDepartRequestDto sysUserDepartRequestDto = SysUserDepartRequestDto.build().clone(sysUserDepartQueryRequestVo);

        Pager page =  Pager.build();
        pager.toPageSize(sysUserDepartQueryRequestVo.getPageSize()).toPageNo(sysUserDepartQueryRequestVo.getPageNo());
        page.toParamObject(sysUserDepartRequestDto );

        SysUserDepartQueryEnum queryEnum =  SysUserDepartQueryEnum.DESC_ID;
        ResponseResultPageVo<SysUserDepartResponseDto> resultList = sysUserDepartService.getSysUserDepartByNextPage(page,queryEnum);
        List<SysUserDepartResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysUserDepartResponseVo.class,data,"导出用户部门关系表信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.sys_sysUserDepart_import, method = RequestMethod.POST)
    //@RequiresPermissions("sys:userdepart:import")
    public void importExcel(OutputSystem out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = sysUserDepartService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}