package com.localisation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.localisation.entities.City;
import com.localisation.entities.User;
import com.localisation.services.UserService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.ui.Model;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	UserService userservice;

	@PostMapping("/register")
	public String processRegister(@RequestBody User user) {
		userservice.save(user);

		return "register_success";
	}

	@GetMapping("/list")
	public List<User>  listUsers(Model model) {
		List<User> listUsers = userservice.listAll();
		model.addAttribute("listUsers", listUsers);

		return listUsers ;
	}
	
	
	
	

}
