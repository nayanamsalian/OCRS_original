package com.ocrs.pojo;

import org.springframework.web.multipart.MultipartFile;

public class FileBucket {

	MultipartFile file=null;
	private int c_id;
	private String username;
	private String p_id;
	private String complaint;
	private String priority;
	private String status;
	
	public int getC_id() {
		return c_id;
	}

	public void setC_id(int complaint_id) {
		this.c_id = complaint_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getP_id() {
		return p_id;
	}

	public void setP_id(String p_code) {
		this.p_id = p_code;
	}

	public String getComplaint() {
		return complaint;
	}

	public void setComplaint(String complaint) {
		this.complaint = complaint;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "FileBucket [file=" + file + ", c_id=" + c_id + ", username=" + username + ", p_id=" + p_id
				+ ", complaint=" + complaint + ", priority=" + priority + ", status=" + status + "]";
	}
	

}