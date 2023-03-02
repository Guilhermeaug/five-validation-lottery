package com.fivevalidation.lottery.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CustomerRequestDto(
        @NotBlank(message = "Name is mandatory") String name
) {
}
