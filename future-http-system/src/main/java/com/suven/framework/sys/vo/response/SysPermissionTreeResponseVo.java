package com.suven.framework.sys.vo.response;


import com.suven.framework.http.data.entity.BaseStatusEntity;
import com.suven.framework.sys.dto.response.SysPermissionResponseDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysPermissionTreeResponseVo extends BaseStatusEntity implements Serializable {

    private static final long serialVersionUID = 1L;
	public static SysPermissionTreeResponseVo build(){
		return new SysPermissionTreeResponseVo();
	}

    /**
     * id
     */
    private long id;

    private long key;

    private String title;

    /**
     * 父id
     */
    private String parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单权限编码
     */
    private String perms;
    /**
     * 权限策略1显示2禁用
     */
    private String permsType;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 组件
     */
    private String component;

    /**
     * 跳转网页链接
     */
    private String url;

    /**
     * 一级菜单跳转地址
     */
    private String redirect;

    /**
     * 菜单排序
     */
    private double sortNo;

    /**
     * 类型（0：一级菜单；1：子菜单 ；2：按钮权限）
     */
    private Integer menuType;

    /**
     * 是否叶子节点: 1:是 0:不是
     */
    private boolean isLeaf;

    /**
     * 是否路由菜单: 0:不是  1:是（默认值1）
     */
    private boolean isRoute;


    /**
     * 是否路缓存页面: 0:不是  1:是（默认值1）
     */
    private boolean keepAlive;


    /**
     * 描述
     */
    private String description;

    /**
     * 删除状态 0正常 1已删除
     */
    private Integer delFlag;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * alwaysShow
     */
    private boolean alwaysShow;
    /**
     * 是否隐藏路由菜单: 0否,1是（默认值0）
     */
    private boolean hidden;

    /**
     * 按钮权限状态(0无效1有效)
     */
    private String status;

    /*update_begin author:wuxianquan date:20190908 for:model增加字段 */
    /**
     * 外链菜单打开方式 0/内部打开 1/外部打开
     */
    private boolean internalOrExternal;
    /*update_end author:wuxianquan date:20190908 for:model增加字段 */


    public SysPermissionTreeResponseVo() {
    }

    public SysPermissionTreeResponseVo(SysPermissionResponseDto permission) {
        this.key = permission.getId();
        this.id = permission.getId();
        this.perms = permission.getPerms();
        this.permsType = String.valueOf(permission.getPermsType());
        this.component = permission.getComponent();
        this.createTime = permission.getCreateTime();
        this.delFlag = permission.getDelFlag();
        this.description = permission.getDescription();
        this.icon = permission.getIcon();
        this.isLeaf = permission.getIsLeaf() == 1 ? true : false;
        this.menuType = permission.getMenuType();
        this.name = permission.getName();
        this.parentId = String.valueOf(permission.getParentId());
        this.sortNo = permission.getSortNo();
        this.updateTime = permission.getUpdateTime();
        this.redirect = permission.getRedirect();
        this.url = permission.getUrl();
        this.hidden = permission.getHidden() == 1 ? true : false;
        this.isRoute = permission.getIsRoute() == 1 ? true : false;
        this.keepAlive = permission.getKeepAlive() == 1 ? true : false;
        this.alwaysShow = permission.getAlwaysShow() == 1 ? true : false;
        this.title = permission.getName();
        if ( permission.getIsLeaf() != 1 ) {
            this.children = new ArrayList<SysPermissionTreeResponseVo>();
        }
        this.status = String.valueOf(permission.getStatus());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private List<SysPermissionTreeResponseVo> children;

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public boolean isAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public List<SysPermissionTreeResponseVo> getChildren() {
        return children;
    }

    public void setChildren(List<SysPermissionTreeResponseVo> children) {
        this.children = children;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }


    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getSortNo() {
        return sortNo;
    }

    public void setSortNo(double sortNo) {
        this.sortNo = sortNo;
    }

    public void setRoute(boolean route) {
        isRoute = route;
    }




    public boolean isInternalOrExternal() {
        return internalOrExternal;
    }

    public void setInternalOrExternal(boolean internalOrExternal) {
        this.internalOrExternal = internalOrExternal;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRoute() {
        return isRoute;
    }

    public void setIsRoute(boolean route) {
        this.isRoute = route;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getPermsType() {
        return permsType;
    }

    public void setPermsType(String permsType) {
        this.permsType = permsType;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    //    public String getStatus() {
//        return status;
//    }

//    public void setStatus(String status) {
//        this.status = status;
//    }

}
