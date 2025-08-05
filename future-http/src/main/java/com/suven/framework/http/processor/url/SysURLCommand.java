package com.suven.framework.http.processor.url;


import com.suven.framework.http.processor.url.annotations.CDN;
import com.suven.framework.http.processor.url.annotations.UrlRemote;

import java.util.concurrent.TimeUnit;


/**
 * @author Joven.wang
 * @version V1.0
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Title: SysURLCommand.java
 * date 2019-10-18 12:35:25
 * Description: (说明) http get/post 接口 url 系统级别业务;
 */


@CDN
@UrlRemote
public class SysURLCommand implements IURLCommand {

    /** 架构使用例子接口 start**/
    public static final String sys_get_framework = "";
    public static final String sys_get_framework_http = "/http";
    public static final String sys_get_error_no = "/sys/errorNo";
    public static final String sys_get_post_param = "/sys/postParam";
    public static final String sys_get_get_param = "/sys/getParam";
    public static final String sys_get_sign_param = "/sys/signParam";
    public static final String sys_get_base_test = "/sys/testBase";
    @CDN(value = 1, unit = TimeUnit.MINUTES)
    public static final String sys_get_cache_test = "/sys/testCache";
    public static final String sys_get_aes_test = "/sys/testAes";
    @CDN(value = 1, unit = TimeUnit.MINUTES)
    public static final String sys_get_aes_cache_test = "/sys/testAesCache";
    public static final String sys_get_service_api_doc = "/v2/api-docs";
    /** 架构使用例子接口 end **/

    /**
     * 登录相关
     **/
    @UrlRemote
    public static final String sys_login = "/sys/login";

    /**
     * 登录相关
     **/
    @UrlRemote
    public static final String sys_loginToken = "/sys/loginToken";

    @UrlRemote
    public static final String sys_logout = "/sys/logout";


    @UrlRemote
    public static final String sys_get_random_image = "/sys/randomImage";

    @UrlRemote
    public static final String sys_get_check_code = "/sys/getCheckCode";

    public static final String sys_get_duplicate_check = "/sys/duplicate/check";

    public static final String sys_permission_list = "/sys/permission/list";

    public static final String sys_permission_get_system_menu_list = "/sys/permission/getSystemMenuList";

    public static final String sys_permission_get_perm_rule_list_byId = "/sys/permission/getPermRuleListByPermId";

    public static final String sys_permission_get_tree_list = "/sys/permission/queryTreeList";

    public static final String sys_permission_add = "/sys/permission/add";

    public static final String sys_permission_edit = "/sys/permission/edit";

    public static final String sys_permission_detail = "/sys/permission/detail";

    public static final String sys_permission_get_submenu = "/sys/permission/getSystemSubmenu";

    public static final String sys_permission_del_batch = "/sys/permission/deleteBatch";

    public static final String sys_sysPermission_role_save = "/sys/permission/saveRolePermission";

    public static final String sys_sysPermission_get_role_permission = "/sys/permission/queryRolePermission";


    public static final String sys_user_list = "/sys/user/list";
    public static final String sys_user_queryByUserName = "/sys/sysUserAgent/queryByUserName";

    public static final String sys_user_add = "/sys/user/add";

    public static final String sys_user_del = "/sys/user/delete";


    public static final String sys_user_frozen_batch = "/sys/user/frozenBatch";

    public static final String sys_user_handle_muted = "/sys/user/handleMuted";

    public static final String sys_user_handle_ban = "/sys/user/handleBan";

    public static final String sys_role_query_tree_list = "/sys/role/queryTreeList";

    public static final String sys_role_list = "/sys/role/list";

    /**
     * 角色
     */
    @CDN(value = 3, unit = TimeUnit.DAYS)
    public static final String sys_role_index = "/sys/role/index";

    @UrlRemote
    public static final String sys_role_add = "/sys/role/add";
    public static final String sys_role_modify = "/sys/role/modify";
    public static final String sys_role_detail = "/sys/role/detail";
    public static final String sys_role_edit = "/sys/role/edit";
    public static final String sys_role_newInfo = "/sys/role/newInfo";
    public static final String sys_role_del = "/sys/role/deleteBatch";
    public static final String sys_role_sort = "/sys/role/sort";
    public static final String sys_role_turnOn = "/sys/role/turnOn";
    public static final String sys_role_turnOff = "/sys/role/turnOff";
    public static final String sys_role_export = "/sys/role/export";

    /**
     * 部门组织
     */
    public static final String sys_sysDepart_index = "/sys/sysDepart/index";
    public static final String sys_sysDepart_list = "/sys/sysDepart/list";
    public static final String sys_sysDepart_add = "/sys/sysDepart/add";
    public static final String sys_sysDepart_edit = "/sys/sysDepart/edit";
    public static final String sys_sysDepart_detail = "/sys/sysDepart/detail";
    public static final String sys_sysDepart_newInfo = "/sys/sysDepart/newInfo";
    public static final String sys_sysDepart_del = "/sys/sysDepart/deleteBatch";
    public static final String sys_sysDepart_sort = "/sys/sysDepart/sort";
    public static final String sys_sysDepart_turnOn = "/sys/sysDepart/turnOn";
    public static final String sys_sysDepart_turnOff = "/sys/sysDepart/turnOff";
    public static final String sys_sysDepart_export = "/sys/sysDepart/export";
    public static final String sys_sysDepart_treeList = "/sys/sysDepart/queryTreeList";
    public static final String sys_sysDepart_treeIdList = "/sys/sysDepart/queryIdTree";
    public static final String sys_sysDepart_searchBy = "/sys/sysDepart/searchBy";

    /**
     * 菜单规则
     */
    public static final String sys_sysPermissionDataRule_index = "/sys/permissiondatarule/index";
    public static final String sys_sysPermissionDataRule_list = "/sys/permissiondatarule/list";
    public static final String sys_sysPermissionDataRule_add = "/sys/permissiondatarule/add";
    public static final String sys_sysPermissionDataRule_modify = "/sys/permissiondatarule/modify";
    public static final String sys_sysPermissionDataRule_detail = "/sys/permissiondatarule/detail";
    public static final String sys_sysPermissionDataRule_edit = "/sys/permissiondatarule/edit";
    public static final String sys_sysPermissionDataRule_newInfo = "/sys/permissiondatarule/newInfo";
    public static final String sys_sysPermissionDataRule_del = "/sys/permissiondatarule/delete";
    public static final String sys_sysPermissionDataRule_sort = "/sys/permissiondatarule/sort";
    public static final String sys_sysPermissionDataRule_turnOn = "/sys/permissiondatarule/turnOn";
    public static final String sys_sysPermissionDataRule_turnOff = "/sys/permissiondatarule/turnOff";
    public static final String sys_sysPermissionDataRule_export = "/sys/permissiondatarule/export";

    /**
     * 菜单权限
     */
    public static final String sys_sysPermission_index = "/sys/sysPermission/index";
    public static final String sys_sysPermission_list = "/sys/sysPermission/list";
    public static final String sys_sysPermission_add = "/sys/sysPermission/add";
    public static final String sys_sysPermission_modify = "/sys/sysPermission/modify";
    public static final String sys_sysPermission_detail = "/sys/sysPermission/detail";
    public static final String sys_sysPermission_edit = "/sys/sysPermission/edit";
    public static final String sys_sysPermission_newInfo = "/sys/sysPermission/newInfo";
    public static final String sys_sysPermission_del = "/sys/sysPermission/delete";
    public static final String sys_sysPermission_sort = "/sys/sysPermission/sort";
    public static final String sys_sysPermission_turnOn = "/sys/sysPermission/turnOn";
    public static final String sys_sysPermission_turnOff = "/sys/sysPermission/turnOff";
    public static final String sys_sysPermission_export = "/sys/sysPermission/export";
    public static final String sys_sysPermission_get = "/sys/permission/getUserPermissionByToken";

    /**
     * 角色权限
     */
    public static final String sys_sysRolePermission_index = "/sys/sysRolePermission/index";
    public static final String sys_sysRolePermission_list = "/sys/sysRolePermission/list";
    public static final String sys_sysRolePermission_add = "/sys/sysRolePermission/add";
    public static final String sys_sysRolePermission_modify = "/sys/sysRolePermission/modify";
    public static final String sys_sysRolePermission_detail = "/sys/sysRolePermission/detail";
    public static final String sys_sysRolePermission_edit = "/sys/sysRolePermission/edit";
    public static final String sys_sysRolePermission_newInfo = "/sys/sysRolePermission/newInfo";
    public static final String sys_sysRolePermission_del = "/sys/sysRolePermission/delete";
    public static final String sys_sysRolePermission_sort = "/sys/sysRolePermission/sort";
    public static final String sys_sysRolePermission_turnOn = "/sys/sysRolePermission/turnOn";
    public static final String sys_sysRolePermission_turnOff = "/sys/sysRolePermission/turnOff";
    public static final String sys_sysRolePermission_export = "/sys/sysRolePermission/export";

    /**
     * 用户部门
     */
    public static final String sys_sysUserDepart_index = "/sys/userdepart/index";
    public static final String sys_sysUserDepart_list = "/sys/userdepart/list";
    public static final String sys_sysUserDepart_add = "/sys/userdepart/add";
    public static final String sys_sysUserDepart_modify = "/sys/userdepart/modify";
    public static final String sys_sysUserDepart_detail = "/sys/userdepart/detail";
    public static final String sys_sysUserDepart_edit = "/sys/userdepart/edit";
    public static final String sys_sysUserDepart_newInfo = "/sys/userdepart/newInfo";
    public static final String sys_sysUserDepart_del = "/sys/userdepart/delete";
    public static final String sys_sysUserDepart_sort = "/sys/userdepart/sort";
    public static final String sys_sysUserDepart_turnOn = "/sys/userdepart/turnOn";
    public static final String sys_sysUserDepart_turnOff = "/sys/userdepart/turnOff";
    public static final String sys_sysUserDepart_export = "/sys/userdepart/export";

    /**
     * 用户
     */
    public static final String sys_user_index = "/sys/user/index";
    public static final String sys_user_modify = "/sys/user/modify";
    public static final String sys_user_detail = "/sys/user/detail";
    public static final String sys_user_edit = "/sys/user/edit";
    public static final String sys_user_newInfo = "/sys/user/newInfo";
    public static final String sys_user_sort = "/sys/user/sort";
    public static final String sys_user_turnOn = "/sys/user/turnOn";
    public static final String sys_user_turnOff = "/sys/user/turnOff";
    public static final String sys_user_export = "/sys/user/export";
    public static final String sys_user_depart = "/sys/user/departUserList";
    public static final String sys_user_role = "/sys/user/queryUserRole";
    public static final String sys_user_del_depart = "/sys/user/deleteUserInDepart";
    public static final String sys_user_editSysDepart = "/sys/user/editSysDepartWithUser";
    public static final String sys_user_updatePassword = "/sys/user/updatePassword";
    public static final String sys_user_userRoleList = "/sys/user/userRoleList";
    public static final String sys_user_addSysUserRole = "/sys/user/addSysUserRole";
    public static final String sys_user_deleteUserRoleBatch = "/sys/user/deleteUserRoleBatch";




}

