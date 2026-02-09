package com.suven.framework.sys.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import lombok.extern.slf4j.Slf4j;

import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
 

import com.suven.framework.sys.service.SysLogService;
import com.suven.framework.sys.vo.request.SysLogQueryRequestVo;
import com.suven.framework.sys.vo.request.SysLogAddRequestVo;
import com.suven.framework.sys.vo.response.SysLogShowResponseVo;

import com.suven.framework.sys.dto.request.SysLogRequestDto;
import com.suven.framework.sys.dto.response.SysLogResponseDto;
import com.suven.framework.sys.dto.enums.SysLogQueryEnum;


/**
 * ClassName: SysLogWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:19
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 系统日志表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysLog")
 * </pre>
 * Copyright: (c) 2021 gc by <a href="https://www.suven.top">www.suven.top</a>
 **/


@RestController
@Slf4j
@Validated
@ApiDoc(
    group = DocumentConst.Sys.SYS_DOC_GROUP,
    groupDesc = DocumentConst.Sys.SYS_DOC_DES,
    module = "系统日志表模块",
    isApp = true
)
public class SysLogWebController {

    @Autowired
    private SysLogService  sysLogService;

    /**
     * 分页获取系统日志表信息
     * 根据查询条件分页获取系统日志表列表
     * @param sysLogQueryRequestVo 查询请求参数
     * @return PageResult<SysLogShowResponseVo> 分页响应结果
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
        value = "分页获取系统日志表信息",
        description = "根据条件分页查询系统日志表数据",
        request = SysLogQueryRequestVo.class,
        response = SysLogShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysLog_list)
    public PageResult<SysLogShowResponseVo> pageList( @Validated  SysLogQueryRequestVo sysLogQueryRequestVo) {

        log.info("分页查询系统日志表, 参数: {}", sysLogQueryRequestVo);

        Pager<SysLogRequestDto> pager = new Pager<>(
            sysLogQueryRequestVo.getPageNo(),
            sysLogQueryRequestVo.getPageSize()
        );
        SysLogRequestDto requestDto = SysLogRequestDto.build().clone(sysLogQueryRequestVo);
        pager.toParamObject(requestDto);

        PageResult<SysLogResponseDto> pageResult = sysLogService
            .getSysLogByNextPage(pager, SysLogQueryEnum.DESC_ID);

        log.info("分页查询系统日志表完成, 总数: {}", pageResult.getTotal());
        return pageResult.convertBuild(SysLogShowResponseVo.class);
    }

    /**
     * 查看系统日志表详情
     * 根据ID获取系统日志表详细信息
     * @param idRequestVo ID请求参数
     * @return SysLogShowResponseVo 详情响应结果
     * @author suven
     * @date 2025-08-18
     *
     * 接口规则：
     * 1. ID参数必须校验非空
     * 2. 必须处理数据不存在情况
     * 3. 必须记录查询日志
     */
    @ApiDoc(
        value = "查看系统日志表信息",
        description = "根据ID获取系统日志表详细信息",
        request = HttpRequestByIdVo.class,
        response = SysLogShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysLog_detail)
    public SysLogShowResponseVo detail( @Validated  HttpRequestByIdVo idRequestVo) {

        log.info("查询系统日志表详情, ID: {}", idRequestVo.getId());

        // 参数校验
        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询系统日志表详情参数错误, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysLogResponseDto responseDto = sysLogService.getSysLogById(idRequestVo.getId());

        if (responseDto == null) {
            log.warn("系统日志表不存在, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        log.info("查询系统日志表详情成功, ID: {}", idRequestVo.getId());
        return SysLogShowResponseVo.build().clone(responseDto);
    }

    /**
     * 新增系统日志表信息
     * 创建新的系统日志表记录
     * @param sysLogAddRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "新增系统日志表信息",
        description = "创建新的系统日志表记录",
        request = SysLogAddRequestVo.class,
        response = Long.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysLog_add)
    public Long create( @Validated  SysLogAddRequestVo sysLogAddRequestVo) {

        log.info("新增系统日志表信息, 参数: {}", sysLogAddRequestVo);

        SysLogRequestDto requestDto = SysLogRequestDto.build().clone(sysLogAddRequestVo);

        SysLogResponseDto responseDto = sysLogService.saveSysLog(requestDto);

        if (responseDto == null) {
            log.error("新增系统日志表信息失败");
            throw ExceptionFactory.sysException(CodeEnum.SYS_UNKOWNN_FAIL);
        }

        log.info("新增系统日志表信息成功, ID: {}", responseDto.getId());
        return responseDto.getId();
    }

    /**
     * 修改系统日志表信息
     * 根据ID更新系统日志表信息
     * @param sysLogAddRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "修改系统日志表信息",
        description = "根据ID更新系统日志表信息",
        request = SysLogAddRequestVo.class,
        response = boolean.class,
        method = RequestMethodEnum.POST
    )
    @PutMapping(value = UrlCommand.sys_sysLog_modify)
    public boolean update( @Validated  SysLogAddRequestVo sysLogAddRequestVo) {

        log.info("修改系统日志表信息, 参数: {}", sysLogAddRequestVo);

        SysLogRequestDto requestDto = SysLogRequestDto.build().clone(sysLogAddRequestVo);

        if (requestDto.getId() == null || requestDto.getId() <= 0) {
            log.warn("修改系统日志表信息参数错误, ID: {}", requestDto.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        boolean result = sysLogService.updateSysLog(requestDto);

        log.info("修改系统日志表信息完成, ID: {}, 结果: {}", requestDto.getId(), result);
        return result;
    }

    /**
     * 删除系统日志表信息
     * 根据ID列表批量删除系统日志表记录
     * @param idRequestVo ID列表请求参数
     * @return Integer 删除的记录数量
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "删除系统日志表信息",
        description = "根据ID列表批量删除系统日志表记录",
        request = HttpRequestByIdListVo.class,
        response = Integer.class,
        method = RequestMethodEnum.POST
    )
    @DeleteMapping(value = UrlCommand.sys_sysLog_del)
    public Integer delete( @Validated  HttpRequestByIdListVo idRequestVo) {

        log.info("删除系统日志表信息, IDs: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除系统日志表信息参数错误, ID列表为空");
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        int result = sysLogService.delSysLogByIds(idRequestVo.getIdList());

        log.info("删除系统日志表信息完成, 删除数量: {}", result);
        return result;
    }

}
