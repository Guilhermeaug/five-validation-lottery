package com.fivevalidation.lottery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fivevalidation.lottery.dto.request.TicketRequestDto;
import com.fivevalidation.lottery.model.CustomerModel;
import com.fivevalidation.lottery.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TicketControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeAll
    void setUp() {
        CustomerModel model = new CustomerModel();
        model.setName("John Doe");
        customerRepository.save(model);
    }

    @Test
    @Order(1)
    void createTicket() throws Exception {
        TicketRequestDto ticketRequestDto = new TicketRequestDto(List.of(2, 3, 4, 5, 7, 8), 1L);
        String ticketRequestDtoJson = objectMapper.writeValueAsString(ticketRequestDto);

        mockMvc.perform(post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ticketRequestDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void createTicketWithRandomNumbers() throws Exception {
        TicketRequestDto ticketRequestDto = new TicketRequestDto(List.of(), 1L);
        String ticketRequestDtoJson = objectMapper.writeValueAsString(ticketRequestDto);

        mockMvc.perform(post("/tickets/random")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ticketRequestDtoJson)
                        .param("amount", "6"))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(3)
    void createInvalidTicket() throws Exception {
        TicketRequestDto ticketRequestDto = new TicketRequestDto(List.of(1, 2, 3), 1L);
        String ticketRequestDtoJson = objectMapper.writeValueAsString(ticketRequestDto);

        mockMvc.perform(post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ticketRequestDtoJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    void getAllTicketsByCustomerName() throws Exception {
        mockMvc.perform(get("/tickets")
                        .param("customerName", "John Doe")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}