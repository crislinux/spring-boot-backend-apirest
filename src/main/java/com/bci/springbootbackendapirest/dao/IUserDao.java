package com.bci.springbootbackendapirest.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bci.springbootbackendapirest.models.entity.User;

public interface IUserDao extends CrudRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE u.email = ?1 and u.isActive = ?2")
	User findAdminByEmailAndState(String email, Boolean isActive);

	boolean existsAdminByEmail(String email);

	Optional<User> findById(UUID id);

}

