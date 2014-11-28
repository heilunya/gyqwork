<%@page language="java"%>
<%@page contentType="text/html;charset=utf8"%>
<HTML>
<HEAD>
	<title>test</title>
	
</HEAD>
<style type="text/css">
.radio
{
	background-color:transparent;
	height:17px;
	width:17px;
	border-left: 1px solid #ffffff;
	border-top:1px solid #ffffff;
	border-right: 1px solid #ffffff;
	border-bottom:1px solid #ffffff; 
	padding-left: 0; 
	padding-right: 0; 
	padding-top:0; 
	padding-bottom: 0;
	vertical-align: middle
}
</style>
<%
	//变量定义
	String submitButtonFlag="",OperName="",OperID="",OperIP="",cusID = "",PerID = "";
	//取session变量
	
	//处理页面参数
	if(request.getParameter("submitButtonFlag")!=null){
		submitButtonFlag=request.getParameter("submitButtonFlag");
		
	}
	if (request.getParameter("cusID") != null) {
		cusID = request.getParameter("cusID");
	}
	
%>
<body>
<br/>
<center>
	<form name="thisForm" method="post">
	<INPUT TYPE="hidden" name="submitButtonFlag" value="<%=submitButtonFlag %>"/>
	<INPUT TYPE="hidden" name="ID"/>
	<INPUT TYPE="hidden" name="cusID" value="<%=cusID %>"/>
	<INPUT TYPE="hidden" name="info"/>
	
				<a onClick="chooseProcess('all');" style="cursor:pointer;color:blue">全选</a>
				<a onClick="chooseProcess('opposition');" style="cursor:pointer;color:blue">反选</a>
				<a onClick="chooseProcess('cancel');" style="cursor:pointer;color:blue">取消选择</a>
			<br/>
			<br/>
	<div id="CusClassifyItem">
	</div>
	<br/><br/>
				<input name="savebutton" id="savebutton" type="button" class="tbbutton" value="保　　存" onclick="SubmitButton(this,'save');">
				<input name="closebutton" id="closebutton" type="button" class="tbbutton" value="关　　闭"  onclick="WinClose();">
			
	
	
	
	</form>
</center>
<br/><br/><br/><br/><br/>
</body>
</html>

<script language="javascript">
	//初始化页面的函数
	initPage();
	initReadOnly(document.thisForm);

	function initPage()
	{
		ajaxfun();
			
	}
	
	function SubmitButton(obj,SubmitFlag)
	{
		switch (SubmitFlag)
		{
			case "save":
				var classficate = document.getElementsByName("CusClassifyItem");
				
				var classficateStr = "";
				for(var i=0;i<classficate.length; i++) {
					if(classficate[i].checked == true){
						classficateStr = classficateStr + classficate[i].value + ";";
					}
				}
				//if(classficateStr==""){
				//	alert("请选择客户分类");
				//	return false;
				//}
				document.thisForm.info.value=classficateStr.substring(0,classficateStr.length -1);
				//document.thisForm.action="/CusCheckAction.do";
				//document.thisForm.submit();
				alert(document.thisForm.info.value);
				break
			default:
				window.alert('没有SubmitFlag参数！');
		}
	}

	function chooseProcess(operator){
		if(operator=="all")	{
			for(var i=0;i<document.thisForm.CusClassifyItem.length;i++) {
				if(document.thisForm.CusClassifyItem[i].name=="CusClassifyItem") {
					document.thisForm.CusClassifyItem[i].checked=true;
				}		
			}
		}	
		if(operator=="cancel")	{
			for(var i=0;i<document.thisForm.CusClassifyItem.length;i++) {
				if(document.thisForm.CusClassifyItem[i].name=="CusClassifyItem") {
					document.thisForm.CusClassifyItem[i].checked=false;
				}
			}
		}	
		if(operator=="opposition")	{
			for(var i=0;i<document.thisForm.CusClassifyItem.length;i++) {
				if(document.thisForm.CusClassifyItem[i].name=="CusClassifyItem") {
					document.thisForm.CusClassifyItem[i].checked=document.thisForm.CusClassifyItem[i].checked?false:true;
				}
			}
		}
	}
   function getajax(){
		$.ajax({
           type: "GET",
           url:"/include/AjaxGetSelectData.jsp",
           data: "submitButtonFlag=GetDicSale&submitParameter1=49&a="+Math.random(),
           success:function(data){
        	   	//alert(data.trim());
        	   	data="test1,1;test2,2;test3,3;";
        	   	var array = data.trim().substring(0,data.trim().length-1).split(";");
        	   	
        	   	var htmlbegin="<table width='50%'><tr>";
        	   	var htmlbody="";
        	   	var htmlend="</tr></table>";
        	   	for(key in array){
        	   		var name = array[key].split(",")[0].trim();
        	   		var id = array[key].split(",")[1].trim();
					//var ClassifyItem=document.createElement("input");
					//ClassifyItem.type="checkbox";
					//ClassifyItem.id="CusClassifyItem";
					//ClassifyItem.name="CusClassifyItem";
					//ClassifyItem.value=id;
					//var td = document.createElement("td");
					//td.align="left";
					//td.setAttribute('height','20');
					//td.appendChild(ClassifyItem);
					//td.appendChild(document.createTextNode(name));
					//tr.appendChild(td);
					htmlbody=htmlbody+"<td><input class='radio' id='CusClassifyItem' type='checkbox' name='CusClassifyItem' value='"+id+"'>"+name+"</td>"
					
				    //document.getElementById("CusClassifyItem").appendChild(ClassifyItem);
        	   		//var checkbox = $("<input type=\"checkbox\" name=\"classficate\" value=\""+id+"\" />").appendTo($("body"));
        	   		
        	   	}
        	   	//table.appendChild(tr);
        	   	//document.getElementById("CusClassifyItem").appendChild(table);
        	   	
        	   	
        	   	$("#CusClassifyItem").append(htmlbegin+htmlbody+htmlend);
        	   	
           }
       });
	}
   function ajaxfun(){
	   data="test1,1;test2,2;test3,3;";
	   	var array = data.trim().substring(0,data.trim().length-1).split(";");
	   	var table = document.createElement("table");
	   	table.width="50%";
	   	var tr = document.createElement("tr");
	   	for(key in array){
	   		var name = array[key].split(",")[0].trim();
	   		var id = array[key].split(",")[1].trim();
	   		var ClassifyItem=document.createElement("input");
			ClassifyItem.type="checkbox";
			ClassifyItem.id="CusClassifyItem";
			ClassifyItem.name="CusClassifyItem";
			ClassifyItem.value=id;
			var td = document.createElement("td");
			//td.align="left";
			td.setAttribute('class','radio');
			//td.setAttribute('height','20');
			td.appendChild(ClassifyItem);
			td.appendChild(document.createTextNode(name));
			tr.appendChild(td);
	   	}
	   	table.appendChild(tr);
	   	document.getElementById("CusClassifyItem").appendChild(table);
	   	//document.getElementById("CusClassifyItem").append(htmlbegin+htmlbody+htmlend);
   }
</script>














