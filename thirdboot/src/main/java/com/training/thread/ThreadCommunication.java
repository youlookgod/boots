package com.training.thread;

import java.util.concurrent.*;

/**
 * george 2018/10/17 10:17  多线程间通信
 */
public class ThreadCommunication {
    public static void main(String[] args) {
        havaReturnThread();
        producerAndConsume();
    }

    private static void producerAndConsume(){
        //new一个面包类
        Breads bre = new Breads();

        //new一个生产者类
        Producer proth = new Producer(bre);
        //new一个消费者类
        Consume conth = new Consume(bre);

        //new一个包含消费者类的线程
        Thread t1 = new Thread(proth, "生产者");

        //new一个包含生产者类的线程
        Thread t2 = new Thread(conth, "消费者");

        //启动线程
        t1.start();
        t2.start();
    }

    private static void havaReturnThread(){
        Callable<String> callable =new Callable() {
            @Override
            public String call() throws Exception {
                return "你好！";
            }
        };
        FutureTask<String> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

/**
 * @author 温煦（昵称：沉沦之巅）
 * @functon 线程通信之面包类
 * @time 2017.12.5
 */
class Breads {

    //面包的id
    private int bid;
    //面包的个数
    private int num;

    //生产面包的方法（由于是demo，方便大家理解，就把synchronized关键字加到方法上面了哦）
    public synchronized void produc() {

        //当面包的数量不为0时，该方法处于等待状态
        if (0 != num) {
            try {
                wait();//等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //当面包数量为0时，那么就开始生产面包了哦
        num = num + 1;//数量加1
        bid = bid + 1;//id当然也得加1
        String threadname = Thread.currentThread().getName();
        System.out.println(threadname + "生产了一个编号为" + bid + "的面包！");
        notify();//当执行完后，去唤醒其他处于等待的线程
    }

    //消费面包的方法
    public synchronized void consume() {
        //当面包的数量为0时，该方法处于等待状态
        if (num == 0) {
            try {
                wait();//等待
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //消费完面包了，所以面包数量降为0了
        num = num - 1;//数量减1
        String name1 = Thread.currentThread().getName();
        System.out.println(name1 + "买了一个面包编号为" + bid);
        notify();//当执行完后，去唤醒其他处于等待的线程
    }


    //set和get方法
    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    //有参构造
    public Breads(int bid, int num) {
        super();
        this.bid = bid;
        this.num = num;
    }

    //无参构造
    public Breads() {
        super();
        // TODO Auto-generated constructor stub
    }
}

/**
 * @author 温煦（昵称：沉沦之巅）
 * @functon 线程通信之生产类（继承Thread类）
 * @time 2017.12.5
 */

class Producer extends Thread {

    //获得面包的类
    private Breads bre;

    //无参构造
    public Producer() {
        super();
    }

    //有参构造
    public Producer(Breads bre) {
        super();
        this.bre = bre;
    }


    //set和get方法
    public Breads getBre() {
        return bre;
    }

    public void setBre(Breads bre) {
        this.bre = bre;
    }

    //继承重写run方法
    @Override
    public void run() {
        pro();
    }

    //生产面包
    private void pro() {
        // 本系统默认循环生产20个面包
        for (int i = 0; i < 20; i++) {
            try {
                //沉睡0.3秒（演示效果需要，可以不加）
                Thread.currentThread().sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //调用面包类里的生产面包的方法
            bre.produc();
        }
    }
}

/**
 * @author 温煦（昵称：沉沦之巅）
 * @functon 线程通信之消费类（继承Thread类）
 * @time 2017.12.5
 */
class Consume extends Thread {

    //获得面包的类
    private Breads bre;

    //set和get方法
    public Breads getBre() {
        return bre;
    }

    public void setBre(Breads bre) {
        this.bre = bre;
    }

    //继承重写run方法
    @Override
    public void run() {
        con();
    }

    //消费面包
    private void con() {
        // 与生产者保持一致，本系统默认循环生产20个面包（生产几个，消费几个）
        for (int i = 0; i < 20; i++) {
            try {
                //沉睡0.3秒（演示效果需要，可以不加）
                Thread.currentThread().sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //调用面包类里的生产面包的方法
            bre.consume();
        }
    }

    //有参构造
    public Consume(Breads bre) {
        super();
        this.bre = bre;
    }

    //无参构造
    public Consume() {
        super();
    }
}
