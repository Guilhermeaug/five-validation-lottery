package com.fivevalidation.lottery.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record TicketResponseDto(
        Long id,
        List<Integer> numbers,
        Double totalPrice,
        LocalDateTime dateOfPurchase
) {
}
