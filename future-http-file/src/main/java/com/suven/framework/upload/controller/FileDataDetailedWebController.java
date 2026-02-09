package com.suven.framework.upload.controller;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.upload.dto.enums.FileDataDetailedQueryEnum;
import com.suven.framework.upload.dto.request.FileDataDetailedRequestDto;
import com.suven.framework.upload.dto.response.FileDataDetailedResponseDto;
import com.suven.framework.upload.service.FileDataDetailedService;
import com.suven.framework.upload.vo.request.FileDataDetailedAddRequestVo;
import com.suven.framework.upload.vo.request.FileDataDetailedQueryRequestVo;
import com.suven.framework.upload.vo.response.FileDataDetailedResponseVo;
import com.suven.framework.upload.vo.response.FileDataDetailedShowResponseVo;
import com.suven.framework.util.excel.ExcelUtils;
import jakarta.servlet.http.HttpServletResponse;
 
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件数据明细 Web 控制器
 *
 * RequestMapping("/upload/fileDataDetailed")
 */
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc = DocumentConst.Sys.SYS_DOC_DES,
        module = "文件数据明细模块"
)
@Controller
@Slf4j
@Validated
public class FileDataDetailedWebController {

    @Autowired
    private FileDataDetailedService fileDataDetailedService;

    public interface UrlCommand {
        String UPLOAD_FILE_DATA_DETAILED_INDEX = "/upload/filedatadetailed/index";
        String UPLOAD_FILE_DATA_DETAILED_PAGE_LIST = "/upload/filedatadetailed/list";
        String UPLOAD_FILE_DATA_DETAILED_QUERY_LIST = "/upload/filedatadetailed/querylist";
        String UPLOAD_FILE_DATA_DETAILED_INFO = "/upload/filedatadetailed/detail";
        String UPLOAD_FILE_DATA_DETAILED_CREATE = "/upload/filedatadetailed/add";
        String UPLOAD_FILE_DATA_DETAILED_UPDATE = "/upload/filedatadetailed/modify";
        String UPLOAD_FILE_DATA_DETAILED_DELETE = "/upload/filedatadetailed/delete";
        String UPLOAD_FILE_DATA_DETAILED_EDIT = "/upload/filedatadetailed/edit";
        String UPLOAD_FILE_DATA_DETAILED_NEW_INFO = "/upload/filedatadetailed/newInfo";
        String UPLOAD_FILE_DATA_DETAILED_EXPORT = "/upload/filedatadetailed/export";
        String UPLOAD_FILE_DATA_DETAILED_IMPORT = "/upload/filedatadetailed/import";
    }

    /**
     * 跳转到主界面
     */
    @GetMapping(value = UrlCommand.UPLOAD_FILE_DATA_DETAILED_INDEX)
    public String index() {
        log.info("跳转文件数据明细主界面");
        return "upload/fileDataDetailed_index";
    }

    /**
     * 分页获取文件数据明细
     */
    @ApiDoc(
            value = "分页获取文件数据明细",
            description = "根据查询条件分页获取文件数据明细列表",
            request = FileDataDetailedQueryRequestVo.class,
            response = FileDataDetailedShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_DATA_DETAILED_PAGE_LIST)
    public PageResult<FileDataDetailedShowResponseVo> pageList(
            @Valid FileDataDetailedQueryRequestVo queryVo) {

        log.info("分页查询文件数据明细, 参数: {}", queryVo);

        FileDataDetailedRequestDto dto = FileDataDetailedRequestDto.build()
                .clone(queryVo);

        Pager<FileDataDetailedRequestDto> pager = new Pager<>(
                queryVo.getPageNo(),
                queryVo.getPageSize()
        );
        pager.toParamObject(dto);

        FileDataDetailedQueryEnum queryEnum = FileDataDetailedQueryEnum.DESC_ID;
        PageResult<FileDataDetailedResponseDto> resultList =
                fileDataDetailedService.getFileDataDetailedByNextPage(queryEnum, pager);

        if (ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())) {
            log.info("分页查询文件数据明细完成, 无数据");
            return new PageResult<>();
        }

        PageResult<FileDataDetailedShowResponseVo> result =
                resultList.convertBuild(FileDataDetailedShowResponseVo.class);
        log.info("分页查询文件数据明细完成, 总数: {}", result.getTotal());
        return result;
    }

    /**
     * 根据条件查询文件数据明细
     */
    @ApiDoc(
            value = "根据条件查询文件数据明细",
            description = "根据查询条件获取文件数据明细列表",
            request = FileDataDetailedQueryRequestVo.class,
            response = FileDataDetailedShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_DATA_DETAILED_QUERY_LIST)
    public List<FileDataDetailedShowResponseVo> queryList(
            @Valid FileDataDetailedQueryRequestVo queryVo) {

        log.info("根据条件查询文件数据明细, 参数: {}", queryVo);

        FileDataDetailedRequestDto dto = FileDataDetailedRequestDto.build()
                .clone(queryVo);

        FileDataDetailedQueryEnum queryEnum = FileDataDetailedQueryEnum.DESC_ID;
        List<FileDataDetailedResponseDto> resultList =
                fileDataDetailedService.getFileDataDetailedListByQuery(queryEnum, dto);

        if (resultList == null || resultList.isEmpty()) {
            log.info("根据条件查询文件数据明细完成, 无数据");
            return new ArrayList<>();
        }

        List<FileDataDetailedShowResponseVo> listVo =
                IterableConvert.convertList(resultList, FileDataDetailedShowResponseVo.class);
        log.info("根据条件查询文件数据明细完成, 数量: {}", listVo.size());
        return listVo;
    }

    /**
     * 新增文件数据明细
     */
    @ApiDoc(
            value = "新增文件数据明细",
            description = "新增文件数据明细记录",
            request = FileDataDetailedAddRequestVo.class,
            response = Long.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_DATA_DETAILED_CREATE)
    public Long create(@Valid FileDataDetailedAddRequestVo addVo) {

        log.info("新增文件数据明细, 参数: {}", addVo);

        FileDataDetailedRequestDto dto = FileDataDetailedRequestDto.build()
                .clone(addVo);

        FileDataDetailedResponseDto responseDto =
                fileDataDetailedService.saveFileDataDetailed(dto);

        if (responseDto == null) {
            log.warn("新增文件数据明细失败");
            throw new RuntimeException("新增失败");
        }

        log.info("新增文件数据明细成功, ID: {}", responseDto.getId());
        return responseDto.getId();
    }

    /**
     * 修改文件数据明细
     */
    @ApiDoc(
            value = "修改文件数据明细",
            description = "根据ID修改文件数据明细记录",
            request = FileDataDetailedAddRequestVo.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_DATA_DETAILED_UPDATE)
    public boolean update(@Valid FileDataDetailedAddRequestVo addVo) {

        log.info("修改文件数据明细, 参数: {}", addVo);

        if (addVo.getId() == null || addVo.getId() <= 0) {
            log.warn("修改文件数据明细参数错误, ID: {}", addVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileDataDetailedRequestDto dto = FileDataDetailedRequestDto.build()
                .clone(addVo);

        boolean result = fileDataDetailedService.updateFileDataDetailed(dto);
        log.info("修改文件数据明细完成, ID: {}, 结果: {}", addVo.getId(), result);
        return result;
    }

    /**
     * 查看文件数据明细详情
     */
    @ApiDoc(
            value = "查看文件数据明细详情",
            description = "根据ID获取文件数据明细详情",
            request = HttpRequestByIdVo.class,
            response = FileDataDetailedShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_DATA_DETAILED_INFO)
    public FileDataDetailedShowResponseVo info( @Validated HttpRequestByIdVo idVo) {

        log.info("查询文件数据明细详情, ID: {}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            log.warn("查询文件数据明细详情参数错误, ID: {}", idVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileDataDetailedResponseDto dto =
                fileDataDetailedService.getFileDataDetailedById(idVo.getId());

        if (dto == null) {
            log.warn("文件数据明细不存在, ID: {}", idVo.getId());
            throw new RuntimeException("数据不存在");
        }

        FileDataDetailedShowResponseVo vo = FileDataDetailedShowResponseVo.build()
                .clone(dto);
        log.info("查询文件数据明细详情成功, ID: {}", idVo.getId());
        return vo;
    }

    /**
     * 跳转编辑页面
     */
    @ApiDoc(
            value = "跳转编辑页面",
            description = "获取文件数据明细编辑页面数据",
            request = HttpRequestByIdVo.class,
            response = FileDataDetailedShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_DATA_DETAILED_EDIT)
    public FileDataDetailedShowResponseVo edit( @Validated HttpRequestByIdVo idVo) {

        log.info("跳转文件数据明细编辑页面, ID: {}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            log.warn("跳转编辑页面参数错误, ID: {}", idVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileDataDetailedResponseDto dto =
                fileDataDetailedService.getFileDataDetailedById(idVo.getId());

        if (dto == null) {
            log.warn("文件数据明细不存在, ID: {}", idVo.getId());
            throw new RuntimeException("数据不存在");
        }

        FileDataDetailedShowResponseVo vo = FileDataDetailedShowResponseVo.build()
                .clone(dto);
        log.info("跳转文件数据明细编辑页面成功, ID: {}", idVo.getId());
        return vo;
    }

    /**
     * 跳转新增编辑界面
     */
    @GetMapping(value = UrlCommand.UPLOAD_FILE_DATA_DETAILED_NEW_INFO)
    public String newInfo(ModelMap modelMap) {
        log.info("跳转文件数据明细新增编辑页面");
        return "upload/fileDataDetailed_edit";
    }

    /**
     * 删除文件数据明细
     */
    @ApiDoc(
            value = "删除文件数据明细",
            description = "根据ID列表删除文件数据明细记录",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_DATA_DETAILED_DELETE)
    public int delete( @Validated HttpRequestByIdListVo idVo) {

        log.info("删除文件数据明细, ID列表: {}", idVo.getIdList());

        if (idVo.getIdList() == null || idVo.getIdList().isEmpty()) {
            log.warn("删除文件数据明细参数错误, ID列表为空");
            throw new RuntimeException("ID列表参数错误");
        }

        int result = fileDataDetailedService.delFileDataDetailedByIds(idVo.getIdList());
        log.info("删除文件数据明细完成, 删除数量: {}", result);
        return result;
    }

    /**
     * 导出文件数据明细
     */
    @ApiDoc(
            value = "导出文件数据明细",
            description = "导出文件数据明细到Excel文件",
            request = FileDataDetailedQueryRequestVo.class,
            response = boolean.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_DATA_DETAILED_EXPORT)
    public void export(HttpServletResponse response,
                       @Valid FileDataDetailedQueryRequestVo queryVo) {

        log.info("导出文件数据明细, 参数: {}", queryVo);

        FileDataDetailedRequestDto dto = FileDataDetailedRequestDto.build()
                .clone(queryVo);

        Pager<FileDataDetailedRequestDto> pager = Pager.of();
        pager.toPageSize(queryVo.getPageSize())
                .toPageNo(queryVo.getPageNo())
                .toParamObject(dto);

        FileDataDetailedQueryEnum queryEnum = FileDataDetailedQueryEnum.DESC_ID;
        PageResult<FileDataDetailedResponseDto> resultList =
                fileDataDetailedService.getFileDataDetailedByNextPage(queryEnum, pager);
        List<FileDataDetailedResponseDto> data = resultList.getList();

        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, FileDataDetailedResponseVo.class,
                    data, "导出文件数据明细");
            log.info("导出文件数据明细完成, 数据量: {}", data.size());
        } catch (Exception e) {
            log.error("导出文件数据明细失败", e);
        }
    }

    /**
     * 通过 Excel 导入文件数据明细
     */
    @ApiDoc(
            value = "导入文件数据明细",
            description = "通过Excel文件导入文件数据明细",
            request = MultipartFile.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_DATA_DETAILED_IMPORT)
    public boolean importExcel(@RequestParam("file") MultipartFile file) {

        log.info("导入文件数据明细, 文件名: {}", file.getOriginalFilename());

        try {
            InputStream initialStream = file.getInputStream();
            boolean result = fileDataDetailedService.saveData(initialStream);
            log.info("导入文件数据明细完成, 结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("导入文件数据明细失败", e);
            throw new RuntimeException("导入失败");
        }
    }
}
