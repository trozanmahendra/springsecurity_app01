package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CustomerDto;
import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;

@RestController
@RequestMapping("/user")
public class HomeController {
	@Autowired
	private CustomerService customerService;

	@Autowired
	private AuthenticationManager authenticationManager;
	@GetMapping("/home")
	public String home() {
		return "Hi welcome you are on homePage"; 
	}
	@GetMapping("/dashboard")
	@PreAuthorize("hasRole('ADMIN')")
	public String dashboard() {
		return "your DashBoard here................";
	}
	
	@GetMapping("/profile")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public String profile() {
		return "your profile is here................";
	}
	@PostMapping("/register")
	public Customer registerCustomer(@RequestBody CustomerDto customerDto) {
		return customerService.registerCustomer(customerDto);
		
	}
	@GetMapping("/login")
	public ResponseEntity<String> login(@RequestParam String name,@RequestParam String password) throws Exception{
		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(name,password));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid credentials");
		}
		return new ResponseEntity<String>("welcome \n login successful..............",HttpStatus.OK);
		
	}
	
	
}
