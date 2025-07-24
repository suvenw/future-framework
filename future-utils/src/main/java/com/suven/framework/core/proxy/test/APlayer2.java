package com.suven.framework.core.proxy.test;

//音频播放器
public class APlayer2 implements Player2 {




    @Override
    public void loadVideo2(String filename) {
        System.out.println("加载MP3音频文件2："+filename);
    }

    @Override
    public void playVideo2(String filename) {
        System.out.println("播放MP3 2："+filename);
    }
}
