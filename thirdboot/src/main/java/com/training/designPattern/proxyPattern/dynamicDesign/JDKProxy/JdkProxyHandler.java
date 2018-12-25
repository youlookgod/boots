package com.training.designPattern.proxyPattern.dynamicDesign.JDKProxy;


import java.lang.reflect.Proxy;

/**
 * lambda方式实现动态代理处理类
 * george 2018/12/24 15:08
 */
public class JdkProxyHandler {
    /**
     * 接收真实的明星对象
     **/
    private Object realStar;

    /**
     * 功能描述:通过构造方法传进来真实的明星对象
     *
     * @param star
     */
    public JdkProxyHandler(Object star) {
        super();
        this.realStar = star;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(realStar.getClass().getClassLoader(), realStar.getClass().getInterfaces(), (proxy, method, args) -> {
            System.out.println("jdk动态明星助理谈合同.....");
            // 唱歌需要明星自己来唱
            Object result = method.invoke(realStar, args);
            System.out.println("jdk动态明星助理收钱.....");
            return result;
        });
    }
}
