package com.micro.springboot.learning.util;

import java.util.ArrayList;
import java.util.List;

import com.micro.springboot.learning.entity.UserInfoEntity;
import com.micro.springboot.learning.model.CreateUserRequest;
import com.micro.springboot.learning.model.User;

public class ValueMapper {

	public static User mapUserInfoEntityToUser(UserInfoEntity userInfo) {
		User user = new User();
		user.setUserId(userInfo.getId());
		user.setFirstName(userInfo.getFirstName());
		user.setLastName(userInfo.getLastName());
		user.setAge(userInfo.getAge());
		user.setEmailId(userInfo.getEmailId());
		return user;
	}

	public static UserInfoEntity mapUserToUserInfoEntity(CreateUserRequest user, UserInfoEntity userInfo) {
		userInfo.setFirstName(user.getFirstName());
		userInfo.setLastName(user.getLastName());
		userInfo.setAge(user.getAge());
		userInfo.setEmailId(user.getEmailId());
		return userInfo;
	}

	public static List<User> mapAllUserInfoEntityToUser(List<UserInfoEntity> userInfo) {
		List<User> userList = new ArrayList<>();
		for(UserInfoEntity u : userInfo) {
			User user = mapUserInfoEntityToUser(u);
			userList.add(user);
		}
		return userList;
	}

}
