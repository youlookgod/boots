package com.deep.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @Author: george
 * @Date: 2018/6/12-19-25
 * @Description:
 */
public class TestRedis {
    public static void main(String[] args){
        Jedis jedis = new Jedis("192.168.81.129");
        jedis.incr("list");
        jedis.watch("list");
        String str = jedis.get("list");
        System.out.println(str);
    }

    private void trasaction(Jedis jedis){
        Transaction transaction = jedis.multi();
        jedis.incr("list");
        jedis.incr("test");
        transaction.exec();
    }
}
