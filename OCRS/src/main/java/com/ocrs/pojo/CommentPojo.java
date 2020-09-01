package com.ocrs.pojo;

public class CommentPojo {
	private int comment_id;
	private String complaint_id;
	private String firstName;
	private String comment;
	
	public CommentPojo() {	}
	
	public CommentPojo(String complaint_id, String firstName, String comment) {
		super();
		this.complaint_id = complaint_id;
		this.firstName = firstName;
		this.comment = comment;
	}
	

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public String getComplaint_id() {
		return complaint_id;
	}

	public void setComplaint_id(String complaint_id) {
		this.complaint_id = complaint_id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
