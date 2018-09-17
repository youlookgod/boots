package com.training.other;

import com.training.domain.Order;
import com.training.domain.Student;

import java.util.Optional;

/**
 * george 2018/9/14 15:48
 * 优雅的使用Optionnal判断null
 * http://www.ibloger.net/article/3209.html
 */
public class OptionalTrain {
    public static void main(String[] args) {
        Order order = new Order("21123", "购买", 32, "2018-09-14");
        Student student = new Student("张三", "上海市", "14341232531", 175, order);
        try {
            String orderType = getOrderType(student);
            System.out.println(orderType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Student newStudent = getStudent(student);
        System.out.println(newStudent.getName());
    }


    public static String getOrderType(Student student) throws Exception {
        return Optional.ofNullable(student).map(Student::getOrder).map(Order::getType).orElseThrow(() -> new Exception("参数错误"));
    }

    public static Student getStudent(Student student) {
        return Optional.ofNullable(student).filter(s -> s.getName().equals("李四")).orElseGet(() -> {
            Student student1 = new Student("李四", "苏州", "157257581");
            return student1;
        });
    }
}
