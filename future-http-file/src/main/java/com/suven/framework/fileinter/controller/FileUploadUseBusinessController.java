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
import com.suven.framework.http.data.vo.PageResult;
import com.suven.framework.core.ObjectTrue;

import com.suven.framework.fileinter.facade.FileUploadUseBusinessFacade;
import com.suven.framework.fileinter.service.FileUploadUseBusinessService;
import com.suven.framework.fileinter.dto.request.FileUploadUseBusinessRequestDto;
import com.suven.framework.fileinter.dto.response.FileUploadUseBusinessResponseDto;
import com.suven.framework.fileinter.dto.enums.FileUploadUseBusinessQueryEnum;
import com.suven.framework.fileinter.vo.request.FileUploadUseBusinessRequestVo;
import com.suven.framework.fileinter.vo.response.FileUploadUseBusinessResponseVo;

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
 * </pre>
 *  Copyright: (c) 2021 gc by https://www.suven.top
 **/

@ApiDoc(
        group = DocumentConst.Api.API_DOC_GROUP,
        groupDesc= DocumentConst.Api.API_DOC_DES,
        module = "模块", isApp = true
)
@Controller
public class FileUploadUseBusinessController {

    @Autowired
    private FileUploadUseBusinessFacade  fileUploadUseBusinessFacade;

    @Autowired
    private FileUploadUseBusinessService  fileUploadUseBusinessService;

    public interface UrlCommand{
        public static final String fileinter_fileUploadUseBusiness_pageList = "/fileinter/fileuploadusebusiness/pageList";
        public static final String fileinter_fileUploadUseBusiness_info = "/fileinter/fileuploadusebusiness/info";
    }

    /**
    * Title: 分页获取信息
    * Description:fileUploadUseBusinessQueryRequestVo @{Link FileUploadUseBusinessQueryRequestVo}
    * @param
    * @return
    * @author suven
    * date 2024-04-19 00:21:42
    *  --------------------------------------------------------
    *  modifyer    modifyTime                 comment
    *
    *  --------------------------------------------------------
    */
    @ApiDoc(
            value = "分页获取信息",
            request = FileUploadUseBusinessRequestVo.class,
            response = FileUploadUseBusinessResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadUseBusiness_pageList, method = RequestMethod.GET)
    public void list( OutputResponse out, FileUploadUseBusinessRequestVo fileUploadUseBusinessRequestVo){
            FileUploadUseBusinessRequestDto fileUploadUseBusinessRequestDto = FileUploadUseBusinessRequestDto.build().clone(fileUploadUseBusinessRequestVo);
        Pager<FileUploadUseBusinessRequestDto> pager = Pager.of(fileUploadUseBusinessRequestVo.getPageNo(),fileUploadUseBusinessRequestVo.getPageSize());
        pager.toParamObject(fileUploadUseBusinessRequestDto );
        PageResult<FileUploadUseBusinessResponseDto> resultList = fileUploadUseBusinessService.getFileUploadUseBusinessByNextPage(FileUploadUseBusinessQueryEnum.DESC_ID,pager);

        if(ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())){
            out.writeSuccess();
            return;
        }
        PageResult<FileUploadUseBusinessRequestVo> list =  resultList.convertBuild(FileUploadUseBusinessRequestVo.class);
        out.write(list);

        out.write(list);
    }





    /**
    * Title: 查看信息
    * Description:HttpRequestByIdVo @{Link HttpRequestByIdVo}
    * @param
    * @return
    * @author suven
    * date 2024-04-19 00:21:42
    *  --------------------------------------------------------
    *  modifyer    modifyTime                 comment
    *
    *  --------------------------------------------------------
    */
    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = FileUploadUseBusinessResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadUseBusiness_info ,method = RequestMethod.GET)
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileUploadUseBusinessResponseDto fileUploadUseBusinessResponseDto = fileUploadUseBusinessService.getFileUploadUseBusinessById(idRequestVo.getId());
            FileUploadUseBusinessResponseVo vo =  FileUploadUseBusinessResponseVo.build().clone(fileUploadUseBusinessResponseDto);
        out.write(vo);
    }



}
