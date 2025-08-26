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


import com.suven.framework.sys.service.SysDictService;
import com.suven.framework.sys.vo.request.SysDictQueryRequestVo;
import com.suven.framework.sys.vo.request.SysDictAddRequestVo;
import com.suven.framework.sys.vo.response.SysDictShowResponseVo;
import com.suven.framework.sys.vo.response.SysDictResponseVo;

import com.suven.framework.sys.dto.request.SysDictRequestDto;
import com.suven.framework.sys.dto.response.SysDictResponseDto;
import com.suven.framework.sys.dto.enums.SysDictQueryEnum;


/**
 * ClassName: SysDictWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:09
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 后台字典类型表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysDict")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "后台字典类型表模块"
)
public class SysDictWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());





    @Autowired
    private SysDictService  sysDictService;

    /**
     * Title: 跳转到后台字典类型表主界面
     * @return 字符串url
     * @author suven
     * date 2022-02-28 16:10:09
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.sys_sysDict_index,method = RequestMethod.GET)
    //@RequiresPermissions("sys:dict:list")
    public String index(){
        return "sys/sysDict_index";
    }


    /**
     * Title: 获取后台字典类型表分页信息
     * Description:sysDictQueryRequestVo @{Link SysDictQueryRequestVo}
     * @param
     * @return  PageResult 对象 List<SysDictShowResponseVo>
     * @throw
     * @author suven
     * date 2022-02-28 16:10:09
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取后台字典类型表分页信息",
            request = SysDictQueryRequestVo.class,
            response = SysDictShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDict_list,method = RequestMethod.GET)
    //@RequiresPermissions("sys:dict:list")
    public   void   list( OutputSystem out, SysDictQueryRequestVo sysDictQueryRequestVo){
            SysDictRequestDto sysDictRequestDto = SysDictRequestDto.build( ).clone(sysDictQueryRequestVo);

        Pager page =  Pager.of();
        page.toPageSize(sysDictQueryRequestVo.getPageSize()).toPageNo(sysDictQueryRequestVo.getPageNo());
        page.toParamObject(sysDictRequestDto );
         SysDictQueryEnum queryEnum =  SysDictQueryEnum.DESC_ID;
        PageResult<SysDictResponseDto> resultList = sysDictService.getSysDictByNextPage(page,queryEnum);
        if(null == resultList || resultList.getList().isEmpty() ){
            out.write( new PageResult());
            return ;
        }

        List<SysDictShowResponseVo> listVo = IterableConvert.convertList(resultList.getList(),SysDictShowResponseVo.class);
        PageResult<SysDictShowResponseVo> result = new PageResult<>();
        result.of(listVo,page.getSize(),resultList.getTotal())
                .toPageIndex(resultList.getPageIndex());
        out.write( result);
    }

/**
     * Title: 根据条件查谒后台字典类型表分页信息
     * Description:sysDictQueryRequestVo @{Link SysDictQueryRequestVo}
     * @param
     * @return   PageResult 对象 List<SysDictShowResponseVo>
     * @author suven
     * date 2022-02-28 16:10:09
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取后台字典类型表分页信息",
            request = SysDictQueryRequestVo.class,
            response = SysDictShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDict_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("sys:dict:query")
    public   void   queryList( OutputSystem out, SysDictQueryRequestVo sysDictQueryRequestVo){
            SysDictRequestDto sysDictRequestDto = SysDictRequestDto.build( ).clone(sysDictQueryRequestVo);

        Pager page =  Pager.of();
        page.toPageSize(sysDictQueryRequestVo.getPageSize()).toPageNo(sysDictQueryRequestVo.getPageNo());
        page.toParamObject(sysDictRequestDto );
        SysDictQueryEnum queryEnum =  SysDictQueryEnum.DESC_ID;
        List<SysDictResponseDto> resultList = sysDictService.getSysDictListByQuery(page,queryEnum);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList<>());
            return ;
        }

        List<SysDictShowResponseVo> listVo = IterableConvert.convertList(resultList,SysDictShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * Title: 新增后台字典类型表信息
     * Description:sysDictAddRequestVo @{Link SysDictAddRequestVo}
     * @param sysDictAddRequestVo 对象
     * @return long类型id
     * @author suven
     * date 2022-02-28 16:10:09
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增后台字典类型表信息",
            request = SysDictAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDict_add,method = RequestMethod.POST)
    //@RequiresPermissions("sys:dict:add")
    public  void  add(OutputSystem out, SysDictAddRequestVo sysDictAddRequestVo){

            SysDictRequestDto sysDictRequestDto =  SysDictRequestDto.build().clone(sysDictAddRequestVo);

            //sysDictRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            SysDictResponseDto sysDictresponseDto =  sysDictService.saveSysDict(sysDictRequestDto);
        if(sysDictresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( sysDictresponseDto.getId());
    }
    /**
     * Title: 修改后台字典类型表信息
     * Description:sysDictAddRequestVo @{Link SysDictAddRequestVo}
     * @param  sysDictAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven
     * date 2022-02-28 16:10:09
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改后台字典类型表信息",
            request = SysDictAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDict_modify , method = RequestMethod.POST)
    //@RequiresPermissions("sys:dict:modify")
    public  void  modify(OutputSystem out,SysDictAddRequestVo sysDictAddRequestVo){

            SysDictRequestDto sysDictRequestDto =  SysDictRequestDto.build().clone(sysDictAddRequestVo);

        if(sysDictRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  sysDictService.updateSysDict(sysDictRequestDto);
        out.write(result);
    }

    /**
     * Title: 查看后台字典类型表信息
     * Description:sysDictRequestVo @{Link SysDictRequestVo}
     * @param
     * @return  SysDictResponseVo  对象
     * @author suven
     * date 2022-02-28 16:10:09
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看后台字典类型表信息",
            request = HttpRequestByIdVo.class,
            response = SysDictShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDict_detail,method = RequestMethod.GET)
    //@RequiresPermissions("sys:dict:list")
    public void detail(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysDictResponseDto sysDictResponseDto = sysDictService.getSysDictById(idRequestVo.getId());
            SysDictShowResponseVo vo =  SysDictShowResponseVo.build().clone(sysDictResponseDto);
        out.write(vo);
    }



    /**
     * Title: 跳转后台字典类型表编辑界面
     * Description:id @{Link Long}
     * @param
     * @return SysDictShowResponseVo 对象
     * @author suven
     * date 2022-02-28 16:10:09
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看后台字典类型表信息",
            request = HttpRequestByIdVo.class,
            response = SysDictShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDict_edit , method = RequestMethod.GET)
    //@RequiresPermissions("sys:dict:modify")
    public void edit(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysDictResponseDto sysDictResponseDto = sysDictService.getSysDictById(idRequestVo.getId());
            SysDictShowResponseVo vo =  SysDictShowResponseVo.build().clone(sysDictResponseDto);
        out.write(vo);

    }




    /**
     * Title: 跳转后台字典类型表新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven
     * date 2022-02-28 16:10:09
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.sys_sysDict_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("sys:dict:add")
    public String newInfo(ModelMap modelMap){
        return "sys/sysDict_edit";
    }

    /**
     * Title: 删除后台字典类型表信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven
     * date 2022-02-28 16:10:09
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除后台字典类型表信息",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDict_del,method = RequestMethod.POST)
    //@RequiresPermissions("sys:dict:del")
    public  void  del(OutputSystem out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = sysDictService.delSysDictByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * Title: 导出后台字典类型表信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven
     * date 2022-02-28 16:10:09
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出后台字典类型表信息",
            request = SysDictQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDict_export,method = RequestMethod.GET)
    //@RequiresPermissions("sys:dict:export")
    public void export(HttpServletResponse response, SysDictQueryRequestVo sysDictQueryRequestVo){

            SysDictRequestDto sysDictRequestDto = SysDictRequestDto.build().clone(sysDictQueryRequestVo);

        Pager<SysDictRequestDto> page =  Pager.of();
        page.toPageSize(sysDictQueryRequestVo.getPageSize()).toPageNo(sysDictQueryRequestVo.getPageNo());
        page.toParamObject(sysDictRequestDto );

        SysDictQueryEnum queryEnum =  SysDictQueryEnum.DESC_ID;
        PageResult<SysDictResponseDto> resultList = sysDictService.getSysDictByNextPage(page,queryEnum);
        List<SysDictResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysDictResponseVo.class,data,"导出后台字典类型表信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.sys_sysDict_import, method = RequestMethod.POST)
    //@RequiresPermissions("sys:dict:import")
    public void importExcel(OutputSystem out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = sysDictService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}