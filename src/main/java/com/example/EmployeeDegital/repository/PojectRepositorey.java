package com.example.EmployeeDegital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EmployeeDegital.entity.Project;

@Repository
public interface PojectRepositorey extends JpaRepository<Project,Long>{
	Optional<Project> findByName(String name);
	Optional<Project> findByCode(String code);
}
