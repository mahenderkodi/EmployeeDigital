package com.example.EmployeeDegital.dto;

import java.util.Set;
import lombok.Data;

@Data
public class EmployeeRegisterRequest {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String departmentName;
    private String designation;
    private Set<String> roles;
}