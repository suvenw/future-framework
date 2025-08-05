package com.suven.framework.core.redis;



/**
 * Title: RedisGroupEnumInterface.java
 * @author Joven.wang
 * date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 *   项目数据源名称泛型实现类，
 *  用来实现多Redis自动切换使用，配合 DataSourceAutoConfiguration 启动初化数据源注入到spring bean 中
 *  创建spring DynamicDataSource bean 对象，并注入到spring容器中，命名为dataSource;
 * Description: (说明) DruidDataSource 用于实现数据库聚群动态扫描加载的接口,配合标注上该  @Setter@GettersourceEnumAnno,来实现动态加载数据模块分组;
 * Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

public interface RedisGroupEnumInterface<E extends Enum<E>> {

    /**
     * 配置文件组名,one,two,three
     * @return
     */
    String getConfig();

    /**
     * 枚举名称
     * @return
     */
    String getGroupName();

}