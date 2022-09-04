package com.micro.springboot.learning.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.micro.springboot.learning.bean.User;

@Component
public class UserDAO {

	private static List<User> userList = new ArrayList<>();
	
	static {
		userList.add(new User("1001","Kurt","Weller",30,"1001"));
		userList.add(new User("1002","Jane","Doe",29,"1002"));
		userList.add(new User("1003","Tasha","Zapata",30,"1003"));
		userList.add(new User("1004","Edgar","Reade",30,"1004"));
	}
	public boolean addUser(User user) {
		userList.add(user);
		return true;
	}
	
	public User getUserInfoByUserId(String userId) {
		User user = userList.stream().filter(u -> u.getUserId().equals(userId)).findFirst().orElse(null);
//		for(User u:userList) {
//			if(u.getUserId().equals(userId)) {
//				return u;
//			}
//		}
		return user;
	}
	
	public User getUserInfoByLoginId(String loginId) {
		User user = userList.stream().filter(u -> u.getLoginId().equals(loginId)).findFirst().orElse(null);
		return user;
	}

	public boolean deleteUser(String userId) {
		
		Iterator<User> itr = userList.iterator();
		
		while(itr.hasNext()) {
			User u = itr.next();
			if(u.getUserId().equals(userId)) {
				itr.remove();
				return true;
			}
		}
		return false;
	}
}
