<%@page contentType="text/html;charset=gbk"%>

<%@page language="java"%>
<html>
<head>
<link rel="shortcut icon" href="/images/boss.ico"/>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=gbk">
<title>��������</title>
</head>
<body style="background-color:#E0EEE0">
<center>
<form name="thisForm" action="ParserContent.jsp" method="post">
<strong>������Ҫ���������ݣ�</strong><br/><br/>
<font size="2px">ҳ��˵�����ȵ�¼������ǰ�����ǣ���Ҫ�����ļ�����Ȼ���ڿհ״��Ҽ���ѡ�����Ʋ鿴ҳ��Դ���룬�鿴Դ�ļ���ѡ�<br/>
���´򿪵Ĵ��ڰ�ctrl+aȫѡ����ctrl+c���ƣ�Ȼ��ճ������ҳ����ı����У�����������ɡ�</font>
<textarea rows="30" cols="150" name="content"></textarea>
<input type="hidden" name="htmlcontent"/>
<br/><br/>
<input type="submit" value="����"/>
<input type="reset" value="����"/>

</form>
</center>

<script type="text/javascript">
	function setHtmlContent(){
		thisForm.htmlcontent.value=thisForm.content.value.trim();
	}
</script>
</body>
</html>