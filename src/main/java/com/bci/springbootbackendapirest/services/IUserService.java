package com.bci.springbootbackendapirest.services;

import java.util.List;
import java.util.UUID;

import com.bci.springbootbackendapirest.models.entity.User;

public interface IUserService {

	public List<User> findAll();
	
	public User findById(UUID id);
		
	public boolean existsAdminByEmail(String email);
	
	public User save(User user);
	
		
}
