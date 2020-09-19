package com.ocrs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mchange.net.MailSender;
import com.ocrs.pojo.CommentPojo;
import com.ocrs.pojo.ComplaintPojo;
import com.ocrs.pojo.FileBucket;
import com.ocrs.pojo.FileValidator;
import com.ocrs.pojo.MailMail;
import com.ocrs.pojo.MultiFileValidator;
import com.ocrs.pojo.Notifications;
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

	private static String UPLOAD_LOCATION="C:\\OCRS_fileupload\\";

	@Autowired
	FileValidator fileValidator;

	@Autowired
	MultiFileValidator multiFileValidator;

	@InitBinder("fileBucket")
	protected void initBinderFileBucket(WebDataBinder binder) {
		binder.setValidator(fileValidator);
	}

	@InitBinder("multiFileBucket")
	protected void initBinderMultiFileBucket(WebDataBinder binder) {
		binder.setValidator(multiFileValidator);
	}
	
	@RequestMapping("/back")
	public ModelAndView backToHome(HttpServletRequest rq, HttpServletResponse rs) {
		ModelAndView mv=new ModelAndView();
		String adminName=rq.getParameter("adminName");
		if(adminName == null)
			mv.setViewName("login");
		else
			mv.setViewName("home");
		return mv;
		
	}

	@RequestMapping("/processRegistrationForm")
	public ModelAndView registerUser(HttpServletRequest rq, HttpServletResponse rs) {
		String username=rq.getParameter("userName");
		String password=rq.getParameter("password");
		String cpassword=rq.getParameter("matchingPassword");
		String firstname=rq.getParameter("firstName");
		String lastname=rq.getParameter("lastName");
		String gender=rq.getParameter("gender");
		String email=rq.getParameter("email");
		ModelAndView mv=new ModelAndView();
		String adminName=rq.getParameter("adminName");

		UserPojo ur=ocrsService.getUserByUserName(username);
		String message=ocrsService.validateUser(username,password,cpassword,firstname,lastname,gender,email);
		System.out.println(ur+  "user");
		if(ur.getUserName() == null && message.equals("")) {
			UserPojo user=new UserPojo(username, password, firstname, lastname, gender, email);
			ocrsService.addUser(user);
			
			System.out.println("adminName"+adminName);
			if(adminName == null)
				mv.setViewName("login");
			else
				mv.setViewName("home");
			
			
		}
		else {
			if(ur.getUserName() != null) {
			if(ur.getUserName().equals(username)) {
				message=message+"username already in use, please try something else !!";
				}
			}
			mv.addObject("message",message);
			mv.addObject("adminName",adminName);
			mv.setViewName("registration-form");
		}

		
		return mv;
	}

	@RequestMapping("/addComplaint")
	public String addCompaint(HttpServletRequest rq, HttpServletResponse rs) {
		return "add-complaint";

	}
	@RequestMapping("/viewAllComplaints")
	public ModelAndView viewCompaint(HttpServletRequest rq, HttpServletResponse rs) {
		String uname=rq.getParameter("user_name");
		int id=0;
		if(rq.getParameter("id")!=null)
			id=Integer.parseInt(rq.getParameter("id"));
		List<ComplaintPojo> complaints=ocrsService.getAllComplaints(uname);
		ModelAndView mv=new ModelAndView();
		int size=complaints.size();
		mv.addObject("size",size);
		if(id!=0)
			complaints=ocrsService.limitRecords(id,complaints, 8);
		else
			complaints=ocrsService.limitRecords(1,complaints,8);

		mv.addObject("complaints",complaints);

		System.out.println("size"+complaints.size());
		mv.setViewName("view_all_my_complaint");
		return mv;
	}

	@PostMapping("/postComplaint")
	public ModelAndView postCompaint(@ModelAttribute FileBucket fileBucket,
			BindingResult result, ModelMap model, HttpServletRequest rq, HttpServletResponse rs) throws IOException {
		System.out.println("Post_complaint");
		String user_name=rq.getParameter("user_name");
		ModelAndView mv=new ModelAndView();
		String url=rq.getParameter("url");
		String uploaded_file_name="";
		fileBucket.setUsername(user_name);

		if(fileBucket.getFile().getOriginalFilename().equals("")) {
			uploaded_file_name="file not found";
		}
		else {
			File directory = new File(UPLOAD_LOCATION);
			if(!directory.exists()) {
				directory.mkdir();
			}
			if (result.hasErrors()) {
				System.out.println("validation errors");
				mv.addObject("message","not able to upload file! try smaller size file");
				mv.setViewName("complaint_details");
				return mv;
			} else {
				//UPLOAD_LOCATION=rq.getServletContext().getRealPath("/uploaded_files/");
				System.out.println("UPLOAD_LOCATION :"+UPLOAD_LOCATION);
				MultipartFile multipartFile = fileBucket.getFile();

				System.out.println("fileBucket.getFile()"+fileBucket.getFile()+"   UPLOAD_LOCATION"+UPLOAD_LOCATION);
				// Now do something with file...

				if(fileBucket.getFile()!=null) {
					uploaded_file_name=user_name+"_"+fileBucket.getFile().getOriginalFilename();
					System.out.println("uploaded_file_name"+uploaded_file_name);
					FileCopyUtils.copy(fileBucket.getFile().getBytes(), new File( UPLOAD_LOCATION + uploaded_file_name));

				}
			}
		}


		System.out.println("c_id"+fileBucket.getC_id()+"\n ");
		ComplaintPojo complaintPojo=new ComplaintPojo(fileBucket.getUsername(), fileBucket.getP_id(), fileBucket.getComplaint(), uploaded_file_name,fileBucket.getPriority(),"In Progress");
		try {
		if(fileBucket.getC_id()!=0) {
			complaintPojo.setComplaint_id(fileBucket.getC_id());
			System.out.println(complaintPojo);
			ocrsService.updateComplaint(complaintPojo);
			
		}
		else {
			ocrsService.postComplaint(complaintPojo);
			Notifications notification_ob=new Notifications(complaintPojo.getP_code(), "Complaint added in "+fileBucket.getP_id(), url.split("WEB-INF")[0]+"getComplaintStationID?userName="+complaintPojo.getUsername(), "");
			System.out.println("url"+url.split("WEB-INF")[0]+"getComplaintStationID?userName="+complaintPojo.getUsername());
			ocrsService.addNotification(notification_ob);
		}
		mv.setViewName("complaint_details");
		return mv;
		}
		catch (Exception e) {
			mv.addObject("message","Attcahed file must not be greater than 4MB!!");
			mv.setViewName("complaint_details");
			return mv;
		}

	}


	@RequestMapping("/download/{fileName:.+}")
	public void downloader(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("fileName") String fileName) {
		try {
			System.out.println("inside download");
			// String downloadFolder = context.getRealPath("/WEB-INF/downloads");
			String downloadFolder=UPLOAD_LOCATION;
			File file = new File(downloadFolder + File.separator + fileName);

			if (file.exists()) {
				String mimeType = context.getMimeType(file.getPath());

				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}

				response.setContentType(mimeType);
				response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
				response.setContentLength((int) file.length());

				OutputStream os = response.getOutputStream();
				FileInputStream fis = new FileInputStream(file);
				byte[] buffer = new byte[4096];
				int b = -1;

				while ((b = fis.read(buffer)) != -1) {
					os.write(buffer, 0, b);
				}

				fis.close();
				os.close();
			} else {
				System.out.println("Requested " + fileName + " file not found!!");
			}
		} catch (IOException e) {
			System.out.println("Error:- " + e.getMessage());
		}
	}

	@RequestMapping("updateCompliantRequest")
	public ModelAndView updateCompliant(HttpServletRequest rq, HttpServletResponse rs) {
		int c_id=Integer.parseInt(rq.getParameter("c_id"));
		ComplaintPojo complaintPojo=ocrsService.getComplaintByComplaintId(c_id);
		ModelAndView mv=new ModelAndView();
		complaintPojo.setAttached_file_path(complaintPojo.getAttached_file_path().split("_")[1]);
		mv.addObject("complaint",complaintPojo);
		mv.setViewName("add-complaint");
		//System.out.println("mail"+mail);
		return mv;
	}


	@RequestMapping("searchUser")
	public ModelAndView searchUser(HttpServletRequest rq, HttpServletResponse rs) {
		String pattern=rq.getParameter("user_pattern");
		List<UserPojo> users=ocrsService.getUsersByPattern(pattern);
		int size=users.size();
		ModelAndView mv=new ModelAndView();
		int limit=10;
		if(rq.getParameter("isPolice") != null) {
			limit=6;
			mv.setViewName("update_user_to_police");
		}
		else
			mv.setViewName("show_users");
		if(users.size()==0)
			users=null;
		else {

			int id=0;
			if(rq.getParameter("id")!=null)
				id=Integer.parseInt(rq.getParameter("id"));

			if(id!=0)
				users=ocrsService.limitRecords(id,users, limit);
			else
				users=ocrsService.limitRecords(1,users, limit);
		}
		mv.addObject("size",size);
		mv.addObject("removed",0);
		mv.addObject("pattern",pattern);

		mv.addObject("users",users);

		return mv;

	}

	@GetMapping("viewAllUser")
	public ModelAndView searchAllUser(HttpServletRequest rq, HttpServletResponse rs) {
		List<UserPojo> users=ocrsService.getAllUsers();
		int size=users.size();
		ModelAndView mv=new ModelAndView();
		if(users.size()==0)
			users=null;
		else {

			int id=0;
			if(rq.getParameter("id")!=null)
				id=Integer.parseInt(rq.getParameter("id"));

			if(id!=0)
				users=ocrsService.limitRecords(id,users, 10);
			else
				users=ocrsService.limitRecords(1,users, 10);
		}
		mv.addObject("users",users);
		mv.addObject("size",size);
		mv.addObject("removed",1);
		mv.setViewName("show_users");
		return mv;

	}

	@RequestMapping("deleteUser")
	public ModelAndView deleteUser(HttpServletRequest rq, HttpServletResponse rs) {
		String username=rq.getParameter("user_name");
		ModelAndView mv=new ModelAndView();
		
		ocrsService.deleteUser(username);
		ocrsService.deleteNotificationByUsername(username);
		mv.addObject("message","deleted '"+username+"' account successfully!");
		mv.setViewName("manage_user");
		return mv;

	}

	@GetMapping("makeAsPolice")
	public ModelAndView makeAsPolice(HttpServletRequest rq, HttpServletResponse rs) {
		List<UserPojo> users=ocrsService.getAllUserWhoAreNotPolice();
		ModelAndView mv=new ModelAndView();
		int size=users.size();
		if(users.size()==0)
			users=null;
		else {

			int id=0;
			if(rq.getParameter("id")!=null)
				id=Integer.parseInt(rq.getParameter("id"));

			if(id!=0)
				users=ocrsService.limitRecords(id,users, 6);
			else
				users=ocrsService.limitRecords(1,users, 6);
		}
		mv.addObject("size",size);
		mv.addObject("removed",1);
		mv.addObject("users",users);
		mv.setViewName("update_user_to_police");
		return mv;

	}

	@RequestMapping("updateUserToPolice")
	public ModelAndView updateUserToPolice(HttpServletRequest rq, HttpServletResponse rs) {
		String username=rq.getParameter("user_name");
		String p_id=rq.getParameter("p_id");
		System.out.println("p_id:"+p_id);
		ocrsService.addPolice(username,p_id);

		List<PolicePojo> polices=ocrsService.getAllPolice();
		ModelAndView mv=new ModelAndView();
		mv.addObject("size",polices.size());
		if(polices.size()==0)
			polices=null;

		polices=ocrsService.limitRecords(1,polices, 9);
		mv.addObject("polices",polices);

		mv.setViewName("show_all_police");
		return mv;
	}

	@GetMapping("viewAllPolice")
	public ModelAndView viewAllPolice(HttpServletRequest rq, HttpServletResponse rs) {
		List<PolicePojo> polices=ocrsService.getAllPolice();
		int size=polices.size();
		if(polices.size()==0)
			polices=null;
		else {

			int id=0;
			if(rq.getParameter("id")!=null)
				id=Integer.parseInt(rq.getParameter("id"));

			if(id!=0)
				polices=ocrsService.limitRecords(id,polices, 9);
			else
				polices=ocrsService.limitRecords(1,polices, 9);
		}

		ModelAndView mv=new ModelAndView();
		mv.addObject("size",size);
		mv.addObject("polices",polices);
		mv.setViewName("show_all_police");
		return mv;

	}

	@RequestMapping("deletePolice")
	public ModelAndView deletePolice(HttpServletRequest rq, HttpServletResponse rs) {
		String username=rq.getParameter("user_name");
		ocrsService.deletePolice(username);
		if(rq.getParameter("noti_id")!=null) {
		int noti_id=Integer.parseInt(rq.getParameter("noti_id"));
		ocrsService.deteleNotificationById(noti_id);
		}
		List<PolicePojo> polices=ocrsService.getAllPolice();
		ModelAndView mv=new ModelAndView();
		mv.addObject("size",polices.size());
		if(polices.size()==0)
			polices=null;
		else {
			polices=ocrsService.limitRecords(1,polices, 9);
		}


		mv.addObject("polices",polices);
		mv.setViewName("show_all_police");
		return mv;

	}

	@RequestMapping("/viewAllRegisteredComplaints")
	public ModelAndView viewAllRegisteredComplaints(HttpServletRequest rq, HttpServletResponse rs) {
		List<ComplaintPojo> complaints=ocrsService.getAllComplaints(" ");
		ModelAndView mv=new ModelAndView();
		System.out.println("size"+complaints.size());
		mv.addObject("size",complaints.size());
		int id=0;
		if(rq.getParameter("id")!=null)
			id=Integer.parseInt(rq.getParameter("id"));
		int size=complaints.size();
		if(id!=0)
			complaints=ocrsService.limitRecords(id,complaints, 8);
		else
			complaints=ocrsService.limitRecords(1,complaints, 8);


		mv.addObject("complaints",complaints);
		mv.setViewName("view_all_complaint");
		return mv;
	}

	@RequestMapping("expandComplaint")
	public ModelAndView expandComplaint(HttpServletRequest rq, HttpServletResponse rs) {
		int c_id=Integer.parseInt(rq.getParameter("c_id"));
		ModelAndView mv=new ModelAndView();
		if(rq.getParameter("noti_id")!=null) {
			int noti_id=Integer.parseInt(rq.getParameter("noti_id"));
			mv.addObject("noti_id",noti_id);
		}
		ComplaintPojo complaintPojo=ocrsService.getComplaintByComplaintId(c_id);
		
		List<CommentPojo> comments=ocrsService.getAllCommentsByCompliantId(c_id);
		if(comments.size()>0) {
		mv.addObject("isComment",1);
		}
		
		mv.addObject("complaint",complaintPojo);
		mv.setViewName("expanded_complaint");
		return mv;
	}

	@RequestMapping("updateComplaintStatus")
	public ModelAndView updateComplaintStatus(HttpServletRequest rq, HttpServletResponse rs) {
		int c_id=Integer.parseInt(rq.getParameter("c_id"));
		ModelAndView mv=new ModelAndView();
		int noti_id=0;
		if(rq.getParameter("noti_id")!=null) {
			noti_id=Integer.parseInt(rq.getParameter("noti_id"));
		}
		String status=rq.getParameter("status");
		ocrsService.updateComplaintStatus(c_id,status);
		ComplaintPojo complaintPojo=ocrsService.getComplaintByComplaintId(c_id);
		List<CommentPojo> comments=ocrsService.getAllCommentsByCompliantId(c_id);
		if(comments.size()>0) {
			mv.addObject("isComment",1);
		}
		if(status.equals("Completed") || status.equals("Rejected")) {
			ocrsService.deteleNotificationById(noti_id);
			Notifications notifications=new Notifications(complaintPojo.getUsername(), "Your complaint "+c_id+" status is Completed/Rejected", "", "message");
			ocrsService.addNotification(notifications);
		}
		mv.addObject("complaint",complaintPojo);
		mv.setViewName("expanded_complaint");
		return mv;
	}

	@RequestMapping("getComplaintStationID")
	public ModelAndView getComplaintStationID(HttpServletRequest rq, HttpServletResponse rs) {
		String username=rq.getParameter("userName");
		ModelAndView mv=new ModelAndView();
		PolicePojo policePojo=ocrsService.getPoliceByUsername(username);
		if(rq.getParameter("noti_id")!=null) {
			int noti_id=Integer.parseInt(rq.getParameter("noti_id"));
			mv.addObject("noti_id",noti_id);
		}
		
		List<ComplaintPojo> complaints=ocrsService.getComplaintsByStationId(policePojo.getP_id());
		if(complaints.size()==0)
			complaints=null;
		

		mv.addObject("size",complaints.size());
		int id=0;
		if(rq.getParameter("id")!=null)
			id=Integer.parseInt(rq.getParameter("id"));
		if(id!=0)
			complaints=ocrsService.limitRecords(id,complaints, 8);
		else
			complaints=ocrsService.limitRecords(1,complaints, 8);
		mv.addObject("ByS_id",100);
		mv.addObject("complaints",complaints);
		mv.addObject("remove_op", 1);
		mv.setViewName("view_all_complaint");
		return mv;
	}

	@RequestMapping("getComplaintByStationID")
	public ModelAndView getComplaintByStationID(HttpServletRequest rq, HttpServletResponse rs) {
		String p_id=rq.getParameter("p_id");
		List<ComplaintPojo> complaints=ocrsService.getComplaintsByStationId(p_id);
		ModelAndView mv=new ModelAndView();
		if(complaints.size()==0) {
			complaints=null;
		}
		else {

			int id=0;
			if(rq.getParameter("id")!=null)
				id=Integer.parseInt(rq.getParameter("id"));
			int size=complaints.size();
			if(id!=0)
				complaints=ocrsService.limitRecords(id,complaints, 8);
			else
				complaints=ocrsService.limitRecords(1,complaints, 8);

			mv.addObject("size",size);

			mv.addObject("ByS_id",100);
		}
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
	public ModelAndView deactivateUser(HttpServletRequest rq, HttpServletResponse rs) {
		ModelAndView mv=new ModelAndView();
		String username=rq.getParameter("userName");
		boolean ispolice=ocrsService.isPolice(username);
		if(ispolice) {
			mv.addObject("message","Unable to delete police, message has been sent to admin approval ");
			if(rq.getParameter("noti_location") != null) {
				String noti_location=rq.getParameter("noti_location");
				noti_location=noti_location.split("WEB-INF")[0]+"showCitizen?user_name="+noti_location.split("=")[1];
				System.out.println(noti_location);
				String notification= username+" requesting you to remove the police account";
				String notification_status="unread";
				Notifications notification_ob=new Notifications("admin", notification, noti_location, notification_status);
				ocrsService.addNotification(notification_ob);
				Notifications notification_ob2=new Notifications(noti_location.split("=")[1], "Police account has been removed, u can deactivate your account now", "", notification_status);
				ocrsService.addNotification(notification_ob2);
				
			}
			mv.setViewName("home");
		}
		else {
			ocrsService.deleteUser(username);
			ocrsService.deleteNotificationByUsername(username);
			mv.addObject("message","Account has been removed");
			mv.setViewName("login");
		}
		
		return mv;
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
		List<CommentPojo> comments=ocrsService.getAllCommentsByCompliantId(c_id);
		ModelAndView mv=new ModelAndView();
		mv.addObject("complaint",complaintPojo);
		mv.addObject("comments",comments);
		if(comments.size()>0) {
			mv.addObject("isComment",1);
			}
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
		if(rq.getParameter("noti_url")!=null) {
		String noti_location=rq.getParameter("noti_url").split("WEB-INF")[0]+"expandComplaint?c_id="+complaint_id;
		String notification="Comment has been added in complaint";
		Notifications notifications=new Notifications(complaintPojo.getUsername(), notification, noti_location, "unread");
		System.out.println("username"+username+" :complaintPojo.getUsername())"+complaintPojo.getUsername());
		if(!username.equals(complaintPojo.getUsername())) {
			ocrsService.addNotification(notifications);
		}
		}
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("complaint",complaintPojo);
		mv.addObject("comments",comments);
		if(comments.size()>0) {
			mv.addObject("isComment",1);
			}
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
		if(comments.size()>0) {
		mv.addObject("isComment",1);
		}
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
		if((Integer.parseInt(rq.getParameter("flag")))!=1) {
			complaints=ocrsService.getAllComplaints(uname);
			System.out.println(complaints.size()+"size");
			mv.setViewName("view_all_my_complaint");
		}
		else {
			complaints=ocrsService.getAllComplaints(" ");
			mv.setViewName("view_all_complaint");
		}
		mv.addObject("complaints",complaints);
		mv.addObject("size",complaints.size());

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
