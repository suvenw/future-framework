package com.suven.framework.core.proxy.test;

//音频播放器
public class APlayer implements PlayerAll {


    @Override
    public void loadVideo(String filename) {
        System.out.println("加载MP3音频文件："+filename);
    }

    @Override
    public void playVideo(String filename) {
        System.out.println("播放MP3："+filename);
    }

    @Override
    public void loadVideo1(String filename) {
        System.out.println("加载MP3音频文件1："+filename);
    }

    @Override
    public void playVideo1(String filename) {
        System.out.println("播放MP3 1："+filename);
    }

    @Override
    public void loadVideo2(String filename) {
        System.out.println("加载MP3音频文件2："+filename);
    }

    @Override
    public void playVideo2(String filename) {
        System.out.println("播放MP3 2："+filename);
    }
}
