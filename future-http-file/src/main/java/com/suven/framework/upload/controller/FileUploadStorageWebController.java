package com.suven.framework.upload.controller;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.upload.dto.request.FileUploadStorageRequestDto;
import com.suven.framework.upload.dto.response.FileUploadStorageResponseDto;
import com.suven.framework.upload.dto.enums.FileUploadStorageQueryEnum;
import com.suven.framework.upload.service.FileUploadStorageService;
import com.suven.framework.upload.vo.request.FileUploadStorageAddRequestVo;
import com.suven.framework.upload.vo.request.FileUploadStorageQueryRequestVo;
import com.suven.framework.upload.vo.response.FileUploadStorageResponseVo;
import com.suven.framework.upload.vo.response.FileUploadStorageShowResponseVo;
import com.suven.framework.util.excel.ExcelUtils;
import jakarta.servlet.http.HttpServletResponse;
 
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者 : suven
 * @version 版本: v2.0.0
 * date 创建时间: 2024-04-18 23:55:18
 * <pre>
 *
 *  Description: 文件上传存储配置表接口控制器
 *
 *  接口规范：
 *  1. 所有接口必须使用 @ApiDoc 注解
 *  2. 请求方式必须明确指定 (GET/POST/PUT/DELETE)
 *  3. 接口URL必须在 UrlCommand 中统一定义
 *  4. 返回结果必须使用统一的响应格式
 *  5. 必须使用 @Validated 开启参数校验
 *  6. 必须使用 @Slf4j 记录日志
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *    v2.0.0         suven    2026-02-05   重构:统一接口规范
 * ----------------------------------------------------------------------------
 * RequestMapping("/upload/fileUploadStorage")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc = DocumentConst.Sys.SYS_DOC_DES,
        module = "文件上传存储配置模块"
)
@Controller
@Slf4j
@Validated
public class FileUploadStorageWebController {

    @Autowired
    private FileUploadStorageService fileUploadStorageService;

    /**
     * URL 命令常量接口
     * 规范：全大写，下划线分隔，描述性名称
     */
    public interface UrlCommand {
        String UPLOAD_FILE_UPLOAD_STORAGE_INDEX = "/upload/fileuploadstorage/index";
        String UPLOAD_FILE_UPLOAD_STORAGE_PAGE_LIST = "/upload/fileuploadstorage/pageList";
        String UPLOAD_FILE_UPLOAD_STORAGE_LIST = "/upload/fileuploadstorage/list";
        String UPLOAD_FILE_UPLOAD_STORAGE_QUERY_LIST = "/upload/fileuploadstorage/queryList";
        String UPLOAD_FILE_UPLOAD_STORAGE_INFO = "/upload/fileuploadstorage/info";
        String UPLOAD_FILE_UPLOAD_STORAGE_CREATE = "/upload/fileuploadstorage/create";
        String UPLOAD_FILE_UPLOAD_STORAGE_UPDATE = "/upload/fileuploadstorage/update";
        String UPLOAD_FILE_UPLOAD_STORAGE_DELETE = "/upload/fileuploadstorage/delete";
        String UPLOAD_FILE_UPLOAD_STORAGE_EDIT = "/upload/fileuploadstorage/edit";
        String UPLOAD_FILE_UPLOAD_STORAGE_NEW_INFO = "/upload/fileuploadstorage/newInfo";
        String UPLOAD_FILE_UPLOAD_STORAGE_EXPORT = "/upload/fileuploadstorage/export";
        String UPLOAD_FILE_UPLOAD_STORAGE_IMPORT = "/upload/fileuploadstorage/import";
    }

    /**
     * 跳转到主界面
     *
     * @return 字符串url
     * @author suven
     * date 2024-04-18 23:55:18
     */
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_STORAGE_INDEX)
    public String index() {
        log.info("跳转文件上传存储配置主界面");
        return "upload/fileUploadStorage_index";
    }

    /**
     * 分页获取文件上传存储配置信息
     *
     * @param fileUploadStorageQueryRequestVo 查询请求参数
     * @return PageResult<FileUploadStorageShowResponseVo> 分页响应结果
     * @author suven
     * date 2024-04-18 23:55:18
     *
     * 接口规则：
     * 1. 分页参数必须使用 Pager 包装
     * 2. 必须指定排序枚举
     * 3. 必须记录操作日志
     */
    @ApiDoc(
            value = "分页获取文件上传存储配置信息",
            description = "根据查询条件分页获取文件上传存储配置列表",
            request = FileUploadStorageQueryRequestVo.class,
            response = FileUploadStorageShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_STORAGE_PAGE_LIST)
    public PageResult<FileUploadStorageShowResponseVo> pageList(
            @Validated  FileUploadStorageQueryRequestVo fileUploadStorageQueryRequestVo) {

        log.info("分页查询文件上传存储配置, 参数: {}", fileUploadStorageQueryRequestVo);

        FileUploadStorageRequestDto fileUploadStorageRequestDto = FileUploadStorageRequestDto.build()
                .clone(fileUploadStorageQueryRequestVo);

        Pager<FileUploadStorageRequestDto> pager = new Pager<>(
                fileUploadStorageQueryRequestVo.getPageNo(),
                fileUploadStorageQueryRequestVo.getPageSize()
        );
        pager.toPageSize(fileUploadStorageQueryRequestVo.getPageSize())
                .toPageNo(fileUploadStorageQueryRequestVo.getPageNo())
                .toParamObject(fileUploadStorageRequestDto);

        FileUploadStorageQueryEnum queryEnum = FileUploadStorageQueryEnum.DESC_ID;
        PageResult<FileUploadStorageResponseDto> resultList = fileUploadStorageService
                .getFileUploadStorageByNextPage(queryEnum, pager);

        if (ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())) {
            log.info("分页查询文件上传存储配置完成, 无数据");
            return new PageResult<>();
        }

        PageResult<FileUploadStorageShowResponseVo> result = resultList
                .convertBuild(FileUploadStorageShowResponseVo.class);
        log.info("分页查询文件上传存储配置完成, 总数: {}", result.getTotal());
        return result;
    }

    /**
     * 根据条件查询文件上传存储配置信息
     *
     * @param fileUploadStorageQueryRequestVo 查询请求参数
     * @return List<FileUploadStorageShowResponseVo> 响应结果列表
     * @author suven
     * date 2024-04-18 23:55:18
     */
    @ApiDoc(
            value = "根据条件查询文件上传存储配置信息",
            description = "根据查询条件获取文件上传存储配置列表",
            request = FileUploadStorageQueryRequestVo.class,
            response = FileUploadStorageShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_STORAGE_QUERY_LIST)
    public List<FileUploadStorageShowResponseVo> queryList(
            @Validated  FileUploadStorageQueryRequestVo fileUploadStorageQueryRequestVo) {

        log.info("根据条件查询文件上传存储配置, 参数: {}", fileUploadStorageQueryRequestVo);

        FileUploadStorageRequestDto fileUploadStorageRequestDto = FileUploadStorageRequestDto.build()
                .clone(fileUploadStorageQueryRequestVo);

        FileUploadStorageQueryEnum queryEnum = FileUploadStorageQueryEnum.DESC_ID;
        List<FileUploadStorageResponseDto> resultList = fileUploadStorageService
                .getFileUploadStorageListByQuery(queryEnum, fileUploadStorageRequestDto);

        if (resultList == null || resultList.isEmpty()) {
            log.info("根据条件查询文件上传存储配置完成, 无数据");
            return new ArrayList<>();
        }

        List<FileUploadStorageShowResponseVo> listVo = IterableConvert.convertList(
                resultList, FileUploadStorageShowResponseVo.class);
        log.info("根据条件查询文件上传存储配置完成, 数量: {}", listVo.size());
        return listVo;
    }

    /**
     * 新增文件上传存储配置信息
     *
     * @param fileUploadStorageAddRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * date 2024-04-18 23:55:18
     */
    @ApiDoc(
            value = "新增文件上传存储配置信息",
            description = "新增文件上传存储配置记录",
            request = FileUploadStorageAddRequestVo.class,
            response = Long.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_STORAGE_CREATE)
    public Long create( @ValidatedFileUploadStorageAddRequestVo fileUploadStorageAddRequestVo) {

        log.info("新增文件上传存储配置, 参数: {}", fileUploadStorageAddRequestVo);

        FileUploadStorageRequestDto fileUploadStorageRequestDto = FileUploadStorageRequestDto.build()
                .clone(fileUploadStorageAddRequestVo);

        FileUploadStorageResponseDto fileUploadStorageresponseDto = fileUploadStorageService
                .saveFileUploadStorage(fileUploadStorageRequestDto);

        if (fileUploadStorageresponseDto == null) {
            log.warn("新增文件上传存储配置失败");
            throw new RuntimeException("新增失败");
        }

        log.info("新增文件上传存储配置成功, ID: {}", fileUploadStorageresponseDto.getId());
        return fileUploadStorageresponseDto.getId();
    }

    /**
     * 修改文件上传存储配置信息
     *
     * @param fileUploadStorageAddRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * date 2024-04-18 23:55:18
     */
    @ApiDoc(
            value = "修改文件上传存储配置信息",
            description = "根据ID修改文件上传存储配置记录",
            request = FileUploadStorageAddRequestVo.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_STORAGE_UPDATE)
    public boolean update( @ValidatedFileUploadStorageAddRequestVo fileUploadStorageAddRequestVo) {

        log.info("修改文件上传存储配置, 参数: {}", fileUploadStorageAddRequestVo);

        if (fileUploadStorageAddRequestVo.getId() == null || fileUploadStorageAddRequestVo.getId() <= 0) {
            log.warn("修改文件上传存储配置参数错误, ID: {}", fileUploadStorageAddRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileUploadStorageRequestDto fileUploadStorageRequestDto = FileUploadStorageRequestDto.build()
                .clone(fileUploadStorageAddRequestVo);

        boolean result = fileUploadStorageService.updateFileUploadStorage(fileUploadStorageRequestDto);
        log.info("修改文件上传存储配置完成, ID: {}, 结果: {}", fileUploadStorageAddRequestVo.getId(), result);
        return result;
    }

    /**
     * 查看文件上传存储配置详情
     *
     * @param idRequestVo ID请求参数
     * @return FileUploadStorageShowResponseVo 详情响应结果
     * @author suven
     * date 2024-04-18 23:55:18
     *
     * 接口规则：
     * 1. ID参数必须校验非空
     * 2. 必须处理数据不存在情况
     * 3. 必须记录查询日志
     */
    @ApiDoc(
            value = "查看文件上传存储配置详情",
            description = "根据ID获取文件上传存储配置详细信息",
            request = HttpRequestByIdVo.class,
            response = FileUploadStorageShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_STORAGE_INFO)
    public FileUploadStorageShowResponseVo info( @Validated  HttpRequestByIdVo idRequestVo) {

        log.info("查询文件上传存储配置详情, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询文件上传存储配置详情参数错误, ID: {}", idRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileUploadStorageResponseDto fileUploadStorageResponseDto = fileUploadStorageService
                .getFileUploadStorageById(idRequestVo.getId());

        if (fileUploadStorageResponseDto == null) {
            log.warn("文件上传存储配置不存在, ID: {}", idRequestVo.getId());
            throw new RuntimeException("数据不存在");
        }

        FileUploadStorageShowResponseVo vo = FileUploadStorageShowResponseVo.build()
                .clone(fileUploadStorageResponseDto);
        log.info("查询文件上传存储配置详情成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 跳转编辑页面
     *
     * @param idRequestVo ID请求参数
     * @return FileUploadStorageShowResponseVo 编辑页面数据
     * @author suven
     * date 2024-04-18 23:55:18
     */
    @ApiDoc(
            value = "跳转编辑页面",
            description = "获取文件上传存储配置编辑页面数据",
            request = HttpRequestByIdVo.class,
            response = FileUploadStorageShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_STORAGE_EDIT)
    public FileUploadStorageShowResponseVo edit( @Validated  HttpRequestByIdVo idRequestVo) {

        log.info("跳转编辑页面, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("跳转编辑页面参数错误, ID: {}", idRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileUploadStorageResponseDto fileUploadStorageResponseDto = fileUploadStorageService
                .getFileUploadStorageById(idRequestVo.getId());

        if (fileUploadStorageResponseDto == null) {
            log.warn("文件上传存储配置不存在, ID: {}", idRequestVo.getId());
            throw new RuntimeException("数据不存在");
        }

        FileUploadStorageShowResponseVo vo = FileUploadStorageShowResponseVo.build()
                .clone(fileUploadStorageResponseDto);
        log.info("跳转编辑页面成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 跳转新增编辑界面
     *
     * @return 页面路径
     * @author suven
     * date 2024-04-18 23:55:18
     */
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_STORAGE_NEW_INFO)
    public String newInfo(ModelMap modelMap) {
        log.info("跳转新增编辑页面");
        return "upload/fileUploadStorage_edit";
    }

    /**
     * 删除文件上传存储配置信息
     *
     * @param idRequestVo ID请求参数
     * @return int 删除数量
     * @author suven
     * date 2024-04-18 23:55:18
     */
    @ApiDoc(
            value = "删除文件上传存储配置信息",
            description = "根据ID列表删除文件上传存储配置记录",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_STORAGE_DELETE)
    public int delete( @Validated  HttpRequestByIdListVo idRequestVo) {

        log.info("删除文件上传存储配置, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除文件上传存储配置参数错误, ID列表为空");
            throw new RuntimeException("ID列表参数错误");
        }

        int result = fileUploadStorageService.delFileUploadStorageByIds(idRequestVo.getIdList());
        log.info("删除文件上传存储配置完成, 删除数量: {}", result);
        return result;
    }

    /**
     * 导出文件上传存储配置信息
     *
     * @param response 响应流
     * @param fileUploadStorageQueryRequestVo 查询参数
     * @author suven
     * date 2024-04-18 23:55:18
     */
    @ApiDoc(
            value = "导出文件上传存储配置信息",
            description = "导出文件上传存储配置数据到Excel文件",
            request = FileUploadStorageQueryRequestVo.class,
            response = boolean.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_STORAGE_EXPORT)
    public void export(HttpServletResponse response,
                       @Validated  FileUploadStorageQueryRequestVo fileUploadStorageQueryRequestVo) {

        log.info("导出文件上传存储配置, 参数: {}", fileUploadStorageQueryRequestVo);

        FileUploadStorageRequestDto fileUploadStorageRequestDto = FileUploadStorageRequestDto.build()
                .clone(fileUploadStorageQueryRequestVo);

        Pager<FileUploadStorageRequestDto> pager = Pager.of();
        pager.toPageSize(fileUploadStorageQueryRequestVo.getPageSize())
                .toPageNo(fileUploadStorageQueryRequestVo.getPageNo())
                .toParamObject(fileUploadStorageRequestDto);

        FileUploadStorageQueryEnum queryEnum = FileUploadStorageQueryEnum.DESC_ID;
        PageResult<FileUploadStorageResponseDto> resultList = fileUploadStorageService
                .getFileUploadStorageByNextPage(queryEnum, pager);
        List<FileUploadStorageResponseDto> data = resultList.getList();

        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, FileUploadStorageResponseVo.class, data, "导出文件上传存储配置信息");
            log.info("导出文件上传存储配置完成, 数据量: {}", data.size());
        } catch (Exception e) {
            log.error("导出文件上传存储配置失败", e);
        }
    }

    /**
     * 通过Excel导入数据
     *
     * @param file 上传文件
     * @return boolean 导入结果
     * @author suven
     * date 2024-04-18 23:55:18
     */
    @ApiDoc(
            value = "导入文件上传存储配置数据",
            description = "通过Excel文件导入文件上传存储配置数据",
            request = MultipartFile.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_UPLOAD_STORAGE_IMPORT)
    public boolean importExcel(@RequestParam("file") MultipartFile file) {

        log.info("导入文件上传存储配置, 文件名: {}", file.getOriginalFilename());

        try {
            InputStream initialStream = file.getInputStream();
            boolean result = fileUploadStorageService.saveData(initialStream);
            log.info("导入文件上传存储配置完成, 结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("导入文件上传存储配置失败", e);
            throw new RuntimeException("导入失败");
        }
    }
}
