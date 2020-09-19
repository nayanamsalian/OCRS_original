package com.ocrs.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private String TMP_FOLDER = "/tmp"; 
    private int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;
    
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { AppConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	
	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		registration.setMultipartConfig(getMultipartConfigElement());
	}

	private MultipartConfigElement getMultipartConfigElement() {
		MultipartConfigElement multipartConfigElement = new MultipartConfigElement(	LOCATION, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
		return multipartConfigElement;
	}

	private static final String LOCATION = "E:\\ws\\upload\\"; // Temporary location where files will be stored

	private static final long MAX_FILE_SIZE = 5242880; // 5MB : Max file size.
														// Beyond that size spring will throw exception.
	private static final long MAX_REQUEST_SIZE = 20971520; // 20MB : Total request size containing Multi part.
	
	private static final int FILE_SIZE_THRESHOLD = 0; // Size threshold after which files will be written to disk
	
	/*
	 * @Override public void onStartup(ServletContext sc) throws ServletException {
	 * ServletRegistration.Dynamic appServlet = sc.addServlet("mvc", new
	 * DispatcherServlet( new GenericWebApplicationContext()));
	 * 
	 * appServlet.setLoadOnStartup(1);
	 * 
	 * MultipartConfigElement multipartConfigElement = new
	 * MultipartConfigElement(TMP_FOLDER, MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE * 2,
	 * MAX_UPLOAD_SIZE / 2);
	 * 
	 * appServlet.setMultipartConfig(multipartConfigElement); }
	 */ 

}






