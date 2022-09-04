package com.micro.springboot.learning.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micro.springboot.learning.bean.User;
import com.micro.springboot.learning.dao.UserDAO;
import com.micro.springboot.learning.exception.UserNotFoundException;
import com.micro.springboot.learning.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDAO userDao;

	@Override
	public User getUserDetails(String userId) {
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
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(String userId) {
		boolean deleted = userDao.deleteUser(userId);
		return deleted;
	}

}
