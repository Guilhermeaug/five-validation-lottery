package com.fivevalidation.lottery.service.implementation;

import com.fivevalidation.lottery.dto.request.CustomerRequestDto;
import com.fivevalidation.lottery.dto.response.CustomerResponseDto;
import com.fivevalidation.lottery.repository.CustomerRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerServiceImplTest {

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Order(1)
    void createCustomer() {
        CustomerRequestDto requestDto = new CustomerRequestDto("John Doe");
        CustomerResponseDto responseDto = customerService.createCustomer(requestDto);
        assertEquals(requestDto.name(), responseDto.name());
    }

    @Test
    @Order(2)
    void getAllCustomers() {
        List<CustomerResponseDto> responseDtoList = customerService.getAllCustomers();
        assertEquals(customerRepository.findAll().size(), responseDtoList.size());
        assertEquals("John Doe", responseDtoList.get(0).name());
    }
}