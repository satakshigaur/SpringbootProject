package com.sample.user.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateUserRequest {

	@NotNull(message = "First Name is mandatory")
	private String firstName;
	
	private String lastName;
	
	@Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 80, message = "Age should not be more than 80")
	@NotNull(message = "Age is mandatory")
	private int age;
	
	@NotNull(message = "Email Id is mandatory")
	@Email(message = "Email Id should be valid")
	private String emailId;
	
	public CreateUserRequest( String firstName, String lastName, int age, String emailId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.emailId = emailId;
	}

	public CreateUserRequest() {
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

	@Override
	public String toString() {
		return "CreateUserRequest [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", emailId="
				+ emailId + "]";
	}

}
