package com.suven.framework.sys.controller;


import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.IterableConvert;
import com.suven.framework.http.api.ApiDoc;
import com.suven.framework.http.api.DocumentConst;
import com.suven.framework.http.data.entity.Pager;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.ResponseResultPageVo;
import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.http.handler.OutputSystem;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.sys.dto.enums.SysPermissionDataRuleQueryEnum;
import com.suven.framework.sys.dto.enums.SysPermissionQueryEnum;
import com.suven.framework.sys.dto.request.SysPermissionRequestDto;
import com.suven.framework.sys.dto.response.SysPermissionDataRuleResponseDto;
import com.suven.framework.sys.dto.response.SysPermissionResponseDto;
import com.suven.framework.sys.facade.SysPermissionFacade;
import com.suven.framework.sys.facade.SysRolePermissionFacade;
import com.suven.framework.sys.service.SysPermissionDataRuleService;
import com.suven.framework.sys.service.SysPermissionService;
import com.suven.framework.sys.service.SysRolePermissionService;
import com.suven.framework.sys.vo.request.SysPermissionAddRequestVo;
import com.suven.framework.sys.vo.request.SysPermissionDataRuleIdRequestVo;
import com.suven.framework.sys.vo.request.SysPermissionQueryRequestVo;
import com.suven.framework.sys.vo.request.SysPermissionRequestVo;
import com.suven.framework.sys.vo.request.SysRolePermissionSaveRequestVo;
import com.suven.framework.sys.vo.request.SysRoleQueryRequestVo;
import com.suven.framework.sys.vo.request.TokenRequestVo;
import com.suven.framework.sys.vo.response.SysPermissionResponseVo;
import com.suven.framework.sys.vo.response.SysPermissionShowResponseVo;
import com.suven.framework.sys.vo.response.SysPermissionTreeResponseVo;
import com.suven.framework.sys.vo.response.SysRoleShowResponseVo;
import com.suven.framework.util.excel.ExcelUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SysPermissionWebController.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:30
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  Description: 菜单权限表 的控制服务类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * @RequestMapping("/sys/sysPermission")
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


@Controller
@ApiDoc(
        group = DocumentConst.Sys.SYS_DOC_GROUP,
        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
        module = "菜单权限表模块"
)
public class SysPermissionWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());



    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysPermissionFacade sysPermissionFacade;

    @Autowired
    private SysPermissionDataRuleService sysPermissionDataRuleService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Autowired
    private SysRolePermissionFacade sysRolePermissionFacade;





    /**
     * @Title: 跳转到菜单权限表主界面
     * @return 字符串url
     * @author suven
     * @date 2022-02-28 16:10:30
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value =  UrlCommand.sys_sysPermission_index,method = RequestMethod.GET)
    //@RequiresPermissions("sys:permission:list")
    public String index(){
        return "sys/sysPermission_index";
    }


    /**
     * @Title: 获取菜单权限表分页信息
     * Description:sysPermissionQueryRequestVo @{Link SysPermissionQueryRequestVo}
     * @param
     * @return  ResponseResultPageVo 对象 List<SysPermissionShowResponseVo>
     * @throw
     * @author suven
     * @date 2022-02-28 16:10:30
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取菜单权限表分页信息",
            request = SysPermissionQueryRequestVo.class,
            response = SysPermissionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPermission_list,method = RequestMethod.GET)
    //@RequiresPermissions("sys:permission:list")
    public   void   list(OutputSystem out, SysPermissionQueryRequestVo sysPermissionQueryRequestVo){
            SysPermissionRequestDto sysPermissionRequestDto = SysPermissionRequestDto.build( ).clone(sysPermissionQueryRequestVo);

        Pager<SysPermissionRequestDto> page =  Pager.build();
        page.toPageSize(sysPermissionQueryRequestVo.getPageSize()).toPageNo(sysPermissionQueryRequestVo.getPageNo());
        page.toParamObject(sysPermissionRequestDto );
         SysPermissionQueryEnum queryEnum =  SysPermissionQueryEnum.DESC_ID;
        ResponseResultPageVo<SysPermissionResponseDto> resultList = sysPermissionService.getSysPermissionByNextPage(page,queryEnum);
        if(null == resultList || resultList.getList().isEmpty() ){
            out.write( new ResponseResultPageVo<>());
            return ;
        }

        List<SysPermissionShowResponseVo> listVo = IterableConvert.convertList(resultList.getList(),SysPermissionShowResponseVo.class);
        ResponseResultPageVo<SysPermissionShowResponseVo> result = new ResponseResultPageVo<>();
        result.of(listVo,page.getSize(),resultList.getTotal());
        out.write( result);
    }





    /**
     * @Title: 新增菜单权限表信息
     * Description:sysPermissionAddRequestVo @{Link SysPermissionAddRequestVo}
     * @return long类型id
     * @author suven
     * @date 2022-02-28 16:10:30
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取菜单权限表分页信息",
            request = SysPermissionQueryRequestVo.class,
            response = SysPermissionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPermission_queryList,method = RequestMethod.GET)
    //@RequiresPermissions("sys:permission:list")
    public   void   queryList( OutputSystem out){
        Map<String, Object> map = this.sysPermissionFacade.queryPermissionTreeList();
        out.write(map, new String[0]);
    }


    @ApiDoc(
            value = "获取角色权限列表",
            request = SysRoleQueryRequestVo.class,
            response = SysRoleShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_role_query_queryRolePermission,method = RequestMethod.GET)
    //@RequiresPermissions({"sys:sysPermission:queryRolePermission"})
    public void queryRolePermission(OutputSystem out, SysRolePermissionSaveRequestVo requestVo) {
        List<String> list = this.sysRolePermissionFacade.queryRolePermission(requestVo);
        out.write(list, new String[0]);
    }

    @ApiDoc(
            value = "获取菜单列表",
            request = SysPermissionRequestVo.class,
            response = SysPermissionTreeResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_role_query_getSystemMenuList,method = RequestMethod.GET)
    //@RequiresPermissions({"sys:permission:list"})
    public void getSystemMenuList(OutputResponse out, SysPermissionRequestVo sysPermissionRequestVo) {
        List<SysPermissionTreeResponseVo> resultList = this.sysPermissionFacade.getSysPermissionByList(sysPermissionRequestVo);
        out.write(resultList, new String[0]);
    }


    @ApiDoc(
            value = "获取子菜单列表",
            request = SysPermissionRequestVo.class,
            response = SysPermissionTreeResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_role_query_getSystemSubmenu,method = RequestMethod.GET)
    //@RequiresPermissions({"sys:permission:list"})
    public void getSystemSubmenu(OutputResponse out, SysPermissionRequestVo requestVo) {
        List<SysPermissionTreeResponseVo> vos = this.sysPermissionFacade.getSystemSubmenu(requestVo);
        out.write(vos, new String[0]);
    }




    @ApiDoc(
            value = "获取树形角色列表",
            request = SysRoleQueryRequestVo.class,
            response = SysRoleShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_role_query_tree_list,method = RequestMethod.GET)
    //@RequiresPermissions({"sys:permission:list"})
    public void queryTreeList(OutputSystem out) {
//        SysPermissionTreeRequestVo vo = this.sysPermissionFacade.queryTreeList();
//        out.write(vo);
        Map<String, Object> map = this.sysPermissionFacade.queryTreeList();
        out.write(map, new String[0]);
    }


    @ApiDoc(
            value = "保存角色权限信息",
            request = SysRolePermissionSaveRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPermission_saveRolePermission,method = RequestMethod.POST)
    //@RequiresPermissions({"sys:sysPermission:saveRolePermission"})
    public void saveRolePermission(OutputSystem out, SysRolePermissionSaveRequestVo requestVo) {
        IResultCodeEnum sysMsgEnum = this.sysRolePermissionFacade.saveRolePermission(requestVo);
        out.write(true);
    }

    /**
     * @Title: 新增菜单权限表信息
     * Description:sysPermissionAddRequestVo @{Link SysPermissionAddRequestVo}
     * @param sysPermissionAddRequestVo 对象
     * @return long类型id
     * @author suven
     * @date 2022-02-28 16:10:30
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增菜单权限表信息",
            request = SysPermissionAddRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPermission_add,method = RequestMethod.POST)
    //@RequiresPermissions("sys:permission:add")
    public  void  add(OutputSystem out, SysPermissionAddRequestVo sysPermissionAddRequestVo){

            SysPermissionRequestDto sysPermissionRequestDto =  SysPermissionRequestDto.build().clone(sysPermissionAddRequestVo);

            //sysPermissionRequestDto.setStatus(TbStatusEnum.ENABLE.index());
            SysPermissionResponseDto sysPermissionresponseDto =  sysPermissionService.saveSysPermission(sysPermissionRequestDto);
        if(sysPermissionresponseDto == null){
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write( sysPermissionresponseDto.getId());
    }
    /**
     * @Title: 修改菜单权限表信息
     * Description:sysPermissionAddRequestVo @{Link SysPermissionAddRequestVo}
     * @param  sysPermissionAddRequestVo 对象
     * @return  boolean 类型1或0;
     * @author suven
     * @date 2022-02-28 16:10:30
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改菜单权限表信息",
            request = SysPermissionAddRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPermission_modify , method = RequestMethod.POST)
    //@RequiresPermissions("sys:permission:modify")
    public  void  modify(OutputSystem out,SysPermissionAddRequestVo sysPermissionAddRequestVo){

            SysPermissionRequestDto sysPermissionRequestDto =  SysPermissionRequestDto.build().clone(sysPermissionAddRequestVo);

        if(sysPermissionRequestDto.getId() == 0){
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result =  sysPermissionService.updateSysPermission(sysPermissionRequestDto);
        out.write(result);
    }

    /**
     * @Title: 查看菜单权限表信息
     * Description:sysPermissionRequestVo @{Link SysPermissionRequestVo}
     * @param
     * @return  SysPermissionResponseVo  对象
     * @author suven
     * @date 2022-02-28 16:10:30
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看菜单权限表信息",
            request = HttpRequestByIdVo.class,
            response = SysPermissionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPermission_detail,method = RequestMethod.GET)
    //@RequiresPermissions("sys:permission:list")
    public void detail(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysPermissionResponseDto sysPermissionResponseDto = sysPermissionService.getSysPermissionById(idRequestVo.getId());
            SysPermissionShowResponseVo vo =  SysPermissionShowResponseVo.build().clone(sysPermissionResponseDto);
        out.write(vo);
    }



    /**
     * @Title: 跳转菜单权限表编辑界面
     * Description:id @{Link Long}
     * @param
     * @return SysPermissionShowResponseVo 对象
     * @author suven
     * @date 2022-02-28 16:10:30
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看菜单权限表信息",
            request = HttpRequestByIdVo.class,
            response = SysPermissionShowResponseVo.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPermission_edit , method = RequestMethod.GET)
    //@RequiresPermissions("sys:permission:list")
    public void edit(OutputSystem out, HttpRequestByIdVo idRequestVo){

            SysPermissionResponseDto sysPermissionResponseDto = sysPermissionService.getSysPermissionById(idRequestVo.getId());
            SysPermissionShowResponseVo vo =  SysPermissionShowResponseVo.build().clone(sysPermissionResponseDto);
        out.write(vo);

    }




    /**
     * @Title: 跳转菜单权限表新增编辑界面
     * Description:id @{Link Long}
     * @param
     * @return  返回新增加的url
     * @author suven
     * @date 2022-02-28 16:10:30
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @RequestMapping(value = UrlCommand.sys_sysPermission_newInfo , method = RequestMethod.GET)
    //@RequiresPermissions("sys:permission:add")
    public String newInfo(ModelMap modelMap){
        return "sys/sysPermission_edit";
    }

    /**
     * @Title: 删除菜单权限表信息
     * Description:id @{Link Long}
     * @param
     * @return   boolean 类型1或0;
     * @author suven
     * @date 2022-02-28 16:10:30
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除菜单权限表信息",
            request = HttpRequestByIdListVo.class,
            response = Integer.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPermission_del,method = RequestMethod.POST)
    //@RequiresPermissions("sys:permission:del")
    public  void  del(OutputSystem out, HttpRequestByIdListVo idRequestVo){
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return ;
        }
        int result = sysPermissionService.delSysPermissionByIds(idRequestVo.getIdList());
        out.write(result);
    }



    /**
     * @Title: 导出菜单权限表信息
     * Description:id @{Link Long}
     * @param
     * @return
     * @author suven
     * @date 2022-02-28 16:10:30
     *  --------------------------------------------------------
     *  modifier    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出菜单权限表信息",
            request = SysPermissionQueryRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = UrlCommand.sys_sysPermission_export,method = RequestMethod.GET)
    //@RequiresPermissions("sys:permission:export")
    public void export(HttpServletResponse response, SysPermissionQueryRequestVo sysPermissionQueryRequestVo){

            SysPermissionRequestDto sysPermissionRequestDto = SysPermissionRequestDto.build().clone(sysPermissionQueryRequestVo);

        Pager page =  Pager.build();
        pager.toPageSize(sysPermissionQueryRequestVo.getPageSize()).toPageNo(sysPermissionQueryRequestVo.getPageNo());
        page.toParamObject(sysPermissionRequestDto );

        SysPermissionQueryEnum queryEnum =  SysPermissionQueryEnum.DESC_ID;
        ResponseResultPageVo<SysPermissionResponseDto> resultList = sysPermissionService.getSysPermissionByNextPage(page,queryEnum);
        List<SysPermissionResponseDto> data = resultList.getList();

        //写入文件
        try {
            OutputStream outputStream = response.getOutputStream();
            ExcelUtils.writeExcel(outputStream, SysPermissionResponseVo.class,data,"导出菜单权限表信息");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
    * 通过excel导入数据
    * @param out
    * @param files
    */
    @RequestMapping(value = UrlCommand.sys_sysPermission_import, method = RequestMethod.POST)
    //@RequiresPermissions("sys:permission:import")
    public void importExcel(OutputSystem out, @PathVariable("files") MultipartFile files) {
        //写入文件
        try {
            InputStream initialStream = files.getInputStream();
            boolean result = sysPermissionService.saveData(initialStream);
            out.write(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


    

    /**
     * 查询用户拥有的菜单权限和按钮权限（根据TOKEN）
     *
     * @return
     */
    @RequestMapping(value = UrlCommand.sys_sysPermission_get, method = RequestMethod.POST)
    //@RequiresPermissions("sys:sysPermission:getByToken")
    public void getUserPermissionByToken(OutputSystem out, TokenRequestVo tokenRequestVo) {
        Object object = sysPermissionFacade.getUserPermissionByToken(tokenRequestVo);
        out.write(object);
    }





    /**
     * 根据菜单id来获取其对应的权限数据
     *
     * @param sysPermissionDataRule
     * @return
     */
    @RequestMapping(value = UrlCommand.sys_permission_get_perm_rule_list_byId, method = RequestMethod.GET)
    //@RequiresPermissions("sys:sysPermission:getListById")
    public void getPermRuleListByPermId(OutputSystem out, SysPermissionDataRuleIdRequestVo sysPermissionDataRule) {
        List<SysPermissionDataRuleResponseDto> permRuleList = sysPermissionDataRuleService
                .getSysPermissionDataRuleListByQuery(sysPermissionDataRule, SysPermissionDataRuleQueryEnum.PERMISSION_ID);
        ResponseResultPageVo<SysPermissionDataRuleResponseDto> result = new ResponseResultPageVo().toList(permRuleList);
        out.write(result);
    }

    /**
     * 根据菜单id来获取其对应的权限数据
     *
     * @param tokenRequestVo
     * @return
     */
    @ApiDoc(
            value = "根据菜单id来获取其对应的权限数据",
            request = TokenRequestVo.class,
            response = SysPermissionResponseDto.class
    )
    @RequestMapping(value = UrlCommand.sys_user_queryUserRolePermission, method = RequestMethod.POST)
    //@RequiresPermissions("sys:sysPermission:getByToken")
    public void getPermRuleListByUserId(OutputSystem out, TokenRequestVo tokenRequestVo) {
        List<SysPermissionResponseDto> resultList = sysPermissionService.queryByUser(tokenRequestVo.getUserId());
        List<SysPermissionShowResponseVo> listVo = IterableConvert.convertList(resultList,SysPermissionShowResponseVo.class);
        out.write(listVo);
    }

}