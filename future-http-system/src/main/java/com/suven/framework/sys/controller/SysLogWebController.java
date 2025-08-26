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
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.handler.OutputSystem;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.util.excel.ExcelUtils;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.common.enums.SysResultCodeEnum;


import com.suven.framework.sys.service.SysLogService;
import com.suven.framework.sys.vo.request.SysLogQueryRequestVo;
import com.suven.framework.sys.vo.request.SysLogAddRequestVo;
import com.suven.framework.sys.vo.response.SysLogShowResponseVo;
import com.suven.framework.sys.vo.response.SysLogResponseVo;

import com.suven.framework.sys.dto.request.SysLogRequestDto;
import com.suven.framework.sys.dto.response.SysLogResponseDto;
import com.suven.framework.sys.dto.enums.SysLogQueryEnum;


/**
 * ClassName: SysLogWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:19
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 系统日志表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysLog")
 * </pre>
 * Copyright: (c) 2021 gc by <a href="https://www.suven.top">www.suven.top</a>
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "系统日志表模块"
)
public class SysLogWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());






    @Autowired
    private SysLogService  sysLogService;

    /**
     * Title: 跳转到系统日志表主界面
     * @return 字符串url
     * @author suven
     * date 2022-02-28 16:10:19
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.sys_sysLog_index,method = RequestMethod.GET)
    //@RequiresPermissions("sys:log:list")
    public String index(){
        return "sys/sysLog_index";
    }


    /**
     * Title: 获取系统日志表分页信息
     * Description:sysLogQueryRequestVo @{Link SysLogQueryRequestVo}
     *  PageResult 对象 List<SysLogShowResponseVo>
     * @author suven
     * date 2022-02-28 16:10:19
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取系统日志表分页信息",
            request = SysLogQueryRequestVo.class,
            response = SysLogShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysLog_list,method = RequestMethod.GET)
    //@RequiresPermissions("sys:log:list")
    public   void   list( OutputSystem out, SysLogQueryRequestVo sysLogQueryRequestVo){
            SysLogRequestDto sysLogRequestDto = SysLogRequestDto.build( ).clone(sysLogQueryRequestVo);

        Pager<SysLogRequestDto> page =  Pager.of();
        page.toPageSize(sysLogQueryRequestVo.getPageSize()).toPageNo(sysLogQueryRequestVo.getPageNo());
        page.toParamObject(sysLogRequestDto );
         SysLogQueryEnum queryEnum =  SysLogQueryEnum.DESC_ID;
        PageResult<SysLogResponseDto> resultList = sysLogService.getSysLogByNextPage(page,queryEnum);
        if(null == resultList || resultList.getList().isEmpty() ){
            out.write( new PageResult<>());
            return ;
        }

        List<SysLogShowResponseVo> listVo = IterableConvert.convertList(resultList.getList(),SysLogShowResponseVo.class);
        PageResult<SysLogShowResponseVo> result = new PageResult<>();
        result.of(listVo,page.getSize(),resultList.getTotal())
                .toPageIndex(resultList.getPageIndex());
        out.write( result);
    }

/**
     * Title: 根据条件查谒系统日志表分页信息
     * Description:sysLogQueryRequestVo @{Link SysLogQueryRequestVo}
     * @param
     * @return   PageResult 对象 List<SysLogShowResponseVo>
     * @author suven
     * date 2022-02-28 16:10:19
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取系统日志表分页信息",
            request = SysLogQueryRequestVo.class,
            response = SysLogShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysLog_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("sys:log:query")
    public   void   queryList( OutputSystem out, SysLogQueryRequestVo sysLogQueryRequestVo){
            SysLogRequestDto sysLogRequestDto = SysLogRequestDto.build( ).clone(sysLogQueryRequestVo);

        Pager<SysLogRequestDto> page =  Pager.of();
        page.toPageSize(sysLogQueryRequestVo.getPageSize()).toPageNo(sysLogQueryRequestVo.getPageNo());
        page.toParamObject(sysLogRequestDto );
        SysLogQueryEnum queryEnum =  SysLogQueryEnum.DESC_ID;
        List<SysLogResponseDto> resultList = sysLogService.getSysLogListByQuery(page,queryEnum);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList<>());
            return ;
        }

        List<SysLogShowResponseVo> listVo = IterableConvert.convertList(resultList,SysLogShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * Title: 新增系统日志表信息
     * Description:sysLogAddRequestVo @{Link SysLogAddRequestVo}
     * @param sysLogAddRequestVo 对象
     * @author suven
     * date 2022-02-28 16:10:19
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增系统日志表信息",
            request = SysLogAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.sys_sysLog_add,method = RequestMethod.POST)
    //@RequiresPermissions("sys:log:add")
    public  void  add(OutputSystem out, SysLogAddRequestVo sysLogAddRequestVo){

            SysLogRequestDto sysLogRequestDto =  SysLogRequestDto.build().clone(sysLogAddRequestVo);

            //sysLogRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            SysLogResponseDto sysLogresponseDto =  sysLogService.saveSysLog(sysLogRequestDto);
        if(sysLogresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( sysLogresponseDto.getId());
    }
    /**
     * Title: 修改系统日志表信息
     * Description:sysLogAddRequestVo @{Link SysLogAddRequestVo}
     * @param  sysLogAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven
     * date 2022-02-28 16:10:19
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改系统日志表信息",
            request = SysLogAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysLog_modify , method = RequestMethod.POST)
    //@RequiresPermissions("sys:log:modify")
    public  void  modify(OutputSystem out,SysLogAddRequestVo sysLogAddRequestVo){

            SysLogRequestDto sysLogRequestDto =  SysLogRequestDto.build().clone(sysLogAddRequestVo);

        if(sysLogRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  sysLogService.updateSysLog(sysLogRequestDto);
        out.write(result);
    }

    /**
     * Title: 查看系统日志表信息
     * Description:sysLogRequestVo @{Link SysLogRequestVo}
     * @author suven
     * date 2022-02-28 16:10:19
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看系统日志表信息",
            request = HttpRequestByIdVo.class,
            response = SysLogShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysLog_detail,method = RequestMethod.GET)
    //@RequiresPermissions("sys:log:list")
    public void detail(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysLogResponseDto sysLogResponseDto = sysLogService.getSysLogById(idRequestVo.getId());
            SysLogShowResponseVo vo =  SysLogShowResponseVo.build().clone(sysLogResponseDto);
        out.write(vo);
    }



    /**
     * Title: 跳转系统日志表编辑界面
     * Description:id @{Link Long}
     * @author suven
     * date 2022-02-28 16:10:19
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看系统日志表信息",
            request = HttpRequestByIdVo.class,
            response = SysLogShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysLog_edit , method = RequestMethod.GET)
    //@RequiresPermissions("sys:log:modify")
    public void edit(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysLogResponseDto sysLogResponseDto = sysLogService.getSysLogById(idRequestVo.getId());
            SysLogShowResponseVo vo =  SysLogShowResponseVo.build().clone(sysLogResponseDto);
        out.write(vo);

    }




    /**
     * Title: 跳转系统日志表新增编辑界面
     * Description:id @{Link Long}
     * @return  返回新增加的url
     * @author suven
     * date 2022-02-28 16:10:19
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.sys_sysLog_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("sys:log:add")
    public String newInfo(ModelMap modelMap){
        return "sys/sysLog_edit";
    }

    /**
     * Title: 删除系统日志表信息
     * Description:id @{Link Long}
     * @author suven
     * date 2022-02-28 16:10:19
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除系统日志表信息",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @RequestMapping(value = UrlCommand.sys_sysLog_del,method = RequestMethod.POST)
    //@RequiresPermissions("sys:log:del")
    public  void  del(OutputSystem out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = sysLogService.delSysLogByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * Title: 导出系统日志表信息
     * Description:id @{Link Long}
     * @author suven
     * date 2022-02-28 16:10:19
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出系统日志表信息",
            request = SysLogQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysLog_export,method = RequestMethod.GET)
    //@RequiresPermissions("sys:log:export")
    public void export(HttpServletResponse response, SysLogQueryRequestVo sysLogQueryRequestVo){

            SysLogRequestDto sysLogRequestDto = SysLogRequestDto.build().clone(sysLogQueryRequestVo);

        Pager<SysLogRequestDto> page =  Pager.of();
        page.toPageSize(sysLogQueryRequestVo.getPageSize()).toPageNo(sysLogQueryRequestVo.getPageNo());
        page.toParamObject(sysLogRequestDto );

        SysLogQueryEnum queryEnum =  SysLogQueryEnum.DESC_ID;
        PageResult<SysLogResponseDto> resultList = sysLogService.getSysLogByNextPage(page,queryEnum);
        List<SysLogResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysLogResponseVo.class,data,"导出系统日志表信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out  输出对象
    * @param files 文件对象
    */
    @RequestMapping(value = UrlCommand.sys_sysLog_import, method = RequestMethod.POST)
    //@RequiresPermissions("sys:log:import")
    public void importExcel(OutputSystem out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = sysLogService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}