package com.example.Healthcare_app.entity;

import com.example.Healthcare_app.util.EncryptionUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "app_user")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @JsonProperty("username")
    private String username;

    @NotBlank(message = "Password is required")
    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

    @JsonProperty("role")
    private String role;

    @PrePersist
    @PreUpdate
    public void encryptEmail() throws Exception {
        if (email != null) {
            this.email = EncryptionUtil.encrypt(email);
        }
    }

    @PostLoad
    public void decryptEmail() {
        try {
            if (email != null && !email.isEmpty()) {
                this.email = EncryptionUtil.decrypt(email);
            }
        } catch (Exception e) {
            // log the error but avoid crashing
            System.err.println("Email decryption failed: " + e.getMessage());
        }
    }
   
    


}
