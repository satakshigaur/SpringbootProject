package com.sample.user.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {

	private String firstName;
	private String lastName;
	
	@Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 80, message = "Age should not be more than 80")
	private int age;
	
	@Email(message = "Email Id should be valid")
	private String emailId;
	
}
