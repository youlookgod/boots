package com.training.designPattern.proxyPattern.dynamicDesign.JDKProxy;

import com.training.designPattern.proxyPattern.common.RealStar;
import com.training.designPattern.proxyPattern.common.Star;

/**
 * george 2018/12/24 16:25
 */
public class Test {
    public static void main(String[] args) {
        Star star = new RealStar();

        //lambda方式实现的InvocationHandler
        Star proxy = (Star) new JdkProxyHandler(star).getProxyInstance();
        proxy.sing();

        //实现InvocationHandler接口
        Star starInvocation = (Star) new StarInvocationHandler(star).getProxyInstance();
        starInvocation.sing();

    }
}
