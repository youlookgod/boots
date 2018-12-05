package com.training.polymorphic;

/**
 * george 2018/12/5 10:52 接口的多态
 */
public class InterfaceMorphic {
    public static void main(String[] args) {
        Su su = new Zu();
        su.sayHi();
    }
}

interface Su {
    void sayHi();
}

class Zu implements Su {
    @Override
    public void sayHi() {
        System.out.println("Hi!");
    }
}