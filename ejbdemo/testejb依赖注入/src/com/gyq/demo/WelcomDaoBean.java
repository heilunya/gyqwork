package com.gyq.demo;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(WelcomDao.class)
public class WelcomDaoBean implements WelcomDao{

	public void SayHello(String msg) {
		System.out.println("Welcom "+msg);
		
	}

}
