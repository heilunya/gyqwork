package rmi.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import rmi.model.Account;
import rmi.model.AccountImpl;

public class PersonServiceImpl extends UnicastRemoteObject implements PersonService{

	public PersonServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Account> getList() throws RemoteException {
		List<Account> all = new ArrayList<Account>();
		Account ac1 = new AccountImpl();
		ac1.setId("1");
		ac1.setName("test1");
		all.add(ac1);
		Account ac2 = new AccountImpl();
		ac2.setId("2");
		ac2.setName("test2");
		all.add(ac2);
		return all;
	}

}
