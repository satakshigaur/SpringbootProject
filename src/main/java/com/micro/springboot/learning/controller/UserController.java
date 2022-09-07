package com.micro.springboot.learning.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.micro.springboot.learning.dao.UserDAO;
import com.micro.springboot.learning.exception.InvalidRequestException;
import com.micro.springboot.learning.model.CreateUserRequest;
import com.micro.springboot.learning.model.UpdateUserRequest;
import com.micro.springboot.learning.model.User;
import com.micro.springboot.learning.service.RequestValidationService;
import com.micro.springboot.learning.service.UserService;

@RestController
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserDAO userDao;
	
	@Autowired
	RequestValidationService requestValidationService;
	
	@Autowired
	UserService userService;
	
	@GetMapping(path="/users/getUsers")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> userList = new ArrayList<>();
		userList = userService.getAllUsersDetails();
		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
	}
	
	@GetMapping(path="/users/getUser/{userId}")
	public ResponseEntity<User> getUserByUserId(@PathVariable int userId) {
		return new ResponseEntity<User>(userService.getUserDetails(userId), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.POST,path="/users/addUser")
	public ResponseEntity<User> addUser(@Valid @RequestBody CreateUserRequest userRequest) {
		try {
			requestValidationService.validateCreateUserRequest(userRequest);
		}catch(InvalidRequestException ire) {
			throw ire;
		}

		User createdUser = userService.createUser(userRequest);
		if(createdUser == null) {
			return new ResponseEntity<User>(createdUser, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
		
	}
	
	@PutMapping(path="users/updateUser/{userId}")
	public ResponseEntity<User> updateUser(@Valid @RequestBody UpdateUserRequest userRequest, @PathVariable int userId){
		
		logger.info("Age for update is "+ userRequest.getAge());
		try {
			requestValidationService.validateUpdateUserRequest(userRequest);
		}catch(InvalidRequestException ire) {
			throw ire;
		}
		User updatedUser = userService.updateUser(userRequest, userId);
		if(updatedUser == null) {
			return new ResponseEntity<User>(new User(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
		
	}
	
	
	@DeleteMapping(path="/users/deleteUser/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable int userId) {

		if(userService.deleteUser(userId)) {
			return new ResponseEntity<String>("User Deleted Successfully",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Some error occurred while deleting user.Please try later",HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
}
