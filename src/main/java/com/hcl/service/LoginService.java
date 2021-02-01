package com.hcl.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.hcl.dao.UserDao;
import com.hcl.model.User;

@Service
public class LoginService {

	@Autowired
	UserDao dao;

	public boolean[] validateUser(String username, String password) {

		try {
			User u = dao.findById(username).get();
			if (password.equals(u.getPassword())) {
				if(u.getRole().equals("admin")) {
					return new boolean[]{true,true};
				}
				return new boolean[]{true,false};
			}
		} catch (NoSuchElementException e) {
		}

		return new boolean[]{false,false};
	}
	
	public List<User> getAllUsers(){
		List<User> allUsers = (List<User>) dao.findAll();
		return allUsers;
	}
	
	public User findUser(String username) {
		User u = dao.findById(username).get();
		return u;
	}
	
	public void updateUser(User u) {
		dao.deleteById(u.getUsername());
		dao.save(u);
	}
	
	public void deleteUser(String username) {
		dao.deleteById(username);
	}
	

}
