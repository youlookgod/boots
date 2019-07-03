package com.training.collection;

import com.training.domain.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * george 2019/6/20 12:57
 */
public class TestList {
    public static void main(String[] args) {
        List<Student> studentList = new ArrayList<>();
        Student student1 = new Student("张三", "上海", "1234");
        Student student2 = new Student("张四", "广州", "1234");
        Student student3 = new Student("张五", "北京", "1234");
        Student student4 = new Student("张六", "深圳", "1234");
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);
        studentList.add(student4);
        removeUnnecessaryParam(studentList);
        System.out.println("....");
    }

    private static void removeUnnecessaryParam(List<Student> studentList) {
        List<Student> temp = new ArrayList<>(studentList);
        studentList.clear();
        temp.stream().forEach(v -> {
            if (v.getName().equals("张三") || v.getName().equals("张四")) {
                studentList.add(v);
            }
        });
    }
}
