package com.suven.framework.core.proxy.test;

//音频播放器
public class APlayer1 implements Player1 {


    @Override
    public void loadVideo1(String filename) {
        System.out.println("加载MP3音频文件1："+filename);
    }

    @Override
    public void playVideo1(String filename) {
        System.out.println("播放MP3 1："+filename);
    }


}
