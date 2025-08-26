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


import com.suven.framework.sys.service.SysThirdAccountService;
import com.suven.framework.sys.vo.request.SysThirdAccountQueryRequestVo;
import com.suven.framework.sys.vo.request.SysThirdAccountAddRequestVo;
import com.suven.framework.sys.vo.response.SysThirdAccountShowResponseVo;
import com.suven.framework.sys.vo.response.SysThirdAccountResponseVo;

import com.suven.framework.sys.dto.request.SysThirdAccountRequestDto;
import com.suven.framework.sys.dto.response.SysThirdAccountResponseDto;
import com.suven.framework.sys.dto.enums.SysThirdAccountQueryEnum;


/**
 * ClassName: SysThirdAccountWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:09:47
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 第三方登陆表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysThirdAccount")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "第三方登陆表模块"
)
public class SysThirdAccountWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());





    @Autowired
    private SysThirdAccountService  sysThirdAccountService;

    /**
     * Title: 跳转到第三方登陆表主界面
     * @return 字符串url
     * @author suven
     * date 2022-02-28 16:09:47
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.sys_sysThirdAccount_index,method = RequestMethod.GET)
    //@RequiresPermissions("sys:thirdaccount:list")
    public String index(){
        return "sys/sysThirdAccount_index";
    }


    /**
     * Title: 获取第三方登陆表分页信息
     * Description:sysThirdAccountQueryRequestVo @{Link SysThirdAccountQueryRequestVo}
     * @param
     * @return  PageResult 对象 List<SysThirdAccountShowResponseVo>
     * @throw
     * @author suven
     * date 2022-02-28 16:09:47
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取第三方登陆表分页信息",
            request = SysThirdAccountQueryRequestVo.class,
            response = SysThirdAccountShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysThirdAccount_list,method = RequestMethod.GET)
    //@RequiresPermissions("sys:thirdaccount:list")
    public   void   list( OutputSystem out, SysThirdAccountQueryRequestVo sysThirdAccountQueryRequestVo){
            SysThirdAccountRequestDto sysThirdAccountRequestDto = SysThirdAccountRequestDto.build( ).clone(sysThirdAccountQueryRequestVo);

        Pager page =  Pager.of();
        page.toPageSize(sysThirdAccountQueryRequestVo.getPageSize()).toPageNo(sysThirdAccountQueryRequestVo.getPageNo());
        page.toParamObject(sysThirdAccountRequestDto );
         SysThirdAccountQueryEnum queryEnum =  SysThirdAccountQueryEnum.DESC_ID;
        PageResult<SysThirdAccountResponseDto> resultPage = sysThirdAccountService.getSysThirdAccountByNextPage(page,queryEnum);
        if(null == resultPage || resultPage.getList().isEmpty() ){
            out.write( new PageResult<>());
            return ;
        }
        PageResult<SysThirdAccountShowResponseVo> resultPageVo = resultPage.convertBuild(SysThirdAccountShowResponseVo.class);
        out.write( resultPageVo);
    }

/**
     * Title: 根据条件查谒第三方登陆表分页信息
     * Description:sysThirdAccountQueryRequestVo @{Link SysThirdAccountQueryRequestVo}
     * @param
     * @return   PageResult 对象 List<SysThirdAccountShowResponseVo>
     * @author suven
     * date 2022-02-28 16:09:47
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取第三方登陆表分页信息",
            request = SysThirdAccountQueryRequestVo.class,
            response = SysThirdAccountShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysThirdAccount_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("sys:thirdaccount:query")
    public   void   queryList( OutputSystem out, SysThirdAccountQueryRequestVo sysThirdAccountQueryRequestVo){
            SysThirdAccountRequestDto sysThirdAccountRequestDto = SysThirdAccountRequestDto.build( ).clone(sysThirdAccountQueryRequestVo);

        Pager page =  Pager.of();
        page.toPageSize(sysThirdAccountQueryRequestVo.getPageSize()).toPageNo(sysThirdAccountQueryRequestVo.getPageNo());
        page.toParamObject(sysThirdAccountRequestDto );
        SysThirdAccountQueryEnum queryEnum =  SysThirdAccountQueryEnum.DESC_ID;
        List<SysThirdAccountResponseDto> resultList = sysThirdAccountService.getSysThirdAccountListByQuery(page,queryEnum);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList<>());
            return ;
        }

        List<SysThirdAccountShowResponseVo> listVo = IterableConvert.convertList(resultList,SysThirdAccountShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * Title: 新增第三方登陆表信息
     * Description:sysThirdAccountAddRequestVo @{Link SysThirdAccountAddRequestVo}
     * @param sysThirdAccountAddRequestVo 对象
     * @return long类型id
     * @author suven
     * date 2022-02-28 16:09:47
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增第三方登陆表信息",
            request = SysThirdAccountAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.sys_sysThirdAccount_add,method = RequestMethod.POST)
    //@RequiresPermissions("sys:thirdaccount:add")
    public  void  add(OutputSystem out, SysThirdAccountAddRequestVo sysThirdAccountAddRequestVo){

            SysThirdAccountRequestDto sysThirdAccountRequestDto =  SysThirdAccountRequestDto.build().clone(sysThirdAccountAddRequestVo);

            //sysThirdAccountRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            SysThirdAccountResponseDto sysThirdAccountresponseDto =  sysThirdAccountService.saveSysThirdAccount(sysThirdAccountRequestDto);
        if(sysThirdAccountresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( sysThirdAccountresponseDto.getId());
    }
    /**
     * Title: 修改第三方登陆表信息
     * Description:sysThirdAccountAddRequestVo @{Link SysThirdAccountAddRequestVo}
     * @param  sysThirdAccountAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven
     * date 2022-02-28 16:09:47
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改第三方登陆表信息",
            request = SysThirdAccountAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysThirdAccount_modify , method = RequestMethod.POST)
    //@RequiresPermissions("sys:thirdaccount:modify")
    public  void  modify(OutputSystem out,SysThirdAccountAddRequestVo sysThirdAccountAddRequestVo){

            SysThirdAccountRequestDto sysThirdAccountRequestDto =  SysThirdAccountRequestDto.build().clone(sysThirdAccountAddRequestVo);

        if(sysThirdAccountRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  sysThirdAccountService.updateSysThirdAccount(sysThirdAccountRequestDto);
        out.write(result);
    }

    /**
     * Title: 查看第三方登陆表信息
     * Description:sysThirdAccountRequestVo @{Link SysThirdAccountRequestVo}
     * @param
     * @return  SysThirdAccountResponseVo  对象
     * @author suven
     * date 2022-02-28 16:09:47
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看第三方登陆表信息",
            request = HttpRequestByIdVo.class,
            response = SysThirdAccountShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysThirdAccount_detail,method = RequestMethod.GET)
    //@RequiresPermissions("sys:thirdaccount:list")
    public void detail(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysThirdAccountResponseDto sysThirdAccountResponseDto = sysThirdAccountService.getSysThirdAccountById(idRequestVo.getId());
            SysThirdAccountShowResponseVo vo =  SysThirdAccountShowResponseVo.build().clone(sysThirdAccountResponseDto);
        out.write(vo);
    }



    /**
     * Title: 跳转第三方登陆表编辑界面
     * Description:id @{Link Long}
     * @param
     * @return SysThirdAccountShowResponseVo 对象
     * @author suven
     * date 2022-02-28 16:09:47
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看第三方登陆表信息",
            request = HttpRequestByIdVo.class,
            response = SysThirdAccountShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysThirdAccount_edit , method = RequestMethod.GET)
    //@RequiresPermissions("sys:thirdaccount:modify")
    public void edit(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysThirdAccountResponseDto sysThirdAccountResponseDto = sysThirdAccountService.getSysThirdAccountById(idRequestVo.getId());
            SysThirdAccountShowResponseVo vo =  SysThirdAccountShowResponseVo.build().clone(sysThirdAccountResponseDto);
        out.write(vo);

    }




    /**
     * Title: 跳转第三方登陆表新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven
     * date 2022-02-28 16:09:47
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.sys_sysThirdAccount_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("sys:thirdaccount:add")
    public String newInfo(ModelMap modelMap){
        return "sys/sysThirdAccount_edit";
    }

    /**
     * Title: 删除第三方登陆表信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven
     * date 2022-02-28 16:09:47
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除第三方登陆表信息",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @RequestMapping(value = UrlCommand.sys_sysThirdAccount_del,method = RequestMethod.POST)
    //@RequiresPermissions("sys:thirdaccount:del")
    public  void  del(OutputSystem out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = sysThirdAccountService.delSysThirdAccountByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * Title: 导出第三方登陆表信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven
     * date 2022-02-28 16:09:47
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出第三方登陆表信息",
            request = SysThirdAccountQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysThirdAccount_export,method = RequestMethod.GET)
    //@RequiresPermissions("sys:thirdaccount:export")
    public void export(HttpServletResponse response, SysThirdAccountQueryRequestVo sysThirdAccountQueryRequestVo){

            SysThirdAccountRequestDto sysThirdAccountRequestDto = SysThirdAccountRequestDto.build().clone(sysThirdAccountQueryRequestVo);

        Pager page =  Pager.of();
        page.toPageSize(sysThirdAccountQueryRequestVo.getPageSize()).toPageNo(sysThirdAccountQueryRequestVo.getPageNo());
        page.toParamObject(sysThirdAccountRequestDto );

        SysThirdAccountQueryEnum queryEnum =  SysThirdAccountQueryEnum.DESC_ID;
        PageResult<SysThirdAccountResponseDto> resultList = sysThirdAccountService.getSysThirdAccountByNextPage(page,queryEnum);
        List<SysThirdAccountResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysThirdAccountResponseVo.class,data,"导出第三方登陆表信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.sys_sysThirdAccount_import, method = RequestMethod.POST)
    //@RequiresPermissions("sys:thirdaccount:import")
    public void importExcel(OutputSystem out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = sysThirdAccountService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}