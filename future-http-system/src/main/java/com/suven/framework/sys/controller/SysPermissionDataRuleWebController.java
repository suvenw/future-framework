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
import com.suven.framework.sys.dto.enums.SysPermissionDataRuleQueryEnum;
import com.suven.framework.sys.dto.request.SysPermissionDataRuleRequestDto;
import com.suven.framework.sys.dto.response.SysPermissionDataRuleResponseDto;
import com.suven.framework.sys.service.SysPermissionDataRuleService;
import com.suven.framework.sys.vo.request.SysPermissionDataRuleAddRequestVo;
import com.suven.framework.sys.vo.request.SysPermissionDataRuleQueryRequestVo;
import com.suven.framework.sys.vo.response.SysPermissionDataRuleShowResponseVo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * ClassName: SysPermissionDataRuleWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:35
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 菜单权限规则表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysPermissionDataRule")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/

@RestController
@Slf4j
@Validated
@ApiDoc(
    group = DocumentConst.Sys.SYS_DOC_GROUP,
    groupDesc = DocumentConst.Sys.SYS_DOC_DES,
    module = "菜单权限规则表模块",
    isApp = true
)
public class SysPermissionDataRuleWebController {

    @Autowired
    private SysPermissionDataRuleService  sysPermissionDataRuleService;

    /**
     * 分页获取菜单权限规则表信息
     * 根据查询条件分页获取菜单权限规则表列表
     * @param sysPermissionDataRuleQueryRequestVo 查询请求参数
     * @return PageResult<SysPermissionDataRuleShowResponseVo> 分页响应结果
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "分页获取菜单权限规则表信息",
        description = "根据条件分页查询菜单权限规则表数据",
        request = SysPermissionDataRuleQueryRequestVo.class,
        response = SysPermissionDataRuleShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysPermissionDataRule_list)
    public PageResult<SysPermissionDataRuleShowResponseVo> pageList(
            @Valid SysPermissionDataRuleQueryRequestVo sysPermissionDataRuleQueryRequestVo) {

        log.info("分页查询菜单权限规则表, 参数: {}", sysPermissionDataRuleQueryRequestVo);

        Pager<SysPermissionDataRuleRequestDto> pager = new Pager<>(
                sysPermissionDataRuleQueryRequestVo.getPageNo(),
                sysPermissionDataRuleQueryRequestVo.getPageSize()
        );
        SysPermissionDataRuleRequestDto requestDto = SysPermissionDataRuleRequestDto.build()
                .clone(sysPermissionDataRuleQueryRequestVo);
        pager.toParamObject(requestDto);

        PageResult<SysPermissionDataRuleResponseDto> pageResult =
                sysPermissionDataRuleService.getSysPermissionDataRuleByNextPage(
                        pager, SysPermissionDataRuleQueryEnum.DESC_ID);

        log.info("分页查询菜单权限规则表完成, 总数: {}", pageResult.getTotal());
        return pageResult.convertBuild(SysPermissionDataRuleShowResponseVo.class);
    }

    /**
     * 查看菜单权限规则表详情
     * 根据ID获取菜单权限规则表详细信息
     * @param idRequestVo ID请求参数
     * @return SysPermissionDataRuleShowResponseVo 详情响应结果
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "查看菜单权限规则表信息",
        description = "根据ID获取菜单权限规则表详细信息",
        request = HttpRequestByIdVo.class,
        response = SysPermissionDataRuleShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysPermissionDataRule_detail)
    public SysPermissionDataRuleShowResponseVo detail(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("查询菜单权限规则表详情, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询菜单权限规则表详情参数错误, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysPermissionDataRuleResponseDto responseDto =
                sysPermissionDataRuleService.getSysPermissionDataRuleById(idRequestVo.getId());

        if (responseDto == null) {
            log.warn("菜单权限规则表不存在, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        log.info("查询菜单权限规则表详情成功, ID: {}", idRequestVo.getId());
        return SysPermissionDataRuleShowResponseVo.build().clone(responseDto);
    }

    /**
     * 新增菜单权限规则表信息
     * 创建新的菜单权限规则表记录
     * @param sysPermissionDataRuleAddRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "新增菜单权限规则表信息",
        description = "创建新的菜单权限规则表记录",
        request = SysPermissionDataRuleAddRequestVo.class,
        response = Long.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysPermissionDataRule_add)
    public Long create(@Valid SysPermissionDataRuleAddRequestVo sysPermissionDataRuleAddRequestVo) {

        log.info("新增菜单权限规则表信息, 参数: {}", sysPermissionDataRuleAddRequestVo);

        SysPermissionDataRuleRequestDto requestDto = SysPermissionDataRuleRequestDto.build()
                .clone(sysPermissionDataRuleAddRequestVo);

        SysPermissionDataRuleResponseDto responseDto =
                sysPermissionDataRuleService.saveSysPermissionDataRule(requestDto);

        if (responseDto == null) {
            log.warn("新增菜单权限规则表失败");
            throw ExceptionFactory.sysException(CodeEnum.SYS_UNKOWNN_FAIL);
        }

        log.info("新增菜单权限规则表成功, ID: {}", responseDto.getId());
        return responseDto.getId();
    }

    /**
     * 修改菜单权限规则表信息
     * 根据ID更新菜单权限规则表信息
     * @param sysPermissionDataRuleAddRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "修改菜单权限规则表信息",
        description = "根据ID更新菜单权限规则表记录",
        request = SysPermissionDataRuleAddRequestVo.class,
        response = boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysPermissionDataRule_modify)
    public boolean update(@Valid SysPermissionDataRuleAddRequestVo sysPermissionDataRuleAddRequestVo) {

        log.info("修改菜单权限规则表信息, 参数: {}", sysPermissionDataRuleAddRequestVo);

        SysPermissionDataRuleRequestDto requestDto = SysPermissionDataRuleRequestDto.build()
                .clone(sysPermissionDataRuleAddRequestVo);

        if (requestDto.getId() == null || requestDto.getId() <= 0) {
            log.warn("修改菜单权限规则表参数错误, ID: {}", requestDto.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        boolean result = sysPermissionDataRuleService.updateSysPermissionDataRule(requestDto);
        log.info("修改菜单权限规则表完成, ID: {}, 结果: {}", requestDto.getId(), result);
        return result;
    }

    /**
     * 删除菜单权限规则表信息
     * 根据ID列表批量删除菜单权限规则表记录
     * @param idRequestVo ID列表请求参数
     * @return Integer 删除的记录数量
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "删除菜单权限规则表信息",
        description = "根据ID列表批量删除菜单权限规则表记录",
        request = HttpRequestByIdListVo.class,
        response = Integer.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysPermissionDataRule_del)
    public int delete(@Valid HttpRequestByIdListVo idRequestVo) {

        log.info("删除菜单权限规则表, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除菜单权限规则表参数错误, ID列表为空");
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        int result = sysPermissionDataRuleService.delSysPermissionDataRuleByIds(idRequestVo.getIdList());
        log.info("删除菜单权限规则表完成, 删除数量: {}", result);
        return result;
    }

}
