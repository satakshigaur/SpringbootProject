package com.micro.springboot.learning.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request){
		return new ResponseEntity<Object>("User Not found",HttpStatus.NOT_FOUND);
	}
	
//	@ExceptionHandler(LoginIdExistsException.class)
//	public final ResponseEntity<Object> handleExistingLoginIdException(Exception ex, WebRequest request){
//		return new ResponseEntity<Object>("Login Id exists",HttpStatus.NOT_FOUND);
//	}
	
}
