package com.deep.baiduspider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * george 2018/8/7 14:12  多线程抓取tts
 */
public class TestPage {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(25);
        int pageSize = 100000;
        for (int i = 0; i < 25; i++) {
            executorService.execute(new SpiderThread(i,pageSize));
        }

    }
}
