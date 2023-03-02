package com.fivevalidation.lottery.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TicketUtils {

    public static List<Integer> generateRandomUniqueNumbers(int amount) {
        if (amount > 10) throw new IllegalArgumentException("Amount cannot be greater than 10");

        Random random = new Random();
        List<Integer> numbers = new ArrayList<>();

        while (numbers.size() < amount) {
            int number = random.nextInt(10) + 1;
            if (!numbers.contains(number)) {
                numbers.add(number);
            }
        }
        Collections.sort(numbers);

        return numbers;
    }
}
