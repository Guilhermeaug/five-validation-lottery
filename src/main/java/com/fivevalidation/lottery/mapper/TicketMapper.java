package com.fivevalidation.lottery.mapper;

import com.fivevalidation.lottery.dto.request.TicketRequestDto;
import com.fivevalidation.lottery.dto.response.TicketResponseDto;
import com.fivevalidation.lottery.model.TicketModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

@Component
public class TicketMapper {

    public TicketResponseDto modelToResponseDto(TicketModel ticketModel) {
        List<Integer> numbers = Arrays.stream(ticketModel
                        .getNumbers()
                        .split(";"))
                .map(Integer::parseInt)
                .toList();
        double totalPrice = BigDecimal.valueOf(ticketModel.getPrice() + ticketModel.getTaxes())
                .setScale(2, RoundingMode.HALF_DOWN)
                .doubleValue();
        return TicketResponseDto.builder()
                .id(ticketModel.getId())
                .totalPrice(totalPrice)
                .numbers(numbers)
                .dateOfPurchase(ticketModel.getDateOfPurchase())
                .build();
    }

    public TicketModel requestDtoToModel(TicketRequestDto ticketRequestDto) {
        String numbers = ticketRequestDto
                .numbers()
                .stream()
                .map(String::valueOf)
                .reduce((s1, s2) -> s1 + ";" + s2)
                .orElse("");
        return TicketModel.builder()
                .numbers(numbers)
                .build();
    }

}
