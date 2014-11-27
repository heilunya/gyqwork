package rmi.rmiClient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import rmi.model.Account;
import rmi.service.PersonService;

public class Client {

	public static void main(String args[]){
		try {
			PersonService perService = (PersonService)Naming.lookup("rmi://192.168.61.137:6600/PersonService");
			List<Account> all = perService.getList();
			for(int i=0;i<all.size();i++){
				System.out.println(all.get(i).getId());
				System.out.println(all.get(i).getName());
				System.out.println("*****************************");
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
