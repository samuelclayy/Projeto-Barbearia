package com.barbearia.dto;

import com.barbearia.enums.TransactionType;
import com.barbearia.model.Transaction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionDTO(
        Long id,
        @NotNull TransactionType type,
        @NotNull @Positive BigDecimal amount,
        @NotNull LocalDate date,
        String description
) {
    public TransactionDTO(Transaction entity) {
        this(entity.getId(), entity.getType(), entity.getAmount(),
                entity.getDate(), entity.getDescription());
    }
}
