package com.suven.framework.upload.controller;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.upload.dto.enums.FileUploadUseBusinessQueryEnum;
import com.suven.framework.upload.dto.request.FileUploadUseBusinessRequestDto;
import com.suven.framework.upload.dto.response.FileUploadUseBusinessResponseDto;
import com.suven.framework.upload.service.FileUploadUseBusinessService;
import com.suven.framework.upload.vo.request.FileUploadUseBusinessAddRequestVo;
import com.suven.framework.upload.vo.request.FileUploadUseBusinessQueryRequestVo;
import com.suven.framework.upload.vo.response.FileUploadUseBusinessResponseVo;
import com.suven.framework.upload.vo.response.FileUploadUseBusinessShowResponseVo;
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

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传业务配置接口控制器
 *
 * 接口规范：
 * 1. 所有接口必须使用 @ApiDoc 注解
 * 2. 请求方式必须明确指定 (GET/POST/PUT/DELETE)
 * 3. 接口URL必须在 UrlCommand 中统一定义
 * 4. 返回结果必须使用统一的响应格式
 * 5. 必须使用 @Validated 开启参数校验
 * 6. 必须使用 @Slf4j 记录日志
 */
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc = DocumentConst.Sys.SYS_DOC_DES,
        module = "文件上传业务配置模块"
)
@Controller
@Slf4j
@Validated
public class FileUploadUseBusinessWebController {

    @Autowired
    private FileUploadUseBusinessService fileUploadUseBusinessService;

    /**
     * URL 命令常量接口
     * 规范：全大写，下划线分隔，描述性名称
     */
    public interface UrlCommand {
        String UPLOAD_FILE_UPLOAD_USE_BUSINESS_INDEX = "/upload/fileuploadusebusiness/index";
        String UPLOAD_FILE_UPLOAD_USE_BUSINESS_PAGE_LIST = "/upload/fileuploadusebusiness/pageList";
        String UPLOAD_FILE_UPLOAD_USE_BUSINESS_LIST = "/upload/fileuploadusebusiness/list";
        String UPLOAD_FILE_UPLOAD_USE_BUSINESS_QUERY_LIST = "/upload/fileuploadusebusiness/queryList";
        String UPLOAD_FILE_UPLOAD_USE_BUSINESS_INFO = "/upload/fileuploadusebusiness/info";
        String UPLOAD_FILE_UPLOAD_USE_BUSINESS_CREATE = "/upload/fileuploadusebusiness/create";
        String UPLOAD_FILE_UPLOAD_USE_BUSINESS_UPDATE = "/upload/fileuploadusebusiness/update";
        String UPLOAD_FILE_UPLOAD_USE_BUSINESS_DELETE = "/upload/fileuploadusebusiness/delete";
        String UPLOAD_FILE_UPLOAD_USE_BUSINESS_EDIT = "/upload/fileuploadusebusiness/edit";
        String UPLOAD_FILE_UPLOAD_USE_BUSINESS_NEW_INFO = "/upload/fileuploadusebusiness/newInfo";
        String UPLOAD_FILE_UPLOAD_USE_BUSINESS_EXPORT = "/upload/fileuploadusebusiness/export";
        String UPLOAD_FILE_UPLOAD_USE_BUSINESS_IMPORT = "/upload/fileuploadusebusiness/import";
    }

    /**
     * 跳转到主界面
     */
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_USE_BUSINESS_INDEX)
    public String index() {
        log.info("跳转文件上传业务配置主界面");
        return "upload/fileUploadUseBusiness_index";
    }

    /**
     * 分页获取文件上传业务配置信息
     */
    @ApiDoc(
            value = "分页获取文件上传业务配置信息",
            description = "根据查询条件分页获取文件上传业务配置列表",
            request = FileUploadUseBusinessQueryRequestVo.class,
            response = FileUploadUseBusinessShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_USE_BUSINESS_PAGE_LIST)
    public PageResult<FileUploadUseBusinessShowResponseVo> pageList(
            @Validated  FileUploadUseBusinessQueryRequestVo queryVo) {

        log.info("分页查询文件上传业务配置, 参数: {}", queryVo);

        FileUploadUseBusinessRequestDto dto = FileUploadUseBusinessRequestDto.build()
                .clone(queryVo);

        Pager<FileUploadUseBusinessRequestDto> pager = new Pager<>(
                queryVo.getPageNo(),
                queryVo.getPageSize()
        );
        pager.toParamObject(dto);

        FileUploadUseBusinessQueryEnum queryEnum = FileUploadUseBusinessQueryEnum.DESC_ID;
        PageResult<FileUploadUseBusinessResponseDto> resultList =
                fileUploadUseBusinessService.getFileUploadUseBusinessByNextPage(queryEnum, pager);

        if (ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())) {
            log.info("分页查询文件上传业务配置完成, 无数据");
            return new PageResult<>();
        }

        PageResult<FileUploadUseBusinessShowResponseVo> result =
                resultList.convertBuild(FileUploadUseBusinessShowResponseVo.class);
        log.info("分页查询文件上传业务配置完成, 总数: {}", result.getTotal());
        return result;
    }

    /**
     * 根据条件查询文件上传业务配置信息
     */
    @ApiDoc(
            value = "根据条件查询文件上传业务配置信息",
            description = "根据查询条件获取文件上传业务配置列表",
            request = FileUploadUseBusinessQueryRequestVo.class,
            response = FileUploadUseBusinessShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_USE_BUSINESS_QUERY_LIST)
    public List<FileUploadUseBusinessShowResponseVo> queryList(
            @Validated  FileUploadUseBusinessQueryRequestVo queryVo) {

        log.info("根据条件查询文件上传业务配置, 参数: {}", queryVo);

        FileUploadUseBusinessRequestDto dto = FileUploadUseBusinessRequestDto.build()
                .clone(queryVo);

        FileUploadUseBusinessQueryEnum queryEnum = FileUploadUseBusinessQueryEnum.DESC_ID;
        List<FileUploadUseBusinessResponseDto> resultList =
                fileUploadUseBusinessService.getFileUploadUseBusinessListByQuery(queryEnum, dto);

        if (resultList == null || resultList.isEmpty()) {
            log.info("根据条件查询文件上传业务配置完成, 无数据");
            return new ArrayList<>();
        }

        List<FileUploadUseBusinessShowResponseVo> listVo =
                IterableConvert.convertList(resultList, FileUploadUseBusinessShowResponseVo.class);
        log.info("根据条件查询文件上传业务配置完成, 数量: {}", listVo.size());
        return listVo;
    }

    /**
     * 新增文件上传业务配置信息
     */
    @ApiDoc(
            value = "新增文件上传业务配置信息",
            description = "新增文件上传业务配置记录",
            request = FileUploadUseBusinessAddRequestVo.class,
            response = Long.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_USE_BUSINESS_CREATE)
    public Long create( @ValidatedFileUploadUseBusinessAddRequestVo addVo) {

        log.info("新增文件上传业务配置, 参数: {}", addVo);

        FileUploadUseBusinessRequestDto dto = FileUploadUseBusinessRequestDto.build()
                .clone(addVo);

        FileUploadUseBusinessResponseDto responseDto =
                fileUploadUseBusinessService.saveFileUploadUseBusiness(dto);

        if (responseDto == null) {
            log.warn("新增文件上传业务配置失败");
            throw new RuntimeException("新增失败");
        }

        log.info("新增文件上传业务配置成功, ID: {}", responseDto.getId());
        return responseDto.getId();
    }

    /**
     * 修改文件上传业务配置信息
     */
    @ApiDoc(
            value = "修改文件上传业务配置信息",
            description = "根据ID修改文件上传业务配置记录",
            request = FileUploadUseBusinessAddRequestVo.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_USE_BUSINESS_UPDATE)
    public boolean update( @ValidatedFileUploadUseBusinessAddRequestVo addVo) {

        log.info("修改文件上传业务配置, 参数: {}", addVo);

        if (addVo.getId() == null || addVo.getId() <= 0) {
            log.warn("修改文件上传业务配置参数错误, ID: {}", addVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileUploadUseBusinessRequestDto dto = FileUploadUseBusinessRequestDto.build()
                .clone(addVo);

        boolean result = fileUploadUseBusinessService.updateFileUploadUseBusiness(dto);
        log.info("修改文件上传业务配置完成, ID: {}, 结果: {}", addVo.getId(), result);
        return result;
    }

    /**
     * 查看文件上传业务配置详情
     */
    @ApiDoc(
            value = "查看文件上传业务配置详情",
            description = "根据ID获取文件上传业务配置详细信息",
            request = HttpRequestByIdVo.class,
            response = FileUploadUseBusinessShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_USE_BUSINESS_INFO)
    public FileUploadUseBusinessShowResponseVo info( @Validated  HttpRequestByIdVo idVo) {

        log.info("查询文件上传业务配置详情, ID: {}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            log.warn("查询文件上传业务配置详情参数错误, ID: {}", idVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileUploadUseBusinessResponseDto dto =
                fileUploadUseBusinessService.getFileUploadUseBusinessById(idVo.getId());

        if (dto == null) {
            log.warn("文件上传业务配置不存在, ID: {}", idVo.getId());
            throw new RuntimeException("数据不存在");
        }

        FileUploadUseBusinessShowResponseVo vo = FileUploadUseBusinessShowResponseVo.build()
                .clone(dto);
        log.info("查询文件上传业务配置详情成功, ID: {}", idVo.getId());
        return vo;
    }

    /**
     * 跳转编辑页面
     */
    @ApiDoc(
            value = "跳转编辑页面",
            description = "获取文件上传业务配置编辑页面数据",
            request = HttpRequestByIdVo.class,
            response = FileUploadUseBusinessShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_USE_BUSINESS_EDIT)
    public FileUploadUseBusinessShowResponseVo edit( @Validated  HttpRequestByIdVo idVo) {

        log.info("跳转编辑页面, ID: {}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            log.warn("跳转编辑页面参数错误, ID: {}", idVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileUploadUseBusinessResponseDto dto =
                fileUploadUseBusinessService.getFileUploadUseBusinessById(idVo.getId());

        if (dto == null) {
            log.warn("文件上传业务配置不存在, ID: {}", idVo.getId());
            throw new RuntimeException("数据不存在");
        }

        FileUploadUseBusinessShowResponseVo vo = FileUploadUseBusinessShowResponseVo.build()
                .clone(dto);
        log.info("跳转编辑页面成功, ID: {}", idVo.getId());
        return vo;
    }

    /**
     * 跳转新增编辑界面
     */
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_USE_BUSINESS_NEW_INFO)
    public String newInfo(ModelMap modelMap) {
        log.info("跳转新增编辑页面");
        return "upload/fileUploadUseBusiness_edit";
    }

    /**
     * 删除文件上传业务配置信息
     */
    @ApiDoc(
            value = "删除文件上传业务配置信息",
            description = "根据ID列表删除文件上传业务配置记录",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_USE_BUSINESS_DELETE)
    public int delete( @Validated  HttpRequestByIdListVo idVo) {

        log.info("删除文件上传业务配置, ID列表: {}", idVo.getIdList());

        if (idVo.getIdList() == null || idVo.getIdList().isEmpty()) {
            log.warn("删除文件上传业务配置参数错误, ID列表为空");
            throw new RuntimeException("ID列表参数错误");
        }

        int result = fileUploadUseBusinessService.delFileUploadUseBusinessByIds(idVo.getIdList());
        log.info("删除文件上传业务配置完成, 删除数量: {}", result);
        return result;
    }

    /**
     * 导出文件上传业务配置信息
     */
    @ApiDoc(
            value = "导出文件上传业务配置信息",
            description = "导出文件上传业务配置数据到Excel文件",
            request = FileUploadUseBusinessQueryRequestVo.class,
            response = boolean.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_USE_BUSINESS_EXPORT)
    public void export(HttpServletResponse response,
                       @Validated  FileUploadUseBusinessQueryRequestVo queryVo) {

        log.info("导出文件上传业务配置, 参数: {}", queryVo);

        FileUploadUseBusinessRequestDto dto = FileUploadUseBusinessRequestDto.build()
                .clone(queryVo);

        Pager<FileUploadUseBusinessRequestDto> pager = Pager.of();
        pager.toPageSize(queryVo.getPageSize())
                .toPageNo(queryVo.getPageNo())
                .toParamObject(dto);

        FileUploadUseBusinessQueryEnum queryEnum = FileUploadUseBusinessQueryEnum.DESC_ID;
        PageResult<FileUploadUseBusinessResponseDto> resultList =
                fileUploadUseBusinessService.getFileUploadUseBusinessByNextPage(queryEnum, pager);
        List<FileUploadUseBusinessResponseDto> data = resultList.getList();

        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, FileUploadUseBusinessResponseVo.class,
                    data, "导出文件上传业务配置信息");
            log.info("导出文件上传业务配置完成, 数据量: {}", data.size());
        } catch (Exception e) {
            log.error("导出文件上传业务配置失败", e);
        }
    }

    /**
     * 通过excel导入数据
     */
    @ApiDoc(
            value = "导入文件上传业务配置信息",
            description = "通过Excel文件导入文件上传业务配置信息",
            request = MultipartFile.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_USE_BUSINESS_IMPORT)
    public boolean importExcel(@RequestParam("file") MultipartFile file) {

        log.info("导入文件上传业务配置, 文件名: {}", file.getOriginalFilename());

        try {
            boolean result = fileUploadUseBusinessService.saveData(file.getInputStream());
            log.info("导入文件上传业务配置完成, 结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("导入文件上传业务配置失败", e);
            throw new RuntimeException("导入失败");
        }
    }
}

