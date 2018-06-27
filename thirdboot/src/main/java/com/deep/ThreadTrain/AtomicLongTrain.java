package com.deep.ThreadTrain;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongTrain {
    public static void main(String[] args){
        testAtomicLong();
    }

    private static void testAtomicLong(){
        AtomicLong value = new AtomicLong(10);
        //value.addAndGet(1);
        System.out.println(value);
        new Thread(()->{
            for(int i=0;i<10;i++){
                value.addAndGet(1);
            }
        }).start();

        new Thread(()->{
            for(int i=0;i<10;i++){
                value.addAndGet(1);
            }
        }).start();

        new Thread(()->{
            for(int i=0;i<10;i++){
                value.addAndGet(1);
            }
        }).start();

        new Thread(()->{
            for(int i=0;i<10;i++){
                value.addAndGet(1);
            }
        }).start();
        try {
            Thread.sleep(1000);//确保以上线程都执行完，不加线程等待的话以上线程不会执行完---输出40。
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(value);
    }
}
