package com.spring.restsecurity.serviceimpl;

import java.util.Optional;
import java.util.stream.Collectors;

import com.spring.restsecurity.config.AppConfig;
import com.spring.restsecurity.entity.Employee;
import com.spring.restsecurity.repo.UserRepository;
import com.spring.restsecurity.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class IUserServiceImpl implements IUserService, UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Integer saveUser(Employee employee) {
		// TODO: Encode Password
		employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
		return repo.save(employee).getId();
	}

	// Spring Security
	@Override
	public Optional<Employee> findByUsername(String username) {
		// TODO Auto-generated method stub
//		repo.findB
		return repo.findByUsername(username);
	}

	// Spring Security
	// From UserDetailsService inbuilt interface
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Employee> employee = findByUsername(username);
		if (employee.isEmpty())
			throw new UsernameNotFoundException(username);

		Employee emp = employee.get();
		return new User(username, emp.getPassword(),
				emp.getRoles().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()));
	}

}
