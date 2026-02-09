package com.suven.framework.sys.controller;


import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
 
import com.suven.framework.sys.dto.enums.SysRolePermissionQueryEnum;
import com.suven.framework.sys.dto.request.SysRolePermissionRequestDto;
import com.suven.framework.sys.dto.response.SysRolePermissionResponseDto;
import com.suven.framework.sys.service.SysRolePermissionService;
import com.suven.framework.sys.vo.request.SysRolePermissionAddRequestVo;
import com.suven.framework.sys.vo.request.SysRolePermissionQueryRequestVo;
import com.suven.framework.sys.vo.response.SysRolePermissionShowResponseVo;
 
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * ClassName: SysRolePermissionWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:49
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 角色权限表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysRolePermission")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/

@RestController
@Slf4j
@Validated
@ApiDoc(
    group = DocumentConst.Sys.SYS_DOC_GROUP,
    groupDesc = DocumentConst.Sys.SYS_DOC_DES,
    module = "角色权限表模块",
    isApp = true
)
public class SysRolePermissionWebController {

    @Autowired
    private SysRolePermissionService  sysRolePermissionService;

    /**
     * 分页获取角色权限表信息
     * 根据查询条件分页获取角色权限表列表
     * @param sysRolePermissionQueryRequestVo 查询请求参数
     * @return PageResult<SysRolePermissionShowResponseVo> 分页响应结果
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "分页获取角色权限表信息",
        description = "根据条件分页查询角色权限表数据",
        request = SysRolePermissionQueryRequestVo.class,
        response = SysRolePermissionShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysRolePermission_list)
    public PageResult<SysRolePermissionShowResponseVo> pageList(
            @Validated  SysRolePermissionQueryRequestVo sysRolePermissionQueryRequestVo) {

        log.info("分页查询角色权限表, 参数: {}", sysRolePermissionQueryRequestVo);

        Pager<SysRolePermissionRequestDto> pager = new Pager<>(
                sysRolePermissionQueryRequestVo.getPageNo(),
                sysRolePermissionQueryRequestVo.getPageSize()
        );
        SysRolePermissionRequestDto requestDto = SysRolePermissionRequestDto.build()
                .clone(sysRolePermissionQueryRequestVo);
        pager.toParamObject(requestDto);

        PageResult<SysRolePermissionResponseDto> pageResult =
                sysRolePermissionService.getSysRolePermissionByNextPage(
                        pager, SysRolePermissionQueryEnum.DESC_ID);

        log.info("分页查询角色权限表完成, 总数: {}", pageResult.getTotal());
        return pageResult.convertBuild(SysRolePermissionShowResponseVo.class);
    }

    /**
     * 查看角色权限表详情
     * 根据ID获取角色权限表详细信息
     * @param idRequestVo ID请求参数
     * @return SysRolePermissionShowResponseVo 详情响应结果
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "查看角色权限表信息",
        description = "根据ID获取角色权限表详细信息",
        request = HttpRequestByIdVo.class,
        response = SysRolePermissionShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysRolePermission_detail)
    public SysRolePermissionShowResponseVo detail( @Validated  HttpRequestByIdVo idRequestVo) {

        log.info("查询角色权限表详情, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询角色权限表详情参数错误, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysRolePermissionResponseDto responseDto =
                sysRolePermissionService.getSysRolePermissionById(idRequestVo.getId());

        if (responseDto == null) {
            log.warn("角色权限表不存在, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        log.info("查询角色权限表详情成功, ID: {}", idRequestVo.getId());
        return SysRolePermissionShowResponseVo.build().clone(responseDto);
    }

    /**
     * 新增角色权限表信息
     * 创建新的角色权限表记录
     * @param sysRolePermissionAddRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "新增角色权限表信息",
        description = "创建新的角色权限表记录",
        request = SysRolePermissionAddRequestVo.class,
        response = Long.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysRolePermission_add)
    public Long create( @Validated  SysRolePermissionAddRequestVo sysRolePermissionAddRequestVo) {

        log.info("新增角色权限表信息, 参数: {}", sysRolePermissionAddRequestVo);

        SysRolePermissionRequestDto requestDto = SysRolePermissionRequestDto.build()
                .clone(sysRolePermissionAddRequestVo);

        SysRolePermissionResponseDto responseDto =
                sysRolePermissionService.saveSysRolePermission(requestDto);

        if (responseDto == null) {
            log.warn("新增角色权限表失败");
            throw ExceptionFactory.sysException(CodeEnum.SYS_UNKOWNN_FAIL);
        }

        log.info("新增角色权限表成功, ID: {}", responseDto.getId());
        return responseDto.getId();
    }

    /**
     * 修改角色权限表信息
     * 根据ID更新角色权限表信息
     * @param sysRolePermissionAddRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "修改角色权限表信息",
        description = "根据ID更新角色权限表记录",
        request = SysRolePermissionAddRequestVo.class,
        response = boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysRolePermission_modify)
    public boolean update( @Validated  SysRolePermissionAddRequestVo sysRolePermissionAddRequestVo) {

        log.info("修改角色权限表信息, 参数: {}", sysRolePermissionAddRequestVo);

        SysRolePermissionRequestDto requestDto = SysRolePermissionRequestDto.build()
                .clone(sysRolePermissionAddRequestVo);

        if (requestDto.getId() == null || requestDto.getId() <= 0) {
            log.warn("修改角色权限表参数错误, ID: {}", requestDto.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        boolean result = sysRolePermissionService.updateSysRolePermission(requestDto);
        log.info("修改角色权限表完成, ID: {}, 结果: {}", requestDto.getId(), result);
        return result;
    }

    /**
     * 删除角色权限表信息
     * 根据ID列表批量删除角色权限表记录
     * @param idRequestVo ID列表请求参数
     * @return Integer 删除的记录数量
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "删除角色权限表信息",
        description = "根据ID列表批量删除角色权限表记录",
        request = HttpRequestByIdListVo.class,
        response = Integer.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysRolePermission_del)
    public int delete( @Validated  HttpRequestByIdListVo idRequestVo) {

        log.info("删除角色权限表, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除角色权限表参数错误, ID列表为空");
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        int result = sysRolePermissionService.delSysRolePermissionByIds(idRequestVo.getIdList());
        log.info("删除角色权限表完成, 删除数量: {}", result);
        return result;
    }

}
