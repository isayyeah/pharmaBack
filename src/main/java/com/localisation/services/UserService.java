package com.localisation.services;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.localisation.entities.City;
import com.localisation.entities.Role;
import com.localisation.entities.User;
import com.localisation.repositories.RoleRepository;
import com.localisation.repositories.UserRepository;


@Service
public class UserService {
	@Autowired
	 UserRepository userRepo;

	@Autowired
	RoleRepository roleRepo;


	public void registerDefaultUser(User user) {
		Role roleUser = roleRepo.findByName("User");
		user.addRole(roleUser);
	
		userRepo.save(user);
	}

	public List<User> listAll() {
		return userRepo.findAll();
	}

	public User get(Long id) {
		return userRepo.findById(id).get();
	}

	public List<Role> listRoles() {
		return roleRepo.findAll();
	}

	public void save(User user) {
 
        userRepo.save(user);
    }
	public User getUserById(long id) {
		 
       return userRepo.findById(id);
    }
	
	

	

}


