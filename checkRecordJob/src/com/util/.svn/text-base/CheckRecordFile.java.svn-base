package com.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.job.CheckRecordTask;

/**
 * 
 * @author guoyaqiu
 * @info 查询录音文件的相关信息类
 */
public class CheckRecordFile {
	
	private static Logger log = Logger.getLogger(CheckRecordFile.class.getName());
	private Config config = null;
	
	public CheckRecordFile(){
		config = new Config();
		//config.setProperties(pro);
	}
	
	/**
	 * @param ip
	 * 查询对应服务器上当天的录音文件夹最新修改时间。
	 * 盘符要对应得上
	 */
	public List<String> checkFolderLastModifyInfo(String ip){
		String path = null;
		String partDirName = null;
		List<String> lastModifyInfo = new ArrayList<String>();
		String disk = config.getDiskByIP(ip);
		if(disk == null || "".equals(disk)){
			log.error("Disk_"+ip+" is null,please check the config.properties!");
			return null;
		}
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");  
		String date=dateformat.format(new Date());
		path = disk+"\\record\\"+date;
		//log.info("the path is "+path);
		long maxmodifytime = 0 ;
		String maxModifyTime = null;
		try{
			File parDir=new File(path);
			String childDirName[];
			childDirName=parDir.list();
			File childDir = null;
			String recordFileName[];
			String lastModifyFileName = null;
			SimpleDateFormat timeformat=new SimpleDateFormat("HH:mm");  
			for(int i=0;i<childDirName.length;i++){
				childDir = new File(path+"\\"+childDirName[i]);
				if(childDir.isDirectory()){
					if(childDir.lastModified()>maxmodifytime){
						maxmodifytime = childDir.lastModified();
						recordFileName = childDir.list();
						lastModifyFileName = recordFileName[recordFileName.length-1];
						partDirName = childDirName[i];
						//log.info(maxmodifytime);
					}
				}
				//log.info(childDirName[i]);
			}
			maxModifyTime = timeformat.format(new Date(maxmodifytime));
			//log.info("maxModifyTime = "+maxModifyTime);
			//log.info("lastModifyFileName = "+lastModifyFileName);
			//log.info("partDirName = "+partDirName);
			lastModifyInfo.add(maxModifyTime);
			lastModifyInfo.add(lastModifyFileName);
			lastModifyInfo.add(partDirName);
			return lastModifyInfo;
		}catch(NullPointerException e){
			log.info("the path is not exist...");
			log.error("the function checkFolderLastModifyInfo(String ip) error \n"+e.getMessage());
			return null;
		}
		
	}
	
	/**
	 * 
	 * 传入ip
	 * 返回该ip对应的映射盘符下是否有当天的文件夹。
	 */
	public boolean checkDirExistByIP(String ip){
		String path = null;
		String disk = config.getDiskByIP(ip);
		if(disk == null || "".equals(disk)){
			log.error("Disk_"+ip+" is null,please check the config.properties!");
			log.error("the checkDirExistByIP return false...");
			return false;
		}
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");  
		String date=dateformat.format(new Date());
		path = disk+"\\record\\"+date;
		//log.info("the path is "+path);
		File folder=new File(path);
		if(folder.exists()){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * 
	 * 传入ip
	 * 如果连record文件夹都没有，表示服务器可能死了，要发邮件提醒。
	 */
	public boolean checkServiceDone(String ip){
		String position = null;
		String disk = config.getDiskByIP(ip);
		if(disk == null || "".equals(disk)){
			log.error("Disk_"+ip+" is null,please check the config.properties!");
			log.error("the checkServiceDone return false...");
			return false;
		}
		position = disk+"\\record";
		File folder=new File(position);
		if(folder.exists()){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * 
	 * 
	 * 检查对应的映射盘符下是否有当天的文件夹。
	 */
	public String checkDirExist(){
		String path = null;
		String returnMsg = "";
		String ipList = config.getRecord_Server_IP();
		String disk = null;
		if(ipList==null||"".equals(ipList)){
			log.error("record_Server_IP is null...");
		}else{
			String[] arrayIp = ipList.split(",");
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");  
			String date=dateformat.format(new Date());
			
			for(int i=0;i<arrayIp.length;i++){
				disk = config.getDiskByIP(arrayIp[i]);
				path = disk+"\\record\\"+date;
				File folder=new File(path);
				if(folder.exists()){
					returnMsg = returnMsg + "<br/>" + arrayIp[i] + " 有今天的录音文件但是没有录音数据！";
				}else{
					//log.info("the "+ ip + " 没有今天的录音文件2...");
				}
			}
		}
		
		//log.info("the returnMsg = "+ returnMsg);
		return returnMsg;
		
	}
	
	/**
	 * 通过IP查询对应映射文件夹的剩余空间，休眠5秒后再查询，以此判断是否录音文件正在生成。
	 * 
	 */
	public boolean checkUseSpaceIsRangeOrNotByFilename(String ip,String parkDirName,String fileName){
		String path = null;
		long filelength1,filelength2;
		String disk = config.getDiskByIP(ip);
		if(disk == null || "".equals(disk)){
			log.error("Disk_"+ip+" is null,please check the config.properties!");
			log.error("the checkUseSpaceIsRangeOrNotByFilename return false...");
			return false;
		}
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd");  
		String date=dateformat.format(new Date());
		path = disk+"\\record\\"+date+"\\"+parkDirName+"\\"+fileName;
		File file=new File(path);
		filelength1 = file.length();
		try{
			Thread.sleep(5000);
		}catch(Exception e){
			log.error("the function checkUseSpaceIsRangeOrNotByFilename(String ip,String parkDirName,String fileName) error \n"+e.getMessage());
			e.printStackTrace();
		}
		filelength2 = file.length();
		if(filelength1==filelength2){
			return false ;
		}else{
			return true ;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
