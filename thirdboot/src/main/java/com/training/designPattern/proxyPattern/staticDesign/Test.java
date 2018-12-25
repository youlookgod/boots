package com.training.designPattern.proxyPattern.staticDesign;

import com.training.designPattern.proxyPattern.common.RealStar;
import com.training.designPattern.proxyPattern.common.Star;

/**
 * george 2018/12/24 14:41
 */
public class Test {
    public static void main(String[] args) {
        Star star = new RealStar();
        ProxyStar proxyStar = new ProxyStar(star);
        proxyStar.sing();
    }
}
