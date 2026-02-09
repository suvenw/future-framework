package com.suven.framework.sys.controller;


import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.enums.RequestMethodEnum;
import com.suven.framework.common.api.ExceptionFactory;
import com.suven.framework.sys.dto.enums.SysDictQueryEnum;
import com.suven.framework.sys.dto.request.SysDictRequestDto;
import com.suven.framework.sys.dto.response.SysDictResponseDto;
import com.suven.framework.sys.service.SysDictService;
import com.suven.framework.sys.vo.request.SysDictAddRequestVo;
import com.suven.framework.sys.vo.request.SysDictQueryRequestVo;
import com.suven.framework.sys.vo.response.SysDictShowResponseVo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 后台字典类型表 Web 控制器
 *
 * RequestMapping("/sys/sysDict")
 */
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc = DocumentConst.Sys.SYS_DOC_DES,
        module = "后台字典类型表模块",
        isApp = true
)
@RestController
@Slf4j
@Validated
public class SysDictWebController {

    @Autowired
    private SysDictService sysDictService;

    /**
     * 分页获取后台字典类型表信息
     * 根据查询条件分页获取后台字典类型表列表
     * @param sysDictQueryRequestVo 查询请求参数
     * @return PageResult<SysDictShowResponseVo> 分页响应结果
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
            value = "分页获取后台字典类型表信息",
            description = "根据条件分页查询后台字典类型表数据",
            request = SysDictQueryRequestVo.class,
            response = SysDictShowResponseVo.class,
            method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysDict_list)
    public PageResult<SysDictShowResponseVo> pageList(
            @Valid SysDictQueryRequestVo sysDictQueryRequestVo) {

        log.info("分页查询后台字典类型表, 参数: {}", sysDictQueryRequestVo);

        Pager<SysDictRequestDto> pager = new Pager<>(
                sysDictQueryRequestVo.getPageNo(),
                sysDictQueryRequestVo.getPageSize()
        );
        SysDictRequestDto requestDto = SysDictRequestDto.build()
                .clone(sysDictQueryRequestVo);
        pager.toParamObject(requestDto);

        PageResult<SysDictResponseDto> pageResult =
                sysDictService.getSysDictByNextPage(pager, SysDictQueryEnum.DESC_ID);

        log.info("分页查询后台字典类型表完成, 总数: {}", pageResult.getTotal());
        return pageResult.convertBuild(SysDictShowResponseVo.class);
    }

    /**
     * 查看后台字典类型表详情
     * 根据ID获取后台字典类型表详细信息
     * @param idRequestVo ID请求参数
     * @return SysDictShowResponseVo 详情响应结果
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
            value = "查看后台字典类型表信息",
            description = "根据ID获取后台字典类型表详细信息",
            request = HttpRequestByIdVo.class,
            response = SysDictShowResponseVo.class,
            method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysDict_detail)
    public SysDictShowResponseVo detail(@Valid HttpRequestByIdVo idRequestVo) {

        log.info("查询后台字典类型表详情, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询后台字典类型表详情参数错误, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysDictResponseDto responseDto =
                sysDictService.getSysDictById(idRequestVo.getId());

        if (responseDto == null) {
            log.warn("后台字典类型表不存在, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        log.info("查询后台字典类型表详情成功, ID: {}", idRequestVo.getId());
        return SysDictShowResponseVo.build().clone(responseDto);
    }

    /**
     * 新增后台字典类型表信息
     * 创建新的后台字典类型表记录
     * @param sysDictAddRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
            value = "新增后台字典类型表信息",
            description = "创建新的后台字典类型表记录",
            request = SysDictAddRequestVo.class,
            response = Long.class,
            method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysDict_add)
    public Long create(@Valid SysDictAddRequestVo sysDictAddRequestVo) {

        log.info("新增后台字典类型表, 参数: {}", sysDictAddRequestVo);

        SysDictRequestDto requestDto = SysDictRequestDto.build()
                .clone(sysDictAddRequestVo);

        SysDictResponseDto responseDto =
                sysDictService.saveSysDict(requestDto);

        if (responseDto == null) {
            log.warn("新增后台字典类型表失败");
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
        }

        log.info("新增后台字典类型表成功, ID: {}", responseDto.getId());
        return responseDto.getId();
    }

    /**
     * 修改后台字典类型表信息
     * 根据ID更新后台字典类型表信息
     * @param sysDictAddRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
            value = "修改后台字典类型表信息",
            description = "根据ID更新后台字典类型表记录",
            request = SysDictAddRequestVo.class,
            response = boolean.class,
            method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysDict_modify)
    public boolean update(@Valid SysDictAddRequestVo sysDictAddRequestVo) {

        log.info("修改后台字典类型表, 参数: {}", sysDictAddRequestVo);

        if (sysDictAddRequestVo.getId() == null || sysDictAddRequestVo.getId() <= 0) {
            log.warn("修改后台字典类型表参数错误, ID: {}", sysDictAddRequestVo.getId());
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysDictRequestDto requestDto = SysDictRequestDto.build()
                .clone(sysDictAddRequestVo);

        boolean result = sysDictService.updateSysDict(requestDto);
        log.info("修改后台字典类型表完成, ID: {}, 结果: {}", sysDictAddRequestVo.getId(), result);
        return result;
    }

    /**
     * 删除后台字典类型表信息
     * 根据ID列表批量删除后台字典类型表记录
     * @param idRequestVo ID列表请求参数
     * @return Integer 删除的记录数量
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
            value = "删除后台字典类型表信息",
            description = "根据ID列表批量删除后台字典类型表记录",
            request = HttpRequestByIdListVo.class,
            response = Integer.class,
            method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysDict_del)
    public int delete(@Valid HttpRequestByIdListVo idRequestVo) {

        log.info("删除后台字典类型表, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除后台字典类型表参数错误, ID列表为空");
            throw ExceptionFactory.sysException(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        int result = sysDictService.delSysDictByIds(idRequestVo.getIdList());
        log.info("删除后台字典类型表完成, 删除数量: {}", result);
        return result;
    }

}
