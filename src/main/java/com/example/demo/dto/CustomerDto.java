package com.example.demo.dto;

import com.example.demo.entity.Authority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
	private String name;
	private String mail;
	private String password;
	private Authority authority;
}
