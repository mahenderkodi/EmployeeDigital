package com.example.EmployeeDegital.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.EmployeeDegital.entity.Attendance.AttendanceStatus;

import lombok.Data;

@Data
public class AttendenceRequest {
	private String employeeCode;
	 private LocalDate date;
	 private LocalDateTime clockIn;
	 private LocalDateTime clockOut;
	 private AttendanceStatus status;
	 private Double workDuration;
}
