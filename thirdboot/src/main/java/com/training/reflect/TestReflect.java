package com.training.reflect;

import com.training.domain.Student;

import java.lang.reflect.*;

/**
 * george 2018/10/10 13:54
 * 一、在获取类中的fields、constructors、methods、annotations时， getXXX 都表示获取本类和父类public修饰的，而getDeclaredXXX表示获取本类所有修饰符类型的
 * 二、setAccessible设置java语言访问检查。false表示检查；true表示不检查，在获取private field的值时需设置不检查，否则会报 “can not access a member of class with modifiers "private"”，在invoke私有方法时也需要设置setAccessible(true)。
 *    即当修饰符是private的时候，需要加上setAccessible(true)。
 */
public class TestReflect {
    public static void main(String[] args) {
//        getFields();
        getMethods();
//        getConstructors();
    }

    /**
     * 功能描述:获取类字段
     */
    public static void getFields() {
        Student student = new Student("张三", "上海市", "123222131");
        //getFields获得某个类及其父类的所有的公共(public修饰符)的字段
        Field[] fields = Student.class.getFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }

        //getDeclaredFields获得某个类的所有声明的字段，即包括public、private和protected，但是不包括父类声明的字段
        Field[] fields1 = Student.class.getDeclaredFields();
        for (Field field : fields1) {
            String fieldName = field.getName();
            System.out.println(fieldName);

            int modifiers = field.getModifiers();
            System.out.println(modifiers);

            Class classz = field.getType();
            System.out.println(classz);

            //返回某字段在指定对象上的值
            try {
                field.setAccessible(true);//不进行java语言访问检查
                Object object = field.get(student);
                System.out.println(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 功能描述:获取类方法
     */
    private static void getMethods() {
        //getMethods获取本类和父类的public方法，所以这样可以获取Object的所有public方法
        Method[] methods = Student.class.getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            System.out.println("getMethods:" + methodName);
        }

        //getDeclaredMethods获取本类所有修饰类型的方法，
        Method[] methods1 = Student.class.getDeclaredMethods();
        for (Method method : methods1) {
            String methodName = method.getName();
            System.out.println("getDeclaredMethods:" + methodName);
        }

        try {
            Method method = Student.class.getDeclaredMethod("sayHello", String.class);
            method.setAccessible(true);//sayHello方法是private的，需要取消java访问检查
            method.invoke(new Student(), "张三");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述:获取类构造器
     */
    private static void getConstructors() {
        //getConstructors获取本类和父类的public 构造函数，所以这样可以获取Object的所有public构造函数
        Constructor[] constructors = Student.class.getConstructors();
        for (Constructor constructor : constructors) {
            String consName = constructor.getName();
            System.out.println("getConstructors:" + consName);
        }

        //getDeclaredConstructors获取本类所有修饰符构造函数，
        Constructor[] constructors1 = Student.class.getDeclaredConstructors();
        for (Constructor constructor : constructors1) {
            String consName = constructor.getName();
            System.out.println("getDeclaredConstructors:" + consName);
        }
    }
}
