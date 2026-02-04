package com.suven.framework.upload.controller;


import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.IterableConvert;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.upload.dto.enums.FileUploadActionWaterQueryEnum;
import com.suven.framework.upload.dto.request.FileUploadActionWaterRequestDto;
import com.suven.framework.upload.dto.response.FileUploadActionWaterResponseDto;
import com.suven.framework.upload.service.FileUploadActionWaterService;
import com.suven.framework.upload.vo.request.FileUploadActionWaterAddRequestVo;
import com.suven.framework.upload.vo.request.FileUploadActionWaterQueryRequestVo;
import com.suven.framework.upload.vo.response.FileUploadActionWaterResponseVo;
import com.suven.framework.upload.vo.response.FileUploadActionWaterShowResponseVo;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.util.excel.ExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:14:12
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
 * RequestMapping("/upload/fileUploadActionWater")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "模块"
)
public class FileUploadActionWaterWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static interface UrlCommand{
        public static final String upload_fileUploadActionWater_index      =   "/upload/fileuploadactionwater/index";
        public static final String upload_fileUploadActionWater_list       =   "/upload/fileuploadactionwater/list";
        public static final String upload_fileUploadActionWater_queryList  =   "/upload/fileuploadactionwater/querylist";
        public static final String upload_fileUploadActionWater_add        =   "/upload/fileuploadactionwater/add";
        public static final String upload_fileUploadActionWater_modify     =   "/upload/fileuploadactionwater/modify";
        public static final String upload_fileUploadActionWater_detail     =   "/upload/fileuploadactionwater/detail";
        public static final String upload_fileUploadActionWater_edit       =   "/upload/fileuploadactionwater/edit";
        public static final String upload_fileUploadActionWater_newInfo    =   "/upload/fileuploadactionwater/newInfo";
        public static final String upload_fileUploadActionWater_del        =   "/upload/fileuploadactionwater/delete";
        public static final String upload_fileUploadActionWater_sort       =   "/upload/fileuploadactionwater/sort";
        public static final String upload_fileUploadActionWater_turnOn     =   "/upload/fileuploadactionwater/turnOn";
        public static final String upload_fileUploadActionWater_turnOff    =   "/upload/fileuploadactionwater/turnOff";
        public static final String upload_fileUploadActionWater_export     =   "/upload/fileuploadactionwater/export";
        public static final String upload_fileUploadActionWater_import     =   "/upload/fileuploadactionwater/import";
    }




    @Autowired
    private FileUploadActionWaterService  fileUploadActionWaterService;

    /**
     * Title: 跳转到主界面
     * @return 字符串url
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.upload_fileUploadActionWater_index,method = RequestMethod.GET)
    //@RequiresPermissions("upload:fileuploadactionwater:index")
    public String index(){
        return "upload/fileUploadActionWater_index";
    }


    /**
     * Title: 获取分页信息
     * Description:fileUploadActionWaterQueryRequestVo @{Link FileUploadActionWaterQueryRequestVo}
     * @param
     * @return  PageResult 对象 List<FileUploadActionWaterShowResponseVo>
     * @throw
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取分页信息",
            request = FileUploadActionWaterQueryRequestVo.class,
            response = FileUploadActionWaterShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.upload_fileUploadActionWater_list,method = RequestMethod.GET)
    //@RequiresPermissions("upload:fileuploadactionwater:list")
    public   void   list( OutputResponse out, FileUploadActionWaterQueryRequestVo fileUploadActionWaterQueryRequestVo){
            FileUploadActionWaterRequestDto fileUploadActionWaterRequestDto = FileUploadActionWaterRequestDto.build( ).clone(fileUploadActionWaterQueryRequestVo);

        Pager<FileUploadActionWaterRequestDto> pager =  Pager.of();
        pager.toPageSize(fileUploadActionWaterQueryRequestVo.getPageSize()).toPageNo(fileUploadActionWaterQueryRequestVo.getPageNo());
        pager.toParamObject(fileUploadActionWaterRequestDto );
         FileUploadActionWaterQueryEnum queryEnum =  FileUploadActionWaterQueryEnum.DESC_ID;
        PageResult<FileUploadActionWaterResponseDto> resultList = fileUploadActionWaterService.getFileUploadActionWaterByNextPage(queryEnum,pager);
        if(ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())){
            out.write( new PageResult<>());
            return ;
        }

        PageResult<FileUploadActionWaterShowResponseVo> result = resultList.convertBuild(FileUploadActionWaterShowResponseVo.class);
        out.write( result);
    }

/**
     * Title: 根据条件查谒分页信息
     * Description:fileUploadActionWaterQueryRequestVo @{Link FileUploadActionWaterQueryRequestVo}
     * @param
     * @return   PageResult 对象 List<FileUploadActionWaterShowResponseVo>
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取分页信息",
            request = FileUploadActionWaterQueryRequestVo.class,
            response = FileUploadActionWaterShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.upload_fileUploadActionWater_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("upload:fileuploadactionwater:query")
    public   void   queryList( OutputResponse out, FileUploadActionWaterQueryRequestVo fileUploadActionWaterQueryRequestVo){
            FileUploadActionWaterRequestDto fileUploadActionWaterRequestDto = FileUploadActionWaterRequestDto.build( ).clone(fileUploadActionWaterQueryRequestVo);

        FileUploadActionWaterQueryEnum queryEnum =  FileUploadActionWaterQueryEnum.DESC_ID;
        List<FileUploadActionWaterResponseDto> resultList = fileUploadActionWaterService.getFileUploadActionWaterListByQuery(queryEnum,fileUploadActionWaterRequestDto);
        if(null == resultList || resultList.isEmpty() ){
            out.write( new ArrayList<>());
            return ;
        }

        List<FileUploadActionWaterShowResponseVo> listVo = IterableConvert.convertList(resultList,FileUploadActionWaterShowResponseVo.class);

        out.write( listVo);
    }



    /**
     * Title: 新增信息
     * Description:fileUploadActionWaterAddRequestVo @{Link FileUploadActionWaterAddRequestVo}
     * @param fileUploadActionWaterAddRequestVo 对象
     * @return long类型id
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增信息",
            request = FileUploadActionWaterAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.upload_fileUploadActionWater_add,method = RequestMethod.POST)
    //@RequiresPermissions("upload:fileuploadactionwater:add")
    public  void  add(OutputResponse out, FileUploadActionWaterAddRequestVo fileUploadActionWaterAddRequestVo){

            FileUploadActionWaterRequestDto fileUploadActionWaterRequestDto =  FileUploadActionWaterRequestDto.build().clone(fileUploadActionWaterAddRequestVo);

            //fileUploadActionWaterRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            FileUploadActionWaterResponseDto fileUploadActionWaterresponseDto =  fileUploadActionWaterService.saveFileUploadActionWater(fileUploadActionWaterRequestDto);
        if(fileUploadActionWaterresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( fileUploadActionWaterresponseDto.getId());
    }
    /**
     * Title: 修改信息
     * Description:fileUploadActionWaterAddRequestVo @{Link FileUploadActionWaterAddRequestVo}
     * @param  fileUploadActionWaterAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改信息",
            request = FileUploadActionWaterAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.upload_fileUploadActionWater_modify , method = RequestMethod.POST)
    //@RequiresPermissions("upload:fileuploadactionwater:modify")
    public  void  modify(OutputResponse out,FileUploadActionWaterAddRequestVo fileUploadActionWaterAddRequestVo){

            FileUploadActionWaterRequestDto fileUploadActionWaterRequestDto =  FileUploadActionWaterRequestDto.build().clone(fileUploadActionWaterAddRequestVo);

        if(fileUploadActionWaterRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  fileUploadActionWaterService.updateFileUploadActionWater(fileUploadActionWaterRequestDto);
        out.write(result);
    }

    /**
     * Title: 查看信息
     * Description:fileUploadActionWaterRequestVo @{Link FileUploadActionWaterRequestVo}
     * @param
     * @return  FileUploadActionWaterResponseVo  对象
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = FileUploadActionWaterShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.upload_fileUploadActionWater_detail,method = RequestMethod.GET)
    //@RequiresPermissions("upload:fileuploadactionwater:list")
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileUploadActionWaterResponseDto fileUploadActionWaterResponseDto = fileUploadActionWaterService.getFileUploadActionWaterById(idRequestVo.getId());
            FileUploadActionWaterShowResponseVo vo =  FileUploadActionWaterShowResponseVo.build().clone(fileUploadActionWaterResponseDto);
        out.write(vo);
    }



    /**
     * Title: 跳转编辑界面
     * Description:id @{Link Long}
     * @param
     * @return FileUploadActionWaterShowResponseVo 对象
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = FileUploadActionWaterShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.upload_fileUploadActionWater_edit , method = RequestMethod.GET)
    //@RequiresPermissions("upload:fileuploadactionwater:modify")
    public void edit(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileUploadActionWaterResponseDto fileUploadActionWaterResponseDto = fileUploadActionWaterService.getFileUploadActionWaterById(idRequestVo.getId());
            FileUploadActionWaterShowResponseVo vo =  FileUploadActionWaterShowResponseVo.build().clone(fileUploadActionWaterResponseDto);
        out.write(vo);

    }




    /**
     * Title: 跳转新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.upload_fileUploadActionWater_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("upload:fileuploadactionwater:add")
    public String newInfo(ModelMap modelMap){
        return "upload/fileUploadActionWater_edit";
    }

    /**
     * Title: 删除信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
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
    @RequestMapping(value = UrlCommand.upload_fileUploadActionWater_del,method = RequestMethod.POST)
    //@RequiresPermissions("upload:fileuploadactionwater:del")
    public  void  del(OutputResponse out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = fileUploadActionWaterService.delFileUploadActionWaterByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * Title: 导出信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven  作者
     * date 2024-04-19 00:14:12 创建时间
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出信息",
            request = FileUploadActionWaterQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.upload_fileUploadActionWater_export,method = RequestMethod.GET)
    //@RequiresPermissions("upload:fileuploadactionwater:export")
    public void export(HttpServletResponse response, FileUploadActionWaterQueryRequestVo fileUploadActionWaterQueryRequestVo){

            FileUploadActionWaterRequestDto fileUploadActionWaterRequestDto = FileUploadActionWaterRequestDto.build().clone(fileUploadActionWaterQueryRequestVo);

        Pager<FileUploadActionWaterRequestDto> pager = Pager.of();
        pager.toPageSize(fileUploadActionWaterQueryRequestVo.getPageSize()).toPageNo(fileUploadActionWaterQueryRequestVo.getPageNo());
        pager.toParamObject(fileUploadActionWaterRequestDto );

        FileUploadActionWaterQueryEnum queryEnum =  FileUploadActionWaterQueryEnum.DESC_ID;
        PageResult<FileUploadActionWaterResponseDto> resultList = fileUploadActionWaterService.getFileUploadActionWaterByNextPage(queryEnum,pager);
        List<FileUploadActionWaterResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, FileUploadActionWaterResponseVo.class,data,"导出信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.upload_fileUploadActionWater_import, method = RequestMethod.POST)
    //@RequiresPermissions("upload:fileuploadactionwater:import")
    public void importExcel(OutputResponse out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = fileUploadActionWaterService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}