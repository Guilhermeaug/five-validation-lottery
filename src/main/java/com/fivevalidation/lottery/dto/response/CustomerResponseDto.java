package com.fivevalidation.lottery.dto.response;

import lombok.Builder;

@Builder
public record CustomerResponseDto(
        Long id,
        String name
) {
}
