package com.example.EmployeeDegital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EmployeeDegital.entity.Employee;
import com.example.EmployeeDegital.entity.EmployeeProject;

@Repository
public interface EmployeeProjectRepositorey extends JpaRepository<EmployeeProject,Long> {
	Optional<EmployeeProject> findByEmployee(Employee employee);
}
