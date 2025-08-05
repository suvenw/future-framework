package com.suven.framework.fileinter.controller;



import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;

import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.ResultPageVo;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.core.IterableConvert;

import com.suven.framework.fileinter.facade.FileUploadStorageFacade;
import com.suven.framework.fileinter.service.FileUploadStorageService;
import com.suven.framework.fileinter.dto.request.FileUploadStorageRequestDto;
import com.suven.framework.fileinter.dto.response.FileUploadStorageResponseDto;
import com.suven.framework.fileinter.dto.enums.FileUploadStorageQueryEnum;
import com.suven.framework.fileinter.vo.request.FileUploadStorageRequestVo;
import com.suven.framework.fileinter.vo.response.FileUploadStorageResponseVo;

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
 * </pre>
 *  Copyright: (c) 2021 gc by https://www.suven.top
 **/

@ApiDoc(
        group = DocumentConst.Api.API_DOC_GROUP,
        groupDesc= DocumentConst.Api.API_DOC_DES,
        module = "模块", isApp = true
)
@Controller
public class FileUploadStorageController {

    @Autowired
    private FileUploadStorageFacade  fileUploadStorageFacade;

    @Autowired
    private FileUploadStorageService  fileUploadStorageService;

    public interface UrlCommand{
        public static final String fileinter_fileUploadStorage_pageList = "/fileinter/fileuploadstorage/pageList";
        public static final String fileinter_fileUploadStorage_info = "/fileinter/fileuploadstorage/info";
    }

    /**
    * @Title: 分页获取信息
    * Description:fileUploadStorageQueryRequestVo @{Link FileUploadStorageQueryRequestVo}
    * @param
    * @return
    * @author suven
    * @date 2024-04-18 23:55:18
    *  --------------------------------------------------------
    *  modifyer    modifyTime                 comment
    *
    *  --------------------------------------------------------
    */
    @ApiDoc(
            value = "分页获取信息",
            request = FileUploadStorageRequestVo.class,
            response = FileUploadStorageResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadStorage_pageList, method = RequestMethod.GET)
    public void list( OutputResponse out, FileUploadStorageRequestVo fileUploadStorageRequestVo){
            FileUploadStorageRequestDto fileUploadStorageRequestDto = FileUploadStorageRequestDto.build().clone(fileUploadStorageRequestVo);
        Pager pager = Pager.build().toPageSize(fileUploadStorageRequestVo.getPageSize()).toPageNo(fileUploadStorageRequestVo.getPageNo());
        pager.toParamObject(fileUploadStorageRequestDto );
        ResultPageVo<FileUploadStorageResponseDto> resultList = fileUploadStorageService.getFileUploadStorageByNextPage(FileUploadStorageQueryEnum.DESC_ID,pager);

        if(ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())){
            out.writeSuccess();
            return;
        }
        ResultPageVo<FileUploadStorageRequestVo> list =  resultList.convertBuild(FileUploadStorageRequestVo.class);
        out.write(list);

        out.write(list);
    }





    /**
    * @Title: 查看信息
    * Description:HttpRequestByIdVo @{Link HttpRequestByIdVo}
    * @param
    * @return
    * @author suven
    * @date 2024-04-18 23:55:18
    *  --------------------------------------------------------
    *  modifyer    modifyTime                 comment
    *
    *  --------------------------------------------------------
    */
    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = FileUploadStorageResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadStorage_info ,method = RequestMethod.GET)
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileUploadStorageResponseDto fileUploadStorageResponseDto = fileUploadStorageService.getFileUploadStorageById(idRequestVo.getId());
            FileUploadStorageResponseVo vo =  FileUploadStorageResponseVo.build().clone(fileUploadStorageResponseDto);
        out.write(vo);
    }



}
