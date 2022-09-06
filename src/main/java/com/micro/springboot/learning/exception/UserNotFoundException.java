package com.micro.springboot.learning.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8483970077134598792L;

	public UserNotFoundException(int userId) {
		super("User not found with user id :"+userId);
	}

}
