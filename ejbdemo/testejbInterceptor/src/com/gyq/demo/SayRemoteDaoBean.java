package com.gyq.demo;
import javax.ejb.*;
import javax.interceptor.Interceptors;

@Stateless
@Local(SayRemoteDao.class)
@Interceptors(SayRemoteIntercepters.class)
public class SayRemoteDaoBean implements SayRemoteDao {

	public String sayword() {
		
		return "hello";
	}
	

}
