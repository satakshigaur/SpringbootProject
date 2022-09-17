package com.sample.user.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sample.user.exception.InvalidRequestException;
import com.sample.user.model.CreateUserRequest;
import com.sample.user.model.UpdateUserRequest;
import com.sample.user.repository.UserInfoRepository;
import com.sample.user.service.impl.RequestValidationServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RequestValidationServiceTest {

	@InjectMocks
	RequestValidationServiceImpl requestValidationServiceImpl;
	
	@Mock
	UserInfoRepository userInfoRepository;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void validateCreateUserRequestTest_success() {
		CreateUserRequest userRequest = new CreateUserRequest();
		userRequest.setFirstName("Test");
		userRequest.setLastName("test");
		boolean isValid = requestValidationServiceImpl.validateCreateUserRequest(userRequest);
		assertTrue(isValid);
	}
	
	@Test
	public void validateCreateUserRequestTest_invalidLastName() {
		CreateUserRequest userRequest = new CreateUserRequest();
		userRequest.setFirstName("Test");
		userRequest.setLastName("");
		Throwable exception = assertThrows(InvalidRequestException.class, () -> requestValidationServiceImpl.validateCreateUserRequest(userRequest));
        assertEquals("Last Name is invalid", exception.getMessage());
	}
	
	@Test
	public void validateUpdateUserRequestTest_success() {
		UpdateUserRequest userRequest = new UpdateUserRequest();
		userRequest.setFirstName("test");
		userRequest.setLastName("test");
		userRequest.setAge(30);
		userRequest.setEmailId("test@test.com");
		boolean isValid = requestValidationServiceImpl.validateUpdateUserRequest(userRequest);
		assertTrue(isValid);
	}
	
	@Test
	public void validateUpdateUserRequestTest_invalidFirstName() {
		UpdateUserRequest userRequest = new UpdateUserRequest();
		userRequest.setFirstName("");
		Throwable exception = assertThrows(InvalidRequestException.class, () -> requestValidationServiceImpl.validateUpdateUserRequest(userRequest));
        assertEquals("First Name is invalid", exception.getMessage());
	}
	
	@Test
	public void validateUpdateUserRequestTest_invalidLastName() {
		UpdateUserRequest userRequest = new UpdateUserRequest();
		userRequest.setFirstName("test");
		userRequest.setLastName("");
		Throwable exception = assertThrows(InvalidRequestException.class, () -> requestValidationServiceImpl.validateUpdateUserRequest(userRequest));
        assertEquals("Last Name is invalid", exception.getMessage());
	}
	
	@Test
	public void validateUpdateUserRequestTest_invalidEmailId() {
		UpdateUserRequest userRequest = new UpdateUserRequest();
		userRequest.setFirstName("test");
		userRequest.setLastName("test");
		userRequest.setAge(30);
		userRequest.setEmailId("");
		Throwable exception = assertThrows(InvalidRequestException.class, () -> requestValidationServiceImpl.validateUpdateUserRequest(userRequest));
        assertEquals("Email Id is invalid", exception.getMessage());
	}
	
}
