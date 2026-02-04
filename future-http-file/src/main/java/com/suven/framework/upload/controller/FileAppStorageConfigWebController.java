package com.suven.framework.upload.controller;



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
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.IterableConvert;
import com.suven.framework.core.ObjectTrue;


import com.suven.framework.upload.service.FileAppStorageConfigService;
import com.suven.framework.upload.vo.request.FileAppStorageConfigQueryRequestVo;
import com.suven.framework.upload.vo.request.FileAppStorageConfigAddRequestVo;
import com.suven.framework.upload.vo.response.FileAppStorageConfigShowResponseVo;
import com.suven.framework.upload.vo.response.FileAppStorageConfigResponseVo;

import com.suven.framework.upload.dto.request.FileAppStorageConfigRequestDto;
import com.suven.framework.upload.dto.response.FileAppStorageConfigResponseDto;
import com.suven.framework.upload.dto.enums.FileAppStorageConfigQueryEnum;


/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:21:54
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
 * RequestMapping("/upload/fileAppStorageConfig")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "模块"
)
public class FileAppStorageConfigWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static interface UrlCommand{
        public static final String upload_fileAppStorageConfig_index      =   "/upload/fileappstorageconfig/index";
        public static final String upload_fileAppStorageConfig_list       =   "/upload/fileappstorageconfig/list";
        public static final String upload_fileAppStorageConfig_queryList  =   "/upload/fileappstorageconfig/querylist";
        public static final String upload_fileAppStorageConfig_add        =   "/upload/fileappstorageconfig/add";
        public static final String upload_fileAppStorageConfig_modify     =   "/upload/fileappstorageconfig/modify";
        public static final String upload_fileAppStorageConfig_detail     =   "/upload/fileappstorageconfig/detail";
        public static final String upload_fileAppStorageConfig_edit       =   "/upload/fileappstorageconfig/edit";
        public static final String upload_fileAppStorageConfig_newInfo    =   "/upload/fileappstorageconfig/newInfo";
        public static final String upload_fileAppStorageConfig_del        =   "/upload/fileappstorageconfig/delete";
        public static final String upload_fileAppStorageConfig_sort       =   "/upload/fileappstorageconfig/sort";
        public static final String upload_fileAppStorageConfig_turnOn     =   "/upload/fileappstorageconfig/turnOn";
        public static final String upload_fileAppStorageConfig_turnOff    =   "/upload/fileappstorageconfig/turnOff";
        public static final String upload_fileAppStorageConfig_export     =   "/upload/fileappstorageconfig/export";
        public static final String upload_fileAppStorageConfig_import     =   "/upload/fileappstorageconfig/import";
    }




    @Autowired
    private FileAppStorageConfigService  fileAppStorageConfigService;

    /**
     * Title: 跳转到主界面
     * @return 字符串url
     * @author suven  作者
     * date 2024-04-19 00:21:54 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.upload_fileAppStorageConfig_index,method = RequestMethod.GET)
    //@RequiresPermissions("upload:fileappstorageconfig:index")
    public String index(){
        return "upload/fileAppStorageConfig_index";
    }


    /**
     * Title: 获取分页信息
     * Description:fileAppStorageConfigQueryRequestVo @{Link FileAppStorageConfigQueryRequestVo}
     * @param
     * @return  PageResult 对象 List<FileAppStorageConfigShowResponseVo>
     * @throw
     * @author suven  作者
     * date 2024-04-19 00:21:54 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取分页信息",
            request = FileAppStorageConfigQueryRequestVo.class,
            response = FileAppStorageConfigShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.upload_fileAppStorageConfig_list,method = RequestMethod.GET)
    //@RequiresPermissions("upload:fileappstorageconfig:list")
    public   void   list( OutputResponse out, FileAppStorageConfigQueryRequestVo fileAppStorageConfigQueryRequestVo){
            FileAppStorageConfigRequestDto fileAppStorageConfigRequestDto = FileAppStorageConfigRequestDto.build( ).clone(fileAppStorageConfigQueryRequestVo);

        Pager<FileAppStorageConfigRequestDto> pager =  Pager.of();
        pager.toPageSize(fileAppStorageConfigQueryRequestVo.getPageSize()).toPageNo(fileAppStorageConfigQueryRequestVo.getPageNo());
        pager.toParamObject(fileAppStorageConfigRequestDto );
         FileAppStorageConfigQueryEnum queryEnum =  FileAppStorageConfigQueryEnum.DESC_ID;
        PageResult<FileAppStorageConfigResponseDto> resultList = fileAppStorageConfigService.getFileAppStorageConfigByNextPage(queryEnum,pager);
        if(ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())){
            out.write( new PageResult<>());
            return ;
        }

        PageResult<FileAppStorageConfigShowResponseVo> result = resultList.convertBuild(FileAppStorageConfigShowResponseVo.class);
        out.write( result);
    }

/**
     * Title: 根据条件查谒分页信息
     * Description:fileAppStorageConfigQueryRequestVo @{Link FileAppStorageConfigQueryRequestVo}
     * @param
     * @return   PageResult 对象 List<FileAppStorageConfigShowResponseVo>
     * @author suven  作者
     * date 2024-04-19 00:21:54 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取分页信息",
            request = FileAppStorageConfigQueryRequestVo.class,
            response = FileAppStorageConfigShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.upload_fileAppStorageConfig_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("upload:fileappstorageconfig:query")
    public   void   queryList( OutputResponse out, FileAppStorageConfigQueryRequestVo fileAppStorageConfigQueryRequestVo){
            FileAppStorageConfigRequestDto fileAppStorageConfigRequestDto = FileAppStorageConfigRequestDto.build( ).clone(fileAppStorageConfigQueryRequestVo);

        FileAppStorageConfigQueryEnum queryEnum =  FileAppStorageConfigQueryEnum.DESC_ID;
        List<FileAppStorageConfigResponseDto> resultList = fileAppStorageConfigService.getFileAppStorageConfigListByQuery(queryEnum,fileAppStorageConfigRequestDto);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList<>());
            return ;
        }

        List<FileAppStorageConfigShowResponseVo> listVo = IterableConvert.convertList(resultList,FileAppStorageConfigShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * Title: 新增信息
     * Description:fileAppStorageConfigAddRequestVo @{Link FileAppStorageConfigAddRequestVo}
     * @param fileAppStorageConfigAddRequestVo 对象
     * @return long类型id
     * @author suven  作者
     * date 2024-04-19 00:21:54 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增信息",
            request = FileAppStorageConfigAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.upload_fileAppStorageConfig_add,method = RequestMethod.POST)
    //@RequiresPermissions("upload:fileappstorageconfig:add")
    public  void  add(OutputResponse out, FileAppStorageConfigAddRequestVo fileAppStorageConfigAddRequestVo){

            FileAppStorageConfigRequestDto fileAppStorageConfigRequestDto =  FileAppStorageConfigRequestDto.build().clone(fileAppStorageConfigAddRequestVo);

            //fileAppStorageConfigRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            FileAppStorageConfigResponseDto fileAppStorageConfigresponseDto =  fileAppStorageConfigService.saveFileAppStorageConfig(fileAppStorageConfigRequestDto);
        if(fileAppStorageConfigresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( fileAppStorageConfigresponseDto.getId());
    }
    /**
     * Title: 修改信息
     * Description:fileAppStorageConfigAddRequestVo @{Link FileAppStorageConfigAddRequestVo}
     * @param  fileAppStorageConfigAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven  作者
     * date 2024-04-19 00:21:54 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改信息",
            request = FileAppStorageConfigAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.upload_fileAppStorageConfig_modify , method = RequestMethod.POST)
    //@RequiresPermissions("upload:fileappstorageconfig:modify")
    public  void  modify(OutputResponse out,FileAppStorageConfigAddRequestVo fileAppStorageConfigAddRequestVo){

            FileAppStorageConfigRequestDto fileAppStorageConfigRequestDto =  FileAppStorageConfigRequestDto.build().clone(fileAppStorageConfigAddRequestVo);

        if(fileAppStorageConfigRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  fileAppStorageConfigService.updateFileAppStorageConfig(fileAppStorageConfigRequestDto);
        out.write(result);
    }

    /**
     * Title: 查看信息
     * Description:fileAppStorageConfigRequestVo @{Link FileAppStorageConfigRequestVo}
     * @param
     * @return  FileAppStorageConfigResponseVo  对象
     * @author suven  作者
     * date 2024-04-19 00:21:54 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = FileAppStorageConfigShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.upload_fileAppStorageConfig_detail,method = RequestMethod.GET)
    //@RequiresPermissions("upload:fileappstorageconfig:list")
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileAppStorageConfigResponseDto fileAppStorageConfigResponseDto = fileAppStorageConfigService.getFileAppStorageConfigById(idRequestVo.getId());
            FileAppStorageConfigShowResponseVo vo =  FileAppStorageConfigShowResponseVo.build().clone(fileAppStorageConfigResponseDto);
        out.write(vo);
    }



    /**
     * Title: 跳转编辑界面
     * Description:id @{Link Long}
     * @param
     * @return FileAppStorageConfigShowResponseVo 对象
     * @author suven  作者
     * date 2024-04-19 00:21:54 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = FileAppStorageConfigShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.upload_fileAppStorageConfig_edit , method = RequestMethod.GET)
    //@RequiresPermissions("upload:fileappstorageconfig:modify")
    public void edit(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileAppStorageConfigResponseDto fileAppStorageConfigResponseDto = fileAppStorageConfigService.getFileAppStorageConfigById(idRequestVo.getId());
            FileAppStorageConfigShowResponseVo vo =  FileAppStorageConfigShowResponseVo.build().clone(fileAppStorageConfigResponseDto);
        out.write(vo);

    }




    /**
     * Title: 跳转新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven  作者
     * date 2024-04-19 00:21:54 创建时间
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.upload_fileAppStorageConfig_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("upload:fileappstorageconfig:add")
    public String newInfo(ModelMap modelMap){
        return "upload/fileAppStorageConfig_edit";
    }

    /**
     * Title: 删除信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven  作者
     * date 2024-04-19 00:21:54 创建时间
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
    @RequestMapping(value = UrlCommand.upload_fileAppStorageConfig_del,method = RequestMethod.POST)
    //@RequiresPermissions("upload:fileappstorageconfig:del")
    public  void  del(OutputResponse out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = fileAppStorageConfigService.delFileAppStorageConfigByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * Title: 导出信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven  作者
     * date 2024-04-19 00:21:54 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出信息",
            request = FileAppStorageConfigQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.upload_fileAppStorageConfig_export,method = RequestMethod.GET)
    //@RequiresPermissions("upload:fileappstorageconfig:export")
    public void export(HttpServletResponse response, FileAppStorageConfigQueryRequestVo fileAppStorageConfigQueryRequestVo){

            FileAppStorageConfigRequestDto fileAppStorageConfigRequestDto = FileAppStorageConfigRequestDto.build().clone(fileAppStorageConfigQueryRequestVo);

        Pager<FileAppStorageConfigRequestDto> pager = Pager.of();
        pager.toPageSize(fileAppStorageConfigQueryRequestVo.getPageSize()).toPageNo(fileAppStorageConfigQueryRequestVo.getPageNo());
        pager.toParamObject(fileAppStorageConfigRequestDto );

        FileAppStorageConfigQueryEnum queryEnum =  FileAppStorageConfigQueryEnum.DESC_ID;
        PageResult<FileAppStorageConfigResponseDto> resultList = fileAppStorageConfigService.getFileAppStorageConfigByNextPage(queryEnum,pager);
        List<FileAppStorageConfigResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, FileAppStorageConfigResponseVo.class,data,"导出信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.upload_fileAppStorageConfig_import, method = RequestMethod.POST)
    //@RequiresPermissions("upload:fileappstorageconfig:import")
    public void importExcel(OutputResponse out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = fileAppStorageConfigService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}