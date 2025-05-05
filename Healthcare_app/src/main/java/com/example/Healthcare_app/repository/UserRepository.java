package com.example.Healthcare_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Healthcare_app.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query(value = "SELECT * FROM app_user WHERE username = :username", nativeQuery = true)
	User findByUsername(@Param("username") String username);
}
