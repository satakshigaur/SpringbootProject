package com.micro.springboot.learning.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micro.springboot.learning.bean.User;
import com.micro.springboot.learning.dao.UserDAO;
import com.micro.springboot.learning.entity.UserInfoEntity;
import com.micro.springboot.learning.exception.UserNotFoundException;
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
	public User createUser(User user) {
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo = ValueMapper.mapUserToUserInfoEntity(user,userInfo);
		try {
			userInfoRepository.save(userInfo);
			user.setUserId(userInfo.getId());
		}catch(Exception e) {
			System.out.println("Unable to save user info");
			return null;
		}
		return user;
	}

	@Override
	public User updateUser(User user) {
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setId(user.getUserId());
		userInfo = ValueMapper.mapUserToUserInfoEntity(user,userInfo);
		try {
			userInfoRepository.save(userInfo);
		}catch(Exception e) {
			System.out.println("Unable to update user info");
			return null;
		}
		return user;
	}

	@Override
	public boolean deleteUser(int userId) {
		UserInfoEntity userInfo = userInfoRepository.findById(Integer.valueOf(userId)).get();
		try {
			userInfoRepository.delete(userInfo);
		}catch(Exception e) {
			System.out.println("Unable to update user");
			return false;
		}
		
		return true;
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
