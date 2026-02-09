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
import com.suven.framework.sys.dto.enums.SysThirdAccountQueryEnum;
import com.suven.framework.sys.dto.request.SysThirdAccountRequestDto;
import com.suven.framework.sys.dto.response.SysThirdAccountResponseDto;
import com.suven.framework.sys.service.SysThirdAccountService;
import com.suven.framework.sys.vo.request.SysThirdAccountAddRequestVo;
import com.suven.framework.sys.vo.request.SysThirdAccountQueryRequestVo;
import com.suven.framework.sys.vo.response.SysThirdAccountShowResponseVo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * ClassName: SysThirdAccountWebController.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:09:47
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 第三方登陆表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * RequestMapping("/sys/sysThirdAccount")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/

@RestController
@Slf4j
@Validated
@ApiDoc(
    group = DocumentConst.Sys.SYS_DOC_GROUP,
    groupDesc = DocumentConst.Sys.SYS_DOC_DES,
    module = "第三方登陆表模块",
    isApp = true
)
public class SysThirdAccountWebController {

    @Autowired
    private SysThirdAccountService  sysThirdAccountService;

    /**
     * 分页获取第三方登陆表信息
     * 根据查询条件分页获取第三方登陆表列表
     * @param sysThirdAccountQueryRequestVo 查询请求参数
     * @return PageResult<SysThirdAccountShowResponseVo> 分页响应结果
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "分页获取第三方登陆表信息",
        description = "根据条件分页查询第三方登陆表数据",
        request = SysThirdAccountQueryRequestVo.class,
        response = SysThirdAccountShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysThirdAccount_list)
    public PageResult<SysThirdAccountShowResponseVo> pageList(
            @Valid SysThirdAccountQueryRequestVo sysThirdAccountQueryRequestVo) {

        log.info("分页查询第三方登陆表, 参数: {}", sysThirdAccountQueryRequestVo);

        Pager<SysThirdAccountRequestDto> pager = new Pager<>(
                sysThirdAccountQueryRequestVo.getPageNo(),
                sysThirdAccountQueryRequestVo.getPageSize()
        );
        SysThirdAccountRequestDto requestDto = SysThirdAccountRequestDto.build()
                .clone(sysThirdAccountQueryRequestVo);
        pager.toParamObject(requestDto);

        PageResult<SysThirdAccountResponseDto> pageResult =
                sysThirdAccountService.getSysThirdAccountByNextPage(
                        pager, SysThirdAccountQueryEnum.DESC_ID);

        log.info("分页查询第三方登陆表完成, 总数: {}", pageResult.getTotal());
        return pageResult.convertBuild(SysThirdAccountShowResponseVo.class);
    }

    /**
     * 查看第三方登陆表详情
     * 根据ID获取第三方登陆表详细信息
     * @param idRequestVo ID请求参数
     * @return SysThirdAccountShowResponseVo 详情响应结果
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "查看第三方登陆表信息",
        description = "根据ID获取第三方登陆表详细信息",
        request = HttpRequestByIdVo.class,
        response = SysThirdAccountShowResponseVo.class,
        method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysThirdAccount_detail)
    public SysThirdAccountShowResponseVo detail(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("查询第三方登陆表详情, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询第三方登陆表详情参数错误, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysThirdAccountResponseDto responseDto =
                sysThirdAccountService.getSysThirdAccountById(idRequestVo.getId());

        if (responseDto == null) {
            log.warn("第三方登陆表不存在, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        log.info("查询第三方登陆表详情成功, ID: {}", idRequestVo.getId());
        return SysThirdAccountShowResponseVo.build().clone(responseDto);
    }

    /**
     * 新增第三方登陆表信息
     * 创建新的第三方登陆表记录
     * @param sysThirdAccountAddRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "新增第三方登陆表信息",
        description = "创建新的第三方登陆表记录",
        request = SysThirdAccountAddRequestVo.class,
        response = Long.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysThirdAccount_add)
    public Long create(@Valid SysThirdAccountAddRequestVo sysThirdAccountAddRequestVo) {

        log.info("新增第三方登陆表信息, 参数: {}", sysThirdAccountAddRequestVo);

        SysThirdAccountRequestDto requestDto = SysThirdAccountRequestDto.build()
                .clone(sysThirdAccountAddRequestVo);

        SysThirdAccountResponseDto responseDto =
                sysThirdAccountService.saveSysThirdAccount(requestDto);

        if (responseDto == null) {
            log.warn("新增第三方登陆表失败");
            throw ExceptionFactory.sysException(CodeEnum.SYS_UNKOWNN_FAIL);
        }

        log.info("新增第三方登陆表成功, ID: {}", responseDto.getId());
        return responseDto.getId();
    }

    /**
     * 修改第三方登陆表信息
     * 根据ID更新第三方登陆表信息
     * @param sysThirdAccountAddRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "修改第三方登陆表信息",
        description = "根据ID更新第三方登陆表记录",
        request = SysThirdAccountAddRequestVo.class,
        response = boolean.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysThirdAccount_modify)
    public boolean update(@Valid SysThirdAccountAddRequestVo sysThirdAccountAddRequestVo) {

        log.info("修改第三方登陆表信息, 参数: {}", sysThirdAccountAddRequestVo);

        SysThirdAccountRequestDto requestDto = SysThirdAccountRequestDto.build()
                .clone(sysThirdAccountAddRequestVo);

        if (requestDto.getId() == null || requestDto.getId() <= 0) {
            log.warn("修改第三方登陆表参数错误, ID: {}", requestDto.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        boolean result = sysThirdAccountService.updateSysThirdAccount(requestDto);
        log.info("修改第三方登陆表完成, ID: {}, 结果: {}", requestDto.getId(), result);
        return result;
    }

    /**
     * 删除第三方登陆表信息
     * 根据ID列表批量删除第三方登陆表记录
     * @param idRequestVo ID列表请求参数
     * @return Integer 删除的记录数量
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
        value = "删除第三方登陆表信息",
        description = "根据ID列表批量删除第三方登陆表记录",
        request = HttpRequestByIdListVo.class,
        response = Integer.class,
        method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysThirdAccount_del)
    public int delete(@Valid HttpRequestByIdListVo idRequestVo) {

        log.info("删除第三方登陆表, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除第三方登陆表参数错误, ID列表为空");
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        int result = sysThirdAccountService.delSysThirdAccountByIds(idRequestVo.getIdList());
        log.info("删除第三方登陆表完成, 删除数量: {}", result);
        return result;
    }

}
