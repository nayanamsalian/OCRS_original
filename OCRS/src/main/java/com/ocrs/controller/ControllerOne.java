package com.ocrs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ocrs.pojo.ComplaintPojo;
import com.ocrs.pojo.Notifications;
import com.ocrs.pojo.PolicePojo;
import com.ocrs.pojo.UserPojo;
import com.service.OcrsService;

@Controller
public class ControllerOne {

	@Autowired
	OcrsService ocrsService;

	@GetMapping("/")
	public ModelAndView showHome() {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("home");
		return mv;
	}

	// add request mapping for /leaders

	@GetMapping("/publicComplaints")
	public String showLeaders() {

		return "public-complaints";
	}

	// add request mapping for /systems

	@GetMapping("/manageUser")
	public String showManageUserPage() {

		return "manage_user";
	}

	@GetMapping("/managePolice")
	public String showManagePolicePage() {

		return "manage_police";
	}

	@RequestMapping("/showRegistrationForm")
	public ModelAndView showRegistrationForm(HttpServletRequest rq, HttpServletResponse rs) {
		String adminName=rq.getParameter("adminName");
		String police=rq.getParameter("police");
		System.out.println("Police:"+police);
		ModelAndView mv=new ModelAndView();
		mv.addObject("adminName",adminName);
		mv.addObject("police",police);
		mv.setViewName("registration-form");
		return mv;

	}

	@GetMapping("/complaintDetails")
	public String showComplaintDetailsPage()
	{
		return "complaint_details";
	}


	@RequestMapping("/myNotifications")
	public ModelAndView myNotificationsForm(HttpServletRequest rq, HttpServletResponse rs) {
		String username=rq.getParameter("userName");
		ModelAndView mv=new ModelAndView();
		
		List<Notifications> notifications=ocrsService.getValidNotofications(username);
		if(notifications == null)
			mv.addObject("message", "You have 0 notifications.");
		else {
			mv.addObject("message", "You have "+notifications.size()+" notifications.");
			mv.addObject("notifications",notifications);
		}

		mv.setViewName("home");
		return mv;
	}

	@RequestMapping("/deleteThisNotification")
	public ModelAndView deleteThisNotifications(HttpServletRequest rq, HttpServletResponse rs) {
		String username=rq.getParameter("userName");
		ModelAndView mv=new ModelAndView();
		if(rq.getParameter("noti_id")!=null) {
			int noti_id=Integer.parseInt(rq.getParameter("noti_id"));
			ocrsService.deteleNotificationById(noti_id);
		}
		List<Notifications> notifications=ocrsService.getValidNotofications(username);
		if(notifications == null)
			mv.addObject("message", "You have 0 notifications.");
		else {
			mv.addObject("message", "You have "+notifications.size()+" notifications.");
			mv.addObject("notifications",notifications);
		}

		mv.setViewName("home");
		return mv;
	}

	@RequestMapping("/showCitizen")
	public ModelAndView showCitizen(HttpServletRequest rq, HttpServletResponse rs) {
		String username=rq.getParameter("user_name");
		String noti_id=rq.getParameter("noti_id");
		ModelAndView mv=new ModelAndView();
		UserPojo userPojo=ocrsService.getUserByUserName(username);
		PolicePojo policePojo=ocrsService.getPoliceByUsername(username);
		if(policePojo != null) {
			mv.addObject("isPolice",1);
			mv.addObject(policePojo);
		}
		mv.addObject("noti_id",noti_id);
		mv.addObject(userPojo);
		mv.setViewName("show_citizen");
		return mv;
	}

}










