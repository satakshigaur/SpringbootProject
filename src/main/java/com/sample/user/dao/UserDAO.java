package com.sample.user.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sample.user.model.User;

// unused class after in memory DB logic
@Component
public class UserDAO {

	private static List<User> userList = new ArrayList<>();
	
	static {
		userList.add(new User(1001,"Kurt","Weller",30,"kurt.weller@test.com"));
		userList.add(new User(1002,"Jane","Doe",29,"jane.doe@test.com"));
		userList.add(new User(1003,"Tasha","Zapata",30,"zapata.tasha@test.com"));
		userList.add(new User(1004,"Edgar","Reade",30,"reade.edgar@test.com"));
	}
	public boolean addUser(User user) {
		userList.add(user);
		return true;
	}
	
	public User getUserInfoByUserId(int userId) {
		User user = userList.stream().filter(u -> u.getUserId() == userId).findFirst().orElse(null);

		return user;
	}
	
	public User getUserInfoByLoginId(String loginId) {
		User user = userList.stream().filter(u -> u.getEmailId().equals(loginId)).findFirst().orElse(null);
		return user;
	}

	public boolean deleteUser(int userId) {
		
		Iterator<User> itr = userList.iterator();
		
		while(itr.hasNext()) {
			User u = itr.next();
			if(u.getUserId()==userId) {
				itr.remove();
				return true;
			}
		}
		return false;
	}
	
	public User updateUser(User user) {
		User existingUser = null;
		for(User u:userList) {
			if(u.getUserId()==user.getUserId()) {
				existingUser = u;
				if(user.getFirstName()!=null && !user.getFirstName().isEmpty()) {
					existingUser.setFirstName(user.getFirstName());
				}
				if(user.getLastName()!=null && !user.getLastName().isEmpty()) {
					existingUser.setLastName(user.getLastName());
				}
				existingUser.setAge(user.getAge());
				if(user.getEmailId()!=null && !user.getEmailId().isEmpty()) {
					existingUser.setEmailId(user.getEmailId());
				}
			}
		}
		return existingUser;
	}
}
