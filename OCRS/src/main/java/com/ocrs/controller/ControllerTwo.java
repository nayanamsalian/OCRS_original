package com.ocrs.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mchange.net.MailSender;
import com.ocrs.pojo.CommentPojo;
import com.ocrs.pojo.ComplaintPojo;
import com.ocrs.pojo.FileModel;
import com.ocrs.pojo.MailMail;
import com.ocrs.pojo.PolicePojo;
import com.ocrs.pojo.UserPojo;
import com.service.OcrsService;

@Controller
public class ControllerTwo {

	private static final List<UserPojo> ModelAndView = null;
	@Autowired
	OcrsService ocrsService;

	@Autowired
	MailMail mail;

	@Autowired
	ServletContext context; 

	@RequestMapping("/processRegistrationForm")
	public String registerUser(HttpServletRequest rq, HttpServletResponse rs) {
		String username=rq.getParameter("userName");
		String password=rq.getParameter("password");
		String cpassword=rq.getParameter("matchingPassword");
		String firstname=rq.getParameter("firstName");
		String lastname=rq.getParameter("lastName");
		String gender=rq.getParameter("gender");
		String email=rq.getParameter("email");

		String adminName=rq.getParameter("adminName");

		UserPojo user=new UserPojo(username, password, firstname, lastname, gender, email);
		ocrsService.addUser(user);
		System.out.println("adminName"+adminName);
		if(adminName == null)
			return "login";
		else
			return "home";
	}

	@RequestMapping("/addComplaint")
	public String addCompaint(HttpServletRequest rq, HttpServletResponse rs) {
		return "add-complaint";

	}
	@RequestMapping("/viewAllComplaints")
	public ModelAndView viewCompaint(HttpServletRequest rq, HttpServletResponse rs) {
		String uname=rq.getParameter("user_name");
		List<ComplaintPojo> complaints=ocrsService.getAllComplaints(uname);
		ModelAndView mv=new ModelAndView();
		mv.addObject("complaints",complaints);
		mv.setViewName("view_all_my_complaint");
		return mv;
	}

	@PostMapping("/postComplaint")
	public String postCompaint(@Validated FileModel file, BindingResult result,HttpServletRequest rq, HttpServletResponse rs) {
		System.out.println("Post_complaint");
		String p_id=rq.getParameter("p_id");
		String user_name=rq.getParameter("user_name");
		String complaint=rq.getParameter("complaint");
		String priority=rq.getParameter("priority");
		//ComplaintPojo complaintPojo=new ComplaintPojo(user_name, p_id, complaint, "In Progress");
		int complaint_id=0;
		String uploaded_file_full_path="";

		if (result.hasErrors()) {
			uploaded_file_full_path="File Not Found";
		} else {            
			System.out.println("inside else:"+file.getFile());
			MultipartFile multipartFile = file.getFile();
			System.out.println("multipartFile else:"+file.getFile());
			String uploadPath = context.getRealPath("") + File.separator +"resources"+File.separator+ "downloads" + File.separator;
			System.out.println("Upload path:"+uploadPath);
			uploaded_file_full_path=uploadPath+file.getFile().getOriginalFilename();
			//complaintPojo.setAttached_file_path(uploaded_file_full_path);
			//Now do something with file...
			try {
				FileCopyUtils.copy(file.getFile().getBytes(), new File(uploadPath+file.getFile().getOriginalFilename()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String fileName = multipartFile.getOriginalFilename();

			//return "success";
		}
		ComplaintPojo complaintPojo=new ComplaintPojo(user_name, p_id, complaint, uploaded_file_full_path,priority,"In Progress");

		if(rq.getParameter("c_id")!=null)
			complaint_id=Integer.parseInt(rq.getParameter("c_id"));
		if(complaint_id!=0) {
			complaintPojo.setComplaint_id(complaint_id);
			System.out.println(complaintPojo);
			ocrsService.updateComplaint(complaintPojo);
		}
		else
			ocrsService.postComplaint(complaintPojo);
		return "complaint_details";

	}
	//@Autowired
	//MailSender mail;

	@RequestMapping("updateCompliantRequest")
	public ModelAndView updateCompliant(HttpServletRequest rq, HttpServletResponse rs) {
		int c_id=Integer.parseInt(rq.getParameter("c_id"));
		ComplaintPojo complaintPojo=ocrsService.getComplaintByComplaintId(c_id);
		ModelAndView mv=new ModelAndView();
		mv.addObject("complaint",complaintPojo);
		mv.setViewName("add-complaint");
		//System.out.println("mail"+mail);
		return mv;
	}


	@RequestMapping("searchUser")
	public ModelAndView searchUser(HttpServletRequest rq, HttpServletResponse rs) {
		String pattern=rq.getParameter("user_pattern");
		List<UserPojo> users=ocrsService.getUsersByPattern(pattern);
		if(users.size()==0)
			users=null;
		ModelAndView mv=new ModelAndView();
		mv.addObject("users",users);
		mv.setViewName("show_users");
		return mv;

	}

	@GetMapping("viewAllUser")
	public ModelAndView searchAllUser(HttpServletRequest rq, HttpServletResponse rs) {
		List<UserPojo> users=ocrsService.getAllUsers();
		if(users.size()==0)
			users=null;
		ModelAndView mv=new ModelAndView();
		mv.addObject("users",users);
		mv.setViewName("show_users");
		return mv;

	}

	@RequestMapping("deleteUser")
	public String deleteUser(HttpServletRequest rq, HttpServletResponse rs) {
		String username=rq.getParameter("user_name");
		ocrsService.deleteUser(username);
		return "manage_user";

	}

	@GetMapping("makeAsPolice")
	public ModelAndView makeAsPolice(HttpServletRequest rq, HttpServletResponse rs) {
		List<UserPojo> users=ocrsService.getAllUsers();
		if(users.size()==0)
			users=null;
		ModelAndView mv=new ModelAndView();
		mv.addObject("users",users);
		mv.setViewName("update_user_to_police");
		return mv;

	}

	@RequestMapping("updateUserToPolice")
	public String updateUserToPolice(HttpServletRequest rq, HttpServletResponse rs) {
		String username=rq.getParameter("user_name");
		String p_id=rq.getParameter("p_id");
		System.out.println("p_id:"+p_id);
		ocrsService.addPolice(username,p_id);
		ModelAndView mv=new ModelAndView();
		return "manage_police";	
	}

	@GetMapping("viewAllPolice")
	public ModelAndView viewAllPolice(HttpServletRequest rq, HttpServletResponse rs) {
		List<PolicePojo> polices=ocrsService.getAllPolice();
		if(polices.size()==0)
			polices=null;
		ModelAndView mv=new ModelAndView();
		mv.addObject("polices",polices);
		mv.setViewName("show_all_police");
		return mv;

	}

	@RequestMapping("deletePolice")
	public String deletePolice(HttpServletRequest rq, HttpServletResponse rs) {
		String username=rq.getParameter("user_name");
		ocrsService.deletePolice(username);
		return "manage_police";

	}

	@RequestMapping("/viewAllRegisteredComplaints")
	public ModelAndView viewAllRegisteredComplaints(HttpServletRequest rq, HttpServletResponse rs) {
		List<ComplaintPojo> complaints=ocrsService.getAllComplaints(" ");
		ModelAndView mv=new ModelAndView();
		mv.addObject("complaints",complaints);
		mv.setViewName("view_all_complaint");
		return mv;
	}

	@RequestMapping("expandComplaint")
	public ModelAndView expandComplaint(HttpServletRequest rq, HttpServletResponse rs) {
		int c_id=Integer.parseInt(rq.getParameter("c_id"));
		ComplaintPojo complaintPojo=ocrsService.getComplaintByComplaintId(c_id);
		ModelAndView mv=new ModelAndView();
		mv.addObject("complaint",complaintPojo);
		mv.setViewName("expanded_complaint");
		return mv;
	}

	@RequestMapping("updateComplaintStatus")
	public ModelAndView updateComplaintStatus(HttpServletRequest rq, HttpServletResponse rs) {
		int c_id=Integer.parseInt(rq.getParameter("c_id"));
		String status=rq.getParameter("status");
		ocrsService.updateComplaintStatus(c_id,status);
		ComplaintPojo complaintPojo=ocrsService.getComplaintByComplaintId(c_id);
		ModelAndView mv=new ModelAndView();
		mv.addObject("complaint",complaintPojo);
		mv.setViewName("expanded_complaint");
		return mv;
	}

	@RequestMapping("getComplaintByStationID")
	public ModelAndView getComplaintByStationID(HttpServletRequest rq, HttpServletResponse rs) {
		String p_id=rq.getParameter("p_id");
		List<ComplaintPojo> complaints=ocrsService.getComplaintsByStationId(p_id);
		if(complaints.size()==0)
			complaints=null;
		ModelAndView mv=new ModelAndView();
		mv.addObject("complaints",complaints);
		mv.setViewName("view_all_complaint");
		return mv;
	}

	@RequestMapping("personDetails")
	public ModelAndView printPersonalDetails(HttpServletRequest rq, HttpServletResponse rs) {
		String username=rq.getParameter("userName");
		UserPojo userPojo=ocrsService.getUserByUserName(username);
		ModelAndView mv=new ModelAndView();
		mv.addObject("userPojo",userPojo);
		mv.setViewName("print_personal_details");
		return mv;
	}

	@RequestMapping("deactivateUser")
	public String deactivateUser(HttpServletRequest rq, HttpServletResponse rs) {
		String username=rq.getParameter("userName");
		ocrsService.deleteUser(username);
		return "login";
	}

	@RequestMapping("printUser")
	public ModelAndView printUser(HttpServletRequest rq, HttpServletResponse rs) {
		String username=rq.getParameter("userName");
		int id=Integer.parseInt(rq.getParameter("id"));
		UserPojo userPojo=ocrsService.getUserByUserName(username);
		ModelAndView mv=new ModelAndView();
		mv.addObject("userPojo",userPojo);
		mv.addObject("id",id);
		mv.setViewName("print_personal_details");
		return mv;
	}

	@RequestMapping("updateUser")
	public ModelAndView updateUser(HttpServletRequest rq, HttpServletResponse rs) {
		String username=rq.getParameter("userName");
		int id=Integer.parseInt(rq.getParameter("id"));
		String element=rq.getParameter("element");
		ocrsService.updateUser(username,id,element);
		UserPojo userPojo=ocrsService.getUserByUserName(username);
		ModelAndView mv=new ModelAndView();
		mv.addObject("userPojo",userPojo);
		//mv.addObject("id",id);
		mv.setViewName("print_personal_details");
		return mv;
	}

	@RequestMapping("reply")
	public ModelAndView reply(HttpServletRequest rq, HttpServletResponse rs) {
		boolean flag=true;
		int c_id=Integer.parseInt(rq.getParameter("complaint_id"));
		ComplaintPojo complaintPojo=ocrsService.getComplaintByComplaintId(c_id);
		ModelAndView mv=new ModelAndView();
		mv.addObject("complaint",complaintPojo);
		mv.addObject("flag",flag);
		mv.setViewName("expanded_complaint");
		return mv;

	}

	@RequestMapping("addComment")
	public ModelAndView addComment(HttpServletRequest rq, HttpServletResponse rs) {
		String username=rq.getParameter("userName");
		int complaint_id=Integer.parseInt(rq.getParameter("complaint_id"));
		String comment=rq.getParameter("comment");
		String firstName=ocrsService.getUserByUserName(username).getFirstName();
		ocrsService.addComment(complaint_id,firstName,comment);
		List<CommentPojo> comments=ocrsService.getAllCommentsByCompliantId(complaint_id);
		ComplaintPojo complaintPojo=ocrsService.getComplaintByComplaintId(complaint_id);
		ModelAndView mv=new ModelAndView();
		mv.addObject("complaint",complaintPojo);
		mv.addObject("comments",comments);
		mv.setViewName("expanded_complaint");
		return mv;
	}

	@RequestMapping("getAllComments")
	public ModelAndView getAllComments(HttpServletRequest rq, HttpServletResponse rs) {
		int complaint_id=Integer.parseInt(rq.getParameter("complaint_id"));
		List<CommentPojo> comments=ocrsService.getAllCommentsByCompliantId(complaint_id);
		ComplaintPojo complaintPojo=ocrsService.getComplaintByComplaintId(complaint_id);
		ModelAndView mv=new ModelAndView();
		mv.addObject("complaint",complaintPojo);
		mv.addObject("comments",comments);
		mv.setViewName("expanded_complaint");
		return mv;
	}


	@RequestMapping("deleteComment")
	public ModelAndView deleteComment(HttpServletRequest rq, HttpServletResponse rs) {
		int complaint_id=Integer.parseInt(rq.getParameter("complaint_id"));
		int comment_id=Integer.parseInt(rq.getParameter("comment_id"));
		ocrsService.deleteCommentById(comment_id);
		List<CommentPojo> comments=ocrsService.getAllCommentsByCompliantId(complaint_id);
		ComplaintPojo complaintPojo=ocrsService.getComplaintByComplaintId(complaint_id);
		ModelAndView mv=new ModelAndView();
		mv.addObject("complaint",complaintPojo);
		mv.addObject("comments",comments);
		mv.setViewName("expanded_complaint");
		return mv;
	}

	@RequestMapping("deleteComplaint")
	public ModelAndView deleteComplaint(HttpServletRequest rq, HttpServletResponse rs) {		
		String uname=rq.getParameter("user_name");
		List<ComplaintPojo> complaints=null;
		int complaint_id=Integer.parseInt(rq.getParameter("complaint_id"));
		ocrsService.deleteComplaintByID(complaint_id);
		ModelAndView mv=new ModelAndView();
		if((Integer.parseInt(rq.getParameter("id")))!=1) {
			complaints=ocrsService.getAllComplaints(uname);
			mv.setViewName("view_all_my_complaint");
		}
		else {
			complaints=ocrsService.getAllComplaints(" ");
			mv.setViewName("view_all_complaint");
		}
		mv.addObject("complaints",complaints);
		return mv;
	}

	@RequestMapping("/show_forgotPasswordPage")
	public String show_forgotPasswordPage() {
		return "recover_password";
	}

	@RequestMapping("forgotPassword")
	public ModelAndView forgotPassword(HttpServletRequest rq, HttpServletResponse rs) {		
		String uname=rq.getParameter("username");
		UserPojo pojo=ocrsService.getUserByUserName(uname);
		ModelAndView mv=new ModelAndView();
		try {
			mail.sendMail(pojo.getFirstName(), pojo.getEmail(), "Hi");
		}
		catch(Exception e) {
			mv.addObject("message","SMTP server error");
			mv.setViewName("recover_password");
			return mv;
		}
		mv.setViewName("fancy-login");
		return mv;
	}
}
