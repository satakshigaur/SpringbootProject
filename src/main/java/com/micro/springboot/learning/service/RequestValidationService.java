package com.micro.springboot.learning.service;

import com.micro.springboot.learning.bean.User;
import com.micro.springboot.learning.exception.InvalidRequestException;


public interface RequestValidationService {
	
	boolean validateCreateUserRequest(User user);

	boolean validateUpdateUserRequest(User user) throws InvalidRequestException;

	void checkForExistingUserByLoginId(String loginId);

	void checkForExistingUserByUserId(String userId);

}
