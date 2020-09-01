package com.ocrs.pojo;

public class PolicePojo {
	
	private String userName;
	private String p_id;
	
	
	public PolicePojo(String userName, String p_id) {
		super();
		this.userName = userName;
		this.p_id = p_id;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	
}
