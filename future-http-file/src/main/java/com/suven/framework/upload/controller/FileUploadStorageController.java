package com.suven.framework.upload.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;

import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.core.ObjectTrue;

import com.suven.framework.upload.facade.FileUploadStorageFacade;
import com.suven.framework.upload.service.FileUploadStorageService;
import com.suven.framework.upload.dto.request.FileUploadStorageRequestDto;
import com.suven.framework.upload.dto.response.FileUploadStorageResponseDto;
import com.suven.framework.upload.dto.enums.FileUploadStorageQueryEnum;
import com.suven.framework.upload.vo.request.FileUploadStorageRequestVo;
import com.suven.framework.upload.vo.response.FileUploadStorageResponseVo;

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
        public static final String upload_fileUploadStorage_pageList = "/upload/fileuploadstorage/pageList";
        public static final String upload_fileUploadStorage_info = "/upload/fileuploadstorage/info";
    }

    /**
    * Title: 分页获取信息
    * Description:fileUploadStorageQueryRequestVo @{Link FileUploadStorageQueryRequestVo}
    * @param
    * @return
    * @author suven
    * date 2024-04-18 23:55:18
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
    @RequestMapping(value = UrlCommand.upload_fileUploadStorage_pageList, method = RequestMethod.GET)
    public void list( OutputResponse out, FileUploadStorageRequestVo fileUploadStorageRequestVo){
            FileUploadStorageRequestDto fileUploadStorageRequestDto = FileUploadStorageRequestDto.build().clone(fileUploadStorageRequestVo);
        Pager<FileUploadStorageRequestDto> pager =  Pager.of( fileUploadStorageRequestVo.getPageNo(),fileUploadStorageRequestVo.getPageSize());
        pager.toParamObject(fileUploadStorageRequestDto );
        PageResult<FileUploadStorageResponseDto> resultList = fileUploadStorageService.getFileUploadStorageByNextPage(FileUploadStorageQueryEnum.DESC_ID,pager);

        if(ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())){
            out.writeSuccess();
            return;
        }
        PageResult<FileUploadStorageRequestVo> list =  resultList.convertBuild(FileUploadStorageRequestVo.class);
        out.write(list);

        out.write(list);
    }





    /**
    * Title: 查看信息
    * Description:HttpRequestByIdVo @{Link HttpRequestByIdVo}
    * @param
    * @return
    * @author suven
    * date 2024-04-18 23:55:18
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
    @RequestMapping(value = UrlCommand.upload_fileUploadStorage_info ,method = RequestMethod.GET)
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileUploadStorageResponseDto fileUploadStorageResponseDto = fileUploadStorageService.getFileUploadStorageById(idRequestVo.getId());
            FileUploadStorageResponseVo vo =  FileUploadStorageResponseVo.build().clone(fileUploadStorageResponseDto);
        out.write(vo);
    }



}
