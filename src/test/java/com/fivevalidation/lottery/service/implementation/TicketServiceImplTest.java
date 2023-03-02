package com.fivevalidation.lottery.service.implementation;

import com.fivevalidation.lottery.dto.request.TicketRequestDto;
import com.fivevalidation.lottery.dto.response.TicketResponseDto;
import com.fivevalidation.lottery.model.CustomerModel;
import com.fivevalidation.lottery.model.TicketModel;
import com.fivevalidation.lottery.repository.CustomerRepository;
import com.fivevalidation.lottery.repository.TicketRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TicketServiceImplTest {

    @Autowired
    private TicketServiceImpl ticketService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @BeforeAll
    void setUp() {
        customerRepository.save(new CustomerModel(1L, "John Doe"));
    }

    @Test
    void calculatePrice() {
        TicketModel ticketModel1 = new TicketModel();
        ticketModel1 = ticketService.calculatePrice(ticketModel1, List.of(1, 2, 3, 4, 5, 6, 7, 8));
        assertEquals(6.65, ticketModel1.getPrice());
        assertEquals(1, ticketModel1.getTaxes());

        TicketModel ticketModel2 = new TicketModel();
        ticketModel2 = ticketService.calculatePrice(ticketModel2, List.of(1, 2, 3, 4, 5));
        assertEquals(5, ticketModel2.getPrice());
        assertEquals(0.75, ticketModel2.getTaxes());
    }

    @Test
    @Order(1)
    void createTicket() {
        TicketRequestDto ticketRequestDto = new TicketRequestDto(List.of(1, 2, 3, 4, 5, 6, 7, 8), 1L);
        TicketResponseDto responseDto = ticketService.createTicket(ticketRequestDto);
        assertEquals(7.65, responseDto.totalPrice());
    }

    @Test
    void createInvalidTicket() {
        TicketRequestDto ticketRequestDto = new TicketRequestDto(List.of(1, 2, 3, 4, 5, 6, 7, 8, 15), 1L);
        assertThrows(IllegalArgumentException.class, () -> ticketService.createTicket(ticketRequestDto));
    }

    @Test
    @Order(2)
    void createTicketWithRandomNumbers() {
        TicketRequestDto requestDto = new TicketRequestDto(null, 1L);
        TicketResponseDto responseDto = ticketService.createTicketWithRandomNumbers(requestDto, 8);
        assertEquals(8, responseDto.numbers().size());
    }

    @Test
    void createInvalidTicketWithRandomNumbers() {
        TicketRequestDto requestDto = new TicketRequestDto(null, 1L);
        assertThrows(IllegalArgumentException.class,
                () -> ticketService.createTicketWithRandomNumbers(requestDto, 20));
    }

    @Test
    @Order(3)
    void getAllTicketsByCustomerName() {
        List<TicketResponseDto> responseDtos = ticketService.getAllTicketsByCustomerName("John Doe");
        assertEquals(ticketRepository.findAllByCustomerName("John Doe").size(), responseDtos.size());
    }

}