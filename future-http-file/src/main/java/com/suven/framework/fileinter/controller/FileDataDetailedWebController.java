package com.suven.framework.fileinter.controller;



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


import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.util.excel.ExcelUtils;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.ResponseResultPageVo;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.IterableConvert;
import com.suven.framework.core.ObjectTrue;


import com.suven.framework.fileinter.service.FileDataDetailedService;
import com.suven.framework.fileinter.vo.request.FileDataDetailedQueryRequestVo;
import com.suven.framework.fileinter.vo.request.FileDataDetailedAddRequestVo;
import com.suven.framework.fileinter.vo.response.FileDataDetailedShowResponseVo;
import com.suven.framework.fileinter.vo.response.FileDataDetailedResponseVo;

import com.suven.framework.fileinter.dto.request.FileDataDetailedRequestDto;
import com.suven.framework.fileinter.dto.response.FileDataDetailedResponseDto;
import com.suven.framework.fileinter.dto.enums.FileDataDetailedQueryEnum;


/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:20:28
 * <pre>
 *
 *  Description:  http业务接口交互数据请求参数实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * @RequestMapping("/fileinter/fileDataDetailed")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "模块"
)
public class FileDataDetailedWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static interface UrlCommand{
        public static final String fileinter_fileDataDetailed_index      =   "/fileinter/filedatadetailed/index";
        public static final String fileinter_fileDataDetailed_list       =   "/fileinter/filedatadetailed/list";
        public static final String fileinter_fileDataDetailed_queryList  =   "/fileinter/filedatadetailed/querylist";
        public static final String fileinter_fileDataDetailed_add        =   "/fileinter/filedatadetailed/add";
        public static final String fileinter_fileDataDetailed_modify     =   "/fileinter/filedatadetailed/modify";
        public static final String fileinter_fileDataDetailed_detail     =   "/fileinter/filedatadetailed/detail";
        public static final String fileinter_fileDataDetailed_edit       =   "/fileinter/filedatadetailed/edit";
        public static final String fileinter_fileDataDetailed_newInfo    =   "/fileinter/filedatadetailed/newInfo";
        public static final String fileinter_fileDataDetailed_del        =   "/fileinter/filedatadetailed/delete";
        public static final String fileinter_fileDataDetailed_sort       =   "/fileinter/filedatadetailed/sort";
        public static final String fileinter_fileDataDetailed_turnOn     =   "/fileinter/filedatadetailed/turnOn";
        public static final String fileinter_fileDataDetailed_turnOff    =   "/fileinter/filedatadetailed/turnOff";
        public static final String fileinter_fileDataDetailed_export     =   "/fileinter/filedatadetailed/export";
        public static final String fileinter_fileDataDetailed_import     =   "/fileinter/filedatadetailed/import";
    }




    @Autowired
    private FileDataDetailedService  fileDataDetailedService;

    /**
     * @Title: 跳转到主界面
     * @return 字符串url
     * @author suven  作者
     * date 2024-04-19 00:20:28 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.fileinter_fileDataDetailed_index,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:filedatadetailed:index")
    public String index(){
        return "fileinter/fileDataDetailed_index";
    }


    /**
     * @Title: 获取分页信息
     * Description:fileDataDetailedQueryRequestVo @{Link FileDataDetailedQueryRequestVo}
     * @param
     * @return  ResponseResultPageVo 对象 List<FileDataDetailedShowResponseVo>
     * @throw
     * @author suven  作者
     * date 2024-04-19 00:20:28 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取分页信息",
            request = FileDataDetailedQueryRequestVo.class,
            response = FileDataDetailedShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileDataDetailed_list,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:filedatadetailed:list")
    public   void   list( OutputResponse out, FileDataDetailedQueryRequestVo fileDataDetailedQueryRequestVo){
            FileDataDetailedRequestDto fileDataDetailedRequestDto = FileDataDetailedRequestDto.build( ).clone(fileDataDetailedQueryRequestVo);

        Pager pager =  Pager.build().toPageSize(fileDataDetailedQueryRequestVo.getPageSize()).toPageNo(fileDataDetailedQueryRequestVo.getPageNo());
        pager.toParamObject(fileDataDetailedRequestDto );
         FileDataDetailedQueryEnum queryEnum =  FileDataDetailedQueryEnum.DESC_ID;
        ResponseResultPageVo<FileDataDetailedResponseDto> resultList = fileDataDetailedService.getFileDataDetailedByNextPage(queryEnum,pager);
        if(ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())){
            out.write( new ResponseResultPageVo());
            return ;
        }

        ResponseResultPageVo<FileDataDetailedShowResponseVo> result = resultList.convertBuild(FileDataDetailedShowResponseVo.class);
        out.write( result);
    }

/**
     * @Title: 根据条件查谒分页信息
     * Description:fileDataDetailedQueryRequestVo @{Link FileDataDetailedQueryRequestVo}
     * @param
     * @return   ResponseResultPageVo 对象 List<FileDataDetailedShowResponseVo>
     * @author suven  作者
     * date 2024-04-19 00:20:28 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取分页信息",
            request = FileDataDetailedQueryRequestVo.class,
            response = FileDataDetailedShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileDataDetailed_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:filedatadetailed:query")
    public   void   queryList( OutputResponse out, FileDataDetailedQueryRequestVo fileDataDetailedQueryRequestVo){
            FileDataDetailedRequestDto fileDataDetailedRequestDto = FileDataDetailedRequestDto.build( ).clone(fileDataDetailedQueryRequestVo);

        FileDataDetailedQueryEnum queryEnum =  FileDataDetailedQueryEnum.DESC_ID;
        List<FileDataDetailedResponseDto> resultList = fileDataDetailedService.getFileDataDetailedListByQuery(queryEnum,fileDataDetailedRequestDto);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList());
            return ;
        }

        List<FileDataDetailedShowResponseVo> listVo = IterableConvert.convertList(resultList,FileDataDetailedShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * @Title: 新增信息
     * Description:fileDataDetailedAddRequestVo @{Link FileDataDetailedAddRequestVo}
     * @param fileDataDetailedAddRequestVo 对象
     * @return long类型id
     * @author suven  作者
     * date 2024-04-19 00:20:28 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增信息",
            request = FileDataDetailedAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileDataDetailed_add,method = RequestMethod.POST)
    //@RequiresPermissions("fileinter:filedatadetailed:add")
    public  void  add(OutputResponse out, FileDataDetailedAddRequestVo fileDataDetailedAddRequestVo){

            FileDataDetailedRequestDto fileDataDetailedRequestDto =  FileDataDetailedRequestDto.build().clone(fileDataDetailedAddRequestVo);

            //fileDataDetailedRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            FileDataDetailedResponseDto fileDataDetailedresponseDto =  fileDataDetailedService.saveFileDataDetailed(fileDataDetailedRequestDto);
        if(fileDataDetailedresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( fileDataDetailedresponseDto.getId());
    }
    /**
     * @Title: 修改信息
     * Description:fileDataDetailedAddRequestVo @{Link FileDataDetailedAddRequestVo}
     * @param  fileDataDetailedAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven  作者
     * date 2024-04-19 00:20:28 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改信息",
            request = FileDataDetailedAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileDataDetailed_modify , method = RequestMethod.POST)
    //@RequiresPermissions("fileinter:filedatadetailed:modify")
    public  void  modify(OutputResponse out,FileDataDetailedAddRequestVo fileDataDetailedAddRequestVo){

            FileDataDetailedRequestDto fileDataDetailedRequestDto =  FileDataDetailedRequestDto.build().clone(fileDataDetailedAddRequestVo);

        if(fileDataDetailedRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  fileDataDetailedService.updateFileDataDetailed(fileDataDetailedRequestDto);
        out.write(result);
    }

    /**
     * @Title: 查看信息
     * Description:fileDataDetailedRequestVo @{Link FileDataDetailedRequestVo}
     * @param
     * @return  FileDataDetailedResponseVo  对象
     * @author suven  作者
     * date 2024-04-19 00:20:28 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = FileDataDetailedShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileDataDetailed_detail,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:filedatadetailed:list")
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileDataDetailedResponseDto fileDataDetailedResponseDto = fileDataDetailedService.getFileDataDetailedById(idRequestVo.getId());
            FileDataDetailedShowResponseVo vo =  FileDataDetailedShowResponseVo.build().clone(fileDataDetailedResponseDto);
        out.write(vo);
    }



    /**
     * @Title: 跳转编辑界面
     * Description:id @{Link Long}
     * @param
     * @return FileDataDetailedShowResponseVo 对象
     * @author suven  作者
     * date 2024-04-19 00:20:28 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = FileDataDetailedShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileDataDetailed_edit , method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:filedatadetailed:modify")
    public void edit(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileDataDetailedResponseDto fileDataDetailedResponseDto = fileDataDetailedService.getFileDataDetailedById(idRequestVo.getId());
            FileDataDetailedShowResponseVo vo =  FileDataDetailedShowResponseVo.build().clone(fileDataDetailedResponseDto);
        out.write(vo);

    }




    /**
     * @Title: 跳转新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven  作者
     * date 2024-04-19 00:20:28 创建时间
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.fileinter_fileDataDetailed_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:filedatadetailed:add")
    public String newInfo(ModelMap modelMap){
        return "fileinter/fileDataDetailed_edit";
    }

    /**
     * @Title: 删除信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven  作者
     * date 2024-04-19 00:20:28 创建时间
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
    @RequestMapping(value = UrlCommand.fileinter_fileDataDetailed_del,method = RequestMethod.POST)
    //@RequiresPermissions("fileinter:filedatadetailed:del")
    public  void  del(OutputResponse out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = fileDataDetailedService.delFileDataDetailedByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * @Title: 导出信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven  作者
     * date 2024-04-19 00:20:28 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出信息",
            request = FileDataDetailedQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileDataDetailed_export,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:filedatadetailed:export")
    public void export(HttpServletResponse response, FileDataDetailedQueryRequestVo fileDataDetailedQueryRequestVo){

            FileDataDetailedRequestDto fileDataDetailedRequestDto = FileDataDetailedRequestDto.build().clone(fileDataDetailedQueryRequestVo);

        Pager pager = Pager.build().toPageSize(fileDataDetailedQueryRequestVo.getPageSize()).toPageNo(fileDataDetailedQueryRequestVo.getPageNo());
        pager.toParamObject(fileDataDetailedRequestDto );

        FileDataDetailedQueryEnum queryEnum =  FileDataDetailedQueryEnum.DESC_ID;
        ResponseResultPageVo<FileDataDetailedResponseDto> resultList = fileDataDetailedService.getFileDataDetailedByNextPage(queryEnum,pager);
        List<FileDataDetailedResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, FileDataDetailedResponseVo.class,data,"导出信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.fileinter_fileDataDetailed_import, method = RequestMethod.POST)
    //@RequiresPermissions("fileinter:filedatadetailed:import")
    public void importExcel(OutputResponse out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = fileDataDetailedService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}