package com.training.CollectionTrain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class TestMuliptMap {
    public static void main(String[] args) {

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
