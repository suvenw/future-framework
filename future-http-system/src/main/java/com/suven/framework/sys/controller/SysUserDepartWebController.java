package com.suven.framework.sys.controller;

import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.sys.dto.request.SysUserDepartRequestDto;
import com.suven.framework.sys.dto.response.SysUserDepartResponseDto;
import com.suven.framework.sys.dto.enums.SysUserDepartQueryEnum;
import com.suven.framework.sys.service.SysUserDepartService;
import com.suven.framework.sys.vo.request.SysUserDepartQueryRequestVo;
import com.suven.framework.sys.vo.request.SysUserDepartRequestVo;
import com.suven.framework.sys.vo.response.SysUserDepartShowResponseVo;
import com.suven.framework.sys.vo.response.SysUserDepartResponseVo;
import com.suven.framework.util.excel.ExcelUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: SysUserDepartWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:14:14
 * @version 版本: v2.0.0
 * <pre>
 *
 *  Description: 用户部门关系表接口控制器
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
 *    v2.0.0         suven    2026-02-05   重构:统一接口规范,支持多租户/文件上传下载/异步调用
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysUserDepart")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc = DocumentConst.Sys.SYS_DOC_DES,
        module = "用户部门关系表模块"
)
@Controller
@Slf4j
@Validated
public class SysUserDepartWebController {

    @Autowired
    private SysUserDepartService sysUserDepartService;

    /**
     * URL 命令常量接口
     * 规范：全大写，下划线分隔，描述性名称
     */
    public interface UrlCommand {
        String SYS_USER_DEPART_INDEX = "/sys/sysUserDepart/index";
        String SYS_USER_DEPART_PAGE_LIST = "/sys/sysUserDepart/pageList";
        String SYS_USER_DEPART_LIST = "/sys/sysUserDepart/list";
        String SYS_USER_DEPART_QUERY_LIST = "/sys/sysUserDepart/queryList";
        String SYS_USER_DEPART_INFO = "/sys/sysUserDepart/info";
        String SYS_USER_DEPART_CREATE = "/sys/sysUserDepart/create";
        String SYS_USER_DEPART_UPDATE = "/sys/sysUserDepart/update";
        String SYS_USER_DEPART_DELETE = "/sys/sysUserDepart/delete";
        String SYS_USER_DEPART_EDIT = "/sys/sysUserDepart/edit";
        String SYS_USER_DEPART_NEW_INFO = "/sys/sysUserDepart/newInfo";
        String SYS_USER_DEPART_EXPORT = "/sys/sysUserDepart/export";
        String SYS_USER_DEPART_IMPORT = "/sys/sysUserDepart/import";
    }

    /**
     * 跳转到用户部门关系表主界面
     *
     * @return 字符串url
     * @author suven
     * date 2022-02-28 16:14:14
     */
    @GetMapping(value = UrlCommand.SYS_USER_DEPART_INDEX)
    public String index() {
        log.info("跳转用户部门关系表主界面");
        return "sys/sysUserDepart_index";
    }

    /**
     * 分页获取用户部门关系表信息
     *
     * @param sysUserDepartQueryRequestVo 查询请求参数
     * @return PageResult<SysUserDepartShowResponseVo> 分页响应结果
     * @author suven
     * date 2022-02-28 16:14:14
     *
     * 接口规则：
     * 1. 分页参数必须使用 Pager 包装
     * 2. 必须指定排序枚举
     * 3. 必须记录操作日志
     */
    @ApiDoc(
            value = "分页获取用户部门关系表信息",
            description = "根据查询条件分页获取用户部门关系表列表",
            request = SysUserDepartQueryRequestVo.class,
            response = SysUserDepartShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.SYS_USER_DEPART_PAGE_LIST)
    public PageResult<SysUserDepartShowResponseVo> pageList(
            @Valid SysUserDepartQueryRequestVo sysUserDepartQueryRequestVo) {

        log.info("分页查询用户部门关系表, 参数: {}", sysUserDepartQueryRequestVo);

        SysUserDepartRequestDto sysUserDepartRequestDto = SysUserDepartRequestDto.build()
                .clone(sysUserDepartQueryRequestVo);

        Pager<SysUserDepartRequestDto> pager = new Pager<>(
                sysUserDepartQueryRequestVo.getPageNo(),
                sysUserDepartQueryRequestVo.getPageSize()
        );
        pager.toParamObject(sysUserDepartRequestDto);

        SysUserDepartQueryEnum queryEnum = SysUserDepartQueryEnum.DESC_ID;
        PageResult<SysUserDepartResponseDto> resultList = sysUserDepartService
                .getSysUserDepartByNextPage(pager, queryEnum);

        if (resultList == null || resultList.getList().isEmpty()) {
            log.info("分页查询用户部门关系表完成, 无数据");
            return new PageResult<>();
        }

        PageResult<SysUserDepartShowResponseVo> result = resultList.convertBuild(SysUserDepartShowResponseVo.class);
        log.info("分页查询用户部门关系表完成, 总数: {}", result.getTotal());
        return result;
    }

    /**
     * 根据条件查询用户部门关系表信息
     *
     * @param sysUserDepartQueryRequestVo 查询请求参数
     * @return List<SysUserDepartShowResponseVo> 响应结果列表
     * @author suven
     * date 2022-02-28 16:14:14
     */
    @ApiDoc(
            value = "根据条件查询用户部门关系表信息",
            description = "根据查询条件获取用户部门关系表列表",
            request = SysUserDepartQueryRequestVo.class,
            response = SysUserDepartShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.SYS_USER_DEPART_QUERY_LIST)
    public List<SysUserDepartShowResponseVo> queryList(
            @Valid SysUserDepartQueryRequestVo sysUserDepartQueryRequestVo) {

        log.info("根据条件查询用户部门关系表, 参数: {}", sysUserDepartQueryRequestVo);

        SysUserDepartRequestDto sysUserDepartRequestDto = SysUserDepartRequestDto.build()
                .clone(sysUserDepartQueryRequestVo);

        Pager<SysUserDepartRequestDto> pager = Pager.of();
        pager.toPageSize(sysUserDepartQueryRequestVo.getPageSize())
                .toPageNo(sysUserDepartQueryRequestVo.getPageNo())
                .toParamObject(sysUserDepartRequestDto);

        SysUserDepartQueryEnum queryEnum = SysUserDepartQueryEnum.DESC_ID;
        List<SysUserDepartResponseDto> resultList = sysUserDepartService
                .getSysUserDepartListByQuery(pager, queryEnum);

        if (resultList == null || resultList.isEmpty()) {
            log.info("根据条件查询用户部门关系表完成, 无数据");
            return new ArrayList<>();
        }

        List<SysUserDepartShowResponseVo> listVo = IterableConvert.convertList(
                resultList, SysUserDepartShowResponseVo.class);
        log.info("根据条件查询用户部门关系表完成, 数量: {}", listVo.size());
        return listVo;
    }

    /**
     * 新增用户部门关系表信息
     *
     * @param sysUserDepartRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * date 2022-02-28 16:14:14
     */
    @ApiDoc(
            value = "新增用户部门关系表信息",
            description = "新增用户部门关系表记录",
            request = SysUserDepartRequestVo.class,
            response = Long.class
    )
    @PostMapping(value = UrlCommand.SYS_USER_DEPART_CREATE)
    public Long create(@Valid SysUserDepartRequestVo sysUserDepartRequestVo) {

        log.info("新增用户部门关系表, 参数: {}", sysUserDepartRequestVo);

        SysUserDepartRequestDto sysUserDepartRequestDto = SysUserDepartRequestDto.build()
                .clone(sysUserDepartRequestVo);

        SysUserDepartResponseDto sysUserDepartResponseDto = sysUserDepartService
                .saveSysUserDepart(sysUserDepartRequestDto);

        if (sysUserDepartResponseDto == null) {
            log.warn("新增用户部门关系表失败");
            throw new RuntimeException("新增失败");
        }

        log.info("新增用户部门关系表成功, ID: {}", sysUserDepartResponseDto.getId());
        return sysUserDepartResponseDto.getId();
    }

    /**
     * 修改用户部门关系表信息
     *
     * @param sysUserDepartRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * date 2022-02-28 16:14:14
     */
    @ApiDoc(
            value = "修改用户部门关系表信息",
            description = "根据ID修改用户部门关系表记录",
            request = SysUserDepartRequestVo.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.SYS_USER_DEPART_UPDATE)
    public boolean update(@Valid SysUserDepartRequestVo sysUserDepartRequestVo) {

        log.info("修改用户部门关系表, 参数: {}", sysUserDepartRequestVo);

        if (sysUserDepartRequestVo.getId() == null || sysUserDepartRequestVo.getId() <= 0) {
            log.warn("修改用户部门关系表参数错误, ID: {}", sysUserDepartRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        SysUserDepartRequestDto sysUserDepartRequestDto = SysUserDepartRequestDto.build()
                .clone(sysUserDepartRequestVo);

        boolean result = sysUserDepartService.updateSysUserDepart(sysUserDepartRequestDto);
        log.info("修改用户部门关系表完成, ID: {}, 结果: {}", sysUserDepartRequestVo.getId(), result);
        return result;
    }

    /**
     * 查看用户部门关系表详情
     *
     * @param idRequestVo ID请求参数
     * @return SysUserDepartShowResponseVo 详情响应结果
     * @author suven
     * date 2022-02-28 16:14:14
     *
     * 接口规则：
     * 1. ID参数必须校验非空
     * 2. 必须处理数据不存在情况
     * 3. 必须记录查询日志
     */
    @ApiDoc(
            value = "查看用户部门关系表详情",
            description = "根据ID获取用户部门关系表详细信息",
            request = HttpRequestByIdVo.class,
            response = SysUserDepartShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.SYS_USER_DEPART_INFO)
    public SysUserDepartShowResponseVo info(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("查询用户部门关系表详情, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询用户部门关系表详情参数错误, ID: {}", idRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        SysUserDepartResponseDto sysUserDepartResponseDto = sysUserDepartService
                .getSysUserDepartById(idRequestVo.getId());

        if (sysUserDepartResponseDto == null) {
            log.warn("用户部门关系表不存在, ID: {}", idRequestVo.getId());
            throw new RuntimeException("数据不存在");
        }

        SysUserDepartShowResponseVo vo = SysUserDepartShowResponseVo.build()
                .clone(sysUserDepartResponseDto);
        log.info("查询用户部门关系表详情成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 跳转用户部门关系表编辑页面
     *
     * @param idRequestVo ID请求参数
     * @return SysUserDepartShowResponseVo 编辑页面数据
     * @author suven
     * date 2022-02-28 16:14:14
     */
    @ApiDoc(
            value = "跳转编辑页面",
            description = "获取用户部门关系表编辑页面数据",
            request = HttpRequestByIdVo.class,
            response = SysUserDepartShowResponseVo.class
    )
    @GetMapping(value = UrlCommand.SYS_USER_DEPART_EDIT)
    public SysUserDepartShowResponseVo edit(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("跳转编辑页面, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("跳转编辑页面参数错误, ID: {}", idRequestVo.getId());
            throw new RuntimeException("ID参数错误");
        }

        SysUserDepartResponseDto sysUserDepartResponseDto = sysUserDepartService
                .getSysUserDepartById(idRequestVo.getId());

        if (sysUserDepartResponseDto == null) {
            log.warn("用户部门关系表不存在, ID: {}", idRequestVo.getId());
            throw new RuntimeException("数据不存在");
        }

        SysUserDepartShowResponseVo vo = SysUserDepartShowResponseVo.build()
                .clone(sysUserDepartResponseDto);
        log.info("跳转编辑页面成功, ID: {}", idRequestVo.getId());
        return vo;
    }

    /**
     * 跳转用户部门关系表新增编辑界面
     *
     * @return 页面路径
     * @author suven
     * date 2022-02-28 16:14:14
     */
    @GetMapping(value = UrlCommand.SYS_USER_DEPART_NEW_INFO)
    public String newInfo(ModelMap modelMap) {
        log.info("跳转新增编辑页面");
        return "sys/sysUserDepart_edit";
    }

    /**
     * 删除用户部门关系表信息
     *
     * @param idRequestVo ID请求参数
     * @return int 删除数量
     * @author suven
     * date 2022-02-28 16:14:14
     */
    @ApiDoc(
            value = "删除用户部门关系表信息",
            description = "根据ID列表删除用户部门关系表记录",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @PostMapping(value = UrlCommand.SYS_USER_DEPART_DELETE)
    public int delete(@Valid HttpRequestByIdListVo idRequestVo) {

        log.info("删除用户部门关系表, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除用户部门关系表参数错误, ID列表为空");
            throw new RuntimeException("ID列表参数错误");
        }

        int result = sysUserDepartService.delSysUserDepartByIds(idRequestVo.getIdList());
        log.info("删除用户部门关系表完成, 删除数量: {}", result);
        return result;
    }

    /**
     * 导出用户部门关系表信息
     *
     * @param response 响应流
     * @param sysUserDepartQueryRequestVo 查询参数
     * @author suven
     * date 2022-02-28 16:14:14
     */
    @ApiDoc(
            value = "导出用户部门关系表信息",
            description = "导出用户部门关系表数据到Excel文件",
            request = SysUserDepartQueryRequestVo.class,
            response = boolean.class
    )
    @GetMapping(value = UrlCommand.SYS_USER_DEPART_EXPORT)
    public void export(HttpServletResponse response,
                       @Valid SysUserDepartQueryRequestVo sysUserDepartQueryRequestVo) {

        log.info("导出用户部门关系表, 参数: {}", sysUserDepartQueryRequestVo);

        SysUserDepartRequestDto sysUserDepartRequestDto = SysUserDepartRequestDto.build()
                .clone(sysUserDepartQueryRequestVo);

        Pager<SysUserDepartRequestDto> pager = Pager.of();
        pager.toPageSize(sysUserDepartQueryRequestVo.getPageSize())
                .toPageNo(sysUserDepartQueryRequestVo.getPageNo())
                .toParamObject(sysUserDepartRequestDto);

        SysUserDepartQueryEnum queryEnum = SysUserDepartQueryEnum.DESC_ID;
        PageResult<SysUserDepartResponseDto> resultList = sysUserDepartService
                .getSysUserDepartByNextPage(pager, queryEnum);
        List<SysUserDepartResponseDto> data = resultList.getList();

        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysUserDepartResponseVo.class, data, "导出用户部门关系表信息");
            log.info("导出用户部门关系表完成, 数据量: {}", data.size());
        } catch (Exception e) {
            log.error("导出用户部门关系表失败", e);
        }
    }

    /**
     * 通过Excel导入数据
     *
     * @param file 上传文件
     * @return boolean 导入结果
     * @author suven
     * date 2022-02-28 16:14:14
     */
    @ApiDoc(
            value = "导入用户部门关系表数据",
            description = "通过Excel文件导入用户部门关系表数据",
            request = MultipartFile.class,
            response = boolean.class
    )
    @PostMapping(value = UrlCommand.SYS_USER_DEPART_IMPORT)
    public boolean importExcel(@RequestParam("file") MultipartFile file) {

        log.info("导入用户部门关系表, 文件名: {}", file.getOriginalFilename());

        try {
            boolean result = sysUserDepartService.saveData(file.getInputStream());
            log.info("导入用户部门关系表完成, 结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("导入用户部门关系表失败", e);
            throw new RuntimeException("导入失败");
        }
    }
}
