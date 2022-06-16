package com.example.demo.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Authority;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String name = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		if(name.contains("@")) 
			name = customerRepository.findByMail(name).get().getName();
		
		Customer customer = customerRepository.findByName(name)
				.orElseThrow(() -> new UsernameNotFoundException("Customer not found"));
		if(passwordEncoder.matches(password,customer.getPassword())) {
			
//			System.out.println("-----------------"+customer.getAuthority().getAuthority());
			return new UsernamePasswordAuthenticationToken(name, password,getAuthorites(customer.getAuthorities()));
		}
		else
			throw new BadCredentialsException("Invalid credentials given");

	}

	private Set<SimpleGrantedAuthority> getAuthorites(Set<Authority> authorities) {
		Set<SimpleGrantedAuthority> set = new HashSet<SimpleGrantedAuthority>();
		for(Authority auth : authorities) {
			set.add(new SimpleGrantedAuthority(auth.getAuthority()));
		}
		return set;
	}

	@Override
	public boolean supports(Class<?> authentication) {

		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
