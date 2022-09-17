package com.sample.user.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sample.user.exception.api_error.ErrorResponse;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request){
		ErrorResponse apiError = 
				new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getMessage());
		return new ResponseEntity<Object>(
				apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, 
			HttpHeaders headers, 
			HttpStatus status, 
			WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ErrorResponse apiError = new ErrorResponse(HttpStatus.BAD_REQUEST,"Invalid argument(s). Please resolve error messages", errors);
		return handleExceptionInternal(
				ex, apiError, headers, apiError.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers, 
			HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";

		ErrorResponse apiError = 
				new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(
				apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<Object> handleInvalidRequestParameter(Exception ex, WebRequest request) {

		ErrorResponse apiError = 
				new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getMessage());
		return new ResponseEntity<Object>(
				apiError, new HttpHeaders(), apiError.getStatus());
	}
}
