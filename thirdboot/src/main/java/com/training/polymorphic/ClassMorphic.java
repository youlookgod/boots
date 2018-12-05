package com.training.polymorphic;

/**
 * george 2018/12/5 10:57 普通类的多态
 */
public class ClassMorphic {
    public static void main(String[] args) {
        Tu tu = new Ti();
        tu.sayHi();
        ((Ti) tu).sayNo();
    }
}

class Tu {
    public void sayHi() {
        System.out.println("Hi!");
    }
}

class Ti extends Tu {
    public void sayNo() {
        System.out.println("No!");
    }

    @Override
    public void sayHi() {
        System.out.println("Hello!");
    }
}