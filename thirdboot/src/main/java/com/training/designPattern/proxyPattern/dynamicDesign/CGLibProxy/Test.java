package com.training.designPattern.proxyPattern.dynamicDesign.CGLibProxy;

import com.training.designPattern.proxyPattern.common.RealStar;
import com.training.designPattern.proxyPattern.common.Star;

/**
 * george 2018/12/25 14:47
 */
public class Test {
    public static void main(String[] args) {
        Star star = new RealStar();
        Star cglibProxy = (Star) new CglibProxyHandler().getProxyInstance(star);
        cglibProxy.sing();
    }
}
