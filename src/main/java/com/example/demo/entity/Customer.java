package com.example.demo.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;
@Entity
@Data
@ToString
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true)
	private String name;
	@Column(unique = true)
	private String mail;
	@JsonIgnore
	private String password;
	
	@OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
//	@JoinColumn(name = "authority_id")
	private Set<Authority> authorities;
}
