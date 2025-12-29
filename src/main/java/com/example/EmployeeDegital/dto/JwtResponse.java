package com.example.EmployeeDegital.dto;

import java.util.Set;

import lombok.Data;

@Data
public class JwtResponse {
	    private String token;
	    private String type = "Bearer"; 
	    private Long id;
	    private String username;
	    private Set<String> roles;

	    public JwtResponse(String accessToken, Long id, String username, Set<String> roles) {
	        this.token = accessToken;
	        this.id = id;
	        this.username = username;
	        this.roles = roles;
	    }
}
