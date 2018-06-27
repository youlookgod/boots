package com.deep.redis;

import redis.clients.jedis.Jedis;

/**
 * @Author: george
 * @Date: 2018/6/12-19-25
 * @Description:
 */
public class TestRedis {
    public static void main(String[] args){
        Jedis jedis = new Jedis("192.168.81.129");
        String str = jedis.get("list");
        System.out.println(str);
    }
}
