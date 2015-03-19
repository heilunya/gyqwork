package org.mybatis.gyq;

import java.util.ArrayList;

import org.apache.ibatis.type.Alias;

@Alias("Person")
public class Person {

	private String perId;
	private String perName;
	private String deptId;
	private String deptName;
	private ArrayList<Recruit> recruits;
	
	public ArrayList<Recruit> getRecruits() {
		return recruits;
	}
	public void setRecruits(ArrayList<Recruit> recruits) {
		this.recruits = recruits;
	}
	public String getPerId() {
		return perId;
	}
	public void setPerId(String perId) {
		this.perId = perId;
	}
	public String getPerName() {
		return perName;
	}
	public void setPerName(String perName) {
		this.perName = perName;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
