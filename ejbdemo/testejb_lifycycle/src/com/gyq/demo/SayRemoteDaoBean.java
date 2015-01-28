package com.gyq.demo;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;

@Stateful
@Remote(SayRemoteDao.class)
public class SayRemoteDaoBean implements SayRemoteDao {

	public String sayword(String msg) {
		
		return "gyq say some word of "+msg;
	}
	
	@Init
	public void initfunction(){
		System.out.println("ִ�� @init function");
	}
	
	@PostConstruct
	public void construct(){
		System.out.println("ִ�� @postConstruct function");
	}
	@PreDestroy
	public void predestroy(){
		System.out.println("ִ�� @preDestroy function");
	}
	@Remove
	public void remove(){
		System.out.println("ִ�� @@Remove function");
	}


}
