package com.example.EmployeeDegital.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EmployeeDegital.dto.EmployeeRegisterRequest;
import com.example.EmployeeDegital.dto.JwtResponse;
import com.example.EmployeeDegital.dto.LoginRequest;
import com.example.EmployeeDegital.entity.Users;
import com.example.EmployeeDegital.service.UserService;


@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody EmployeeRegisterRequest request) {
        userService.registration(request);
        return ResponseEntity.ok("User registered successfully!");
    }
    @PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		try {
            JwtResponse jwtResponse = userService.userLogin(request);
            return ResponseEntity.ok(jwtResponse);
        } catch (BadCredentialsException | UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", ex.getMessage()));
        }
	}
 
  
}
