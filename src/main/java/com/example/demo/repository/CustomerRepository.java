package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByNameOrMail(String name,String mail);

	Optional<Customer> findByName(String username);

	Optional<Customer> findByMail(String username);
}
