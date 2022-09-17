package com.sample.user.service;

import java.util.List;

import com.sample.user.model.CreateUserRequest;
import com.sample.user.model.UpdateUserRequest;
import com.sample.user.model.User;


public interface UserService {
	
	User getUserDetails(int userId);
	
	User createUser(CreateUserRequest user);
	
	User updateUser(UpdateUserRequest user, int userId);
	
	boolean deleteUser(int userId);

	List<User> getAllUsersDetails();

}
