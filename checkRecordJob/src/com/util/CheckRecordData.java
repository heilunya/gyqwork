package com.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.boss.database.DataBase;

/**
 * @author guoyaqiu
 * @info 连接jobcn_cti数据库，查询录音数据类
 */
public class CheckRecordData {
	
	private static Logger log = Logger.getLogger(CheckRecordData.class.getName());
	private Config config = null;
	
	public CheckRecordData(){
		config = new Config();
		//config.setProperties(pro);
	}
	
	/**
	 * 查询数据库，获取每台服务器保存到数据库的最新录音数据
	 * 与getdate()的分钟数差距
	 */
	public List<List<String>> checkDatabase(){
		DataBase           db   = null;
		Connection         conn = null;
		Statement          stmt = null;
		ResultSet          rs   = null;
		String             sql  = null;
		String ip;
		String diffmin;
		String recordTime;
		String duration;
		String registerDate;
		String database = "jobcn_cti";
		List<List<String>> allInfo = new ArrayList<List<String>>() ;
		
		
		try{
			db = new DataBase();
			conn = db.connectjdbc(database);
			stmt = conn.createStatement();
			String record_Server_IP = config.getRecord_Server_IP();
			String[] channelRange = null;
			if(record_Server_IP==null||"".equals(record_Server_IP)){
				log.error("record_Server_IP is null...");
			}else{
				String ipArray[] = record_Server_IP.split(",");
				for(int i=0;i<ipArray.length;i++){
					String channelMap = config.getChannelMapByIP(ipArray[i]);
					if(channelMap==null||"".equals(channelMap)){
						log.error("channelMap_"+ipArray[i]+" is null,please check it...");
						continue ;
					}
					channelRange = channelMap.split(",");
					sql = "select top 1 datediff(s,registerDate,getdate()) as diffmin,recordTime,duration,registerDate from record where channel between '"+channelRange[0]+"' and '"+channelRange[1]+"'  and "
							 + "registerDate between convert(varchar(10),getdate(),120) and convert(varchar(10),dateadd(d,1,getdate()),120) order by "
							 + "registerDate desc;";
					rs = stmt.executeQuery(sql) ;
					//log.info(sql);
					while(rs.next()){
						//ip= "192.168.60.37";
						diffmin= rs.getString("diffmin");
						//log.info("diffmin = "+diffmin);
						recordTime= rs.getString("recordTime").substring(11, 16);
						//log.info("recordTime = "+recordTime);
						duration = rs.getString("duration");
						//log.info("duration = "+duration);
						registerDate = rs.getString("registerDate");
						//log.info("registerDate = "+registerDate);
						List<String> iptoinfo = new ArrayList<String>();
						iptoinfo.add(ipArray[i]);
						iptoinfo.add(diffmin);
						iptoinfo.add(recordTime);
						iptoinfo.add(duration);
						iptoinfo.add(registerDate);
						allInfo.add(iptoinfo);
					}
				}
			}
			db.close(rs, stmt, conn);
		}catch(Exception e){
			log.error("the function checkDatabase() error \n"+e.getMessage());
			e.printStackTrace();
		}finally{
			db.close(rs, stmt, conn);
		}
		return allInfo;
	}
	
	public String checkDatabaseByIP(String ip){
		DataBase           db   = null;
		Connection         conn = null;
		Statement          stmt = null;
		ResultSet          rs   = null;
		String             sql  = null;
		String registerDate = null;
		String database = "jobcn_cti";
		String channelMap = config.getChannelMapByIP(ip);
		if(channelMap==null||"".equals(channelMap)){
			log.error("channelMap_"+ip+" is null,please check it...");
		}else{
			String[] channelRange = channelMap.split(",");
			try{
				db = new DataBase();
				conn = db.connectjdbc(database);
				stmt = conn.createStatement();
				sql = "select top 1 datediff(s,registerDate,getdate()) as diffmin,recordTime,duration,registerDate from record where channel between '"+channelRange[0]+"' and '"+channelRange[1]+"'  and "
						 + "registerDate between convert(varchar(10),getdate(),120) and convert(varchar(10),dateadd(d,1,getdate()),120) order by "
						 + "registerDate desc;";
				rs = stmt.executeQuery(sql) ;
				while(rs.next()){
					registerDate= rs.getString("registerDate");
					log.info("registerDate = "+registerDate);
				}
			db.close(rs, stmt, conn);
			}catch(Exception e){
				log.error("the function checkDatabaseByIP(String ip) error \n"+e.getMessage());
				e.printStackTrace();
			}finally{
				db.close(rs, stmt, conn);
			}
		}
			
		return registerDate;
	}
	
	/**
	 * @param fileName
	 * 根据文件名查询record表是否有该录音数据。
	 * 
	 */
	public boolean checkDatabaseByFileName(String fileName){
		DataBase           db   = null;
		Connection         conn = null;
		Statement          stmt = null;
		ResultSet          rs   = null;
		String             sql  = null;
		String database = "jobcn_cti";
		boolean isExist = false;
		try{
			db = new DataBase();
			conn = db.connectjdbc(database);
			stmt = conn.createStatement();
			sql = "select top 1 id from record where recordDate=convert(varchar(10),getdate(),120) and filePath like '%"+fileName+"'";
			rs = stmt.executeQuery(sql) ;
			if(rs.next()){
				isExist = true ;
			}
			db.close(rs, stmt, conn);
		}catch(Exception e){
			log.error("the function checkDatabaseByFileName(String fileName) error \n"+e.getMessage());
			e.printStackTrace();
		}finally{
			db.close(rs, stmt, conn);
		}
		return isExist;
	}
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	
}
