package com.suven.framework.upload.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.suven.framework.core.mybatis.SQLDbUtils;
import com.suven.framework.core.db.ext.DS;
import com.suven.framework.http.api.ApiDesc;
import com.suven.framework.http.data.entity.BaseTenantEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;


/**
 * 存储应该对应云空间的key,value config配置信息,一个空间自定义多条配置实现
 */
@Setter@Getter
@Accessors(chain = true)
@TableName(value = "file_upload_logger")
@DS(DataSourceModuleName.module_name_file)
public class FileAppStorageConfig extends BaseTenantEntity {


    @ApiDesc(value = "注册应用id", required = 0)
    private String appId; //appid 应用id
    @ApiDesc(value = "注册应用名称", required = 0)
    private String appName;  //应用名称
    @ApiDesc(value = "注册应用授权Id", required = 0)
    private String clientId; //appid 授权id

    @ApiDesc(value = "注册应用授权Id,对应FileStorageEnum", required = 0)
    private String storageConfigId; //appid 授权id

    //动态配置,业务自定义自段名;1.本地;basePath 2.
    @ApiDesc(value = "动态配置,业务自定义自段名;1.本地;basePath ", required = 0)
    private String configFiledName;
    //动态配置,业务自定义自段名;1.本地 /user/path/
    @ApiDesc(value = "动态配置,业务自定义自段名值", required = 0)
    private String configFiledValue;

    public static FileAppStorageConfig build(){
        return new FileAppStorageConfig();
    }
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getStorageConfigId() {
        return storageConfigId;
    }

    public void setStorageConfigId(String storageConfigId) {
        this.storageConfigId = storageConfigId;
    }

    public String getConfigFiledName() {
        return configFiledName;
    }

    public void setConfigFiledName(String configFiledName) {
        this.configFiledName = configFiledName;
    }

    public String getConfigFiledValue() {
        return configFiledValue;
    }

    public void setConfigFiledValue(String configFiledValue) {
        this.configFiledValue = configFiledValue;
    }

    public static void main(String[] args) throws Exception {
        List<Class<?>> list = Arrays.asList(FileAppStorageConfig.class, FileDataDetailed.class,
                FileUploadActionWater.class,FileUploadApp.class,FileUploadStorage.class,FileUploadUseBusiness.class);
      for (Class<?> c : list){
          String a = SQLDbUtils.creatTableIdSQLClass(c);
		System.out.println(a);
      }

//		TestCreateModel t = new TestCreateModel();
//		t.setAge(10);
//		t.setName("111");
//
//		String a = SQLDbUtils.creatTableSQL(t);
//		System.out.println(a);
//		Class cls = TestEnum.class;
//  	Class cls = STARGET.class;
//		creatRowMapper(cls);
    }
}
