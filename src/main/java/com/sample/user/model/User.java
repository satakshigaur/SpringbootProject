package com.sample.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class User {

	private String firstName;
	private String lastName;
	private int age;
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

}
