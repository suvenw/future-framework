package com.suven.framework.upload.controller;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.upload.dto.enums.FileAppStorageConfigQueryEnum;
import com.suven.framework.upload.dto.request.FileAppStorageConfigRequestDto;
import com.suven.framework.upload.dto.response.FileAppStorageConfigResponseDto;
import com.suven.framework.upload.service.FileAppStorageConfigService;
import com.suven.framework.upload.vo.request.FileAppStorageConfigAddRequestVo;
import com.suven.framework.upload.vo.request.FileAppStorageConfigQueryRequestVo;
import com.suven.framework.upload.vo.response.FileAppStorageConfigResponseVo;
import com.suven.framework.upload.vo.response.FileAppStorageConfigShowResponseVo;
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
 * 文件应用存储配置接口控制器
 *
 * 接口规范：
 * 1. 所有接口必须使用 @ApiDoc 注解
 * 2. 请求方式必须明确指定 (GET/POST/PUT/DELETE)
 * 3. 接口URL必须在 UrlCommand 中统一定义
 * 4. 返回结果必须使用统一的响应格式
 * 5. 必须使用 @Validated 开启参数校验
 * 6. 必须使用 @Slf4j 记录日志
 *
 * RequestMapping("/upload/fileAppStorageConfig")
 */
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc = DocumentConst.Sys.SYS_DOC_DES,
        module = "文件应用存储配置模块"
)
@Controller
@Slf4j
@Validated
public class FileAppStorageConfigWebController {

    @Autowired
    private FileAppStorageConfigService fileAppStorageConfigService;

    /**
     * URL 命令常量接口
     * 规范：全大写，下划线分隔，描述性名称
     */
    public interface UrlCommand {
        String UPLOAD_FILE_APP_STORAGE_CONFIG_INDEX = "/upload/fileappstorageconfig/index";
        String UPLOAD_FILE_APP_STORAGE_CONFIG_PAGE_LIST = "/upload/fileappstorageconfig/pageList";
        String UPLOAD_FILE_APP_STORAGE_CONFIG_LIST = "/upload/fileappstorageconfig/list";
        String UPLOAD_FILE_APP_STORAGE_CONFIG_QUERY_LIST = "/upload/fileappstorageconfig/querylist";
        String UPLOAD_FILE_APP_STORAGE_CONFIG_INFO = "/upload/fileappstorageconfig/detail";
        String UPLOAD_FILE_APP_STORAGE_CONFIG_CREATE = "/upload/fileappstorageconfig/add";
        String UPLOAD_FILE_APP_STORAGE_CONFIG_UPDATE = "/upload/fileappstorageconfig/modify";
        String UPLOAD_FILE_APP_STORAGE_CONFIG_DELETE = "/upload/fileappstorageconfig/delete";
        String UPLOAD_FILE_APP_STORAGE_CONFIG_EDIT = "/upload/fileappstorageconfig/edit";
        String UPLOAD_FILE_APP_STORAGE_CONFIG_NEW_INFO = "/upload/fileappstorageconfig/newInfo";
        String UPLOAD_FILE_APP_STORAGE_CONFIG_SORT = "/upload/fileappstorageconfig/sort";
        String UPLOAD_FILE_APP_STORAGE_CONFIG_TURN_ON = "/upload/fileappstorageconfig/turnOn";
        String UPLOAD_FILE_APP_STORAGE_CONFIG_TURN_OFF = "/upload/fileappstorageconfig/turnOff";
        String UPLOAD_FILE_APP_STORAGE_CONFIG_EXPORT = "/upload/fileappstorageconfig/export";
        String UPLOAD_FILE_APP_STORAGE_CONFIG_IMPORT = "/upload/fileappstorageconfig/import";
    }

    /**
     * 跳转到主界面
     *
     * @return 页面路径
     */
    @GetMapping(value = UrlCommand.UPLOAD_FILE_APP_STORAGE_CONFIG_INDEX)
    public String index() {
        log.info("跳转文件应用存储配置主界面");
        return "upload/fileAppStorageConfig_index";
    }

    /**
     * 分页获取文件应用存储配置信息
     *
     * @param queryVo 查询请求参数
     * @return PageResult<FileAppStorageConfigShowResponseVo> 分页响应结果
     *
     * 接口规则：
     * 1. 分页参数必须使用 Pager 包装
     * 2. 必须指定排序枚举
     * 3. 必须记录操作日志
     */
    @ApiDoc(
            value = "分页获取文件应用存储配置信息",
            description = "根据查询条件分页获取文件应用存储配置列表",
            request = FileAppStorageConfigQueryRequestVo.class,
            response = FileAppStorageConfigShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_APP_STORAGE_CONFIG_PAGE_LIST)
    public PageResult<FileAppStorageConfigShowResponseVo> pageList(
            @Valid FileAppStorageConfigQueryRequestVo queryVo) {

        log.info("分页查询文件应用存储配置, 参数: {}", queryVo);

        FileAppStorageConfigRequestDto dto = FileAppStorageConfigRequestDto.build()
                .clone(queryVo);

        Pager<FileAppStorageConfigRequestDto> pager = new Pager<>(
                queryVo.getPageNo(),
                queryVo.getPageSize()
        );
        pager.toPageSize(queryVo.getPageSize())
                .toPageNo(queryVo.getPageNo())
                .toParamObject(dto);

        FileAppStorageConfigQueryEnum queryEnum = FileAppStorageConfigQueryEnum.DESC_ID;
        PageResult<FileAppStorageConfigResponseDto> resultList =
                fileAppStorageConfigService.getFileAppStorageConfigByNextPage(queryEnum, pager);

        if (ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())) {
            log.info("分页查询文件应用存储配置完成, 无数据");
            return new PageResult<>();
        }

        PageResult<FileAppStorageConfigShowResponseVo> result =
                resultList.convertBuild(FileAppStorageConfigShowResponseVo.class);
        log.info("分页查询文件应用存储配置完成, 总数: {}", result.getTotal());
        return result;
    }

    /**
     * 根据条件查询文件应用存储配置信息
     *
     * @param queryVo 查询请求参数
     * @return List<FileAppStorageConfigShowResponseVo> 响应结果列表
     */
    @ApiDoc(
            value = "根据条件查询文件应用存储配置信息",
            description = "根据查询条件获取文件应用存储配置列表",
            request = FileAppStorageConfigQueryRequestVo.class,
            response = FileAppStorageConfigShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_APP_STORAGE_CONFIG_QUERY_LIST)
    public List<FileAppStorageConfigShowResponseVo> queryList(
            @Valid FileAppStorageConfigQueryRequestVo queryVo) {

        log.info("根据条件查询文件应用存储配置, 参数: {}", queryVo);

        FileAppStorageConfigRequestDto dto = FileAppStorageConfigRequestDto.build()
                .clone(queryVo);

        FileAppStorageConfigQueryEnum queryEnum = FileAppStorageConfigQueryEnum.DESC_ID;
        List<FileAppStorageConfigResponseDto> resultList =
                fileAppStorageConfigService.getFileAppStorageConfigListByQuery(queryEnum, dto);

        if (resultList == null || resultList.isEmpty()) {
            log.info("根据条件查询文件应用存储配置完成, 无数据");
            return new ArrayList<>();
        }

        List<FileAppStorageConfigShowResponseVo> listVo =
                IterableConvert.convertList(resultList, FileAppStorageConfigShowResponseVo.class);
        log.info("根据条件查询文件应用存储配置完成, 数量: {}", listVo.size());
        return listVo;
    }

    /**
     * 新增文件应用存储配置信息
     *
     * @param addVo 新增请求参数
     * @return Long 新增记录的ID
     */
    @ApiDoc(
            value = "新增文件应用存储配置信息",
            description = "新增文件应用存储配置记录",
            request = FileAppStorageConfigAddRequestVo.class,
            response = Long.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_APP_STORAGE_CONFIG_CREATE)
    public Long create(@Valid FileAppStorageConfigAddRequestVo addVo) {

        log.info("新增文件应用存储配置, 参数: {}", addVo);

        FileAppStorageConfigRequestDto dto = FileAppStorageConfigRequestDto.build()
                .clone(addVo);

        FileAppStorageConfigResponseDto responseDto =
                fileAppStorageConfigService.saveFileAppStorageConfig(dto);

        if (responseDto == null) {
            log.warn("新增文件应用存储配置失败");
            throw new RuntimeException("新增失败");
        }

        log.info("新增文件应用存储配置成功, ID: {}", responseDto.getId());
        return responseDto.getId();
    }

    /**
     * 修改文件应用存储配置信息
     *
     * @param addVo 修改请求参数
     * @return boolean 修改是否成功
     */
    @ApiDoc(
            value = "修改文件应用存储配置信息",
            description = "根据ID修改文件应用存储配置记录",
            request = FileAppStorageConfigAddRequestVo.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_APP_STORAGE_CONFIG_UPDATE)
    public boolean update(@Valid FileAppStorageConfigAddRequestVo addVo) {

        log.info("修改文件应用存储配置, 参数: {}", addVo);

        if (addVo.getId() == null || addVo.getId() <= 0) {
            log.warn("修改文件应用存储配置参数错误, ID: {}", addVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileAppStorageConfigRequestDto dto = FileAppStorageConfigRequestDto.build()
                .clone(addVo);

        boolean result = fileAppStorageConfigService.updateFileAppStorageConfig(dto);
        log.info("修改文件应用存储配置完成, ID: {}, 结果: {}", addVo.getId(), result);
        return result;
    }

    /**
     * 查看文件应用存储配置详情
     *
     * @param idVo ID请求参数
     * @return FileAppStorageConfigShowResponseVo 详情响应结果
     *
     * 接口规则：
     * 1. ID参数必须校验非空
     * 2. 必须处理数据不存在情况
     * 3. 必须记录查询日志
     */
    @ApiDoc(
            value = "查看文件应用存储配置详情",
            description = "根据ID获取文件应用存储配置详细信息",
            request = HttpRequestByIdVo.class,
            response = FileAppStorageConfigShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_APP_STORAGE_CONFIG_INFO)
    public FileAppStorageConfigShowResponseVo info(@Valid HttpRequestByIdVo idVo) {

        log.info("查询文件应用存储配置详情, ID: {}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            log.warn("查询文件应用存储配置详情参数错误, ID: {}", idVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileAppStorageConfigResponseDto dto =
                fileAppStorageConfigService.getFileAppStorageConfigById(idVo.getId());

        if (dto == null) {
            log.warn("文件应用存储配置不存在, ID: {}", idVo.getId());
            throw new RuntimeException("数据不存在");
        }

        FileAppStorageConfigShowResponseVo vo = FileAppStorageConfigShowResponseVo.build()
                .clone(dto);
        log.info("查询文件应用存储配置详情成功, ID: {}", idVo.getId());
        return vo;
    }

    /**
     * 跳转编辑页面（加载详情用于编辑）
     *
     * @param idVo ID请求参数
     * @return FileAppStorageConfigShowResponseVo 编辑页面数据
     */
    @ApiDoc(
            value = "跳转编辑页面",
            description = "获取文件应用存储配置编辑页面数据",
            request = HttpRequestByIdVo.class,
            response = FileAppStorageConfigShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_APP_STORAGE_CONFIG_EDIT)
    public FileAppStorageConfigShowResponseVo edit(@Valid HttpRequestByIdVo idVo) {

        log.info("跳转文件应用存储配置编辑页面, ID: {}", idVo.getId());

        if (idVo.getId() == null || idVo.getId() <= 0) {
            log.warn("跳转编辑页面参数错误, ID: {}", idVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        FileAppStorageConfigResponseDto dto =
                fileAppStorageConfigService.getFileAppStorageConfigById(idVo.getId());

        if (dto == null) {
            log.warn("文件应用存储配置不存在, ID: {}", idVo.getId());
            throw new RuntimeException("数据不存在");
        }

        FileAppStorageConfigShowResponseVo vo = FileAppStorageConfigShowResponseVo.build()
                .clone(dto);
        log.info("跳转文件应用存储配置编辑页面成功, ID: {}", idVo.getId());
        return vo;
    }

    /**
     * 跳转新增编辑界面
     *
     * @return 页面路径
     */
    @GetMapping(value = UrlCommand.UPLOAD_FILE_APP_STORAGE_CONFIG_NEW_INFO)
    public String newInfo(ModelMap modelMap) {
        log.info("跳转文件应用存储配置新增编辑页面");
        return "upload/fileAppStorageConfig_edit";
    }

    /**
     * 删除文件应用存储配置信息
     *
     * @param idVo ID列表请求参数
     * @return int 删除数量
     */
    @ApiDoc(
            value = "删除文件应用存储配置信息",
            description = "根据ID列表删除文件应用存储配置记录",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_APP_STORAGE_CONFIG_DELETE)
    public int delete(@Valid HttpRequestByIdListVo idVo) {

        log.info("删除文件应用存储配置, ID列表: {}", idVo.getIdList());

        if (idVo.getIdList() == null || idVo.getIdList().isEmpty()) {
            log.warn("删除文件应用存储配置参数错误, ID列表为空");
            throw new RuntimeException("ID列表参数错误");
        }

        int result = fileAppStorageConfigService.delFileAppStorageConfigByIds(idVo.getIdList());
        log.info("删除文件应用存储配置完成, 删除数量: {}", result);
        return result;
    }

    /**
     * 导出文件应用存储配置信息
     *
     * @param response 响应流
     * @param queryVo 查询参数
     */
    @ApiDoc(
            value = "导出文件应用存储配置信息",
            description = "导出文件应用存储配置数据到Excel文件",
            request = FileAppStorageConfigQueryRequestVo.class,
            response = boolean.class
    )
    @GetMapping(value = UrlCommand.UPLOAD_FILE_APP_STORAGE_CONFIG_EXPORT)
    public void export(HttpServletResponse response,
                       @Valid FileAppStorageConfigQueryRequestVo queryVo) {

        log.info("导出文件应用存储配置, 参数: {}", queryVo);

        FileAppStorageConfigRequestDto dto = FileAppStorageConfigRequestDto.build()
                .clone(queryVo);

        Pager<FileAppStorageConfigRequestDto> pager = Pager.of();
        pager.toPageSize(queryVo.getPageSize())
                .toPageNo(queryVo.getPageNo())
                .toParamObject(dto);

        FileAppStorageConfigQueryEnum queryEnum = FileAppStorageConfigQueryEnum.DESC_ID;
        PageResult<FileAppStorageConfigResponseDto> resultList =
                fileAppStorageConfigService.getFileAppStorageConfigByNextPage(queryEnum, pager);
        List<FileAppStorageConfigResponseDto> data = resultList.getList();

        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, FileAppStorageConfigResponseVo.class,
                    data, "导出文件应用存储配置信息");
            log.info("导出文件应用存储配置完成, 数据量: {}", data.size());
        } catch (Exception e) {
            log.error("导出文件应用存储配置失败", e);
        }
    }

    /**
     * 通过Excel导入文件应用存储配置信息
     *
     * @param file 上传文件
     * @return boolean 导入结果
     */
    @ApiDoc(
            value = "导入文件应用存储配置信息",
            description = "通过Excel文件导入文件应用存储配置信息",
            request = MultipartFile.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.UPLOAD_FILE_APP_STORAGE_CONFIG_IMPORT)
    public boolean importExcel(@RequestParam("file") MultipartFile file) {

        log.info("导入文件应用存储配置, 文件名: {}", file.getOriginalFilename());

        try {
            InputStream initialStream = file.getInputStream();
            boolean result = fileAppStorageConfigService.saveData(initialStream);
            log.info("导入文件应用存储配置完成, 结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("导入文件应用存储配置失败", e);
            throw new RuntimeException("导入失败");
        }
    }
}

