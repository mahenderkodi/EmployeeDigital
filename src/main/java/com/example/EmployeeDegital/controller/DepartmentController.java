package com.example.EmployeeDegital.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EmployeeDegital.entity.Department;
import com.example.EmployeeDegital.entity.Employee;
import com.example.EmployeeDegital.repository.DepartmentRepositorey;
import com.example.EmployeeDegital.repository.EmployeeRepositorey;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentRepositorey departmentRepo;
    private final EmployeeRepositorey employeeRepo;

    public DepartmentController(DepartmentRepositorey departmentRepo, EmployeeRepositorey employeeRepo) {
        this.departmentRepo = departmentRepo;
        this.employeeRepo = employeeRepo;
    }


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentRepo.findAll());
    }

  
    @GetMapping("/{name}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<Department> getDepartmentByName(@PathVariable String name) {
        return departmentRepo.findByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{deptName}/assign-manager/{empCode}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    public ResponseEntity<String> assignManager(
            @PathVariable String deptName,
            @PathVariable String empCode) {

        Department department = departmentRepo.findByName(deptName)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        Employee manager = employeeRepo.findByEmployeeCode(empCode)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        department.setManager(manager);
        departmentRepo.save(department);

        return ResponseEntity.ok("Manager assigned successfully to " + deptName);
    }
}