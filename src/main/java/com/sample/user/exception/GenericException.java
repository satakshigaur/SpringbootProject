package com.sample.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GenericException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8483970077134598792L;

	public GenericException() {
		super("Some error occurred while performing operation. Please try again later");
	}

}
