package com.suven.framework.fileinter.controller;


import com.suven.framework.core.ObjectTrue;
import com.suven.framework.fileinter.dto.enums.FileUploadActionWaterQueryEnum;
import com.suven.framework.fileinter.dto.request.FileUploadActionWaterRequestDto;
import com.suven.framework.fileinter.dto.response.FileUploadActionWaterResponseDto;
import com.suven.framework.fileinter.facade.FileUploadActionWaterFacade;
import com.suven.framework.fileinter.service.FileUploadActionWaterService;
import com.suven.framework.fileinter.vo.request.FileUploadActionWaterRequestVo;
import com.suven.framework.fileinter.vo.response.FileUploadActionWaterResponseVo;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.PageResult;
import com.suven.framework.http.handler.OutputResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
 * </pre>
 *  Copyright: (c) 2021 gc by https://www.suven.top
 **/

@ApiDoc(
        group = DocumentConst.Api.API_DOC_GROUP,
        groupDesc= DocumentConst.Api.API_DOC_DES,
        module = "模块", isApp = true
)
@Controller
public class FileUploadActionWaterController {

    @Autowired
    private FileUploadActionWaterFacade  fileUploadActionWaterFacade;

    @Autowired
    private FileUploadActionWaterService  fileUploadActionWaterService;

    public interface UrlCommand{
        public static final String fileinter_fileUploadActionWater_pageList = "/fileinter/fileuploadactionwater/pageList";
        public static final String fileinter_fileUploadActionWater_info = "/fileinter/fileuploadactionwater/info";
    }

    /**
    * Title: 分页获取信息
    * Description:fileUploadActionWaterQueryRequestVo @{Link FileUploadActionWaterQueryRequestVo}
    * @param
    * @return
    * @author suven
    * date 2024-04-19 00:14:12
    *  --------------------------------------------------------
    *  modifyer    modifyTime                 comment
    *
    *  --------------------------------------------------------
    */
    @ApiDoc(
            value = "分页获取信息",
            request = FileUploadActionWaterRequestVo.class,
            response = FileUploadActionWaterResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadActionWater_pageList, method = RequestMethod.GET)
    public void list( OutputResponse out, FileUploadActionWaterRequestVo fileUploadActionWaterRequestVo){
            FileUploadActionWaterRequestDto fileUploadActionWaterRequestDto = FileUploadActionWaterRequestDto.build().clone(fileUploadActionWaterRequestVo);
        Pager pager = Pager.of();
        pager.toPageSize(fileUploadActionWaterRequestVo.getPageSize()).toPageNo(fileUploadActionWaterRequestVo.getPageNo());
        pager.toParamObject(fileUploadActionWaterRequestDto );
        PageResult<FileUploadActionWaterResponseDto> resultList = fileUploadActionWaterService.getFileUploadActionWaterByNextPage(FileUploadActionWaterQueryEnum.DESC_ID,pager);

        if(ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())){
            out.writeSuccess();
            return;
        }
        PageResult<FileUploadActionWaterRequestVo> list =  resultList.convertBuild(FileUploadActionWaterRequestVo.class);
        out.write(list);

        out.write(list);
    }





    /**
    * Title: 查看信息
    * Description:HttpRequestByIdVo @{Link HttpRequestByIdVo}
    * @param
    * @return
    * @author suven
    * date 2024-04-19 00:14:12
    *  --------------------------------------------------------
    *  modifyer    modifyTime                 comment
    *
    *  --------------------------------------------------------
    */
    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = FileUploadActionWaterResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileUploadActionWater_info ,method = RequestMethod.GET)
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileUploadActionWaterResponseDto fileUploadActionWaterResponseDto = fileUploadActionWaterService.getFileUploadActionWaterById(idRequestVo.getId());
            FileUploadActionWaterResponseVo vo =  FileUploadActionWaterResponseVo.build().clone(fileUploadActionWaterResponseDto);
        out.write(vo);
    }



}
