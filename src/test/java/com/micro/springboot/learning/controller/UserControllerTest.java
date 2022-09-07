package com.micro.springboot.learning.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.micro.springboot.learning.exception.GenericException;
import com.micro.springboot.learning.exception.InvalidRequestException;
import com.micro.springboot.learning.model.CreateUserRequest;
import com.micro.springboot.learning.model.UpdateUserRequest;
import com.micro.springboot.learning.model.User;
import com.micro.springboot.learning.service.RequestValidationService;
import com.micro.springboot.learning.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@InjectMocks
    UserController userController;
     
    @Mock
    RequestValidationService requestValidationService;
    
    @Mock
    UserService userService;
    
    @Test
    public void testAddUser_userCreationSuccessful() {
    	MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        User user = new User();
        user.setUserId(1001);
		user.setFirstName("Test");
		user.setLastName("test");
		user.setAge(20);
		user.setEmailId("test@test.com");
		CreateUserRequest userRequest = new CreateUserRequest();
		userRequest.setFirstName("Test");
		userRequest.setLastName("test");
		userRequest.setAge(20);
		userRequest.setEmailId("test@test.com");
        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(user);
        ResponseEntity<User> responseEntity = userController.addUser(userRequest);
        
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getBody().getUserId()).isEqualTo(1001);
    }
    
    @Test
    public void testAddUser_invalidRequest() {
    	MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		CreateUserRequest userRequest = new CreateUserRequest();
		userRequest.setFirstName("Test");
		userRequest.setLastName(null);
		userRequest.setAge(20);
		userRequest.setEmailId("test@test.com");
        when(requestValidationService.validateCreateUserRequest(any(CreateUserRequest.class))).thenThrow(new InvalidRequestException("Last Name is invalid"));
        Throwable exception = assertThrows(InvalidRequestException.class, () -> userController.addUser(userRequest));
        assertEquals("Last Name is invalid", exception.getMessage());

    }
    
    @Test
    public void testAddUser_genericException() {
    	MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		CreateUserRequest userRequest = new CreateUserRequest();
		userRequest.setFirstName("Test");
		userRequest.setLastName("user");
		userRequest.setAge(20);
		userRequest.setEmailId("test@test.com");
		when(userService.createUser(any(CreateUserRequest.class))).thenReturn(null);
        Throwable exception = assertThrows(GenericException.class, () -> userController.addUser(userRequest));
        assertEquals("Some error occurred while performing operation. Please try again later", exception.getMessage());

    }
    
    @Test
    public void testGetAllUsers_ok() 
    {
    	User user1 = new User(101, "First", "User", 20,"test@test.com");
    	User user2 = new User(102, "Second", "User", 20,"test@test.com");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        when(userService.getAllUsersDetails()).thenReturn(userList);
 
        ResponseEntity<List<User>> users = userController.getAllUsers();
        assertThat(users.getStatusCodeValue()).isEqualTo(200);
        assertThat(users.getBody().size()).isEqualTo(2);
         
        assertThat(users.getBody().get(0).getFirstName())
                        .isEqualTo(user1.getFirstName());
        assertThat(users.getBody().get(1).getFirstName())
                        .isEqualTo(user2.getFirstName());
    }
    
    @Test
    public void testGetUserByUserId_ok() 
    {
    	User user1 = new User(101, "First", "User", 20,"test@test.com");
        when(userService.getUserDetails(101)).thenReturn(user1);
 
        ResponseEntity<User> users = userController.getUserByUserId(101);
        assertThat(users.getStatusCodeValue()).isEqualTo(200);
        assertThat(users.getBody().getFirstName())
                        .isEqualTo(user1.getFirstName());
        assertThat(users.getBody().getUserId())
                        .isEqualTo(user1.getUserId());
    }
    
    @Test
    public void testUpdateUser_Successful() {
    	MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        User user = new User();
        user.setUserId(1001);
		user.setFirstName("Changed");
		user.setLastName("test");
		user.setAge(30);
		user.setEmailId("test@test.com");
		UpdateUserRequest userRequest = new UpdateUserRequest();
		userRequest.setFirstName("Changed");
		userRequest.setLastName("test");
		userRequest.setAge(30);
		userRequest.setEmailId("test@test.com");
        when(userService.updateUser(any(UpdateUserRequest.class),any(Integer.class))).thenReturn(user);
        ResponseEntity<User> responseEntity = userController.updateUser(userRequest,1001);
        
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().getUserId()).isEqualTo(1001);
        assertThat(responseEntity.getBody().getFirstName()).isEqualTo("Changed");
        assertThat(responseEntity.getBody().getAge()).isEqualTo(30);
    }
    
    @Test
    public void testUpdateUser_invalidRequest() {
    	MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
		UpdateUserRequest userRequest = new UpdateUserRequest();
		userRequest.setFirstName("Changed");
		userRequest.setLastName("");
		userRequest.setAge(30);
		userRequest.setEmailId("test@test.com");
        
		when(requestValidationService.validateUpdateUserRequest(any(UpdateUserRequest.class))).thenThrow(new InvalidRequestException("Last Name is invalid"));
        Throwable exception = assertThrows(InvalidRequestException.class, () -> userController.updateUser(userRequest,1001));
        assertEquals("Last Name is invalid", exception.getMessage());
    }
    
    @Test
    public void testUpdateUser_genericException() {
    	MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        
		UpdateUserRequest userRequest = new UpdateUserRequest();
		userRequest.setFirstName("Changed");
		userRequest.setLastName("");
		userRequest.setAge(30);
		userRequest.setEmailId("test@test.com");
        
		when(userService.updateUser(any(UpdateUserRequest.class),any(Integer.class))).thenReturn(null);
        Throwable exception = assertThrows(GenericException.class, () -> userController.updateUser(userRequest,1001));
        assertEquals("Some error occurred while performing operation. Please try again later", exception.getMessage());
    }
    
    @Test
    public void testDeleteUser_ok() 
    {
        when(userService.deleteUser(any(Integer.class))).thenReturn(true);
 
        ResponseEntity<String> response = userController.deleteUser(0);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody())
                        .isEqualTo("User Deleted Successfully");
        
    }
    @Test
    public void testDeleteUser_error() 
    {
        when(userService.deleteUser(any(Integer.class))).thenReturn(false);
 
        ResponseEntity<String> response = userController.deleteUser(0);
        assertThat(response.getStatusCodeValue()).isEqualTo(500);
        assertThat(response.getBody())
                        .isEqualTo("Some error occurred while deleting user.Please try later");
        
    }
}
