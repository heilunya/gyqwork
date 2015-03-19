package org.mybatis.gyq;

import org.apache.ibatis.type.Alias;

@Alias("Department")
public class Department {

	private int id;
	private String name;
	private String disableFlag;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisableFlag() {
		return disableFlag;
	}
	public void setDisableFlag(String disableFlag) {
		this.disableFlag = disableFlag;
	}
}
