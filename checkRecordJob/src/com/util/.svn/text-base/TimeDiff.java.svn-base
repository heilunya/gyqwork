package com.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.job.CheckRecordTask;

/**
 * @author guoyaqiu
 * @info 工具类
 */
public class TimeDiff {
	
	private static Logger log = Logger.getLogger(TimeDiff.class.getName());

	/**
	 * 
	 * 传入两个时间字符串，如16:20,16:23
	 * 返回两个时间之前间隔多少秒
	 */
	public int compareTime(String time1,String time2){
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");  
		String date=dateformat.format(new Date());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String date1 = date+" "+time1;
		String date2 = date+" "+time2;
		//log.info("date1 = "+date1);
		//log.info("date2 = "+date2);
		int difftime = 0 ;
		try {
		    Date dt1 = df.parse(date1);
		    Date dt2 = df.parse(date2);
		    difftime = (int)(dt2.getTime()-dt1.getTime())/1000;
		    //log.info("the difftime is "+difftime);
		    
		} catch (Exception e) {
			log.error("the function compareTime(String time1,String time2) error \n"+e.getMessage());
		    e.printStackTrace();
		}
		return difftime;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
