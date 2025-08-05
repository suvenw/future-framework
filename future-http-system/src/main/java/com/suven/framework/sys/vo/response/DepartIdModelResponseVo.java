//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.suven.framework.sys.vo.response;

import com.suven.framework.sys.entity.SysDepart;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DepartIdModelResponseVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private long key;
    private long value;
    private String title;
    List<DepartIdModelResponseVo> children = new ArrayList<>();

    public DepartIdModelResponseVo() {
    }

    public DepartIdModelResponseVo convert(SysDepartTreeModelResponseVo treeModel) {
        this.key = treeModel.getId();
        this.value = treeModel.getId();
        this.title = treeModel.getDepartName();
        return this;
    }

    public DepartIdModelResponseVo convertByUserDepart(SysDepart sysDepart) {
        this.key = sysDepart.getId();
        this.value = sysDepart.getId();
        this.title = sysDepart.getDepartName();
        return this;
    }

    public List<DepartIdModelResponseVo> getChildren() {
        return this.children;
    }

    public void setChildren(List<DepartIdModelResponseVo> children) {
        this.children = children;
    }

    public static long getSerialVersionUID() {
        return 1L;
    }

    public long getKey() {
        return this.key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public long getValue() {
        return this.value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
