package com.example.EmployeeDegital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EmployeeDegital.entity.Users;

@Repository
public interface UserRepositorey extends JpaRepository<Users,Long>{
	Optional<Users> findByUsername(String username);
	Optional<Users> findByEmail(String email);
	boolean existsByUsername(String username);
	
}
