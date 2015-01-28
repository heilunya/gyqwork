<%@page language="java"%>
<%@page contentType="text/html;charset=utf8"%>
<HTML>
<HEAD>
	<title>test</title>
	
</HEAD>
<body>
<form name="thisForm" method="post">
	<a href="http://www.baidu.com">baidu</a>
	<input type="button" name="submitbutton" value="提交" onclick="openSubmitWin()"/>
</form>
</body>
</HTML>
<script	language="javascript" type="text/javascript">

function openSubmitWin() 
{ 
	action=location.href;
	var userAgent = window.navigator.userAgent.toLowerCase();
	//如果是谷歌浏览器，则在链接后加个随机数
	if(userAgent.indexOf("chrome")>0){
		document.thisForm.target="_blank";
		if(action.indexOf("?") >= 0 )   
		{   
			document.thisForm.action=action+"&r="+Math.random();
		} else{
			document.thisForm.action=action+"?r="+Math.random();
		}
		document.thisForm.submit();
		return true;
	}else{
		document.thisForm.target="_blank";
		document.thisForm.action=action;
		document.thisForm.submit();
		return true;
	}
	
}
</script>