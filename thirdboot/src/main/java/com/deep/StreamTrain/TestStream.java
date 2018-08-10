package com.deep.StreamTrain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.Predicate;
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
        //testFilter();
        //testSort();

        //testDistinct();

        //testStreamMap();

        //testGrouping();
        testGrouping2();

//        Random random = new Random();
//        int n = random.nextInt(0);
//        System.out.println(n);
    }

    private static void testGrouping2() {
        List<UserRequest> orderList = new ArrayList<UserRequest>() {
            {
                add(new UserRequest("r213", "123", "2018-07-10",2));
                add(new UserRequest("r213", "123", "2018-07-11",3));
                add(new UserRequest("r213", "124", "2018-07-10",1));
                add(new UserRequest("r213", "124", "2018-07-11",2));
                add(new UserRequest("r213", "125", "2018-07-10",2));
                add(new UserRequest("r213", "125", "2018-07-11",4));
                add(new UserRequest("r213", "126", "2018-07-10",2));
                add(new UserRequest("r213", "126", "2018-07-11",6));
                add(new UserRequest("r214", "234", "2018-07-10",3));
                add(new UserRequest("r214", "234", "2018-07-11",7));
                add(new UserRequest("r214", "235", "2018-07-10",2));
                add(new UserRequest("r214", "235", "2018-07-11",5));
                add(new UserRequest("r214", "236", "2018-07-10",7));
                add(new UserRequest("r214", "236", "2018-07-11",8));
                add(new UserRequest("r214", "237", "2018-07-10",12));
                add(new UserRequest("r214", "237", "2018-07-11",10));
            }
        };
        orderList.stream().collect(Collectors.groupingBy(UserRequest::getRobotId)).forEach((robotId,list)->{
            Map<String, Long> userMap = new HashMap<>();
            list.stream().collect(Collectors.groupingBy(UserRequest::getUserId)).forEach((userId,uList)->{
                Long count = uList.stream().mapToLong(UserRequest::getCount).sum();
                userMap.put(userId,count);
            });
            long sum = userMap.size();
        });
        System.out.println(111);

//        orderList.stream().collect(Collectors.groupingBy(UserRequest::getRobotId,Collectors.groupingBy(UserRequest::getUserId))).forEach((robotId,list)->{
//            Map<String, Long> userMap = new HashMap<>();
//            AtomicLong total = new AtomicLong(0);
//            list.forEach((userId,uList)->{
//                long sum = uList.stream().mapToLong(UserRequest::getCount).sum();
//                System.out.println(sum);
//                total.addAndGet(sum);
//                userMap.put(userId, sum);
//            });
//
//        });
    }

    private static void testStreamMap() {
        List<Order> orderList = new ArrayList<Order>() {
            {
                add(new Order("213", "香蕉", 12,"2018-07-10"));
                add(new Order("4424", "苹果", 18,"2018-07-10"));
                add(new Order("241524", "苹果", 10,"2018-07-10"));
                add(new Order("124111", "梨", 13,"2018-07-10"));
                add(new Order("124111", "梨", 13,"2018-07-10"));
                add(new Order("124111", "苹果", 13,"2018-07-09"));
            }
        };

        long total = orderList.stream().mapToLong(Order::getPrice).sum();

        List<String> idList = orderList.stream().map(Order::getId).collect(Collectors.toList());
        List<String> typeList = orderList.stream().filter(distinctByKey(Order::getType)).map(Order::getType).collect(Collectors.toList());
     }

    private static void testGrouping() {
        List<Order> orderList = new ArrayList<Order>() {
            {
                add(new Order("213", "香蕉", 12,"2018-07-10"));
                add(new Order("4424", "苹果", 18,"2018-07-10"));
                add(new Order("2415", "苹果", 10,"2018-07-10"));
                add(new Order("124111", "梨", 13,"2018-07-10"));
                add(new Order("124111", "梨", 13,"2018-07-10"));
                add(new Order("124111", "苹果", 13,"2018-07-09"));
            }
        };
        orderList.stream().collect(Collectors.groupingBy(Order::getType,Collectors.groupingBy(Order::getDate))).forEach((v,list)->{
            String type = v;
            System.out.println(v);
            list.forEach((k1,v1)->{
                System.out.println(v1.size());
                System.out.println(v1.get(0).getId());
            });
        });

//        orderList.stream().forEach(v->{
//            String name = v.getType();
//            v.setName(name);
//        });
//        orderList.stream().collect(Collectors.groupingBy(Order::getName)).forEach((v,list)->{
//            System.out.println(v);
//        });
//        Map<String, Long> priceMap = orderList.stream().collect(Collectors.groupingBy(Order::getType, Collectors.summingLong(Order::getPrice)));
//        priceMap.forEach((type, count) -> {
//            System.out.println(type + "" + count);
//        });
    }

    private static void testDistinct() {
        List<Student> studentList = new ArrayList<Student>() {
            {
                add(new Student("张三", null, "445656", 172));
                add(new Student("王五", "湖南", "18874525", 178));
                add(new Student("王五", "湖北", null, 170));
                add(new Student("瘪三", "湖南", "169481", 173));
            }
        };
        List<String> addressList = studentList.stream().map(Student::getAddress).collect(Collectors.toList());
        if (addressList.contains("湖南")) {
            System.out.println("有值");
        }
        studentList = studentList.stream().filter(distinctByKey(s -> s.getName())).collect(Collectors.toList());
        System.out.println(studentList.size());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private static void testSort() {
        List<Student> studentList = new ArrayList<Student>() {
            {
                add(new Student("张三", null, "445656", 172));
                add(new Student("", "湖南", "18874525", 178));
                add(new Student("王五", "湖北", null, 170));
                add(new Student("瘪三", "湖南", "169481", 173));
            }
        };

        studentList.stream().filter(s -> StringUtils.isNotEmpty(s.getName()) && StringUtils.isNotEmpty(s.getAddress()) && StringUtils.isNotEmpty(s.getPhone())).forEach(v -> {
            System.out.println(v.getName());
        });
        Comparator<Student> comparator = Comparator.comparing(Student::getHeight);
        studentList.sort(comparator.reversed());
        studentList.stream().forEach(v -> System.out.println(v.getHeight()));
    }

    private static void testFilter() {
        List<Student> studentList = new ArrayList<Student>() {
            {
                add(new Student("张三", "湖北", ""));
                add(new Student("李四", "湖南", ""));
                add(new Student("王五", "湖北", ""));
                add(new Student("瘪三", "湖南", ""));
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

    static class UserRequest{
        private String robotId;
        private String userId;
        private String date;
        private Integer count;
        public UserRequest(String robotId,String userId,String date,Integer count){
            this.robotId = robotId;
            this.userId = userId;
            this.date = date;
            this.count = count;
        }

        public String getRobotId() {
            return robotId;
        }

        public void setRobotId(String robotId) {
            this.robotId = robotId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

    static class Order {
        private String id;
        private String name;
        private String type;
        private long price;
        private String date;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Order(String id, String type, long price, String date) {
            this.id = id;
            this.type = type;
            this.price = price;
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getPrice() {
            return price;
        }

        public void setPrice(long price) {
            this.price = price;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
