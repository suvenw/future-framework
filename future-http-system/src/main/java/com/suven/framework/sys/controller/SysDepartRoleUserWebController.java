package com.suven.framework.sys.controller;


import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.api.RequestMethodEnum;
import com.suven.framework.http.exception.ExceptionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import lombok.extern.slf4j.Slf4j;

import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
 

import com.suven.framework.sys.service.SysDepartRoleUserService;
import com.suven.framework.sys.vo.request.SysDepartRoleUserQueryRequestVo;
import com.suven.framework.sys.vo.request.SysDepartRoleUserAddRequestVo;
import com.suven.framework.sys.vo.response.SysDepartRoleUserShowResponseVo;

import com.suven.framework.sys.dto.request.SysDepartRoleUserRequestDto;
import com.suven.framework.sys.dto.response.SysDepartRoleUserResponseDto;
import com.suven.framework.sys.dto.enums.SysDepartRoleUserQueryEnum;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * ClassName: SysDepartRoleUserWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:14:21
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 部门角色用户表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysDepartRoleUser")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@RestController
@Slf4j
@Validated
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
    groupDesc = DocumentConst.Sys.SYS_DOC_DES,
    module = "部门角色用户表模块",
    isApp = true
)
public class SysDepartRoleUserWebController {

    @Autowired
    private SysDepartRoleUserService  sysDepartRoleUserService;

    /**
     * 分页获取部门角色用户表信息
     * 根据查询条件分页获取部门角色用户表列表
     * @param sysDepartRoleUserQueryRequestVo 查询请求参数
     * @return PageResult<SysDepartRoleUserShowResponseVo> 分页响应结果
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
        value = "分页获取部门角色用户表信息",
        description = "根据条件分页查询部门角色用户表数据",
            request = SysDepartRoleUserQueryRequestVo.class,
        response = SysDepartRoleUserShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysDepartRoleUser_list)
    public PageResult<SysDepartRoleUserShowResponseVo> pageList( @Validated  SysDepartRoleUserQueryRequestVo sysDepartRoleUserQueryRequestVo) {

        log.info("分页查询部门角色用户表, 参数: {}", sysDepartRoleUserQueryRequestVo);

        Pager<SysDepartRoleUserRequestDto> pager = new Pager<>(
            sysDepartRoleUserQueryRequestVo.getPageNo(),
            sysDepartRoleUserQueryRequestVo.getPageSize()
        );
        SysDepartRoleUserRequestDto requestDto = SysDepartRoleUserRequestDto.build().clone(sysDepartRoleUserQueryRequestVo);
        pager.toParamObject(requestDto);

        PageResult<SysDepartRoleUserResponseDto> pageResult = sysDepartRoleUserService
            .getSysDepartRoleUserByNextPage(pager, SysDepartRoleUserQueryEnum.DESC_ID);

        log.info("分页查询部门角色用户表完成, 总数: {}", pageResult.getTotal());
        return pageResult.convertBuild(SysDepartRoleUserShowResponseVo.class);
    }

    /**
     * 查看部门角色用户表详情
     * 根据ID获取部门角色用户表详细信息
     * @param idRequestVo ID请求参数
     * @return SysDepartRoleUserShowResponseVo 详情响应结果
     * @author suven
     * @date 2025-08-18
     *
     * 接口规则：
     * 1. ID参数必须校验非空
     * 2. 必须处理数据不存在情况
     * 3. 必须记录查询日志
     */
    @ApiDoc(
        value = "查看部门角色用户表信息",
        description = "根据ID获取部门角色用户表详细信息",
        request = HttpRequestByIdVo.class,
        response = SysDepartRoleUserShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysDepartRoleUser_detail)
    public SysDepartRoleUserShowResponseVo detail( @Validated  HttpRequestByIdVo idRequestVo) {

        log.info("查询部门角色用户表详情, ID: {}", idRequestVo.getId());

        // 参数校验
        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询部门角色用户表详情参数错误, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysDepartRoleUserResponseDto responseDto = sysDepartRoleUserService.getSysDepartRoleUserById(idRequestVo.getId());

        if (responseDto == null) {
            log.warn("部门角色用户表不存在, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        log.info("查询部门角色用户表详情成功, ID: {}", idRequestVo.getId());
        return SysDepartRoleUserShowResponseVo.build().clone(responseDto);
    }

    /**
     * 新增部门角色用户表信息
     * 创建新的部门角色用户表记录
     * @param sysDepartRoleUserAddRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
            value = "新增部门角色用户表信息",
        description = "创建新的部门角色用户表记录",
            request = SysDepartRoleUserAddRequestVo.class,
        response = Long.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysDepartRoleUser_add)
    public Long create( @Validated  SysDepartRoleUserAddRequestVo sysDepartRoleUserAddRequestVo) {

        log.info("新增部门角色用户表信息, 参数: {}", sysDepartRoleUserAddRequestVo);

        SysDepartRoleUserRequestDto requestDto = SysDepartRoleUserRequestDto.build().clone(sysDepartRoleUserAddRequestVo);

        SysDepartRoleUserResponseDto responseDto = sysDepartRoleUserService.saveSysDepartRoleUser(requestDto);

        if (responseDto == null) {
            log.error("新增部门角色用户表信息失败");
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
        }

        log.info("新增部门角色用户表信息成功, ID: {}", responseDto.getId());
        return responseDto.getId();
    }

    /**
     * 修改部门角色用户表信息
     * 根据ID更新部门角色用户表信息
     * @param sysDepartRoleUserAddRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "修改部门角色用户表信息",
        description = "根据ID更新部门角色用户表信息",
        request = SysDepartRoleUserAddRequestVo.class,
        response = boolean.class,
        method = RequestMethodEnum.POST
    )
    @PutMapping(value = UrlCommand.sys_sysDepartRoleUser_modify)
    public boolean update( @Validated  SysDepartRoleUserAddRequestVo sysDepartRoleUserAddRequestVo) {

        log.info("修改部门角色用户表信息, 参数: {}", sysDepartRoleUserAddRequestVo);

        SysDepartRoleUserRequestDto requestDto = SysDepartRoleUserRequestDto.build().clone(sysDepartRoleUserAddRequestVo);

        if (requestDto.getId() == null || requestDto.getId() <= 0) {
            log.warn("修改部门角色用户表信息参数错误, ID: {}", requestDto.getId());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        boolean result = sysDepartRoleUserService.updateSysDepartRoleUser(requestDto);

        log.info("修改部门角色用户表信息完成, ID: {}, 结果: {}", requestDto.getId(), result);
        return result;
    }

    /**
     * 删除部门角色用户表信息
     * 根据ID列表批量删除部门角色用户表记录
     * @param idRequestVo ID列表请求参数
     * @return Integer 删除的记录数量
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
            value = "删除部门角色用户表信息",
        description = "根据ID列表批量删除部门角色用户表记录",
            request = HttpRequestByIdListVo.class,
        response = Integer.class,
        method = RequestMethodEnum.POST
    )
    @DeleteMapping(value = UrlCommand.sys_sysDepartRoleUser_del)
    public Integer delete( @Validated  HttpRequestByIdListVo idRequestVo) {

        log.info("删除部门角色用户表信息, IDs: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除部门角色用户表信息参数错误, ID列表为空");
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        int result = sysDepartRoleUserService.delSysDepartRoleUserByIds(idRequestVo.getIdList());

        log.info("删除部门角色用户表信息完成, 删除数量: {}", result);
        return result;
    }

}
