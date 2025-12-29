package com.example.EmployeeDegital.dto;

import java.time.LocalDate;

import com.example.EmployeeDegital.entity.Project.ProjectStatus;

import lombok.Data;

@Data
public class ProgectRegister {
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private ProjectStatus status;
}
