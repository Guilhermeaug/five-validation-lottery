package com.fivevalidation.lottery.service.implementation;

import com.fivevalidation.lottery.dto.request.TicketRequestDto;
import com.fivevalidation.lottery.dto.response.TicketResponseDto;
import com.fivevalidation.lottery.exception.ResourceNotFoundException;
import com.fivevalidation.lottery.mapper.TicketMapper;
import com.fivevalidation.lottery.model.CustomerModel;
import com.fivevalidation.lottery.model.TicketModel;
import com.fivevalidation.lottery.repository.CustomerRepository;
import com.fivevalidation.lottery.repository.TicketRepository;
import com.fivevalidation.lottery.service.TicketService;
import com.fivevalidation.lottery.util.TicketUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private final TicketMapper ticketMapper;

    @Transactional
    @Override
    public TicketResponseDto createTicket(TicketRequestDto ticketRequestDto) {
        TicketModel ticketModel = createModel(ticketRequestDto);
        return ticketMapper.modelToResponseDto(ticketRepository.save(ticketModel));
    }

    @Transactional
    @Override
    public TicketResponseDto createTicketWithRandomNumbers(TicketRequestDto ticketRequestDto, int amount) {
        List<Integer> numbers = TicketUtils.generateRandomUniqueNumbers(amount);
        TicketRequestDto ticketRequestDtoWithRandomNumbers = new TicketRequestDto(
                numbers,
                ticketRequestDto.customerId()
        );
        TicketModel ticketModel = createModel(ticketRequestDtoWithRandomNumbers);
        return ticketMapper.modelToResponseDto(ticketRepository.save(ticketModel));
    }

    @Override
    public List<TicketResponseDto> getAllTickets() {
        return ticketRepository
                .findAll()
                .stream()
                .map(ticketMapper::modelToResponseDto)
                .toList();
    }

    @Override
    public List<TicketResponseDto> getAllTicketsByCustomerName(String customerName) {
        return ticketRepository
                .findAllByCustomerName(customerName)
                .stream()
                .map(ticketMapper::modelToResponseDto)
                .toList();
    }

    TicketModel createModel(TicketRequestDto ticketRequestDto) {
        validateNumbers(ticketRequestDto.numbers());

        CustomerModel customerModel = customerRepository
                .findById(ticketRequestDto.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        TicketModel ticketModel = ticketMapper.requestDtoToModel(ticketRequestDto);
        ticketModel.setDateOfPurchase(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        ticketModel = calculatePrice(ticketModel, ticketRequestDto.numbers());
        ticketModel.setCustomer(customerModel);

        return ticketModel;
    }

    void validateNumbers(List<Integer> numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("The numbers must not be null");
        }

        if (numbers.size() < 5 || numbers.size() > 10) {
            throw new IllegalArgumentException("The number of numbers must be between 5 and 10");
        }

        if (numbers.stream().anyMatch(number -> number < 1 || number > 10)) {
            throw new IllegalArgumentException("The numbers must be between 1 and 10");
        }

        if (numbers.stream().distinct().count() != numbers.size()) {
            throw new IllegalArgumentException("The numbers must be unique");
        }
    }

    TicketModel calculatePrice(TicketModel ticketModel, List<Integer> numbers) {
        BigDecimal price = new BigDecimal("5.0");

        int size = numbers.size();
        if (size > 5) {
            price = price.multiply(new BigDecimal("1.1").pow(size - 5));
        }

        double priceRounded = price
                .setScale(2, RoundingMode.HALF_DOWN)
                .doubleValue();
        double taxes = price
                .multiply(new BigDecimal("0.15"))
                .setScale(2, RoundingMode.HALF_DOWN)
                .doubleValue();
        ticketModel.setPrice(priceRounded);
        ticketModel.setTaxes(taxes);

        return ticketModel;
    }
}
