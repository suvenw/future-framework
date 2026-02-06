package com.suven.framework.sys.controller;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.sys.dto.enums.SysDictQueryEnum;
import com.suven.framework.sys.dto.request.SysDictRequestDto;
import com.suven.framework.sys.dto.response.SysDictResponseDto;
import com.suven.framework.sys.service.SysDictService;
import com.suven.framework.sys.vo.request.SysDictAddRequestVo;
import com.suven.framework.sys.vo.request.SysDictQueryRequestVo;
import com.suven.framework.sys.vo.response.SysDictResponseVo;
import com.suven.framework.sys.vo.response.SysDictShowResponseVo;
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
 * 后台字典类型表 Web 控制器
 *
 * RequestMapping("/sys/sysDict")
 */
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc = DocumentConst.Sys.SYS_DOC_DES,
        module = "后台字典类型表模块"
)
@Controller
@Slf4j
@Validated
public class SysDictWebController {

    @Autowired
    private SysDictService sysDictService;

    /**
     * 跳转到后台字典类型表主界面
     */
    @GetMapping(value = UrlCommand.sys_sysDict_index)
    public String index() {
        log.info("跳转后台字典类型表主界面");
        return "sys/sysDict_index";
    }

    /**
     * 获取后台字典类型表分页信息
     */
    @ApiDoc(
            value = "获取后台字典类型表分页信息",
            description = "根据查询条件分页获取后台字典类型表列表",
            request = SysDictQueryRequestVo.class,
            response = SysDictShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.sys_sysDict_list)
    public PageResult<SysDictShowResponseVo> pageList(
            @Valid SysDictQueryRequestVo sysDictQueryRequestVo) {

        log.info("分页查询后台字典类型表, 参数: {}", sysDictQueryRequestVo);

        SysDictRequestDto sysDictRequestDto = SysDictRequestDto.build()
                .clone(sysDictQueryRequestVo);

        Pager<SysDictRequestDto> pager = new Pager<>(
                sysDictQueryRequestVo.getPageNo(),
                sysDictQueryRequestVo.getPageSize()
        );
        pager.toParamObject(sysDictRequestDto);

        SysDictQueryEnum queryEnum = SysDictQueryEnum.DESC_ID;
        PageResult<SysDictResponseDto> resultList =
                sysDictService.getSysDictByNextPage(pager, queryEnum);

        if (ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())) {
            log.info("分页查询后台字典类型表完成, 无数据");
            return new PageResult<>();
        }

        PageResult<SysDictShowResponseVo> result =
                resultList.convertBuild(SysDictShowResponseVo.class);
        log.info("分页查询后台字典类型表完成, 总数: {}", result.getTotal());
        return result;
    }

    /**
     * 根据条件查询后台字典类型表信息
     */
    @ApiDoc(
            value = "根据条件查询后台字典类型表信息",
            description = "根据查询条件获取后台字典类型表列表",
            request = SysDictQueryRequestVo.class,
            response = SysDictShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.sys_sysDict_queryList)
    public List<SysDictShowResponseVo> queryList(
            @Valid SysDictQueryRequestVo sysDictQueryRequestVo) {

        log.info("根据条件查询后台字典类型表, 参数: {}", sysDictQueryRequestVo);

        SysDictRequestDto sysDictRequestDto = SysDictRequestDto.build()
                .clone(sysDictQueryRequestVo);

        Pager<SysDictRequestDto> pager = Pager.of();
        pager.toPageSize(sysDictQueryRequestVo.getPageSize())
                .toPageNo(sysDictQueryRequestVo.getPageNo())
                .toParamObject(sysDictRequestDto);

        SysDictQueryEnum queryEnum = SysDictQueryEnum.DESC_ID;
        List<SysDictResponseDto> resultList =
                sysDictService.getSysDictListByQuery(pager, queryEnum);

        if (resultList == null || resultList.isEmpty()) {
            log.info("根据条件查询后台字典类型表完成, 无数据");
            return new ArrayList<>();
        }

        List<SysDictShowResponseVo> listVo =
                IterableConvert.convertList(resultList, SysDictShowResponseVo.class);
        log.info("根据条件查询后台字典类型表完成, 数量: {}", listVo.size());
        return listVo;
    }

    /**
     * 新增后台字典类型表信息
     */
    @ApiDoc(
            value = "新增后台字典类型表信息",
            description = "新增后台字典类型表记录",
            request = SysDictAddRequestVo.class,
            response = Long.class
    )
    @PostMapping(value = UrlCommand.sys_sysDict_add)
    public Long create(@Valid SysDictAddRequestVo sysDictAddRequestVo) {

        log.info("新增后台字典类型表, 参数: {}", sysDictAddRequestVo);

        SysDictRequestDto sysDictRequestDto = SysDictRequestDto.build()
                .clone(sysDictAddRequestVo);

        SysDictResponseDto sysDictResponseDto =
                sysDictService.saveSysDict(sysDictRequestDto);

        if (sysDictResponseDto == null) {
            log.warn("新增后台字典类型表失败");
            throw new RuntimeException("新增失败");
        }

        log.info("新增后台字典类型表成功, ID: {}", sysDictResponseDto.getId());
        return sysDictResponseDto.getId();
    }

    /**
     * 修改后台字典类型表信息
     */
    @ApiDoc(
            value = "修改后台字典类型表信息",
            description = "根据ID修改后台字典类型表记录",
            request = SysDictAddRequestVo.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.sys_sysDict_modify)
    public boolean update(@Valid SysDictAddRequestVo sysDictAddRequestVo) {

        log.info("修改后台字典类型表, 参数: {}", sysDictAddRequestVo);

        if (sysDictAddRequestVo.getId() == null || sysDictAddRequestVo.getId() <= 0) {
            log.warn("修改后台字典类型表参数错误, ID: {}", sysDictAddRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        SysDictRequestDto sysDictRequestDto = SysDictRequestDto.build()
                .clone(sysDictAddRequestVo);

        boolean result = sysDictService.updateSysDict(sysDictRequestDto);
        log.info("修改后台字典类型表完成, ID: {}, 结果: {}", sysDictAddRequestVo.getId(), result);
        return result;
    }

    /**
     * 查看后台字典类型表详情
     */
    @ApiDoc(
            value = "查看后台字典类型表信息",
            description = "根据ID获取后台字典类型表详细信息",
            request = HttpRequestByIdVo.class,
            response = SysDictShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.sys_sysDict_detail)
    public SysDictShowResponseVo info(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("查询后台字典类型表详情, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询后台字典类型表详情参数错误, ID: {}", idRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        SysDictResponseDto sysDictResponseDto =
                sysDictService.getSysDictById(idRequestVo.getId());

        if (sysDictResponseDto == null) {
            log.warn("后台字典类型表不存在, ID: {}", idRequestVo.getId());
            throw new RuntimeException("数据不存在");
        }

        SysDictShowResponseVo vo = SysDictShowResponseVo.build()
                .clone(sysDictResponseDto);
        log.info("查询后台字典类型表详情成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 跳转后台字典类型表编辑页面（加载详情数据）
     */
    @ApiDoc(
            value = "跳转后台字典类型表编辑页面",
            description = "获取后台字典类型表编辑页面数据",
            request = HttpRequestByIdVo.class,
            response = SysDictShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.sys_sysDict_edit)
    public SysDictShowResponseVo edit(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("跳转后台字典类型表编辑页面, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("跳转编辑页面参数错误, ID: {}", idRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        SysDictResponseDto sysDictResponseDto =
                sysDictService.getSysDictById(idRequestVo.getId());

        if (sysDictResponseDto == null) {
            log.warn("后台字典类型表不存在, ID: {}", idRequestVo.getId());
            throw new RuntimeException("数据不存在");
        }

        SysDictShowResponseVo vo = SysDictShowResponseVo.build()
                .clone(sysDictResponseDto);
        log.info("跳转后台字典类型表编辑页面成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 跳转后台字典类型表新增编辑界面
     */
    @GetMapping(value = UrlCommand.sys_sysDict_newInfo)
    public String newInfo(ModelMap modelMap) {
        log.info("跳转后台字典类型表新增编辑页面");
        return "sys/sysDict_edit";
    }

    /**
     * 删除后台字典类型表信息
     */
    @ApiDoc(
            value = "删除后台字典类型表信息",
            description = "根据ID列表删除后台字典类型表记录",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @PostMapping(value = UrlCommand.sys_sysDict_del)
    public int delete(@Valid HttpRequestByIdListVo idRequestVo) {

        log.info("删除后台字典类型表, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除后台字典类型表参数错误, ID列表为空");
            throw new RuntimeException("ID列表参数错误");
        }

        int result = sysDictService.delSysDictByIds(idRequestVo.getIdList());
        log.info("删除后台字典类型表完成, 删除数量: {}", result);
        return result;
    }

    /**
     * 导出后台字典类型表信息
     */
    @ApiDoc(
            value = "导出后台字典类型表信息",
            description = "导出后台字典类型表数据到Excel文件",
            request = SysDictQueryRequestVo.class,
            response = boolean.class
    )
    @GetMapping(value = UrlCommand.sys_sysDict_export)
    public void export(HttpServletResponse response,
                       @Valid SysDictQueryRequestVo sysDictQueryRequestVo) {

        log.info("导出后台字典类型表, 参数: {}", sysDictQueryRequestVo);

        SysDictRequestDto sysDictRequestDto = SysDictRequestDto.build()
                .clone(sysDictQueryRequestVo);

        Pager<SysDictRequestDto> pager = Pager.of();
        pager.toPageSize(sysDictQueryRequestVo.getPageSize())
                .toPageNo(sysDictQueryRequestVo.getPageNo())
                .toParamObject(sysDictRequestDto);

        SysDictQueryEnum queryEnum = SysDictQueryEnum.DESC_ID;
        PageResult<SysDictResponseDto> resultList =
                sysDictService.getSysDictByNextPage(pager, queryEnum);
        List<SysDictResponseDto> data = resultList.getList();

        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysDictResponseVo.class,
                    data, "导出后台字典类型表信息");
            log.info("导出后台字典类型表完成, 数据量: {}", data.size());
        } catch (Exception e) {
            log.error("导出后台字典类型表失败", e);
        }
    }

    /**
     * 通过 Excel 导入后台字典类型表数据
     */
    @ApiDoc(
            value = "导入后台字典类型表数据",
            description = "通过Excel文件导入后台字典类型表数据",
            request = MultipartFile.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.sys_sysDict_import)
    public boolean importExcel(@RequestParam("file") MultipartFile file) {

        log.info("导入后台字典类型表, 文件名: {}", file.getOriginalFilename());

        try {
            InputStream initialStream = file.getInputStream();
            boolean result = sysDictService.saveData(initialStream);
            log.info("导入后台字典类型表完成, 结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("导入后台字典类型表失败", e);
            throw new RuntimeException("导入失败");
        }
    }
}
