<%@page contentType="text/html;charset=gbk"%>
<%@page import="subsystem.recruit.parserresume.ParserHtmlContent,org.htmlparser.Parser;" %>
<%@page import="java.sql.*,com.boss.database.DataBase;" %>
<%@page language="java"%>
<html>
<head>
<link rel="shortcut icon" href="/images/boss.ico"/>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=gbk">
<%@include file="/include/inc/InfoTop.inc"%>
<script language="javascript" src="/js/Ajax.js" type="text/javascript"></script>
<title>��������</title>
</head>
<%
	String content = request.getParameter("content");
	Parser parser = new Parser();
	parser.setInputHTML(content);
	ParserHtmlContent phc = new ParserHtmlContent();
	phc.setParser(parser);
	String title = phc.getTitle();
	parser.setInputHTML(content);
	//phc.setParser(parser);
	String flag = title.substring(0, 2);
	String result = "";
	if(flag.equals("����")){
		result = phc.getZhilianResumeInfo();
	}else{
		result = phc.getQianchengResumeInfo();
	}
	String[] resultarray = result.split("&"); 
	//System.out.println("the result is "+result);
	String perAccountID="",perName="",mobileNum="",birthday="",hometown_C="",marrystr="",location_C="",iDCardNum="",address="",jobLocation2_C="",jobLocation3_C="",jobLocation3_P=""
			,zipCode="",email="",photourl="",jobLocation1_C="",salary="",currentWorkState="",jobSeeking="",workedYear="",workedMonth="",jobLocation1_P="",jobLocation2_P=""
			,wantworktype="",jobSeeking1="",jobSeeking2="",jobSeeking3="",wantworkcalling="",selfDescription="",sex="",height="",location_P="",hometown_P="" ;
	String eduBeginDate="",eduEndDate="",schoolName="",speciality="",degreeName="",fLEngLevel="",cantoneseLevel="",chineseLevel="",interesting="";
	String workexpresultsb="",workexpbeginDate="",workexpendDate="",comName="",otherposition="",worksalary="",description="",recruiteChannelID="";
	String[] workexparray ;
	String jobLocation_C="";
	if(resultarray.length>=43){
		perAccountID=resultarray[0];
		perName=resultarray[1];
		mobileNum=resultarray[2];
		birthday=resultarray[3];
		marrystr=resultarray[4];
		location_C=resultarray[5];
		hometown_C=resultarray[6];
		iDCardNum=resultarray[7];
		address=resultarray[8];
		zipCode=resultarray[9];
		email=resultarray[10];
		//photourl=resultarray[11];
		jobLocation1_C=resultarray[12];
		salary=resultarray[13];
		currentWorkState=resultarray[14];
		wantworktype=resultarray[15];
		jobSeeking1=resultarray[16];
		jobSeeking2=resultarray[17];
		jobSeeking3=resultarray[18];
		wantworkcalling=resultarray[19];
		selfDescription=resultarray[20];
		sex=resultarray[21];
		height=resultarray[22];
		eduBeginDate=resultarray[23];
		eduEndDate=resultarray[24];
		schoolName=resultarray[25];
		speciality=resultarray[26];
		degreeName=resultarray[27];
		fLEngLevel=resultarray[28];
		cantoneseLevel=resultarray[29];
		chineseLevel=resultarray[30];
		interesting=resultarray[31];
		recruiteChannelID=resultarray[32];
		workexpresultsb=resultarray[33];
		workedYear=resultarray[34];
		workedMonth=resultarray[35];
		location_P=resultarray[36];
		hometown_P=resultarray[37];
		jobLocation2_C=resultarray[38];
		jobLocation3_C=resultarray[39];
		jobLocation1_P=resultarray[40];
		jobLocation2_P=resultarray[41];
		jobLocation3_P=resultarray[42];
		
	}
	if(!jobSeeking1.equals("")&&!jobSeeking2.equals("")&&!jobSeeking3.equals("")){
		jobSeeking=jobSeeking1+","+jobSeeking2+","+jobSeeking3;
	}else if(!jobSeeking1.equals("")&&!jobSeeking2.equals("")){
		jobSeeking=jobSeeking1+","+jobSeeking2;
	}else if(!jobSeeking1.equals("")){
		jobSeeking=jobSeeking1;
	}else{
		jobSeeking="";
	}
	if(!jobLocation1_C.equals("")&&!jobLocation2_C.equals("")&&!jobLocation3_C.equals("")){
		jobLocation_C=jobLocation1_C+","+jobLocation2_C+","+jobLocation3_C;
	}else if(!jobLocation1_C.equals("")&&!jobLocation2_C.equals("")){
		jobLocation_C=jobLocation1_C+","+jobLocation2_C;
	}else if(!jobLocation1_C.equals("")){
		jobLocation_C=jobLocation1_C;
	}else{
		jobLocation_C="";
	}
	workexparray = workexpresultsb.split("[#]");
	int k = workexparray.length/8;
%>
<body>
<center>
<form name="thisForm" action="ParserResumeAction.do" method="post">
	<input type="hidden"  name="submitButtonFlag"/>
	<input type="hidden" name="workexpresultsb" value="<%=workexpresultsb %>"/>
	<input type="hidden" name="recruiteChannelID" value="<%=recruiteChannelID %>"/>
	<input type="hidden" name="workedYear" value="<%=workedYear %>"/>
	<input type="hidden" name="workedMonth" value="<%=workedMonth %>"/>
	<input type="hidden" name="location_P" value="<%=location_P %>"/>
	<input type="hidden" name="hometown_P" value="<%=hometown_P %>"/>
	<input type="hidden" name="jobLocation1_C" value="<%=jobLocation1_C %>"/>
	<input type="hidden" name="jobLocation2_C" value="<%=jobLocation2_C %>"/>
	<input type="hidden" name="jobLocation3_C" value="<%=jobLocation3_C %>"/>
	<input type="hidden" name="jobLocation1_P" value="<%=jobLocation1_P %>"/>
	<input type="hidden" name="jobLocation2_P" value="<%=jobLocation2_P %>"/>
	<input type="hidden" name="jobLocation3_P" value="<%=jobLocation3_P %>"/>
	
		<table border='0' cellpadding='4'  align="center" cellspacing='1' id="table1" class="table" >
			<tr class="tr">
				<td colspan="6"    class="tdtextred">
					<a id="personinfoclick" onClick="showPersonInfo();" style="cursor:pointer;"><font color="blue">&nbsp;�� ���ء�������Ϣ��</font></a>
					<input type="hidden" name="personopen" value="0">  
				</td>
			</tr>
			<tr class="tr" id="personinfo" name="personinfo">
				<td colspan="6" width="100%">	
	        		<table width="100%">
	        			<tr class="tr">
							<td colspan="6" align="center" style="font-weight:bold; solid #86CAF2;height:25px;line-height:25px;background:#EDF9FE">
								������Ϣ
							</td>
						</tr>
				      	 <tr class="tr">
				      	 <%
				      	 if(flag.equals("����")){
				      	%>
				            <td nowrap class='tdright'>������ţ�</td>
				      	<% 
				        }else{
				      	%>
				        	 <td nowrap class='tdright'>ǰ�����Ǳ�ţ�</td>
				        <% 
				         }
				         %>
				            <td class='td'>
								<input type="text" name="perAccountID" value="<%=perAccountID %>"/>
							</td>
				            <td nowrap class='tdrighttextred'>������</td>
				            <td class='td'>
								<input type="text" name="perName" value="<%=perName %>"/>
							</td>
				            <td nowrap class='tdcenter' rowspan="6" colspan="2">
				              		<img   height="202" src="<%=photourl %>" />
							</td>
				        </tr>
				         <tr class="tr">
				         	<td nowrap class='tdright'>�ֻ����룺</td>
				            <td class='td'>
				            	<input type="text" name="mobileNum" value="<%=mobileNum %>"/>
							</td>
				            <td nowrap class='tdrighttextred'>�Ա�</td>
				            <td class='td'>
				            <select>
				            <%
				            if(sex.equals("��")){
				            %>
				            	<option value="100400" selected>��</option>
				            	<option value="100401">Ů</option>
				            	<input type="hidden" name="sex" value="100400"/>
				            <%
				            }else{
				            %>
				            	<option value="100400">��</option>
				            	<option value="100401" selected>Ů</option>
				            	<input type="hidden" name="sex" value="100401"/>
				            <%
				            }
				            %>     
				            </select>  
							</td>  
				        </tr>
				         <tr class="tr">
				         	 <td nowrap class='tdright'>���䣺</td>
				            <td class='td'>
				            	<input type="text" name="email" value="<%=email %>"/>
							</td>
				            <td nowrap class='tdright'>�������ڣ�</td>
				            <td class='td'>
								<input type="text" name="birthday" value="<%=birthday %>"/>
							</td>
							
				        </tr>
				         <tr class="tr">
				            <td nowrap class='tdright'>���</td>
				            <td class='td'>
				             <select>
				            <%
				            if(marrystr.equals("δ��")){
				            %>
				            	<option value="100500" selected>δ��</option>
				            	<option value="100501">�ѻ�</option>
				            	<option value="100502">����</option>
				            	<option value="100503">����</option>
				            	<input type="hidden" name="maritalStatus" value="100500"/>
				            <%
				            }else if(marrystr.equals("�ѻ�")){
				            %>
				            	<option value="100500">δ��</option>
				            	<option value="100501" selected>�ѻ�</option>
				            	<option value="100502">����</option>
				            	<option value="100503">����</option>
				            	<input type="hidden" name="maritalStatus" value="100501"/>
				            <%
				            }else if(marrystr.equals("����")){
				            %> 
				            	<option value="100500">δ��</option>
				            	<option value="100501">�ѻ�</option>
				            	<option value="100502" selected>����</option>
				            	<option value="100503">����</option>
				            	<input type="hidden" name="maritalStatus" value="100502"/>
				            <%
				            }else if(marrystr.equals("����")){
				            %>  
				            <option value="100500">δ��</option>
				            	<option value="100501">�ѻ�</option>
				            	<option value="100502">����</option>
				            	<option value="100503" selected>����</option>
				            	<input type="hidden" name="maritalStatus" value="100503"/>
				            <%
				            }else{
				            %>  
				            <option value="100500">δ��</option>
				            	<option value="100501">�ѻ�</option>
				            	<option value="100502">����</option>
				            	<option value="100503" selected>����</option>
				            	<input type="hidden" name="maritalStatus" value="100503"/>
				            <%
				            }
				            %>    
				            </select>  
							</td>
							<td nowrap class='tdright'>��ߣ�</td>
				            <td class='td'>
								<input type="text" name="height" value="<%=height %>"/>
								
							</td>
				        </tr>
				         <tr class="tr">
				            <td nowrap class='tdright'>���֤�ţ�</td>
				            <td class='td'>
									<input type="text" name="IDCardNum" value="<%=iDCardNum %>"/>
							</td>
							<td nowrap class='tdright'>���ڣ�</td>
				            <td class='td'>
								
								<input type="text" name="hometown_C" value="<%=hometown_C %>"/>
							</td>
				        </tr>
				       
				        <tr class="tr">
				            <td nowrap class='tdright'>��ַ��</td>
				            <td class='td'>
				            		<input type="text" name="address" value="<%=address %>"/>
							</td>
							<td nowrap class='tdright'>�������룺</td>
				            <td class='td'>
				            	<input type="text" name="zipCode" value="<%=zipCode %>"/>
							</td>
				        </tr>
				        
				        <tr class="tr">
				            <td nowrap class='tdrighttextred'>ӦƸ��λ��</td>
				            <td class='td'>
				            	<select id="postID" name="postID" style="width:158px">
								</select>
							</td>
				        </tr>
				         
				        <tr class="tr">
				            <td nowrap class='tdright'>�������ۣ�</td>
				            <td class='td' colspan="5">
								<textarea  rows="5" cols="95" name="selfDescription" style="width:803px"><%=selfDescription %></textarea>
							</td>
				        </tr>
	        		</table>
	        	</td>
				
			</tr>
			
	         <tr class="tr">
				<td colspan="6" class="tdtextred">
					<a id="seekinfoclick" onClick="showSeekInfo();" style="cursor:pointer;"><font color="blue">&nbsp;�� ���ء���ְ����</font></a>
					<input type="hidden" name="seekopen" value="0">  
				</td>
			</tr>
			 <tr class="tr" id="seekinfo" name="seekinfo">
	        	<td colspan="6" width="100%">	
	        		<table width="100%">
	        			  <tr class="tr">
					            <td nowrap class='tdright'>��������������</td>
					            <td class='td' colspan="5">
									<input type="text" name="jobLocation_C" value="<%=jobLocation_C %>"/>
								</td>
					        </tr>
					        
					        <tr class="tr">
					            <td nowrap class='tdright'>н��Ҫ��</td>
					            <td class='td'>
					            	<input type="text" size="10px" value="<%=salary %>"/>Ԫ/��&nbsp;&nbsp;&nbsp;&nbsp;
									<%
									if(salary.equals("����")){
										salary = "";
									}
									%>
									<input type="hidden" name="salary" value="<%=salary %>"/>
								</td>
								<td nowrap class='tdright'>Ѱ��ְλ��</td>
					            <td class='td'>
					            	<input type="text" name="jobSeeking" value="<%=jobSeeking %>"/>
					            	<input type="hidden" name="jobSeeking1" value="<%=jobSeeking1 %>"/>
					            	<input type="hidden" name="jobSeeking2" value="<%=jobSeeking2 %>"/>
					            	<input type="hidden" name="jobSeeking3" value="<%=jobSeeking3 %>"/>
								</td>
								<td nowrap class='tdright'>�������ڵأ�</td>
					            <td class='td'>
									<input type="text" name="location_C" value="<%=location_C %>"/>
								</td>
					        </tr>
	        		</table>
	        	</td>
	        </tr>
	        
	        <tr class="tr">
				<td colspan="6" class="tdtextred">
					<a id="educationinfoclick" onClick="showEducationInfo();" style="cursor:pointer;"><font color="blue">&nbsp;�� ���ء�����������</font></a>
					<input type="hidden" name="educationopen" value="0">  
				</td>
			</tr>
			 <tr class="tr" id="educationinfo" name="educationinfo">
	        	<td colspan="6" width="100%">	
	        		<table width="100%">
	        			  <tr class="tr">
					            <td nowrap class='tdright'>��ʼʱ�䣺</td>
					            <td class='td'>
									<input type="text" name="eduBeginDate" value="<%=eduBeginDate %>"/>
								</td>
					            <td nowrap class='tdright'>����ʱ�䣺</td>
					            <td class='td'>
					            	<input type="text" name="eduEndDate" value="<%=eduEndDate %>"/>
							</tr>
					        <tr class="tr">	
								</td>
								<td nowrap class='tdright'>ѧУ���ƣ�</td>
					            <td class='td'>
					            	<input type="text" name="schoolName" value="<%=schoolName %>"/>
								</td>
								<td nowrap class='tdright'>��ѧרҵ��</td>
					            <td class='td'>
									<input type="text" name="speciality" value="<%=speciality %>"/>
								</td>
								<td nowrap class='tdright'>ѧ����</td>
					            <td class='td'>
									<input type="text" name="degreeName" value="<%=degreeName %>"/>
								</td>
					        </tr>
	        		</table>
	        	</td>
	        </tr>
	        
	        <tr class="tr">
				<td colspan="6" class="tdtextred">
					<a id="languageinfoclick" onClick="showLanguageInfo();" style="cursor:pointer;"><font color="blue">&nbsp;�� ���ء�����������</font></a>
					<input type="hidden" name="languageopen" value="0">  
				</td>
			</tr>
			 <tr class="tr" id="languageinfo" name="languageinfo">
	        	<td colspan="6" width="100%">	
	        		<table width="100%">
	        			  <tr class="tr">
					            <td nowrap class='tdright'>Ӣ�</td>
					            <td class='td'>
									<input type="text" value="<%=fLEngLevel %>"/>
								</td>
					            <td nowrap class='tdright'>���</td>
					            <td class='td'>
					            	<input type="text" value="<%=cantoneseLevel %>"/>
								<td nowrap class='tdright'>��ͨ����</td>
					            <td class='td'>
					            	<input type="text" value="<%=chineseLevel %>"/>
					            	<%
					            	if(chineseLevel.equals("����")||chineseLevel.equals("��ͨ")||chineseLevel.equals("����")){
					            		chineseLevel = "1";
					            	}else if(chineseLevel.equals("һ��")){
					            		chineseLevel = "2";
					            	}else{
					            		chineseLevel = "0";
					            	}
					            	if(cantoneseLevel.equals("����")||chineseLevel.equals("��ͨ")||cantoneseLevel.equals("����")){
					            		cantoneseLevel = "1";
					            	}else if(cantoneseLevel.equals("һ��")){
					            		cantoneseLevel = "2";
					            	}else{
					            		cantoneseLevel = "0";
					            	}
					            	%>
								</td>
								<input type="hidden" name="fLEngLevel" value="<%=fLEngLevel %>"/>
								<input type="hidden" name="chineseLevel" value="<%=chineseLevel %>"/>
								
								<input type="hidden" name="cantoneseLevel" value="<%=cantoneseLevel %>"/>
					        </tr>
					         <tr class="tr">
					            <td nowrap class='tdright'>��Ȥ���ã�</td>
					            <td class='td' colspan="3">
									<input type="text" name="interesting" size="40px" value="<%=interesting %>"/>
								</td>
							</tr>
	        		</table>
	        	</td>
	        </tr>
	        <%
	        if(k>=1){
	        %>
	         <tr class="tr">
				<td colspan="6" class="tdtextred">
					<a id="workinfoclick" onClick="showWorkInfo();" style="cursor:pointer;"><font color="blue">&nbsp;�� ���ء�����������</font></a>
					<input type="hidden" name="workopen" value="0">  
				</td>
			</tr>
			<tr class="tr" id="workinfo" name="workinfo">
	        	<td colspan="6" width="100%">
			<%
				for(int i=0;i<k;i++){
					workexpbeginDate=workexparray[i*8+0];
					workexpendDate=workexparray[i*8+1];
					comName=workexparray[i*8+2];
					otherposition=workexparray[i*8+3];
					worksalary=workexparray[i*8+4];
					description=workexparray[i*8+5];
			%>
			 	
	        		<table width="100%">
	        			  <tr class="tr">
					            <td nowrap class='tdright'>��ʼʱ�䣺</td>
					            <td class='td'>
									<input type="text" name="workexpbeginDate" value="<%=workexpbeginDate.equals("")?"����":workexpbeginDate %>"/>
								</td>
					            <td nowrap class='tdright'>����ʱ�䣺</td>
					            <td class='td'>
					            	<input type="text" name="workexpendDate" value="<%=workexpendDate.equals("")?"����":workexpendDate %>"/>
				       </tr>
				         <tr class="tr">
								<td nowrap class='tdright'>��˾���ƣ�</td>
					            <td class='td'>
					            	<input type="text" name="comName" value="<%=comName %>"/>
								</td>
								<td nowrap class='tdright'>����ְλ��</td>
					            <td class='td'>
					            	<input type="text" name="otherposition" value="<%=otherposition %>"/>
								</td>
								<td nowrap class='tdright'>нˮ��</td>
					            <td class='td'>
					            	<input type="text" name="worksalary" value="<%=worksalary.equals("0")?"":worksalary %>"/>
								</td>
					        </tr>
					          <tr class="tr">
				            <td nowrap class='tdright'>����������</td>
				            <td class='td' colspan="5">
								<textarea  rows="5" cols="95" name="description" style="width:803px"><%=description %></textarea>
							</td>
				        </tr>
	        		</table>
	        	
	        
	       <%
				}
			 }
		   %>
	       </td>
	        </tr>
			<tr class="trcenter" >
				<td height="25" colspan='6' nowrap></td>
			</tr>
			<tr class="trcenter" >
				<td height="25" colspan='6' nowrap>
				<center>
					<input name="savebutton" id="savebutton" type="button" class="tbbutton" value="��������" onclick="SubmitButton(this,'save');">
					<input name="closebutton" id="closebutton" type="button" class="tbbutton" value="�ء�����"  onclick="WinClose();">
				</center>
				</td>
			</tr>
		</table>
</form>
</center>
</body>
<script type="text/javascript">

initPage();

function SubmitButton(obj,SubmitFlag)
{
	DisableObj(obj);
	window.setTimeout(EnableObj,3000,obj);
	submitButtonFlagBackup=document.thisForm.submitButtonFlag.value;

	switch (SubmitFlag)
	{
		case "save":
			if(document.thisForm.postID.value==""){
				alert("��ѡ��ӦƸ��λ!");
				document.thisForm.postID.focus();
				return false;
			}
			document.thisForm.submitButtonFlag.value= "add";
			localSubmitWin("/ParserResumeAction.do");
			document.thisForm.submitButtonFlag.value=submitButtonFlagBackup;
			break
		case "lock":
			document.thisForm.submitButtonFlag.value= "lock";
			localSubmitWin("/ParserResumeAction.do");
			document.thisForm.submitButtonFlag.value=submitButtonFlagBackup;
			return false;
		default:
			window.alert("û��SubmitFlag������");
	}
}

function initPage(){
	GetChildrenValue(document.thisForm.postID,'all',"GetDicRecruitePost");
}

function showPersonInfo(){

	var open = document.thisForm.personopen.value;
	if( open == 0 ){
		document.getElementById("personinfo").style.display = "none";
		document.getElementById("personinfoclick").innerHTML = "&nbsp;�� ��ʾ��������Ϣ��";
		document.thisForm.personopen.value = 1;
		
	}else{
		document.all["personinfo"].style.display = "";
		document.getElementById("personinfoclick").innerHTML = "&nbsp;�� ���ء�������Ϣ��";
		document.thisForm.personopen.value = 0;	
	}
	document.getElementById("personinfoclick").style.color="blue";
}

function showEducationInfo(){

	var open = document.thisForm.educationopen.value;
	if( open == 0 ){
		document.getElementById("educationinfo").style.display = "none";
		document.getElementById("educationinfoclick").innerHTML = "&nbsp;�� ��ʾ������������";
		document.thisForm.educationopen.value = 1;
		
	}else{
		document.all["educationinfo"].style.display = "";
		document.getElementById("educationinfoclick").innerHTML = "&nbsp;�� ���ء�����������";
		document.thisForm.educationopen.value = 0;	
	}
	document.getElementById("educationinfoclick").style.color="blue";
}

function showLanguageInfo(){

	var open = document.thisForm.languageopen.value;
	if( open == 0 ){
		document.getElementById("languageinfo").style.display = "none";
		document.getElementById("languageinfoclick").innerHTML = "&nbsp;�� ��ʾ������������";
		document.thisForm.languageopen.value = 1;
		
	}else{
		document.all["languageinfo"].style.display = "";
		document.getElementById("languageinfoclick").innerHTML = "&nbsp;�� ���ء�����������";
		document.thisForm.languageopen.value = 0;	
	}
	document.getElementById("languageinfoclick").style.color="blue";
}

function showWorkInfo(){

	var open = document.thisForm.workopen.value;
	if( open == 0 ){
		document.getElementById("workinfo").style.display = "none";
		document.getElementById("workinfoclick").innerHTML = "&nbsp;�� ��ʾ������������";
		document.thisForm.workopen.value = 1;
		
	}else{
		document.all["workinfo"].style.display = "";
		document.getElementById("workinfoclick").innerHTML = "&nbsp;�� ���ء�����������";
		document.thisForm.workopen.value = 0;	
	}
	document.getElementById("workinfoclick").style.color="blue";
}

function showSeekInfo(){

	var open = document.thisForm.seekopen.value;
	if( open == 0 ){
		document.getElementById("seekinfo").style.display = "none";
		document.getElementById("seekinfoclick").innerHTML = "&nbsp;�� ��ʾ����ְ����";
		document.thisForm.seekopen.value = 1;
		
	}else{
		document.all["seekinfo"].style.display = "";
		document.getElementById("seekinfoclick").innerHTML = "&nbsp;�� ���ء���ְ����";
		document.thisForm.seekopen.value = 0;	
	}
	document.getElementById("seekinfoclick").style.color="blue";
}
</script>
</html>