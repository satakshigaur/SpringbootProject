package com.micro.springboot.learning.bean;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class User {

	@NotNull(message = "First Name cannot be null")
	private String firstName;
	
	private String lastName;
	
	@Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 80, message = "Age should not be more than 80")
	private int age;
	
	@NotNull(message = "Email Id cannot be null")
	@Email(message = "Email Id should be valid")
	private String emailId;
	
	private int userId;
	
	public User(int userId, String firstName, String lastName, int age, String emailId) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.emailId = emailId;
	}

	public User() {
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
