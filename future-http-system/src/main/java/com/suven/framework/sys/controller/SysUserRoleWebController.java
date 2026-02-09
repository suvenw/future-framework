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
import com.suven.framework.sys.dto.request.SysUserRoleRequestDto;
import com.suven.framework.sys.dto.response.SysUserRoleResponseDto;
import com.suven.framework.sys.dto.enums.SysUserRoleQueryEnum;
import com.suven.framework.sys.service.SysUserRoleService;
import com.suven.framework.sys.vo.request.SysUserRoleQueryRequestVo;
import com.suven.framework.sys.vo.request.SysUserRoleAddRequestVo;
import com.suven.framework.sys.vo.response.SysUserRoleShowResponseVo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;


/**
 * ClassName: SysUserRoleWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:11:27
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 用户角色关系表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *    v2.0.0         suven    2026-02-05   重构:统一接口规范
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysUserRole")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@RestController
@Slf4j
@Validated
@ApiDoc(
    group = DocumentConst.Sys.SYS_DOC_GROUP,
    groupDesc = DocumentConst.Sys.SYS_DOC_DES,
    module = "用户角色关系表模块",
    isApp = true
)
public class SysUserRoleWebController {

    @Autowired
    private SysUserRoleService  sysUserRoleService;

    /**
     * 分页获取用户角色关系表信息
     * 根据查询条件分页获取用户角色关系表列表
     * @param sysUserRoleQueryRequestVo 查询请求参数
     * @return PageResult<SysUserRoleShowResponseVo> 分页响应结果
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
        value = "分页获取用户角色关系表信息",
        description = "根据查询条件分页获取用户角色关系表列表",
        request = SysUserRoleQueryRequestVo.class,
        response = SysUserRoleShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysUserRole_list)
    public PageResult<SysUserRoleShowResponseVo> pageList(
            @Valid SysUserRoleQueryRequestVo sysUserRoleQueryRequestVo) {

        log.info("分页查询用户角色关系表, 参数: {}", sysUserRoleQueryRequestVo);

        Pager<SysUserRoleRequestDto> pager = new Pager<>(
                sysUserRoleQueryRequestVo.getPageNo(),
                sysUserRoleQueryRequestVo.getPageSize()
        );
        SysUserRoleRequestDto requestDto = SysUserRoleRequestDto.build()
                .clone(sysUserRoleQueryRequestVo);
        pager.toParamObject(requestDto);

        PageResult<SysUserRoleResponseDto> pageResult = sysUserRoleService
                .getSysUserRoleByNextPage(pager, SysUserRoleQueryEnum.DESC_ID);

        log.info("分页查询用户角色关系表完成, 总数: {}", pageResult.getTotal());
        return pageResult.convertBuild(SysUserRoleShowResponseVo.class);
    }

    /**
     * 查看用户角色关系表详情
     * 根据ID获取用户角色关系表详细信息
     * @param idRequestVo ID请求参数
     * @return SysUserRoleShowResponseVo 详情响应结果
     * @author suven
     * @date 2025-08-18
     *
     * 接口规则：
     * 1. ID参数必须校验非空
     * 2. 必须处理数据不存在情况
     * 3. 必须记录查询日志
     */
    @ApiDoc(
        value = "查看用户角色关系表信息",
        description = "根据ID获取用户角色关系表详细信息",
        request = HttpRequestByIdVo.class,
        response = SysUserRoleShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysUserRole_detail)
    public SysUserRoleShowResponseVo detail(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("查询用户角色关系表详情, ID: {}", idRequestVo.getId());

        // 参数校验
        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询用户角色关系表详情参数错误, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysUserRoleResponseDto responseDto = sysUserRoleService.getSysUserRoleById(idRequestVo.getId());

        if (responseDto == null) {
            log.warn("用户角色关系表不存在, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        log.info("查询用户角色关系表详情成功, ID: {}", idRequestVo.getId());
        return SysUserRoleShowResponseVo.build().clone(responseDto);
    }

    /**
     * 新增用户角色关系表信息
     * 创建新的用户角色关系表记录
     * @param sysUserRoleAddRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "新增用户角色关系表信息",
        description = "新增用户角色关系表记录",
        request = SysUserRoleAddRequestVo.class,
        response = Long.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysUserRole_add)
    public Long create(@Valid SysUserRoleAddRequestVo sysUserRoleAddRequestVo) {

        log.info("新增用户角色关系表, 参数: {}", sysUserRoleAddRequestVo);

        SysUserRoleRequestDto requestDto = SysUserRoleRequestDto.build()
                .clone(sysUserRoleAddRequestVo);
        SysUserRoleResponseDto responseDto = sysUserRoleService.saveSysUserRole(requestDto);

        if (responseDto == null) {
            log.error("新增用户角色关系表失败");
            throw ExceptionFactory.sysException(CodeEnum.SYS_UNKOWNN_FAIL);
        }

        log.info("新增用户角色关系表成功, ID: {}", responseDto.getId());
        return responseDto.getId();
    }

    /**
     * 修改用户角色关系表信息
     * 根据ID更新用户角色关系表信息
     * @param sysUserRoleAddRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "修改用户角色关系表信息",
        description = "根据ID修改用户角色关系表记录",
        request = SysUserRoleAddRequestVo.class,
        response = boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysUserRole_modify)
    public boolean update(@Valid SysUserRoleAddRequestVo sysUserRoleAddRequestVo) {

        log.info("修改用户角色关系表, 参数: {}", sysUserRoleAddRequestVo);

        SysUserRoleRequestDto requestDto = SysUserRoleRequestDto.build()
                .clone(sysUserRoleAddRequestVo);

        if (requestDto.getId() == null || requestDto.getId() <= 0) {
            log.warn("修改用户角色关系表参数错误, ID: {}", requestDto.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        boolean result = sysUserRoleService.updateSysUserRole(requestDto);

        log.info("修改用户角色关系表完成, ID: {}, 结果: {}", requestDto.getId(), result);
        return result;
    }

    /**
     * 删除用户角色关系表信息
     * 根据ID列表批量删除用户角色关系表记录
     * @param idRequestVo ID列表请求参数
     * @return Integer 删除数量
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "删除用户角色关系表信息",
        description = "根据ID列表删除用户角色关系表记录",
        request = HttpRequestByIdListVo.class,
        response = Integer.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysUserRole_del)
    public Integer delete(@Valid HttpRequestByIdListVo idRequestVo) {

        log.info("删除用户角色关系表, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除用户角色关系表参数错误, ID列表为空");
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        int result = sysUserRoleService.delSysUserRoleByIds(idRequestVo.getIdList());

        log.info("删除用户角色关系表完成, 删除数量: {}", result);
        return result;
    }

}
