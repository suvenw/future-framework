package com.suven.framework.sys.controller;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.sys.dto.request.SysRoleRequestDto;
import com.suven.framework.sys.dto.response.SysRoleResponseDto;
import com.suven.framework.sys.dto.enums.SysRoleQueryEnum;
import com.suven.framework.sys.service.SysRoleService;
import com.suven.framework.sys.vo.request.SysRoleQueryRequestVo;
import com.suven.framework.sys.vo.request.SysRoleAddRequestVo;
import com.suven.framework.sys.vo.response.SysRoleShowResponseVo;
import com.suven.framework.sys.vo.response.SysRoleResponseVo;
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

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: SysRoleWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:43
 * @version 版本: v2.0.0
 * <pre>
 *
 *  Description: 角色表接口控制器
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
 * RequestMapping("/sys/sysRole")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc = DocumentConst.Sys.SYS_DOC_DES,
        module = "角色表模块"
)
@Controller
@Slf4j
@Validated
public class SysRoleWebController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * URL 命令常量接口
     * 规范：全大写，下划线分隔，描述性名称
     */
    public interface UrlCommand {
        String SYS_ROLE_INDEX = "/sys/sysRole/index";
        String SYS_ROLE_PAGE_LIST = "/sys/sysRole/pageList";
        String SYS_ROLE_LIST = "/sys/sysRole/list";
        String SYS_ROLE_QUERY_LIST = "/sys/sysRole/queryList";
        String SYS_ROLE_INFO = "/sys/sysRole/info";
        String SYS_ROLE_CREATE = "/sys/sysRole/create";
        String SYS_ROLE_UPDATE = "/sys/sysRole/update";
        String SYS_ROLE_DELETE = "/sys/sysRole/delete";
        String SYS_ROLE_EDIT = "/sys/sysRole/edit";
        String SYS_ROLE_NEW_INFO = "/sys/sysRole/newInfo";
        String SYS_ROLE_EXPORT = "/sys/sysRole/export";
        String SYS_ROLE_IMPORT = "/sys/sysRole/import";
    }

    /**
     * 跳转到角色表主界面
     *
     * @return 字符串url
     * @author suven
     * date 2022-02-28 16:10:43
     */
    @GetMapping(value = UrlCommand.SYS_ROLE_INDEX)
    public String index() {
        log.info("跳转角色表主界面");
        return "sys/sysRole_index";
    }

    /**
     * 分页获取角色表信息
     *
     * @param sysRoleQueryRequestVo 查询请求参数
     * @return PageResult<SysRoleShowResponseVo> 分页响应结果
     * @author suven
     * date 2022-02-28 16:10:43
     *
     * 接口规则：
     * 1. 分页参数必须使用 Pager 包装
     * 2. 必须指定排序枚举
     * 3. 必须记录操作日志
     */
    @ApiDoc(
            value = "分页获取角色表信息",
            description = "根据查询条件分页获取角色表列表",
            request = SysRoleQueryRequestVo.class,
            response = SysRoleShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.SYS_ROLE_PAGE_LIST)
    public PageResult<SysRoleShowResponseVo> pageList(
            @Valid SysRoleQueryRequestVo sysRoleQueryRequestVo) {

        log.info("分页查询角色表, 参数: {}", sysRoleQueryRequestVo);

        SysRoleRequestDto sysRoleRequestDto = SysRoleRequestDto.build().clone(sysRoleQueryRequestVo);

        Pager<SysRoleRequestDto> pager = new Pager<>(
                sysRoleQueryRequestVo.getPageNo(),
                sysRoleQueryRequestVo.getPageSize()
        );
        pager.toPageSize(sysRoleQueryRequestVo.getPageSize())
                .toPageNo(sysRoleQueryRequestVo.getPageNo())
                .toParamObject(sysRoleRequestDto);

        SysRoleQueryEnum queryEnum = SysRoleQueryEnum.DESC_ID;
        PageResult<SysRoleResponseDto> resultList = sysRoleService.getSysRoleByNextPage(pager, queryEnum);

        if (resultList == null || resultList.getList().isEmpty()) {
            log.info("分页查询角色表完成, 无数据");
            return new PageResult<>();
        }

        PageResult<SysRoleShowResponseVo> result = resultList.convertBuild(SysRoleShowResponseVo.class);
        log.info("分页查询角色表完成, 总数: {}", result.getTotal());
        return result;
    }

/**
     * 根据条件查询角色表信息
     *
     * @param sysRoleQueryRequestVo 查询请求参数
     * @return List<SysRoleShowResponseVo> 响应结果列表
     * @author suven
     * date 2022-02-28 16:10:43
     */
    @ApiDoc(
            value = "根据条件查询角色表信息",
            description = "根据查询条件获取角色表列表",
            request = SysRoleQueryRequestVo.class,
            response = SysRoleShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.SYS_ROLE_QUERY_LIST)
    public List<SysRoleShowResponseVo> queryList(
            @Valid SysRoleQueryRequestVo sysRoleQueryRequestVo) {

        log.info("根据条件查询角色表, 参数: {}", sysRoleQueryRequestVo);

        SysRoleRequestDto sysRoleRequestDto = SysRoleRequestDto.build().clone(sysRoleQueryRequestVo);

        Pager<SysRoleRequestDto> pager = Pager.of();
        pager.toPageSize(sysRoleQueryRequestVo.getPageSize())
                .toPageNo(sysRoleQueryRequestVo.getPageNo())
                .toParamObject(sysRoleRequestDto);

        SysRoleQueryEnum queryEnum = SysRoleQueryEnum.DESC_ID;
        List<SysRoleResponseDto> resultList = sysRoleService.getSysRoleListByQuery(pager, queryEnum);

        if (resultList == null || resultList.isEmpty()) {
            log.info("根据条件查询角色表完成, 无数据");
            return new ArrayList<>();
        }

        List<SysRoleShowResponseVo> listVo = IterableConvert.convertList(resultList, SysRoleShowResponseVo.class);
        log.info("根据条件查询角色表完成, 数量: {}", listVo.size());
        return listVo;
    }

    /**
     * 新增角色表信息
     *
     * @param sysRoleAddRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * date 2022-02-28 16:10:43
     */
    @ApiDoc(
            value = "新增角色表信息",
            description = "新增角色表记录",
            request = SysRoleAddRequestVo.class,
            response = Long.class
    )
    @PostMapping(value = UrlCommand.SYS_ROLE_CREATE)
    public Long create(@Valid SysRoleAddRequestVo sysRoleAddRequestVo) {

        log.info("新增角色表, 参数: {}", sysRoleAddRequestVo);

        SysRoleRequestDto sysRoleRequestDto = SysRoleRequestDto.build().clone(sysRoleAddRequestVo);
        SysRoleResponseDto sysRoleResponseDto = sysRoleService.saveSysRole(sysRoleRequestDto);

        if (sysRoleResponseDto == null) {
            log.warn("新增角色表失败");
            throw new RuntimeException("新增失败");
        }

        log.info("新增角色表成功, ID: {}", sysRoleResponseDto.getId());
        return sysRoleResponseDto.getId();
    }

    /**
     * 修改角色表信息
     *
     * @param sysRoleAddRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * date 2022-02-28 16:10:43
     */
    @ApiDoc(
            value = "修改角色表信息",
            description = "根据ID修改角色表记录",
            request = SysRoleAddRequestVo.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.SYS_ROLE_UPDATE)
    public boolean update(@Valid SysRoleAddRequestVo sysRoleAddRequestVo) {

        log.info("修改角色表, 参数: {}", sysRoleAddRequestVo);

        if (sysRoleAddRequestVo.getId() == null || sysRoleAddRequestVo.getId() <= 0) {
            log.warn("修改角色表参数错误, ID: {}", sysRoleAddRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        SysRoleRequestDto sysRoleRequestDto = SysRoleRequestDto.build().clone(sysRoleAddRequestVo);
        boolean result = sysRoleService.updateSysRole(sysRoleRequestDto);
        log.info("修改角色表完成, ID: {}, 结果: {}", sysRoleAddRequestVo.getId(), result);
        return result;
    }

    /**
     * 查看角色表详情
     *
     * @param idRequestVo ID请求参数
     * @return SysRoleShowResponseVo 详情响应结果
     * @author suven
     * date 2022-02-28 16:10:43
     *
     * 接口规则：
     * 1. ID参数必须校验非空
     * 2. 必须处理数据不存在情况
     * 3. 必须记录查询日志
     */
    @ApiDoc(
            value = "查看角色表详情",
            description = "根据ID获取角色表详细信息",
            request = HttpRequestByIdVo.class,
            response = SysRoleShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.SYS_ROLE_INFO)
    public SysRoleShowResponseVo info(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("查询角色表详情, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询角色表详情参数错误, ID: {}", idRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

            SysRoleResponseDto sysRoleResponseDto = sysRoleService.getSysRoleById(idRequestVo.getId());

        if (sysRoleResponseDto == null) {
            log.warn("角色表不存在, ID: {}", idRequestVo.getId());
            throw new RuntimeException("数据不存在");
        }

        SysRoleShowResponseVo vo = SysRoleShowResponseVo.build().clone(sysRoleResponseDto);
        log.info("查询角色表详情成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 跳转角色表编辑页面
     *
     * @param idRequestVo ID请求参数
     * @return SysRoleShowResponseVo 编辑页面数据
     * @author suven
     * date 2022-02-28 16:10:43
     */
    @ApiDoc(
            value = "跳转编辑页面",
            description = "获取角色表编辑页面数据",
            request = HttpRequestByIdVo.class,
            response = SysRoleShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.SYS_ROLE_EDIT)
    public SysRoleShowResponseVo edit(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("跳转编辑页面, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("跳转编辑页面参数错误, ID: {}", idRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

            SysRoleResponseDto sysRoleResponseDto = sysRoleService.getSysRoleById(idRequestVo.getId());

        if (sysRoleResponseDto == null) {
            log.warn("角色表不存在, ID: {}", idRequestVo.getId());
            throw new RuntimeException("数据不存在");
        }

        SysRoleShowResponseVo vo = SysRoleShowResponseVo.build().clone(sysRoleResponseDto);
        log.info("跳转编辑页面成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 跳转角色表新增编辑界面
     *
     * @return 页面路径
     * @author suven
     * date 2022-02-28 16:10:43
     */
    @GetMapping(value = UrlCommand.SYS_ROLE_NEW_INFO)
    public String newInfo(ModelMap modelMap) {
        log.info("跳转新增编辑页面");
        return "sys/sysRole_edit";
    }

    /**
     * 删除角色表信息
     *
     * @param idRequestVo ID请求参数
     * @return int 删除数量
     * @author suven
     * date 2022-02-28 16:10:43
     */
    @ApiDoc(
            value = "删除角色表信息",
            description = "根据ID列表删除角色表记录",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @PostMapping(value = UrlCommand.SYS_ROLE_DELETE)
    public int delete(@Valid HttpRequestByIdListVo idRequestVo) {

        log.info("删除角色表, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除角色表参数错误, ID列表为空");
            throw new RuntimeException("ID列表参数错误");
        }

        int result = sysRoleService.delSysRoleByIds(idRequestVo.getIdList());
        log.info("删除角色表完成, 删除数量: {}", result);
        return result;
    }

    /**
     * 导出角色表信息
     *
     * @param response 响应流
     * @param sysRoleQueryRequestVo 查询参数
     * @author suven
     * date 2022-02-28 16:10:43
     */
    @ApiDoc(
            value = "导出角色表信息",
            description = "导出角色表数据到Excel文件",
            request = SysRoleQueryRequestVo.class,
            response = boolean.class
    )
    @GetMapping(value = UrlCommand.SYS_ROLE_EXPORT)
    public void export(HttpServletResponse response,
                       @Valid SysRoleQueryRequestVo sysRoleQueryRequestVo) {

        log.info("导出角色表, 参数: {}", sysRoleQueryRequestVo);

            SysRoleRequestDto sysRoleRequestDto = SysRoleRequestDto.build().clone(sysRoleQueryRequestVo);

        Pager<SysRoleRequestDto> pager = Pager.of();
        pager.toPageSize(sysRoleQueryRequestVo.getPageSize())
                .toPageNo(sysRoleQueryRequestVo.getPageNo())
                .toParamObject(sysRoleRequestDto);

        SysRoleQueryEnum queryEnum = SysRoleQueryEnum.DESC_ID;
        PageResult<SysRoleResponseDto> resultList = sysRoleService.getSysRoleByNextPage(pager, queryEnum);
        List<SysRoleResponseDto> data = resultList.getList();

        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysRoleResponseVo.class, data, "导出角色表信息");
            log.info("导出角色表完成, 数据量: {}", data.size());
        } catch (Exception e) {
            log.error("导出角色表失败", e);
        }
    }

    /**
     * 通过Excel导入数据
     *
     * @param file 上传文件
     * @return boolean 导入结果
     * @author suven
     * date 2022-02-28 16:10:43
     */
    @ApiDoc(
            value = "导入角色表数据",
            description = "通过Excel文件导入角色表数据",
            request = MultipartFile.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.SYS_ROLE_IMPORT)
    public boolean importExcel(@RequestParam("file") MultipartFile file) {

        log.info("导入角色表, 文件名: {}", file.getOriginalFilename());

        try {
            boolean result = sysRoleService.saveData(file.getInputStream());
            log.info("导入角色表完成, 结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("导入角色表失败", e);
            throw new RuntimeException("导入失败");
        }
    }
}
