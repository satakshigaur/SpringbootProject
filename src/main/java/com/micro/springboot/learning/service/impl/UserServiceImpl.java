package com.micro.springboot.learning.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micro.springboot.learning.dao.UserDAO;
import com.micro.springboot.learning.entity.UserInfoEntity;
import com.micro.springboot.learning.exception.GenericException;
import com.micro.springboot.learning.exception.UserNotFoundException;
import com.micro.springboot.learning.model.CreateUserRequest;
import com.micro.springboot.learning.model.UpdateUserRequest;
import com.micro.springboot.learning.model.User;
import com.micro.springboot.learning.repository.UserInfoRepository;
import com.micro.springboot.learning.service.UserService;
import com.micro.springboot.learning.util.ValueMapper;

@Service
public class UserServiceImpl implements UserService {
	/*
	@Autowired
	UserDAO userDao;
	
	@Override
	public User getUserDetails(int userId) {
		User user = userDao.getUserInfoByUserId(userId);
		if(null == user) {
			throw new UserNotFoundException(userId);
		}
		return user;
	}

	@Override
	public User createUser(User user) {
		boolean result = userDao.addUser(user);
		if(result) {
			return user;
		}
		return null;
	}

	@Override
	public User updateUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public boolean deleteUser(int userId) {
		boolean deleted = userDao.deleteUser(userId);
		return deleted;
	}
	*/
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
			System.out.println("Unable to save user info");
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
			System.out.println("Unable to udpate user info");
			throw new GenericException();
		}
		return user;
	}

	@Override
	public boolean deleteUser(int userId) {
		Optional<UserInfoEntity> userInfo = userInfoRepository.findById(userId);
		if(userInfo.isPresent()) {
			userInfoRepository.delete(userInfo.get());
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
			System.out.println("no users found");
		}
		return userList;
	}

}
