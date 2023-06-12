package com.bci.springbootbackendapirest.services;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bci.springbootbackendapirest.dao.IPhoneDao;
import com.bci.springbootbackendapirest.models.entity.Phone;

@Service
public class PhoneService implements IPhoneService{
	
	@Autowired
	private IPhoneDao phoneDao;

	@Override
	@Transactional
	public List<Phone> findAll() {
		return (List<Phone>) phoneDao.findAll();
	}

	@Override
	@Transactional
	public Phone findById(UUID id) {
		return phoneDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public boolean existsPhoneByNumber(String number) {
		return phoneDao.existsPhoneByNumber(number);
	}

	@Override
	@Transactional
	public Phone save(Phone phone) {
		return phoneDao.save(phone);
	}

}
