package com.suven.framework.sys.vo.response;


import java.io.Serializable;
import java.util.Date;

import com.suven.framework.http.data.entity.BaseBeanClone;
import com.suven.framework.http.api.ApiDesc;


/**
 * ClassName: SysPermissionShowResponseVo.java
 *
 * @author 作者 : suven
 * CreateDate 创建时间: 2022-02-28 16:10:30
 * @version 版本: v1.0.0
 * <pre>
 *
 *  Description: 菜单权限表 http业务接口交互数据返回参数实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/


public class SysPermissionShowResponseVo extends BaseBeanClone  implements Serializable {

   /** id 对应的业务表的主键id  */
    @ApiDesc(value = "对应的业务表的主键id ")
    private long id;

 		/** parent_id 父id  */
 		@ApiDesc(value = "父id", required = 0)
 		private long parentId;
 		/** name 菜单标题  */
 		@ApiDesc(value = "菜单标题", required = 0)
 		private String name;
 		/** url 路径  */
 		@ApiDesc(value = "路径", required = 0)
 		private String url;
 		/** component 组件  */
 		@ApiDesc(value = "组件", required = 0)
 		private String component;
 		/** component_name 组件名字  */
 		@ApiDesc(value = "组件名字", required = 0)
 		private String componentName;
 		/** redirect 一级菜单跳转地址  */
 		@ApiDesc(value = "一级菜单跳转地址", required = 0)
 		private String redirect;
 		/** menu_type 菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)  */
 		@ApiDesc(value = "菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)", required = 0)
 		private int menuType;
 		/** perms 菜单权限编码  */
 		@ApiDesc(value = "菜单权限编码", required = 0)
 		private String perms;
 		/** perms_type 权限策略1显示2禁用  */
 		@ApiDesc(value = "权限策略1显示2禁用", required = 0)
 		private String permsType;
 		/** sort_no 菜单排序  */
 		@ApiDesc(value = "菜单排序", required = 0)
 		private double sortNo;
 		/** always_show 聚合子路由: 1是0否  */
 		@ApiDesc(value = "聚合子路由: 1是0否", required = 0)
 		private int alwaysShow;
 		/** icon 菜单图标  */
 		@ApiDesc(value = "菜单图标", required = 0)
 		private String icon;
 		/** is_route 是否路由菜单: 0:不是  1:是（默认值1）  */
 		@ApiDesc(value = "是否路由菜单: 0:不是  1:是（默认值1）", required = 0)
 		private int isRoute;
 		/** is_leaf 是否叶子节点:    1:是   0:不是  */
 		@ApiDesc(value = "是否叶子节点:    1:是   0:不是", required = 0)
 		private int isLeaf;
 		/** keep_alive 是否缓存该页面:    1:是   0:不是  */
 		@ApiDesc(value = "是否缓存该页面:    1:是   0:不是", required = 0)
 		private int keepAlive;
 		/** hidden 是否隐藏路由: 0否,1是  */
 		@ApiDesc(value = "是否隐藏路由: 0否,1是", required = 0)
 		private int hidden;
 		/** hide_tab 是否隐藏tab: 0否,1是  */
 		@ApiDesc(value = "是否隐藏tab: 0否,1是", required = 0)
 		private int hideTab;
 		/** description 描述  */
 		@ApiDesc(value = "描述", required = 0)
 		private String description;
 		/** create_by 创建人  */
 		@ApiDesc(value = "创建人", required = 0)
 		private String createBy;
 		/** create_time 创建时间  */
 		@ApiDesc(value = "创建时间", required = 0)
 		private Date createTime;
 		/** update_by 更新人  */
 		@ApiDesc(value = "更新人", required = 0)
 		private String updateBy;
 		/** update_time 更新时间  */
 		@ApiDesc(value = "更新时间", required = 0)
 		private Date updateTime;
 		/** del_flag 删除状态 0正常 1已删除  */
 		@ApiDesc(value = "删除状态 0正常 1已删除", required = 0)
 		private int delFlag;
 		/** rule_flag 是否添加数据权限1是0否  */
 		@ApiDesc(value = "是否添加数据权限1是0否", required = 0)
 		private int ruleFlag;
 		/** status 按钮权限状态(0无效1有效)  */
 		@ApiDesc(value = "按钮权限状态(0无效1有效)", required = 0)
 		private int status;
 		/** internal_or_external 外链菜单打开方式 0/内部打开 1/外部打开  */
 		@ApiDesc(value = "外链菜单打开方式 0/内部打开 1/外部打开", required = 0)
 		private int internalOrExternal;


    public static SysPermissionShowResponseVo build(){
        return new SysPermissionShowResponseVo();
    }
 		public void setParentId( long parentId){
 		 		this.parentId = parentId ; 
 		 		}

 		public long getParentId(){
 		 		return this.parentId;
 		}
 		public SysPermissionShowResponseVo toParentId( long parentId){
 		 		this.parentId = parentId ; 
 		 		 return this ;
 		}
 		public void setName( String name){
 		 		this.name = name ; 
 		 		}

 		public String getName(){
 		 		return this.name;
 		}
 		public SysPermissionShowResponseVo toName( String name){
 		 		this.name = name ; 
 		 		 return this ;
 		}
 		public void setUrl( String url){
 		 		this.url = url ; 
 		 		}

 		public String getUrl(){
 		 		return this.url;
 		}
 		public SysPermissionShowResponseVo toUrl( String url){
 		 		this.url = url ; 
 		 		 return this ;
 		}
 		public void setComponent( String component){
 		 		this.component = component ; 
 		 		}

 		public String getComponent(){
 		 		return this.component;
 		}
 		public SysPermissionShowResponseVo toComponent( String component){
 		 		this.component = component ; 
 		 		 return this ;
 		}
 		public void setComponentName( String componentName){
 		 		this.componentName = componentName ; 
 		 		}

 		public String getComponentName(){
 		 		return this.componentName;
 		}
 		public SysPermissionShowResponseVo toComponentName( String componentName){
 		 		this.componentName = componentName ; 
 		 		 return this ;
 		}
 		public void setRedirect( String redirect){
 		 		this.redirect = redirect ; 
 		 		}

 		public String getRedirect(){
 		 		return this.redirect;
 		}
 		public SysPermissionShowResponseVo toRedirect( String redirect){
 		 		this.redirect = redirect ; 
 		 		 return this ;
 		}
 		public void setMenuType( int menuType){
 		 		this.menuType = menuType ; 
 		 		}

 		public int getMenuType(){
 		 		return this.menuType;
 		}
 		public SysPermissionShowResponseVo toMenuType( int menuType){
 		 		this.menuType = menuType ; 
 		 		 return this ;
 		}
 		public void setPerms( String perms){
 		 		this.perms = perms ; 
 		 		}

 		public String getPerms(){
 		 		return this.perms;
 		}
 		public SysPermissionShowResponseVo toPerms( String perms){
 		 		this.perms = perms ; 
 		 		 return this ;
 		}
 		public void setPermsType( String permsType){
 		 		this.permsType = permsType ; 
 		 		}

 		public String getPermsType(){
 		 		return this.permsType;
 		}
 		public SysPermissionShowResponseVo toPermsType( String permsType){
 		 		this.permsType = permsType ; 
 		 		 return this ;
 		}
 		public void setSortNo( double sortNo){
 		 		this.sortNo = sortNo ; 
 		 		}

 		public double getSortNo(){
 		 		return this.sortNo;
 		}
 		public SysPermissionShowResponseVo toSortNo( double sortNo){
 		 		this.sortNo = sortNo ; 
 		 		 return this ;
 		}
 		public void setAlwaysShow( int alwaysShow){
 		 		this.alwaysShow = alwaysShow ; 
 		 		}

 		public int getAlwaysShow(){
 		 		return this.alwaysShow;
 		}
 		public SysPermissionShowResponseVo toAlwaysShow( int alwaysShow){
 		 		this.alwaysShow = alwaysShow ; 
 		 		 return this ;
 		}
 		public void setIcon( String icon){
 		 		this.icon = icon ; 
 		 		}

 		public String getIcon(){
 		 		return this.icon;
 		}
 		public SysPermissionShowResponseVo toIcon( String icon){
 		 		this.icon = icon ; 
 		 		 return this ;
 		}
 		public void setIsRoute( int isRoute){
 		 		this.isRoute = isRoute ; 
 		 		}

 		public int getIsRoute(){
 		 		return this.isRoute;
 		}
 		public SysPermissionShowResponseVo toIsRoute( int isRoute){
 		 		this.isRoute = isRoute ; 
 		 		 return this ;
 		}
 		public void setIsLeaf( int isLeaf){
 		 		this.isLeaf = isLeaf ; 
 		 		}

 		public int getIsLeaf(){
 		 		return this.isLeaf;
 		}
 		public SysPermissionShowResponseVo toIsLeaf( int isLeaf){
 		 		this.isLeaf = isLeaf ; 
 		 		 return this ;
 		}
 		public void setKeepAlive( int keepAlive){
 		 		this.keepAlive = keepAlive ; 
 		 		}

 		public int getKeepAlive(){
 		 		return this.keepAlive;
 		}
 		public SysPermissionShowResponseVo toKeepAlive( int keepAlive){
 		 		this.keepAlive = keepAlive ; 
 		 		 return this ;
 		}
 		public void setHidden( int hidden){
 		 		this.hidden = hidden ; 
 		 		}

 		public int getHidden(){
 		 		return this.hidden;
 		}
 		public SysPermissionShowResponseVo toHidden( int hidden){
 		 		this.hidden = hidden ; 
 		 		 return this ;
 		}
 		public void setHideTab( int hideTab){
 		 		this.hideTab = hideTab ; 
 		 		}

 		public int getHideTab(){
 		 		return this.hideTab;
 		}
 		public SysPermissionShowResponseVo toHideTab( int hideTab){
 		 		this.hideTab = hideTab ; 
 		 		 return this ;
 		}
 		public void setDescription( String description){
 		 		this.description = description ; 
 		 		}

 		public String getDescription(){
 		 		return this.description;
 		}
 		public SysPermissionShowResponseVo toDescription( String description){
 		 		this.description = description ; 
 		 		 return this ;
 		}
 		public void setCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		}

 		public String getCreateBy(){
 		 		return this.createBy;
 		}
 		public SysPermissionShowResponseVo toCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		 return this ;
 		}
 		public void setCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		}

 		public Date getCreateTime(){
 		 		return this.createTime;
 		}
 		public SysPermissionShowResponseVo toCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		 return this ;
 		}
 		public void setUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		}

 		public String getUpdateBy(){
 		 		return this.updateBy;
 		}
 		public SysPermissionShowResponseVo toUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		 return this ;
 		}
 		public void setUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		}

 		public Date getUpdateTime(){
 		 		return this.updateTime;
 		}
 		public SysPermissionShowResponseVo toUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		 return this ;
 		}
 		public void setDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		}

 		public int getDelFlag(){
 		 		return this.delFlag;
 		}
 		public SysPermissionShowResponseVo toDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		 return this ;
 		}
 		public void setRuleFlag( int ruleFlag){
 		 		this.ruleFlag = ruleFlag ; 
 		 		}

 		public int getRuleFlag(){
 		 		return this.ruleFlag;
 		}
 		public SysPermissionShowResponseVo toRuleFlag( int ruleFlag){
 		 		this.ruleFlag = ruleFlag ; 
 		 		 return this ;
 		}
 		public void setStatus( int status){
 		 		this.status = status ; 
 		 		}

 		public int getStatus(){
 		 		return this.status;
 		}
 		public SysPermissionShowResponseVo toStatus( int status){
 		 		this.status = status ; 
 		 		 return this ;
 		}
 		public void setInternalOrExternal( int internalOrExternal){
 		 		this.internalOrExternal = internalOrExternal ; 
 		 		}

 		public int getInternalOrExternal(){
 		 		return this.internalOrExternal;
 		}
 		public SysPermissionShowResponseVo toInternalOrExternal( int internalOrExternal){
 		 		this.internalOrExternal = internalOrExternal ; 
 		 		 return this ;
 		}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }





}
