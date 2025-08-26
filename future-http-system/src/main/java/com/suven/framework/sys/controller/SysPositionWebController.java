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


import com.suven.framework.sys.service.SysPositionService;
import com.suven.framework.sys.vo.request.SysPositionQueryRequestVo;
import com.suven.framework.sys.vo.request.SysPositionAddRequestVo;
import com.suven.framework.sys.vo.response.SysPositionShowResponseVo;
import com.suven.framework.sys.vo.response.SysPositionResponseVo;

import com.suven.framework.sys.dto.request.SysPositionRequestDto;
import com.suven.framework.sys.dto.response.SysPositionResponseDto;
import com.suven.framework.sys.dto.enums.SysPositionQueryEnum;


/**
 * ClassName: SysPositionWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:13:52
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description:  的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysPosition")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "模块"
)
public class SysPositionWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());






    @Autowired
    private SysPositionService  sysPositionService;

    /**
     * Title: 跳转到主界面
     * @return 字符串url
     * @author suven
     * date 2022-02-28 16:13:52
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.sys_sysPosition_index,method = RequestMethod.GET)
    //@RequiresPermissions("sys:position:list")
    public String index(){
        return "sys/sysPosition_index";
    }


    /**
     * Title: 获取分页信息
     * Description:sysPositionQueryRequestVo @{Link SysPositionQueryRequestVo}
     * @param
     * @return  PageResult 对象 List<SysPositionShowResponseVo>
     * @throw
     * @author suven
     * date 2022-02-28 16:13:52
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取分页信息",
            request = SysPositionQueryRequestVo.class,
            response = SysPositionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPosition_list,method = RequestMethod.GET)
    //@RequiresPermissions("sys:position:list")
    public   void   list( OutputSystem out, SysPositionQueryRequestVo sysPositionQueryRequestVo){
            SysPositionRequestDto sysPositionRequestDto = SysPositionRequestDto.build( ).clone(sysPositionQueryRequestVo);

        Pager<SysPositionRequestDto> page =  Pager.of();
        page.toPageSize(sysPositionQueryRequestVo.getPageSize()).toPageNo(sysPositionQueryRequestVo.getPageNo());
        page.toParamObject(sysPositionRequestDto );
         SysPositionQueryEnum queryEnum =  SysPositionQueryEnum.DESC_ID;
        PageResult<SysPositionResponseDto> resultList = sysPositionService.getSysPositionByNextPage(page,queryEnum);
        if(null == resultList || resultList.getList().isEmpty() ){
            out.write( new PageResult<>());
            return ;
        }

        List<SysPositionShowResponseVo> listVo = IterableConvert.convertList(resultList.getList(),SysPositionShowResponseVo.class);
        PageResult<SysPositionShowResponseVo> result = new PageResult<>();
        result.of(listVo,page.getSize(),resultList.getTotal())
                .toPageIndex(resultList.getPageIndex());
        out.write( result);
    }

/**
     * Title: 根据条件查谒分页信息
     * Description:sysPositionQueryRequestVo @{Link SysPositionQueryRequestVo}
     * @param
     * @return   PageResult 对象 List<SysPositionShowResponseVo>
     * @author suven
     * date 2022-02-28 16:13:52
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取分页信息",
            request = SysPositionQueryRequestVo.class,
            response = SysPositionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPosition_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("sys:position:query")
    public   void   queryList( OutputSystem out, SysPositionQueryRequestVo sysPositionQueryRequestVo){
            SysPositionRequestDto sysPositionRequestDto = SysPositionRequestDto.build( ).clone(sysPositionQueryRequestVo);

        Pager<SysPositionRequestDto> page =  Pager.of();
        page.toPageSize(sysPositionQueryRequestVo.getPageSize()).toPageNo(sysPositionQueryRequestVo.getPageNo());
        page.toParamObject(sysPositionRequestDto );
        SysPositionQueryEnum queryEnum =  SysPositionQueryEnum.DESC_ID;
        List<SysPositionResponseDto> resultList = sysPositionService.getSysPositionListByQuery(page,queryEnum);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList<>());
            return ;
        }

        List<SysPositionShowResponseVo> listVo = IterableConvert.convertList(resultList,SysPositionShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * Title: 新增信息
     * Description:sysPositionAddRequestVo @{Link SysPositionAddRequestVo}
     * @param sysPositionAddRequestVo 对象
     * @return long类型id
     * @author suven
     * date 2022-02-28 16:13:52
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增信息",
            request = SysPositionAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPosition_add,method = RequestMethod.POST)
    //@RequiresPermissions("sys:position:add")
    public  void  add(OutputSystem out, SysPositionAddRequestVo sysPositionAddRequestVo){

            SysPositionRequestDto sysPositionRequestDto =  SysPositionRequestDto.build().clone(sysPositionAddRequestVo);

            //sysPositionRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            SysPositionResponseDto sysPositionresponseDto =  sysPositionService.saveSysPosition(sysPositionRequestDto);
        if(sysPositionresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( sysPositionresponseDto.getId());
    }
    /**
     * Title: 修改信息
     * Description:sysPositionAddRequestVo @{Link SysPositionAddRequestVo}
     * @param  sysPositionAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven
     * date 2022-02-28 16:13:52
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改信息",
            request = SysPositionAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPosition_modify , method = RequestMethod.POST)
    //@RequiresPermissions("sys:position:modify")
    public  void  modify(OutputSystem out,SysPositionAddRequestVo sysPositionAddRequestVo){

            SysPositionRequestDto sysPositionRequestDto =  SysPositionRequestDto.build().clone(sysPositionAddRequestVo);

        if(sysPositionRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  sysPositionService.updateSysPosition(sysPositionRequestDto);
        out.write(result);
    }

    /**
     * Title: 查看信息
     * Description:sysPositionRequestVo @{Link SysPositionRequestVo}
     * @param
     * @return  SysPositionResponseVo  对象
     * @author suven
     * date 2022-02-28 16:13:52
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = SysPositionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPosition_detail,method = RequestMethod.GET)
    //@RequiresPermissions("sys:position:list")
    public void detail(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysPositionResponseDto sysPositionResponseDto = sysPositionService.getSysPositionById(idRequestVo.getId());
            SysPositionShowResponseVo vo =  SysPositionShowResponseVo.build().clone(sysPositionResponseDto);
        out.write(vo);
    }



    /**
     * Title: 跳转编辑界面
     * Description:id @{Link Long}
     * @param
     * @return SysPositionShowResponseVo 对象
     * @author suven
     * date 2022-02-28 16:13:52
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = SysPositionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPosition_edit , method = RequestMethod.GET)
    //@RequiresPermissions("sys:position:modify")
    public void edit(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysPositionResponseDto sysPositionResponseDto = sysPositionService.getSysPositionById(idRequestVo.getId());
            SysPositionShowResponseVo vo =  SysPositionShowResponseVo.build().clone(sysPositionResponseDto);
        out.write(vo);

    }




    /**
     * Title: 跳转新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven
     * date 2022-02-28 16:13:52
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.sys_sysPosition_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("sys:position:add")
    public String newInfo(ModelMap modelMap){
        return "sys/sysPosition_edit";
    }

    /**
     * Title: 删除信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven
     * date 2022-02-28 16:13:52
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除信息",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPosition_del,method = RequestMethod.POST)
    //@RequiresPermissions("sys:position:del")
    public  void  del(OutputSystem out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = sysPositionService.delSysPositionByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * Title: 导出信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven
     * date 2022-02-28 16:13:52
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出信息",
            request = SysPositionQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPosition_export,method = RequestMethod.GET)
    //@RequiresPermissions("sys:position:export")
    public void export(HttpServletResponse response, SysPositionQueryRequestVo sysPositionQueryRequestVo){

            SysPositionRequestDto sysPositionRequestDto = SysPositionRequestDto.build().clone(sysPositionQueryRequestVo);

        Pager page =  Pager.of();
        page.toPageSize(sysPositionQueryRequestVo.getPageSize()).toPageNo(sysPositionQueryRequestVo.getPageNo());
        page.toParamObject(sysPositionRequestDto );

        SysPositionQueryEnum queryEnum =  SysPositionQueryEnum.DESC_ID;
        PageResult<SysPositionResponseDto> resultList = sysPositionService.getSysPositionByNextPage(page,queryEnum);
        List<SysPositionResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysPositionResponseVo.class,data,"导出信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.sys_sysPosition_import, method = RequestMethod.POST)
    //@RequiresPermissions("sys:position:import")
    public void importExcel(OutputSystem out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = sysPositionService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}