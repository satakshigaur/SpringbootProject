package com.micro.springboot.learning.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micro.springboot.learning.bean.User;
import com.micro.springboot.learning.dao.UserDAO;
import com.micro.springboot.learning.exception.InvalidRequestException;
import com.micro.springboot.learning.exception.LoginIdExistsException;
import com.micro.springboot.learning.exception.UserNotFoundException;
import com.micro.springboot.learning.service.RequestValidationService;

@Service
public class RequestValidationServiceImpl implements RequestValidationService {
	
	@Autowired
	UserDAO userDao;

	@Override
	public boolean validateCreateUserRequest(User user) throws InvalidRequestException {
		if(user == null) {
			throw new InvalidRequestException("Invalid Request");
		}
		checkForExistingUserByUserId(user.getUserId());
		checkForExistingUserByLoginId(user.getLoginId());
		
		if(user.getFirstName() == null || user.getFirstName().isEmpty()) {
			throw new InvalidRequestException("First Name is mandatory");
		}
		
		return true;
	}
	
	@Override
	public void checkForExistingUserByUserId(String userId) {
		User existingUser = userDao.getUserInfoByUserId(userId);
		if(null != existingUser) {
			throw new InvalidRequestException("Another user exists with this user Id. Please change user Id");
		}
		
	}

	@Override
	public void checkForExistingUserByLoginId(String loginId) {

		User existingUser = userDao.getUserInfoByLoginId(loginId);
		if(null != existingUser) {
			throw new LoginIdExistsException(loginId);
		}
	}

	@Override
	public boolean validateUpdateUserRequest(User user) throws InvalidRequestException {
		if(user == null) {
			throw new InvalidRequestException("Invalid Request");
		}
		
		return true;
	}
	
	

}
