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
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.IterableConvert;
import com.suven.framework.core.ObjectTrue;


import com.suven.framework.fileinter.service.FileUploadAppService;
import com.suven.framework.fileinter.vo.request.FileUploadAppQueryRequestVo;
import com.suven.framework.fileinter.vo.request.FileUploadAppAddRequestVo;
import com.suven.framework.fileinter.vo.response.FileUploadAppShowResponseVo;
import com.suven.framework.fileinter.vo.response.FileUploadAppResponseVo;

import com.suven.framework.fileinter.dto.request.FileUploadAppRequestDto;
import com.suven.framework.fileinter.dto.response.FileUploadAppResponseDto;
import com.suven.framework.fileinter.dto.enums.FileUploadAppQueryEnum;


/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:21:49
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
 * RequestMapping("/fileinter/fileUploadApp")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "模块"
)
public class FileUploadAppWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static interface UrlCommand{
        public static final String fileinter_fileUploadApp_index      =   "/fileinter/fileuploadapp/index";
        public static final String fileinter_fileUploadApp_list       =   "/fileinter/fileuploadapp/list";
        public static final String fileinter_fileUploadApp_queryList  =   "/fileinter/fileuploadapp/querylist";
        public static final String fileinter_fileUploadApp_add        =   "/fileinter/fileuploadapp/add";
        public static final String fileinter_fileUploadApp_modify     =   "/fileinter/fileuploadapp/modify";
        public static final String fileinter_fileUploadApp_detail     =   "/fileinter/fileuploadapp/detail";
        public static final String fileinter_fileUploadApp_edit       =   "/fileinter/fileuploadapp/edit";
        public static final String fileinter_fileUploadApp_newInfo    =   "/fileinter/fileuploadapp/newInfo";
        public static final String fileinter_fileUploadApp_del        =   "/fileinter/fileuploadapp/delete";
        public static final String fileinter_fileUploadApp_sort       =   "/fileinter/fileuploadapp/sort";
        public static final String fileinter_fileUploadApp_turnOn     =   "/fileinter/fileuploadapp/turnOn";
        public static final String fileinter_fileUploadApp_turnOff    =   "/fileinter/fileuploadapp/turnOff";
        public static final String fileinter_fileUploadApp_export     =   "/fileinter/fileuploadapp/export";
        public static final String fileinter_fileUploadApp_import     =   "/fileinter/fileuploadapp/import";
    }




    @Autowired
    private FileUploadAppService  fileUploadAppService;

    /**
     * Title: 跳转到主界面
     * @return 字符串url
     * @author suven  作者
     * date 2024-04-19 00:21:49 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.fileinter_fileUploadApp_index,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadapp:index")
    public String index(){
        return "fileinter/fileUploadApp_index";
    }


    /**
     * Title: 获取分页信息
     * Description:fileUploadAppQueryRequestVo @{Link FileUploadAppQueryRequestVo}
     * @param
     * @return  PageResult 对象 List<FileUploadAppShowResponseVo>
     * @throw
     * @author suven  作者
     * date 2024-04-19 00:21:49 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取分页信息",
            request = FileUploadAppQueryRequestVo.class,
            response = FileUploadAppShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadApp_list,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadapp:list")
    public   void   list( OutputResponse out, FileUploadAppQueryRequestVo fileUploadAppQueryRequestVo){
            FileUploadAppRequestDto fileUploadAppRequestDto = FileUploadAppRequestDto.build( ).clone(fileUploadAppQueryRequestVo);

        Pager<FileUploadAppRequestDto> pager =  Pager.of(fileUploadAppQueryRequestVo.getPageNo(),fileUploadAppQueryRequestVo.getPageSize());
        pager.toParamObject(fileUploadAppRequestDto );
         FileUploadAppQueryEnum queryEnum =  FileUploadAppQueryEnum.DESC_ID;
        PageResult<FileUploadAppResponseDto> resultList = fileUploadAppService.getFileUploadAppByNextPage(queryEnum,pager);
        if(ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())){
            out.write( new PageResult<>());
            return ;
        }

        PageResult<FileUploadAppShowResponseVo> result = resultList.convertBuild(FileUploadAppShowResponseVo.class);
        out.write( result);
    }

/**
     * Title: 根据条件查谒分页信息
     * Description:fileUploadAppQueryRequestVo @{Link FileUploadAppQueryRequestVo}
     * @param
     * @return   PageResult 对象 List<FileUploadAppShowResponseVo>
     * @author suven  作者
     * date 2024-04-19 00:21:49 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取分页信息",
            request = FileUploadAppQueryRequestVo.class,
            response = FileUploadAppShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadApp_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadapp:query")
    public   void   queryList( OutputResponse out, FileUploadAppQueryRequestVo fileUploadAppQueryRequestVo){
            FileUploadAppRequestDto fileUploadAppRequestDto = FileUploadAppRequestDto.build( ).clone(fileUploadAppQueryRequestVo);

        FileUploadAppQueryEnum queryEnum =  FileUploadAppQueryEnum.DESC_ID;
        List<FileUploadAppResponseDto> resultList = fileUploadAppService.getFileUploadAppListByQuery(queryEnum,fileUploadAppRequestDto);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList<>());
            return ;
        }

        List<FileUploadAppShowResponseVo> listVo = IterableConvert.convertList(resultList,FileUploadAppShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * Title: 新增信息
     * Description:fileUploadAppAddRequestVo @{Link FileUploadAppAddRequestVo}
     * @param fileUploadAppAddRequestVo 对象
     * @return long类型id
     * @author suven  作者
     * date 2024-04-19 00:21:49 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增信息",
            request = FileUploadAppAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadApp_add,method = RequestMethod.POST)
    //@RequiresPermissions("fileinter:fileuploadapp:add")
    public  void  add(OutputResponse out, FileUploadAppAddRequestVo fileUploadAppAddRequestVo){

            FileUploadAppRequestDto fileUploadAppRequestDto =  FileUploadAppRequestDto.build().clone(fileUploadAppAddRequestVo);

            //fileUploadAppRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            FileUploadAppResponseDto fileUploadAppresponseDto =  fileUploadAppService.saveFileUploadApp(fileUploadAppRequestDto);
        if(fileUploadAppresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( fileUploadAppresponseDto.getId());
    }
    /**
     * Title: 修改信息
     * Description:fileUploadAppAddRequestVo @{Link FileUploadAppAddRequestVo}
     * @param  fileUploadAppAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven  作者
     * date 2024-04-19 00:21:49 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改信息",
            request = FileUploadAppAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadApp_modify , method = RequestMethod.POST)
    //@RequiresPermissions("fileinter:fileuploadapp:modify")
    public  void  modify(OutputResponse out,FileUploadAppAddRequestVo fileUploadAppAddRequestVo){

            FileUploadAppRequestDto fileUploadAppRequestDto =  FileUploadAppRequestDto.build().clone(fileUploadAppAddRequestVo);

        if(fileUploadAppRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  fileUploadAppService.updateFileUploadApp(fileUploadAppRequestDto);
        out.write(result);
    }

    /**
     * Title: 查看信息
     * Description:fileUploadAppRequestVo @{Link FileUploadAppRequestVo}
     * @param
     * @return  FileUploadAppResponseVo  对象
     * @author suven  作者
     * date 2024-04-19 00:21:49 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = FileUploadAppShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadApp_detail,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadapp:list")
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileUploadAppResponseDto fileUploadAppResponseDto = fileUploadAppService.getFileUploadAppById(idRequestVo.getId());
            FileUploadAppShowResponseVo vo =  FileUploadAppShowResponseVo.build().clone(fileUploadAppResponseDto);
        out.write(vo);
    }



    /**
     * Title: 跳转编辑界面
     * Description:id @{Link Long}
     * @param
     * @return FileUploadAppShowResponseVo 对象
     * @author suven  作者
     * date 2024-04-19 00:21:49 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = FileUploadAppShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadApp_edit , method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadapp:modify")
    public void edit(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileUploadAppResponseDto fileUploadAppResponseDto = fileUploadAppService.getFileUploadAppById(idRequestVo.getId());
            FileUploadAppShowResponseVo vo =  FileUploadAppShowResponseVo.build().clone(fileUploadAppResponseDto);
        out.write(vo);

    }




    /**
     * Title: 跳转新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven  作者
     * date 2024-04-19 00:21:49 创建时间
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.fileinter_fileUploadApp_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadapp:add")
    public String newInfo(ModelMap modelMap){
        return "fileinter/fileUploadApp_edit";
    }

    /**
     * Title: 删除信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven  作者
     * date 2024-04-19 00:21:49 创建时间
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
    @RequestMapping(value = UrlCommand.fileinter_fileUploadApp_del,method = RequestMethod.POST)
    //@RequiresPermissions("fileinter:fileuploadapp:del")
    public  void  del(OutputResponse out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = fileUploadAppService.delFileUploadAppByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * Title: 导出信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven  作者
     * date 2024-04-19 00:21:49 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出信息",
            request = FileUploadAppQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadApp_export,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadapp:export")
    public void export(HttpServletResponse response, FileUploadAppQueryRequestVo fileUploadAppQueryRequestVo){

            FileUploadAppRequestDto fileUploadAppRequestDto = FileUploadAppRequestDto.build().clone(fileUploadAppQueryRequestVo);

        Pager<FileUploadAppRequestDto> pager = Pager.of();
        pager.toPageSize(fileUploadAppQueryRequestVo.getPageSize()).toPageNo(fileUploadAppQueryRequestVo.getPageNo());
        pager.toParamObject(fileUploadAppRequestDto );

        FileUploadAppQueryEnum queryEnum =  FileUploadAppQueryEnum.DESC_ID;
        PageResult<FileUploadAppResponseDto> resultList = fileUploadAppService.getFileUploadAppByNextPage(queryEnum,pager);
        List<FileUploadAppResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, FileUploadAppResponseVo.class,data,"导出信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.fileinter_fileUploadApp_import, method = RequestMethod.POST)
    //@RequiresPermissions("fileinter:fileuploadapp:import")
    public void importExcel(OutputResponse out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = fileUploadAppService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}