package org.mybatis.gyq;

import org.apache.ibatis.type.Alias;

@Alias("Recruit")
public class Recruit {

	private String rRID;
	private String name;
	private String operId;
	private String operName;
	public String getrRID() {
		return rRID;
	}
	public void setrRID(String rRID) {
		this.rRID = rRID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
}
