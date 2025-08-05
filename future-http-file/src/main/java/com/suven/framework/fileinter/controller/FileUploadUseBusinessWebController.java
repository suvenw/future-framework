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


import com.suven.framework.fileinter.service.FileUploadUseBusinessService;
import com.suven.framework.fileinter.vo.request.FileUploadUseBusinessQueryRequestVo;
import com.suven.framework.fileinter.vo.request.FileUploadUseBusinessAddRequestVo;
import com.suven.framework.fileinter.vo.response.FileUploadUseBusinessShowResponseVo;
import com.suven.framework.fileinter.vo.response.FileUploadUseBusinessResponseVo;

import com.suven.framework.fileinter.dto.request.FileUploadUseBusinessRequestDto;
import com.suven.framework.fileinter.dto.response.FileUploadUseBusinessResponseDto;
import com.suven.framework.fileinter.dto.enums.FileUploadUseBusinessQueryEnum;


/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:21:42
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
 * RequestMapping("/fileinter/fileUploadUseBusiness")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "模块"
)
public class FileUploadUseBusinessWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static interface UrlCommand{
        public static final String fileinter_fileUploadUseBusiness_index      =   "/fileinter/fileuploadusebusiness/index";
        public static final String fileinter_fileUploadUseBusiness_list       =   "/fileinter/fileuploadusebusiness/list";
        public static final String fileinter_fileUploadUseBusiness_queryList  =   "/fileinter/fileuploadusebusiness/querylist";
        public static final String fileinter_fileUploadUseBusiness_add        =   "/fileinter/fileuploadusebusiness/add";
        public static final String fileinter_fileUploadUseBusiness_modify     =   "/fileinter/fileuploadusebusiness/modify";
        public static final String fileinter_fileUploadUseBusiness_detail     =   "/fileinter/fileuploadusebusiness/detail";
        public static final String fileinter_fileUploadUseBusiness_edit       =   "/fileinter/fileuploadusebusiness/edit";
        public static final String fileinter_fileUploadUseBusiness_newInfo    =   "/fileinter/fileuploadusebusiness/newInfo";
        public static final String fileinter_fileUploadUseBusiness_del        =   "/fileinter/fileuploadusebusiness/delete";
        public static final String fileinter_fileUploadUseBusiness_sort       =   "/fileinter/fileuploadusebusiness/sort";
        public static final String fileinter_fileUploadUseBusiness_turnOn     =   "/fileinter/fileuploadusebusiness/turnOn";
        public static final String fileinter_fileUploadUseBusiness_turnOff    =   "/fileinter/fileuploadusebusiness/turnOff";
        public static final String fileinter_fileUploadUseBusiness_export     =   "/fileinter/fileuploadusebusiness/export";
        public static final String fileinter_fileUploadUseBusiness_import     =   "/fileinter/fileuploadusebusiness/import";
    }




    @Autowired
    private FileUploadUseBusinessService  fileUploadUseBusinessService;

    /**
     * Title: 跳转到主界面
     * @return 字符串url
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.fileinter_fileUploadUseBusiness_index,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadusebusiness:index")
    public String index(){
        return "fileinter/fileUploadUseBusiness_index";
    }


    /**
     * Title: 获取分页信息
     * Description:fileUploadUseBusinessQueryRequestVo @{Link FileUploadUseBusinessQueryRequestVo}
     * @param
     * @return  ResponseResultPageVo 对象 List<FileUploadUseBusinessShowResponseVo>
     * @throw
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取分页信息",
            request = FileUploadUseBusinessQueryRequestVo.class,
            response = FileUploadUseBusinessShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadUseBusiness_list,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadusebusiness:list")
    public   void   list( OutputResponse out, FileUploadUseBusinessQueryRequestVo fileUploadUseBusinessQueryRequestVo){
            FileUploadUseBusinessRequestDto fileUploadUseBusinessRequestDto = FileUploadUseBusinessRequestDto.build( ).clone(fileUploadUseBusinessQueryRequestVo);

        Pager<FileUploadUseBusinessRequestDto> pager =  Pager.build(fileUploadUseBusinessQueryRequestVo.getPageNo(),fileUploadUseBusinessQueryRequestVo.getPageSize());
        pager.toParamObject(fileUploadUseBusinessRequestDto );
         FileUploadUseBusinessQueryEnum queryEnum =  FileUploadUseBusinessQueryEnum.DESC_ID;
        ResponseResultPageVo<FileUploadUseBusinessResponseDto> resultList = fileUploadUseBusinessService.getFileUploadUseBusinessByNextPage(queryEnum,pager);
        if(ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())){
            out.write( new ResponseResultPageVo<>());
            return ;
        }

        ResponseResultPageVo<FileUploadUseBusinessShowResponseVo> result = resultList.convertBuild(FileUploadUseBusinessShowResponseVo.class);
        out.write( result);
    }

/**
     * Title: 根据条件查谒分页信息
     * Description:fileUploadUseBusinessQueryRequestVo @{Link FileUploadUseBusinessQueryRequestVo}
     * @param
     * @return   ResponseResultPageVo 对象 List<FileUploadUseBusinessShowResponseVo>
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取分页信息",
            request = FileUploadUseBusinessQueryRequestVo.class,
            response = FileUploadUseBusinessShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadUseBusiness_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadusebusiness:query")
    public   void   queryList( OutputResponse out, FileUploadUseBusinessQueryRequestVo fileUploadUseBusinessQueryRequestVo){
            FileUploadUseBusinessRequestDto fileUploadUseBusinessRequestDto = FileUploadUseBusinessRequestDto.build( ).clone(fileUploadUseBusinessQueryRequestVo);

        FileUploadUseBusinessQueryEnum queryEnum =  FileUploadUseBusinessQueryEnum.DESC_ID;
        List<FileUploadUseBusinessResponseDto> resultList = fileUploadUseBusinessService.getFileUploadUseBusinessListByQuery(queryEnum,fileUploadUseBusinessRequestDto);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList<>());
            return ;
        }

        List<FileUploadUseBusinessShowResponseVo> listVo = IterableConvert.convertList(resultList,FileUploadUseBusinessShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * Title: 新增信息
     * Description:fileUploadUseBusinessAddRequestVo @{Link FileUploadUseBusinessAddRequestVo}
     * @param fileUploadUseBusinessAddRequestVo 对象
     * @return long类型id
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增信息",
            request = FileUploadUseBusinessAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadUseBusiness_add,method = RequestMethod.POST)
    //@RequiresPermissions("fileinter:fileuploadusebusiness:add")
    public  void  add(OutputResponse out, FileUploadUseBusinessAddRequestVo fileUploadUseBusinessAddRequestVo){

            FileUploadUseBusinessRequestDto fileUploadUseBusinessRequestDto =  FileUploadUseBusinessRequestDto.build().clone(fileUploadUseBusinessAddRequestVo);

            //fileUploadUseBusinessRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            FileUploadUseBusinessResponseDto fileUploadUseBusinessresponseDto =  fileUploadUseBusinessService.saveFileUploadUseBusiness(fileUploadUseBusinessRequestDto);
        if(fileUploadUseBusinessresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( fileUploadUseBusinessresponseDto.getId());
    }
    /**
     * Title: 修改信息
     * Description:fileUploadUseBusinessAddRequestVo @{Link FileUploadUseBusinessAddRequestVo}
     * @param  fileUploadUseBusinessAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改信息",
            request = FileUploadUseBusinessAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadUseBusiness_modify , method = RequestMethod.POST)
    //@RequiresPermissions("fileinter:fileuploadusebusiness:modify")
    public  void  modify(OutputResponse out,FileUploadUseBusinessAddRequestVo fileUploadUseBusinessAddRequestVo){

            FileUploadUseBusinessRequestDto fileUploadUseBusinessRequestDto =  FileUploadUseBusinessRequestDto.build().clone(fileUploadUseBusinessAddRequestVo);

        if(fileUploadUseBusinessRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  fileUploadUseBusinessService.updateFileUploadUseBusiness(fileUploadUseBusinessRequestDto);
        out.write(result);
    }

    /**
     * Title: 查看信息
     * Description:fileUploadUseBusinessRequestVo @{Link FileUploadUseBusinessRequestVo}
     * @param
     * @return  FileUploadUseBusinessResponseVo  对象
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = FileUploadUseBusinessShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadUseBusiness_detail,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadusebusiness:list")
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileUploadUseBusinessResponseDto fileUploadUseBusinessResponseDto = fileUploadUseBusinessService.getFileUploadUseBusinessById(idRequestVo.getId());
            FileUploadUseBusinessShowResponseVo vo =  FileUploadUseBusinessShowResponseVo.build().clone(fileUploadUseBusinessResponseDto);
        out.write(vo);
    }



    /**
     * Title: 跳转编辑界面
     * Description:id @{Link Long}
     * @param
     * @return FileUploadUseBusinessShowResponseVo 对象
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = FileUploadUseBusinessShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadUseBusiness_edit , method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadusebusiness:modify")
    public void edit(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileUploadUseBusinessResponseDto fileUploadUseBusinessResponseDto = fileUploadUseBusinessService.getFileUploadUseBusinessById(idRequestVo.getId());
            FileUploadUseBusinessShowResponseVo vo =  FileUploadUseBusinessShowResponseVo.build().clone(fileUploadUseBusinessResponseDto);
        out.write(vo);

    }




    /**
     * Title: 跳转新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.fileinter_fileUploadUseBusiness_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadusebusiness:add")
    public String newInfo(ModelMap modelMap){
        return "fileinter/fileUploadUseBusiness_edit";
    }

    /**
     * Title: 删除信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
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
    @RequestMapping(value = UrlCommand.fileinter_fileUploadUseBusiness_del,method = RequestMethod.POST)
    //@RequiresPermissions("fileinter:fileuploadusebusiness:del")
    public  void  del(OutputResponse out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = fileUploadUseBusinessService.delFileUploadUseBusinessByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * Title: 导出信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven  作者
     * date 2024-04-19 00:21:42 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出信息",
            request = FileUploadUseBusinessQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadUseBusiness_export,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadusebusiness:export")
    public void export(HttpServletResponse response, FileUploadUseBusinessQueryRequestVo fileUploadUseBusinessQueryRequestVo){

            FileUploadUseBusinessRequestDto fileUploadUseBusinessRequestDto = FileUploadUseBusinessRequestDto.build().clone(fileUploadUseBusinessQueryRequestVo);

        Pager<FileUploadUseBusinessRequestDto> pager = Pager.build();
        pager.toPageSize(fileUploadUseBusinessQueryRequestVo.getPageSize()).toPageNo(fileUploadUseBusinessQueryRequestVo.getPageNo());
        pager.toParamObject(fileUploadUseBusinessRequestDto );

        FileUploadUseBusinessQueryEnum queryEnum =  FileUploadUseBusinessQueryEnum.DESC_ID;
        ResponseResultPageVo<FileUploadUseBusinessResponseDto> resultList = fileUploadUseBusinessService.getFileUploadUseBusinessByNextPage(queryEnum,pager);
        List<FileUploadUseBusinessResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, FileUploadUseBusinessResponseVo.class,data,"导出信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.fileinter_fileUploadUseBusiness_import, method = RequestMethod.POST)
    //@RequiresPermissions("fileinter:fileuploadusebusiness:import")
    public void importExcel(OutputResponse out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = fileUploadUseBusinessService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}