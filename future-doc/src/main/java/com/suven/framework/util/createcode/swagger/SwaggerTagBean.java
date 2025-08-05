package com.suven.framework.util.createcode.swagger;

import com.suven.framework.http.api.ApiDoc;

/**
 * 界面展示对象;名称,描述,分组模块名称,分组描述
 */
public class SwaggerTagBean {
    private String name;
    private String description;
    private String groupModule;
    private String groupDesc;


    public static SwaggerTagBean build(){
        return  new SwaggerTagBean();
    }

    public static SwaggerTagBean init(String name, String desc){
        return build().setName(name).setDescription(desc);
    }
    public static SwaggerTagBean init(String classSimpleName,ApiDoc apiDoc){
        String desc = apiDoc.module() == null ? apiDoc.value() : apiDoc.module();
        String classDesc  =  desc == null ? classSimpleName : desc;

        String group = notEquals(apiDoc.group()) ? apiDoc.group():classSimpleName;

        if(group.equals(classSimpleName) && notEquals( apiDoc.groupDesc() )){
            classDesc = apiDoc.groupDesc();
        }
        return build().setName(classSimpleName)
                .setDescription(classDesc)
                .toGroupDesc(apiDoc.groupDesc())
                .toGroupModule(group);
    }

    private static boolean notEquals(String name){
        return null != name && !"".equals(name);
    }

    public String getName() {
        return name;
    }

    public SwaggerTagBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SwaggerTagBean setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getGroupModule() {
        return groupModule;
    }

    public void setGroupModule(String groupModule) {
        this.groupModule = groupModule;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }
    public SwaggerTagBean toGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
        return this;
    }
    public SwaggerTagBean toGroupModule(String groupModule) {
        this.groupModule = groupModule;
        return this;
    }
}
