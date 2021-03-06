package com.training.stream;

import com.training.domain.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: George
 * @Date 2018/5/14 19:34
 * @Description jdk1.8新特性 lambda表达式的使用
 **/
public class TestJDKLambda {
    public static void main(String[] args){
//        listToArray();
//        arrayToList();
//        testCharBubbleSort();
        //selectSort();
        testThreadLambda();
    }

    private static void testThreadLambda(){
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        System.out.println(Thread.currentThread().getName());
        Callable<String> callable = ()->{
            return "nihao";
        };
        List<Callable<String>> list = Arrays.asList(()->"hello",()->"world",()->"test");
        List<Future<String>> futures;
        try {
            futures = executorService.invokeAll(list);
            for(Future future:futures){
                System.out.println(future.get(10,TimeUnit.SECONDS));
            }

            Future<String> f = executorService.submit(callable);
            System.out.println(f.get());
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());

    }

    private static void listToArray(){
        List<Student> list = new ArrayList<>();
        for(int i = 10; i<90; i++){
            Student s = new Student();
            s.setName("name"+i);
            s.setPhone("159872893"+i);
            list.add(s);
        }

        Student[] students = list.stream().toArray(Student[]::new);
        System.out.println(students[0].getName());
    }

    private static void arrayToList(){
        Student[] students = new Student[20];
        for(int i = 0; i < students.length; i++){
            Student student = new Student();
            student.setName("george"+i);
            student.setAddress("上海松江" + i + "弄");
            student.setPhone("136837125" + i);
            students[i] = student;
        }

        List<Student> studentList = Stream.of(students).collect(Collectors.toList());

        String[] forSort = new String[]{"f","b","p","o","t","c","l","k","a"};
        Arrays.sort(forSort, (s,a) -> -s.compareTo(a));

        System.out.println(forSort.length);
    }

}
