package com.sample.user.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
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
public class CreateUserRequest {

	@NotNull(message = "First Name is mandatory")
	@NotEmpty
	private String firstName;
	
	private String lastName;
	
	@NotNull(message = "Age is mandatory")
	@Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 80, message = "Age should not be more than 80")
	private int age;
	
	@NotNull(message = "Email Id is mandatory")
	@Email(message = "Email Id should be valid")
	private String emailId;
	
}
