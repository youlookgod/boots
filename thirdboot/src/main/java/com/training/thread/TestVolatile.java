package com.training.thread;

/**
 * george 2019/3/5 10:19
 * volatile能保证可见性和部分有序性，但不能保证原子性
 */
public class TestVolatile {
    public static boolean flag = false;

    public static volatile boolean voFlag = false;

    public static void main(String[] args) throws InterruptedException {
        noVolatileTest();
        //volatileTest();
    }

    /**
     * 功能描述:flag变量没有使用volatile关键字修饰
     * 1、在线程A先启动的情况下，A线程会拷贝一份flag的到A线程的工作内存中，所以线程A中flag副本永远是初始的false，进入无限循环，无法break
     * 2、线程B修改了flag为true，也刷新到主内存中去了，但是线程A中的flag副本可用，不会从主内存中加载最新的值，。
     */
    public static void noVolatileTest() throws InterruptedException {
        ThreadA threadA = new ThreadA();
        ThreadB threadB = new ThreadB();
        new Thread(threadA, "threadA").start();
        Thread.sleep(1000);//为了保证threadA比threadB先启动，sleep一下
        new Thread(threadB, "threadB").start();
    }

    /**
     * 功能描述:voFlag变量使用volatile关键字修饰。
     * 1、对于volatile修饰的变量，读操作时jvm会把工作内存中对应的值设为无效，要求线程从主存中读取；写操作时，jvm会把工作内存中对应的数据刷新到主存中。
     *
     */
    public static void volatileTest() throws InterruptedException {
        ThreadC threadC = new ThreadC();
        ThreadD threadD = new ThreadD();
        new Thread(threadC, "threadC").start();
        Thread.sleep(1000);//为了保证threadC比threadD先启动，sleep一下
        new Thread(threadD, "threadD").start();
    }

    static class ThreadA extends Thread {
        @Override
        public void run() {
            int i = 0;
            while (true) {
                if (flag) {
                    System.out.println(Thread.currentThread().getName() + ":flag if " + flag);
                    break;
                }
            }
        }
    }

    static class ThreadB extends Thread {
        @Override
        public void run() {
            flag = true;
            System.out.println(Thread.currentThread().getName() + ":flag if " + flag);
        }
    }

    static class ThreadC extends Thread {
        @Override
        public void run() {
            int i = 0;
            while (true) {
                if (voFlag) {
                    System.out.println(Thread.currentThread().getName() + ":voFlag if " + voFlag);
                    break;
                }
            }
        }
    }

    static class ThreadD extends Thread {
        @Override
        public void run() {
            voFlag = true;
            System.out.println(Thread.currentThread().getName() + ":voFlag if " + voFlag);
        }
    }

}
