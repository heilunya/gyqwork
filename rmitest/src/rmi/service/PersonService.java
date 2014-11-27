package rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import rmi.model.Account;

public interface PersonService extends Remote {
	public List<Account> getList() throws RemoteException;
}
