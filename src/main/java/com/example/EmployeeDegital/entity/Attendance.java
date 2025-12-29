package com.example.EmployeeDegital.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate date;

    private LocalDateTime clockIn;

    private LocalDateTime clockOut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status;

    @Column(name = "work_duration")
    private Double workDuration;

    public void calculateWorkDuration() {
        if (clockIn != null && clockOut != null) {
            long seconds = java.time.Duration.between(clockIn, clockOut).getSeconds();
            this.workDuration = seconds / 3600.0;
        } else {
            this.workDuration = 0.0;
        }
    }

    public enum AttendanceStatus {
        PRESENT,
        ABSENT,
        LATE,
        ON_LEAVE
    }

}
