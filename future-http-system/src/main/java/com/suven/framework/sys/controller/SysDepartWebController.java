package com.suven.framework.sys.controller;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.core.ObjectTrue;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.sys.dto.enums.SysDepartQueryEnum;
import com.suven.framework.sys.dto.request.SysDepartRequestDto;
import com.suven.framework.sys.dto.response.SysDepartResponseDto;
import com.suven.framework.sys.facade.SysDepartFacade;
import com.suven.framework.sys.service.SysDepartService;
import com.suven.framework.sys.vo.request.SysDepartAddRequestVo;
import com.suven.framework.sys.vo.request.SysDepartQueryRequestVo;
import com.suven.framework.sys.vo.response.SysDepartResponseVo;
import com.suven.framework.sys.vo.response.SysDepartShowResponseVo;
import com.suven.framework.sys.vo.response.SysDepartTreeModelResponseVo;
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
 * 组织机构表 Web 控制器
 *
 * RequestMapping("/sys/sysDepart")
 */
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc = DocumentConst.Sys.SYS_DOC_DES,
        module = "组织机构表模块"
)
@Controller
@Slf4j
@Validated
public class SysDepartWebController {

    @Autowired
    private SysDepartFacade sysDepartFacade;

    @Autowired
    private SysDepartService sysDepartService;

    /**
     * 跳转到组织机构表主界面
     */
    @GetMapping(value = UrlCommand.sys_sysDepart_index)
    public String index() {
        log.info("跳转组织机构表主界面");
        return "sys/sysDepart_index";
    }

    /**
     * 获取组织机构表分页信息
     */
    @ApiDoc(
            value = "获取组织机构表分页信息",
            description = "根据查询条件分页获取组织机构表列表",
            request = SysDepartQueryRequestVo.class,
            response = SysDepartShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.sys_sysDepart_list)
    public PageResult<SysDepartShowResponseVo> pageList(
            @Valid SysDepartQueryRequestVo sysDepartQueryRequestVo) {

        log.info("分页查询组织机构表, 参数: {}", sysDepartQueryRequestVo);

        SysDepartRequestDto sysDepartRequestDto = SysDepartRequestDto.build()
                .clone(sysDepartQueryRequestVo);

        Pager<SysDepartRequestDto> pager = Pager.of();
        pager.toPageSize(sysDepartQueryRequestVo.getPageSize())
                .toPageNo(sysDepartQueryRequestVo.getPageNo())
                .toParamObject(sysDepartRequestDto);

        SysDepartQueryEnum queryEnum = SysDepartQueryEnum.DESC_ID;
        PageResult<SysDepartResponseDto> resultList =
                sysDepartService.getSysDepartByNextPage(pager, queryEnum);

        if (ObjectTrue.isEmpty(resultList) || ObjectTrue.isEmpty(resultList.getList())) {
            log.info("分页查询组织机构表完成, 无数据");
            return new PageResult<>();
        }

        PageResult<SysDepartShowResponseVo> result =
                resultList.convertBuild(SysDepartShowResponseVo.class);
        log.info("分页查询组织机构表完成, 总数: {}", result.getTotal());
        return result;
    }

    /**
     * 根据条件查询组织机构表信息
     */
    @ApiDoc(
            value = "根据条件查询组织机构表信息",
            description = "根据查询条件获取组织机构表列表",
            request = SysDepartQueryRequestVo.class,
            response = SysDepartShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.sys_sysDepart_queryList)
    public List<SysDepartShowResponseVo> queryList(
            @Valid SysDepartQueryRequestVo sysDepartQueryRequestVo) {

        log.info("根据条件查询组织机构表, 参数: {}", sysDepartQueryRequestVo);

        SysDepartRequestDto sysDepartRequestDto = SysDepartRequestDto.build()
                .clone(sysDepartQueryRequestVo);

        Pager<SysDepartRequestDto> pager = Pager.of();
        pager.toPageSize(sysDepartQueryRequestVo.getPageSize())
                .toPageNo(sysDepartQueryRequestVo.getPageNo())
                .toParamObject(sysDepartRequestDto);

        SysDepartQueryEnum queryEnum = SysDepartQueryEnum.DEPART_NAME;
        List<SysDepartResponseDto> resultList =
                sysDepartService.getSysDepartListByQuery(pager, queryEnum);

        if (resultList == null || resultList.isEmpty()) {
            log.info("根据条件查询组织机构表完成, 无数据");
            return new ArrayList<>();
        }

        List<SysDepartShowResponseVo> listVo =
                IterableConvert.convertList(resultList, SysDepartShowResponseVo.class);
        log.info("根据条件查询组织机构表完成, 数量: {}", listVo.size());
        return listVo;
    }

    /**
     * 获取组织机构树形结构
     */
    @ApiDoc(
            value = "获取组织机构树形结构",
            description = "获取组织机构树形结构列表",
            request = Object.class,
            response = SysDepartTreeModelResponseVo.class
    )
    @GetMapping(value = UrlCommand.sys_sysDepart_queryTreeList)
    public List<SysDepartTreeModelResponseVo> queryTreeList() {

        log.info("查询组织机构树形结构");
        List<SysDepartTreeModelResponseVo> list = sysDepartFacade.queryTreeList();
        log.info("查询组织机构树形结构完成, 数量: {}", list.size());
        return list;
    }

    /**
     * 新增组织机构表信息
     */
    @ApiDoc(
            value = "新增组织机构表信息",
            description = "新增组织机构表记录",
            request = SysDepartAddRequestVo.class,
            response = Long.class
    )
    @PostMapping(value = UrlCommand.sys_sysDepart_add)
    public Long create(@Valid SysDepartAddRequestVo sysDepartAddRequestVo) {

        log.info("新增组织机构表, 参数: {}", sysDepartAddRequestVo);

        SysDepartRequestDto sysDepartRequestDto = SysDepartRequestDto.build()
                .clone(sysDepartAddRequestVo);

        SysDepartResponseDto sysDepartResponseDto =
                sysDepartService.saveSysDepart(sysDepartRequestDto);

        if (sysDepartResponseDto == null) {
            log.warn("新增组织机构表失败");
            throw new RuntimeException("新增失败");
        }

        log.info("新增组织机构表成功, ID: {}", sysDepartResponseDto.getId());
        return sysDepartResponseDto.getId();
    }

    /**
     * 修改组织机构表信息
     */
    @ApiDoc(
            value = "修改组织机构表信息",
            description = "根据ID修改组织机构表记录",
            request = SysDepartAddRequestVo.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.sys_sysDepart_modify)
    public boolean update(@Valid SysDepartAddRequestVo sysDepartAddRequestVo) {

        log.info("修改组织机构表, 参数: {}", sysDepartAddRequestVo);

        if (sysDepartAddRequestVo.getId() == null || sysDepartAddRequestVo.getId() <= 0) {
            log.warn("修改组织机构表参数错误, ID: {}", sysDepartAddRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        SysDepartRequestDto sysDepartRequestDto = SysDepartRequestDto.build()
                .clone(sysDepartAddRequestVo);

        boolean result = sysDepartService.updateSysDepart(sysDepartRequestDto);
        log.info("修改组织机构表完成, ID: {}, 结果: {}", sysDepartAddRequestVo.getId(), result);
        return result;
    }

    /**
     * 查看组织机构表详情
     */
    @ApiDoc(
            value = "查看组织机构表信息",
            description = "根据ID获取组织机构表详细信息",
            request = HttpRequestByIdVo.class,
            response = SysDepartShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.sys_sysDepart_detail)
    public SysDepartShowResponseVo info(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("查询组织机构表详情, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询组织机构表详情参数错误, ID: {}", idRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        SysDepartResponseDto sysDepartResponseDto =
                sysDepartService.getSysDepartById(idRequestVo.getId());

        if (sysDepartResponseDto == null) {
            log.warn("组织机构表不存在, ID: {}", idRequestVo.getId());
            throw new RuntimeException("数据不存在");
        }

        SysDepartShowResponseVo vo = SysDepartShowResponseVo.build()
                .clone(sysDepartResponseDto);
        log.info("查询组织机构表详情成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 跳转组织机构表编辑页面（加载详情数据）
     */
    @ApiDoc(
            value = "跳转组织机构表编辑页面",
            description = "获取组织机构表编辑页面数据",
            request = HttpRequestByIdVo.class,
            response = SysDepartShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.sys_sysDepart_edit)
    public SysDepartShowResponseVo edit(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("跳转组织机构表编辑页面, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("跳转编辑页面参数错误, ID: {}", idRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        SysDepartResponseDto sysDepartResponseDto =
                sysDepartService.getSysDepartById(idRequestVo.getId());

        if (sysDepartResponseDto == null) {
            log.warn("组织机构表不存在, ID: {}", idRequestVo.getId());
            throw new RuntimeException("数据不存在");
        }

        SysDepartShowResponseVo vo = SysDepartShowResponseVo.build()
                .clone(sysDepartResponseDto);
        log.info("跳转组织机构表编辑页面成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 跳转组织机构表新增编辑界面
     */
    @GetMapping(value = UrlCommand.sys_sysDepart_newInfo)
    public String newInfo(ModelMap modelMap) {
        log.info("跳转组织机构表新增编辑页面");
        return "sys/sysDepart_edit";
    }

    /**
     * 删除组织机构表信息
     */
    @ApiDoc(
            value = "删除组织机构表信息",
            description = "根据ID列表删除组织机构表记录",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @PostMapping(value = UrlCommand.sys_sysDepart_del)
    public int delete(@Valid HttpRequestByIdListVo idRequestVo) {

        log.info("删除组织机构表, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除组织机构表参数错误, ID列表为空");
            throw new RuntimeException("ID列表参数错误");
        }

        int result = sysDepartService.delSysDepartByIds(idRequestVo.getIdList());
        log.info("删除组织机构表完成, 删除数量: {}", result);
        return result;
    }

    /**
     * 导出组织机构表信息
     */
    @ApiDoc(
            value = "导出组织机构表信息",
            description = "导出组织机构表数据到Excel文件",
            request = SysDepartQueryRequestVo.class,
            response = boolean.class
    )
    @GetMapping(value = UrlCommand.sys_sysDepart_export)
    public void export(HttpServletResponse response,
                       @Valid SysDepartQueryRequestVo sysDepartQueryRequestVo) {

        log.info("导出组织机构表, 参数: {}", sysDepartQueryRequestVo);

        SysDepartRequestDto sysDepartRequestDto = SysDepartRequestDto.build()
                .clone(sysDepartQueryRequestVo);

        Pager<SysDepartRequestDto> pager = Pager.of();
        pager.toPageSize(sysDepartQueryRequestVo.getPageSize())
                .toPageNo(sysDepartQueryRequestVo.getPageNo())
                .toParamObject(sysDepartRequestDto);

        SysDepartQueryEnum queryEnum = SysDepartQueryEnum.DESC_ID;
        PageResult<SysDepartResponseDto> resultList =
                sysDepartService.getSysDepartByNextPage(pager, queryEnum);
        List<SysDepartResponseDto> data = resultList.getList();

        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysDepartResponseVo.class,
                    data, "导出组织机构表信息");
            log.info("导出组织机构表完成, 数据量: {}", data.size());
        } catch (Exception e) {
            log.error("导出组织机构表失败", e);
        }
    }

    /**
     * 通过 Excel 导入组织机构表数据
     */
    @ApiDoc(
            value = "导入组织机构表数据",
            description = "通过Excel文件导入组织机构表数据",
            request = MultipartFile.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.sys_sysDepart_import)
    public boolean importExcel(@RequestParam("file") MultipartFile file) {

        log.info("导入组织机构表, 文件名: {}", file.getOriginalFilename());

        try {
            InputStream initialStream = file.getInputStream();
            boolean result = sysDepartService.saveData(initialStream);
            log.info("导入组织机构表完成, 结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("导入组织机构表失败", e);
            throw new RuntimeException("导入失败");
        }
    }
}
