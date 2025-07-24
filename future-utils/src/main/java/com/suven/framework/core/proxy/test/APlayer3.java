package com.suven.framework.core.proxy.test;

//音频播放器
public class APlayer3 implements Player3 {


    @Override
    public void loadVideo(String filename) {
        System.out.println("加载MP3音频文件："+filename);
    }

    @Override
    public void playVideo(String filename) {
        System.out.println("播放MP3："+filename);
    }


}
