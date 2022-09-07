package com.micro.springboot.learning.service;

import java.util.List;

import com.micro.springboot.learning.model.CreateUserRequest;
import com.micro.springboot.learning.model.UpdateUserRequest;
import com.micro.springboot.learning.model.User;


public interface UserService {
	
	User getUserDetails(int userId);
	
	User createUser(CreateUserRequest user);
	
	User updateUser(UpdateUserRequest user, int userId);
	
	boolean deleteUser(int userId);

	List<User> getAllUsersDetails();

}
