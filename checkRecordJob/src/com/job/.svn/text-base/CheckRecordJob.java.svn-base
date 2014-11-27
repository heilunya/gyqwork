package com.job;
import java.util.Timer;

import org.apache.log4j.Logger;
/**
 * @author guoyaqiu
 */
public class CheckRecordJob {

	private static Logger log = Logger.getLogger(CheckRecordJob.class.getName());
	private Timer timer = null;

	  public void taskInitialized() {
	    timer = new Timer(true);
	    //设置任务计划，启动和间隔时间
	    log.info("now start CheckRecordTask ...");
	    timer.schedule(new CheckRecordTask(), 0, 240000);
	  }

	  public void taskDestroyed() {
	    timer.cancel();
	  }
	  
	  public static void main(String args[]){
		  new CheckRecordJob().taskInitialized();
		  while(true){
			  try{
				  new Thread().sleep(1000000);
				  log.info("sleep 1000s done ...");
			  }catch(Exception e){
				  e.printStackTrace();
				  log.error("the main error /n"+e.getMessage());
			  }
			  
		  }
	  }

}
