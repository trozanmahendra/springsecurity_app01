package com.example.demo.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Authority;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String uname = username;
		if (username.contains("@")) {
			username = customerRepository.findByMail(username).get().getName();
		}
		Customer customer1 = customerRepository.findByName(username)
				.orElseThrow(() -> new RuntimeException("Invalid user :" + uname));
//		List<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority(customer1.getAuthorities()));
//		System.out.println("-----------------"+customer1.getAuthority().getAuthority());
		return new User(customer1.getName(), customer1.getPassword(), getAuthorites(customer1.getAuthorities()));
	}

	private Set<SimpleGrantedAuthority> getAuthorites(Set<Authority> authorities) {
		Set<SimpleGrantedAuthority> set = new HashSet<SimpleGrantedAuthority>();
		for(Authority auth : authorities) {
			set.add(new SimpleGrantedAuthority(auth.getAuthority()));
		}
		return set;
	}
}
