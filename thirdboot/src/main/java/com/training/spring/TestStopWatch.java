package com.training.spring;

import org.springframework.util.StopWatch;

/**
 * george 2019/5/9 15:45
 */
public class TestStopWatch {
    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        Thread.sleep(200);
        stopWatch.stop();
        long time = stopWatch.getTotalTimeMillis();
        long time1 = stopWatch.getLastTaskTimeMillis();
        System.out.println("time==" + time + "ms,time1==" + time1 + "ms");
        stopWatch.start();
        Thread.sleep(1000);
        stopWatch.stop();
        long time2 = stopWatch.getTotalTimeMillis();
        long time3 = stopWatch.getLastTaskTimeMillis();
    }
}
