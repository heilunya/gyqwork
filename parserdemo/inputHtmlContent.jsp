<%@page contentType="text/html;charset=gbk"%>

<%@page language="java"%>
<html>
<head>
<link rel="shortcut icon" href="/images/boss.ico"/>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=gbk">
<title>解析简历</title>
</head>
<body style="background-color:#E0EEE0">
<center>
<form name="thisForm" action="ParserContent.jsp" method="post">
<strong>请输入要解析的内容：</strong><br/><br/>
<font size="2px">页面说明：先登录智联或前程无忧，打开要解析的简历，然后在空白处右键，选择类似查看页面源代码，查看源文件的选项，<br/>
在新打开的窗口按ctrl+a全选，再ctrl+c复制，然后粘贴到本页面的文本框中，点击解析即可。</font>
<textarea rows="30" cols="150" name="content"></textarea>
<input type="hidden" name="htmlcontent"/>
<br/><br/>
<input type="submit" value="解析"/>
<input type="reset" value="重置"/>

</form>
</center>

<script type="text/javascript">
	function setHtmlContent(){
		thisForm.htmlcontent.value=thisForm.content.value.trim();
	}
</script>
</body>
</html>