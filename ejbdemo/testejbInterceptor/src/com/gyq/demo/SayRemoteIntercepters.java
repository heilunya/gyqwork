package com.gyq.demo;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class SayRemoteIntercepters {

	@AroundInvoke
	public Object log(InvocationContext ict) throws Exception{
		System.out.println("��������ʼ ...");
		try{
			if(ict.getMethod().getName().equals("sayword")){
				System.out.println("*** sayword �����Ѿ�������***");
			}
			Object obj = ict.getTarget();
			System.out.println("obj gettarget toString method "+obj.toString());
			return ict.proceed();
		}catch(Exception e){
			throw e;
		}
	}

}
