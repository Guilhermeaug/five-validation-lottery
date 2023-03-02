package com.fivevalidation.lottery.controller;

import com.fivevalidation.lottery.dto.request.TicketRequestDto;
import com.fivevalidation.lottery.dto.response.TicketResponseDto;
import com.fivevalidation.lottery.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("tickets")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    ResponseEntity<TicketResponseDto> createTicket(
            @Valid @RequestBody TicketRequestDto ticketRequestDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ticketService.createTicket(ticketRequestDto));
    }

    @PostMapping("/random")
    ResponseEntity<TicketResponseDto> createTicketWithRandomNumbers(
            @Valid @RequestBody TicketRequestDto ticketRequestDto,
            @RequestParam int amount
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ticketService.createTicketWithRandomNumbers(ticketRequestDto, amount));
    }

    @GetMapping
    ResponseEntity<List<TicketResponseDto>> getAllTicketsByCustomerName(
            @RequestParam(required = false) String customerName
    ) {
        return customerName == null ?
                ResponseEntity.ok(ticketService.getAllTickets()) :
                ResponseEntity.ok(ticketService.getAllTicketsByCustomerName(customerName));
    }

}
