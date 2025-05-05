package com.example.Healthcare_app.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf(csrf -> csrf.disable()) // Disable CSRF (important for APIs)
	            .authorizeHttpRequests(auth -> auth
	                // Allow access to Swagger UI and Swagger-related endpoints without authentication
	            		.requestMatchers(
	            			    "/v3/api-docs",           // add without /** to match exact path
	            			    "/v3/api-docs/**",        // also match subpaths (if any)
	            			    "/swagger-ui.html",
	            			    "/swagger-ui/**",
	            			    "/swagger-resources/**",
	            			    "/webjars/**"
	            			).permitAll()
	                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()// Swagger config endpoint).permitAll()
	                // Allow both GET and POST requests for specific API endpoints
	                .requestMatchers("/api/auths/**","/api/appointments/**","/api/doctors/**","/api/chat/**").permitAll()
	                .anyRequest().authenticated() // Other requests must be authenticated
	            )
	           .formLogin(login -> login.disable()) // Disable default form login page
	            .httpBasic(basic -> basic.disable()) // Disable basic authentication (optional)
	            .sessionManagement(session -> session
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Ensure no session is created (stateless)
	        
	        return http.build();
	    }

		@Bean
	    BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
		

	

}
