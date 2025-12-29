package com.example.EmployeeDegital.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.EmployeeDegital.entity.Department;
import com.example.EmployeeDegital.entity.Roles;
import com.example.EmployeeDegital.repository.DepartmentRepositorey;
import com.example.EmployeeDegital.repository.RoleRepositorey;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner{
	private final RoleRepositorey roleRepo;
	private final DepartmentRepositorey departmentRepo;
	
	@Override
	public void run(String... args) {
		initializeRoles();
		initializeDepartment();
	}
	public void initializeRoles(){
		 if (roleRepo.count() == 0) {
	            roleRepo.save(new Roles("ROLE_ADMIN", "System Administrator"));
	            roleRepo.save(new Roles("ROLE_HR", "Human Resource Manager"));
	            roleRepo.save(new Roles("ROLE_EMPLOYEE", "Normal Employee"));
	            System.out.println("Default roles added");
	        }
	    }
	public void initializeDepartment(){
		if(departmentRepo.count()==0) {
			departmentRepo.save(new Department("IT", "Information Technology", null));
			departmentRepo.save(new Department("HR", "Human Resources", null));
			departmentRepo.save(new Department("SALES", "Sales and Marketing", null));
	            System.out.println("Default departments added");
		}
	}
}
