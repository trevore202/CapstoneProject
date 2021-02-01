package com.hcl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.dao.UserDao;
import com.hcl.model.User;

@Service
public class RegistrationService {

	@Autowired
	UserDao dao;
	
	public boolean addUser(String username, String password, String role, String fname,
			String lname, String address, String email) {
		if (dao.findById(username).isPresent()) {
			return false;
		}
		try{
			User u = new User(username, password, role, fname, lname, address, email);
			dao.save(u);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
}