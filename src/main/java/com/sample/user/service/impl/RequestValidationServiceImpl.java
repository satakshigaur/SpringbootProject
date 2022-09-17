package com.sample.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.user.exception.InvalidRequestException;
import com.sample.user.model.CreateUserRequest;
import com.sample.user.model.UpdateUserRequest;
import com.sample.user.repository.UserInfoRepository;
import com.sample.user.service.RequestValidationService;

@Service
public class RequestValidationServiceImpl implements RequestValidationService {
	
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
