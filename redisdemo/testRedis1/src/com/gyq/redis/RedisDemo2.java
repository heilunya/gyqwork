package com.gyq.redis;

import redis.clients.jedis.Jedis;

public class RedisDemo2 {

	public void test(){
		Jedis jedis = RedisServer.getSentinelPool().getResource();
		jedis.set("foot", "2f23");
		RedisServer.getSentinelPool().returnResource(jedis);
	}
	
	public static void main(String[] args) throws InterruptedException {
		RedisDemo2 demo=new RedisDemo2();
		demo.test();
		System.out.println("************done...");
	}

}
