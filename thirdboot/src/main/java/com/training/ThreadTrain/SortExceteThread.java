package com.training.ThreadTrain;

/**
 * george 2018/9/14 14:43
 */
public class SortExceteThread {
    public static void main(String[] args) {
        testOrderThread();
    }
    /**
     * 功能描述:线程顺序执行
     */
    private static void testOrderThread() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread1 run");
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread2 run");
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread3 run");
            }
        });

        t3.start();
        t1.start();
        t2.start();
    }
}
