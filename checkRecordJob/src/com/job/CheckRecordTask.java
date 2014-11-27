package com.job;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimerTask;
import java.util.List;

import org.apache.log4j.Logger;

import com.util.*;



/**
 * @author guoyaqiu
 * 
 */
public class CheckRecordTask extends TimerTask{
	
	private static Logger log = Logger.getLogger(CheckRecordTask.class.getName());
	
	//这个记录上次任务查询最后生成的录音文件但是在数据库没有数据，并记录次情况的次数。
	private static String[] IPList = {"192.168.60.30","192.168.60.31","192.168.60.33","192.168.60.37","192.168.60.38","192.168.60.39"};
	private static int[] FailCount = {0,0,0,0,0,0};
	private static String[] FailFileName = {"","","","","",""};
	private static String[] LastRangeFile = {"","","","","",""};

	/**
	 * 检查录音服务器是否假死的任务
	 */
	public void run() {
		Config config = new Config();
		CheckRecordData checkRecordData = new CheckRecordData();
		CheckRecordFile checkRecordFile = new CheckRecordFile();
		SendEmail sendEmail = new SendEmail();
		TimeDiff timeDiff = new TimeDiff();
		List<List<String>> allDatabaseInfo = checkRecordData.checkDatabase();
		String ip = null;
		int diffsec,diffsec2 ;
		String registerDate1,registerDate2;
		String recordTime = null;
		int duration = 0;
		String[] ipArray = config.getRecord_Server_IP().split(",");
		if(allDatabaseInfo!=null && allDatabaseInfo.size()>0){
			if(allDatabaseInfo.size()<ipArray.length){
				log.info(allDatabaseInfo.size()+"台服务器有今天的数据，现在开始检查没有数据的服务器是否正常...");
				String Msg = "";
				//剔除那些今天有数据的服务器
				for(int i=0;i<allDatabaseInfo.size();i++){
					ip = allDatabaseInfo.get(i).get(0);
					for(int j=0;j<ipArray.length;j++){
						if(ip.equals(ipArray[j])){
							ipArray[j]="";
							break;
						}
					}
				}
				for(int i=0;i<ipArray.length;i++){
					log.info("-------------------------------------------------");
					//只查没有今天数据的服务器
					if(!ipArray[i].equals("")){
						if(checkRecordFile.checkServiceDone(ipArray[i])){
							log.info("ipArray[i]="+ ipArray[i] + " 能映射上。。。");
						}else{
							sendEmail.sendEmail("ip为："+ipArray[i]+" 的服务器连不上，请检查2！");
							continue;
						}
						if(checkRecordFile.checkDirExistByIP(ipArray[i])){
							//有可能是第一通录音文件刚开始生成，等到录音文件生成完了，数据才会插入数据库，此时再查询数据库
							List<String> lastModifyInfo = checkRecordFile.checkFolderLastModifyInfo(ipArray[i]);
							String lastmodifyTime = lastModifyInfo.get(0);
							String lastmodifyfilename = lastModifyInfo.get(1);
							String partDirName = lastModifyInfo.get(2);
							if(checkRecordFile.checkUseSpaceIsRangeOrNotByFilename(ipArray[i],partDirName,lastmodifyfilename)){
								log.info("ipArray[i]="+ipArray[i]+" 检查到录音文件还在生成中。");
								for(int k=0;k<IPList.length;k++){
									if(ipArray[i].equals(IPList[k])){
										if(!LastRangeFile[k].equals("")){
											log.info("上次检查时录音文件正在生成，所以跳过，跳过的文件是："+LastRangeFile[k]);
											String lastPartDirName = LastRangeFile[k].split(",")[0];
											String lastRangeFileName = LastRangeFile[k].split(",")[1];
											if(lastRangeFileName.equals(lastmodifyfilename)){
												log.info("这次检查，该文件还是作为最新生成文件，并且还在生成中，跳过，等待下一轮检查。");
											}else{
												log.info("又有新的文件在生成，文件名是："+lastmodifyfilename+"  现在检查上一轮检查时还在生成的录音文件。");
												if(checkRecordFile.checkUseSpaceIsRangeOrNotByFilename(ipArray[i],lastPartDirName,lastRangeFileName)){
													log.info("上一轮检查时还在生成的录音，这次也还在生成，故跳过，等待下一轮检查。");
												}else{
													log.info("上一轮检查时还在生成的录音，这次已经生成完了，现在检查数据库是否有该录音数据。");
													LastRangeFile[k]=partDirName+","+lastmodifyfilename;
													if(checkRecordData.checkDatabaseByFileName(lastRangeFileName)){
														log.info("录音文件 "+lastRangeFileName+" 在数据库中可以查到，故正常。");
														if(FailCount[k]!=0){
															FailCount[k]=0;
															FailFileName[k]="";
														}
														LastRangeFile[k]="";
													}else{
														log.info("录音文件 "+lastRangeFileName+" 的数据在数据库中查不到，先记录下。");
														if(FailCount[k]!=0){
															if(FailFileName[k].equals(lastRangeFileName)){
																log.info("该文件已经存在于保存失败记录中。");
															}else{
																FailCount[k]=FailCount[k]++;
																if(FailCount[k]>=2){
																	Msg = Msg + "<br/>" + ipArray[i] + " 有今天的录音文件但是没有录音数据！"
																			+"<br/>原因：连续两轮检查该服务器上的录音数据都没保存。"
																			+"<br/>第一封保存失败的录音是："+FailFileName[k]
																			+"<br/>第二封保存失败的录音是："+lastRangeFileName;
																	sendEmail.sendEmail(Msg);
																	FailCount[k]=0;
																	FailFileName[k]="";
																	LastRangeFile[k]="";
																}
															}
														}else{
															FailCount[k]=FailCount[k]++;
															FailFileName[k]=lastRangeFileName;
														}
													}
												}
											}
										}else{
											LastRangeFile[k]=partDirName+","+lastmodifyfilename;
											log.info("新加入一个lastRangeFile为："+LastRangeFile[k]);
											log.info("ipArray[i]="+ipArray[i]+" 今天的录音文件夹刚生成，第一通录音还在录中，所以还没插入到数据库，数据库还没有该服务器今天的录音数据，属于正常。");
										}
										break;
									}
								}
								continue;
							}else{
								if(checkRecordData.checkDatabaseByFileName(lastmodifyfilename)){
									log.info("ipArray[i]="+ipArray[i]+" 的第一通录音 "+lastmodifyfilename+" 在数据库中可以查到，故正常。");
									continue;
								}else{
									if(checkRecordData.checkDatabaseByIP(ipArray[i])==null){
										//Date date = new Date();
										//Calendar c = Calendar.getInstance();
										//c.set(Calendar.HOUR_OF_DAY,8);//这是时
										//c.set(Calendar.MINUTE,18);//这是分
										//c.set(Calendar.SECOND,0);//这是秒
										//if (date.after(c.getTime())){
											String failFileName = null;
											for(int k=0;k<IPList.length;k++){
												if(ip.equals(IPList[k])){
													if(FailCount[k]!=0){
														failFileName = FailFileName[k];
													}
													break;
												}
											}
											if(failFileName!=null){
												if(lastmodifyfilename.equals(failFileName)){
													continue;
												}
											}
											for(int k=0;k<IPList.length;k++){
												if(ip.equals(IPList[k])){
													FailCount[k]=FailCount[k]++;
													FailFileName[k]=lastmodifyfilename;
													if(FailCount[k]>=2){
														Msg = Msg + "<br/>" + ipArray[i] + " 有今天的录音文件但是没有录音数据！"
																+"<br/>原因：连续两轮检查该服务器上的录音数据都没保存。"
																+"<br/>第一封保存失败的录音是："+failFileName
																+"<br/>第二封保存失败的录音是："+lastmodifyfilename;
														FailCount[k]=0;
														FailFileName[k]="";
													}
													LastRangeFile[k]="";
													break;
												}
											}
										//}else{
										//	log.info("ipArray[i]="+ipArray[i]+" 查了数据库，还是完全没有数据，但是现在还没到8:10am，故忽略它。");
										//	continue;
										//}	
									}else{
										log.info("ipArray[i]="+ipArray[i]+" 查到有数据了，故正常。");
										continue;
									}
								}
							}
						}else{
							log.info("ipArray[i]="+ ipArray[i] + " 不单单没有今天的数据，也没有今天的录音文件，故正常。");
						}
					}
				}
				if(!Msg.equals("")){
					log.info("发送邮件的信息Msg是："+Msg);
					log.info("现在发送一封邮件...");
					sendEmail.sendEmail(Msg);
				}
			}
			if(allDatabaseInfo.size()==ipArray.length){
				log.info(ipArray.length+"台服务器都有今天的数据，现在开始检查...");
			}
			for(int i=0;i<allDatabaseInfo.size();i++){
				log.info("------------------------------------------------");
				List<String> ipToInfo = allDatabaseInfo.get(i);
				ip = ipToInfo.get(0);
				diffsec = Integer.parseInt(ipToInfo.get(1));
				recordTime = ipToInfo.get(2);
				duration = Integer.parseInt(ipToInfo.get(3));
				registerDate1 = ipToInfo.get(4);
				if(diffsec>=300){
					if(checkRecordFile.checkServiceDone(ip)){
						log.info("ip="+ ip + " 能映射上");
					}else{
						sendEmail.sendEmail("ip为："+ip+" 的服务器连不上，上一分钟还连得上，请检查！");
					}
					log.info("ip="+ip+" 已经有5分钟没有数据了，现在开始检查...");
					List<String> lastModifyInfo = checkRecordFile.checkFolderLastModifyInfo(ip);
					if(lastModifyInfo==null){
						continue;
					}else{
						String lastmodifyTime = lastModifyInfo.get(0);
						String lastmodifyfilename = lastModifyInfo.get(1);
						String partDirName = lastModifyInfo.get(2);
						String failFileName = null;
						for(int k=0;k<IPList.length;k++){
							if(ip.equals(IPList[k])){
								if(FailCount[k]!=0){
									failFileName = FailFileName[k];
								}
								break;
							}
						}
						if(failFileName!=null){
							if(lastmodifyfilename.equals(failFileName)){
								log.info("还是刚才那个没保存到数据库的录音文件，没有新文件生成，故跳过！");
								continue;
							}
						}
						if(recordTime.equals(lastmodifyTime)){
							log.info("ip="+ip+" 没有新录音文件生成,数据库中是最新数据，正常。");
							for(int k=0;k<IPList.length;k++){
								if(ip.equals(IPList[k])){
									if(FailCount[k]!=0){
										FailCount[k]=0;
										FailFileName[k]="";
									}
									LastRangeFile[k]="";
									break;
								}
							}
							continue;
						}else{
							diffsec2 = timeDiff.compareTime(recordTime,lastmodifyTime);
							if(diffsec2<duration){
								log.info("ip="+ip+" 新的一通录音文件，在上一通录音还没录完就开始了，现在对服务器进行检查...");
								//找出文件名，然后查数据库中是否有记录
								if(checkRecordData.checkDatabaseByFileName(lastmodifyfilename)){
									log.info("ip="+ip+" 最新生成的录音文件的数据已写入数据库，正常。");
									for(int k=0;k<IPList.length;k++){
										if(ip.equals(IPList[k])){
											if(FailCount[k]!=0){
												FailCount[k]=0;
												FailFileName[k]="";
											}
											LastRangeFile[k]="";
											break;
										}
									}
									continue;
								}else{
									if(checkRecordFile.checkUseSpaceIsRangeOrNotByFilename(ip,partDirName,lastmodifyfilename)){
										log.info("ip="+ip+" 最新生成的录音文件还没写入数据库，因为录音文件还在生成中。");
										for(int k=0;k<IPList.length;k++){
											if(ip.equals(IPList[k])){
												if(!LastRangeFile[k].equals("")){
													log.info("上次检查时录音文件正在生成，所以跳过，跳过的文件是："+LastRangeFile[k]);
													String lastPartDirName = LastRangeFile[k].split(",")[0];
													String lastRangeFileName = LastRangeFile[k].split(",")[1];
													if(lastRangeFileName.equals(lastmodifyfilename)){
														log.info("这次检查，该文件还是作为最新生成文件，并且还在生成中，跳过，等待下一轮检查。");
													}else{
														log.info("又有新的文件在生成，文件名是："+lastmodifyfilename+"  现在检查上一轮检查时还在生成的录音文件。");
														if(checkRecordFile.checkUseSpaceIsRangeOrNotByFilename(ip,lastPartDirName,lastRangeFileName)){
															log.info("上一轮检查时还在生成的录音，这次也还在生成，故跳过，等待下一轮检查。");
														}else{
															log.info("上一轮检查时还在生成的录音，这次已经生成完了，现在检查数据库是否有该录音数据。");
															LastRangeFile[k]=partDirName+","+lastmodifyfilename;
															if(checkRecordData.checkDatabaseByFileName(lastRangeFileName)){
																log.info("录音文件 "+lastRangeFileName+" 在数据库中可以查到，故正常。");
																if(FailCount[k]!=0){
																	FailCount[k]=0;
																	FailFileName[k]="";
																}
																LastRangeFile[k]="";
															}else{
																log.info("录音文件 "+lastRangeFileName+" 的数据在数据库中查不到，先记录下。");
																if(FailCount[k]!=0){
																	if(FailFileName[k].equals(lastRangeFileName)){
																		log.info("该文件已经存在于保存失败记录中。");
																	}else{
																		FailCount[k]=FailCount[k]++;
																		if(FailCount[k]>=2){
																			String Msg2 = "<br/>" + ip + " 有今天的录音文件但是没有录音数据！"
																					+"<br/>原因：连续两轮检查该服务器上的录音数据都没保存。"
																					+"<br/>第一封保存失败的录音是："+FailFileName[k]
																					+"<br/>第二封保存失败的录音是："+lastRangeFileName;
																			sendEmail.sendEmail(Msg2);
																			FailCount[k]=0;
																			FailFileName[k]="";
																			LastRangeFile[k]="";
																		}
																	}
																}else{
																	FailCount[k]=FailCount[k]++;
																	FailFileName[k]=lastRangeFileName;
																}
															}
														}
													}
												}else{
													LastRangeFile[k]=partDirName+","+lastmodifyfilename;
													log.info("新加入一个lastRangeFile为："+LastRangeFile[k]);
													//log.info("ip="+ip+" 今天的录音文件夹刚生成，第一通录音还在录中，所以还没插入到数据库，数据库还没有该服务器今天的录音数据，属于正常。");
												}
												break;
											}
										}
										continue;
									}else{
										registerDate2 = checkRecordData.checkDatabaseByIP(ip);
										if(registerDate1.equals(registerDate2)){
											//累计连续两轮查询都有不用的录音文件在数据库中找不到录音数据，就判断为服务器假死，发送邮件，其中若有新的录音数据生成，则清空对应FailCount,FailFileName
											log.info("ip="+ip+" 再查一次数据库，还是没有新数据。");
											failFileName = null;
											for(int k=0;k<IPList.length;k++){
												if(ip.equals(IPList[k])){
													if(FailCount[k]!=0){
														failFileName = FailFileName[k];
													}
													break;
												}
											}
											if(failFileName!=null){
												if(lastmodifyfilename.equals(failFileName)){
													continue;
												}
											}
											for(int k=0;k<IPList.length;k++){
												if(ip.equals(IPList[k])){
													FailCount[k]=FailCount[k]++;
													FailFileName[k]=lastmodifyfilename;
													if(FailCount[k]>=2){
														String Msg = "IP 为："+ip+" 录音服务器假死！"
																+"<br/>原因：连续两轮检查该服务器上的录音数据都没保存。"
																+"<br/>第一封保存失败的录音是："+failFileName
																+"<br/>第二封保存失败的录音是："+lastmodifyfilename;
														log.info("ip="+ip+" 已经连续两轮查询录音文件都没保存到数据库，且是不同的录音文件，故判断服务器假死，现在发送邮件。");
														log.info("发送的邮件Msg是："+Msg);
														sendEmail.sendEmail(Msg);
														FailCount[k]=0;
														FailFileName[k]="";
													}
													LastRangeFile[k]="";
													break;
												}
											}
										}else{
											log.info("ip="+ip+" 已经又有数据生成了，故服务器正常。");
											for(int k=0;k<IPList.length;k++){
												if(ip.equals(IPList[k])){
													if(FailCount[k]!=0){
														FailCount[k]=0;
														FailFileName[k]="";
													}
													LastRangeFile[k]="";
													break;
												}
											}
											continue;
										}
									}
								}
							}else{
								log.info("ip="+ip+" 新的一通录音文件，在上一通录音录完才开始，现在对服务器进行检查...");
								if(checkRecordFile.checkUseSpaceIsRangeOrNotByFilename(ip,partDirName,lastmodifyfilename)){
									log.info("ip="+ip+" 最新生成的录音文件还没写入数据库，因为录音文件还在生成中。");
									for(int k=0;k<IPList.length;k++){
										if(ip.equals(IPList[k])){
											if(!LastRangeFile[k].equals("")){
												log.info("上次检查时录音文件正在生成，所以跳过，跳过的文件是："+LastRangeFile[k]);
												String lastPartDirName = LastRangeFile[k].split(",")[0];
												String lastRangeFileName = LastRangeFile[k].split(",")[1];
												if(lastRangeFileName.equals(lastmodifyfilename)){
													log.info("这次检查，该文件还是作为最新生成文件，并且还在生成中，跳过，等待下一轮检查。");
												}else{
													log.info("又有新的文件在生成，文件名是："+lastmodifyfilename+"  现在检查上一轮检查时还在生成的录音文件。");
													if(checkRecordFile.checkUseSpaceIsRangeOrNotByFilename(ip,lastPartDirName,lastRangeFileName)){
														log.info("上一轮检查时还在生成的录音，这次也还在生成，故跳过，等待下一轮检查。");
													}else{
														log.info("上一轮检查时还在生成的录音，这次已经生成完了，现在检查数据库是否有该录音数据。");
														LastRangeFile[k]=partDirName+","+lastmodifyfilename;
														if(checkRecordData.checkDatabaseByFileName(lastRangeFileName)){
															log.info("录音文件 "+lastRangeFileName+" 在数据库中可以查到，故正常。");
															if(FailCount[k]!=0){
																FailCount[k]=0;
																FailFileName[k]="";
															}
															LastRangeFile[k]="";
														}else{
															log.info("录音文件 "+lastRangeFileName+" 的数据在数据库中查不到，先记录下。");
															if(FailCount[k]!=0){
																if(FailFileName[k].equals(lastRangeFileName)){
																	log.info("该文件已经存在于保存失败记录中。");
																}else{
																	FailCount[k]=FailCount[k]++;
																	if(FailCount[k]>=2){
																		String Msg2 = "<br/>" + ip + " 有今天的录音文件但是没有录音数据！"
																				+"<br/>原因：连续两轮检查该服务器上的录音数据都没保存。"
																				+"<br/>第一封保存失败的录音是："+FailFileName[k]
																				+"<br/>第二封保存失败的录音是："+lastRangeFileName;
																		sendEmail.sendEmail(Msg2);
																		FailCount[k]=0;
																		FailFileName[k]="";
																		LastRangeFile[k]="";
																	}
																}
															}else{
																FailCount[k]=FailCount[k]++;
																FailFileName[k]=lastRangeFileName;
															}
														}
													}
												}
											}else{
												LastRangeFile[k]=partDirName+","+lastmodifyfilename;
												log.info("新加入一个lastRangeFile为："+LastRangeFile[k]);
												//log.info("ip="+ip+" 今天的录音文件夹刚生成，第一通录音还在录中，所以还没插入到数据库，数据库还没有该服务器今天的录音数据，属于正常。");
											}
											break;
										}
									}
									continue;
								}else{
									log.info("ip="+ip+" 最新录音文件已经生成完成，现在检查数据库...");
									registerDate2 = checkRecordData.checkDatabaseByIP(ip);
									if(registerDate1.equals(registerDate2)){
										log.info("ip="+ip+" 再查一次数据库，还是没有新数据。");
										failFileName = null;
										for(int k=0;k<IPList.length;k++){
											if(ip.equals(IPList[k])){
												if(FailCount[k]!=0){
													failFileName = FailFileName[k];
												}
												break;
											}
										}
										if(failFileName!=null){
											if(lastmodifyfilename.equals(failFileName)){
												continue;
											}
										}
										for(int k=0;k<IPList.length;k++){
											if(ip.equals(IPList[k])){
												FailCount[k]=FailCount[k]++;
												FailFileName[k]=lastmodifyfilename;
												if(FailCount[k]>=2){
													String Msg = "IP 为："+ip+" 录音服务器假死！"
															+"<br/>原因：连续两轮检查，该服务器上的录音数据都没保存。"
															+"<br/>第一封保存失败的录音文件名是："+failFileName
															+"<br/>第二封保存失败的录音文件名是："+lastmodifyfilename;
													log.info("ip="+ip+" 已经连续两轮查询录音文件都没保存到数据库，且是不同的录音文件，故判断服务器假死，现在发送邮件。");
													log.info("发送的邮件Msg是："+Msg);
													sendEmail.sendEmail(Msg);
													FailCount[k]=0;
													FailFileName[k]="";
												}
												LastRangeFile[k]="";
												break;
											}
										}
									}else{
										log.info("ip="+ip+" 已经又有数据生成了，故服务器正常。");
										for(int k=0;k<IPList.length;k++){
											if(ip.equals(IPList[k])){
												if(FailCount[k]!=0){
													FailCount[k]=0;
													FailFileName[k]="";
												}
												LastRangeFile[k]="";
												break;
											}
										}
										continue;
									}
								}
							}
						}
					}
				}else{
					log.info(ip+" 录音服务器正常");
					log.info("the ip is "+ip);
					log.info("the diffsec is "+diffsec);
					log.info("the recordTime is "+recordTime);
					log.info("continue...\n");
				}
			}
		}else{
			log.info("------------------------------------------------");
			//查询下录音服务器是否有当天的文件夹
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY,8);//这是时
			c.set(Calendar.MINUTE,18);//这是分
			c.set(Calendar.SECOND,0);//这是秒
			if (date.after(c.getTime())){
				String returnMsg = checkRecordFile.checkDirExist();
				if(!returnMsg.equals("")){
					log.info("六台服务器都没数据，但是却有些有今天的录音文件，故出错，现在发送邮件...");
					log.info("the returnMsg is "+returnMsg);
					sendEmail.sendEmail(returnMsg);
				}
			}else{
				log.info("六台服务器都没录音数据，但是现在还不到8：10am，故忽略它!");
			}
			
		}
	}
	
	
	public static void main(String args[]){
//		List<List<String>> allInfo=new CheckRecordTask().checkDatabase();
//		log.info(allInfo.size());
//		List<String> info = allInfo.get(0);
//		log.info(info.size());
//		log.info("........done");
//		new CheckRecordTask().checkFileModifyTime("192.168.60.30");
//		new CheckRecordTask().compareTime("16:20","16:23");
//		new CheckRecordTask().checkDatabaseByIP("192.168.60.33");
//		new CheckRecordTask().testFunction();
		
	}

}
