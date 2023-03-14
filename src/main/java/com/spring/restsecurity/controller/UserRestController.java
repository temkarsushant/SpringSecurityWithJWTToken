package com.spring.restsecurity.controller;

import java.security.Principal;

import com.spring.restsecurity.entity.Employee;
import com.spring.restsecurity.requestpojo.UserRequestPojo;
import com.spring.restsecurity.responsepojo.UserResponsePojo;
import com.spring.restsecurity.service.IUserService;
import com.spring.restsecurity.utility.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRestController {

	@Autowired
	private IUserService service;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	AuthenticationManager authenticationManager;

	@PostMapping("/save")
	public ResponseEntity<String> saveUser(@RequestBody Employee employee) {
		System.out.println(employee);
		Integer id = service.saveUser(employee);

		String body = "user " + id + " saved";

		return ResponseEntity.ok(body);
	}

	@PostMapping("/login")
	public ResponseEntity<UserResponsePojo> loginUser(@RequestBody UserRequestPojo userRequestPojo) {

		// TODO: Validate Username, Password with database
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userRequestPojo.getUsername(), userRequestPojo.getPassword()));
		String token = jwtUtil.generateToken(userRequestPojo.getUsername());
		System.out.println(token);

		return ResponseEntity.ok(new UserResponsePojo(token, "Success"));
	}

//	3. after login only
	@PostMapping("/welcome")
	public ResponseEntity<String> accessData(Principal p) {
		return ResponseEntity.ok(p.getName());
	}
}
