package com.suven.framework.sys.controller;


import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.api.RequestMethodEnum;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.exception.ExceptionFactory;
import com.suven.framework.sys.dto.request.SysUserDepartRequestDto;
import com.suven.framework.sys.dto.response.SysUserDepartResponseDto;
import com.suven.framework.sys.dto.enums.SysUserDepartQueryEnum;
import com.suven.framework.sys.service.SysUserDepartService;
import com.suven.framework.sys.vo.request.SysUserDepartQueryRequestVo;
import com.suven.framework.sys.vo.request.SysUserDepartRequestVo;
import com.suven.framework.sys.vo.response.SysUserDepartShowResponseVo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
 *    v2.0.0         suven    2026-02-05   重构:统一接口规范
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysUserDepart")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
@RestController
@Slf4j
@Validated
@ApiDoc(
    group = DocumentConst.Sys.SYS_DOC_GROUP,
    groupDesc = DocumentConst.Sys.SYS_DOC_DES,
    module = "用户部门关系表模块",
    isApp = true
)
public class SysUserDepartWebController {

    @Autowired
    private SysUserDepartService sysUserDepartService;

    /**
     * 分页获取用户部门关系表信息
     * 根据查询条件分页获取用户部门关系表列表
     * @param sysUserDepartQueryRequestVo 查询请求参数
     * @return PageResult<SysUserDepartShowResponseVo> 分页响应结果
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
        value = "分页获取用户部门关系表信息",
        description = "根据查询条件分页获取用户部门关系表列表",
        request = SysUserDepartQueryRequestVo.class,
        response = SysUserDepartShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysUserDepart_list)
    public PageResult<SysUserDepartShowResponseVo> pageList(
            @Validated  SysUserDepartQueryRequestVo sysUserDepartQueryRequestVo) {

        log.info("分页查询用户部门关系表, 参数: {}", sysUserDepartQueryRequestVo);

        Pager<SysUserDepartRequestDto> pager = new Pager<>(
                sysUserDepartQueryRequestVo.getPageNo(),
                sysUserDepartQueryRequestVo.getPageSize()
        );
        SysUserDepartRequestDto requestDto = SysUserDepartRequestDto.build()
                .clone(sysUserDepartQueryRequestVo);
        pager.toParamObject(requestDto);

        PageResult<SysUserDepartResponseDto> pageResult = sysUserDepartService
                .getSysUserDepartByNextPage(pager, SysUserDepartQueryEnum.DESC_ID);

        log.info("分页查询用户部门关系表完成, 总数: {}", pageResult.getTotal());
        return pageResult.convertBuild(SysUserDepartShowResponseVo.class);
    }

    /**
     * 查看用户部门关系表详情
     * 根据ID获取用户部门关系表详细信息
     * @param idRequestVo ID请求参数
     * @return SysUserDepartShowResponseVo 详情响应结果
     * @author suven
     * @date 2025-08-18
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
        response = SysUserDepartShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysUserDepart_detail)
    public SysUserDepartShowResponseVo detail( @Validated  HttpRequestByIdVo idRequestVo) {

        log.info("查询用户部门关系表详情, ID: {}", idRequestVo.getId());

        // 参数校验
        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询用户部门关系表详情参数错误, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysUserDepartResponseDto responseDto = sysUserDepartService
                .getSysUserDepartById(idRequestVo.getId());

        if (responseDto == null) {
            log.warn("用户部门关系表不存在, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        log.info("查询用户部门关系表详情成功, ID: {}", idRequestVo.getId());
        return SysUserDepartShowResponseVo.build().clone(responseDto);
    }

    /**
     * 新增用户部门关系表信息
     * 创建新的用户部门关系表记录
     * @param sysUserDepartRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "新增用户部门关系表信息",
        description = "新增用户部门关系表记录",
        request = SysUserDepartRequestVo.class,
        response = Long.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysUserDepart_add)
    public Long create( @Validated  SysUserDepartRequestVo sysUserDepartRequestVo) {

        log.info("新增用户部门关系表, 参数: {}", sysUserDepartRequestVo);

        SysUserDepartRequestDto requestDto = SysUserDepartRequestDto.build()
                .clone(sysUserDepartRequestVo);
        SysUserDepartResponseDto responseDto = sysUserDepartService.saveSysUserDepart(requestDto);

        if (responseDto == null) {
            log.error("新增用户部门关系表失败");
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
        }

        log.info("新增用户部门关系表成功, ID: {}", responseDto.getId());
        return responseDto.getId();
    }

    /**
     * 修改用户部门关系表信息
     * 根据ID更新用户部门关系表信息
     * @param sysUserDepartRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "修改用户部门关系表信息",
        description = "根据ID修改用户部门关系表记录",
        request = SysUserDepartRequestVo.class,
        response = boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysUserDepart_modify)
    public boolean update( @Validated  SysUserDepartRequestVo sysUserDepartRequestVo) {

        log.info("修改用户部门关系表, 参数: {}", sysUserDepartRequestVo);

        if (sysUserDepartRequestVo.getId() == null || sysUserDepartRequestVo.getId() <= 0) {
            log.warn("修改用户部门关系表参数错误, ID: {}", sysUserDepartRequestVo.getId());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysUserDepartRequestDto requestDto = SysUserDepartRequestDto.build()
                .clone(sysUserDepartRequestVo);
        boolean result = sysUserDepartService.updateSysUserDepart(requestDto);

        log.info("修改用户部门关系表完成, ID: {}, 结果: {}", sysUserDepartRequestVo.getId(), result);
        return result;
    }

    /**
     * 删除用户部门关系表信息
     * 根据ID列表批量删除用户部门关系表记录
     * @param idRequestVo ID列表请求参数
     * @return Integer 删除数量
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "删除用户部门关系表信息",
        description = "根据ID列表删除用户部门关系表记录",
        request = HttpRequestByIdListVo.class,
        response = Integer.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysUserDepart_del)
    public Integer delete( @Validated  HttpRequestByIdListVo idRequestVo) {

        log.info("删除用户部门关系表, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除用户部门关系表参数错误, ID列表为空");
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        int result = sysUserDepartService.delSysUserDepartByIds(idRequestVo.getIdList());
        log.info("删除用户部门关系表完成, 删除数量: {}", result);
        return result;
    }

}
