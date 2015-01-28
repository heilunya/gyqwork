package com.gyq.demo;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class SayRemoteIntercepters {

	@AroundInvoke
	public Object log(InvocationContext ict) throws Exception{
		System.out.println("拦截器开始 ...");
		try{
			if(ict.getMethod().getName().equals("sayword")){
				System.out.println("*** sayword 方法已经被调用***");
			}
			Object obj = ict.getTarget();
			System.out.println("obj gettarget toString method "+obj.toString());
			return ict.proceed();
		}catch(Exception e){
			throw e;
		}
	}

}
