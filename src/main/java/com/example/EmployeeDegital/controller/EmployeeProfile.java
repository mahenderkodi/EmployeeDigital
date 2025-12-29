package com.example.EmployeeDegital.controller;

import com.example.EmployeeDegital.entity.Employee;

import lombok.Data;


public class EmployeeProfile {
	private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String designation;
    private String department;
    private String profilePicture;
    private String status;
	public EmployeeProfile(Employee employee) {
		 this.id = employee.getId();
	        this.firstName = employee.getFirstName();
	        this.lastName = employee.getLastName();
	        this.email = employee.getEmail();
	        this.phone = employee.getPhone();
	        this.designation = employee.getDesignation();
	        this.profilePicture = employee.getProfilePicture();
	        this.status = employee.getStatus().name();

	        if (employee.getDepartment() != null) {
	            this.department = employee.getDepartment().getName();
	        }
	    }
	}