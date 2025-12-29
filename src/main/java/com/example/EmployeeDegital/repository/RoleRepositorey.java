package com.example.EmployeeDegital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EmployeeDegital.entity.Roles;

@Repository
public interface RoleRepositorey extends JpaRepository<Roles,Long>{
	Optional<Roles> findByName(String name);
}
