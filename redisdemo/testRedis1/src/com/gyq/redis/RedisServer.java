package com.gyq.redis;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;


import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public class RedisServer {

	private static JedisPool pool = null;
	private static JedisSentinelPool spool =null;
	public static JedisPool getPool() {
		if (pool == null) {
			pool = new JedisPool(new JedisPoolConfig(), "192.168.61.137", 6379);
		}
		return pool;
	}

	public static void returnResource(Jedis redis) {
		if (redis != null) {
			pool.returnResource(redis);
		}
	}
	
	
	private static final String MASTER_NAME = "mymaster";

	protected static HostAndPort sentinel1 = HostAndPortUtil
			.getSentinelServers().get(0);
	protected static HostAndPort sentinel2 = HostAndPortUtil
			.getSentinelServers().get(1);
	protected static Set<String> sentinels = new HashSet<String>();

	public static JedisSentinelPool getSentinelPool()  {
		if (spool == null) {
			sentinels.add(sentinel1.toString());
			sentinels.add(sentinel2.toString());
			GenericObjectPoolConfig config = new GenericObjectPoolConfig();
			spool= new JedisSentinelPool(MASTER_NAME, sentinels,config);
		}
		return spool;
	}
}
