package com.example.EmployeeDegital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EmployeeDegital.entity.Department;
import com.example.EmployeeDegital.entity.Employee;
import com.example.EmployeeDegital.entity.Users;

@Repository
public interface EmployeeRepositorey extends JpaRepository<Employee,Long>{
	Optional<Employee> findByUsers(Users users);
	Optional<Employee> findByEmail(String email);
	Optional<Employee> findByEmployeeCode(String employeeCode);
	List<Employee> findByDepartment(Department department);
	List<Employee> findByDepartment_Name(String departmentName);
	Optional<Employee> findByUsers_Username(String username);
}
