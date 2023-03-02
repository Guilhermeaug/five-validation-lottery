package com.fivevalidation.lottery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fivevalidation.lottery.dto.request.CustomerRequestDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerControllerTest {

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void createCustomer() throws Exception {
        CustomerRequestDto customerRequestDto = new CustomerRequestDto("John Doe");
        String customerRequestDtoJson = objectMapper.writeValueAsString(customerRequestDto);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerRequestDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void getAllCustomers() throws Exception {
        mockMvc.perform(get("/customers")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> result.getResponse().getContentAsString().contains("John Doe"));
    }
}