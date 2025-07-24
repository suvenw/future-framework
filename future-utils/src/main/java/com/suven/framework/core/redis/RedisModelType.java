package com.suven.framework.core.redis;


/**
 * @author 作者 : suven
 * @version 版本: v1.0.0
 *d ate 创建时间: 2023-12-29
 * <pre>
 * description (说明): 实现支持model 为 ,standalone,sentinel,masterSlave，cluster，group 默认为 cluster;
 *  *         model: cluster
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 *
 * # model 为 ,standalone,sentinel,masterSlave，cluster，group 默认为 cluster;
 *         model: cluster
 **/
public interface RedisModelType{


        /**
         * 单结点模式
         ***/
        boolean isStandalone();

        /**
         * 主从节点模式
         ***/
        boolean isMasterSlave();
        /**
         * 目前只支持两种模式,默认是集群模式,
         * 不是聚群模式,就是单结点模式;
         * 暂时不做哨兵模式逻辑,
         * 哨兵模式使用单结点模式逻辑
         ***/
        boolean isSentinel();


        /**
         * 目前只支持两种模式,默认是集群模式,
         * 不是聚群模式,就是单结点模式;
         * 暂时不做哨兵模式逻辑,
         * 哨兵模式使用单结点模式逻辑
         ***/
        boolean isCluster();
        /**
         * 目前只支持两种模式,默认是集群模式,
         * 不是聚群模式,就是单结点模式;
         * 暂时不做哨兵模式逻辑,
         * 哨兵模式使用单结点模式逻辑
         ***/
        boolean isClusterGroup();
    }