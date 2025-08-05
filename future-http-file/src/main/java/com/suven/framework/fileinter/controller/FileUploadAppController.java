package com.suven.framework.fileinter.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;

import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.ResponseResultPageVo;
import com.suven.framework.core.ObjectTrue;

import com.suven.framework.fileinter.facade.FileUploadAppFacade;
import com.suven.framework.fileinter.service.FileUploadAppService;
import com.suven.framework.fileinter.dto.request.FileUploadAppRequestDto;
import com.suven.framework.fileinter.dto.response.FileUploadAppResponseDto;
import com.suven.framework.fileinter.dto.enums.FileUploadAppQueryEnum;
import com.suven.framework.fileinter.vo.request.FileUploadAppRequestVo;
import com.suven.framework.fileinter.vo.response.FileUploadAppResponseVo;

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
 * </pre>
 *  Copyright: (c) 2021 gc by https://www.suven.top
 **/

@ApiDoc(
        group = DocumentConst.Api.API_DOC_GROUP,
        groupDesc= DocumentConst.Api.API_DOC_DES,
        module = "模块", isApp = true
)
@Controller
public class FileUploadAppController {

    @Autowired
    private FileUploadAppFacade  fileUploadAppFacade;

    @Autowired
    private FileUploadAppService  fileUploadAppService;

    public interface UrlCommand{
        public static final String fileinter_fileUploadApp_pageList = "/fileinter/fileuploadapp/pageList";
        public static final String fileinter_fileUploadApp_info = "/fileinter/fileuploadapp/info";
    }

    /**
    * @Title: 分页获取信息
    * Description:fileUploadAppQueryRequestVo @{Link FileUploadAppQueryRequestVo}
    * @param
    * @return
    * @author suven
    * @date 2024-04-19 00:21:49
    *  --------------------------------------------------------
    *  modifyer    modifyTime                 comment
    *
    *  --------------------------------------------------------
    */
    @ApiDoc(
            value = "分页获取信息",
            request = FileUploadAppRequestVo.class,
            response = FileUploadAppResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadApp_pageList, method = RequestMethod.GET)
    public void list( OutputResponse out, FileUploadAppRequestVo fileUploadAppRequestVo){
            FileUploadAppRequestDto fileUploadAppRequestDto = FileUploadAppRequestDto.build().clone(fileUploadAppRequestVo);
        Pager pager = Pager.build().toPageSize(fileUploadAppRequestVo.getPageSize()).toPageNo(fileUploadAppRequestVo.getPageNo());
        pager.toParamObject(fileUploadAppRequestDto );
        ResponseResultPageVo<FileUploadAppResponseDto> resultList = fileUploadAppService.getFileUploadAppByNextPage(FileUploadAppQueryEnum.DESC_ID,pager);

        if(ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())){
            out.writeSuccess();
            return;
        }
        ResponseResultPageVo<FileUploadAppRequestVo> list =  resultList.convertBuild(FileUploadAppRequestVo.class);
        out.write(list);

        out.write(list);
    }





    /**
    * @Title: 查看信息
    * Description:HttpRequestByIdVo @{Link HttpRequestByIdVo}
    * @param
    * @return
    * @author suven
    * @date 2024-04-19 00:21:49
    *  --------------------------------------------------------
    *  modifyer    modifyTime                 comment
    *
    *  --------------------------------------------------------
    */
    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = FileUploadAppResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadApp_info ,method = RequestMethod.GET)
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileUploadAppResponseDto fileUploadAppResponseDto = fileUploadAppService.getFileUploadAppById(idRequestVo.getId());
            FileUploadAppResponseVo vo =  FileUploadAppResponseVo.build().clone(fileUploadAppResponseDto);
        out.write(vo);
    }



}
