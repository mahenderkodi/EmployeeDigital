package com.example.EmployeeDegital.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.EmployeeDegital.dto.ProgectRegister;
import com.example.EmployeeDegital.entity.Project;
import com.example.EmployeeDegital.repository.PojectRepositorey;

@Service
public class ProjectService {
	private final PojectRepositorey pojectRepositorey;
	
	public ProjectService(PojectRepositorey pojectRepositorey) {
		this.pojectRepositorey=pojectRepositorey;
	}
	public void newProject(ProgectRegister req) {
        Optional<Project> existingProject = pojectRepositorey.findByName(req.getName());
        if (existingProject.isPresent()) {
            throw new IllegalArgumentException("Project name already exists: " + req.getName());
        }
        Project project = new Project();
        project.setName(req.getName());
        project.setStartDate(req.getStartDate());
        project.setEndDate(req.getEndDate());
        project.setStatus(Project.ProjectStatus.PLANNED);
        Project savedProject = pojectRepositorey.save(project);
        savedProject.setCode(String.format("PR-%02d", savedProject.getId()));
        pojectRepositorey.save(savedProject);
    }

}
