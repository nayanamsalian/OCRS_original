package com.ocrs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ocrs.pojo.ComplaintPojo;

@Controller
public class ControllerOne {

	@GetMapping("/")
	public String showHome() {
		
		return "home";
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
	
}










