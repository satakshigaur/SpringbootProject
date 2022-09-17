package com.sample.user.controller;

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

import com.sample.user.exception.GenericException;
import com.sample.user.exception.InvalidRequestException;
import com.sample.user.model.CreateUserRequest;
import com.sample.user.model.UpdateUserRequest;
import com.sample.user.model.User;
import com.sample.user.service.RequestValidationService;
import com.sample.user.service.UserService;

@RestController
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
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
			throw new GenericException();
		}
		return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
		
	}
	
	@PutMapping(path="users/updateUser/{userId}")
	public ResponseEntity<User> updateUser(@Valid @RequestBody UpdateUserRequest userRequest, @PathVariable int userId){
		
		try {
			requestValidationService.validateUpdateUserRequest(userRequest);
		}catch(InvalidRequestException ire) {
			throw ire;
		}
		User updatedUser = userService.updateUser(userRequest, userId);
		if(updatedUser == null) {
			throw new GenericException();
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
