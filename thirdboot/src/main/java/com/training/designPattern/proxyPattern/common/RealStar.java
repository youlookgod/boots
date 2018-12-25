package com.training.designPattern.proxyPattern.common;


/**
 * 真实明星唱歌类
 * george 2018/12/24 14:36
 */
public class RealStar implements Star {
    @Override
    public void sing() {
        System.out.println("明星开始唱歌");
    }
}
