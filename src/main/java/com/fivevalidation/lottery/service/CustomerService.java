package com.fivevalidation.lottery.service;

import com.fivevalidation.lottery.dto.request.CustomerRequestDto;
import com.fivevalidation.lottery.dto.response.CustomerResponseDto;

import java.util.List;

public interface CustomerService {
    CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto);
    List<CustomerResponseDto> getAllCustomers();
}
