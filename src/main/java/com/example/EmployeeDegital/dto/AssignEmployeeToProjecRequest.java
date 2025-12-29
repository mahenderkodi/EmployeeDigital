package com.example.EmployeeDegital.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class AssignEmployeeToProjecRequest {
	  private String employeeCode;
	  private String code;
	  private LocalDate assignedFrom;
	  private LocalDate assignedTo;
	  private String roleInProject;
}
