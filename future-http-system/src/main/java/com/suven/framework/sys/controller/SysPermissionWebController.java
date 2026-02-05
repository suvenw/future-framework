package com.suven.framework.sys.controller;

import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.http.handler.OutputSystem;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.sys.dto.enums.SysPermissionDataRuleQueryEnum;
import com.suven.framework.sys.dto.enums.SysPermissionQueryEnum;
import com.suven.framework.sys.dto.request.SysPermissionRequestDto;
import com.suven.framework.sys.dto.response.SysPermissionDataRuleResponseDto;
import com.suven.framework.sys.dto.response.SysPermissionResponseDto;
import com.suven.framework.sys.facade.SysPermissionFacade;
import com.suven.framework.sys.facade.SysRolePermissionFacade;
import com.suven.framework.sys.service.SysPermissionDataRuleService;
import com.suven.framework.sys.service.SysPermissionService;
import com.suven.framework.sys.service.SysRolePermissionService;
import com.suven.framework.sys.vo.request.SysPermissionAddRequestVo;
import com.suven.framework.sys.vo.request.SysPermissionDataRuleIdRequestVo;
import com.suven.framework.sys.vo.request.SysPermissionQueryRequestVo;
import com.suven.framework.sys.vo.request.SysPermissionRequestVo;
import com.suven.framework.sys.vo.request.SysRolePermissionSaveRequestVo;
import com.suven.framework.sys.vo.request.SysRoleQueryRequestVo;
import com.suven.framework.sys.vo.request.TokenRequestVo;
import com.suven.framework.sys.vo.response.SysPermissionResponseVo;
import com.suven.framework.sys.vo.response.SysPermissionShowResponseVo;
import com.suven.framework.sys.vo.response.SysPermissionTreeResponseVo;
import com.suven.framework.sys.vo.response.SysRoleShowResponseVo;
import com.suven.framework.util.excel.ExcelUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;
import java.util.Map;

/**
 * ClassName: SysPermissionWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:30
 * @version 版本: v2.0.0
 * <pre>
 *
 *  Description: 菜单权限表接口控制器
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
 * RequestMapping("/sys/sysPermission")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc = DocumentConst.Sys.SYS_DOC_DES,
        module = "菜单权限表模块"
)
@Controller
@Slf4j
@Validated
public class SysPermissionWebController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysPermissionFacade sysPermissionFacade;

    @Autowired
    private SysPermissionDataRuleService sysPermissionDataRuleService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     * URL 命令常量接口
     * 规范：全大写，下划线分隔，描述性名称
     */
    public interface UrlCommand {
        String SYS_PERMISSION_INDEX = "/sys/sysPermission/index";
        String SYS_PERMISSION_PAGE_LIST = "/sys/sysPermission/pageList";
        String SYS_PERMISSION_LIST = "/sys/sysPermission/list";
        String SYS_PERMISSION_QUERY_LIST = "/sys/sysPermission/queryList";
        String SYS_PERMISSION_INFO = "/sys/sysPermission/info";
        String SYS_PERMISSION_CREATE = "/sys/sysPermission/create";
        String SYS_PERMISSION_UPDATE = "/sys/sysPermission/update";
        String SYS_PERMISSION_DELETE = "/sys/sysPermission/delete";
        String SYS_PERMISSION_EDIT = "/sys/sysPermission/edit";
        String SYS_PERMISSION_NEW_INFO = "/sys/sysPermission/newInfo";
        String SYS_PERMISSION_EXPORT = "/sys/sysPermission/export";
        String SYS_PERMISSION_IMPORT = "/sys/sysPermission/import";
    }

    /**
     * 跳转到菜单权限表主界面
     *
     * @return 字符串url
     * @author suven
     * date 2022-02-28 16:10:30
     */
    @GetMapping(value = UrlCommand.SYS_PERMISSION_INDEX)
    public String index() {
        log.info("跳转菜单权限表主界面");
        return "sys/sysPermission_index";
    }

    /**
     * 分页获取菜单权限表信息
     *
     * @param sysPermissionQueryRequestVo 查询请求参数
     * @return PageResult<SysPermissionShowResponseVo> 分页响应结果
     * @author suven
     * date 2022-02-28 16:10:30
     *
     * 接口规则：
     * 1. 分页参数必须使用 Pager 包装
     * 2. 必须指定排序枚举
     * 3. 必须记录操作日志
     */
    @ApiDoc(
            value = "分页获取菜单权限表信息",
            description = "根据查询条件分页获取菜单权限表列表",
            request = SysPermissionQueryRequestVo.class,
            response = SysPermissionShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.SYS_PERMISSION_PAGE_LIST)
    public PageResult<SysPermissionShowResponseVo> pageList(
            @Valid SysPermissionQueryRequestVo sysPermissionQueryRequestVo) {

        log.info("分页查询菜单权限表, 参数: {}", sysPermissionQueryRequestVo);

        SysPermissionRequestDto sysPermissionRequestDto = SysPermissionRequestDto.build()
                .clone(sysPermissionQueryRequestVo);

        Pager<SysPermissionRequestDto> pager = new Pager<>(
                sysPermissionQueryRequestVo.getPageNo(),
                sysPermissionQueryRequestVo.getPageSize()
        );
        pager.toPageSize(sysPermissionQueryRequestVo.getPageSize())
                .toPageNo(sysPermissionQueryRequestVo.getPageNo())
                .toParamObject(sysPermissionRequestDto);

        SysPermissionQueryEnum queryEnum = SysPermissionQueryEnum.DESC_ID;
        PageResult<SysPermissionResponseDto> resultList = sysPermissionService
                .getSysPermissionByNextPage(pager, queryEnum);

        if (resultList == null || resultList.getList().isEmpty()) {
            log.info("分页查询菜单权限表完成, 无数据");
            return new PageResult<>();
        }

        PageResult<SysPermissionShowResponseVo> result = resultList
                .convertBuild(SysPermissionShowResponseVo.class);
        log.info("分页查询菜单权限表完成, 总数: {}", result.getTotal());
        return result;
    }

    /**
     * 根据条件查询菜单权限表信息
     *
     * @param sysPermissionQueryRequestVo 查询请求参数
     * @return List<SysPermissionShowResponseVo> 响应结果列表
     * @author suven
     * date 2022-02-28 16:10:30
     */
    @ApiDoc(
            value = "根据条件查询菜单权限表信息",
            description = "根据查询条件获取菜单权限表列表",
            request = SysPermissionQueryRequestVo.class,
            response = SysPermissionShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.SYS_PERMISSION_QUERY_LIST)
    public List<SysPermissionShowResponseVo> queryList(
            @Valid SysPermissionQueryRequestVo sysPermissionQueryRequestVo) {

        log.info("根据条件查询菜单权限表, 参数: {}", sysPermissionQueryRequestVo);

        SysPermissionRequestDto sysPermissionRequestDto = SysPermissionRequestDto.build()
                .clone(sysPermissionQueryRequestVo);

        Pager<SysPermissionRequestDto> pager = Pager.of();
        pager.toPageSize(sysPermissionQueryRequestVo.getPageSize())
                .toPageNo(sysPermissionQueryRequestVo.getPageNo())
                .toParamObject(sysPermissionRequestDto);

        SysPermissionQueryEnum queryEnum = SysPermissionQueryEnum.DESC_ID;
        List<SysPermissionResponseDto> resultList = sysPermissionService
                .getSysPermissionListByQuery(pager, queryEnum);

        if (resultList == null || resultList.isEmpty()) {
            log.info("根据条件查询菜单权限表完成, 无数据");
            return new java.util.ArrayList<>();
        }

        List<SysPermissionShowResponseVo> listVo = IterableConvert.convertList(
                resultList, SysPermissionShowResponseVo.class);
        log.info("根据条件查询菜单权限表完成, 数量: {}", listVo.size());
        return listVo;
    }

    /**
     * 新增菜单权限表信息
     *
     * @param sysPermissionAddRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * date 2022-02-28 16:10:30
     */
    @ApiDoc(
            value = "新增菜单权限表信息",
            description = "新增菜单权限表记录",
            request = SysPermissionAddRequestVo.class,
            response = Long.class
    )
    @PostMapping(value = UrlCommand.SYS_PERMISSION_CREATE)
    public Long create(@Valid SysPermissionAddRequestVo sysPermissionAddRequestVo) {

        log.info("新增菜单权限表, 参数: {}", sysPermissionAddRequestVo);

        SysPermissionRequestDto sysPermissionRequestDto = SysPermissionRequestDto.build()
                .clone(sysPermissionAddRequestVo);
        SysPermissionResponseDto sysPermissionResponseDto = sysPermissionService
                .saveSysPermission(sysPermissionRequestDto);

        if (sysPermissionResponseDto == null) {
            log.warn("新增菜单权限表失败");
            throw new RuntimeException("新增失败");
        }

        log.info("新增菜单权限表成功, ID: {}", sysPermissionResponseDto.getId());
        return sysPermissionResponseDto.getId();
    }

    /**
     * 修改菜单权限表信息
     *
     * @param sysPermissionAddRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * date 2022-02-28 16:10:30
     */
    @ApiDoc(
            value = "修改菜单权限表信息",
            description = "根据ID修改菜单权限表记录",
            request = SysPermissionAddRequestVo.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.SYS_PERMISSION_UPDATE)
    public boolean update(@Valid SysPermissionAddRequestVo sysPermissionAddRequestVo) {

        log.info("修改菜单权限表, 参数: {}", sysPermissionAddRequestVo);

        if (sysPermissionAddRequestVo.getId() == null || sysPermissionAddRequestVo.getId() <= 0) {
            log.warn("修改菜单权限表参数错误, ID: {}", sysPermissionAddRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        SysPermissionRequestDto sysPermissionRequestDto = SysPermissionRequestDto.build()
                .clone(sysPermissionAddRequestVo);
        boolean result = sysPermissionService.updateSysPermission(sysPermissionRequestDto);
        log.info("修改菜单权限表完成, ID: {}, 结果: {}", sysPermissionAddRequestVo.getId(), result);
        return result;
    }

    /**
     * 查看菜单权限表详情
     *
     * @param idRequestVo ID请求参数
     * @return SysPermissionShowResponseVo 详情响应结果
     * @author suven
     * date 2022-02-28 16:10:30
     *
     * 接口规则：
     * 1. ID参数必须校验非空
     * 2. 必须处理数据不存在情况
     * 3. 必须记录查询日志
     */
    @ApiDoc(
            value = "查看菜单权限表详情",
            description = "根据ID获取菜单权限表详细信息",
            request = HttpRequestByIdVo.class,
            response = SysPermissionShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.SYS_PERMISSION_INFO)
    public SysPermissionShowResponseVo info(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("查询菜单权限表详情, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询菜单权限表详情参数错误, ID: {}", idRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        SysPermissionResponseDto sysPermissionResponseDto = sysPermissionService
                .getSysPermissionById(idRequestVo.getId());

        if (sysPermissionResponseDto == null) {
            log.warn("菜单权限表不存在, ID: {}", idRequestVo.getId());
            throw new RuntimeException("数据不存在");
        }

        SysPermissionShowResponseVo vo = SysPermissionShowResponseVo.build()
                .clone(sysPermissionResponseDto);
        log.info("查询菜单权限表详情成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 跳转菜单权限表编辑页面
     *
     * @param idRequestVo ID请求参数
     * @return SysPermissionShowResponseVo 编辑页面数据
     * @author suven
     * date 2022-02-28 16:10:30
     */
    @ApiDoc(
            value = "跳转编辑页面",
            description = "获取菜单权限表编辑页面数据",
            request = HttpRequestByIdVo.class,
            response = SysPermissionShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.SYS_PERMISSION_EDIT)
    public SysPermissionShowResponseVo edit(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("跳转编辑页面, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("跳转编辑页面参数错误, ID: {}", idRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        SysPermissionResponseDto sysPermissionResponseDto = sysPermissionService
                .getSysPermissionById(idRequestVo.getId());

        if (sysPermissionResponseDto == null) {
            log.warn("菜单权限表不存在, ID: {}", idRequestVo.getId());
            throw new RuntimeException("数据不存在");
        }

        SysPermissionShowResponseVo vo = SysPermissionShowResponseVo.build()
                .clone(sysPermissionResponseDto);
        log.info("跳转编辑页面成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 跳转菜单权限表新增编辑界面
     *
     * @return 页面路径
     * @author suven
     * date 2022-02-28 16:10:30
     */
    @GetMapping(value = UrlCommand.SYS_PERMISSION_NEW_INFO)
    public String newInfo(ModelMap modelMap) {
        log.info("跳转新增编辑页面");
        return "sys/sysPermission_edit";
    }

    /**
     * 删除菜单权限表信息
     *
     * @param idRequestVo ID请求参数
     * @return int 删除数量
     * @author suven
     * date 2022-02-28 16:10:30
     */
    @ApiDoc(
            value = "删除菜单权限表信息",
            description = "根据ID列表删除菜单权限表记录",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @PostMapping(value = UrlCommand.SYS_PERMISSION_DELETE)
    public int delete(@Valid HttpRequestByIdListVo idRequestVo) {

        log.info("删除菜单权限表, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除菜单权限表参数错误, ID列表为空");
            throw new RuntimeException("ID列表参数错误");
        }

        int result = sysPermissionService.delSysPermissionByIds(idRequestVo.getIdList());
        log.info("删除菜单权限表完成, 删除数量: {}", result);
        return result;
    }

    /**
     * 导出菜单权限表信息
     *
     * @param response 响应流
     * @param sysPermissionQueryRequestVo 查询参数
     * @author suven
     * date 2022-02-28 16:10:30
     */
    @ApiDoc(
            value = "导出菜单权限表信息",
            description = "导出菜单权限表数据到Excel文件",
            request = SysPermissionQueryRequestVo.class,
            response = boolean.class
    )
    @GetMapping(value = UrlCommand.SYS_PERMISSION_EXPORT)
    public void export(HttpServletResponse response,
                       @Valid SysPermissionQueryRequestVo sysPermissionQueryRequestVo) {

        log.info("导出菜单权限表, 参数: {}", sysPermissionQueryRequestVo);

        SysPermissionRequestDto sysPermissionRequestDto = SysPermissionRequestDto.build()
                .clone(sysPermissionQueryRequestVo);

        Pager<SysPermissionRequestDto> pager = Pager.of();
        pager.toPageSize(sysPermissionQueryRequestVo.getPageSize())
                .toPageNo(sysPermissionQueryRequestVo.getPageNo())
                .toParamObject(sysPermissionRequestDto);

        SysPermissionQueryEnum queryEnum = SysPermissionQueryEnum.DESC_ID;
        PageResult<SysPermissionResponseDto> resultList = sysPermissionService
                .getSysPermissionByNextPage(pager, queryEnum);
        List<SysPermissionResponseDto> data = resultList.getList();

        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysPermissionResponseVo.class, data, "导出菜单权限表信息");
            log.info("导出菜单权限表完成, 数据量: {}", data.size());
        } catch (Exception e) {
            log.error("导出菜单权限表失败", e);
        }
    }

    /**
     * 通过Excel导入数据
     *
     * @param file 上传文件
     * @return boolean 导入结果
     * @author suven
     * date 2022-02-28 16:10:30
     */
    @ApiDoc(
            value = "导入菜单权限表数据",
            description = "通过Excel文件导入菜单权限表数据",
            request = MultipartFile.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.SYS_PERMISSION_IMPORT)
    public boolean importExcel(@RequestParam("file") MultipartFile file) {

        log.info("导入菜单权限表, 文件名: {}", file.getOriginalFilename());

        try {
            boolean result = sysPermissionService.saveData(file.getInputStream());
            log.info("导入菜单权限表完成, 结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("导入菜单权限表失败", e);
            throw new RuntimeException("导入失败");
        }
    }
}
