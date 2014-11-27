package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.boss.database.DataBase;
import com.job.CheckRecordTask;

/**
 * @author guoyaqiu
 * @info 发送邮件类
 */
public class SendEmail {

	private static Logger log = Logger.getLogger(SendEmail.class.getName());
	private Config config = null;
	
	public SendEmail(){
		config = new Config();
		//config.setProperties(pro);
	}
	/**
	 * 发送邮箱
	 * @param Msg
	 */
	public synchronized boolean sendEmail(String Msg) {
		
		
		Msg = URLEncoder.encode(Msg);
		String title = "录音服务器假死";
		title = URLEncoder.encode(title);
		String[] toEmail = config.getToEmail().split(",");
		BufferedReader buff = null;
		HttpURLConnection urlconn = null;
		for(int i=0;i<toEmail.length;i++){
			String urlAddress = "http://192.168.60.118/subsystem/cti/sendTips.jsp?toEmail="+toEmail[i]+"&title="+title+"&body="+Msg+"&sendType=email";
			//log.info("*************the urlAddress is "+urlAddress);
			
			try {
				URL url = new URL(urlAddress);
				urlconn = (HttpURLConnection) url.openConnection();
			//	urlconn.setConnectTimeout(1000 * 60 * 1);
			//	urlconn.setReadTimeout(1000 * 60 * 1);
				
				// 设置通用的请求属性
				urlconn.setRequestProperty("accept", "*/*");
				urlconn.setRequestProperty("connection", "Keep-Alive");
				urlconn.setRequestProperty("user-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				
	            //建立连接
				urlconn.connect();
				
				log.info("--------------------sendEmail,建立请求连接－－－－－－－－－－－－－－－－－－");
				
				InputStreamReader inputStreamReader = new InputStreamReader(urlconn.getInputStream(), "GBK");
				buff = new BufferedReader(inputStreamReader);
				StringBuilder sb = new StringBuilder();
				String s = null;
				
				if (null != buff) {
					while (null != (s = buff.readLine())) { 		// 读取页面内容
						sb.append(s + "\n");
					}
					
					log.info("--------------------sendEmail,得到请求的结果－－－－－－－－－－－－－－－－－－" + (null != sb ? sb.toString().trim() : "sb等于空"));
					
					if (null != sb && !"".equals(sb.toString())) {
						if(null != sb.toString() && "true".equals(sb.toString().trim())){  //判断返回结果
							log.info("--------------------sendEmail,发送邮件提醒,发送成功!－－－－－－－－－－－－－－－－－－");
//							sendEmailCount++;
						}else{
							log.info("--------------------sendEmail,发送邮件提醒,发送失败!－－－－－－－－－－－－－－－－－－");
						}
					}
				}
			} catch (Exception e) {
				log.error("--------------------sendEmail,发送邮件提醒,发生异常－－－－－－－－－－－－－－－－－－" + e.getMessage());
				e.printStackTrace();
			}finally{
				try {
					if(null != buff){   
						buff.close();
					}
					if(null != urlconn){
						urlconn.disconnect();
					}
				} catch (IOException e) {
					log.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		return true;
	}
	
	
//	public void sendEmail2(String Msg){
//		String sendType = "email";
//		String toEmail = "8960@jobcn.com";
//		String toPersonId = "JCNEP5991";
//		String title = "录音服务器假死";
//		DataBase           db   = null;
//		Connection         conn = null;
//		Statement          stmt = null;
//		String             sql  = null;
//		String database = "jobcn_boss_operlog";
//		
//		try{
//			db = new DataBase();
//			conn = db.connectjdbc(database);
//			stmt = conn.createStatement();
//			title=java.net.URLDecoder.decode(title,"gbk");
//			Msg=java.net.URLDecoder.decode(Msg,"gbk");
//			sql="insert into tips(sendType,fromEmail,sender,toEmail,toPersonId,title,msg,flag,registerdate) "
//				+" values('"+sendType+"','BOSS@jobcn.com','BOSS','"+toEmail+"','"+toPersonId+"','"+title+":','"+Msg+"',0,getdate())";	 
//			//log.info("the sql is "+sql);
//			stmt.execute(sql) ;
//			if (stmt != null) {
//				stmt.close();
//				stmt = null;
//			}		
//			if (conn != null) {
//				conn.close();
//				conn = null;
//			}
//		}catch(Exception e){
//			log.error("the function sendEmail(String Msg) error \n"+e.getMessage());
//			e.printStackTrace();
//		}finally{
//			try {
//				if (stmt != null) {
//					stmt.close();
//					stmt = null;
//				}
//			} catch (Exception e2) {
//				log.error("the close stmt error \n"+e2.getMessage());
//				e2.printStackTrace();
//			}
//			try {
//				if (conn != null) {
//					conn.close();
//					conn = null;
//				}
//			} catch (Exception e3) {
//				log.error("the close conn error \n"+e3.getMessage());
//				e3.printStackTrace();
//			}
//		}
//	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SendEmail().sendEmail("test");
	}

}
