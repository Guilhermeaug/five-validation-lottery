package com.fivevalidation.lottery.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TicketRequestDto(
        List<Integer> numbers,
        @NotNull(message = "Customer is mandatory") Long customerId
) {

}
