package com.example.Healthcare_app.controller;



import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Healthcare_app.dto.UserDataReq;
import com.example.Healthcare_app.entity.User;
import com.example.Healthcare_app.repository.UserRepository;
import com.example.Healthcare_app.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/auths")
@Tag(name = "Authentication Controller", description = "APIs for user registration and login")
public class AuthCheckController {
	
	    private  final UserRepository userRepository;
	    private final JwtUtil jwtUtil;
	    private final BCryptPasswordEncoder passwordEncoder;
	    private final  ModelMapper mapper;
	    
	    public AuthCheckController(UserRepository userRepository,JwtUtil jwtUtil,BCryptPasswordEncoder passwordEncoder)
	    {
	    	this.jwtUtil=jwtUtil;
	    	this.passwordEncoder=passwordEncoder;
	    	this.userRepository=userRepository;
			this.mapper = new ModelMapper(); 	
	    } 
	    @PostMapping(value = "/register", produces =  "application/json")
	    @Operation(summary = "Register a new user")
	    public ResponseEntity<?> register(@Valid @RequestBody UserDataReq user) { // Remove @Valid temporarily
	        System.out.println("Received user: " + user);
	        
	       User userData =  mapper.map(user, User.class); 
	        try {
	            if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
	                Map<String, String> errorResponse = new HashMap<>();
	                errorResponse.put("error", "Username, password, and email are required");
	                return ResponseEntity.badRequest().body(errorResponse);
	            }
	            userData.setPassword(passwordEncoder.encode(user.getPassword()));
	            userData.setRole(user.getRole());
	            User savedUser = userRepository.save(userData);
	            System.out.println("User saved: " + savedUser);
	            return ResponseEntity.ok(savedUser);
	        } catch (Exception e) {
	            System.out.println("Registration error: " + e.getMessage());
	            Map<String, String> errorResponse = new HashMap<>();
	            errorResponse.put("error", "Error during registration: " + e.getMessage());
	            return ResponseEntity.status(500).body(errorResponse);
	        }
	    }

	    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	    @Operation(summary = "Login and generate JWT token")
	    public ResponseEntity<?> login(@Valid @RequestBody UserDataReq user) {
	        User existingUser = userRepository.findByUsername(user.getUsername());
	        
	        log.info("Getting  existingUser || {}",existingUser);
	        
	        if (existingUser != null  && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
	           
	        	String jwtToken = jwtUtil.generateToken(user.getUsername(), user.getRole());
	        	
	        	return ResponseEntity.ok(jwtToken);
	        }
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("error", "Invalid credentials");
	        return ResponseEntity.status(401).body(errorResponse);
	    }

}
