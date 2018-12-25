package com.training.designPattern.proxyPattern.dynamicDesign.JDKProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 实现InvocationHandler的动态代理处理类
 * george 2018/12/24 15:14
 */
public class StarInvocationHandler implements InvocationHandler {
    /**目标对象 **/
    private Object target;

    public StarInvocationHandler(Object target){
        this.target = target;
    }

    public Object getProxyInstance(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("jdk动态明星助理谈合同.....");
        Object result = method.invoke(target, args);
        System.out.println("jdk动态明星助理收钱.....");
        return result;
    }
}
