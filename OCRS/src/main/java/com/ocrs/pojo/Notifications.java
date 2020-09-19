package com.ocrs.pojo;

public class Notifications {
	
	private int notification_id;
	private String username;
	private String notification;
	private String location;
	private String notification_status;
	
	public Notifications() {}
	
	public Notifications(int notification_id, String username, String notification, String location, String notification_status) {
		super();
		this.notification_id=notification_id;
		this.username = username;
		this.notification = notification;
		this.location = location;
		this.notification_status=notification_status;
		
	}
	
	public Notifications(String username, String notification, String location, String notification_status) {
		super();
		this.username = username;
		this.notification = notification;
		this.location = location;
		this.notification_status=notification_status;
		
	}
	public String getNotification_status() {
		return notification_status;
	}

	public void setNotification_status(String notification_status) {
		this.notification_status = notification_status;
	}

	public int getNotification_id() {
		return notification_id;
	}
	public void setNotification_id(int notification_id) {
		this.notification_id = notification_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
