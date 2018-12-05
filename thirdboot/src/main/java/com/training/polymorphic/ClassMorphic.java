package com.training.polymorphic;

/**
 * george 2018/12/5 10:57 普通类的多态
 * **总结**:属性值看引用的，即看等号左边；执行的方法看实例的，即等号右边，如果等号右边没有重写方法，则看父类的方法。
 */
public class ClassMorphic {
    public static void main(String[] args) {
        Tu tu = new Ti();
        tu.sayHi();
        ((Ti) tu).sayNo();
        System.out.println(tu.name);//此时是父类的成员变量name。

        Ti ti = new Ti();
        System.out.println(ti.name);//此时是子类的成员变量name
    }
}

class Tu {
    String name = "zhangsan";

    public void sayHi() {
        System.out.println("Hi! " + name);
    }
}

class Ti extends Tu {
    String name = "lisi";

    public void sayNo() {
        System.out.println("No! " + name);
    }

    @Override
    public void sayHi() {
        System.out.println("Hello! " + name);
    }
}