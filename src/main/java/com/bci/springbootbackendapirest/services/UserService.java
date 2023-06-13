package com.bci.springbootbackendapirest.services;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bci.springbootbackendapirest.dao.IUserDao;
import com.bci.springbootbackendapirest.models.entity.User; 


@Service
public class UserService implements IUserService {
	
	@Autowired
	private IUserDao userDao;

	@Override
	@Transactional
	public List<User> findAll() {
		return (List<User>) userDao.findAll();
	}

	@Override
	@Transactional
	public User findById(UUID id) {
		return userDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public User save(User user) {
		return userDao.save(user);
	}

	@Override
	public boolean existsAdminByEmail(String email) {
		return userDao.existsAdminByEmail(email);
	}
	
	
}
