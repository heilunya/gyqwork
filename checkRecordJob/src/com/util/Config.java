package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * @author guoyaqiu
 * @info 读取配置文件类
 */
public class Config {

	public static final String filePath = new File("").getAbsolutePath() + "\\conf\\config.properties"  ;
	private static Logger log = Logger.getLogger(Config.class.getName());
	
	private Properties pro = new Properties();
	private FileInputStream inputStream = null;
	private File properFile = null;
	
	private String record_Server_IP = null;
	private String channelMap = null;
	private String disk = null;
	private String toEmail = null;
	
	
	private void openProperties(){
		try{
			//log.info("the file path is " + filePath);
			properFile = new File(filePath);
			inputStream = new FileInputStream(properFile);
			pro.load(inputStream);
		}catch(Exception e){
			log.error("openProperties error : "+e.getMessage());
		}
	}
	
//	public Properties getProperties(){
//		pro = new Properties();
//		openProperties();
//		return pro;
//	}
	
//	public void setProperties(Properties pro){
//		this.pro = pro;
//	}
	
	public void closeProperties(){
		try {
			inputStream.close();
			properFile = null ;
			inputStream = null;
			System.gc();
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//返回所有待检查的服务器IP
	public String getRecord_Server_IP(){
		openProperties();
		record_Server_IP=pro.getProperty("Record_Server_IP");
		//System.out.println(record_Server_IP);
		closeProperties();
		return record_Server_IP;
	}
	
	//返回对应的逻辑端口，如：4000,4999
	public String getChannelMapByIP(String ip){
		openProperties();
		channelMap=pro.getProperty("ChannelMap_"+ip);
		closeProperties();
		return channelMap;
	}
	
	//返回对应的盘符，如：V:
	public String getDiskByIP(String ip){
		openProperties();
		disk = pro.getProperty("Disk_"+ip);
		closeProperties();
		return disk;
	}
	
	//返回发送邮件的邮箱
		public String getToEmail(){
			openProperties();
			toEmail=pro.getProperty("ToEmail");
			//System.out.println(record_Server_IP);
			closeProperties();
			return toEmail;
		}
	
//	public boolean testProEqual(Config conf){
//		log.info("***the first conf is "+this.pro);
//		log.info("***the second conf is "+conf.pro);
//		if(this.pro == conf.pro){
//			return true ;
//		}else{
//			return false ;
//		}
//	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Config();
		System.out.println("done ...");

	}

}
