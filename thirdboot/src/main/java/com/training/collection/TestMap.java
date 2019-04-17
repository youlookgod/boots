package com.training.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * george 2019/3/1 15:09
 */
public class TestMap {
    public static void main(String[] args) {
        Map<String, Long> map = new HashMap<>();
        map.put("key1", 12L);
        map.put("key2", 12L);
        map.put("key3", 12L);
        updateMap(map, "key2");
        System.out.println(map.get("key2"));
    }

    private static void updateMap(Map<String, Long> old, String key) {
        old.put(key, 15L);
    }

}
