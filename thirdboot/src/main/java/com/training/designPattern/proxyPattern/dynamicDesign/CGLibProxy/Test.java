package com.training.designPattern.proxyPattern.dynamicDesign.CGLibProxy;

import com.training.designPattern.proxyPattern.common.RealStar;
import com.training.designPattern.proxyPattern.common.Star;
import com.training.domain.UserRequest;
import com.training.domain.UserRequestSupport;
import org.springframework.aop.config.AopNamespaceHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.Resource;

/**
 * george 2018/12/25 14:47
 */
public class Test {
    public static void main(String[] args) {
//        DefaultListableBeanFactory
        Star star = new RealStar();
//        AbstractApplicationContext
        Synthe
        Star cglibProxy = (Star) new CglibProxyHandler().getProxyInstance(star);
        cglibProxy.sing();

        UserRequest userRequest = new UserRequest();
        UserRequestSupport userRequestSupport = new UserRequestSupport();
        validate(userRequest);
        validate(userRequestSupport);
    }

    public static void validate(UserRequest userRequest) {
        if (userRequest instanceof UserRequestSupport) {
            System.out.println("UserRequestSupport");
        }else{
            System.out.println("ParentClass");
        }
    }
}
