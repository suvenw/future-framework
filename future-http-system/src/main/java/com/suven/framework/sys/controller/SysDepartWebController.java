package com.suven.framework.sys.controller;



import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.sys.facade.SysDepartFacade;
import com.suven.framework.sys.vo.response.SysDepartTreeModelResponseVo;
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
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.http.handler.OutputSystem;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.util.excel.ExcelUtils;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.common.enums.SysResultCodeEnum;


import com.suven.framework.sys.service.SysDepartService;
import com.suven.framework.sys.vo.request.SysDepartQueryRequestVo;
import com.suven.framework.sys.vo.request.SysDepartAddRequestVo;
import com.suven.framework.sys.vo.response.SysDepartShowResponseVo;
import com.suven.framework.sys.vo.response.SysDepartResponseVo;

import com.suven.framework.sys.dto.request.SysDepartRequestDto;
import com.suven.framework.sys.dto.response.SysDepartResponseDto;
import com.suven.framework.sys.dto.enums.SysDepartQueryEnum;


/**
 * @ClassName: SysDepartWebController.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:13:31
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 组织机构表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * @RequestMapping("/sys/sysDepart")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "组织机构表模块"
)
public class SysDepartWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());



    @Autowired
    private SysDepartFacade sysDepartFacade;


    @Autowired
    private SysDepartService  sysDepartService;

    /**
     * @Title: 跳转到组织机构表主界面
     * @return 字符串url
     * @author suven
     * @date 2022-02-28 16:13:31
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.sys_sysDepart_index,method = RequestMethod.GET)
    //@RequiresPermissions("sys:depart:list")
    public String index(){
        return "sys/sysDepart_index";
    }


    /**
     * @Title: 获取组织机构表分页信息
     * Description:sysDepartQueryRequestVo @{Link SysDepartQueryRequestVo}
     * @param
     * @return  ResponseResultList 对象 List<SysDepartShowResponseVo>
     * @throw
     * @author suven
     * @date 2022-02-28 16:13:31
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取组织机构表分页信息",
            request = SysDepartQueryRequestVo.class,
            response = SysDepartShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepart_list,method = RequestMethod.GET)
    //@RequiresPermissions("sys:depart:list")
    public   void   list( OutputSystem out, SysDepartQueryRequestVo sysDepartQueryRequestVo){
            SysDepartRequestDto sysDepartRequestDto = SysDepartRequestDto.build( ).clone(sysDepartQueryRequestVo);

        Pager page =  Pager.build().toPageSize(sysDepartQueryRequestVo.getPageSize()).toPageNo(sysDepartQueryRequestVo.getPageNo());
        page.toParamObject(sysDepartRequestDto );
         SysDepartQueryEnum queryEnum =  SysDepartQueryEnum.DESC_ID;
        ResponseResultList<SysDepartResponseDto> resultList = sysDepartService.getSysDepartByNextPage(page,queryEnum);
        if(null == resultList || resultList.getList().isEmpty() ){
            out.write( ResponseResultList.build());
            return ;
        }

        List<SysDepartShowResponseVo> listVo = IterableConvert.convertList(resultList.getList(),SysDepartShowResponseVo.class);
        ResponseResultList result = ResponseResultList.build()
                .setResult(listVo,page.getSize(),resultList.getTotal())
                .toPageIndex(resultList.getPageIndex());
        out.write( result);
    }

/**
     * @Title: 根据条件查谒组织机构表分页信息
     * Description:sysDepartQueryRequestVo @{Link SysDepartQueryRequestVo}
     * @param
     * @return   ResponseResultList 对象 List<SysDepartShowResponseVo>
     * @author suven
     * @date 2022-02-28 16:13:31
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取组织机构表分页信息",
            request = SysDepartQueryRequestVo.class,
            response = SysDepartShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepart_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("sys:depart:query")
    public   void   queryList( OutputSystem out, SysDepartQueryRequestVo sysDepartQueryRequestVo){
            SysDepartRequestDto sysDepartRequestDto = SysDepartRequestDto.build( ).clone(sysDepartQueryRequestVo);

        Pager page =  Pager.build().toPageSize(sysDepartQueryRequestVo.getPageSize()).toPageNo(sysDepartQueryRequestVo.getPageNo());
        page.toParamObject(sysDepartRequestDto );
        SysDepartQueryEnum queryEnum =  SysDepartQueryEnum.DEPART_NAME;
        List<SysDepartResponseDto> resultList = sysDepartService.getSysDepartListByQuery(page,queryEnum);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList());
            return ;
        }

        List<SysDepartShowResponseVo> listVo = IterableConvert.convertList(resultList,SysDepartShowResponseVo.class);

        out.write( listVo);
    }


    @ApiDoc(
            value = "获取组织机构表信息",
            request = String.class,
            response = SysDepartShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepart_queryTreeList,method = RequestMethod.GET)
//    //@RequiresPermissions({"sys:sysDepart:queryTreeList"})
    public void queryTreeList(OutputResponse out) {
        List<SysDepartTreeModelResponseVo> list = sysDepartFacade.queryTreeList();
        out.write(list, new String[0]);
    }

//    @ApiDoc(
//            value = "获取我的组织机构信息",
//            request = String.class,
//            response = SysDepartShowResponseVo.class
//    )
//    @RequestMapping(value = UrlCommand.sys_sysDepart_myDeptTreeList,method = RequestMethod.GET)
//    //@RequiresPermissions({"sys:sysDepart:queryTreeList"})
//    public void myDeptTreeList(OutputResponse out) {
//        List<SysDepartTreeModelResponseVo> list = sysDepartFacade.myDeptTreeList();
//        out.write(list, new String[0]);
//    }



    /**
     * @Title: 新增组织机构表信息
     * Description:sysDepartAddRequestVo @{Link SysDepartAddRequestVo}
     * @param sysDepartAddRequestVo 对象
     * @return long类型id
     * @author suven
     * @date 2022-02-28 16:13:31
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增组织机构表信息",
            request = SysDepartAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepart_add,method = RequestMethod.POST)
    //@RequiresPermissions("sys:depart:add")
    public  void  add(OutputSystem out, SysDepartAddRequestVo sysDepartAddRequestVo){

            SysDepartRequestDto sysDepartRequestDto =  SysDepartRequestDto.build().clone(sysDepartAddRequestVo);

            //sysDepartRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            SysDepartResponseDto sysDepartresponseDto =  sysDepartService.saveSysDepart(sysDepartRequestDto);
        if(sysDepartresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( sysDepartresponseDto.getId());
    }
    /**
     * @Title: 修改组织机构表信息
     * Description:sysDepartAddRequestVo @{Link SysDepartAddRequestVo}
     * @param  sysDepartAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven
     * @date 2022-02-28 16:13:31
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改组织机构表信息",
            request = SysDepartAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepart_modify , method = RequestMethod.POST)
    //@RequiresPermissions("sys:depart:modify")
    public  void  modify(OutputSystem out,SysDepartAddRequestVo sysDepartAddRequestVo){

            SysDepartRequestDto sysDepartRequestDto =  SysDepartRequestDto.build().clone(sysDepartAddRequestVo);

        if(sysDepartRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  sysDepartService.updateSysDepart(sysDepartRequestDto);
        out.write(result);
    }

    /**
     * @Title: 查看组织机构表信息
     * Description:sysDepartRequestVo @{Link SysDepartRequestVo}
     * @param
     * @return  SysDepartResponseVo  对象
     * @author suven
     * @date 2022-02-28 16:13:31
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看组织机构表信息",
            request = HttpRequestByIdVo.class,
            response = SysDepartShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepart_detail,method = RequestMethod.GET)
    //@RequiresPermissions("sys:depart:list")
    public void detail(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysDepartResponseDto sysDepartResponseDto = sysDepartService.getSysDepartById(idRequestVo.getId());
            SysDepartShowResponseVo vo =  SysDepartShowResponseVo.build().clone(sysDepartResponseDto);
        out.write(vo);
    }



    /**
     * @Title: 跳转组织机构表编辑界面
     * Description:id @{Link Long}
     * @param
     * @return SysDepartShowResponseVo 对象
     * @author suven
     * @date 2022-02-28 16:13:31
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看组织机构表信息",
            request = HttpRequestByIdVo.class,
            response = SysDepartShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepart_edit , method = RequestMethod.GET)
    //@RequiresPermissions("sys:depart:modify")
    public void edit(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysDepartResponseDto sysDepartResponseDto = sysDepartService.getSysDepartById(idRequestVo.getId());
            SysDepartShowResponseVo vo =  SysDepartShowResponseVo.build().clone(sysDepartResponseDto);
        out.write(vo);

    }




    /**
     * @Title: 跳转组织机构表新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven
     * @date 2022-02-28 16:13:31
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.sys_sysDepart_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("sys:depart:add")
    public String newInfo(ModelMap modelMap){
        return "sys/sysDepart_edit";
    }

    /**
     * @Title: 删除组织机构表信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven
     * @date 2022-02-28 16:13:31
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除组织机构表信息",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepart_del,method = RequestMethod.POST)
    //@RequiresPermissions("sys:depart:del")
    public  void  del(OutputSystem out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = sysDepartService.delSysDepartByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * @Title: 导出组织机构表信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven
     * @date 2022-02-28 16:13:31
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出组织机构表信息",
            request = SysDepartQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDepart_export,method = RequestMethod.GET)
    //@RequiresPermissions("sys:depart:export")
    public void export(HttpServletResponse response, SysDepartQueryRequestVo sysDepartQueryRequestVo){

            SysDepartRequestDto sysDepartRequestDto = SysDepartRequestDto.build().clone(sysDepartQueryRequestVo);

        Pager page =  Pager.build().toPageSize(sysDepartQueryRequestVo.getPageSize()).toPageNo(sysDepartQueryRequestVo.getPageNo());
        page.toParamObject(sysDepartRequestDto );

        SysDepartQueryEnum queryEnum =  SysDepartQueryEnum.DESC_ID;
        ResponseResultList<SysDepartResponseDto> resultList = sysDepartService.getSysDepartByNextPage(page,queryEnum);
        List<SysDepartResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysDepartResponseVo.class,data,"导出组织机构表信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.sys_sysDepart_import, method = RequestMethod.POST)
    //@RequiresPermissions("sys:depart:import")
    public void importExcel(OutputSystem out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = sysDepartService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}