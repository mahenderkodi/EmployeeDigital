package com.example.EmployeeDegital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EmployeeDegital.entity.Employee;
import com.example.EmployeeDegital.entity.EmployeeRole;
import com.example.EmployeeDegital.entity.Roles;

@Repository
public interface EmployeeRoleRepositorey extends JpaRepository<EmployeeRole,Long>{
	List<EmployeeRole> findByEmployee(Employee employee);
	List<EmployeeRole> findByRole(Roles role);
	Optional<EmployeeRole> findByEmployeeAndRole(Employee employee, Roles role);
	List<EmployeeRole> findByRole_Name(String roleName);
	
}
