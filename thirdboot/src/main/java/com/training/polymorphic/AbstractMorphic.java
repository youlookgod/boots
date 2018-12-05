package com.training.polymorphic;

/**
 * george 2018/12/5 10:47  抽象类的多态
 */
public class AbstractMorphic {
    public static void main(String[] args) {
        Fu fu = new Zi();
        fu.sayHi();
        fu.sayNo();
    }
}

abstract class Fu{
    public abstract void sayHi();

    public void sayNo(){
        System.out.println("no!");
    }
}

class Zi extends Fu{

    @Override
    public void sayHi() {
        System.out.println("Hi!");
    }
}
