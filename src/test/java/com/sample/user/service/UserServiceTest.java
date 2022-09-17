package com.sample.user.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sample.user.entity.UserInfoEntity;
import com.sample.user.exception.GenericException;
import com.sample.user.exception.UserNotFoundException;
import com.sample.user.model.CreateUserRequest;
import com.sample.user.model.UpdateUserRequest;
import com.sample.user.model.User;
import com.sample.user.repository.UserInfoRepository;
import com.sample.user.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	UserServiceImpl userServiceImpl;
	
	@Mock
	UserInfoRepository userInfoRepository;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getUserDetailsTest_success() {
		
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setId(111);
		userInfo.setFirstName("test");
		when(userInfoRepository.findById(Integer.valueOf(any(Integer.class)))).thenReturn(Optional.of(userInfo));
		User user = userServiceImpl.getUserDetails(111);
		assertEquals(user.getUserId(), 111);
		assertEquals(user.getFirstName(),"test");
	}
	
	@Test
	public void getUserDetailsTest_notFound() {
		
		when(userInfoRepository.findById(Integer.valueOf(any(Integer.class)))).thenReturn(Optional.empty());
		Throwable exception = assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserDetails(111));
        assertEquals("User not found with user id :111", exception.getMessage());
	}
	
	@Test
	public void getUserDetailsTest_noSuchElement() {
		
		when(userInfoRepository.findById(Integer.valueOf(any(Integer.class)))).thenThrow(new NoSuchElementException());
		Throwable exception = assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserDetails(111));
        assertEquals("User not found with user id :111", exception.getMessage());
	}
	
	@Test
	public void createUserTest_exception() {
		CreateUserRequest userRequest = new CreateUserRequest();
		userRequest.setFirstName("Test");
		userRequest.setLastName("");
		when(userInfoRepository.save(any(UserInfoEntity.class))).thenThrow(new GenericException());
		Throwable exception = assertThrows(GenericException.class, () -> userServiceImpl.createUser(userRequest));
        assertEquals("Some error occurred while performing operation. Please try again later", exception.getMessage());
	}
	
	@Test
	public void createUserTest_success() {
		CreateUserRequest userRequest = new CreateUserRequest();
		userRequest.setFirstName("Test");
		userRequest.setLastName("test");
		userRequest.setAge(20);
		userRequest.setEmailId("test@test.com");
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setId(111);
		userInfo.setFirstName("test");
		when(userInfoRepository.save(any(UserInfoEntity.class))).thenReturn(userInfo);
		User user = userServiceImpl.createUser(userRequest);
		assertTrue(user!=null);
		assertEquals(user.getUserId(), 0);
	}
	
	@Test
	public void updateUserTest_UserNotFoundException() {
		UpdateUserRequest userRequest = new UpdateUserRequest();
		userRequest.setFirstName("test");
		userRequest.setLastName("test");
		userRequest.setAge(30);
		userRequest.setEmailId("test@test.com");
		when(userInfoRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
		Throwable exception = assertThrows(UserNotFoundException.class, () -> userServiceImpl.updateUser(userRequest,111));
        assertEquals("User not found with user id :111", exception.getMessage());
	}
	
	@Test
	public void updateUserTest_Exception() {
		UpdateUserRequest userRequest = new UpdateUserRequest();
		userRequest.setFirstName("test");
		userRequest.setLastName("test");
		userRequest.setAge(30);
		userRequest.setEmailId("test@test.com");
		
		when(userInfoRepository.findById(any(Integer.class))).thenThrow(new GenericException());
		Throwable exception = assertThrows(GenericException.class, () -> userServiceImpl.updateUser(userRequest,111));
        assertEquals("Some error occurred while performing operation. Please try again later", exception.getMessage());
	}
	
	@Test
	public void updateUserTest_Success() {
		UpdateUserRequest userRequest = new UpdateUserRequest();
		userRequest.setFirstName("test");
		userRequest.setLastName("test");
		userRequest.setAge(30);
		userRequest.setEmailId("test@test.com");
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setId(111);
		userInfo.setFirstName("test");
		when(userInfoRepository.findById(any(Integer.class))).thenReturn(Optional.of(userInfo));
		when(userInfoRepository.save(any(UserInfoEntity.class))).thenReturn(userInfo);
		User user = userServiceImpl.updateUser(userRequest,111);
		assertTrue(user!=null);
		assertEquals(user.getUserId(), 111);
	}
	
	@Test
	public void deleteUserTest_UserNotFoundException() {
		
		when(userInfoRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
		Throwable exception = assertThrows(UserNotFoundException.class, () -> userServiceImpl.deleteUser(111));
        assertEquals("User not found with user id :111", exception.getMessage());
	}
	
	@Test
	public void deleteUserTest_Success() {
		UserInfoEntity userInfo = new UserInfoEntity();
		userInfo.setId(111);
		userInfo.setFirstName("test");
		when(userInfoRepository.findById(any(Integer.class))).thenReturn(Optional.of(userInfo));
		doNothing().when(userInfoRepository).delete(any(UserInfoEntity.class));
		boolean result = userServiceImpl.deleteUser(111);
		assertTrue(result);
	}
	
	@Test
	public void getAllUsersDetails_emptyList() {
		
		when(userInfoRepository.findAll()).thenThrow(new NoSuchElementException());
		List<User> result = userServiceImpl.getAllUsersDetails();
		assertTrue(result == null);
	}
	
	@Test
	public void getAllUsersDetails_Success() {
		User user1 = new User(101, "First", "User", 20,"test@test.com");
    	User user2 = new User(102, "Second", "User", 20,"test@test.com");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        
        List<UserInfoEntity> userInfoList = new ArrayList<>();
        UserInfoEntity userInfo1 = new UserInfoEntity();
		userInfo1.setId(101);
		userInfo1.setFirstName("First");
		
		UserInfoEntity userInfo2 = new UserInfoEntity();
		userInfo2.setId(102);
		userInfo2.setFirstName("Second");
		userInfoList.add(userInfo1);
		userInfoList.add(userInfo2);
        when(userInfoRepository.findAll()).thenReturn(userInfoList);
        
        List<User> resultList = userServiceImpl.getAllUsersDetails();
		assertTrue(resultList!=null);
		assertTrue(resultList.size() == 2);
	}
	
}
