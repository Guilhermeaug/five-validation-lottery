package com.fivevalidation.lottery.controller;

import com.fivevalidation.lottery.dto.request.CustomerRequestDto;
import com.fivevalidation.lottery.dto.response.CustomerResponseDto;
import com.fivevalidation.lottery.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    ResponseEntity<CustomerResponseDto> createCustomer(
            @Valid @RequestBody CustomerRequestDto customerRequestDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerService.createCustomer(customerRequestDto));
    }

    @GetMapping
    ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

}
