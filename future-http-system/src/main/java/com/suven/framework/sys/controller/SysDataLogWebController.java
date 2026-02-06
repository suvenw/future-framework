package com.suven.framework.sys.controller;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.sys.dto.enums.SysDataLogQueryEnum;
import com.suven.framework.sys.dto.request.SysDataLogRequestDto;
import com.suven.framework.sys.dto.response.SysDataLogResponseDto;
import com.suven.framework.sys.service.SysDataLogService;
import com.suven.framework.sys.vo.request.SysDataLogAddRequestVo;
import com.suven.framework.sys.vo.request.SysDataLogQueryRequestVo;
import com.suven.framework.sys.vo.response.SysDataLogResponseVo;
import com.suven.framework.sys.vo.response.SysDataLogShowResponseVo;
import com.suven.framework.util.excel.ExcelUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
 * 系统数据日志 Web 控制器
 *
 * RequestMapping("/sys/sysDataLog")
 */
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc = DocumentConst.Sys.SYS_DOC_DES,
        module = "系统数据日志模块"
)
@Controller
@Slf4j
@Validated
public class SysDataLogWebController {

    @Autowired
    private SysDataLogService sysDataLogService;

    /**
     * 跳转到系统数据日志主界面
     */
    @GetMapping(value = UrlCommand.sys_sysDataLog_index)
    public String index() {
        log.info("跳转系统数据日志主界面");
        return "sys/sysDataLog_index";
    }

    /**
     * 获取系统数据日志分页信息
     */
    @ApiDoc(
            value = "获取系统数据日志分页信息",
            description = "根据查询条件分页获取系统数据日志列表",
            request = SysDataLogQueryRequestVo.class,
            response = SysDataLogShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.sys_sysDataLog_list)
    public PageResult<SysDataLogShowResponseVo> pageList(
            @Valid SysDataLogQueryRequestVo sysDataLogQueryRequestVo) {

        log.info("分页查询系统数据日志, 参数: {}", sysDataLogQueryRequestVo);

        SysDataLogRequestDto sysDataLogRequestDto = SysDataLogRequestDto.build()
                .clone(sysDataLogQueryRequestVo);

        Pager<SysDataLogRequestDto> pager = Pager.of();
        pager.toPageSize(sysDataLogQueryRequestVo.getPageSize())
                .toPageNo(sysDataLogQueryRequestVo.getPageNo())
                .toParamObject(sysDataLogRequestDto);

        SysDataLogQueryEnum queryEnum = SysDataLogQueryEnum.DESC_ID;
        PageResult<SysDataLogResponseDto> resultList =
                sysDataLogService.getSysDataLogByNextPage(pager, queryEnum);

        if (ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())) {
            log.info("分页查询系统数据日志完成, 无数据");
            return new PageResult<>();
        }

        PageResult<SysDataLogShowResponseVo> result =
                resultList.convertBuild(SysDataLogShowResponseVo.class);
        log.info("分页查询系统数据日志完成, 总数: {}", result.getTotal());
        return result;
    }

    /**
     * 根据条件查询系统数据日志信息
     */
    @ApiDoc(
            value = "根据条件查询系统数据日志信息",
            description = "根据查询条件获取系统数据日志列表",
            request = SysDataLogQueryRequestVo.class,
            response = SysDataLogShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.sys_sysDataLog_queryList)
    public List<SysDataLogShowResponseVo> queryList(
            @Valid SysDataLogQueryRequestVo sysDataLogQueryRequestVo) {

        log.info("根据条件查询系统数据日志, 参数: {}", sysDataLogQueryRequestVo);

        SysDataLogRequestDto sysDataLogRequestDto = SysDataLogRequestDto.build()
                .clone(sysDataLogQueryRequestVo);

        Pager<SysDataLogRequestDto> pager = Pager.of();
        pager.toPageSize(sysDataLogQueryRequestVo.getPageSize())
                .toPageNo(sysDataLogQueryRequestVo.getPageNo())
                .toParamObject(sysDataLogRequestDto);

        SysDataLogQueryEnum queryEnum = SysDataLogQueryEnum.DESC_ID;
        List<SysDataLogResponseDto> resultList =
                sysDataLogService.getSysDataLogListByQuery(pager, queryEnum);

        if (resultList == null || resultList.isEmpty()) {
            log.info("根据条件查询系统数据日志完成, 无数据");
            return new ArrayList<>();
        }

        List<SysDataLogShowResponseVo> listVo =
                IterableConvert.convertList(resultList, SysDataLogShowResponseVo.class);
        log.info("根据条件查询系统数据日志完成, 数量: {}", listVo.size());
        return listVo;
    }

    /**
     * 新增系统数据日志信息
     */
    @ApiDoc(
            value = "新增系统数据日志信息",
            description = "新增系统数据日志记录",
            request = SysDataLogAddRequestVo.class,
            response = Long.class
    )
    @PostMapping(value = UrlCommand.sys_sysDataLog_add)
    public Long create(@Valid SysDataLogAddRequestVo sysDataLogAddRequestVo) {

        log.info("新增系统数据日志, 参数: {}", sysDataLogAddRequestVo);

        SysDataLogRequestDto sysDataLogRequestDto = SysDataLogRequestDto.build()
                .clone(sysDataLogAddRequestVo);

        SysDataLogResponseDto sysDataLogResponseDto =
                sysDataLogService.saveSysDataLog(sysDataLogRequestDto);

        if (sysDataLogResponseDto == null) {
            log.warn("新增系统数据日志失败");
            throw new RuntimeException("新增失败");
        }

        log.info("新增系统数据日志成功, ID: {}", sysDataLogResponseDto.getId());
        return sysDataLogResponseDto.getId();
    }

    /**
     * 修改系统数据日志信息
     */
    @ApiDoc(
            value = "修改系统数据日志信息",
            description = "根据ID修改系统数据日志记录",
            request = SysDataLogAddRequestVo.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.sys_sysDataLog_modify)
    public boolean update(@Valid SysDataLogAddRequestVo sysDataLogAddRequestVo) {

        log.info("修改系统数据日志, 参数: {}", sysDataLogAddRequestVo);

        if (sysDataLogAddRequestVo.getId() == null || sysDataLogAddRequestVo.getId() <= 0) {
            log.warn("修改系统数据日志参数错误, ID: {}", sysDataLogAddRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        SysDataLogRequestDto sysDataLogRequestDto = SysDataLogRequestDto.build()
                .clone(sysDataLogAddRequestVo);

        boolean result = sysDataLogService.updateSysDataLog(sysDataLogRequestDto);
        log.info("修改系统数据日志完成, ID: {}, 结果: {}", sysDataLogAddRequestVo.getId(), result);
        return result;
    }

    /**
     * 查看系统数据日志详情
     */
    @ApiDoc(
            value = "查看系统数据日志信息",
            description = "根据ID获取系统数据日志详细信息",
            request = HttpRequestByIdVo.class,
            response = SysDataLogShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.sys_sysDataLog_detail)
    public SysDataLogShowResponseVo info(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("查询系统数据日志详情, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询系统数据日志详情参数错误, ID: {}", idRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        SysDataLogResponseDto sysDataLogResponseDto =
                sysDataLogService.getSysDataLogById(idRequestVo.getId());

        if (sysDataLogResponseDto == null) {
            log.warn("系统数据日志不存在, ID: {}", idRequestVo.getId());
            throw new RuntimeException("数据不存在");
        }

        SysDataLogShowResponseVo vo = SysDataLogShowResponseVo.build()
                .clone(sysDataLogResponseDto);
        log.info("查询系统数据日志详情成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 跳转系统数据日志编辑页面（加载详情数据）
     */
    @ApiDoc(
            value = "跳转系统数据日志编辑页面",
            description = "获取系统数据日志编辑页面数据",
            request = HttpRequestByIdVo.class,
            response = SysDataLogShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.sys_sysDataLog_edit)
    public SysDataLogShowResponseVo edit(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("跳转系统数据日志编辑页面, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("跳转编辑页面参数错误, ID: {}", idRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        SysDataLogResponseDto sysDataLogResponseDto =
                sysDataLogService.getSysDataLogById(idRequestVo.getId());

        if (sysDataLogResponseDto == null) {
            log.warn("系统数据日志不存在, ID: {}", idRequestVo.getId());
            throw new RuntimeException("数据不存在");
        }

        SysDataLogShowResponseVo vo = SysDataLogShowResponseVo.build()
                .clone(sysDataLogResponseDto);
        log.info("跳转系统数据日志编辑页面成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 跳转系统数据日志新增编辑界面
     */
    @GetMapping(value = UrlCommand.sys_sysDataLog_newInfo)
    public String newInfo(ModelMap modelMap) {
        log.info("跳转系统数据日志新增编辑页面");
        return "sys/sysDataLog_edit";
    }

    /**
     * 删除系统数据日志信息
     */
    @ApiDoc(
            value = "删除系统数据日志信息",
            description = "根据ID列表删除系统数据日志记录",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @PostMapping(value = UrlCommand.sys_sysDataLog_del)
    public int delete(@Valid HttpRequestByIdListVo idRequestVo) {

        log.info("删除系统数据日志, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除系统数据日志参数错误, ID列表为空");
            throw new RuntimeException("ID列表参数错误");
        }

        int result = sysDataLogService.delSysDataLogByIds(idRequestVo.getIdList());
        log.info("删除系统数据日志完成, 删除数量: {}", result);
        return result;
    }

    /**
     * 导出系统数据日志信息
     */
    @ApiDoc(
            value = "导出系统数据日志信息",
            description = "导出系统数据日志数据到Excel文件",
            request = SysDataLogQueryRequestVo.class,
            response = boolean.class
    )
    @GetMapping(value = UrlCommand.sys_sysDataLog_export)
    public void export(HttpServletResponse response,
                       @Valid SysDataLogQueryRequestVo sysDataLogQueryRequestVo) {

        log.info("导出系统数据日志, 参数: {}", sysDataLogQueryRequestVo);

        SysDataLogRequestDto sysDataLogRequestDto = SysDataLogRequestDto.build()
                .clone(sysDataLogQueryRequestVo);

        Pager<SysDataLogRequestDto> pager = Pager.of();
        pager.toPageSize(sysDataLogQueryRequestVo.getPageSize())
                .toPageNo(sysDataLogQueryRequestVo.getPageNo())
                .toParamObject(sysDataLogRequestDto);

        SysDataLogQueryEnum queryEnum = SysDataLogQueryEnum.DESC_ID;
        PageResult<SysDataLogResponseDto> resultList =
                sysDataLogService.getSysDataLogByNextPage(pager, queryEnum);
        List<SysDataLogResponseDto> data = resultList.getList();

        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysDataLogResponseVo.class,
                    data, "导出系统数据日志信息");
            log.info("导出系统数据日志完成, 数据量: {}", data.size());
        } catch (Exception e) {
            log.error("导出系统数据日志失败", e);
        }
    }

    /**
     * 通过 Excel 导入系统数据日志数据
     */
    @ApiDoc(
            value = "导入系统数据日志数据",
            description = "通过Excel文件导入系统数据日志数据",
            request = MultipartFile.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.sys_sysDataLog_import)
    public boolean importExcel(@RequestParam("file") MultipartFile file) {

        log.info("导入系统数据日志, 文件名: {}", file.getOriginalFilename());

        try {
            InputStream initialStream = file.getInputStream();
            boolean result = sysDataLogService.saveData(initialStream);
            log.info("导入系统数据日志完成, 结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("导入系统数据日志失败", e);
            throw new RuntimeException("导入失败");
        }
    }
}
