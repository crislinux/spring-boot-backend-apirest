package com.bci.springbootbackendapirest.services;

import java.util.List;
import java.util.UUID;

import com.bci.springbootbackendapirest.models.entity.Phone;

public interface IPhoneService {

	public List<Phone> findAll();
	
	public Phone findById(UUID id);
	
	public boolean existsPhoneByNumber(String number);
	
	public Phone save(Phone phone);
		
}
