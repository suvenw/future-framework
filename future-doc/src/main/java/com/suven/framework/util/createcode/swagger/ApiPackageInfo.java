package com.suven.framework.util.createcode.swagger;

import com.suven.framework.http.api.ApiPackages;
import org.springframework.stereotype.Component;

/**
 * @author 作者 : suven.wang
 * CreateDate 创建时间: 2021-12-23
 * @WeeK 星期: 星期四
 * @version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  文档扫描包根文目录
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * Copyright: (c) 2021 gc by https://www.suven.top
 **/
@Component
public class ApiPackageInfo  implements ApiPackages {

    String packages = "com.suven";

    public String getPackages() {
        return packages;
    }
}
