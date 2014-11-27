package rmi.rmiService;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import rmi.service.PersonService;
import rmi.service.PersonServiceImpl;

public class Service {

	public static void main(String args[]){
		try{
			PersonService perservice = new PersonServiceImpl();
			//注册通讯端口  
          LocateRegistry.createRegistry(6600);  
            //注册通讯路径  
          Naming.rebind("rmi://192.168.61.137:6600/PersonService", perservice);  
          System.out.println("Service Start!");  
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
