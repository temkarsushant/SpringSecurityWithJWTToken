package com.spring.restsecurity.service;

import java.util.Optional;

import com.spring.restsecurity.entity.Employee;

public interface IUserService {

	Integer saveUser(Employee employee);
	
	Optional<Employee> findByUsername(String username);
}
