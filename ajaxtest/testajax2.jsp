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
<script src="/js/jquery-1.4.2.min.js"></script>
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
				<input name="savebutton" id="savebutton" type="button" value="保　　存" onclick="SubmitButton(this,'save');">
				<input name="closebutton" id="closebutton" type="button" value="关　　闭"  onclick="WinClose();">
			
	
	
	
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
   
   function ajaxfun(){
	   data="test1,1;test2,2;test3,3;";
	   
	   	var arrField=data.split(";");
	   	//alert("hello1");
		var Obj =document.getElementById("CusClassifyItem");
		if (arrField.length<=0)
		{
			alert("返回串为空!");
			return false;
		}
		else
		{
			//alert("hello1");
			//var table = document.createElement("table");
		   //	table.width="50%";
		   //	var tr = document.createElement("tr");
		   	var bodystr = "";
			for(var i=0;i<=arrField.length-2;i+=1)
			{	
				//alert("hello2");
				 var arrField2=arrField[i].split(",");
				 //alert(arrField2.length);
				 if (arrField2.length>0)
				 {
					 	var name = arrField2[0];
				   		var id = arrField2[1];
				   		bodystr = bodystr + "<input type='checkbox' id='tt' name='tt' value='"+id+"'>"+name;
					 
				 }
				 

			}
			//alert("hello3");
			 //table.appendChild(tr);
			// document.getElementById("CusClassifyItem").appendChild(table);
			 //alert("hello4");
			//$("#productID").attr("loaded",true);
           	$("#CusClassifyItem").html(bodystr);
           	//alert("hello4");
		}
	 
	   	/*  	
	   	var arrField=trim(data).split(";");
		var SelectObj =document.getElementById("CusClassifyItem");
		if (arrField.length<=0)
		{
			alert("返回串为空!");
			return false;
		}
		else
		{
			ClearSelectOption('GetDicttt',SelectObj);
			for(var i=0;i<=arrField.length-2;i+=1)
			{	
				 
				 var arrField2=arrField[i].split(",");
				 //alert(arrField2.length);
				 if (arrField2.length>0)
				 {
					AddSelectOption(SelectObj,arrField2[0],arrField2[0]);
				 }
			 

			}
		}
		*/
	   	//document.getElementById("CusClassifyItem").append(htmlbegin+htmlbody+htmlend);
   }
   
   function ClearSelectOption(submitButtonFlag,obj)
	{
		var textstr="==请选择==";

		for(i=obj.length-1;i>=0;i--)
			obj.options[i]=null;
		if (submitButtonFlag=="GetDepartmentAll")
		{
			textstr="==所有==";
		}
		if (submitButtonFlag=="CusBrand")
		{
			textstr="==选择品牌类型==";
		}
		AddSelectOption(obj,textstr,"");
	}
   
   function AddSelectOption(obj,text,value)
	{
		var opt=document.createElement("OPTION");
		opt.text=text;
		opt.value=trim(value);
		//obj.add(opt); //ｉｅ
		obj.options.add(opt);//firefox ie
	}
   function trim(str) {
		var str1=str.replace(/(^\s*)|(\s*$)/g,"");
		return str1.replace("　","");
   }
</script>














