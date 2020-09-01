package com.ocrs.pojo;

public class ComplaintPojo {
	private int complaint_id;
	private String username;
	private String p_code;
	private String complaint;
	private String attached_file_path;
	private String priority;
	private String status;
	public ComplaintPojo(String username, String p_code, String complaint, String attached_file_path, String priority, String status) {
		super();
		this.username = username;
		this.p_code = p_code;
		this.complaint = complaint;
		this.attached_file_path=attached_file_path;
		this.priority=priority;
		this.status = status;
	}
	
	
	public String getAttached_file_path() {
		return attached_file_path;
	}


	public void setAttached_file_path(String attached_file_path) {
		this.attached_file_path = attached_file_path;
	}


	public String getPriority() {
		return priority;
	}


	public void setPriority(String priority) {
		this.priority = priority;
	}


	public int getComplaint_id() {
		return complaint_id;
	}


	public void setComplaint_id(int complaint_id) {
		this.complaint_id = complaint_id;
	}


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getP_code() {
		return p_code;
	}
	public void setP_code(String p_code) {
		this.p_code = p_code;
	}
	public String getComplaint() {
		return complaint;
	}
	public void setComplaint(String complaint) {
		this.complaint = complaint;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "ComplaintPojo [complaint_id=" + complaint_id + ", username=" + username + ", p_code=" + p_code
				+ ", complaint=" + complaint + ", status=" + status + "]";
	}	
	
}
