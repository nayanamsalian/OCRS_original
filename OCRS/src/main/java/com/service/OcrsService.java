package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dao.OcrsDoa;
import com.ocrs.pojo.CommentPojo;
import com.ocrs.pojo.ComplaintPojo;
import com.ocrs.pojo.PolicePojo;
import com.ocrs.pojo.UserPojo;

@Component
public class OcrsService {
	
	@Autowired
	OcrsDoa ocrsDoa;
	
	public void addUser(UserPojo user) {
		ocrsDoa.addUser(user);
	}
	
	public List<UserPojo> getUserDetails(){
		List<UserPojo> users=ocrsDoa.getLoginDetails();
		return users;
	}
	
	public List<ComplaintPojo> getAllComplaints(String username){
		return ocrsDoa.getAllComplaints(username);
	}
	public void postComplaint(ComplaintPojo complaintPojo) {
		ocrsDoa.postComplaint(complaintPojo);
	}
	
	public ComplaintPojo getComplaintByComplaintId(int complaint_id) {
		return ocrsDoa.getComplaintByComplaintId(complaint_id);
	}
	
	public void updateComplaint(ComplaintPojo complaintPojo) {
		ocrsDoa.updateComplaint(complaintPojo);
	}

	public List<UserPojo> getUsersByPattern(String pattern) {
		return ocrsDoa.getUsersByPattern(pattern);
	}

	public void deleteUser(String username) {
		ocrsDoa.deleteUser(username);
	}

	public List<UserPojo> getAllUsers() {
		
		return ocrsDoa.getAllUsers();
	}

	public void addPolice(String username,String p_id) {
		ocrsDoa.addPolice(username,p_id);
		
	}

	public List<PolicePojo> getAllPolice() {
		return ocrsDoa.getAllPolice();
	}
	
	public void deletePolice(String username) {
		ocrsDoa.deletePolice(username);
	}

	public void updateComplaintStatus(int c_id, String status) {
		ocrsDoa.updateComplaintStatus(c_id, status);
	}

	public List<ComplaintPojo> getComplaintsByStationId(String p_id) {
		return ocrsDoa.getComplaintsByStationId(p_id);
	}

	public UserPojo getUserByUserName(String username) {
		// TODO Auto-generated method stub
		return ocrsDoa.getUserByUserName(username);
	}

	public void updateUser(String username, int id, String element) {
		ocrsDoa.updateUser(username,id,element);
		
	}

	public void addComment(int complaint_id, String firstName, String comment) {
		ocrsDoa.addComment(complaint_id,firstName,comment);
	}

	public List<CommentPojo> getAllCommentsByCompliantId(int complaint_id) {
		return ocrsDoa.getAllCommentsByCompliantId(complaint_id);
	}

	public void deleteCommentById(int comment_id) {
		ocrsDoa.deleteCommentById(comment_id);
		
	}
	
	public void deleteComplaintByID(int complaint_id)
	{
		ocrsDoa.deleteComplaintByID(complaint_id);
	}
}
