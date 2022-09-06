package com.micro.springboot.learning.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micro.springboot.learning.bean.User;
import com.micro.springboot.learning.dao.UserDAO;
import com.micro.springboot.learning.entity.UserInfoEntity;
import com.micro.springboot.learning.exception.InvalidRequestException;
import com.micro.springboot.learning.exception.LoginIdExistsException;
import com.micro.springboot.learning.exception.UserNotFoundException;
import com.micro.springboot.learning.repository.UserInfoRepository;
import com.micro.springboot.learning.service.RequestValidationService;

@Service
public class RequestValidationServiceImpl implements RequestValidationService {
	
	@Autowired
	UserDAO userDao;
	
	@Autowired
	UserInfoRepository userInfoRepository;

	@Override
	public boolean validateCreateUserRequest(User user) throws InvalidRequestException {
		if(user == null) {
			throw new InvalidRequestException("Invalid Request");
		}
		
		if(checkIfUserIdExists(user.getUserId())) {
			throw new InvalidRequestException("Another user exists with this user Id. "
					+ "Please remove user Id from request. It will be auto generated");
		}
		
		if(user.getFirstName() == null || user.getFirstName().isEmpty()) {
			throw new InvalidRequestException("First Name is mandatory");
		}
		
		return true;
	}
	
	@Override
	public boolean checkIfUserIdExists(int userId) {
//		return userDao.getUserInfoByUserId(userId);
		Optional<UserInfoEntity> userInfo = userInfoRepository.findById(userId);
		if(userInfo.isPresent()) {
			return true;
		}else {
			return false;
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
