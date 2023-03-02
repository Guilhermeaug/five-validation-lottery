package com.fivevalidation.lottery.service.implementation;

import com.fivevalidation.lottery.dto.response.DrawResult;
import com.fivevalidation.lottery.model.CustomerModel;
import com.fivevalidation.lottery.model.TicketModel;
import com.fivevalidation.lottery.repository.CustomerRepository;
import com.fivevalidation.lottery.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DrawServiceImplTest {

    @Autowired
    private DrawServiceImpl drawService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @Test
    void getDrawResult() {
        ticketRepository.deleteAll();

        List<DrawResult> drawResults1 = drawService.getDrawResult();
        assertEquals(1, drawResults1.size());
        assertEquals(0, drawResults1.get(0).totalJackpot());
        assertEquals(0, drawResults1.get(0).wonJackpot());
        assertEquals("", drawResults1.get(0).customerName());

        CustomerModel customer1 = new CustomerModel(1L, "Customer1");
        customerRepository.save(customer1);
        ticketRepository.save(new TicketModel(1L, customer1, "1;2;3;4;5;6;7;8;9;10", 10.0, 0.0, LocalDateTime.now()));

        List<DrawResult> drawResults2 = drawService.getDrawResult();
        assertEquals(1, drawResults2.size());
        assertEquals(10, drawResults2.get(0).totalJackpot());
        assertEquals(10, drawResults2.get(0).wonJackpot());
        assertEquals("Customer1", drawResults2.get(0).customerName());
    }
}