package com.fivevalidation.lottery.service.implementation;

import com.fivevalidation.lottery.dto.response.DrawResult;
import com.fivevalidation.lottery.model.TicketModel;
import com.fivevalidation.lottery.repository.TicketRepository;
import com.fivevalidation.lottery.service.DrawService;
import com.fivevalidation.lottery.util.TicketUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DrawServiceImpl implements DrawService {

    private final TicketRepository ticketRepository;

    /**
     * This method generates a random list of 5 unique numbers between 1 and 10. It then filters the
     * list of tickets to find all tickets that have at least 5 of the winning numbers. After that it calculates
     * the jackpot and the amount of money each winner will receive. If there is no winner, it returns a DrawResult
     * object with an empty customerName and the accumulated jackpot.
     * @return A list of DrawResult objects, one for each winner.
     */
    @Override
    public List<DrawResult> getDrawResult() {
        List<Integer> winningNumbersArray = TicketUtils.generateRandomUniqueNumbers(5);
        List<TicketModel> tickets = ticketRepository.findAll();
        List<TicketModel> winningTickets = tickets.stream()
                .filter(ticket ->
                        Arrays.stream(ticket.getNumbers().split(";"))
                                .mapToInt(Integer::parseInt)
                                .filter(winningNumbersArray::contains)
                                .count() >= 5)
                .toList();

        double jackpot = tickets.stream().reduce(0.0, (a, b) -> a + b.getPrice(), Double::sum);

        if (winningTickets.isEmpty()) {
            return List.of(new DrawResult("", winningNumbersArray, jackpot, 0));
        }

        double jackpotPerWinner = jackpot / winningTickets.size();

        // If two or more tickets have the same customerName, we need to merge them
        return winningTickets.stream()
                .collect(Collectors.groupingBy(TicketModel::getCustomer))
                .values()
                .stream()
                .map(ticketModels ->
                        new DrawResult(
                                ticketModels.get(0).getCustomer().getName(),
                                winningNumbersArray,
                                jackpot,
                                jackpotPerWinner * ticketModels.size()
                        )
                )
                .toList();
    }


}
