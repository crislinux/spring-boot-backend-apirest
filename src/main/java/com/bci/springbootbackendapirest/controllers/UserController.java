package com.bci.springbootbackendapirest.controllers;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bci.springbootbackendapirest.models.entity.Phone;
import com.bci.springbootbackendapirest.models.entity.User;
import com.bci.springbootbackendapirest.services.IPhoneService;
import com.bci.springbootbackendapirest.services.IUserService;
import com.bci.springbootbackendapirest.utils.ErrorResponse;
import com.bci.springbootbackendapirest.utils.InvalidDataException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IPhoneService phoneService;
	
	
	private static SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	
	@Autowired
    public UserController(IUserService userService, IPhoneService phoneService) {
        this.userService = userService;
        this.phoneService = phoneService;
    }
	
	@GetMapping("/User")
	public List<User> findAll() {
		return userService.findAll();
	}
	
	@GetMapping("/User/{id}")
	public User findById(@PathVariable UUID id) {
		return userService.findById(id);
	}

	@PostMapping("/User")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?>  create(@RequestBody @Valid User user, BindingResult result) {
		
		if (result.hasErrors()) {
			  throw new InvalidDataException(result);
		}

        if (userService.existsAdminByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(new ErrorResponse("El correo ya está registrado."));
        }
        
    	user.setToken(generateToken()); 
    	user.setCreated();
		user.setLastLogin();
		user.setActive(true);
		
	
        
        List<Phone> phones = user.getPhones();
        if (phones != null && !phones.isEmpty()) {
        	boolean existsDuplicatePhone = phones.stream()
    	            .anyMatch(phone -> phoneService.existsPhoneByNumber(phone.getNumber()));
    	    if (existsDuplicatePhone) {
    	       return ResponseEntity.badRequest().body(new ErrorResponse("Uno o más números de teléfono ya están registrados."));
    	    }
            phones.forEach(phoneService::save);
        }
		
	
		User createdUser = userService.save(user);
	    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}
	
	
	private String generateToken() {    
	    String token = Jwts.builder().signWith(SECRET_KEY).compact();
	    return token;
	}
}

