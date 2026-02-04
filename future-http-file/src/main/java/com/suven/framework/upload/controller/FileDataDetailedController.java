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

import com.suven.framework.upload.facade.FileDataDetailedFacade;
import com.suven.framework.upload.service.FileDataDetailedService;
import com.suven.framework.upload.dto.request.FileDataDetailedRequestDto;
import com.suven.framework.upload.dto.response.FileDataDetailedResponseDto;
import com.suven.framework.upload.dto.enums.FileDataDetailedQueryEnum;
import com.suven.framework.upload.vo.request.FileDataDetailedRequestVo;
import com.suven.framework.upload.vo.response.FileDataDetailedResponseVo;

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
 * </pre>
 *  Copyright: (c) 2021 gc by https://www.suven.top
 **/

@ApiDoc(
        group = DocumentConst.Api.API_DOC_GROUP,
        groupDesc= DocumentConst.Api.API_DOC_DES,
        module = "模块", isApp = true
)
@Controller
public class FileDataDetailedController {

    @Autowired
    private FileDataDetailedFacade  fileDataDetailedFacade;

    @Autowired
    private FileDataDetailedService  fileDataDetailedService;

    public interface UrlCommand{
        public static final String upload_fileDataDetailed_pageList = "/upload/filedatadetailed/pageList";
        public static final String upload_fileDataDetailed_info = "/upload/filedatadetailed/info";
    }

    /**
    * Title: 分页获取信息
    * Description:fileDataDetailedQueryRequestVo @{Link FileDataDetailedQueryRequestVo}
    * @param
    * @return
    * @author suven
    * date 2024-04-19 00:20:28
    *  --------------------------------------------------------
    *  modifyer    modifyTime                 comment
    *
    *  --------------------------------------------------------
    */
    @ApiDoc(
            value = "分页获取信息",
            request = FileDataDetailedRequestVo.class,
            response = FileDataDetailedResponseVo.class
    )
    @RequestMapping(value = UrlCommand.upload_fileDataDetailed_pageList, method = RequestMethod.GET)
    public void list( OutputResponse out, FileDataDetailedRequestVo fileDataDetailedRequestVo){
            FileDataDetailedRequestDto fileDataDetailedRequestDto = FileDataDetailedRequestDto.build().clone(fileDataDetailedRequestVo);
        Pager<FileDataDetailedRequestDto> pager = Pager.of();
        pager.toPageSize(fileDataDetailedRequestVo.getPageSize()).toPageNo(fileDataDetailedRequestVo.getPageNo());
        pager.toParamObject(fileDataDetailedRequestDto );
        PageResult<FileDataDetailedResponseDto> resultList = fileDataDetailedService.getFileDataDetailedByNextPage(FileDataDetailedQueryEnum.DESC_ID,pager);

        if(ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())){
            out.writeSuccess();
            return;
        }
        PageResult<FileDataDetailedRequestVo> list =  resultList.convertBuild(FileDataDetailedRequestVo.class);
        out.write(list);

        out.write(list);
    }





    /**
    * Title: 查看信息
    * Description:HttpRequestByIdVo @{Link HttpRequestByIdVo}
    * @param
    * @return
    * @author suven
    * date 2024-04-19 00:20:28
    *  --------------------------------------------------------
    *  modifyer    modifyTime                 comment
    *
    *  --------------------------------------------------------
    */
    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = FileDataDetailedResponseVo.class
    )
    @RequestMapping(value = UrlCommand.upload_fileDataDetailed_info ,method = RequestMethod.GET)
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileDataDetailedResponseDto fileDataDetailedResponseDto = fileDataDetailedService.getFileDataDetailedById(idRequestVo.getId());
            FileDataDetailedResponseVo vo =  FileDataDetailedResponseVo.build().clone(fileDataDetailedResponseDto);
        out.write(vo);
    }



}
