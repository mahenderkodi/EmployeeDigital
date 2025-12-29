package com.example.EmployeeDegital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EmployeeDegital.entity.Employee;
import com.example.EmployeeDegital.entity.Leave;

@Repository
public interface LeaveRepositorey extends JpaRepository<Leave,Long>{
	Optional<Leave> findByEmployee(Employee employee);
}
