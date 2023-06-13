package com.bci.springbootbackendapirest.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bci.springbootbackendapirest.models.entity.User;

public interface IUserDao extends CrudRepository<User, Long> {
	
	boolean existsAdminByEmail(String email);

	Optional<User> findById(UUID id);

}

