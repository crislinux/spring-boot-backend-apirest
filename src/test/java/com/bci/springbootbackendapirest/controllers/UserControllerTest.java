package com.bci.springbootbackendapirest.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.bci.springbootbackendapirest.models.entity.User;
import com.bci.springbootbackendapirest.services.IPhoneService;
import com.bci.springbootbackendapirest.services.IUserService;
import com.bci.springbootbackendapirest.utils.ErrorResponse;
import com.bci.springbootbackendapirest.utils.InvalidDataException;

public class UserControllerTest {

    private UserController userController;
    private IUserService userService;
    private IPhoneService phoneService;
    private Validator validator;

    @BeforeEach
    public void setup() {
        userService = mock(IUserService.class);
        phoneService = mock(IPhoneService.class);
        userController = new UserController(userService, phoneService);

        // Inicializar el validador
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testFindAll() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());

        when(userService.findAll()).thenReturn(userList);

        List<User> result = userController.findAll();

        assertEquals(2, result.size());
        verify(userService, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        when(userService.findById(userId)).thenReturn(user);

        User result = userController.findById(userId);

        assertEquals(userId, result.getId());
        verify(userService, times(1)).findById(userId);
    }

    @Test
    public void testCreate_ValidUser() {
        User user = new User();
        user.setEmail("test@example.com");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.existsAdminByEmail(user.getEmail())).thenReturn(false);
        when(userService.save(user)).thenReturn(user);

        ResponseEntity<?> responseEntity = userController.create(user, bindingResult);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
        verify(userService, times(1)).existsAdminByEmail(user.getEmail());
        verify(userService, times(1)).save(user);
    }

    @Test
    public void testCreate_InvalidUser() {
        User user = new User();
        user.setEmail("test@example.com");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        assertThrows(InvalidDataException.class, () -> userController.create(user, bindingResult));
        verifyZeroInteractions(userService);
    }

    @Test
    public void testCreate_DuplicateEmail() {
        User user = new User();
        user.setEmail("test@example.com");

        BindingResult bindingResult = mock(BindingResult.class);
        when(userService.existsAdminByEmail(user.getEmail())).thenReturn(true);

        ResponseEntity<?> responseEntity = userController.create(user, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof ErrorResponse);
        verify(userService, times(1)).existsAdminByEmail(user.getEmail());
        verifyZeroInteractions(userService);
    }
}