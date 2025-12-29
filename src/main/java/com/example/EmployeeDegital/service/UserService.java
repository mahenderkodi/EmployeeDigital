package com.example.EmployeeDegital.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.EmployeeDegital.dto.EmployeeRegisterRequest;
import com.example.EmployeeDegital.dto.JwtResponse;
import com.example.EmployeeDegital.dto.LoginRequest;
import com.example.EmployeeDegital.entity.Department;
import com.example.EmployeeDegital.entity.Employee;
import com.example.EmployeeDegital.entity.EmployeeRole;
import com.example.EmployeeDegital.entity.Roles;
import com.example.EmployeeDegital.entity.Users;
import com.example.EmployeeDegital.repository.DepartmentRepositorey;
import com.example.EmployeeDegital.repository.EmployeeRepositorey;
import com.example.EmployeeDegital.repository.EmployeeRoleRepositorey;
import com.example.EmployeeDegital.repository.RoleRepositorey;
import com.example.EmployeeDegital.repository.UserRepositorey;
import com.example.EmployeeDegital.security.JwtUtill;
import com.example.EmployeeDegital.security.OurUser;


@Service
public class UserService {
	
	private final UserRepositorey userRepositorey;
	private final RoleRepositorey roleRepositorey;
	private final DepartmentRepositorey departmentRepositorey;
	private final EmployeeRepositorey employeeRepositorey;
	private final EmployeeRoleRepositorey employeeRoleRepositorey;
	private final AuthenticationManager authenticationManager;
	@Autowired
    private PasswordEncoder passwordEncoder;
    private final JwtUtill jwtUtill;
	
	public UserService(UserRepositorey userRepositorey,RoleRepositorey roleRepositorey,
			DepartmentRepositorey departmentRepositorey,EmployeeRepositorey employeeRepositorey,
			EmployeeRoleRepositorey employeeRoleRepositorey,AuthenticationManager authenticationManager,
			JwtUtill jwtUtill) {
		this.userRepositorey=userRepositorey;
		this.roleRepositorey=roleRepositorey;
		this.departmentRepositorey=departmentRepositorey;
		this.employeeRepositorey=employeeRepositorey;
		this.employeeRoleRepositorey=employeeRoleRepositorey;
		this.authenticationManager=authenticationManager;
		this.jwtUtill=jwtUtill;
	}
	public Optional<Users> getUserById(Long id) {
		return userRepositorey.findById(id);
	}
	public Optional<Users> getUsersByEmail(String email) {
		return userRepositorey.findByEmail(email);
	}
	public List<Users> getAllUsers() {
		return userRepositorey.findAll();
	}
	public  List<Employee> getEmployeeByDepartment_Name(String deptname) {
		Department department = departmentRepositorey.findByName(deptname)
                .orElseThrow(() -> new RuntimeException("Department not found"));
		return employeeRepositorey.findByDepartment(department);
	}
	
	public Optional<Users> getUserByUsername(String username) {
		return userRepositorey.findByUsername(username);
	}
	
	@Transactional
	public void registration(EmployeeRegisterRequest request) {
  
        if (userRepositorey.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists with username " + request.getUsername());
        }

        if (userRepositorey.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists with email " + request.getEmail());
        }

     
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); 
        user.setEnabled(true);

     
        Set<Roles> roles = new HashSet<>();
        if (request.getRoles() == null || request.getRoles().isEmpty()) {
            Roles defaultRole = roleRepositorey.findByName("ROLE_EMPLOYEE")
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            roles.add(defaultRole);
        } else {
            for (String roleName : request.getRoles()) {
                Roles role = roleRepositorey.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
                roles.add(role);
            }
        }

        user.setRoles(roles);
        userRepositorey.save(user);

    
        Department department = departmentRepositorey.findByName(request.getDepartmentName())
                .orElseThrow(() -> new RuntimeException("Department not found"));

 
        Employee employee = new Employee();
        employee.setUsers(user);
        employee.setEmail(request.getEmail());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setPhone(request.getPhone());
        employee.setDepartment(department);
        employee.setStatus(Employee.EmployeeStatus.ACTIVE);
        employee.setDesignation(request.getDesignation());

        Employee savedEmployee = employeeRepositorey.save(employee);

        savedEmployee.setEmployeeCode(String.format("EMP%04d", savedEmployee.getId()));
        employeeRepositorey.save(savedEmployee);

       
        for (Roles role : roles) {
            EmployeeRole empRole = new EmployeeRole();
            empRole.setEmployee(savedEmployee);
            empRole.setRole(role);
            empRole.setAssignedBy(null); // or system admin later
            employeeRoleRepositorey.save(empRole);
        }
    }
	
		public JwtResponse userLogin(LoginRequest request) {
	    if (!userRepositorey.existsByUsername(request.getUsername())) {
	        throw new UsernameNotFoundException("User with email " + request.getUsername() + " not found");
	    }  
	    Authentication authentication = authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(
	            request.getUsername(),
	            request.getPassword()
	        )
	    );	   
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    OurUser users = (OurUser) authentication.getPrincipal();
	    String jwt = jwtUtill.generateToken(users);	 
	    Set<String> roles = users.getAuthorities().stream()
	            .map(GrantedAuthority::getAuthority)
	            .collect(Collectors.toSet());
	    return new JwtResponse(
	            jwt,
	            users.getId(),
	            users.getUsername(),
	            roles
	    );
	}

}