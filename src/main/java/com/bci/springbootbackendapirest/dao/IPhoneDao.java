package com.bci.springbootbackendapirest.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.bci.springbootbackendapirest.models.entity.Phone;

public interface IPhoneDao extends CrudRepository<Phone, Long> {
	
	boolean existsPhoneByNumber(String number);

	Optional<Phone> findById(UUID id);

}
