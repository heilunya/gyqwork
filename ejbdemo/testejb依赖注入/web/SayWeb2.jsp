<%@page contentType="text/html; charset=utf8" %>
<%@page import="com.gyq.demo.SayRemoteDao,javax.naming.*,java.util.Properties" %>
<%
try{
	SayRemoteDao sayremotedao = (SayRemoteDao)session.getAttribute("sayremote");
	if(sayremotedao==null){
		InitialContext ctx = new InitialContext();
		sayremotedao = (SayRemoteDao)ctx.lookup("SayRemoteDaoBean/local");
		session.setAttribute("sayremote", sayremotedao);
	}
	
	out.print("<h3>"+sayremotedao.sayword()+"</h3>");
	sayremotedao.saywelcomtoperson();
	//sayremotedao.remove();
}catch(NamingException e){
	out.println(e.getMessage());
}
%>
