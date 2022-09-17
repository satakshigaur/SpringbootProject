package com.sample.user.service;

import com.sample.user.exception.InvalidRequestException;
import com.sample.user.model.CreateUserRequest;
import com.sample.user.model.UpdateUserRequest;


public interface RequestValidationService {
	
	boolean validateCreateUserRequest(CreateUserRequest user);

	boolean validateUpdateUserRequest(UpdateUserRequest user) throws InvalidRequestException;

}
