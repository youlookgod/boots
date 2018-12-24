package com.training.annotation;

import com.training.annotation.inherited.Dbtable;
import com.training.annotation.inherited.Dbtable2;

import java.util.Arrays;

/**
 * george 2018/12/18 11:19
 */
public class TestInherited {
    public static void main(String[] args) {
        Class sub = Sub.class;
        System.out.println(Arrays.asList(sub.getFields()));
        System.out.println(Arrays.asList(sub.getDeclaredFields()));
        System.out.println(Arrays.asList(sub.getMethods()));
        System.out.println(Arrays.asList(sub.getDeclaredMethods()));
        System.out.println(Arrays.asList(sub.getAnnotations()));
        System.out.println(Arrays.asList(sub.getDeclaredAnnotations()));

        System.out.println(sub.isAnnotationPresent(Dbtable.class));
        System.out.println(sub.getAnnotation(Dbtable2.class));

        System.out.println("name=" + sub.getName());
    }
}

@Dbtable
class Super {
    public int superPubBuf;
    private int superPrivate;

    public Super() {
    }

    private int superPrivateM() {
        return 0;
    }

    public int superPubliceM() {
        return 0;
    }
}

@Dbtable2
class Sub extends Super {
    private int subPrivateF;
    public int subPublicF;

    private Sub() {
    }

    public Sub(int i) {
    }

    private int subPrivateM() {
        return 0;
    }

    public int subPubliceM() {
        return 0;
    }
}