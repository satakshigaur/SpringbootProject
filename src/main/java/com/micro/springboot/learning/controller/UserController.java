package com.micro.springboot.learning.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.micro.springboot.learning.bean.User;
import com.micro.springboot.learning.dao.UserDAO;
import com.micro.springboot.learning.exception.InvalidRequestException;
import com.micro.springboot.learning.exception.UserNotFoundException;
import com.micro.springboot.learning.service.RequestValidationService;
import com.micro.springboot.learning.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserDAO userDao;
	
	@Autowired
	RequestValidationService requestValidationService;
	
	@Autowired
	UserService userService;
	
	@GetMapping(path="/users/getUser/{userId}")
	public ResponseEntity<User> getUserByUserId(@PathVariable String userId) {
		return new ResponseEntity<User>(userService.getUserDetails(userId), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.POST,path="/users/addUser")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		try {
			requestValidationService.validateCreateUserRequest(user);
		}catch(InvalidRequestException ire) {
			throw ire;
		}

		User createdUser = userService.createUser(user);
		if(createdUser == null) {
			return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
		
//		URI uri = ServletUriComponentsBuilder.fromPath("localhost:8080/users/getUser").
//				path("/{userId}").
//				buildAndExpand(user.getUserId()).
//				toUri();
//		return ResponseEntity.created(uri).build();
		
	}
	
	@DeleteMapping(path="/users/deleteUser/{userId}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable String userId) {
		requestValidationService.checkForExistingUserByUserId(userId);
		return new ResponseEntity<Boolean>(userService.deleteUser(userId),HttpStatus.OK);
	}
}
