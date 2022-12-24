package com.security.auth.service;

import com.security.auth.Entity.Employee;
import com.security.auth.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDetailsServiceImpl implements UserDetailsService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository
                .findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Employee Not Found with username: " + username)
                );

        return EmployeeDetailsImpl.build(employee);
    }
}
