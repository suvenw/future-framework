package com.suven.framework.sys.controller;


import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.ResponseResultPageVo;
import com.suven.framework.http.handler.OutputSystem;
import com.suven.framework.sys.dto.enums.SysDataLogQueryEnum;
import com.suven.framework.sys.dto.request.SysDataLogRequestDto;
import com.suven.framework.sys.dto.response.SysDataLogResponseDto;
import com.suven.framework.sys.service.SysDataLogService;
import com.suven.framework.sys.vo.request.SysDataLogAddRequestVo;
import com.suven.framework.sys.vo.request.SysDataLogQueryRequestVo;
import com.suven.framework.sys.vo.response.SysDataLogResponseVo;
import com.suven.framework.sys.vo.response.SysDataLogShowResponseVo;
import com.suven.framework.util.excel.ExcelUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * ClassName: SysDataLogWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:02
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
 * RequestMapping("/sys/sysDataLog")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@RestController
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "模块"
)
public class SysDataLogWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());






    @Autowired
    private SysDataLogService  sysDataLogService;

    /**
     * Title: 跳转到主界面
     * @return 字符串url
     * @author suven
     * date 2022-02-28 16:10:02
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.sys_sysDataLog_index,method = RequestMethod.GET)
    //@RequiresPermissions("sys:datalog:list")
    public String index(){
        return "sys/sysDataLog_index";
    }


    /**
     * Title: 获取分页信息
     * Description:sysDataLogQueryRequestVo @{Link SysDataLogQueryRequestVo}
     * @param
     * @return  ResponseResultPageVo 对象 List<SysDataLogShowResponseVo>
     * @throw
     * @author suven
     * date 2022-02-28 16:10:02
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取分页信息",
            request = SysDataLogQueryRequestVo.class,
            response = SysDataLogShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDataLog_list,method = RequestMethod.GET)
    //@RequiresPermissions("sys:datalog:list")
    public   void   list( OutputSystem out, SysDataLogQueryRequestVo sysDataLogQueryRequestVo){
            SysDataLogRequestDto sysDataLogRequestDto = SysDataLogRequestDto.build( ).clone(sysDataLogQueryRequestVo);

        Pager<SysDataLogRequestDto> page =  Pager.build();
        pager.toPageSize(sysDataLogQueryRequestVo.getPageSize()).toPageNo(sysDataLogQueryRequestVo.getPageNo());
        page.toParamObject(sysDataLogRequestDto );
         SysDataLogQueryEnum queryEnum =  SysDataLogQueryEnum.DESC_ID;
        ResponseResultPageVo<SysDataLogResponseDto> resultList = sysDataLogService.getSysDataLogByNextPage(page,queryEnum);
        if(null == resultList || resultList.getList().isEmpty() ){
            out.write( new ResponseResultPageVo());
            return ;
        }

        List<SysDataLogShowResponseVo> listVo = IterableConvert.convertList(resultList.getList(),SysDataLogShowResponseVo.class);
        ResponseResultPageVo result = new ResponseResultPageVo()
                .setResult(listVo,page.getSize(),resultList.getTotal())
                .toPageIndex(resultList.getPageIndex());
        out.write( result);
    }

/**
     * Title: 根据条件查谒分页信息
     * Description:sysDataLogQueryRequestVo @{Link SysDataLogQueryRequestVo}
     * @param
     * @return   ResponseResultPageVo 对象 List<SysDataLogShowResponseVo>
     * @author suven
     * date 2022-02-28 16:10:02
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取分页信息",
            request = SysDataLogQueryRequestVo.class,
            response = SysDataLogShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDataLog_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("sys:datalog:query")
    public   void   queryList( OutputSystem out, SysDataLogQueryRequestVo sysDataLogQueryRequestVo){
            SysDataLogRequestDto sysDataLogRequestDto = SysDataLogRequestDto.build( ).clone(sysDataLogQueryRequestVo);

        Pager page =  Pager.build();
        pager.toPageSize(sysDataLogQueryRequestVo.getPageSize()).toPageNo(sysDataLogQueryRequestVo.getPageNo());
        page.toParamObject(sysDataLogRequestDto );
        SysDataLogQueryEnum queryEnum =  SysDataLogQueryEnum.DESC_ID;
        List<SysDataLogResponseDto> resultList = sysDataLogService.getSysDataLogListByQuery(page,queryEnum);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList<>());
            return ;
        }

        List<SysDataLogShowResponseVo> listVo = IterableConvert.convertList(resultList,SysDataLogShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * Title: 新增信息
     * Description:sysDataLogAddRequestVo @{Link SysDataLogAddRequestVo}
     * @param sysDataLogAddRequestVo 对象
     * @return long类型id
     * @author suven
     * date 2022-02-28 16:10:02
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增信息",
            request = SysDataLogAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDataLog_add,method = RequestMethod.POST)
    //@RequiresPermissions("sys:datalog:add")
    public  void  add(OutputSystem out, SysDataLogAddRequestVo sysDataLogAddRequestVo){

            SysDataLogRequestDto sysDataLogRequestDto =  SysDataLogRequestDto.build().clone(sysDataLogAddRequestVo);

            //sysDataLogRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            SysDataLogResponseDto sysDataLogresponseDto =  sysDataLogService.saveSysDataLog(sysDataLogRequestDto);
        if(sysDataLogresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( sysDataLogresponseDto.getId());
    }
    /**
     * Title: 修改信息
     * Description:sysDataLogAddRequestVo @{Link SysDataLogAddRequestVo}
     * @param  sysDataLogAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven
     * date 2022-02-28 16:10:02
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改信息",
            request = SysDataLogAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDataLog_modify , method = RequestMethod.POST)
    //@RequiresPermissions("sys:datalog:modify")
    public  void  modify(OutputSystem out,SysDataLogAddRequestVo sysDataLogAddRequestVo){

            SysDataLogRequestDto sysDataLogRequestDto =  SysDataLogRequestDto.build().clone(sysDataLogAddRequestVo);

        if(sysDataLogRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  sysDataLogService.updateSysDataLog(sysDataLogRequestDto);
        out.write(result);
    }

    /**
     * Title: 查看信息
     * Description:sysDataLogRequestVo @{Link SysDataLogRequestVo}
     * @param
     * @return  SysDataLogResponseVo  对象
     * @author suven
     * date 2022-02-28 16:10:02
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = SysDataLogShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDataLog_detail,method = RequestMethod.GET)
    //@RequiresPermissions("sys:datalog:list")
    public void detail(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysDataLogResponseDto sysDataLogResponseDto = sysDataLogService.getSysDataLogById(idRequestVo.getId());
            SysDataLogShowResponseVo vo =  SysDataLogShowResponseVo.build().clone(sysDataLogResponseDto);
        out.write(vo);
    }



    /**
     * Title: 跳转编辑界面
     * Description:id @{Link Long}
     * @param
     * @return SysDataLogShowResponseVo 对象
     * @author suven
     * date 2022-02-28 16:10:02
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = SysDataLogShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDataLog_edit , method = RequestMethod.GET)
    //@RequiresPermissions("sys:datalog:modify")
    public void edit(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysDataLogResponseDto sysDataLogResponseDto = sysDataLogService.getSysDataLogById(idRequestVo.getId());
            SysDataLogShowResponseVo vo =  SysDataLogShowResponseVo.build().clone(sysDataLogResponseDto);
        out.write(vo);

    }




    /**
     * Title: 跳转新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven
     * date 2022-02-28 16:10:02
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.sys_sysDataLog_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("sys:datalog:add")
    public String newInfo(ModelMap modelMap){
        return "sys/sysDataLog_edit";
    }

    /**
     * Title: 删除信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven
     * date 2022-02-28 16:10:02
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
    @RequestMapping(value = UrlCommand.sys_sysDataLog_del,method = RequestMethod.POST)
    //@RequiresPermissions("sys:datalog:del")
    public  void  del(OutputSystem out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }

        int result = sysDataLogService.delSysDataLogByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * Title: 导出信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven
     * date 2022-02-28 16:10:02
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出信息",
            request = SysDataLogQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysDataLog_export,method = RequestMethod.GET)
    //@RequiresPermissions("sys:datalog:export")
    public void export(HttpServletResponse response, SysDataLogQueryRequestVo sysDataLogQueryRequestVo){

            SysDataLogRequestDto sysDataLogRequestDto = SysDataLogRequestDto.build().clone(sysDataLogQueryRequestVo);

        Pager page =  Pager.build();
        pager.toPageSize(sysDataLogQueryRequestVo.getPageSize()).toPageNo(sysDataLogQueryRequestVo.getPageNo());
        page.toParamObject(sysDataLogRequestDto );

        SysDataLogQueryEnum queryEnum =  SysDataLogQueryEnum.DESC_ID;
        ResponseResultPageVo<SysDataLogResponseDto> resultList = sysDataLogService.getSysDataLogByNextPage(page,queryEnum);
        List<SysDataLogResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysDataLogResponseVo.class,data,"导出信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.sys_sysDataLog_import, method = RequestMethod.POST)
    //@RequiresPermissions("sys:datalog:import")
    public void importExcel(OutputSystem out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = sysDataLogService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}