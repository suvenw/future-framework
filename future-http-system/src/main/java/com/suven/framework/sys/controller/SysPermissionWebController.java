package com.suven.framework.sys.controller;


import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.enums.RequestMethodEnum;
import com.suven.framework.common.api.ExceptionFactory;
import com.suven.framework.common.enums.CodeEnum;
import com.suven.framework.sys.dto.enums.SysPermissionQueryEnum;
import com.suven.framework.sys.dto.request.SysPermissionRequestDto;
import com.suven.framework.sys.dto.response.SysPermissionResponseDto;
import com.suven.framework.sys.facade.SysPermissionFacade;
import com.suven.framework.sys.service.SysPermissionService;
import com.suven.framework.sys.vo.request.SysPermissionAddRequestVo;
import com.suven.framework.sys.vo.request.SysPermissionQueryRequestVo;
import com.suven.framework.sys.vo.response.SysPermissionShowResponseVo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


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
@RestController
@Slf4j
@Validated
@ApiDoc(
    group = DocumentConst.Sys.SYS_DOC_GROUP,
    groupDesc = DocumentConst.Sys.SYS_DOC_DES,
    module = "菜单权限表模块",
    isApp = true
)
public class SysPermissionWebController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysPermissionFacade sysPermissionFacade;

    /**
     * 分页获取菜单权限表信息
     * 根据查询条件分页获取菜单权限表列表
     * @param sysPermissionQueryRequestVo 查询请求参数
     * @return PageResult<SysPermissionShowResponseVo> 分页响应结果
     * @author suven
     * @date 2025-08-18
     *
     * 接口规则：
     * 1. 分页参数必须使用 Pager 包装
     * 2. 必须指定排序枚举
     * 3. 必须记录操作日志
     * 4. 必须进行参数校验
     */
    @ApiDoc(
        value = "分页获取菜单权限表信息",
        description = "根据查询条件分页获取菜单权限表列表",
        request = SysPermissionQueryRequestVo.class,
        response = SysPermissionShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysPermission_list)
    public PageResult<SysPermissionShowResponseVo> pageList(
            @Valid SysPermissionQueryRequestVo sysPermissionQueryRequestVo) {

        log.info("分页查询菜单权限表, 参数: {}", sysPermissionQueryRequestVo);

        Pager<SysPermissionRequestDto> pager = new Pager<>(
                sysPermissionQueryRequestVo.getPageNo(),
                sysPermissionQueryRequestVo.getPageSize()
        );
        SysPermissionRequestDto requestDto = SysPermissionRequestDto.build()
                .clone(sysPermissionQueryRequestVo);
        pager.toParamObject(requestDto);

        PageResult<SysPermissionResponseDto> pageResult = sysPermissionService
                .getSysPermissionByNextPage(pager, SysPermissionQueryEnum.DESC_ID);

        log.info("分页查询菜单权限表完成, 总数: {}", pageResult.getTotal());
        return pageResult.convertBuild(SysPermissionShowResponseVo.class);
    }

    /**
     * 查看菜单权限表详情
     * 根据ID获取菜单权限表详细信息
     * @param idRequestVo ID请求参数
     * @return SysPermissionShowResponseVo 详情响应结果
     * @author suven
     * @date 2025-08-18
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
        response = SysPermissionShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysPermission_detail)
    public SysPermissionShowResponseVo detail(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("查询菜单权限表详情, ID: {}", idRequestVo.getId());

        // 参数校验
        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询菜单权限表详情参数错误, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysPermissionResponseDto responseDto = sysPermissionService
                .getSysPermissionById(idRequestVo.getId());

        if (responseDto == null) {
            log.warn("菜单权限表不存在, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        log.info("查询菜单权限表详情成功, ID: {}", idRequestVo.getId());
        return SysPermissionShowResponseVo.build().clone(responseDto);
    }

    /**
     * 新增菜单权限表信息
     * 创建新的菜单权限表记录
     * @param sysPermissionAddRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "新增菜单权限表信息",
        description = "新增菜单权限表记录",
        request = SysPermissionAddRequestVo.class,
        response = Long.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysPermission_add)
    public Long create(@Valid SysPermissionAddRequestVo sysPermissionAddRequestVo) {

        log.info("新增菜单权限表, 参数: {}", sysPermissionAddRequestVo);

        SysPermissionRequestDto requestDto = SysPermissionRequestDto.build()
                .clone(sysPermissionAddRequestVo);
        SysPermissionResponseDto responseDto = sysPermissionService.saveSysPermission(requestDto);

        if (responseDto == null) {
            log.error("新增菜单权限表失败");
            throw ExceptionFactory.sysException(CodeEnum.SYS_UNKOWNN_FAIL);
        }

        log.info("新增菜单权限表成功, ID: {}", responseDto.getId());
        return responseDto.getId();
    }

    /**
     * 修改菜单权限表信息
     * 根据ID更新菜单权限表信息
     * @param sysPermissionAddRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "修改菜单权限表信息",
        description = "根据ID修改菜单权限表记录",
        request = SysPermissionAddRequestVo.class,
        response = boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysPermission_modify)
    public boolean update(@Valid SysPermissionAddRequestVo sysPermissionAddRequestVo) {

        log.info("修改菜单权限表, 参数: {}", sysPermissionAddRequestVo);

        if (sysPermissionAddRequestVo.getId() == null || sysPermissionAddRequestVo.getId() <= 0) {
            log.warn("修改菜单权限表参数错误, ID: {}", sysPermissionAddRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysPermissionRequestDto requestDto = SysPermissionRequestDto.build()
                .clone(sysPermissionAddRequestVo);
        boolean result = sysPermissionService.updateSysPermission(requestDto);

        log.info("修改菜单权限表完成, ID: {}, 结果: {}", sysPermissionAddRequestVo.getId(), result);
        return result;
    }

    /**
     * 删除菜单权限表信息
     * 根据ID列表批量删除菜单权限表记录
     * @param idRequestVo ID列表请求参数
     * @return Integer 删除数量
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "删除菜单权限表信息",
        description = "根据ID列表删除菜单权限表记录",
        request = HttpRequestByIdListVo.class,
        response = Integer.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysPermission_del)
    public Integer delete(@Valid HttpRequestByIdListVo idRequestVo) {

        log.info("删除菜单权限表, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除菜单权限表参数错误, ID列表为空");
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        int result = sysPermissionService.delSysPermissionByIds(idRequestVo.getIdList());
        log.info("删除菜单权限表完成, 删除数量: {}", result);
        return result;
    }

}
