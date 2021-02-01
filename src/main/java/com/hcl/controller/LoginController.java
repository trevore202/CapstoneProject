package com.hcl.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.hcl.model.User;
import com.hcl.service.LoginService;

@RestController
@SessionAttributes("name")
public class LoginController {
	
	@Autowired
	LoginService service;
	
	@GetMapping("/login")
	public ModelAndView showLoginPage(ModelMap model){
		return new ModelAndView("login", model);
	}
	
	@PostMapping("/login")
	public ModelAndView showWelcomePage(HttpSession session, ModelMap model, @RequestParam String name, @RequestParam String password){
		boolean[] isValidUserAndIsAdmin = service.validateUser(name, password);
		
		if (!isValidUserAndIsAdmin[0]) {
			model.put("errorMessage", "Invalid Credentials");
			return new ModelAndView("login", model);
		}
		model.clear();
		session.setAttribute("name", name);
		if(isValidUserAndIsAdmin[1]) {
			session.setAttribute("role", "admin");
		} else {
			session.setAttribute("role", "customer");
		}
		
		return new ModelAndView("redirect:/gohome", model);
	}
	
	
	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session, ModelMap model) {
		try {
			model.clear();
			session.invalidate();
		} catch (IllegalStateException e) {
			
		}
		return new ModelAndView("redirect:/gohome");
	}
	
	@GetMapping("/editusers")
	public ModelAndView showAllUsers(HttpSession session, ModelMap model) {
		List<User> allUsers= service.getAllUsers();
		model.put("allUsers", allUsers);
		return new ModelAndView("allusers", model);
	}
	
	@GetMapping("/edituser")
	public ModelAndView editUser(ModelMap model, @RequestParam String username) {
		User u = service.findUser(username);
		model.put("user", u);
		return new ModelAndView("edituser", model);
	}
	
	@PostMapping("/edituser")
	public ModelAndView submitEditedUser(ModelMap model, @RequestParam String userid,
			@RequestParam String password, @RequestParam String fname, @RequestParam String lname,
			@RequestParam String role, @RequestParam String email, @RequestParam String address) {
		User u = new User(userid, password, role, fname, lname, address, email);		
		service.updateUser(u);
		model.put("updatemessage", "User Updated!");
		model.put("allUsers", service.getAllUsers());
		return new ModelAndView("allusers", model);
	}
	
}
