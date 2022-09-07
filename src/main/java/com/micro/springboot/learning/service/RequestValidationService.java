package com.micro.springboot.learning.service;

import com.micro.springboot.learning.exception.InvalidRequestException;
import com.micro.springboot.learning.model.CreateUserRequest;
import com.micro.springboot.learning.model.UpdateUserRequest;


public interface RequestValidationService {
	
	boolean validateCreateUserRequest(CreateUserRequest user);

	boolean validateUpdateUserRequest(UpdateUserRequest user) throws InvalidRequestException;

	boolean checkIfUserIdExists(int userId);

}
