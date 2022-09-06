package com.micro.springboot.learning.service;

import java.util.List;

import com.micro.springboot.learning.bean.User;


public interface UserService {
	
	User getUserDetails(int userId);
	
	User createUser(User user);
	
	User updateUser(User user);
	
	boolean deleteUser(int userId);

	List<User> getAllUsersDetails();

}
