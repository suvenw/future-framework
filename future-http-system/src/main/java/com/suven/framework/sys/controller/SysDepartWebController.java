package com.suven.framework.sys.controller;


import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.PageResult;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
 
import com.suven.framework.sys.dto.enums.SysDepartQueryEnum;
import com.suven.framework.sys.dto.request.SysDepartRequestDto;
import com.suven.framework.sys.dto.response.SysDepartResponseDto;
import com.suven.framework.sys.facade.SysDepartFacade;
import com.suven.framework.sys.service.SysDepartService;
import com.suven.framework.sys.vo.request.SysDepartAddRequestVo;
import com.suven.framework.sys.vo.request.SysDepartQueryRequestVo;
import com.suven.framework.sys.vo.response.SysDepartShowResponseVo;
import com.suven.framework.sys.vo.response.SysDepartTreeModelResponseVo;
 
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 组织机构表 Web 控制器
 *
 * RequestMapping("/sys/sysDepart")
 */
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc = DocumentConst.Sys.SYS_DOC_DES,
        module = "组织机构表模块",
        isApp = true
)
@RestController
@Slf4j
@Validated
public class SysDepartWebController {

    @Autowired
    private SysDepartFacade sysDepartFacade;

    @Autowired
    private SysDepartService sysDepartService;

    /**
     * 分页获取组织机构表信息
     * 根据查询条件分页获取组织机构表列表
     * @param sysDepartQueryRequestVo 查询请求参数
     * @return PageResult<SysDepartShowResponseVo> 分页响应结果
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
            value = "分页获取组织机构表信息",
            description = "根据条件分页查询组织机构表数据",
            request = SysDepartQueryRequestVo.class,
            response = SysDepartShowResponseVo.class,
            method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysDepart_list)
    public PageResult<SysDepartShowResponseVo> pageList(
            @Valid SysDepartQueryRequestVo sysDepartQueryRequestVo) {

        log.info("分页查询组织机构表, 参数: {}", sysDepartQueryRequestVo);

        Pager<SysDepartRequestDto> pager = new Pager<>(
                sysDepartQueryRequestVo.getPageNo(),
                sysDepartQueryRequestVo.getPageSize()
        );
        SysDepartRequestDto requestDto = SysDepartRequestDto.build()
                .clone(sysDepartQueryRequestVo);
        pager.toParamObject(requestDto);

        PageResult<SysDepartResponseDto> pageResult =
                sysDepartService.getSysDepartByNextPage(pager, SysDepartQueryEnum.DESC_ID);

        log.info("分页查询组织机构表完成, 总数: {}", pageResult.getTotal());
        return pageResult.convertBuild(SysDepartShowResponseVo.class);
    }

    /**
     * 获取组织机构树形结构
     */
    @ApiDoc(
            value = "获取组织机构树形结构",
            description = "获取组织机构树形结构列表",
            response = SysDepartTreeModelResponseVo.class,
            method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysDepart_queryTreeList)
    public List<SysDepartTreeModelResponseVo> queryTreeList() {

        log.info("查询组织机构树形结构");
        List<SysDepartTreeModelResponseVo> list = sysDepartFacade.queryTreeList();
        log.info("查询组织机构树形结构完成, 数量: {}", list.size());
        return list;
    }

    /**
     * 查看组织机构表详情
     * 根据ID获取组织机构表详细信息
     * @param idRequestVo ID请求参数
     * @return SysDepartShowResponseVo 详情响应结果
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
            value = "查看组织机构表信息",
            description = "根据ID获取组织机构表详细信息",
            request = HttpRequestByIdVo.class,
            response = SysDepartShowResponseVo.class,
            method = RequestMethodEnum.GET
    )
    @GetMapping(value = UrlCommand.sys_sysDepart_detail)
    public SysDepartShowResponseVo detail( @Validated HttpRequestByIdVo idRequestVo) {

        log.info("查询组织机构表详情, ID: {}", idRequestVo.getId());

        if (idRequestVo.getId() == null || idRequestVo.getId() <= 0) {
            log.warn("查询组织机构表详情参数错误, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysDepartResponseDto responseDto =
                sysDepartService.getSysDepartById(idRequestVo.getId());

        if (responseDto == null) {
            log.warn("组织机构表不存在, ID: {}", idRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        log.info("查询组织机构表详情成功, ID: {}", idRequestVo.getId());
        return SysDepartShowResponseVo.build().clone(responseDto);
    }

    /**
     * 新增组织机构表信息
     * 创建新的组织机构表记录
     * @param sysDepartAddRequestVo 新增请求参数
     * @return Long 新增记录的ID
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
            value = "新增组织机构表信息",
            description = "创建新的组织机构表记录",
            request = SysDepartAddRequestVo.class,
            response = Long.class,
            method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysDepart_add)
    public Long create(@Valid SysDepartAddRequestVo sysDepartAddRequestVo) {

        log.info("新增组织机构表, 参数: {}", sysDepartAddRequestVo);

        SysDepartRequestDto requestDto = SysDepartRequestDto.build()
                .clone(sysDepartAddRequestVo);

        SysDepartResponseDto responseDto =
                sysDepartService.saveSysDepart(requestDto);

        if (responseDto == null) {
            log.warn("新增组织机构表失败");
            throw ExceptionFactory.sysException(CodeEnum.SYS_UNKOWNN_FAIL);
        }

        log.info("新增组织机构表成功, ID: {}", responseDto.getId());
        return responseDto.getId();
    }

    /**
     * 修改组织机构表信息
     * 根据ID更新组织机构表信息
     * @param sysDepartAddRequestVo 修改请求参数
     * @return boolean 修改是否成功
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
            value = "修改组织机构表信息",
            description = "根据ID更新组织机构表记录",
            request = SysDepartAddRequestVo.class,
            response = boolean.class,
            method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysDepart_modify)
    public boolean update(@Valid SysDepartAddRequestVo sysDepartAddRequestVo) {

        log.info("修改组织机构表, 参数: {}", sysDepartAddRequestVo);

        if (sysDepartAddRequestVo.getId() == null || sysDepartAddRequestVo.getId() <= 0) {
            log.warn("修改组织机构表参数错误, ID: {}", sysDepartAddRequestVo.getId());
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        SysDepartRequestDto requestDto = SysDepartRequestDto.build()
                .clone(sysDepartAddRequestVo);

        boolean result = sysDepartService.updateSysDepart(requestDto);
        log.info("修改组织机构表完成, ID: {}, 结果: {}", sysDepartAddRequestVo.getId(), result);
        return result;
    }

    /**
     * 删除组织机构表信息
     * 根据ID列表批量删除组织机构表记录
     * @param idRequestVo ID列表请求参数
     * @return Integer 删除的记录数量
     * @author suven
     * @date 2025-08-18
     */
    @ApiDoc(
            value = "删除组织机构表信息",
            description = "根据ID列表批量删除组织机构表记录",
            request = HttpRequestByIdListVo.class,
            response = Integer.class,
            method = RequestMethodEnum.POST
    )
    @PostMapping(value = UrlCommand.sys_sysDepart_del)
    public int delete( @Validated HttpRequestByIdListVo idRequestVo) {

        log.info("删除组织机构表, ID列表: {}", idRequestVo.getIdList());

        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            log.warn("删除组织机构表参数错误, ID列表为空");
            throw ExceptionFactory.sysException(CodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
        }

        int result = sysDepartService.delSysDepartByIds(idRequestVo.getIdList());
        log.info("删除组织机构表完成, 删除数量: {}", result);
        return result;
    }

}
