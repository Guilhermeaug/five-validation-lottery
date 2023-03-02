package com.fivevalidation.lottery.service.implementation;

import com.fivevalidation.lottery.dto.request.CustomerRequestDto;
import com.fivevalidation.lottery.dto.response.CustomerResponseDto;
import com.fivevalidation.lottery.mapper.CustomerMapper;
import com.fivevalidation.lottery.model.CustomerModel;
import com.fivevalidation.lottery.repository.CustomerRepository;
import com.fivevalidation.lottery.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional
    @Override
    public CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto) {
        CustomerModel customerModel = customerMapper.requestDtoToModel(customerRequestDto);
        return customerMapper.modelToResponseDto(customerRepository.save(customerModel));
    }

    @Override
    public List<CustomerResponseDto> getAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customerMapper::modelToResponseDto)
                .toList();
    }
}
