package com.example.EmployeeDegital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EmployeeDegital.entity.Attendance;
import com.example.EmployeeDegital.entity.Employee;

@Repository
public interface AttendenceRepositorey extends JpaRepository<Attendance,Long>{
	Optional<Attendance> findByEmployee(Employee employee);
	
}
