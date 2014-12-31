package com.gyq.redis;

import java.io.Serializable;

/**
 * @author hutian
 *select top 10 id,PerID,JobcnID,ProvinceID,CityID,CusSort from Customer
 */
public class CustomerDto implements Serializable{
	
	private String cityId="";
	
	private String provinceId="";
	
	private String id="";
	
	private String jobcnId="";
	
	private String cussort="";
	
	private String perId="";

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobcnId() {
		return jobcnId;
	}

	public void setJobcnId(String jobcnId) {
		this.jobcnId = jobcnId;
	}

	public String getCussort() {
		return cussort;
	}

	public void setCussort(String cussort) {
		this.cussort = cussort;
	}

	public String getPerId() {
		return perId;
	}

	public void setPerId(String perId) {
		this.perId = perId;
	}
	
	
}
