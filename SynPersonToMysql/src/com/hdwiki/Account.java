package com.hdwiki;

public class Account {

	//uid,username,password,email,gender,credit2,credit1,birthday,image,postcode,location,regip,regtime,lastip,lasttime,groupid,timeoffset,style,
	//language,signature,truename,telephone,qq,msn,authstr,creates,edits,views,checkup,newdocs
	
	//username,password,email,gender,image,postcode,location,regip,lastip,groupid,truename,telephone,qq,msn,authstr
	
	private String username;  //用户工号
	private String password;  //密码(注意：此处要求是用户的明文密码)
	private String email;     //邮箱
	private String gender;  //性别
	private String image;  //图片
	private String postcode = ""; 
	private String location;      
	private String regip = "unknown"; 
	private String lastip = "unknown"; 
	private String groupid = "2";
	private String truename;
	private String telephone;
	private String qq = "";
	private String msn = "";
	private String authstr = "";
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRegip() {
		return regip;
	}

	public void setRegip(String regip) {
		this.regip = regip;
	}

	public String getLastip() {
		return lastip;
	}

	public void setLastip(String lastip) {
		this.lastip = lastip;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getAuthstr() {
		return authstr;
	}

	public void setAuthstr(String authstr) {
		this.authstr = authstr;
	}





	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
