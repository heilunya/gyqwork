package com.gyq.demo;
import javax.ejb.*;

@Stateless
@Local(SayRemoteDao.class)
public class SayRemoteDaoBean implements SayRemoteDao {

	@EJB(beanName="WelcomDaoBean")WelcomDao wd;
	
	public String sayword()  {
		/*
		try{
			InitialContext ctx = new InitialContext();
			WelcomDao wd2 = (WelcomDao)ctx.lookup("java:comp/env/ejb3/WelcomDaoBean");
			wd2.SayHello("gyq2 ...");
		}catch(NamingException e){
			e.printStackTrace();
		}
		*/
		return "hello";
	}

	//用ejb注解方式 注入其它bean实例
	public void saywelcomtoperson() {
		wd.SayHello("gyq...");
		
	}
	
	

}
