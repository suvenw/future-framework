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

import com.suven.framework.fileinter.facade.FileDataDetailedFacade;
import com.suven.framework.fileinter.service.FileDataDetailedService;
import com.suven.framework.fileinter.dto.request.FileDataDetailedRequestDto;
import com.suven.framework.fileinter.dto.response.FileDataDetailedResponseDto;
import com.suven.framework.fileinter.dto.enums.FileDataDetailedQueryEnum;
import com.suven.framework.fileinter.vo.request.FileDataDetailedRequestVo;
import com.suven.framework.fileinter.vo.response.FileDataDetailedResponseVo;

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
        public static final String fileinter_fileDataDetailed_pageList = "/fileinter/filedatadetailed/pageList";
        public static final String fileinter_fileDataDetailed_info = "/fileinter/filedatadetailed/info";
    }

    /**
    * @Title: 分页获取信息
    * Description:fileDataDetailedQueryRequestVo @{Link FileDataDetailedQueryRequestVo}
    * @param
    * @return
    * @author suven
    * @date 2024-04-19 00:20:28
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
    @RequestMapping(value = UrlCommand.fileinter_fileDataDetailed_pageList, method = RequestMethod.GET)
    public void list( OutputResponse out, FileDataDetailedRequestVo fileDataDetailedRequestVo){
            FileDataDetailedRequestDto fileDataDetailedRequestDto = FileDataDetailedRequestDto.build().clone(fileDataDetailedRequestVo);
        Pager pager = Pager.build().toPageSize(fileDataDetailedRequestVo.getPageSize()).toPageNo(fileDataDetailedRequestVo.getPageNo());
        pager.toParamObject(fileDataDetailedRequestDto );
        ResultPageVo<FileDataDetailedResponseDto> resultList = fileDataDetailedService.getFileDataDetailedByNextPage(FileDataDetailedQueryEnum.DESC_ID,pager);

        if(ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())){
            out.writeSuccess();
            return;
        }
        ResultPageVo<FileDataDetailedRequestVo> list =  resultList.convertBuild(FileDataDetailedRequestVo.class);
        out.write(list);

        out.write(list);
    }





    /**
    * @Title: 查看信息
    * Description:HttpRequestByIdVo @{Link HttpRequestByIdVo}
    * @param
    * @return
    * @author suven
    * @date 2024-04-19 00:20:28
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
    @RequestMapping(value = UrlCommand.fileinter_fileDataDetailed_info ,method = RequestMethod.GET)
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileDataDetailedResponseDto fileDataDetailedResponseDto = fileDataDetailedService.getFileDataDetailedById(idRequestVo.getId());
            FileDataDetailedResponseVo vo =  FileDataDetailedResponseVo.build().clone(fileDataDetailedResponseDto);
        out.write(vo);
    }



}
