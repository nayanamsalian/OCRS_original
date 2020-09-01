package com.ocrs.pojo;



public class UserPojo {
	private String userName;

	private String password;
	
	private String gender;

	private String firstName;

	private String lastName;

	private String email;
	
	public UserPojo() {	}

	public UserPojo(String userName, String password, String firstName,
			String lastName, String gender,  String email) {
		super();
		this.userName = userName;
		this.password = password;
		this.gender = gender;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserPojo [userName=" + userName + ", gender=" + gender + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}
	
	

}
