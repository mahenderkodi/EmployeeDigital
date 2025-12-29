package com.example.EmployeeDegital.service;

import org.springframework.stereotype.Service;
import com.example.EmployeeDegital.dto.AssignEmployeeToProjecRequest;
import com.example.EmployeeDegital.entity.Employee;
import com.example.EmployeeDegital.entity.EmployeeProject;
import com.example.EmployeeDegital.entity.Project;
import com.example.EmployeeDegital.repository.EmployeeProjectRepositorey;
import com.example.EmployeeDegital.repository.EmployeeRepositorey;
import com.example.EmployeeDegital.repository.PojectRepositorey;

import jakarta.transaction.Transactional;

@Service
public class EmployeeProjectService {

	private final EmployeeRepositorey employeeRepository;
	private final PojectRepositorey projectRepository;
	private final EmployeeProjectRepositorey employeeProjectRepositorey;

	public EmployeeProjectService(EmployeeRepositorey employeeRepository, PojectRepositorey projectRepository,
			EmployeeProjectRepositorey employeeProjectRepositorey) {
		this.employeeRepository = employeeRepository;
		this.projectRepository = projectRepository;
		this.employeeProjectRepositorey = employeeProjectRepositorey;
	}

	@Transactional
	public EmployeeProject assignEmployeeToProject(AssignEmployeeToProjecRequest req) {

		Employee employee = employeeRepository.findByEmployeeCode(req.getEmployeeCode())
				.orElseThrow(() -> new RuntimeException("Project not found with id " + req.getCode()));

		Project project = projectRepository.findByCode(req.getCode())
				.orElseThrow(() -> new RuntimeException("Project not found with id " + req.getCode()));

		EmployeeProject employeeProject = new EmployeeProject();
		employeeProject.setEmployee(employee);
		employeeProject.setProject(project);
		employeeProject.setAssignedFrom(req.getAssignedFrom());
		employeeProject.setAssignedTo(req.getAssignedTo());
		employeeProject.setRoleInProject(req.getRoleInProject());

		return employeeProjectRepositorey.save(employeeProject);
	}
}
