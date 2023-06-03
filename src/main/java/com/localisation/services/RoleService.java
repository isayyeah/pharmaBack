package com.localisation.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.localisation.entities.Role;
import com.localisation.repositories.RoleRepository;

public class RoleService {
	


	@Autowired
	RoleRepository roleRepo;




	public List<Role> listAll() {
		return roleRepo.findAll();
	}

	public Role get(Integer id) {
		return roleRepo.findById(id).get();
	}


	public List<Role> listRoles() {
		return roleRepo.findAll();
	}

	public Role save(Role role ) {
		
		return roleRepo.save(role);
	}

}
