package com.example.demo.service;

import com.example.demo.dto.CustomerDto;
import com.example.demo.entity.Customer;

public interface CustomerService {

Customer registerCustomer(CustomerDto customerDto);
}
