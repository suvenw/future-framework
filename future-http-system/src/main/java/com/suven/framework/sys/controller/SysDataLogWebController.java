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
import com.suven.framework.sys.dto.enums.SysDataLogQueryEnum;
import com.suven.framework.sys.dto.request.SysDataLogRequestDto;
import com.suven.framework.sys.dto.response.SysDataLogResponseDto;
import com.suven.framework.sys.service.SysDataLogService;
import com.suven.framework.sys.vo.request.SysDataLogAddRequestVo;
import com.suven.framework.sys.vo.request.SysDataLogQueryRequestVo;
import com.suven.framework.sys.vo.response.SysDataLogShowResponseVo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 系统数据日志 Web 控制器
 *
 * RequestMapping("/sys/sysDataLog")
 */
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc = DocumentConst.Sys.SYS_DOC_DES,
        module = "系统数据日志模块",
        isApp = true
)
@RestController
@Slf4j
@Validated
public class SysDataLogWebController {

    @Autowired
    private SysDataLogService sysDataLogService;

    /**
     * 分页获取系统数据日志信息
     * 根据查询条件分页获取系统数据日志列表
     * @param sysDataLogQueryRequestVo 查询请求参数
     * @return PageResult<SysDataLogShowResponseVo> 分页响应结果
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
            value = "分页获取系统数据日志信息",
            description = "根据条件分页查询系统数据日志数据",
            request = SysDataLogQueryRequestVo.class,
            response = SysDataLogShowResponseVo.class,
            method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysDataLog_list)
    public PageResult<SysDataLogShowResponseVo> pageList(
            @Valid SysDataLogQueryRequestVo sysDataLogQueryRequestVo) {

        log.info("分页查询系统数据日志, 参数: {}", sysDataLogQueryRequestVo);

        Pager<SysDataLogRequestDto> pager = new Pager<>(
                sysDataLogQueryRequestVo.getPageNo(),
                sysDataLogQueryRequestVo.getPageSize()
        );
        SysDataLogRequestDto requestDto = SysDataLogRequestDto.build()
                .clone(sysDataLogQueryRequestVo);
        pager.toParamObject(requestDto);

        PageResult<SysDataLogResponseDto> pageResult =
                sysDataLogService.getSysDataLogByNextPage(pager, SysDataLogQueryEnum.DESC_ID);

        log.info("分页查询系统数据日志完成, 总数: {}", pageResult.getTotal());
        return pageResult.convertBuild(SysDataLogShowResponseVo.class);
    }

    /**
     * 查看系统数据日志详情
     * 根据ID获取系统数据日志详细信息
     * @param idRequestVo ID请求参数
     * @return SysDataLogShowResponseVo 详情响应结果
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
            value = "查看系统数据日志信息",
            description = "根据ID获取系统数据日志详细信息",
            request = HttpRequestByIdVo.class,
            response = SysDataLogShowResponseVo.class,
            method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysDataLog_detail)
    public SysDataLogShowResponseVo detail(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("查询系统数据日志详情, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询系统数据日志详情参数错误, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysDataLogResponseDto responseDto =
                sysDataLogService.getSysDataLogById(idRequestVo.getId());

        if (responseDto == null) {
            log.warn("系统数据日志不存在, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        log.info("查询系统数据日志详情成功, ID: {}", idRequestVo.getId());
        return SysDataLogShowResponseVo.build().clone(responseDto);
    }

    /**
     * 新增系统数据日志信息
     * 创建新的系统数据日志记录
     * @param sysDataLogAddRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
            value = "新增系统数据日志信息",
            description = "创建新的系统数据日志记录",
            request = SysDataLogAddRequestVo.class,
            response = Long.class,
            method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysDataLog_add)
    public Long create(@Valid SysDataLogAddRequestVo sysDataLogAddRequestVo) {

        log.info("新增系统数据日志, 参数: {}", sysDataLogAddRequestVo);

        SysDataLogRequestDto requestDto = SysDataLogRequestDto.build()
                .clone(sysDataLogAddRequestVo);

        SysDataLogResponseDto responseDto =
                sysDataLogService.saveSysDataLog(requestDto);

        if (responseDto == null) {
            log.warn("新增系统数据日志失败");
            throw ExceptionFactory.sysException(CodeEnum.SYS_UNKOWNN_FAIL);
        }

        log.info("新增系统数据日志成功, ID: {}", responseDto.getId());
        return responseDto.getId();
    }

    /**
     * 修改系统数据日志信息
     * 根据ID更新系统数据日志信息
     * @param sysDataLogAddRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
            value = "修改系统数据日志信息",
            description = "根据ID更新系统数据日志记录",
            request = SysDataLogAddRequestVo.class,
            response = boolean.class,
            method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysDataLog_modify)
    public boolean update(@Valid SysDataLogAddRequestVo sysDataLogAddRequestVo) {

        log.info("修改系统数据日志, 参数: {}", sysDataLogAddRequestVo);

        if (sysDataLogAddRequestVo.getId() == null || sysDataLogAddRequestVo.getId() <= 0) {
            log.warn("修改系统数据日志参数错误, ID: {}", sysDataLogAddRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysDataLogRequestDto requestDto = SysDataLogRequestDto.build()
                .clone(sysDataLogAddRequestVo);

        boolean result = sysDataLogService.updateSysDataLog(requestDto);
        log.info("修改系统数据日志完成, ID: {}, 结果: {}", sysDataLogAddRequestVo.getId(), result);
        return result;
    }

    /**
     * 删除系统数据日志信息
     * 根据ID列表批量删除系统数据日志记录
     * @param idRequestVo ID列表请求参数
     * @return Integer 删除的记录数量
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
            value = "删除系统数据日志信息",
            description = "根据ID列表批量删除系统数据日志记录",
            request = HttpRequestByIdListVo.class,
            response = Integer.class,
            method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysDataLog_del)
    public int delete(@Valid HttpRequestByIdListVo idRequestVo) {

        log.info("删除系统数据日志, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除系统数据日志参数错误, ID列表为空");
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        int result = sysDataLogService.delSysDataLogByIds(idRequestVo.getIdList());
        log.info("删除系统数据日志完成, 删除数量: {}", result);
        return result;
    }

}
