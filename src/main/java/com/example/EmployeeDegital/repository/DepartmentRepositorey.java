package com.example.EmployeeDegital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EmployeeDegital.entity.Department;

@Repository
public interface DepartmentRepositorey extends JpaRepository<Department,Long>{
	Optional<Department> findByName(String name);
	Optional<Department> findByCode(String code);
	
}
