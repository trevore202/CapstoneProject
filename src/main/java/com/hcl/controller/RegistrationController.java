package com.hcl.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.hcl.service.RegistrationService;

@RestController
@SessionAttributes("name")
public class RegistrationController {

	@Autowired
	RegistrationService service;
	
	@GetMapping("/register")
	public ModelAndView showRegisterPage(ModelMap model) {
		return new ModelAndView("register", model);
	}
	
	@PostMapping("/register")
	public ModelAndView showProductsPage(HttpSession session, ModelMap model, @RequestParam String username, @RequestParam String password,
			@RequestParam String role, @RequestParam String fname, @RequestParam String lname,
			@RequestParam String address, @RequestParam String email){
		
		boolean isValidUser = service.addUser(username, password, role, fname, lname, address, email);
		
		if (!isValidUser) {
			model.put("errorMessage", "Could not Register User. Try a different username.");
			return new ModelAndView("register", model);
		}
		
		model.remove("name");
		model.remove("role");
		session.setAttribute("name", username);
		session.setAttribute("role", role);
		return new ModelAndView("index", model);
	}
	
}
