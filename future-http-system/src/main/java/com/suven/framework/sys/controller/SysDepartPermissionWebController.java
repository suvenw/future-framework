package com.suven.framework.sys.controller;



import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
// import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.validation.annotation.Validated;
import lombok.extern.slf4j.Slf4j;


import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.handler.OutputSystem;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.util.excel.ExcelUtils;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.exception.ExceptionFactory;
import com.suven.framework.http.api.RequestMethodEnum;


import com.suven.framework.sys.service.SysDepartPermissionService;
import com.suven.framework.sys.vo.request.SysDepartPermissionQueryRequestVo;
import com.suven.framework.sys.vo.request.SysDepartPermissionAddRequestVo;
import com.suven.framework.sys.vo.response.SysDepartPermissionShowResponseVo;
import com.suven.framework.sys.vo.response.SysDepartPermissionResponseVo;

import com.suven.framework.sys.dto.request.SysDepartPermissionRequestDto;
import com.suven.framework.sys.dto.response.SysDepartPermissionResponseDto;
import com.suven.framework.sys.dto.enums.SysDepartPermissionQueryEnum;


/**
 * ClassName: SysDepartPermissionWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:14:27
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门权限表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysDepartPermission")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@RestController
@Slf4j
@Validated
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
    groupDesc = DocumentConst.Sys.SYS_DOC_DES,
    module = "部门权限表模块",
    isApp = true
)
public class SysDepartPermissionWebController {






    @Autowired
    private SysDepartPermissionService  sysDepartPermissionService;



    /**
     * 分页获取部门权限表信息
     * 根据查询条件分页获取部门权限表列表
     * @param sysDepartPermissionQueryRequestVo 查询请求参数
     * @return PageResult<SysDepartPermissionShowResponseVo> 分页响应结果
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
        value = "分页获取部门权限表信息",
        description = "根据条件分页查询部门权限表数据",
            request = SysDepartPermissionQueryRequestVo.class,
        response = SysDepartPermissionShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysDepartPermission_list)
    public PageResult<SysDepartPermissionShowResponseVo> pageList(@Validated SysDepartPermissionQueryRequestVo sysDepartPermissionQueryRequestVo) {

        log.info("分页查询部门权限表, 参数: {}", sysDepartPermissionQueryRequestVo);

        // 参数校验

        Pager<SysDepartPermissionRequestDto> pager = new Pager<>(
            sysDepartPermissionQueryRequestVo.getPageNo(),
            sysDepartPermissionQueryRequestVo.getPageSize()
        );
        SysDepartPermissionRequestDto requestDto = SysDepartPermissionRequestDto.build().clone(sysDepartPermissionQueryRequestVo);
        pager.toParamObject(requestDto);

        PageResult<SysDepartPermissionResponseDto> pageResult = sysDepartPermissionService
            .getSysDepartPermissionByNextPage(pager, SysDepartPermissionQueryEnum.DESC_ID);

        log.info("分页查询部门权限表完成, 总数: {}", pageResult.getTotal());
        return pageResult.convertBuild(SysDepartPermissionShowResponseVo.class);
    }




    /**
     * 新增部门权限表信息
     * 创建新的部门权限表记录
     * @param sysDepartPermissionAddRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * @date 2025-08-18
     *
     * 接口规则：
     * 1. 必须进行参数校验
     * 2. 必须记录操作日志
     * 3. 必须处理保存失败情况
     */
    @ApiDoc(
            value = "新增部门权限表信息",
        description = "创建新的部门权限表记录",
            request = SysDepartPermissionAddRequestVo.class,
        response = Long.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysDepartPermission_add)
    public Long create(@Validated SysDepartPermissionAddRequestVo sysDepartPermissionAddRequestVo) {

        log.info("新增部门权限表信息, 参数: {}", sysDepartPermissionAddRequestVo);

        SysDepartPermissionRequestDto requestDto = SysDepartPermissionRequestDto.build().clone(sysDepartPermissionAddRequestVo);

        SysDepartPermissionResponseDto responseDto = sysDepartPermissionService.saveSysDepartPermission(requestDto);

        if (responseDto == null) {
            log.error("新增部门权限表信息失败");
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
        }

        log.info("新增部门权限表信息成功, ID: {}", responseDto.getId());
        return responseDto.getId();
    }
    /**
     * 修改部门权限表信息
     * 根据ID更新部门权限表信息
     * @param sysDepartPermissionAddRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * @date 2025-08-18
     *
     * 接口规则：
     * 1. ID参数必须校验非空
     * 2. 必须进行参数校验
     * 3. 必须记录操作日志
     */
    @ApiDoc(
            value = "修改部门权限表信息",
        description = "根据ID更新部门权限表信息",
            request = SysDepartPermissionAddRequestVo.class,
        response = boolean.class,
        method = RequestMethodEnum.POST
    )
    @PutMapping(value = UrlCommand.sys_sysDepartPermission_modify)
    public boolean update(@Validated SysDepartPermissionAddRequestVo sysDepartPermissionAddRequestVo) {

        log.info("修改部门权限表信息, 参数: {}", sysDepartPermissionAddRequestVo);

        SysDepartPermissionRequestDto requestDto = SysDepartPermissionRequestDto.build().clone(sysDepartPermissionAddRequestVo);

        if (requestDto.getId() == null || requestDto.getId() <= 0) {
            log.warn("修改部门权限表信息参数错误, ID: {}", requestDto.getId());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        boolean result = sysDepartPermissionService.updateSysDepartPermission(requestDto);

        log.info("修改部门权限表信息完成, ID: {}, 结果: {}", requestDto.getId(), result);
        return result;
    }

    /**
     * 查看部门权限表详情
     * 根据ID获取部门权限表详细信息
     * @param idRequestVo ID请求参数
     * @return SysDepartPermissionShowResponseVo 详情响应结果
     * @author suven
     * @date 2025-08-18
     *
     * 接口规则：
     * 1. ID参数必须校验非空
     * 2. 必须处理数据不存在情况
     * 3. 必须记录查询日志
     */
    @ApiDoc(
            value = "查看部门权限表信息",
        description = "根据ID获取部门权限表详细信息",
            request = HttpRequestByIdVo.class,
        response = SysDepartPermissionShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysDepartPermission_detail)
    public SysDepartPermissionShowResponseVo detail(@Validated HttpRequestByIdVo idRequestVo) {

        log.info("查询部门权限表详情, ID: {}", idRequestVo.getId());

        // 参数校验
        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询部门权限表详情参数错误, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysDepartPermissionResponseDto responseDto = sysDepartPermissionService.getSysDepartPermissionById(idRequestVo.getId());

        if (responseDto == null) {
            log.warn("部门权限表不存在, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        log.info("查询部门权限表详情成功, ID: {}", idRequestVo.getId());
        return SysDepartPermissionShowResponseVo.build().clone(responseDto);
    }








    /**
     * 删除部门权限表信息
     * 根据ID列表批量删除部门权限表记录
     * @param idRequestVo ID列表请求参数
     * @return Integer 删除的记录数量
     * @author suven
     * @date 2025-08-18
     *
     * 接口规则：
     * 1. ID列表必须校验非空
     * 2. 必须记录操作日志
     */
    @ApiDoc(
            value = "删除部门权限表信息",
        description = "根据ID列表批量删除部门权限表记录",
            request = HttpRequestByIdListVo.class,
        response = Integer.class,
        method = RequestMethodEnum.POST
    )
    @DeleteMapping(value = UrlCommand.sys_sysDepartPermission_del)
    public Integer delete(@Validated HttpRequestByIdListVo idRequestVo) {

        log.info("删除部门权限表信息, IDs: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除部门权限表信息参数错误, ID列表为空");
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        int result = sysDepartPermissionService.delSysDepartPermissionByIds(idRequestVo.getIdList());

        log.info("删除部门权限表信息完成, 删除数量: {}", result);
        return result;
    }







}