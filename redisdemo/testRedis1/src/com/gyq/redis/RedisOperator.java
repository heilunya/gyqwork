package com.gyq.redis;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

/**
 * @author hutian
 *操作索引
 */
public class RedisOperator {
	
	/**
	 * @param key 
	 * @param dto 
	 * 保存javabean
	 */
	public void setObject(String key,Object dto ) {
		Jedis jedis = RedisServer.getPool().getResource();
		jedis.set(key.getBytes(), SerializeUtil.serialize(dto));
		RedisServer.returnResource(jedis);
	}
	
	/**
	 * @param key
	 * @return
	 * 获取javabean
	 */
	public Object getObject(String key) {
		Jedis jedis = RedisServer.getPool().getResource();
		byte[] dto = jedis.get((key).getBytes());
		RedisServer.returnResource(jedis);
		return SerializeUtil.unserialize(dto);
	}
	
	public void setValue(String key,String value ) {
		Jedis jedis = RedisServer.getPool().getResource();
		jedis.set(key, value);
		RedisServer.returnResource(jedis);
	}
	
	public String getValue(String key){
		Jedis jedis = RedisServer.getPool().getResource();
		RedisServer.returnResource(jedis);
		return jedis.get(key);
	}
	
	public Map<String,String> collectionAddress(String sql){
		Map<String,String> datas=new HashMap<String, String>();
		DataBase database = new DataBase();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		//保存需要同步数据的 
		try {
			conn = database.connectjdbc("jobcn_boss_sale");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				datas.put("DICADDRESS"+rs.getString("id"), rs.getString("InviteEffectFlag"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.close(rs, stmt, conn);
		}
		return datas;
	}
	
	public List<CustomerDto> collectionCustomer(String sql){
		List<CustomerDto> datas=new ArrayList<CustomerDto>();
		DataBase database = new DataBase();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		//保存需要同步数据的 
		try {
			conn = database.connectjdbc("jobcn_boss_sale");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				CustomerDto dto=new CustomerDto();
				dto.setId(rs.getString("id"));
				dto.setPerId(rs.getString("perId"));
				dto.setJobcnId(rs.getString("jobcnId"));
				dto.setCityId(rs.getString("CityID"));
				dto.setProvinceId(rs.getString("ProvinceID"));
				dto.setCussort(rs.getString("CusSort"));
				datas.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.close(rs, stmt, conn);
		}
		return datas;
	}
}
