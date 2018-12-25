package com.training.designPattern.proxyPattern.staticDesign;

import com.training.designPattern.proxyPattern.common.Star;

/**
 * 明星的静态代理类
 * george 2018/12/24 14:38
 */
public class ProxyStar implements Star {

    /**接收真实的明星对象**/
    private Star star;

    /**
     * 功能描述:通过构造方法传进来真实的明星对象
     *
     * @param star
     */
    public ProxyStar(Star star) {
        this.star = star;
    }

    @Override
    public void sing() {
        System.out.println("明星助理谈合同......");
        //唱歌只能明星自己唱
        this.star.sing();
        System.out.println("明星助理收钱.....");
    }
}
