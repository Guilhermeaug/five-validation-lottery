package com.fivevalidation.lottery.mapper;

import com.fivevalidation.lottery.dto.request.CustomerRequestDto;
import com.fivevalidation.lottery.dto.response.CustomerResponseDto;
import com.fivevalidation.lottery.model.CustomerModel;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponseDto modelToResponseDto(CustomerModel customerModel) {
        return CustomerResponseDto.builder()
                .id(customerModel.getId())
                .name(customerModel.getName())
                .build();
    }

    public CustomerModel requestDtoToModel(CustomerRequestDto customerRequestDto) {
        return CustomerModel.builder()
                .name(customerRequestDto.name())
                .build();
    }

}
