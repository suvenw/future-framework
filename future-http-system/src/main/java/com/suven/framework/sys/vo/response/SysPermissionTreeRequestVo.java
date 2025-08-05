package com.suven.framework.sys.vo.response;

import java.util.List;

/**
 * @author 作者 : suven.wang
 * CreateDate 创建时间: 2022-03-08
 * @WeeK 星期: 星期四
 * @version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  SysPermissionRequest返回对象
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
public class SysPermissionTreeRequestVo implements java.io.Serializable{
    List<TreeModelResponseVo> treeList;
    List<Long> ids;

    public static  SysPermissionTreeRequestVo build() {
        return new SysPermissionTreeRequestVo();
    }

    public List<TreeModelResponseVo> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<TreeModelResponseVo> treeList) {
        this.treeList = treeList;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
