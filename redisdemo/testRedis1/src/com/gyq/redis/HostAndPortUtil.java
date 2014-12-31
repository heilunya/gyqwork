package com.gyq.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Protocol;

public class HostAndPortUtil {
	private static List<HostAndPort> redisHostAndPortList = new ArrayList<HostAndPort>();
	private static List<HostAndPort> sentinelHostAndPortList = new ArrayList<HostAndPort>();

	static {
		redisHostAndPortList.add(new HostAndPort("192.168.61.137",
				Protocol.DEFAULT_PORT));
		redisHostAndPortList.add(new HostAndPort("192.168.61.137",
				Protocol.DEFAULT_PORT - 1));

		sentinelHostAndPortList.add(new HostAndPort("192.168.61.137",
				Protocol.DEFAULT_SENTINEL_PORT));
		sentinelHostAndPortList.add(new HostAndPort("192.168.61.137",
				Protocol.DEFAULT_SENTINEL_PORT - 1));

		String envRedisHosts = "192.168.61.137";
		String envSentinelHosts = "192.168.61.137";

		redisHostAndPortList = parseHosts(envRedisHosts, redisHostAndPortList);
		sentinelHostAndPortList = parseHosts(envSentinelHosts,
				sentinelHostAndPortList);
	}

	public static List<HostAndPort> parseHosts(String envHosts,
			List<HostAndPort> existingHostsAndPorts) {

		if (null != envHosts && 0 < envHosts.length()) {

			String[] hostDefs = envHosts.split(",");

			if (null != hostDefs && 2 <= hostDefs.length) {

				List<HostAndPort> envHostsAndPorts = new ArrayList<HostAndPort>(
						hostDefs.length);

				for (String hostDef : hostDefs) {

					String[] hostAndPort = hostDef.split(":");

					if (null != hostAndPort && 2 == hostAndPort.length) {
						String host = hostAndPort[0];
						int port = Protocol.DEFAULT_PORT;

						try {
							port = Integer.parseInt(hostAndPort[1]);
						} catch (final NumberFormatException nfe) {
						}

						envHostsAndPorts.add(new HostAndPort(host, port));
					}
				}

				return envHostsAndPorts;
			}
		}

		return existingHostsAndPorts;
	}

	public static List<HostAndPort> getRedisServers() {
		return redisHostAndPortList;
	}

	public static List<HostAndPort> getSentinelServers() {
		return sentinelHostAndPortList;
	}

}
