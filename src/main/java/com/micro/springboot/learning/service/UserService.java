package com.micro.springboot.learning.service;

import com.micro.springboot.learning.bean.User;


public interface UserService {
	
	User getUserDetails(String userId);
	
	User createUser(User user);
	
	User updateUser(User user);
	
	boolean deleteUser(String userId);

}
