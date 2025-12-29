package com.example.EmployeeDegital.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;
import com.example.EmployeeDegital.dto.AssignEmployeeToProjecRequest;
import com.example.EmployeeDegital.dto.ProgectRegister;
import com.example.EmployeeDegital.entity.Project;
import com.example.EmployeeDegital.entity.Project.ProjectStatus;
import com.example.EmployeeDegital.repository.DepartmentRepositorey;
import com.example.EmployeeDegital.repository.PojectRepositorey;
import com.example.EmployeeDegital.service.ProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
	private final ProjectService projectService;
	private final PojectRepositorey projectRepositorey;
	public ProjectController(ProjectService projectService,PojectRepositorey projectRepositorey) {
		this.projectService=projectService;
		this.projectRepositorey=projectRepositorey;
		
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')") 
	@PostMapping("/register")
	public ResponseEntity<String> registerProgectDetails(@RequestBody ProgectRegister req) {
		projectService.newProject(req);
        return ResponseEntity.ok("Project details entered successfully");
      }
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE')")
	@GetMapping
	public ResponseEntity<List<Project>> getAllProjects() {
	    List<Project> projects = projectRepositorey.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
	    return ResponseEntity.ok(projects);
	}
	
	
}
