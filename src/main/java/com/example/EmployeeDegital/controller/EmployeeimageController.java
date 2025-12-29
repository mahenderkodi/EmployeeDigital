package com.example.EmployeeDegital.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.EmployeeDegital.entity.Employee;
import com.example.EmployeeDegital.repository.EmployeeRepositorey;


@RestController
@RequestMapping("/api/employees")
public class EmployeeimageController {

    private final EmployeeRepositorey employeeRepo;

    @Value("${file.upload-dir}")
    private String uploadDir; 

    public EmployeeimageController(EmployeeRepositorey employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @PostMapping("/{id}/upload-profile")
    public ResponseEntity<String> uploadProfile(@PathVariable Long id,@RequestParam("file") MultipartFile file) {

        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
           
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String newFileName = "EMP_" + id + "_" + fileName;
            Path targetPath = uploadPath.resolve(newFileName);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            employee.setProfilePicture(newFileName);
            employeeRepo.save(employee);

            return ResponseEntity.ok("Profile image uploaded successfully: " + newFileName);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Could not store file: " + e.getMessage());
        }
    }
}