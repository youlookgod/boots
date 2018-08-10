package com.deep;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class TestMuliptMap {
    public static void main(String[] args) {
        //testMultiMap();
        float f1 = (float) 0.133;
        String result1 = String.format("%.2f",f1);
        System.out.println(result1);

        File file = new File("E:\\tempdata\\test.txt");
        String[] strs = new String[]{"你好","你真好","你太好了","你可以啊"};
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
            StringBuilder sb = new StringBuilder("你好吗##");
            for (String str : strs) {
                sb.append(str).append("##");
            }

            int index = sb.lastIndexOf("##");
            sb.replace(index, sb.length(), "");
            String result = sb.toString()+";";
            fos.write(result.getBytes());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int sum = 90;
        int v1 = 11;
        Float num = ((float) v1 / sum) * 100;
        num = (float) (Math.round(num * 100)) / 100;

        List<String> testList = new ArrayList<>(10);
        testList.add("12313");
        testList.add("545142");
        int size = testList.size();
        double f = 45.246335;
        System.out.println(String.format("%.2f", f));

        String str = "false";
        if (1 > 0 && (2 > 1 || "1".equals("2"))) {
            str = "true";
        }

        AtomicLong atomicLong = new AtomicLong(0);
        atomicLong.addAndGet(1);

        Calendar cal11 = Calendar.getInstance();
        int day11 = cal11.get(Calendar.DATE);
        cal11.set(Calendar.DATE, day11);
        Date yesterday = cal11.getTime();
        cal11.set(Calendar.DATE, day11 - 7);
        Date sevenDay = cal11.getTime();
        List<String> sevenday = getDayBetween(sevenDay, yesterday);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化为年月日
        SimpleDateFormat sdf1 = new SimpleDateFormat("M/d");//格式化为年月日
        String date11 = sdf1.format(new Date());
        Calendar cal1 = Calendar.getInstance();
        int day1 = cal1.get(Calendar.DATE);
        cal1.set(Calendar.DATE, day1 - 7);
        String beforeSevenDay = sdf.format(cal1.getTime());

        String date = "2018-07-04";
        Date d = null;
        String md = null;
        try {
            d = sdf.parse(date);
            md = sdf1.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        //cal.setTime(new Date());
        int day = cal.get(Calendar.DATE);

        cal.set(Calendar.DATE, day);
        Date beforeDay = cal.getTime();
        String before = sdf.format(beforeDay);

        List<String> sevenDays = getDayBetween(beforeDay, new Date());


//        Date date = new Date();
//        GregorianCalendar gc = (GregorianCalendar)Calendar.getInstance();
//        gc.setTime(date);
//        int day = gc.get(5);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化为年月日
//        Date beginOfToday = new Date();
//        String today = sdf.format(beginOfToday);

//        String yesterday = sdf.format(getBeginOfYesterday());
//        String today = sdf.format(getBeginOfToday());
//
//        Date beginOfYesterday = getBeginOfYesterday();
//        Date endOfYesterday = getEndOfYesterday();
//
//        Date beginOfToday = getBeginOfToday();
//        Date endOfToday = getEndOfToday();


       /* String name = "random.value";
        String name1 = name.substring("random.".length());
        System.out.println(name1);*/
    }

    public static List<String> getDayBetween(Date beginDate, Date endDate) {
        List<String> result = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(beginDate);
        max.setTime(endDate);
        Calendar curr = min;

        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(5, 1);
        }

        return result;
    }

    public static Date getEndOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getEndOfToday());
        cal.add(5, -1);
        return cal.getTime();
    }

    public static Date getEndOfToday() {
        Calendar cal = new GregorianCalendar();
        cal.set(11, 23);
        cal.set(12, 59);
        cal.set(13, 59);
        cal.set(14, 999);
        return cal.getTime();
    }

    public static Date getBeginOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getBeginOfToday());
        cal.add(5, -1);
        return cal.getTime();
    }

    private static Date getBeginOfToday() {
        Calendar cal = new GregorianCalendar();
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    private static void testMultiMap() {
        List<String> list = new ArrayList<>();
        int i = 0;
        for (; i < 5; i++) {
            list.add("name" + i);
        }

        list.add(0, "nametest1");

        list.add(0, "nametest2");
        list.add(0, "nametest3");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("早班 9:00-11:00", "周一");
        map.add("早班 9:00-11:00", "周二");
        map.add("中班 1:00-3:00", "周三");
        map.add("晚班 5:00-8:00", "周四");
        map.add("晚班 5:00-8:00", "周五");
        map.add("中班 1:00-3:00", "周六");
        map.add("早班 9:00-11:00", "周日");
        Set<String> set = map.keySet();
        for (String key : set) {
            List<String> values = map.get(key);
            System.out.println(StringUtils.join(values.toArray(), " ") + ":" + key);
        }
    }
}
