package com.ocrs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage() {
		
		// return "plain-login";

		return "login";
		
	}
	
	// add request mapping for /access-denied
	
	@GetMapping("/access-denied")
	public ModelAndView showAccessDenied() {
		ModelAndView mv=new ModelAndView();
		String message=new String("Access denied !!");
		mv.addObject(message);
		mv.setViewName("access-denied");
		return mv;
		
	}
	
}









