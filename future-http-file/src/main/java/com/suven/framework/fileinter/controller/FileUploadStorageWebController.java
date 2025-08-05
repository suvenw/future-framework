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


import com.suven.framework.fileinter.service.FileUploadStorageService;
import com.suven.framework.fileinter.vo.request.FileUploadStorageQueryRequestVo;
import com.suven.framework.fileinter.vo.request.FileUploadStorageAddRequestVo;
import com.suven.framework.fileinter.vo.response.FileUploadStorageShowResponseVo;
import com.suven.framework.fileinter.vo.response.FileUploadStorageResponseVo;

import com.suven.framework.fileinter.dto.request.FileUploadStorageRequestDto;
import com.suven.framework.fileinter.dto.response.FileUploadStorageResponseDto;
import com.suven.framework.fileinter.dto.enums.FileUploadStorageQueryEnum;


/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-18 23:55:18
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
 * RequestMapping("/fileinter/fileUploadStorage")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "模块"
)
public class FileUploadStorageWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static interface UrlCommand{
        public static final String fileinter_fileUploadStorage_index      =   "/fileinter/fileuploadstorage/index";
        public static final String fileinter_fileUploadStorage_list       =   "/fileinter/fileuploadstorage/list";
        public static final String fileinter_fileUploadStorage_queryList  =   "/fileinter/fileuploadstorage/querylist";
        public static final String fileinter_fileUploadStorage_add        =   "/fileinter/fileuploadstorage/add";
        public static final String fileinter_fileUploadStorage_modify     =   "/fileinter/fileuploadstorage/modify";
        public static final String fileinter_fileUploadStorage_detail     =   "/fileinter/fileuploadstorage/detail";
        public static final String fileinter_fileUploadStorage_edit       =   "/fileinter/fileuploadstorage/edit";
        public static final String fileinter_fileUploadStorage_newInfo    =   "/fileinter/fileuploadstorage/newInfo";
        public static final String fileinter_fileUploadStorage_del        =   "/fileinter/fileuploadstorage/delete";
        public static final String fileinter_fileUploadStorage_sort       =   "/fileinter/fileuploadstorage/sort";
        public static final String fileinter_fileUploadStorage_turnOn     =   "/fileinter/fileuploadstorage/turnOn";
        public static final String fileinter_fileUploadStorage_turnOff    =   "/fileinter/fileuploadstorage/turnOff";
        public static final String fileinter_fileUploadStorage_export     =   "/fileinter/fileuploadstorage/export";
        public static final String fileinter_fileUploadStorage_import     =   "/fileinter/fileuploadstorage/import";
    }




    @Autowired
    private FileUploadStorageService  fileUploadStorageService;

    /**
     * Title: 跳转到主界面
     * @return 字符串url
     * @author suven  作者
     * date 2024-04-18 23:55:18 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.fileinter_fileUploadStorage_index,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadstorage:index")
    public String index(){
        return "fileinter/fileUploadStorage_index";
    }


    /**
     * Title: 获取分页信息
     * Description:fileUploadStorageQueryRequestVo @{Link FileUploadStorageQueryRequestVo}
     * @param
     * @return  ResponseResultPageVo 对象 List<FileUploadStorageShowResponseVo>
     * @throw
     * @author suven  作者
     * date 2024-04-18 23:55:18 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取分页信息",
            request = FileUploadStorageQueryRequestVo.class,
            response = FileUploadStorageShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadStorage_list,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadstorage:list")
    public   void   list( OutputResponse out, FileUploadStorageQueryRequestVo fileUploadStorageQueryRequestVo){
            FileUploadStorageRequestDto fileUploadStorageRequestDto = FileUploadStorageRequestDto.build( ).clone(fileUploadStorageQueryRequestVo);

        Pager<FileUploadStorageRequestDto> pager =  Pager.build();
        pager.toPageSize(fileUploadStorageQueryRequestVo.getPageSize()).toPageNo(fileUploadStorageQueryRequestVo.getPageNo());
        pager.toParamObject(fileUploadStorageRequestDto );
         FileUploadStorageQueryEnum queryEnum =  FileUploadStorageQueryEnum.DESC_ID;
        ResponseResultPageVo<FileUploadStorageResponseDto> resultList = fileUploadStorageService.getFileUploadStorageByNextPage(queryEnum,pager);
        if(ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())){
            out.write( new ResponseResultPageVo<>());
            return ;
        }

        ResponseResultPageVo<FileUploadStorageShowResponseVo> result = resultList.convertBuild(FileUploadStorageShowResponseVo.class);
        out.write( result);
    }

/**
     * Title: 根据条件查谒分页信息
     * Description:fileUploadStorageQueryRequestVo @{Link FileUploadStorageQueryRequestVo}
     * @param
     * @return   ResponseResultPageVo 对象 List<FileUploadStorageShowResponseVo>
     * @author suven  作者
     * date 2024-04-18 23:55:18 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取分页信息",
            request = FileUploadStorageQueryRequestVo.class,
            response = FileUploadStorageShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadStorage_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadstorage:query")
    public   void   queryList( OutputResponse out, FileUploadStorageQueryRequestVo fileUploadStorageQueryRequestVo){
            FileUploadStorageRequestDto fileUploadStorageRequestDto = FileUploadStorageRequestDto.build( ).clone(fileUploadStorageQueryRequestVo);

        FileUploadStorageQueryEnum queryEnum =  FileUploadStorageQueryEnum.DESC_ID;
        List<FileUploadStorageResponseDto> resultList = fileUploadStorageService.getFileUploadStorageListByQuery(queryEnum,fileUploadStorageRequestDto);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList<>());
            return ;
        }

        List<FileUploadStorageShowResponseVo> listVo = IterableConvert.convertList(resultList,FileUploadStorageShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * Title: 新增信息
     * Description:fileUploadStorageAddRequestVo @{Link FileUploadStorageAddRequestVo}
     * @param fileUploadStorageAddRequestVo 对象
     * @return long类型id
     * @author suven  作者
     * date 2024-04-18 23:55:18 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增信息",
            request = FileUploadStorageAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadStorage_add,method = RequestMethod.POST)
    //@RequiresPermissions("fileinter:fileuploadstorage:add")
    public  void  add(OutputResponse out, FileUploadStorageAddRequestVo fileUploadStorageAddRequestVo){

            FileUploadStorageRequestDto fileUploadStorageRequestDto =  FileUploadStorageRequestDto.build().clone(fileUploadStorageAddRequestVo);

            //fileUploadStorageRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            FileUploadStorageResponseDto fileUploadStorageresponseDto =  fileUploadStorageService.saveFileUploadStorage(fileUploadStorageRequestDto);
        if(fileUploadStorageresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( fileUploadStorageresponseDto.getId());
    }
    /**
     * Title: 修改信息
     * Description:fileUploadStorageAddRequestVo @{Link FileUploadStorageAddRequestVo}
     * @param  fileUploadStorageAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven  作者
     * date 2024-04-18 23:55:18 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改信息",
            request = FileUploadStorageAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadStorage_modify , method = RequestMethod.POST)
    //@RequiresPermissions("fileinter:fileuploadstorage:modify")
    public  void  modify(OutputResponse out,FileUploadStorageAddRequestVo fileUploadStorageAddRequestVo){

            FileUploadStorageRequestDto fileUploadStorageRequestDto =  FileUploadStorageRequestDto.build().clone(fileUploadStorageAddRequestVo);

        if(fileUploadStorageRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  fileUploadStorageService.updateFileUploadStorage(fileUploadStorageRequestDto);
        out.write(result);
    }

    /**
     * Title: 查看信息
     * Description:fileUploadStorageRequestVo @{Link FileUploadStorageRequestVo}
     * @param
     * @return  FileUploadStorageResponseVo  对象
     * @author suven  作者
     * date 2024-04-18 23:55:18 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = FileUploadStorageShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadStorage_detail,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadstorage:list")
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileUploadStorageResponseDto fileUploadStorageResponseDto = fileUploadStorageService.getFileUploadStorageById(idRequestVo.getId());
            FileUploadStorageShowResponseVo vo =  FileUploadStorageShowResponseVo.build().clone(fileUploadStorageResponseDto);
        out.write(vo);
    }



    /**
     * Title: 跳转编辑界面
     * Description:id @{Link Long}
     * @param
     * @return FileUploadStorageShowResponseVo 对象
     * @author suven  作者
     * date 2024-04-18 23:55:18 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = FileUploadStorageShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadStorage_edit , method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadstorage:modify")
    public void edit(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileUploadStorageResponseDto fileUploadStorageResponseDto = fileUploadStorageService.getFileUploadStorageById(idRequestVo.getId());
            FileUploadStorageShowResponseVo vo =  FileUploadStorageShowResponseVo.build().clone(fileUploadStorageResponseDto);
        out.write(vo);

    }




    /**
     * Title: 跳转新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven  作者
     * date 2024-04-18 23:55:18 创建时间
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.fileinter_fileUploadStorage_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadstorage:add")
    public String newInfo(ModelMap modelMap){
        return "fileinter/fileUploadStorage_edit";
    }

    /**
     * Title: 删除信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven  作者
     * date 2024-04-18 23:55:18 创建时间
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
    @RequestMapping(value = UrlCommand.fileinter_fileUploadStorage_del,method = RequestMethod.POST)
    //@RequiresPermissions("fileinter:fileuploadstorage:del")
    public  void  del(OutputResponse out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = fileUploadStorageService.delFileUploadStorageByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * Title: 导出信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven  作者
     * date 2024-04-18 23:55:18 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出信息",
            request = FileUploadStorageQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadStorage_export,method = RequestMethod.GET)
    //@RequiresPermissions("fileinter:fileuploadstorage:export")
    public void export(HttpServletResponse response, FileUploadStorageQueryRequestVo fileUploadStorageQueryRequestVo){

            FileUploadStorageRequestDto fileUploadStorageRequestDto = FileUploadStorageRequestDto.build().clone(fileUploadStorageQueryRequestVo);

        Pager<FileUploadStorageRequestDto> pager = Pager.build();
        pager.toPageSize(fileUploadStorageQueryRequestVo.getPageSize()).toPageNo(fileUploadStorageQueryRequestVo.getPageNo());
        pager.toParamObject(fileUploadStorageRequestDto );

        FileUploadStorageQueryEnum queryEnum =  FileUploadStorageQueryEnum.DESC_ID;
        ResponseResultPageVo<FileUploadStorageResponseDto> resultList = fileUploadStorageService.getFileUploadStorageByNextPage(queryEnum,pager);
        List<FileUploadStorageResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, FileUploadStorageResponseVo.class,data,"导出信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.fileinter_fileUploadStorage_import, method = RequestMethod.POST)
    //@RequiresPermissions("fileinter:fileuploadstorage:import")
    public void importExcel(OutputResponse out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = fileUploadStorageService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}