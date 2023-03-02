package com.fivevalidation.lottery.repository;

import com.fivevalidation.lottery.model.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<TicketModel, Long> {
    List<TicketModel> findAllByCustomerName(String customerName);
}
