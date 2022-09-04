package com.micro.springboot.learning.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class LoginIdExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8483970077134598792L;

	public LoginIdExistsException(String loginId) {
		super("Another user exists with same login Id : "+loginId+" .Please choose another login Id");
	}

}
