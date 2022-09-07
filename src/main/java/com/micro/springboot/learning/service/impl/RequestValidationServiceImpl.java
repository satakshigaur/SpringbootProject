package com.micro.springboot.learning.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micro.springboot.learning.dao.UserDAO;
import com.micro.springboot.learning.exception.InvalidRequestException;
import com.micro.springboot.learning.model.CreateUserRequest;
import com.micro.springboot.learning.model.UpdateUserRequest;
import com.micro.springboot.learning.repository.UserInfoRepository;
import com.micro.springboot.learning.service.RequestValidationService;

@Service
public class RequestValidationServiceImpl implements RequestValidationService {
	
	@Autowired
	UserDAO userDao;
	
	@Autowired
	UserInfoRepository userInfoRepository;

	@Override
	public boolean validateCreateUserRequest(CreateUserRequest user) throws InvalidRequestException {
		
		if(user.getLastName() != null && user.getLastName().isEmpty()) {
			throw new InvalidRequestException("Last Name is invalid");
		}
		
		return true;
	}
	
	@Override
	public boolean validateUpdateUserRequest(UpdateUserRequest user) throws InvalidRequestException {
		if(user.getFirstName()!=null && user.getFirstName().isEmpty() ) {
			throw new InvalidRequestException("First Name is invalid");
		}
		if(user.getLastName() != null && user.getLastName().isEmpty()) {
			throw new InvalidRequestException("Last Name is invalid");
		}
		if(user.getEmailId()!=null && user.getEmailId().isEmpty()) {
			throw new InvalidRequestException("Email Id is invalid");
		}
		return true;
	}

}
