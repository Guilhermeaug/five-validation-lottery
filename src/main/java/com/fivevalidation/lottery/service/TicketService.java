package com.fivevalidation.lottery.service;

import com.fivevalidation.lottery.dto.request.TicketRequestDto;
import com.fivevalidation.lottery.dto.response.TicketResponseDto;

import java.util.List;

public interface TicketService {
    TicketResponseDto createTicket(TicketRequestDto ticketRequestDto);
    TicketResponseDto createTicketWithRandomNumbers(TicketRequestDto ticketRequestDto, int amount);
    List<TicketResponseDto> getAllTickets();
    List<TicketResponseDto> getAllTicketsByCustomerName(String customerName);
}
