package com.barbearia.repository;

import com.barbearia.enums.AccountStatus;
import com.barbearia.model.Payable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PayableRepository extends JpaRepository<Payable, Long> {

    List<Payable> findByBarbershopId(Long barbershopId);

    Optional<Payable> findByIdAndBarbershopId(Long id, Long barbershopId);

    List<Payable> findByStatusAndBarbershopId(AccountStatus status, Long barbershopId);

    // Total a pagar no mes — dessa barbearia
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payable p " +
            "WHERE p.dueDate BETWEEN :start AND :end AND p.barbershop.id = :barbershopId")
    BigDecimal sumAmountByDueDateBetweenAndBarbershopId(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("barbershopId") Long barbershopId);
}