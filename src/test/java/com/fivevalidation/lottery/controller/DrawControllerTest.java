package com.fivevalidation.lottery.controller;

import com.fivevalidation.lottery.model.CustomerModel;
import com.fivevalidation.lottery.model.TicketModel;
import com.fivevalidation.lottery.repository.CustomerRepository;
import com.fivevalidation.lottery.repository.TicketRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DrawControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @BeforeAll
    void setUp() {
        CustomerModel model = new CustomerModel();
        model.setName("John Doe");
        customerRepository.save(model);

        TicketModel ticketModel = new TicketModel();
        ticketModel.setCustomer(model);
        ticketModel.setNumbers("1;2;3;4;5");
        ticketModel.setPrice(5.0);
        ticketModel.setTaxes(0.75);
        ticketRepository.save(ticketModel);
    }

    @Test
    void getDrawResult() throws Exception {
        mockMvc.perform(get("/draws"))
                .andExpect(status().isOk());
    }
}