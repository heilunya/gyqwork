package org.mybatis.gyq;

import org.apache.ibatis.type.Alias;

@Alias("Menu")
public class Menu {

	private int id;
	private int Parent;
	private String Name;
	public int getID() {
		return id;
	}
	public void setID(int iD) {
		id = iD;
	}
	public int getParent() {
		return Parent;
	}
	public void setParent(int parent) {
		Parent = parent;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
}
