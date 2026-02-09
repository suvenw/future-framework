package com.suven.framework.upload.controller;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.upload.dto.enums.FileUploadActionWaterQueryEnum;
import com.suven.framework.upload.dto.request.FileUploadActionWaterRequestDto;
import com.suven.framework.upload.dto.response.FileUploadActionWaterResponseDto;
import com.suven.framework.upload.service.FileUploadActionWaterService;
import com.suven.framework.upload.vo.request.FileUploadActionWaterAddRequestVo;
import com.suven.framework.upload.vo.request.FileUploadActionWaterQueryRequestVo;
import com.suven.framework.upload.vo.response.FileUploadActionWaterResponseVo;
import com.suven.framework.upload.vo.response.FileUploadActionWaterShowResponseVo;
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
 * 文件上传行为流水 Web 控制器
 *
 * RequestMapping("/upload/fileUploadActionWater")
 */
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc = DocumentConst.Sys.SYS_DOC_DES,
        module = "文件上传行为流水模块"
)
@Controller
@Slf4j
@Validated
public class FileUploadActionWaterWebController {

    @Autowired
    private FileUploadActionWaterService fileUploadActionWaterService;

    public interface UrlCommand {
        String UPLOAD_FILE_UPLOAD_ACTION_WATER_INDEX = "/upload/fileuploadactionwater/index";
        String UPLOAD_FILE_UPLOAD_ACTION_WATER_PAGE_LIST = "/upload/fileuploadactionwater/list";
        String UPLOAD_FILE_UPLOAD_ACTION_WATER_QUERY_LIST = "/upload/fileuploadactionwater/querylist";
        String UPLOAD_FILE_UPLOAD_ACTION_WATER_INFO = "/upload/fileuploadactionwater/detail";
        String UPLOAD_FILE_UPLOAD_ACTION_WATER_CREATE = "/upload/fileuploadactionwater/add";
        String UPLOAD_FILE_UPLOAD_ACTION_WATER_UPDATE = "/upload/fileuploadactionwater/modify";
        String UPLOAD_FILE_UPLOAD_ACTION_WATER_DELETE = "/upload/fileuploadactionwater/delete";
        String UPLOAD_FILE_UPLOAD_ACTION_WATER_EDIT = "/upload/fileuploadactionwater/edit";
        String UPLOAD_FILE_UPLOAD_ACTION_WATER_NEW_INFO = "/upload/fileuploadactionwater/newInfo";
        String UPLOAD_FILE_UPLOAD_ACTION_WATER_EXPORT = "/upload/fileuploadactionwater/export";
        String UPLOAD_FILE_UPLOAD_ACTION_WATER_IMPORT = "/upload/fileuploadactionwater/import";
    }

    /**
     * 跳转到主界面
     */
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_ACTION_WATER_INDEX)
    public String index() {
        log.info("跳转文件上传行为流水主界面");
        return "upload/fileUploadActionWater_index";
    }

    /**
     * 分页获取文件上传行为流水
     */
    @ApiDoc(
            value = "分页获取文件上传行为流水",
            description = "根据查询条件分页获取文件上传行为流水列表",
            request = FileUploadActionWaterQueryRequestVo.class,
            response = FileUploadActionWaterShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_ACTION_WATER_PAGE_LIST)
    public PageResult<FileUploadActionWaterShowResponseVo> pageList(
            @Valid FileUploadActionWaterQueryRequestVo queryVo) {

        log.info("分页查询文件上传行为流水, 参数: {}", queryVo);

        FileUploadActionWaterRequestDto dto = FileUploadActionWaterRequestDto.build()
                .clone(queryVo);

        Pager<FileUploadActionWaterRequestDto> pager = Pager.of();
        pager.toPageSize(queryVo.getPageSize())
                .toPageNo(queryVo.getPageNo())
                .toParamObject(dto);

        FileUploadActionWaterQueryEnum queryEnum = FileUploadActionWaterQueryEnum.DESC_ID;
        PageResult<FileUploadActionWaterResponseDto> resultList =
                fileUploadActionWaterService.getFileUploadActionWaterByNextPage(queryEnum, pager);

        if (ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())) {
            log.info("分页查询文件上传行为流水完成, 无数据");
            return new PageResult<>();
        }

        PageResult<FileUploadActionWaterShowResponseVo> result =
                resultList.convertBuild(FileUploadActionWaterShowResponseVo.class);
        log.info("分页查询文件上传行为流水完成, 总数: {}", result.getTotal());
        return result;
    }

    /**
     * 根据条件查询文件上传行为流水
     */
    @ApiDoc(
            value = "根据条件查询文件上传行为流水",
            description = "根据查询条件获取文件上传行为流水列表",
            request = FileUploadActionWaterQueryRequestVo.class,
            response = FileUploadActionWaterShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_ACTION_WATER_QUERY_LIST)
    public List<FileUploadActionWaterShowResponseVo> queryList(
            @Valid FileUploadActionWaterQueryRequestVo queryVo) {

        log.info("根据条件查询文件上传行为流水, 参数: {}", queryVo);

        FileUploadActionWaterRequestDto dto = FileUploadActionWaterRequestDto.build()
                .clone(queryVo);

        FileUploadActionWaterQueryEnum queryEnum = FileUploadActionWaterQueryEnum.DESC_ID;
        List<FileUploadActionWaterResponseDto> resultList =
                fileUploadActionWaterService.getFileUploadActionWaterListByQuery(queryEnum, dto);

        if (resultList == null || resultList.isEmpty()) {
            log.info("根据条件查询文件上传行为流水完成, 无数据");
            return new ArrayList<>();
        }

        List<FileUploadActionWaterShowResponseVo> listVo =
                IterableConvert.convertList(resultList, FileUploadActionWaterShowResponseVo.class);
        log.info("根据条件查询文件上传行为流水完成, 数量: {}", listVo.size());
        return listVo;
    }

    /**
     * 新增文件上传行为流水
     */
    @ApiDoc(
            value = "新增文件上传行为流水",
            description = "新增文件上传行为流水记录",
            request = FileUploadActionWaterAddRequestVo.class,
            response = Long.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_ACTION_WATER_CREATE)
    public Long create(@Valid FileUploadActionWaterAddRequestVo addVo) {

        log.info("新增文件上传行为流水, 参数: {}", addVo);

        FileUploadActionWaterRequestDto dto = FileUploadActionWaterRequestDto.build()
                .clone(addVo);

        FileUploadActionWaterResponseDto responseDto =
                fileUploadActionWaterService.saveFileUploadActionWater(dto);

        if (responseDto == null) {
            log.warn("新增文件上传行为流水失败");
            throw new RuntimeException("新增失败");
        }

        log.info("新增文件上传行为流水成功, ID: {}", responseDto.getId());
        return responseDto.getId();
    }

    /**
     * 修改文件上传行为流水
     */
    @ApiDoc(
            value = "修改文件上传行为流水",
            description = "根据ID修改文件上传行为流水记录",
            request = FileUploadActionWaterAddRequestVo.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_ACTION_WATER_UPDATE)
    public boolean update(@Valid FileUploadActionWaterAddRequestVo addVo) {

        log.info("修改文件上传行为流水, 参数: {}", addVo);

        if (addVo.getId() == null || addVo.getId() <= 0) {
            log.warn("修改文件上传行为流水参数错误, ID: {}", addVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileUploadActionWaterRequestDto dto = FileUploadActionWaterRequestDto.build()
                .clone(addVo);

        boolean result = fileUploadActionWaterService.updateFileUploadActionWater(dto);
        log.info("修改文件上传行为流水完成, ID: {}, 结果: {}", addVo.getId(), result);
        return result;
    }

    /**
     * 查看文件上传行为流水详情
     */
    @ApiDoc(
            value = "查看文件上传行为流水详情",
            description = "根据ID获取文件上传行为流水详情",
            request = HttpRequestByIdVo.class,
            response = FileUploadActionWaterShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_ACTION_WATER_INFO)
    public FileUploadActionWaterShowResponseVo info( @Validated HttpRequestByIdVo idVo) {

        log.info("查询文件上传行为流水详情, ID: {}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            log.warn("查询文件上传行为流水详情参数错误, ID: {}", idVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileUploadActionWaterResponseDto dto =
                fileUploadActionWaterService.getFileUploadActionWaterById(idVo.getId());

        if (dto == null) {
            log.warn("文件上传行为流水不存在, ID: {}", idVo.getId());
            throw new RuntimeException("数据不存在");
        }

        FileUploadActionWaterShowResponseVo vo = FileUploadActionWaterShowResponseVo.build()
                .clone(dto);
        log.info("查询文件上传行为流水详情成功, ID: {}", idVo.getId());
        return vo;
    }

    /**
     * 跳转编辑页面
     */
    @ApiDoc(
            value = "跳转编辑页面",
            description = "获取文件上传行为流水编辑页面数据",
            request = HttpRequestByIdVo.class,
            response = FileUploadActionWaterShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_ACTION_WATER_EDIT)
    public FileUploadActionWaterShowResponseVo edit( @Validated HttpRequestByIdVo idVo) {

        log.info("跳转文件上传行为流水编辑页面, ID: {}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            log.warn("跳转编辑页面参数错误, ID: {}", idVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileUploadActionWaterResponseDto dto =
                fileUploadActionWaterService.getFileUploadActionWaterById(idVo.getId());

        if (dto == null) {
            log.warn("文件上传行为流水不存在, ID: {}", idVo.getId());
            throw new RuntimeException("数据不存在");
        }

        FileUploadActionWaterShowResponseVo vo = FileUploadActionWaterShowResponseVo.build()
                .clone(dto);
        log.info("跳转文件上传行为流水编辑页面成功, ID: {}", idVo.getId());
        return vo;
    }

    /**
     * 跳转新增编辑界面
     */
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_ACTION_WATER_NEW_INFO)
    public String newInfo(ModelMap modelMap) {
        log.info("跳转文件上传行为流水新增编辑页面");
        return "upload/fileUploadActionWater_edit";
    }

    /**
     * 删除文件上传行为流水
     */
    @ApiDoc(
            value = "删除文件上传行为流水",
            description = "根据ID列表删除文件上传行为流水记录",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_ACTION_WATER_DELETE)
    public int delete( @Validated HttpRequestByIdListVo idVo) {

        log.info("删除文件上传行为流水, ID列表: {}", idVo.getIdList());

        if (idVo.getIdList() == null || idVo.getIdList().isEmpty()) {
            log.warn("删除文件上传行为流水参数错误, ID列表为空");
            throw new RuntimeException("ID列表参数错误");
        }

        int result = fileUploadActionWaterService.delFileUploadActionWaterByIds(idVo.getIdList());
        log.info("删除文件上传行为流水完成, 删除数量: {}", result);
        return result;
    }

    /**
     * 导出文件上传行为流水
     */
    @ApiDoc(
            value = "导出文件上传行为流水",
            description = "导出文件上传行为流水到Excel文件",
            request = FileUploadActionWaterQueryRequestVo.class,
            response = boolean.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_ACTION_WATER_EXPORT)
    public void export(HttpServletResponse response,
                       @Valid FileUploadActionWaterQueryRequestVo queryVo) {

        log.info("导出文件上传行为流水, 参数: {}", queryVo);

        FileUploadActionWaterRequestDto dto = FileUploadActionWaterRequestDto.build()
                .clone(queryVo);

        Pager<FileUploadActionWaterRequestDto> pager = Pager.of();
        pager.toPageSize(queryVo.getPageSize())
                .toPageNo(queryVo.getPageNo())
                .toParamObject(dto);

        FileUploadActionWaterQueryEnum queryEnum = FileUploadActionWaterQueryEnum.DESC_ID;
        PageResult<FileUploadActionWaterResponseDto> resultList =
                fileUploadActionWaterService.getFileUploadActionWaterByNextPage(queryEnum, pager);
        List<FileUploadActionWaterResponseDto> data = resultList.getList();

        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, FileUploadActionWaterResponseVo.class,
                    data, "导出文件上传行为流水");
            log.info("导出文件上传行为流水完成, 数据量: {}", data.size());
        } catch (Exception e) {
            log.error("导出文件上传行为流水失败", e);
        }
    }

    /**
     * 通过 Excel 导入文件上传行为流水
     */
    @ApiDoc(
            value = "导入文件上传行为流水",
            description = "通过Excel文件导入文件上传行为流水",
            request = MultipartFile.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_ACTION_WATER_IMPORT)
    public boolean importExcel(@RequestParam("file") MultipartFile file) {

        log.info("导入文件上传行为流水, 文件名: {}", file.getOriginalFilename());

        try {
            InputStream initialStream = file.getInputStream();
            boolean result = fileUploadActionWaterService.saveData(initialStream);
            log.info("导入文件上传行为流水完成, 结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("导入文件上传行为流水失败", e);
            throw new RuntimeException("导入失败");
        }
    }
}
