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

import com.suven.framework.fileinter.facade.FileAppStorageConfigFacade;
import com.suven.framework.fileinter.service.FileAppStorageConfigService;
import com.suven.framework.fileinter.dto.request.FileAppStorageConfigRequestDto;
import com.suven.framework.fileinter.dto.response.FileAppStorageConfigResponseDto;
import com.suven.framework.fileinter.dto.enums.FileAppStorageConfigQueryEnum;
import com.suven.framework.fileinter.vo.request.FileAppStorageConfigRequestVo;
import com.suven.framework.fileinter.vo.response.FileAppStorageConfigResponseVo;

/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *  date 创建时间: 2024-04-19 00:21:54
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
public class FileAppStorageConfigController {

    @Autowired
    private FileAppStorageConfigFacade  fileAppStorageConfigFacade;

    @Autowired
    private FileAppStorageConfigService  fileAppStorageConfigService;

    public interface UrlCommand{
        public static final String fileinter_fileAppStorageConfig_pageList = "/fileinter/fileappstorageconfig/pageList";
        public static final String fileinter_fileAppStorageConfig_info = "/fileinter/fileappstorageconfig/info";
    }

    /**
    * Title: 分页获取信息
    * Description:fileAppStorageConfigQueryRequestVo @{Link FileAppStorageConfigQueryRequestVo}
    * @param
    * @return
    * @author suven
    * date 2024-04-19 00:21:54
    *  --------------------------------------------------------
    *  modifyer    modifyTime                 comment
    *
    *  --------------------------------------------------------
    */
    @ApiDoc(
            value = "分页获取信息",
            request = FileAppStorageConfigRequestVo.class,
            response = FileAppStorageConfigResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileAppStorageConfig_pageList, method = RequestMethod.GET)
    public void list( OutputResponse out, FileAppStorageConfigRequestVo fileAppStorageConfigRequestVo){
            FileAppStorageConfigRequestDto fileAppStorageConfigRequestDto = FileAppStorageConfigRequestDto.build().clone(fileAppStorageConfigRequestVo);
        Pager pager = Pager.build();toPageSize(fileAppStorageConfigRequestVo.getPageSize()).toPageNo(fileAppStorageConfigRequestVo.getPageNo());
        pager.toParamObject(fileAppStorageConfigRequestDto );
        ResponseResultPageVo<FileAppStorageConfigResponseDto> resultList = fileAppStorageConfigService.getFileAppStorageConfigByNextPage(FileAppStorageConfigQueryEnum.DESC_ID,pager);

        if(ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())){
            out.writeSuccess();
            return;
        }
        ResponseResultPageVo<FileAppStorageConfigRequestVo> list =  resultList.convertBuild(FileAppStorageConfigRequestVo.class);
        out.write(list);

        out.write(list);
    }





    /**
    * Title: 查看信息
    * Description:HttpRequestByIdVo @{Link HttpRequestByIdVo}
    * @param
    * @return
    * @author suven
    * date 2024-04-19 00:21:54
    *  --------------------------------------------------------
    *  modifyer    modifyTime                 comment
    *
    *  --------------------------------------------------------
    */
    @ApiDoc(
            value = "查看信息",
            request = HttpRequestByIdVo.class,
            response = FileAppStorageConfigResponseVo.class
    )
    @RequestMapping(value = UrlCommand.fileinter_fileAppStorageConfig_info ,method = RequestMethod.GET)
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo){

            FileAppStorageConfigResponseDto fileAppStorageConfigResponseDto = fileAppStorageConfigService.getFileAppStorageConfigById(idRequestVo.getId());
            FileAppStorageConfigResponseVo vo =  FileAppStorageConfigResponseVo.build().clone(fileAppStorageConfigResponseDto);
        out.write(vo);
    }



}
