package com.example.EmployeeDegital.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EmployeeDegital.entity.Department;
import com.example.EmployeeDegital.entity.Employee;
import com.example.EmployeeDegital.entity.EmployeeRole;
import com.example.EmployeeDegital.entity.Roles;
import com.example.EmployeeDegital.entity.Users;
import com.example.EmployeeDegital.repository.DepartmentRepositorey;
import com.example.EmployeeDegital.repository.EmployeeRepositorey;
import com.example.EmployeeDegital.repository.EmployeeRoleRepositorey;
import com.example.EmployeeDegital.repository.RoleRepositorey;
import com.example.EmployeeDegital.service.UserService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepositorey employeeRepo;
    private final EmployeeRoleRepositorey employeeRoleRepo;
    private final UserService userService;
    private final RoleRepositorey roleRepositorey;
    private final DepartmentRepositorey departmentRepositorey;

    public EmployeeController(EmployeeRepositorey employeeRepo, EmployeeRoleRepositorey employeeRoleRepo,
    		UserService userService,RoleRepositorey roleRepositorey,
    		DepartmentRepositorey departmentRepositorey) {
        this.employeeRepo = employeeRepo;
        this.employeeRoleRepo = employeeRoleRepo;
        this.userService=userService;
        this.roleRepositorey=roleRepositorey;
        this.departmentRepositorey=departmentRepositorey;
    }
    
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    
    public ResponseEntity<Employee> getLoggedInEmployeeProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); 
        Optional<Employee> employee = employeeRepo.findByUsers_Username(username); 
        Employee emp=new Employee();
        emp=employee.get();
        Optional<Department> department=departmentRepositorey.findById(emp.getId());
        Department dept=department.get();
        dept.getName();
        return employee.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeRepo.findAll());
    }

    @GetMapping("/code/{code}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<Employee> getEmployeeByCode(@PathVariable String code) {
        return employeeRepo.findByEmployeeCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

   
    @GetMapping("/department/{deptName}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<List<Employee>> getByDepartment(@PathVariable String deptName) {
        List<Employee> employees = employeeRepo.findByDepartment_Name(deptName);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/role/{roleName}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Employee>> getByRole(@PathVariable String roleName) {
        List<EmployeeRole> empRoles = employeeRoleRepo.findByRole_Name(roleName);
        List<Employee> employees = empRoles.stream()
                                           .map(EmployeeRole::getEmployee)
                                           .toList();
        return ResponseEntity.ok(employees);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Optional<Users> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<Users> getUserByEmail(@PathVariable String email) {
        Optional<Users> user = userService.getUsersByEmail(email);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<Users> getUserByUsername(@PathVariable String username) {
        Optional<Users> user = userService.getUserByUsername(username);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
}