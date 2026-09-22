package com.barbearia.repository;

import com.barbearia.enums.TransactionType;
import com.barbearia.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByBarbershopId(Long barbershopId);

    Optional<Transaction> findByIdAndBarbershopId(Long id, Long barbershopId);

    List<Transaction> findByDateAndBarbershopId(LocalDate date, Long barbershopId);

    List<Transaction> findByDateBetweenAndBarbershopId(
            LocalDate start, LocalDate end, Long barbershopId);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
            "WHERE t.type = :type AND t.date BETWEEN :start AND :end " +
            "AND t.barbershop.id = :barbershopId")
    BigDecimal sumAmountByTypeAndPeriodAndBarbershopId(
            @Param("type") TransactionType type,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("barbershopId") Long barbershopId);
}