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
import com.suven.framework.sys.dto.enums.SysPositionQueryEnum;
import com.suven.framework.sys.dto.request.SysPositionRequestDto;
import com.suven.framework.sys.dto.response.SysPositionResponseDto;
import com.suven.framework.sys.service.SysPositionService;
import com.suven.framework.sys.vo.request.SysPositionAddRequestVo;
import com.suven.framework.sys.vo.request.SysPositionQueryRequestVo;
import com.suven.framework.sys.vo.response.SysPositionShowResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: SysPositionWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:13:52
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 岗位表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysPosition")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/

@RestController
@Slf4j
@Validated
@ApiDoc(
    group = DocumentConst.Sys.SYS_DOC_GROUP,
    groupDesc = DocumentConst.Sys.SYS_DOC_DES,
    module = "岗位表模块",
    isApp = true
)
public class SysPositionWebController {

    @Autowired
    private SysPositionService  sysPositionService;

    /**
     * 分页获取岗位表信息
     * 根据查询条件分页获取岗位表列表
     * @param sysPositionQueryRequestVo 查询请求参数
     * @return PageResult<SysPositionShowResponseVo> 分页响应结果
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "分页获取岗位表信息",
        description = "根据条件分页查询岗位表数据",
        request = SysPositionQueryRequestVo.class,
        response = SysPositionShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysPosition_list)
    public PageResult<SysPositionShowResponseVo> pageList(
            @Validated  SysPositionQueryRequestVo sysPositionQueryRequestVo) {

        log.info("分页查询岗位表, 参数: {}", sysPositionQueryRequestVo);

        Pager<SysPositionRequestDto> pager = new Pager<>(
                sysPositionQueryRequestVo.getPageNo(),
                sysPositionQueryRequestVo.getPageSize()
        );
        SysPositionRequestDto requestDto = SysPositionRequestDto.build()
                .clone(sysPositionQueryRequestVo);
        pager.toParamObject(requestDto);

        PageResult<SysPositionResponseDto> pageResult =
                sysPositionService.getSysPositionByNextPage(pager, SysPositionQueryEnum.DESC_ID);

        log.info("分页查询岗位表完成, 总数: {}", pageResult.getTotal());
        return pageResult.convertBuild(SysPositionShowResponseVo.class);
    }

    /**
     * 查看岗位表详情
     * 根据ID获取岗位表详细信息
     * @param idRequestVo ID请求参数
     * @return SysPositionShowResponseVo 详情响应结果
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "查看岗位表信息",
        description = "根据ID获取岗位表详细信息",
        request = HttpRequestByIdVo.class,
        response = SysPositionShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysPosition_detail)
    public SysPositionShowResponseVo detail( @Validated HttpRequestByIdVo idRequestVo) {

        log.info("查询岗位表详情, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询岗位表详情参数错误, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysPositionResponseDto responseDto =
                sysPositionService.getSysPositionById(idRequestVo.getId());

        if (responseDto == null) {
            log.warn("岗位表不存在, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        log.info("查询岗位表详情成功, ID: {}", idRequestVo.getId());
        return SysPositionShowResponseVo.build().clone(responseDto);
    }

    /**
     * 新增岗位表信息
     * 创建新的岗位表记录
     * @param sysPositionAddRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "新增岗位表信息",
        description = "创建新的岗位表记录",
        request = SysPositionAddRequestVo.class,
        response = Long.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysPosition_add)
    public Long create( @Validated SysPositionAddRequestVo sysPositionAddRequestVo) {

        log.info("新增岗位表信息, 参数: {}", sysPositionAddRequestVo);

        SysPositionRequestDto requestDto = SysPositionRequestDto.build()
                .clone(sysPositionAddRequestVo);

        SysPositionResponseDto responseDto =
                sysPositionService.saveSysPosition(requestDto);

        if (responseDto == null) {
            log.warn("新增岗位表失败");
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
        }

        log.info("新增岗位表成功, ID: {}", responseDto.getId());
        return responseDto.getId();
    }

    /**
     * 修改岗位表信息
     * 根据ID更新岗位表信息
     * @param sysPositionAddRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "修改岗位表信息",
        description = "根据ID更新岗位表记录",
        request = SysPositionAddRequestVo.class,
        response = boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysPosition_modify)
    public boolean update( @Validated SysPositionAddRequestVo sysPositionAddRequestVo) {

        log.info("修改岗位表信息, 参数: {}", sysPositionAddRequestVo);

        SysPositionRequestDto requestDto = SysPositionRequestDto.build()
                .clone(sysPositionAddRequestVo);

        if (requestDto.getId() == null || requestDto.getId() <= 0) {
            log.warn("修改岗位表参数错误, ID: {}", requestDto.getId());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        boolean result = sysPositionService.updateSysPosition(requestDto);
        log.info("修改岗位表完成, ID: {}, 结果: {}", requestDto.getId(), result);
        return result;
    }

    /**
     * 删除岗位表信息
     * 根据ID列表批量删除岗位表记录
     * @param idRequestVo ID列表请求参数
     * @return Integer 删除的记录数量
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "删除岗位表信息",
        description = "根据ID列表批量删除岗位表记录",
        request = HttpRequestByIdListVo.class,
        response = Integer.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysPosition_del)
    public int delete( @Validated HttpRequestByIdListVo idRequestVo) {

        log.info("删除岗位表, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除岗位表参数错误, ID列表为空");
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        int result = sysPositionService.delSysPositionByIds(idRequestVo.getIdList());
        log.info("删除岗位表完成, 删除数量: {}", result);
        return result;
    }

}
