package subsystem.recruit.parserresume;

import org.apache.struts.taglib.html.HtmlTag;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.HeadTag;
import org.htmlparser.tags.HeadingTag;
import org.htmlparser.tags.Html;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

import com.jobcn.util.StringUtil;

import subsystem.recruit.recruiteresume.RecruiteResumeForm;
public class ParserHtmlContent {
	private Parser parser=null ;
	private String title="kong";
	private String id = "", perResumeID = "", perAccountID = "", groupID = "",
			perName = "", sex = "", nationality = "", birthday = "",
			cardType = "", IDCardNum = "", height = "", maritalStatus = "",
			hometown_P = "", hometown_C = "", location_P = "", location_C = "",
			selfDescription = "", degreeID = "", degreeScript = "",
			relationPhone = "", mobileNum = "", bPNumber = "", iMNum = "",
			email = "", homepage = "", address = "", zipCode = "",
			techTitle = "", computerLevel = "", computerSkills = "",
			chineseLevel = "", cantoneseLevel = "", fLEngSpeciality = "",
			fLEngLevel = "", fLEngSpeechLevel = "", fLanguage = "",
			fLLevel = "", fLTwo = "", fLTwoLevel = "", fLOther = "",
			otherSkills = "", interesting = "", gethortation = "",
			jobFunction1 = "", jobFunction2 = "", jobFunction3 = "",
			jobSeeking1 = "", jobSeeking2 = "", jobSeeking3 = "",
			jobLocation1_P = "", jobLocation1_C = "", jobLocation2_P = "",
			jobLocation2_C = "", jobLocation3_P = "", jobLocation3_C = "",
			salary = "", salaryNegotiable = "", houseNeeded = "",
			checkinDate = "", otherRequirement = "", careerDirection = "",
			major = "", workExprience = "", workedComNumber = "",
			workedYear = "", workedMonth = "", cerFilename = "",
			photoname = "", photoflag = "", photoAttachFile = "",
			registerDate = "", updateDate = "", memberClass = "",
			resumeStatus = "", relationFlag = "", checkDate = "",
			checkFlag = "", perID = "", password = "", stateID = "",
			joinPubDate = "", pubSort = "", fromPubSort = "",
			resumeTypeID = "", lastRLLID = "", lastLinkDate = "",
			lastLinkContent = "", takeDate = "", giveupReason = "",
			giveupDate = "", rITID = "", currentWorkState = "", otherInfo = "",
			comeInReason = "", resumeUpdateDate = "", operID = "",
			operName = "", updateDate2 = "", registerDate2 = "", deptID = "",
			postID = "", recruiteChannelID = "", jobFunction4 = "",
			jobFunction5 = "", jobLocation4_P = "", jobLocation4_C = "",
			jobLocation5_P = "", jobLocation5_C = "", jobLocPC1 = "",
			jobLocPC2 = "", jobLocPC3 = "", jobLocPC4 = "", jobLocPC5 = "",
			bookingDate = "",recruiteChannelKeyWord="",blackListInfo="",duration="",
			age="",marrystr="";
	private String RITID="",edudegreeID="",degreeName="",beginDate="",endDate="",schoolName="",speciality="",certificate="",eduoperID="",eduoperName="",eduregisterDate="",eduupdateDate="";
	private String otherposition="",photourl="",idno="",wantworktype="",wantworkcalling="",listenability="",writeability="",lanage="",ability="";
	private String workexpbeginDate="",workexpendDate="",comName="",comTypestr="",comCalling="",mainCatalogstr="",jobFuncationID="",otherPosition="",description="",leftReason="",worksalary="",workexppoststr="";
	private StringBuffer workexpresultsb = new StringBuffer();
	private RecruiteResumeForm resumeform = new RecruiteResumeForm();
	//测试用的
	public static void main(String args[]){
		try {
			Parser parser = new Parser("");
			parser.setInputHTML("<div class='r'><div class='hed'><title>智联招聘</title></div><h2>guoyaqwiu test</h2></div>");
			NodeIterator iterator2 = parser.elements();
			HasAttributeFilter filter2 = new HasAttributeFilter("class","hed");
			//NodeIterator iterator2 = parser.extractAllNodesThatMatch (filter2).elements ();
			int i=0;
			while(iterator2.hasMoreNodes()){
				Node node = iterator2.nextNode();
				if(null!=node){
					//System.out.println(node.toHtml());
					if (node instanceof TextNode) {
					} else if (node instanceof TagNode) {
						Div div = (Div) node;
						for(Node n:div.getChildrenAsNodeArray()){
							if(n instanceof TitleTag){
								String title = n.toPlainTextString();
							}
						}
						
					}
				}
			}
			if(null != parser){
				// 根据条件过滤出有关简历的页面区域
				HasAttributeFilter filter = new HasAttributeFilter("class","r");
				NodeIterator iterator = parser.extractAllNodesThatMatch (filter).elements ();
				
				while(iterator.hasMoreNodes()){
					Node node = iterator.nextNode();
					if(null!=node){
						System.out.println(node.toHtml());
						//new ParserHtmlContent().getResumeInfo(node);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//设置Parser对象
	public void setParser(Parser parser){
		this.parser = parser ;
	}
	//解析返回title，用于判断该份简历是智联或前程无忧，
	//然后分别调用后面对应的解析方法getZhilianResumeInfo()，getQianchengResumeInfo()
	public String getTitle() throws ParserException{
		NodeIterator iterator = parser.elements();
		String str ="";
		int beginindex ;
		while(iterator.hasMoreNodes()){
			Node node = iterator.nextNode();
			str = node.toPlainTextString();
			if((beginindex=str.indexOf("ID:"))!=-1 && str.length()>beginindex+26){
				perAccountID = str.substring(beginindex+3, beginindex+26);
				
			}
			if(null!=node){
				if (node instanceof TextNode) {
				} else if (node instanceof TagNode) {
					if(node instanceof Html){
						Node nod = ((Html) node).getChild(1);
						//System.out.println(nod.toHtml());
						if(nod instanceof HeadTag){
							HeadTag headtag = (HeadTag) nod;
							for(Node n:headtag.getChildrenAsNodeArray()){
								if(n instanceof TitleTag){
									this.title = n.toPlainTextString();
									break;
									
								}
							}
						}
					}
					
				}
			}
		}
		return title.replaceAll("\t|\n|\r|", "").replace(" ", "").substring(0,2);
	}
	//解析智联招聘的简历
	public String getZhilianResumeInfo(){
		HasAttributeFilter filter = new HasAttributeFilter("id","resumeContentBody");
		NodeIterator iterator;
		try {
			iterator = this.parser.extractAllNodesThatMatch (filter).elements ();
		while(iterator.hasMoreNodes()){
			Node node = iterator.nextNode();
			if(null!=node){
				if (node instanceof TextNode) {
				} else if (node instanceof TagNode) {
					Div div = (Div) node;
					for(Node n1:div.getChildrenAsNodeArray()){
						if (n1 instanceof TagNode) {
						Div divn1 = (Div) n1;
						if("resume-preview-main-title posR".equals(divn1.getAttribute("class"))){
							for(Node n2:divn1.getChildrenAsNodeArray()){
								if (n2 instanceof TagNode) {
								Div divn2 = (Div) n2;
								if("main-title-fl fc6699cc".equals(divn2.getAttribute("class"))){
									perName= n2.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "");
								}else if("main-title-fr".equals(divn2.getAttribute("class"))){
									mobileNum = n2.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "").substring(3);
									//System.out.println("the mobileNum is "+mobileNum);
								}
							}
							}
						}
						
						if("summary".equals(divn1.getAttribute("class"))){
							for(Node n2:divn1.getChildrenAsNodeArray()){
								if (n2 instanceof TagNode) {
									if(n2 instanceof Div){
										Div divn2 = (Div) n2;
										if("summary-top".equals(divn2.getAttribute("class"))){
											String str = n2.toHtml().replace(" ", "").substring(32);
											int flg = str.indexOf("</span>");
											if(flg!=-1){
												str = str.substring(0, flg);
											}
											//System.out.println("************str2 is "+str);
											String[] str2 = str.split("&nbsp;&nbsp;&nbsp;&nbsp;");
											for(int i=0;i<str2.length;i++){
												sex = str2[0];
												//System.out.println("the sex is "+sex);
												if((str2[i].indexOf("岁")!=-1)&&(str2[i].indexOf("年")!=-1)&&(str2[i].indexOf("月")!=-1)){
													int beginIndex = str2[i].indexOf("("),endIndex = str2[i].indexOf(")");
													birthday = str2[i].substring(beginIndex+1,endIndex);
													if((beginIndex=birthday.indexOf("月"))!=-1){
														int t = Integer.valueOf(birthday.substring(5, beginIndex));
														if(t>9){
															birthday = birthday.replace("年","-").replace("月", "-")+"01";
														}else{
															birthday = birthday.replace("年","-0").replace("月", "-")+"01";
														}
													}
													//System.out.println("the birthday is "+birthday);
												}else if(str2[i].indexOf("工作经验")!=-1){
													int endIndex = str2[i].indexOf("年");
													String workexpyear = str2[i].substring(0,endIndex);
													//System.out.println("the workexpyear is "+workexpyear);
												}else if(str2[i].indexOf("婚")!=-1){
													marrystr = str2[i];
													//System.out.println("the marrystr is "+marrystr);
												}
											}
											
											flg = n2.toPlainTextString().indexOf("现居住地");
											String str3 = n2.toPlainTextString().substring(flg).replaceAll("\t|\n|\r|", "").replace(" ", "");
											//System.out.println(str3);
											if(flg!=-1){
												int endIndex = str3.indexOf("|户口");
												if(endIndex!=-1){
													location_C = str3.substring(5, endIndex);
													//System.out.println("the location_C is "+location_C);
													endIndex = str3.indexOf("|");
													//只是判断最常见的城市对于的省份，其他赋值为“”，下面相同
													if(location_C.equals("东莞")||location_C.equals("广州")||location_C.equals("深圳")||location_C.equals("佛山")||location_C.equals(" 中山")||location_C.equals("惠州")){
														location_P = "广东";
													}
													String str4 = str3.substring(endIndex+1);
													endIndex = str4.indexOf("|");
													if(endIndex!=-1){
														//System.out.println("the endIndex is "+endIndex);
														hometown_C = str4.substring(3,endIndex);
														//System.out.println("the hometown_C is "+hometown_C);
													}else{
														hometown_C = str4.substring(3);
														//System.out.println("the hometown_C is "+hometown_C);
													}
													if(hometown_C.equals("东莞")||hometown_C.equals("广州")||hometown_C.equals("深圳")||hometown_C.equals("佛山")||hometown_C.equals(" 中山")||hometown_C.equals("惠州")||hometown_C.equals("清远")){
														hometown_P = "广东";
													}
													//System.out.println("the hometown_P is "+hometown_P);
												}
											}
										}else if("summary-bottom".equals(divn2.getAttribute("class"))){
											String str1 = n2.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "").replace("：", ",");
											//System.out.println("the str1 is "+str1);
											String[] strarray = str1.split(",");
											int index1 = str1.indexOf("身份证");
											int index2 = str1.indexOf("手机");
											int index3 = str1.indexOf("E-mail");
											int index4 = str1.indexOf("地址");
											int index5 = str1.indexOf("邮编");
											int index6 = str1.indexOf("其他");
											int index7 = str1.indexOf("家庭电话");
											int i=1;
											if((index1!=-1)&&strarray.length>=i){
												idno = strarray[i].substring(0, 18);
												//System.out.println("the idno is "+idno);
												i++;
											}else if((index6!=-1)&&strarray.length>=i){
												i++;
											}
											if((index4!=-1)&&strarray.length>=i){
												int endindex = strarray[i].indexOf("|");
												if(endindex==-1){
													if((endindex=strarray[i].indexOf("手机"))==-1){
														endindex = 0;
													}
												}
												address = strarray[i].substring(0, endindex);
												
												//System.out.println("the address is "+address);
												i++;
											}
											if((index5!=-1)&&strarray.length>=i){
												zipCode = strarray[i].substring(0, 6);
												//System.out.println("the zipCode is "+zipCode);
												i++;
											}
											if((index2!=-1)&&strarray.length>=i){
												mobileNum = strarray[i].substring(0, 11);
												//mobileNum = strarray[i];
												//System.out.println("the mobileNum is "+mobileNum);
												i++;
											}
											if((index7!=-1)&&strarray.length>=i){
												//relationPhone = strarray[i].substring(0, 11);
												//mobileNum = strarray[i];
												//System.out.println("the mobileNum is "+mobileNum);
												i++;
											}
											if((index3!=-1)&&strarray.length>=i){
												email = strarray[i];
												//System.out.println("the email is "+email);
												i++;
											}
										}
									}if(n2 instanceof LinkTag){
										LinkTag nod = (LinkTag) n2;
										//现在解析出photourl，但是不保存
										photourl = nod.getLink();
										//System.out.println("the photourl is "+photourl);
									}
								}
							}
						}
						if("resume-preview-all".equals(divn1.getAttribute("class"))){
							Node n = divn1.getChild(1);
							if(n instanceof HeadingTag){
								HeadingTag nod = (HeadingTag) n;
								String h2text = n.toPlainTextString();
								if(h2text.equals("求职意向")){
									for(Node n2:divn1.getChildrenAsNodeArray()){
										if (n2 instanceof TagNode) {
											if(n2 instanceof Div){
												Div divn2 = (Div) n2;
												for(Node n3:((Div) n2).getChildrenAsNodeArray()){
												n = n3;
												if(n instanceof TableTag){
													TableTag table = (TableTag)n;
													for(Node t:table.getRows()){
														TableRow tbr = (TableRow) t;
														String tdd1 = tbr.getColumns()[0].toPlainTextString();
														String tdd2 = tbr.getColumns()[1].toPlainTextString();
														int index;
														if(tdd1.equals("期望工作地区：")){
															jobLocation1_C = tdd2;
															if((index=tdd2.indexOf("、"))!=-1){
																jobLocation1_C = tdd2.substring(0,index);
																tdd2 = tdd2.substring(index+1);
																if((index=tdd2.indexOf("、"))!=-1){
																	jobLocation2_C = tdd2.substring(0,index);
																	tdd2 = tdd2.substring(index+1);
																	if((index=tdd2.indexOf("、"))!=-1){
																		jobLocation3_C = tdd2.substring(0,index);
																	}else{
																		jobLocation3_C = tdd2;
																	}
																}else{
																	jobLocation2_C = tdd2;
																}
															}
															if(jobLocation1_C.equals("东莞")||jobLocation1_C.equals("广州")||jobLocation1_C.equals("深圳")||jobLocation1_C.equals("佛山")||jobLocation1_C.equals(" 中山")||jobLocation1_C.equals("惠州")){
																jobLocation1_P = "广东";
															}
															if(jobLocation2_C.equals("东莞")||jobLocation2_C.equals("广州")||jobLocation2_C.equals("深圳")||jobLocation2_C.equals("佛山")||jobLocation2_C.equals(" 中山")||jobLocation2_C.equals("惠州")){
																jobLocation2_P = "广东";
															}
															if(jobLocation3_C.equals("东莞")||jobLocation3_C.equals("广州")||jobLocation3_C.equals("深圳")||jobLocation3_C.equals("佛山")||jobLocation3_C.equals(" 中山")||jobLocation3_C.equals("惠州")){
																jobLocation3_P = "广东";
															}
															//System.out.println("the jobLocation1_C is "+jobLocation1_C);
														}else if(tdd1.equals("期望月薪：")){
															int endindex = tdd2.indexOf("元/月");
															int beginIndex = tdd2.indexOf("-");
															if((endindex!=-1)&&(beginIndex!=-1)){
																int salary1 = Integer.valueOf(tdd2.substring(0,beginIndex));
																int salary2 = Integer.valueOf(tdd2.substring(beginIndex+1,endindex));
																salary = String.valueOf((salary1+salary2)/2);
															}
															//System.out.println("the salary is "+salary);
														}else if(tdd1.equals("目前状况：")){
															currentWorkState = tdd2;
															//System.out.println("the currentWorkState is "+currentWorkState);
														}else if(tdd1.equals("期望工作性质：")){
															wantworktype = tdd2;
															//System.out.println("the wantworktype is "+wantworktype);
														}else if(tdd1.equals("期望从事职业：")){
															String[] jobseekingarray = tdd2.split("、");
															if(jobseekingarray.length>=3){
																jobSeeking1 = jobseekingarray[0];
																jobSeeking2 = jobseekingarray[1];
																jobSeeking3 = jobseekingarray[2];
															}else if(jobseekingarray.length==2){
																jobSeeking1 = jobseekingarray[0];
																jobSeeking2 = jobseekingarray[1];
															}else if(jobseekingarray.length==1){
																jobSeeking1 = jobseekingarray[0];
															}
															//System.out.println("the jobSeeking1,jobSeeking2,jobSeeking3 is "+jobSeeking1+jobSeeking2+jobSeeking3);
														}else if(tdd1.equals("期望从事行业：")){
															wantworkcalling = tdd2;
															//System.out.println("the wantworkcalling is "+wantworkcalling);
														}
													}
												}
											}
										}
									}
								}
								}
								else if(h2text.equals("自我评价")){
									for(Node n2:divn1.getChildrenAsNodeArray()){
										if (n2 instanceof Div) {
											selfDescription = n2.toPlainTextString().replace("&#8226;", "*");
											int index1 =0 ,index2=0;
											String subs="" ;
											
											if(((index1=selfDescription.indexOf("&"))!=-1)&&((index2=selfDescription.indexOf(";"))!=-1)){
												if(index2>index1){
													subs=selfDescription.substring(index1, index2+1);
													selfDescription = selfDescription.replaceAll(subs, "");
												}
											}
											
											//System.out.println("the selfDescription is"+ selfDescription);
										}
									}
								}else if(h2text.equals("项目经历")){
									
								}else if(h2text.equals("教育经历")){
									for(Node n2:divn1.getChildrenAsNodeArray()){
										if (n2 instanceof Div) {
											//System.out.println(n2.toPlainTextString());
											String str = n2.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "");
											//System.out.println(str);
											String[] strarray = str.split("&nbsp;&nbsp;");
											if(strarray.length>=4){
												beginDate = strarray[0].substring(0,7).replace(".", "-");
												if(strarray[0].indexOf("至今")!=-1){
													endDate = "";
												}else{
													endDate = strarray[0].substring(8, 15).replace(".", "-");
												}
												schoolName = strarray[1];
												speciality = strarray[2];
												degreeName = strarray[3].substring(0, 2);
											}
											//System.out.println("the beginDate endDate is "+beginDate+"/"+endDate);
											//System.out.println("the schoolName length is "+schoolName);
											//System.out.println("the speciality length is "+speciality);
											//System.out.println("the degreeName length is "+degreeName);
										}
									}
								}else if(h2text.equals("语言能力")){
									for(Node n2:divn1.getChildrenAsNodeArray()){
										if (n2 instanceof Div) {
											//System.out.println(n2.toPlainTextString());
											String str = n2.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "");
											//System.out.println(str);
											int index = str.indexOf("：");
											if(index!=-1){
												lanage = str.substring(0, index);
												str = str.substring(index+1);
												if(lanage.indexOf("英语")!=-1){
													if(str.length()>=6){
														fLEngLevel = str.substring(4, 6);
													}
												}else if(lanage.indexOf("粤语")!=-1){
													if(str.length()>=6){
														cantoneseLevel = str.substring(4, 6);
													}
												}else if(lanage.indexOf("普通话")!=-1){
													if(str.length()>=6){
														chineseLevel = str.substring(4, 6);
													}
												}
//												listenability = str.substring(4, 6);
//												writeability = str.substring(11, 13);				
											}
											//System.out.println("the lanage is "+lanage);
//											System.out.println("the listenability is "+listenability);
//											System.out.println("the writeability is "+writeability);
										}
									}
								}else if(h2text.equals("特长职业目标")){
									for(Node n2:divn1.getChildrenAsNodeArray()){
										if (n2 instanceof Div) {
											ability = n2.toPlainTextString();
											//System.out.println("the ability is "+ability);
										}
									}
								}else if(h2text.equals("证书")){
									
								}else if(h2text.equals("兴趣爱好")){
									for(Node n2:divn1.getChildrenAsNodeArray()){
										if (n2 instanceof Div) {
											interesting = n2.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "").replace("&#183;", "");
											//System.out.println("the interesting is "+interesting);
										}
									}
								}
							}
							
						}
						//工作经历
						if("resume-preview-all workExperience".equals(divn1.getAttribute("class"))){
							int workedyearint=0,workedmonthint=0;
							for(Node n2:divn1.getChildrenAsNodeArray()){
								String str = n2.toHtml().replace(" ", "");
								//String strarray = str.split("");
								//System.out.println("str is "+str);
								if(str.indexOf("<h2>")!=-1){
									workexpbeginDate = str.substring(4, 11).replace(".", "-");
									workexpresultsb.append(workexpbeginDate);
									workexpresultsb.append("#");
									String str2 = str.substring(12,14);
									//System.out.println("the str2 is "+str2);
									if(str2.equals("至今")){
										workexpendDate = "";
									}else{
										workexpendDate = str.substring(12,19).replace(".", "-");
									}
									workexpresultsb.append(workexpendDate);
									workexpresultsb.append("#");
									//System.out.println("the workexpbeginDate is "+workexpbeginDate);
									//System.out.println("the workexpendDate is "+workexpendDate);
									int index = str.indexOf("&nbsp;&nbsp;");
									String str3="";
									if(index!=-1){
										str2 = str.substring(index+2);
										index = str2.indexOf("&nbsp;&nbsp;");
										if(index!=-1){
											comName = str2.substring(10,index);
											str3 = str2.substring(index+12);
											//System.out.println("***********xxxxxx********"+str3);
										}else{
											comName = str2.substring(10);
										}
										workexpresultsb.append(comName);
										workexpresultsb.append("#");
										//System.out.println("the comName is "+comName);
									}
									index = str3.indexOf("（");
									int index2 = str3.indexOf("）");
									if(index!=-1&&index2!=-1){
										index = str3.indexOf("年");
										if(index!=-1){
											workedYear = str3.substring(1,index);
											index2 = str3.indexOf("个月");
											if(index2!=-1){
												workedMonth = str3.substring(index+1,index2);
											}
										}else{
											index2 = str3.indexOf("个月");
											if(index2!=-1){
												workedMonth = str3.substring(1,index2);
											}
										}
									}
									if(!workedYear.equals("")){
										workedyearint = workedyearint + Integer.valueOf(workedYear);
										workedYear="";
									}
									if(!workedMonth.equals("")){
										workedmonthint = workedmonthint + Integer.valueOf(workedMonth);
										workedyearint = workedyearint + workedmonthint/12 ;
										workedmonthint = workedmonthint%12;
										workedMonth="";
									}
								}else if(str.indexOf("<h5>")!=-1){
									String[] strarray = str.replace("|", ",").split(",");
									//System.out.println(str);
									int endIndex ;
									if(strarray.length>2){
										otherposition = strarray[strarray.length-2];
										endIndex = strarray[strarray.length-1].indexOf("</h5>");
										worksalary = strarray[strarray.length-1].substring(0, endIndex);
									}else if(strarray.length==2 && str.indexOf("元/月")!=-1){
										otherposition = strarray[0].substring(4);
										endIndex = strarray[1].indexOf("</h5>");
										worksalary = strarray[1].substring(0, endIndex);
									}else if(strarray.length==2){
										endIndex = strarray[1].indexOf("</h5>");
										otherposition = strarray[1].substring(0, endIndex);
										worksalary = "";
									}else if(strarray.length==1){
										endIndex = strarray[0].indexOf("</h5>");
										otherposition = strarray[0].substring(4, endIndex);
										worksalary = "";
									}
									//System.out.println("the otherposition is "+otherposition);
									//System.out.println("the worksalary is "+worksalary);
									workexpresultsb.append(otherposition);
									workexpresultsb.append("#");
									workexpresultsb.append(worksalary);
									workexpresultsb.append("#");
								}else if((str.indexOf("resume-preview-dl")!=-1)&&(str.indexOf("工作描述：")!=-1)){
									String str2 = n2.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "");
									int index = str2.indexOf("工作描述");
									if(index!=-1){
										description = str2.substring(5).replace("&#8226;", "*").replace("&quot;", "'").replace("&#183;","");
										int index1 =0 ,index2=0;
										String subs="" ;
										
										if(((index1=description.indexOf("&"))!=-1)&&((index2=description.indexOf(";"))!=-1)){
											if(index2>index1){
												subs=description.substring(index1, index2+1);
												description = description.replaceAll(subs, "");
											}
										}
										if(((index1=description.indexOf("&"))!=-1)&&((index2=description.indexOf(";"))!=-1)){
											if(index2>index1){
												subs=description.substring(index1, index2+1);
												description = description.replaceAll(subs, "");
											}
										}
										
									}
									//System.out.println("the description is "+description);
									workexpresultsb.append(description);
									workexpresultsb.append("#");
									if(comCalling.equals("")){
										comCalling="35";
									}if(comTypestr.equals("")){
										comTypestr="14";
									}
									//System.out.println("the mainCatalogstr is "+mainCatalogstr + "the comTypestr is "+comTypestr);
									//System.out.println("666666666666the mainCatalogstr is "+mainCatalogstr);
									//System.out.println("666666666666the comCalling is "+comCalling);
									//System.out.println("666666666666the comTypestr is "+comTypestr);
									workexpresultsb.append(comCalling);
									workexpresultsb.append("#");
									workexpresultsb.append(comTypestr);
									workexpresultsb.append("#");
									comCalling="";
									comTypestr="";
								}else if((str.indexOf("resume-preview-dl")!=-1)&&(str.indexOf("|")!=-1)){
									int index;
									String[] strarray = str.replaceAll("\t|\n|\r|", "").replace("|", ",").split(",");
									if(strarray.length>1){
										mainCatalogstr = strarray[0].substring(30);
										if((index=mainCatalogstr.indexOf("/"))!=-1){
											mainCatalogstr = mainCatalogstr.substring(0,index);
										}else{
											mainCatalogstr = mainCatalogstr;
										}
										if((index=mainCatalogstr.indexOf("（"))!=-1){
											mainCatalogstr = mainCatalogstr.substring(0,2);
										}
										if("互联网、电子商务".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "1";
								        }if("计算机业（软件、数据库、系统集成）".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "2";
								        }if("计算机业（硬件、网络设备）".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "3";
								        }if("电子、微电子技术".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "4";
								        }if("通讯、电信网络设备业".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "5";
								        }if("通信、电信运营、增值服务业".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "37";
								        }if("家具、家电、工艺品、玩具".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "6";
								        }if("批发零售(百货、超市、购物中心、专卖店…)".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "7";
								        }if("贸易、商务、进出口".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "8";
								        }if("电气".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "9";
								        }if("电力、能源、矿产业".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "10";
								        }if("石油、化工业".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "11";
								        }if("生物工程、制药、环保".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "12";
								        }if("机械制造、机电设备、重工业".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "13";
								        }if("汽车、摩托车及配件业".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "14";
								        }if("仪器仪表、电工设备".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "15";
								        }if("广告、公关、设计".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "16";
								        }if("艺术、文化传播".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "18";
								        }if("媒体、影视制作、新闻出版".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "38";
								        }if("快速消费品（食品、饮料、粮油、化妆品、烟酒…）".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "19";
								        }if("纺织品业（服饰、服装、鞋类、家纺用品、皮具…）".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "20";
								        }if("咨询业（顾问、会计师、审计师、法律）".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "21";
								        }if("金融业（投资、保险、证券、银行、基金）".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "22";
								        }if("建筑、装潢".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "23";
								        }if("房地产、物业管理".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "24";
								        }if("运输、物流、快递".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "25";
								        }if("旅游业、酒店".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "26";
								        }if("餐饮、娱乐".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "40";
								        }if("办公设备、办公用品及设备、文化体育休闲用品".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "27";
								        }if("印刷、包装、造纸".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "28";
								        }if("生产、制造、修饰加工".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "29";
								        }if("教育、培训、科研院所".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "30";
								        }if("医疗、美容保健、卫生服务".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "31";
								        }if("人才交流、中介".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "32";
								        }if("协会、社团".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "33";
								        }if("政府公用事业、社区服务".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "41";
								        }if("农、林、牧、副、渔业".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "34";
								        }if("法律".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "36";
								        }if("房地产开发".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "39";
								        }if("家居、室内设计、装潢".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "53";
								        }if("银行".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "44";
								        }if("其他其它".indexOf(mainCatalogstr)!=-1){
								        	comCalling = "35";
								        }
										index = strarray[1].indexOf("企业性质：");
										String comTypestr2 = "";
										if(index!=-1){
											comTypestr2 = strarray[1].substring(5);
										}
										if ("国企".indexOf(comTypestr2)!=-1) {
											comTypestr = "1";
								        }
										if ("外企".indexOf(comTypestr2)!=-1) {
											comTypestr = "2";
								        }
										if ("合资企业".indexOf(comTypestr2)!=-1) {
											comTypestr = "3";
								        }
										if ("私营企业".indexOf(comTypestr2)!=-1) {
											comTypestr = "4";
								        }
										if ("民营企业".indexOf(comTypestr2)!=-1) {
											comTypestr = "5";
								        }
										if ("股份制企业".indexOf(comTypestr2)!=-1) {
											comTypestr = "6";
								        }
										if ("集体企业".indexOf(comTypestr2)!=-1) {
											comTypestr = "7";
								        }
										if ("集体事业".indexOf(comTypestr2)!=-1) {
											comTypestr = "8";
								        }
										if ("乡镇企业".indexOf(comTypestr2)!=-1) {
											comTypestr = "9";
								        }
										if ("行政机关".indexOf(comTypestr2)!=-1) {
											comTypestr = "10";
								        }
										if ("社会团体".indexOf(comTypestr2)!=-1) {
											comTypestr = "11";
								        }
										if ("事业单位".indexOf(comTypestr2)!=-1) {
											comTypestr = "12";
								        }
										if ("跨国公司(集团)".indexOf(comTypestr2)!=-1) {
											comTypestr = "13";
								        }
										if ("其它".indexOf(comTypestr2)!=-1) {
											comTypestr = "14";
								        }
									}else if(strarray.length==1){
									}
									
								}
								
							}
							
							workedYear = String.valueOf(workedyearint);
							workedMonth = String.valueOf(workedmonthint);
							//System.out.println("the workedYear is "+workedYear);
							//System.out.println("the workedMonth is "+workedMonth);
						}
					}
					}
				}
			}
		}

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("the workstr is "+workexpresultsb.toString());
		recruiteChannelID = "100608";
		String result = perAccountID+"&"+perName+"&"+mobileNum+"&"+birthday+"&"+marrystr+"&"+location_C+"&"+hometown_C
				+"&"+idno+"&"+address+"&"+zipCode+"&"+email+"&"+photourl+"&"+jobLocation1_C+"&"+salary+"&"+currentWorkState
				+"&"+wantworktype+"&"+jobSeeking1+"&"+jobSeeking2+"&"+jobSeeking3+"&"+wantworkcalling+"&"+selfDescription+"&"+sex+"&"+height
				+"&"+beginDate+"&"+endDate+"&"+schoolName+"&"+speciality+"&"+degreeName
				+"&"+fLEngLevel+"&"+cantoneseLevel+"&"+chineseLevel+"&"+interesting+"&"+recruiteChannelID
				+"&"+workexpresultsb.toString()+"&"+workedYear+"&"+workedMonth+"&"+location_P+"&"+hometown_P+"&"+jobLocation2_C+"&"+jobLocation3_C
				+"&"+jobLocation1_P+"&"+jobLocation2_P+"&"+jobLocation3_P+"&end";
		return result;
	}
	
	//解析前程无忧的简历
	public String getQianchengResumeInfo(){
		HasAttributeFilter filter = new HasAttributeFilter("id","divResume");
		NodeIterator iterator;
		try {
			iterator = this.parser.extractAllNodesThatMatch (filter).elements ();
		while(iterator.hasMoreNodes()){
			Node node = iterator.nextNode();
			if(null!=node){
				if (node instanceof TextNode) {
				} else if (node instanceof TagNode) {
					TableTag table = (TableTag) node;
					Node td1 = table.getRows()[0].getColumns()[0];
					Node n = td1.getChildren().elementAt(7);
					table = (TableTag) n;
					Node td = table.getRows()[1].getColumns()[0];
					NodeList nl = td.getChildren();
					for(Node tdItem:td.getChildren().toNodeArray()){
						if(tdItem instanceof TableTag){
							TableTag table1 = (TableTag)tdItem;
							Node tdd = table1.getRows()[0].getColumns()[0];
							int i=0;
							for(Node tdItem2:tdd.getChildren().toNodeArray()){
								i++;
								if(tdItem2 instanceof TableTag){
									TableTag table2 = (TableTag)tdItem2;
									if(i==2){
										int j=0;
										for(Node t:table2.getRows()){
											j++;
											if(j==1){
												String str = t.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "");
												int endIndex = str.indexOf("&nbsp;");
												if(endIndex!=-1){
													perName = str.substring(5, endIndex);
												}
												//System.out.println("the name is "+perName);
											}else if(j==2){
												Node no = t.getFirstChild().getFirstChild();
												TableTag table3 = (TableTag) no;
												int y = 0;
												int beginIndex,endIndex;
												for(Node tt:table3.getRows()){
													y++;
													if(y==1){
														String[] strarray = tt.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "").split("&nbsp;");
														for(int k=0;k<strarray.length;k++){
															if((beginIndex=strarray[k].indexOf("工作经验"))!=-1){
																endIndex = strarray[k].indexOf("年");
																String workexpyear = strarray[k].substring(0, endIndex);
																//System.out.println("is workexpyear="+workexpyear);
															}else if((strarray[k].indexOf("男")!=-1)||(strarray[k].indexOf("女")!=-1)){
																sex = strarray[k].substring(0, 1);
																//System.out.println("when the j equals 2 then the result is sex="+sex);
															}else if((strarray[k].indexOf("岁")!=-1)&&(strarray[k].indexOf("年")!=-1)){
																beginIndex = strarray[k].indexOf("岁")+2;
																endIndex = strarray[k].indexOf("日");
																birthday = strarray[k].substring(beginIndex, endIndex);
																
																if((beginIndex=birthday.indexOf("月"))!=-1){
																	int q = Integer.valueOf(birthday.substring(5, beginIndex));
																	int p = Integer.valueOf(birthday.substring(beginIndex+1));
																	if(q>9){
																		birthday = birthday.replace("年","-");
																	}else{
																		birthday = birthday.replace("年","-0");
																	}
																	if(p>9){
																		birthday = birthday.replace("月", "-");
																	}else{
																		birthday = birthday.replace("月", "-0");
																	}
																}
																//System.out.println("when the j equals 2 then the result is birthday="+birthday);
																if((beginIndex=strarray[k].indexOf("ID:"))!=-1){
																	endIndex=strarray[k].indexOf(")");
																	if(endIndex!=-1){
																		perAccountID = strarray[k].substring(beginIndex+3,endIndex);
																	}else{
																		perAccountID = strarray[k].substring(beginIndex+3,beginIndex+12);
																	}
																	//System.out.println("when the j equals 2 then the result is perAccountID="+perAccountID);
																}
															}else if((beginIndex=strarray[k].indexOf("ID:"))!=-1){
																endIndex=strarray[k].indexOf(")");
																if(endIndex!=-1){
																	perAccountID = strarray[k].substring(beginIndex+3,endIndex);
																}else{
																	perAccountID = strarray[k].substring(beginIndex+3,beginIndex+12);
																}
																//System.out.println("when the j equals 2 then the result is perAccountID="+perAccountID);
															}else if((beginIndex=strarray[k].indexOf("婚"))!=-1){
																String marrystr = strarray[k].substring(0, strarray[k].length()-1);
																//System.out.println("when the j equals 2 then the result is marrystr="+marrystr);
															}else if((beginIndex=strarray[k].indexOf("cm"))!=-1){
																height = strarray[k].substring(0, beginIndex);
																//System.out.println("when the j equals 2 then the result is height="+height);
															}
														}
													}else{
														String str = tt.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "");
														if((beginIndex=str.indexOf("居住地："))!=-1){
															if((endIndex=str.indexOf("户　口"))!=-1){
																location_C = str.substring(beginIndex+4,endIndex);
															}else{
																location_C = str.substring(beginIndex+4);
															}
															if(location_C.equals("东莞")||location_C.equals("广州")||location_C.equals("深圳")||location_C.equals("佛山")||location_C.equals(" 中山")||location_C.equals("惠州")){
																location_P = "广东";
															}
															//System.out.println("when the y equals 2 then the result is location_C="+location_C);
														}else if((beginIndex=str.indexOf("电　话："))!=-1){
															mobileNum = str.substring(beginIndex+4,beginIndex+15);
															//System.out.println("when the y equals 2 then the result is mobileNum="+mobileNum);
														}else if((beginIndex=str.indexOf("E-mail："))!=-1){
															email = str.substring(beginIndex+7);
															//System.out.println("when the y equals 2 then the result is email="+email);
														}else if((beginIndex=str.indexOf("地　址："))!=-1){
															endIndex = str.indexOf("（邮编：");
															if(endIndex!=-1){
																address = str.substring(beginIndex+4,endIndex);
																beginIndex = endIndex;
																endIndex = str.indexOf("）");
																if(endIndex!=-1){
																	zipCode = str.substring(beginIndex+4,endIndex);
																}
															}else{
																address = str.substring(beginIndex+4);
															}
															//System.out.println("when the y equals 2 then the result is mobileNum="+mobileNum);
														}
													}
												}
												
											}
										}
									}else if(i==4){
										int j=0;
										Node t = table2.getRow(2).getChild(0);
										NodeList nl2 = t.getChildren();
										int beginIndex,endIndex;
										Node tt ;
										for(int k=0;k<nl2.size();k++){
											if(k==0){
												tt=nl2.elementAt(k);
												String ttstr = tt.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "");
												if((beginIndex=ttstr.indexOf("目前年薪："))!=-1){
													String nowsalary = ttstr.substring(beginIndex+5);
													//System.out.println("when the y equals 2 then the result is nowsalary="+nowsalary);
												}
											}else{
												tt=nl2.elementAt(k);
												if(tt instanceof TableTag){
													TableTag ttable = (TableTag) tt;
													if(!tt.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "").equals("")){
														Node ttr1 = ttable.getRows()[0].getColumns()[0];
														String ttrcontent = ttr1.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "");
														if(ttrcontent.equals("自我评价")){
															selfDescription = ttable.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "").substring(4);
															//System.out.println("the selfDescription is "+selfDescription);
														}
														if(ttrcontent.equals("求职意向")){
															int m =0;
															Node ttr = ttable.getRows()[3].getColumns()[0].getChild(0);
															if(ttr instanceof TableTag){
																TableTag tabtag = (TableTag) ttr ;
																String str2;
																for(Node ttr2:tabtag.getRows()){
																	str2=ttr2.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "");
																	if((beginIndex=str2.indexOf("工作性质："))!=-1){
																		wantworktype = str2.substring(5);
																		//System.out.println(" wantworktype="+wantworktype);
																	}else if(str2.indexOf("期望月薪：")!=-1){
																		endIndex = str2.indexOf("/月");
																		if(endIndex!=-1){
																			salary = str2.substring(5,endIndex);
																			if((endIndex=salary.indexOf("-"))!=-1){
																				int salary1 = Integer.valueOf(salary.substring(0,endIndex));
																				int salary2 = Integer.valueOf(salary.substring(endIndex+1));
																				salary = String.valueOf((salary1+salary2)/2);
																			}
																		}
																		//System.out.println("salary="+salary);
																	}else if(str2.indexOf("希望行业")!=-1){
																		wantworkcalling = str2.substring(5);
																		//System.out.println("the wantworkcalling is "+wantworkcalling);
																	}else if(str2.indexOf("目标职能")!=-1){
																		String[] jobseekingarray = str2.substring(5).split("，");
																		if(jobseekingarray.length>=3){
																			jobSeeking1 = jobseekingarray[0];
																			jobSeeking2 = jobseekingarray[1];
																			jobSeeking3 = jobseekingarray[2];
																		}else if(jobseekingarray.length==2){
																			jobSeeking1 = jobseekingarray[0];
																			jobSeeking2 = jobseekingarray[1];
																		}else if(jobseekingarray.length==1){
																			jobSeeking1 = jobseekingarray[0];
																		}
																		//System.out.println("the jobSeeking1 is "+jobSeeking1);
																		//System.out.println("the jobSeeking2 is "+jobSeeking2);
																		//System.out.println("the jobSeeking3 is "+jobSeeking3);
																	}else if(str2.indexOf("目标地点")!=-1){
																		jobLocation1_C = str2.substring(5);
																		int index;
																		String str3 = jobLocation1_C;
																		if((index=str3.indexOf("，"))!=-1){
																			jobLocation1_C = str3.substring(0,index);
																			str3 = str3.substring(index+1);
																			if((index=str3.indexOf("，"))!=-1){
																				jobLocation2_C = str3.substring(0,index);
																				str3 = str3.substring(index+1);
																				if((index=str3.indexOf("，"))!=-1){
																					jobLocation3_C = str3.substring(0,index);
																				}else{
																					jobLocation3_C = str3;
																				}
																			}else{
																				jobLocation2_C = str3;
																			}
																		}
																		if(jobLocation1_C.equals("东莞")||jobLocation1_C.equals("广州")||jobLocation1_C.equals("深圳")||jobLocation1_C.equals("佛山")||jobLocation1_C.equals(" 中山")||jobLocation1_C.equals("惠州")){
																			jobLocation1_P = "广东";
																		}
																		if(jobLocation2_C.equals("东莞")||jobLocation2_C.equals("广州")||jobLocation2_C.equals("深圳")||jobLocation2_C.equals("佛山")||jobLocation2_C.equals(" 中山")||jobLocation2_C.equals("惠州")){
																			jobLocation2_P = "广东";
																		}
																		if(jobLocation3_C.equals("东莞")||jobLocation3_C.equals("广州")||jobLocation3_C.equals("深圳")||jobLocation3_C.equals("佛山")||jobLocation3_C.equals(" 中山")||jobLocation3_C.equals("惠州")){
																			jobLocation3_P = "广东";
																		}
																		//System.out.println("the jobLocation1_C is "+jobLocation1_C);
																	}
																}
															}
														}else{
															int m =0;
															String str = null ;
															String content ;
															int workedyearint = 0,workedmonthint=0;
															for(Node tr:ttable.getRows()){
																if(m%5==0){
																	str=tr.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "");
																}
																if(str.equals("工作经验")){
																	content=tr.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "");
																	if(content.equals("")||null==content){
																	}else{
																		Node t3 = tr.getFirstChild().getFirstChild();
																		int w = 0;
																		if(t3 instanceof TableTag){
																			TableTag ttable2 = (TableTag) t3;
																			for(Node trr:ttable2.getRows()){
																				String str3 = trr.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "");
																				if(w%5==0){
																					if((beginIndex=str3.indexOf("--"))!=-1){
																						workexpbeginDate = str3.substring(0,beginIndex);
																						if((beginIndex=workexpbeginDate.indexOf("/"))!=-1){
																							int p = Integer.valueOf(workexpbeginDate.substring(beginIndex+1));
																							if(p>9){
																								workexpbeginDate =workexpbeginDate.replace("/", "-");
																							}else{
																								workexpbeginDate =workexpbeginDate.replace("/", "-0");
																							}
																						}
																					}else{
																						workexpbeginDate = str3.substring(0,6).replace("/", "-");
																					}
																					workexpresultsb.append(workexpbeginDate);
																					workexpresultsb.append("#");
																					if(str3.indexOf("至今")==-1){
																						if((beginIndex=str3.indexOf("--"))!=-1){
																							endIndex = str3.indexOf("：");
																							workexpendDate = str3.substring(beginIndex+2, endIndex);
																							if((beginIndex=workexpendDate.indexOf("/"))!=-1){
																								int p = Integer.valueOf(workexpendDate.substring(beginIndex+1));
																								if(p>9){
																									workexpendDate =workexpendDate.replace("/", "-");
																								}else{
																									workexpendDate =workexpendDate.replace("/", "-0");
																								}
																							}
																						}else{
																							workexpendDate = "";
																						}
																					}else{
																						workexpendDate = "";
																					}
																					workexpresultsb.append(workexpendDate);
																					workexpresultsb.append("#");
																					beginIndex = str3.indexOf("：");
																					endIndex = str3.indexOf("(");
																					if(beginIndex!=-1&&endIndex!=-1){
																						comName = str3.substring(beginIndex+1,endIndex);
																					}else if(beginIndex!=-1&&endIndex==-1){
																						endIndex = str3.indexOf("[");
																						if(endIndex!=-1){
																							comName = str3.substring(beginIndex+1,endIndex);
																						}else{
																							comName = str3.substring(beginIndex+1);
																						}
																					}
																					workexpresultsb.append(comName);
																					workexpresultsb.append("#");
																					beginIndex = str3.indexOf("[");
																					endIndex = str3.indexOf("]");
																					if(beginIndex!=-1&&endIndex!=-1){
																						String str4 = str3.substring(beginIndex+1,endIndex);
																						beginIndex = str4.indexOf("年");
																						if(beginIndex!=-1){
																							workedYear = str4.substring(0,beginIndex);
																							endIndex = str4.indexOf("个月");
																							if(endIndex!=-1){
																								workedMonth = str4.substring(beginIndex+1,endIndex);
																							}
																						}else{
																							endIndex = str4.indexOf("个月");
																							if(endIndex!=-1){
																								workedMonth = str4.substring(0,endIndex);
																							}
																						}
																						
																					}
																					if(!workedYear.equals("")){
																						workedyearint = workedyearint + Integer.valueOf(workedYear);
																						workedYear="";
																					}
																					if(!workedMonth.equals("")){
																						workedmonthint = workedmonthint + Integer.valueOf(workedMonth);
																						workedyearint = workedyearint + workedmonthint/12 ;
																						workedmonthint = workedmonthint%12;
																						workedMonth="";
																					}
																					//System.out.println("the workexpbeginDate is "+workexpbeginDate);
																					//System.out.println("the workexpendDate is "+workexpendDate);
																					//System.out.println("the comName is "+comName);
																				}
																				if(w%5==1){
																					if(str3.indexOf("所属行业：")!=-1){
																						if((beginIndex=str3.indexOf("/"))!=-1){
																							mainCatalogstr = str3.substring(5,beginIndex);
																						}else{
																							mainCatalogstr = str3.substring(5);
																						}
																						if((beginIndex=mainCatalogstr.indexOf("("))!=-1){
																							mainCatalogstr = mainCatalogstr.substring(0,beginIndex);
																						}
																				        if("互联网、电子商务".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "1";
																				        }if("计算机业（软件、数据库、系统集成、计算机软件）".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "2";
																				        }if("计算机业（硬件、网络设备）".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "3";
																				        }if("电子、微电子技术".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "4";
																				        }if("通讯、电信网络设备业".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "5";
																				        }if("通信、电信运营、增值服务业".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "37";
																				        }if("家具、家电、工艺品、玩具".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "6";
																				        }if("批发零售(百货、超市、购物中心、专卖店…)".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "7";
																				        }if("贸易、商务、进出口".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "8";
																				        }if("电气".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "9";
																				        }if("电力、能源、矿产业".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "10";
																				        }if("石油、化工业".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "11";
																				        }if("生物工程、制药、环保".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "12";
																				        }if("机械制造、机电设备、重工业".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "13";
																				        }if("汽车、摩托车及配件业".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "14";
																				        }if("仪器仪表、电工设备".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "15";
																				        }if("广告、公关、设计".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "16";
																				        }if("艺术、文化传播".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "18";
																				        }if("媒体、影视制作、新闻出版".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "38";
																				        }if("快速消费品（食品、饮料、粮油、化妆品、烟酒…）".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "19";
																				        }if("纺织品业（服饰、服装、鞋类、家纺用品、皮具…）".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "20";
																				        }if("咨询业（顾问、会计师、审计师、法律）".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "21";
																				        }if("金融业（投资、保险、证券、银行、基金）".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "22";
																				        }if("建筑、装潢".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "23";
																				        }if("房地产、物业管理".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "24";
																				        }if("运输、物流、快递".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "25";
																				        }if("旅游业、酒店".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "26";
																				        }if("餐饮、娱乐".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "40";
																				        }if("办公设备、办公用品及设备、文化体育休闲用品".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "27";
																				        }if("印刷、包装、造纸".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "28";
																				        }if("生产、制造、修饰加工".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "29";
																				        }if("教育、培训、科研院所".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "30";
																				        }if("医疗、美容保健、卫生服务".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "31";
																				        }if("人才交流、中介".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "32";
																				        }if("协会、社团".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "33";
																				        }if("政府公用事业、社区服务".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "41";
																				        }if("农、林、牧、副、渔业".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "34";
																				        }if("法律".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "36";
																				        }if("房地产开发".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "39";
																				        }if("家居、室内设计、装潢".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "53";
																				        }if("银行".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "44";
																				        }if("其他".indexOf(mainCatalogstr)!=-1){
																				        	comCalling = "35";
																				        }

																						//System.out.println("666666666666the mainCatalogstr is "+mainCatalogstr);
																						//System.out.println("666666666666the comCalling is "+comCalling);
																					}
																					
																				}
																				if(w%5==2){
																					otherposition = trr.getLastChild().toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "");
																					//System.out.println("the otherposition is "+otherposition);
																					workexpresultsb.append(otherposition);
																					workexpresultsb.append("#");
																					workexpresultsb.append(worksalary);
																					workexpresultsb.append("#");
																				}
																				if(w%5==3){
																					description = str3;
																					//System.out.println("the description is "+description);
																					workexpresultsb.append(description);
																					workexpresultsb.append("#");
																					if(comCalling.equals("")){
																						comCalling="35";
																					}if(comTypestr.equals("")){
																						comTypestr="14";
																					}
																					workexpresultsb.append(comCalling);
																					workexpresultsb.append("#");
																					workexpresultsb.append(comTypestr);
																					workexpresultsb.append("#");
																					comCalling="";
																					comTypestr="";
																				}
																				w++;
																			}
																		}
																		
																	}
																}
																
																if(str.equals("教育经历")){
																	content=tr.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "");
																	if(content.equals("")||null==content){
																	}else{
																		if(!content.equals("教育经历")){
																		Node t3 = tr.getFirstChild().getFirstChild();
																		int w = 0;
																		if(t3 instanceof TableTag){
																			TableTag ttable2 = (TableTag) t3;
																				TableRow tbr = (TableRow) ttable2.getRows()[0] ;
																				String str3;
																				for(Node trr2:tbr.getColumns()){
																					str3 = trr2.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "");
																					if(w==0){
																						if((beginIndex=str3.indexOf("--"))!=-1){
																							beginDate = str3.substring(0,beginIndex);
																							if((beginIndex=beginDate.indexOf("/"))!=-1){
																								int p = Integer.valueOf(beginDate.substring(beginIndex+1));
																								if(p>9){
																									beginDate =beginDate.replace("/", "-");
																								}else{
																									beginDate =beginDate.replace("/", "-0");
																								}
																							}
	//																						int num = Integer.valueOf(beginDate.substring(beginDate.length()-1));
																						}else{
																							beginDate = str3.substring(0,6).replace("/", "-");
																						}
																						
																						if(str3.indexOf("至今")==-1){
																							if((beginIndex=str3.indexOf("--"))!=-1){
																								endDate = str3.substring(beginIndex+2);
																								if((beginIndex=endDate.indexOf("/"))!=-1){
																									int p = Integer.valueOf(endDate.substring(beginIndex+1));
																									if(p>9){
																										endDate =endDate.replace("/", "-");
																									}else{
																										endDate =endDate.replace("/", "-0");
																									}
																								}
																							}else{
																								endDate = "";
																							}
																						}
																					}
																					if(w==1){
																						schoolName = str3;
																					}
																					if(w==2){
																						speciality = str3;
																					}if(w==3){
																						degreeName = str3;
																					}
																					
																				w++;
																			}
																			//System.out.println("the beginDate length is "+beginDate);
																			//System.out.println("the endDate length is "+endDate);
																			//System.out.println("the schoolName length is "+schoolName);
																			//System.out.println("the speciality length is "+speciality);
																			//System.out.println("the degreeName length is "+degreeName);
																			}
																		}
																	}
																}
																if(str.equals("语言能力")){
																	content=tr.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "");
																	if(content.equals("")||null==content){
																	}else{
																		if(!content.equals("语言能力")){
																			Node t3 = tr.getFirstChild().getFirstChild();
																			int w = 0;
																			if(t3 instanceof TableTag){
																				String str3 = t3.toPlainTextString().replaceAll("\t|\n|\r|", "").replace(" ", "");
																				if((beginIndex=str3.indexOf("普通话（"))!=-1){
																					chineseLevel = str3.substring(beginIndex+4, beginIndex+6);
																				}
																				if((beginIndex=str3.indexOf("英语等级"))!=-1){
																					if(str3.length()>=beginIndex+9){
																						fLEngLevel = str3.substring(beginIndex+5, beginIndex+9);
																					}else {
																						
																					}
																					
																				}else if((beginIndex=str3.indexOf("英语（"))!=-1){
																					fLEngLevel = str3.substring(beginIndex+3, beginIndex+5);
																				}
																				if((beginIndex=str3.indexOf("粤语（"))!=-1){
																					cantoneseLevel = str3.substring(beginIndex+3, beginIndex+5);
																				}
																			}
																		}
																		//System.out.println("the chineseLevel length is "+chineseLevel);
																		//System.out.println("the fLEngLevel length is "+fLEngLevel);
																		//System.out.println("the cantoneseLevel length is "+cantoneseLevel);
																	}
																}
																m++;
															}
															workedYear = String.valueOf(workedyearint);
															workedMonth = String.valueOf(workedmonthint);
															//System.out.println("the workedYear is "+workedYear);
															//System.out.println("the workedMonth is "+workedMonth);
															
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
						
			}
		}

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		recruiteChannelID = "100607";
		String result = perAccountID+"&"+perName+"&"+mobileNum+"&"+birthday+"&"+marrystr+"&"+location_C+"&"+hometown_C
				+"&"+idno+"&"+address+"&"+zipCode+"&"+email+"&"+photourl+"&"+jobLocation1_C+"&"+salary+"&"+currentWorkState
				+"&"+wantworktype+"&"+jobSeeking1+"&"+jobSeeking2+"&"+jobSeeking3+"&"+wantworkcalling+"&"+selfDescription+"&"+sex+"&"+height
				+"&"+beginDate+"&"+endDate+"&"+schoolName+"&"+speciality+"&"+degreeName
				+"&"+fLEngLevel+"&"+cantoneseLevel+"&"+chineseLevel+"&"+interesting+"&"+recruiteChannelID
				+"&"+workexpresultsb.toString()+"&"+workedYear+"&"+workedMonth+"&"+location_P+"&"+hometown_P+"&"+jobLocation2_C+"&"+jobLocation3_C
				+"&"+jobLocation1_P+"&"+jobLocation2_P+"&"+jobLocation3_P+"&end";
		return result;
	}
	
}





















