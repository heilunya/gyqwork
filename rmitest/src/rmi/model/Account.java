package rmi.model;

import java.io.Serializable;

public interface Account {
	
	public String sayHello();
	public String getId() ;
	public void setId(String id) ;
	public String getName() ;
	public void setName(String name) ;
}
