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
import com.suven.framework.http.data.vo.PageResult;
import com.suven.framework.http.handler.OutputSystem;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.util.excel.ExcelUtils;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.common.enums.SysResultCodeEnum;


import com.suven.framework.sys.service.SysDictItemService;
import com.suven.framework.sys.vo.request.SysDictItemQueryRequestVo;
import com.suven.framework.sys.vo.request.SysDictItemAddRequestVo;
import com.suven.framework.sys.vo.response.SysDictItemShowResponseVo;
import com.suven.framework.sys.vo.response.SysDictItemResponseVo;

import com.suven.framework.sys.dto.request.SysDictItemRequestDto;
import com.suven.framework.sys.dto.response.SysDictItemResponseDto;
import com.suven.framework.sys.dto.enums.SysDictItemQueryEnum;


/**
 * ClassName: SysDictItemWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:15
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 数据字典明细表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysDictItem")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "数据字典明细表模块"
)
public class SysDictItemWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());






    @Autowired
    private SysDictItemService  sysDictItemService;

    /**
     * Title: 跳转到数据字典明细表主界面
     * @return 字符串url
     * @author suven
     * date 2022-02-28 16:10:15
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.sys_sysDictItem_index,method = RequestMethod.GET)
    //@RequiresPermissions("sys:dictitem:list")
    public String index(){
        return "sys/sysDictItem_index";
    }


    /**
     * Title: 获取数据字典明细表分页信息
     * Description:sysDictItemQueryRequestVo @{Link SysDictItemQueryRequestVo}
     * @param
     * @return  PageResult 对象 List<SysDictItemShowResponseVo>
     * @throw
     * @author suven
     * date 2022-02-28 16:10:15
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取数据字典明细表分页信息",
            request = SysDictItemQueryRequestVo.class,
            response = SysDictItemShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDictItem_list,method = RequestMethod.GET)
    //@RequiresPermissions("sys:dictitem:list")
    public   void   list( OutputSystem out, SysDictItemQueryRequestVo sysDictItemQueryRequestVo){
            SysDictItemRequestDto sysDictItemRequestDto = SysDictItemRequestDto.build( ).clone(sysDictItemQueryRequestVo);

        Pager page =  Pager.of();
        page.toPageSize(sysDictItemQueryRequestVo.getPageSize()).toPageNo(sysDictItemQueryRequestVo.getPageNo());
        page.toParamObject(sysDictItemRequestDto );
         SysDictItemQueryEnum queryEnum =  SysDictItemQueryEnum.DESC_ID;
        PageResult<SysDictItemResponseDto> resultList = sysDictItemService.getSysDictItemByNextPage(page,queryEnum);
        if(null == resultList || resultList.getList().isEmpty() ){
            out.write( new PageResult());
            return ;
        }

        List<SysDictItemShowResponseVo> listVo = IterableConvert.convertList(resultList.getList(),SysDictItemShowResponseVo.class);
        PageResult result = new PageResult<>();
        result.of(listVo,page.getSize(),resultList.getTotal())
                .toPageIndex(resultList.getPageIndex());
        out.write( result);
    }

/**
     * Title: 根据条件查谒数据字典明细表分页信息
     * Description:sysDictItemQueryRequestVo @{Link SysDictItemQueryRequestVo}
     * @param
     * @return   PageResult 对象 List<SysDictItemShowResponseVo>
     * @author suven
     * date 2022-02-28 16:10:15
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取数据字典明细表分页信息",
            request = SysDictItemQueryRequestVo.class,
            response = SysDictItemShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDictItem_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("sys:dictitem:query")
    public   void   queryList( OutputSystem out, SysDictItemQueryRequestVo sysDictItemQueryRequestVo){
            SysDictItemRequestDto sysDictItemRequestDto = SysDictItemRequestDto.build( ).clone(sysDictItemQueryRequestVo);

        Pager page =  Pager.of();
        page.toPageSize(sysDictItemQueryRequestVo.getPageSize()).toPageNo(sysDictItemQueryRequestVo.getPageNo());
        page.toParamObject(sysDictItemRequestDto );
        SysDictItemQueryEnum queryEnum =  SysDictItemQueryEnum.DESC_ID;
        List<SysDictItemResponseDto> resultList = sysDictItemService.getSysDictItemListByQuery(page,queryEnum);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList<>());
            return ;
        }

        List<SysDictItemShowResponseVo> listVo = IterableConvert.convertList(resultList,SysDictItemShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * Title: 新增数据字典明细表信息
     * Description:sysDictItemAddRequestVo @{Link SysDictItemAddRequestVo}
     * @param sysDictItemAddRequestVo 对象
     * @return long类型id
     * @author suven
     * date 2022-02-28 16:10:15
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增数据字典明细表信息",
            request = SysDictItemAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDictItem_add,method = RequestMethod.POST)
    //@RequiresPermissions("sys:dictitem:add")
    public  void  add(OutputSystem out, SysDictItemAddRequestVo sysDictItemAddRequestVo){

            SysDictItemRequestDto sysDictItemRequestDto =  SysDictItemRequestDto.build().clone(sysDictItemAddRequestVo);

            //sysDictItemRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            SysDictItemResponseDto sysDictItemresponseDto =  sysDictItemService.saveSysDictItem(sysDictItemRequestDto);
        if(sysDictItemresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( sysDictItemresponseDto.getId());
    }
    /**
     * Title: 修改数据字典明细表信息
     * Description:sysDictItemAddRequestVo @{Link SysDictItemAddRequestVo}
     * @param  sysDictItemAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven
     * date 2022-02-28 16:10:15
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改数据字典明细表信息",
            request = SysDictItemAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDictItem_modify , method = RequestMethod.POST)
    //@RequiresPermissions("sys:dictitem:modify")
    public  void  modify(OutputSystem out,SysDictItemAddRequestVo sysDictItemAddRequestVo){

            SysDictItemRequestDto sysDictItemRequestDto =  SysDictItemRequestDto.build().clone(sysDictItemAddRequestVo);

        if(sysDictItemRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  sysDictItemService.updateSysDictItem(sysDictItemRequestDto);
        out.write(result);
    }

    /**
     * Title: 查看数据字典明细表信息
     * Description:sysDictItemRequestVo @{Link SysDictItemRequestVo}
     * @param
     * @return  SysDictItemResponseVo  对象
     * @author suven
     * date 2022-02-28 16:10:15
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看数据字典明细表信息",
            request = HttpRequestByIdVo.class,
            response = SysDictItemShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDictItem_detail,method = RequestMethod.GET)
    //@RequiresPermissions("sys:dictitem:list")
    public void detail(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysDictItemResponseDto sysDictItemResponseDto = sysDictItemService.getSysDictItemById(idRequestVo.getId());
            SysDictItemShowResponseVo vo =  SysDictItemShowResponseVo.build().clone(sysDictItemResponseDto);
        out.write(vo);
    }



    /**
     * Title: 跳转数据字典明细表编辑界面
     * Description:id @{Link Long}
     * @param
     * @return SysDictItemShowResponseVo 对象
     * @author suven
     * date 2022-02-28 16:10:15
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看数据字典明细表信息",
            request = HttpRequestByIdVo.class,
            response = SysDictItemShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDictItem_edit , method = RequestMethod.GET)
    //@RequiresPermissions("sys:dictitem:modify")
    public void edit(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysDictItemResponseDto sysDictItemResponseDto = sysDictItemService.getSysDictItemById(idRequestVo.getId());
            SysDictItemShowResponseVo vo =  SysDictItemShowResponseVo.build().clone(sysDictItemResponseDto);
        out.write(vo);

    }




    /**
     * Title: 跳转数据字典明细表新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven
     * date 2022-02-28 16:10:15
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.sys_sysDictItem_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("sys:dictitem:add")
    public String newInfo(ModelMap modelMap){
        return "sys/sysDictItem_edit";
    }

    /**
     * Title: 删除数据字典明细表信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven
     * date 2022-02-28 16:10:15
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除数据字典明细表信息",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDictItem_del,method = RequestMethod.POST)
    //@RequiresPermissions("sys:dictitem:del")
    public  void  del(OutputSystem out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = sysDictItemService.delSysDictItemByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * Title: 导出数据字典明细表信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven
     * date 2022-02-28 16:10:15
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出数据字典明细表信息",
            request = SysDictItemQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDictItem_export,method = RequestMethod.GET)
    //@RequiresPermissions("sys:dictitem:export")
    public void export(HttpServletResponse response, SysDictItemQueryRequestVo sysDictItemQueryRequestVo){

            SysDictItemRequestDto sysDictItemRequestDto = SysDictItemRequestDto.build().clone(sysDictItemQueryRequestVo);

        Pager page =  Pager.of();
        page.toPageSize(sysDictItemQueryRequestVo.getPageSize()).toPageNo(sysDictItemQueryRequestVo.getPageNo());
        page.toParamObject(sysDictItemRequestDto );

        SysDictItemQueryEnum queryEnum =  SysDictItemQueryEnum.DESC_ID;
        PageResult<SysDictItemResponseDto> resultList = sysDictItemService.getSysDictItemByNextPage(page,queryEnum);
        List<SysDictItemResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysDictItemResponseVo.class,data,"导出数据字典明细表信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.sys_sysDictItem_import, method = RequestMethod.POST)
    //@RequiresPermissions("sys:dictitem:import")
    public void importExcel(OutputSystem out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = sysDictItemService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}