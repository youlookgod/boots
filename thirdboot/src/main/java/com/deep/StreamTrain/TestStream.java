package com.deep.StreamTrain;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: George
 * @Date 2018/5/14 10:19
 * @Description
 **/
public class TestStream {
    public static void main(String[] args) {
        //testToMap();
        //System.out.println(efficiency());
        testFilter();

        Random random = new Random();
        int n = random.nextInt(0);
        System.out.println(n);
    }

    private static void testFilter() {
        List<Student> studentList = new ArrayList<Student>(){
            {
                add(new Student("张三","湖北",""));
                add(new Student("李四","湖南",""));
                add(new Student("王五","湖北",""));
                add(new Student("瘪三","湖南",""));
            }
        };

        studentList = studentList.stream().filter(student -> !"湖南".equals(student.getAddress())).collect(Collectors.toList());
        System.out.println(studentList);
    }


    private static String efficiency() {
        List<String> testList = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            testList.add("test" + i);
        }
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long forStart = System.currentTimeMillis();
        for (int j = 0; j < testList.size(); j++) {
            testList.get(j).hashCode();
        }
        String result1 = "for循环耗时==" + (System.currentTimeMillis() - forStart);

        long streamStart = System.currentTimeMillis();
        testList.stream().forEach((v) -> v.hashCode());
        String result2 = "stream.forEach耗时==" + (System.currentTimeMillis() - streamStart);

        long foreachStart = System.currentTimeMillis();
        testList.stream().forEach((v) -> v.hashCode());
        String result3 = "forEach耗时==" + (System.currentTimeMillis() - foreachStart);

        long foreach2Start = System.currentTimeMillis();
        testList.forEach((v) -> v.hashCode());
        String result4 = "forEach耗时==" + (System.currentTimeMillis() - foreach2Start);

        long foreach3Start = System.currentTimeMillis();
        testList.iterator().forEachRemaining(v -> v.hashCode());
        String result5 = "forEach耗时==" + (System.currentTimeMillis() - foreach3Start);
        return result1 + "||" + result2 + "||" + result3 + "||" + result4 + "||" + result5;
    }

    /**
     * 测试toMap
     */
    private static void testToMap() {
        List<Student> list = new ArrayList<>();
        for (int i = 10; i < 20; i++) {
            Student s = new Student();
            s.setName("name" + i);
            s.setPhone("159872893" + i);
            list.add(s);
        }
        for (int j = 10; j < 20; j++) {
            if (j % 2 == 0) {
                Student ss = new Student();
                ss.setName("name" + j);
                ss.setPhone("421424141" + j);
                list.add(ss);
            }
        }

        for (int n = 10; n < 20; n++) {
            if (n % 4 == 0) {
                Student ss = new Student();
                ss.setName("name" + n);
                ss.setPhone("333333333" + n);
                list.add(ss);
            }
        }

//        list.stream().forEach(value ->{
//            System.out.println(value.getName()+"|||"+value.getPhone());
//        });

        //list转成map<String,Student>,并设置map中key重复时候解决策略，(first,last) -> first意思是map中已经有张三的学生了，当list再遇到张三则以最开始的张三的student信息为准，相反若是last则以最后那个张三
//        Map<String,String> studentMap = list.stream().collect(Collectors.toMap(Student::getName, Student::getPhone, (s, a) -> s+","+a));
//        Iterator<Map.Entry<String,Student>> iterator = studentMap.entrySet().iterator();
//        while(iterator.hasNext()){
//            String key = iterator.next().getKey();
//            String value = studentMap.get(key).getPhone();
//            System.out.println(key + "====" + value);
//        }

        //Map<String,Student> studentMap = list.stream().collect(Collectors.toMap(Student::getName, student ->student, (first, last) -> first));
        Map<String, String> studentMap = list.stream().collect(Collectors.toMap(Student::getName, Student::getPhone, (s, a) -> s + "," + a));
        Iterator<Map.Entry<String, String>> iterator = studentMap.entrySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next().getKey();
            String value = studentMap.get(key);
            System.out.println(key + "====" + value);
        }

        String[] strs = new String[]{"a", "c", "b"};
        Arrays.sort(strs, (s, t) -> {
            return s.compareTo(t);
        });

        System.out.println("测试");
    }
}
