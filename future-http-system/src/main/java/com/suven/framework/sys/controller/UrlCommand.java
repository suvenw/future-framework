package com.suven.framework.sys.controller;

import com.suven.framework.http.processor.url.IURLCommand;
import com.suven.framework.http.processor.url.annotations.CDN;
import com.suven.framework.http.processor.url.annotations.UrlRemote;

@CDN
@UrlRemote
public  interface UrlCommand extends IURLCommand {
        public static final String sys_sysDataLog_index      =   "/sys/datalog/index";
        public static final String sys_sysDataLog_list       =   "/sys/datalog/list";
        public static final String sys_sysDataLog_queryList  =   "/sys/datalog/querylist";
        public static final String sys_sysDataLog_add        =   "/sys/datalog/add";
        public static final String sys_sysDataLog_modify     =   "/sys/datalog/modify";
        public static final String sys_sysDataLog_detail     =   "/sys/datalog/detail";
        public static final String sys_sysDataLog_edit       =   "/sys/datalog/edit";
        public static final String sys_sysDataLog_newInfo    =   "/sys/datalog/newInfo";
        public static final String sys_sysDataLog_del        =   "/sys/datalog/delete";
        public static final String sys_sysDataLog_export     =   "/sys/datalog/export";
        public static final String sys_sysDataLog_import     =   "/sys/datalog/import";



                public static final String sys_sysDepartPermission_index      =   "/sys/departpermission/index";
                public static final String sys_sysDepartPermission_list       =   "/sys/departpermission/list";
                public static final String sys_sysDepartPermission_queryList  =   "/sys/departpermission/querylist";
                public static final String sys_sysDepartPermission_add        =   "/sys/departpermission/add";
                public static final String sys_sysDepartPermission_modify     =   "/sys/departpermission/modify";
                public static final String sys_sysDepartPermission_detail     =   "/sys/departpermission/detail";
                public static final String sys_sysDepartPermission_edit       =   "/sys/departpermission/edit";
                public static final String sys_sysDepartPermission_newInfo    =   "/sys/departpermission/newInfo";
                public static final String sys_sysDepartPermission_del        =   "/sys/departpermission/delete";
                public static final String sys_sysDepartPermission_export     =   "/sys/departpermission/export";
                public static final String sys_sysDepartPermission_import     =   "/sys/departpermission/import";


        public static final String sys_sysDepartRolePermission_index      =   "/sys/departrolepermission/index";
        public static final String sys_sysDepartRolePermission_list       =   "/sys/departrolepermission/list";
        public static final String sys_sysDepartRolePermission_queryList  =   "/sys/departrolepermission/querylist";
        public static final String sys_sysDepartRolePermission_add        =   "/sys/departrolepermission/add";
        public static final String sys_sysDepartRolePermission_modify     =   "/sys/departrolepermission/modify";
        public static final String sys_sysDepartRolePermission_detail     =   "/sys/departrolepermission/detail";
        public static final String sys_sysDepartRolePermission_edit       =   "/sys/departrolepermission/edit";
        public static final String sys_sysDepartRolePermission_newInfo    =   "/sys/departrolepermission/newInfo";
        public static final String sys_sysDepartRolePermission_del        =   "/sys/departrolepermission/delete";
        public static final String sys_sysDepartRolePermission_export     =   "/sys/departrolepermission/export";
        public static final String sys_sysDepartRolePermission_import     =   "/sys/departrolepermission/import";


        public static final String sys_sysDepartRoleUser_index      =   "/sys/departroleuser/index";
        public static final String sys_sysDepartRoleUser_list       =   "/sys/departroleuser/list";
        public static final String sys_sysDepartRoleUser_queryList  =   "/sys/departroleuser/querylist";
        public static final String sys_sysDepartRoleUser_add        =   "/sys/departroleuser/add";
        public static final String sys_sysDepartRoleUser_modify     =   "/sys/departroleuser/modify";
        public static final String sys_sysDepartRoleUser_detail     =   "/sys/departroleuser/detail";
        public static final String sys_sysDepartRoleUser_edit       =   "/sys/departroleuser/edit";
        public static final String sys_sysDepartRoleUser_newInfo    =   "/sys/departroleuser/newInfo";
        public static final String sys_sysDepartRoleUser_del        =   "/sys/departroleuser/delete";
        public static final String sys_sysDepartRoleUser_export     =   "/sys/departroleuser/export";
        public static final String sys_sysDepartRoleUser_import     =   "/sys/departroleuser/import";



        public static final String sys_sysDepart_index      =   "/sys/depart/index";
        public static final String sys_sysDepart_list       =   "/sys/depart/list";
        public static final String sys_sysDepart_queryList  =   "/sys/depart/querylist";
        public static final String sys_sysDepart_add        =   "/sys/depart/add";
        public static final String sys_sysDepart_modify     =   "/sys/depart/modify";
        public static final String sys_sysDepart_detail     =   "/sys/depart/detail";
        public static final String sys_sysDepart_edit       =   "/sys/depart/edit";
        public static final String sys_sysDepart_newInfo    =   "/sys/depart/newInfo";
        public static final String sys_sysDepart_del        =   "/sys/depart/delete";
        public static final String sys_sysDepart_export     =   "/sys/depart/export";
        public static final String sys_sysDepart_import     =   "/sys/depart/import";
        public static final String sys_sysDepart_queryTreeList     =   "/sys/depart/queryTreeList";
        public static final String sys_sysDepart_myDeptTreeList     =   "/sys/depart/queryMyDeptTreeList";


        public static final String sys_sysDictItem_index      =   "/sys/dictitem/index";
        public static final String sys_sysDictItem_list       =   "/sys/dictitem/list";
        public static final String sys_sysDictItem_queryList  =   "/sys/dictitem/querylist";
        public static final String sys_sysDictItem_add        =   "/sys/dictitem/add";
        public static final String sys_sysDictItem_modify     =   "/sys/dictitem/modify";
        public static final String sys_sysDictItem_detail     =   "/sys/dictitem/detail";
        public static final String sys_sysDictItem_edit       =   "/sys/dictitem/edit";
        public static final String sys_sysDictItem_newInfo    =   "/sys/dictitem/newInfo";
        public static final String sys_sysDictItem_del        =   "/sys/dictitem/delete";
        public static final String sys_sysDictItem_export     =   "/sys/dictitem/export";
        public static final String sys_sysDictItem_import     =   "/sys/dictitem/import";


        public static final String sys_sysDict_index      =   "/sys/dict/index";
        public static final String sys_sysDict_list       =   "/sys/dict/list";
        public static final String sys_sysDict_queryList  =   "/sys/dict/querylist";
        public static final String sys_sysDict_add        =   "/sys/dict/add";
        public static final String sys_sysDict_modify     =   "/sys/dict/modify";
        public static final String sys_sysDict_detail     =   "/sys/dict/detail";
        public static final String sys_sysDict_edit       =   "/sys/dict/edit";
        public static final String sys_sysDict_newInfo    =   "/sys/dict/newInfo";
        public static final String sys_sysDict_del        =   "/sys/dict/delete";
        public static final String sys_sysDict_export     =   "/sys/dict/export";
        public static final String sys_sysDict_import     =   "/sys/dict/import";


        public static final String sys_sysLog_index      =   "/sys/log/index";
        public static final String sys_sysLog_list       =   "/sys/log/list";
        public static final String sys_sysLog_queryList  =   "/sys/log/querylist";
        public static final String sys_sysLog_add        =   "/sys/log/add";
        public static final String sys_sysLog_modify     =   "/sys/log/modify";
        public static final String sys_sysLog_detail     =   "/sys/log/detail";
        public static final String sys_sysLog_edit       =   "/sys/log/edit";
        public static final String sys_sysLog_newInfo    =   "/sys/log/newInfo";
        public static final String sys_sysLog_del        =   "/sys/log/delete";
        public static final String sys_sysLog_export     =   "/sys/log/export";
        public static final String sys_sysLog_import     =   "/sys/log/import";


        public static final String sys_sysPermissionDataRule_index      =   "/sys/permissiondatarule/index";
        public static final String sys_sysPermissionDataRule_list       =   "/sys/permissiondatarule/list";
        public static final String sys_sysPermissionDataRule_queryList  =   "/sys/permissiondatarule/querylist";
        public static final String sys_sysPermissionDataRule_add        =   "/sys/permissiondatarule/add";
        public static final String sys_sysPermissionDataRule_modify     =   "/sys/permissiondatarule/modify";
        public static final String sys_sysPermissionDataRule_detail     =   "/sys/permissiondatarule/detail";
        public static final String sys_sysPermissionDataRule_edit       =   "/sys/permissiondatarule/edit";
        public static final String sys_sysPermissionDataRule_newInfo    =   "/sys/permissiondatarule/newInfo";
        public static final String sys_sysPermissionDataRule_del        =   "/sys/permissiondatarule/delete";
        public static final String sys_sysPermissionDataRule_export     =   "/sys/permissiondatarule/export";
        public static final String sys_sysPermissionDataRule_import     =   "/sys/permissiondatarule/import";


        public static final String sys_sysPermission_index      =   "/sys/permission/index";
        public static final String sys_sysPermission_list       =   "/sys/permission/list";
        public static final String sys_sysPermission_queryList  =   "/sys/permission/querylist";
        public static final String sys_sysPermission_add        =   "/sys/permission/add";
        public static final String sys_sysPermission_modify     =   "/sys/permission/modify";
        public static final String sys_sysPermission_detail     =   "/sys/permission/detail";
        public static final String sys_sysPermission_edit       =   "/sys/permission/edit";
        public static final String sys_sysPermission_newInfo    =   "/sys/permission/newInfo";
        public static final String sys_sysPermission_del        =   "/sys/permission/delete";
        public static final String sys_sysPermission_export     =   "/sys/permission/export";
        public static final String sys_sysPermission_import     =   "/sys/permission/import";

        public static final String sys_sysPermission_saveRolePermission     =   "sys/permission/saveRolePermission";


        public static final String sys_sysPosition_index      =   "/sys/position/index";
        public static final String sys_sysPosition_list       =   "/sys/position/list";
        public static final String sys_sysPosition_queryList  =   "/sys/position/querylist";
        public static final String sys_sysPosition_add        =   "/sys/position/add";
        public static final String sys_sysPosition_modify     =   "/sys/position/modify";
        public static final String sys_sysPosition_detail     =   "/sys/position/detail";
        public static final String sys_sysPosition_edit       =   "/sys/position/edit";
        public static final String sys_sysPosition_newInfo    =   "/sys/position/newInfo";
        public static final String sys_sysPosition_del        =   "/sys/position/delete";
        public static final String sys_sysPosition_export     =   "/sys/position/export";
        public static final String sys_sysPosition_import     =   "/sys/position/import";


        public static final String sys_sysRolePermission_index      =   "/sys/rolepermission/index";
        public static final String sys_sysRolePermission_list       =   "/sys/rolepermission/list";
        public static final String sys_sysRolePermission_queryList  =   "/sys/rolepermission/querylist";
        public static final String sys_sysRolePermission_add        =   "/sys/rolepermission/add";
        public static final String sys_sysRolePermission_modify     =   "/sys/rolepermission/modify";
        public static final String sys_sysRolePermission_detail     =   "/sys/rolepermission/detail";
        public static final String sys_sysRolePermission_edit       =   "/sys/rolepermission/edit";
        public static final String sys_sysRolePermission_newInfo    =   "/sys/rolepermission/newInfo";
        public static final String sys_sysRolePermission_del        =   "/sys/rolepermission/delete";
        public static final String sys_sysRolePermission_export     =   "/sys/rolepermission/export";
        public static final String sys_sysRolePermission_import     =   "/sys/rolepermission/import";


        public static final String sys_sysRole_index      =   "/sys/role/index";
        public static final String sys_sysRole_list       =   "/sys/role/list";
        public static final String sys_sysRole_queryList  =   "/sys/role/querylist";
        public static final String sys_sysRole_add        =   "/sys/role/add";
        public static final String sys_sysRole_modify     =   "/sys/role/modify";
        public static final String sys_sysRole_detail     =   "/sys/role/detail";
        public static final String sys_sysRole_edit       =   "/sys/role/edit";
        public static final String sys_sysRole_newInfo    =   "/sys/role/newInfo";
        public static final String sys_sysRole_del        =   "/sys/role/delete";
        public static final String sys_sysRole_export     =   "/sys/role/export";
        public static final String sys_sysRole_import     =   "/sys/role/import";
        public static final String sys_sysRole_checkRoleCode     =   "/sys/role/checkRoleCode";


        public static final String sys_sysThirdAccount_index      =   "/sys/thirdaccount/index";
        public static final String sys_sysThirdAccount_list       =   "/sys/thirdaccount/list";
        public static final String sys_sysThirdAccount_queryList  =   "/sys/thirdaccount/querylist";
        public static final String sys_sysThirdAccount_add        =   "/sys/thirdaccount/add";
        public static final String sys_sysThirdAccount_modify     =   "/sys/thirdaccount/modify";
        public static final String sys_sysThirdAccount_detail     =   "/sys/thirdaccount/detail";
        public static final String sys_sysThirdAccount_edit       =   "/sys/thirdaccount/edit";
        public static final String sys_sysThirdAccount_newInfo    =   "/sys/thirdaccount/newInfo";
        public static final String sys_sysThirdAccount_del        =   "/sys/thirdaccount/delete";
        public static final String sys_sysThirdAccount_export     =   "/sys/thirdaccount/export";
        public static final String sys_sysThirdAccount_import     =   "/sys/thirdaccount/import";


        public static final String sys_sysUserDepart_index      =   "/sys/userdepart/index";
        public static final String sys_sysUserDepart_list       =   "/sys/userdepart/list";
        public static final String sys_sysUserDepart_queryList  =   "/sys/userdepart/querylist";
        public static final String sys_sysUserDepart_add        =   "/sys/userdepart/add";
        public static final String sys_sysUserDepart_modify     =   "/sys/userdepart/modify";
        public static final String sys_sysUserDepart_detail     =   "/sys/userdepart/detail";
        public static final String sys_sysUserDepart_edit       =   "/sys/userdepart/edit";
        public static final String sys_sysUserDepart_newInfo    =   "/sys/userdepart/newInfo";
        public static final String sys_sysUserDepart_del        =   "/sys/userdepart/delete";
        public static final String sys_sysUserDepart_export     =   "/sys/userdepart/export";
        public static final String sys_sysUserDepart_import     =   "/sys/userdepart/import";

        public static final String sys_sysUserRole_index      =   "/sys/userrole/index";
        public static final String sys_sysUserRole_list       =   "/sys/userrole/list";
        public static final String sys_sysUserRole_queryList  =   "/sys/userrole/querylist";
        public static final String sys_sysUserRole_add        =   "/sys/userrole/add";
        public static final String sys_sysUserRole_modify     =   "/sys/userrole/modify";
        public static final String sys_sysUserRole_detail     =   "/sys/userrole/detail";
        public static final String sys_sysUserRole_edit       =   "/sys/userrole/edit";
        public static final String sys_sysUserRole_newInfo    =   "/sys/userrole/newInfo";
        public static final String sys_sysUserRole_del        =   "/sys/userrole/delete";
        public static final String sys_sysUserRole_export     =   "/sys/userrole/export";
        public static final String sys_sysUserRole_import     =   "/sys/userrole/import";

        /**登录相关**/
        @UrlRemote
        public static final String sys_sysUser_login      =   "/sys/login";
        /**登录相关**/
        @UrlRemote
        public static final String sys_sysUser_checkToken      =   "/sys/checkToken";
        /**退出相关**/
        @UrlRemote
        public static final String sys_logout             =   "/sys/user/logout";
        public static final String sys_sysUser_index      =   "/sys/user/index";
        public static final String sys_sysUser_list       =   "/sys/user/list";
        public static final String sys_sysUser_queryList  =   "/sys/user/querylist";
        public static final String sys_sysUser_add        =   "/sys/user/add";
        public static final String sys_sysUser_modify     =   "/sys/user/modify";
        public static final String sys_sysUser_detail     =   "/sys/user/detail";
        public static final String sys_sysUser_edit       =   "/sys/user/edit";
        public static final String sys_sysUser_newInfo    =   "/sys/user/newInfo";
        public static final String sys_sysUser_del        =   "/sys/user/delete";
        public static final String sys_sysUser_export     =   "/sys/user/export";
        public static final String sys_sysUser_import     =   "/sys/user/import";
        public static final String sys_user_sort          = "/sys/user/sort";
        public static final String sys_user_turnOn        = "/sys/user/turnOn";
        public static final String sys_user_turnOff        = "/sys/user/turnOff";
        public static final String sys_user_updatePassword = "/sys/user/updatePassword";

       /** 获取图型验证码 **/
        @UrlRemote
        public static final String sys_get_random_image = "/sys/randomImage";
        /** 获取数字验证码 **/
        @UrlRemote
        public static final String sys_get_check_code = "/sys/getCheckCode";

        public static final String sys_user_frozen_batch = "/sys/user/frozenBatch";

        public static final String sys_user_handle_muted = "/sys/user/handleMuted";

        public static final String sys_user_handle_ban = "/sys/user/handleBan";

        public static final String sys_role_query_tree_list = "/sys/role/queryTreeList";

        public static final String sys_role_query_queryRolePermission = "/sys/permission/queryRolePermission";

        public static final String sys_role_query_getSystemMenuList = "/sys/permission/getSystemMenuList";

        public static final String sys_role_query_getSystemSubmenu = "/sys/permission/getSystemSubmenu";

        public static final String sys_role_list = "/sys/role/list";

        /**
         * 菜单权限
         */
        public static final String sys_sysPermission_get = "/sys/permission/getUserPermissionByToken";

        public static final String sys_permission_get_perm_rule_list_byId = "/sys/permission/getPermRuleListByPermId";
        public static final String sys_user_role = "/sys/user/queryUserRole";
        public static final String sys_user_editSysDepart = "/sys/user/editSysDepartWithUser";
        public static final String sys_user_depart = "/sys/user/departUserList";
        public static final String sys_user_del_depart = "/sys/user/deleteUserInDepart";
        public static final String sys_user_userRoleList = "/sys/user/userRoleList";
        public static final String sys_user_addSysUserRole = "/sys/user/addSysUserRole";
        public static final String sys_user_deleteUserRoleBatch = "/sys/user/deleteUserRoleBatch";
        public static final String sys_user_queryUserRolePermission = "/sys/user/queryUserRolePermission";


    }