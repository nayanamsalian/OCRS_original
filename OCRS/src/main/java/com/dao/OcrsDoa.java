package com.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ocrs.pojo.CommentPojo;
import com.ocrs.pojo.ComplaintPojo;
import com.ocrs.pojo.UserPojo;

@Component
public interface OcrsDoa {
	public void addUser(UserPojo user);
	public List<UserPojo> getLoginDetails();
	public void postComplaint(ComplaintPojo complaintPojo);
	public List<ComplaintPojo> getAllComplaints(String username);
	public ComplaintPojo getComplaintByComplaintId(int complaint_id);
	public void updateComplaint(ComplaintPojo complaintPojo);
	public List<UserPojo> getUsersByPattern(String pattern);
	public void deleteUser(String username);
	public List<UserPojo> getAllUsers();
	public void addPolice(String username,String p_id);
	public List getAllPolice();
	public void deletePolice(String username);
	public void updateComplaintStatus(int c_id, String status);
	public List<ComplaintPojo> getComplaintsByStationId(String p_id);
	public UserPojo getUserByUserName(String username);
	public void updateUser(String username, int id, String element);
	public void addComment(int complaint_id, String firstName, String comment);
	public List<CommentPojo> getAllCommentsByCompliantId(int complaint_id);
	public void deleteCommentById(int comment_id);
	public void deleteComplaintByID(int complaint_id);
}
