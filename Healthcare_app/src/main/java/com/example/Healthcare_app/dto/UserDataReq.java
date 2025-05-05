package com.example.Healthcare_app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class UserDataReq {
   
	@NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
    
    private String email;

    private String role;
    
}

