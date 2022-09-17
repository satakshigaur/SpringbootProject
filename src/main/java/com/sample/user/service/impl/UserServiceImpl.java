package com.sample.user.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.user.entity.UserInfoEntity;
import com.sample.user.exception.GenericException;
import com.sample.user.exception.UserNotFoundException;
import com.sample.user.model.CreateUserRequest;
import com.sample.user.model.UpdateUserRequest;
import com.sample.user.model.User;
import com.sample.user.repository.UserInfoRepository;
import com.sample.user.service.UserService;
import com.sample.user.util.ValueMapper;

@Service
public class UserServiceImpl implements UserService {
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserInfoRepository userInfoRepository;

	@Override
	public User getUserDetails(int userId) {
		User user = null;
		try {
			UserInfoEntity userInfo = userInfoRepository.findById(Integer.valueOf(userId)).get();
			if(userInfo == null) {
				throw new UserNotFoundException(userId);
			}else {
				user = ValueMapper.mapUserInfoEntityToUser(userInfo);
			}
		}catch(NoSuchElementException nse) {
			throw new UserNotFoundException(userId);
		}
		return user;
	}

	@Override
	public User createUser(CreateUserRequest userRequest) {
		User user = null;
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo = ValueMapper.mapUserToUserInfoEntity(userRequest,userInfo);
		try {
			userInfoRepository.save(userInfo);
			user = ValueMapper.mapUserInfoEntityToUser(userInfo);
		}catch(Exception e) {
			logger.error("Exception occurred while saving user ", e);
			throw new GenericException();
		}
		return user;
	}

	@Override
	public User updateUser(UpdateUserRequest userRequest, int userId) {
		User user = null;

		try {
			Optional<UserInfoEntity> userInfo = userInfoRepository.findById(userId);
			if(userInfo.isPresent()) {
				UserInfoEntity userInfoEntity = userInfo.get();
				if(userRequest.getFirstName()!=null) {
					userInfoEntity.setFirstName(userRequest.getFirstName());
				}
				if(userRequest.getLastName()!=null) {
					userInfoEntity.setLastName(userRequest.getLastName());
				}
				if(userRequest.getEmailId()!=null) {
					userInfoEntity.setEmailId(userRequest.getEmailId());
				}
				if((Integer)userRequest.getAge() != null) {
					userInfoEntity.setAge(userRequest.getAge());
				}
				userInfoEntity = userInfoRepository.save(userInfoEntity);
				user = ValueMapper.mapUserInfoEntityToUser(userInfoEntity);
			}else {
				throw new UserNotFoundException(userId);
			}
		}catch(UserNotFoundException nse) {
			throw nse;
		}catch(Exception e) {
			logger.error("Exception occurred while updating user ", e);
			throw new GenericException();
		}
		return user;
	}

	@Override
	public boolean deleteUser(int userId) {
		Optional<UserInfoEntity> userInfo = userInfoRepository.findById(userId);
		if(userInfo.isPresent()) {
			userInfoRepository.delete(userInfo.get());
			logger.info("User with id "+userId+ " deleted successfully");
			return true;
		}else {
			throw new UserNotFoundException(userId);
		}
	}

	@Override
	public List<User> getAllUsersDetails() {
		List<User> userList = null;
		try {
			List<UserInfoEntity> userInfo = (List<UserInfoEntity>) userInfoRepository.findAll();
			userList = ValueMapper.mapAllUserInfoEntityToUser(userInfo);
		}catch(NoSuchElementException nse) {
			logger.error("Exception occurred while getting all users ", nse);
		}
		return userList;
	}

}
