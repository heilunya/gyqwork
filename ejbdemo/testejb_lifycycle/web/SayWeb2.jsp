<%@page contentType="text/html; charset=utf8" %>
<%@page import="com.gyq.demo.SayRemoteDao,javax.naming.*,java.util.Properties" %>
<%
try{
	SayRemoteDao sayremotedao = (SayRemoteDao)session.getAttribute("sayremote");
	if(sayremotedao==null){
		InitialContext ctx = new InitialContext();
		sayremotedao = (SayRemoteDao)ctx.lookup("SayRemoteDaoBean/remote");
		session.setAttribute("sayremote", sayremotedao);
	}
	
	out.print("<h2>"+sayremotedao.sayword("ttt ejb")+"</h2>");
	//sayremotedao.remove();
}catch(NamingException e){
	out.println(e.getMessage());
}
%>
