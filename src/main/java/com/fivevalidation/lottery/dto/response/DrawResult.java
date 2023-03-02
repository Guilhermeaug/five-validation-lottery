package com.fivevalidation.lottery.dto.response;

import java.util.List;

public record DrawResult(
        String customerName,
        List<Integer> winningNumbers,
        double totalJackpot,
        double wonJackpot
) {
}
