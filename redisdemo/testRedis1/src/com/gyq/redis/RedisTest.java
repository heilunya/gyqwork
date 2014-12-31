package com.gyq.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import redis.clients.jedis.Jedis;

public class RedisTest {
	public void test1() {
		//Jedis redis = new Jedis("192.168.61.137");// 连接redis
		Jedis redis = RedisServer.getPool().getResource();
		Set keys = redis.keys("*");// 列出所有的key，查找特定的key如：redis.keys("foo")
        Iterator t1 = keys.iterator();
        while (t1.hasNext()) {
                Object obj1 = t1.next();
                System.out.println(obj1);
        }
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RedisTest t1 = new RedisTest();
		t1.test1();
		System.out.println("*********finish...");
	}
}