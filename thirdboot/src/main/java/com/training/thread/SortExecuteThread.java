package com.training.thread;

import java.util.concurrent.CountDownLatch;

/**
 * george 2018/9/14 14:43
 */
public class SortExecuteThread {
    public static void main(String[] args) {
//        testOrderThread();
//        acrossThreadSort();
        testCountDownLatch();
    }

    /**
     * 功能描述:利用Thread.join让线程顺序执行
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

    /**
     * 功能描述: 2个线程有序交叉运行
     * 注意：这个例子不是很严谨，因为A、B多线程并发，它们两随机获得lock锁。
     * 一、如果A先获得锁(cpu执行权)，那么A先执行,A打印A out 1后执行wait并释放lock锁，此时B获得lock执行打印，B打印完后执行notify通知A继续打印A out 2和 A out 3。
     * 二、如果B先获得lock锁，那么B将先执行，B打印b1、b2、b3后notify通知A执行，A打印了A1后A线程进入wait状态，此时已经没有别的notify通知A继续打印，所以线程在此一直处于等待状态，导致A线程阻塞。
     */
    private static void acrossThreadSort(){
        Object lock = new Object();//定义锁

        Thread A = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println("A out 1");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("A out 2");
                    System.out.println("A out 3");
                }
            }
        });

        Thread B = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println("B out 1");
                    System.out.println("B out 2");
                    System.out.println("B out 3");
                    lock.notify();
                }
            }
        });

        A.start();
        B.start();
    }

    /**
     * 功能描述:计数器控制线程执行顺序
     */
    private static void testCountDownLatch(){
        int worker = 3;
        CountDownLatch countDownLatch = new CountDownLatch(worker);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("D thread is waiting for other thread done");
                try{
                    //等待A、B、C执行完。
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        for (char threadName = 'A'; threadName <= 'C'; threadName++) {
            final String th = String.valueOf(threadName);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(th + " is working");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(th + " is finished");
                    countDownLatch.countDown();
                }
            }).start();
        }
    }
}
