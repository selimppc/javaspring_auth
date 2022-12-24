package com.security.auth.controller;


import com.security.auth.Entity.ERole;
import com.security.auth.Entity.Employee;
import com.security.auth.Entity.Role;
import com.security.auth.repository.EmployeeRepository;
import com.security.auth.repository.RoleRepository;
import com.security.auth.request.LoginRequest;
import com.security.auth.request.SignupRequest;
import com.security.auth.response.JwtResponse;
import com.security.auth.response.MessageResponse;
import com.security.auth.service.EmployeeDetailsImpl;
import com.security.auth.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateEmployee
            (@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (loginRequest.getUsername(),
                                loginRequest.getPassword()));

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        EmployeeDetailsImpl employeeDetails = (EmployeeDetailsImpl)
                authentication.getPrincipal();
        List<String> roles = employeeDetails.getAuthorities()
                .stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                employeeDetails.getId(),
                employeeDetails.getUsername(),
                employeeDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser
            (@RequestBody SignupRequest signUpRequest) {

        if (employeeRepository.existsByUsername
                (signUpRequest.getUsername())) {

            return ResponseEntity.badRequest()
                    .body(new MessageResponse
                            ("Error: username is already taken!"));
        }

        if (employeeRepository
                .existsByEmail(signUpRequest.getEmail())) {

            return ResponseEntity.badRequest()
                    .body(new MessageResponse
                            ("Error: Email is already in use!"));
        }

        // Create new employee account
        Employee employee = new Employee(
                "11111",
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                null
        );

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role employeeRole = roleRepository
                    .findByName(String.valueOf(ERole.ROLE_EMPLOYEE))
                    .orElseThrow(() -> new RuntimeException
                            ("Error: Role is not found."));
            roles.add(employeeRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository
                                .findByName(String.valueOf(ERole.ROLE_ADMIN))
                                .orElseThrow(() -> new RuntimeException
                                        ("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role defaultRole = roleRepository
                                .findByName(String.valueOf(ERole.ROLE_EMPLOYEE))
                                .orElseThrow(() -> new RuntimeException
                                        ("Error: Role is not found."));
                        roles.add(defaultRole);
                }
            });
        }

        employee.setRoles(roles);
        employeeRepository.save(employee);

        return ResponseEntity.ok(new MessageResponse
                ("Employee registered successfully!"));
    }
}
