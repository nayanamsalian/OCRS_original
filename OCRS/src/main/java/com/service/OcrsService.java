package com.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dao.OcrsDoa;
import com.ocrs.pojo.CommentPojo;
import com.ocrs.pojo.ComplaintPojo;
import com.ocrs.pojo.Notifications;
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
	
	public List<UserPojo> getAllUserWhoAreNotPolice() {
		
		List<UserPojo> users=ocrsDoa.getAllUsers();
		List<UserPojo> user_not_police=new ArrayList<UserPojo>();
		List<PolicePojo> polices=ocrsDoa.getAllPolice();
		for(UserPojo pojo: users) {
			int flag=0;
			for (PolicePojo policePojo : polices) {
				if(policePojo.getUserName().equals(pojo.getUserName()))
				{
					++flag;
				}
			}
			if(flag==0) {
			user_not_police.add(pojo);
			}
		}
		return user_not_police;
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
		ComplaintPojo complaintPojo=ocrsDoa.getComplaintByComplaintId(complaint_id);
		ocrsDoa.deleteComplaintByID(complaint_id);
		
		File file = new File(complaintPojo.getAttached_file_path()); 
        
        if(file.delete()) 
        { 
            System.out.println("File deleted successfully"); 
        } 
        else
        { 
            System.out.println("Failed to delete the file"); 
        } 
	}

	public PolicePojo getPoliceByUsername(String username) {
		
		return ocrsDoa.getPoliceByUsername(username);
	}

	public List limitRecords(int id, List records, int limit) {
		int number=(id==1?0:((id==2)?limit:(limit+limit)));
		int max=limit;
		int total=records.size()-number;
		List record_list=new ArrayList();
		while(max>0 && total>0) {
			record_list.add(records.get(number++));
			--total;
			--max;
		}
		return record_list;
	}

	public String validateUser(String username, String password, String cpassword, String firstname, String lastname,
			String gender, String email) {
		String message="";
		if(username.equals("") || username == null || password.equals("") || password == null || cpassword.equals("") || cpassword == null || firstname.equals("") || firstname == null) {
			message=message+"Enter the missing field/s, ";
		}
		if(!password.equals(cpassword)) {
			message=message+"Password is not matching, ";
		}
		String pattern = ".*@.*";


		if(!Pattern.matches(pattern, email)) {
			message=message+" invalid email id,";
		}
		
		return message;
	}

	public List<Notifications> getAllNotifications(String username1, String username2,String username3) {
		return ocrsDoa.getAllNotifications(username1, username2, username3);
	}

	public boolean isPolice(String username) {
		PolicePojo pojo=ocrsDoa.getPoliceByUsername(username);
		if(pojo == null)
		return false;
		else
		return true;
	}

	public void addNotification(Notifications notification_ob) {
		ocrsDoa.addNotification(notification_ob);
	}

	public boolean isAdmin(String username) {
		List authorities=ocrsDoa.getAuthorities(username);
		System.out.println(authorities);
		if(authorities.contains("ROLE_ADMIN"))
			return true;
		else
		return false;
	}

	public void deteleNotificationById(int noti_id) {
		ocrsDoa.deteleNotificationById(noti_id);
	}

	public void deleteNotificationByUsername(String username) {
		ocrsDoa.deleteNotificationByUsername(username);
		
	}

	
	public List<Notifications> getValidNotofications(String username){
		boolean isAdmin=isAdmin(username);
		boolean isPolice=isPolice(username);
		PolicePojo policePojo=getPoliceByUsername(username);
		List<Notifications> notifications=null;
		if(isAdmin) {
			notifications=getAllNotifications(username,"admin",policePojo.getP_id());
		}else if(isPolice) {
			notifications=getAllNotifications(username,policePojo.getP_id(), "");
		}
		else {
		notifications=getAllNotifications(username,"","");
		}
		return notifications;
	}

}
