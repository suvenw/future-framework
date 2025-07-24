package com.suven.framework.core.proxy.test;

import com.suven.framework.core.proxy.CglibProxyFactory;
import com.suven.framework.core.proxy.ProxyTargetCache;
import com.suven.framework.core.proxy.JdkProxyFactory;

public class Client3 {

    public static void cglibProxyTarget(String[] args) {

        ProxyTargetCache cglibProxyTarget = ProxyTargetCache.getInstance().init();
        PlayerAll proxy = new JdkProxyFactory<>(PlayerAll.class,cglibProxyTarget).createProxy();
        //验证代理类的父类
        System.out.println("代理类的父类："+proxy.getClass().getSuperclass().getSimpleName());
        System.out.println();

        proxy.loadVideo("荷塘月色.mp3");
        proxy.playVideo("荷塘月色.mp3");
        proxy.loadVideo1("荷塘月色.mp3");
        proxy.playVideo1("荷塘月色.mp3");
        proxy.loadVideo2("荷塘月色.mp3");
        proxy.playVideo2("荷塘月色.mp3");
    }

    public static void jdkProxyTarget(String[] args) {

        ProxyTargetCache cglibProxyTarget = ProxyTargetCache.getInstance().init();
        PlayerAll proxy = new CglibProxyFactory<>(PlayerAll.class,cglibProxyTarget).createProxy();
        //验证代理类的父类
        System.out.println("代理类的父类："+proxy.getClass().getSuperclass().getSimpleName());
        System.out.println();

        proxy.loadVideo("荷塘月色.mp3");
        proxy.playVideo("荷塘月色.mp3");
        proxy.loadVideo1("荷塘月色.mp3");
        proxy.playVideo1("荷塘月色.mp3");
        proxy.loadVideo2("荷塘月色.mp3");
        proxy.playVideo2("荷塘月色.mp3");
    }

}
