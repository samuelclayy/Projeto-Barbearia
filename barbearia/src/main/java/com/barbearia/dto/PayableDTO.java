package com.barbearia.dto;

import com.barbearia.enums.AccountStatus;
import com.barbearia.model.Payable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PayableDTO(
        Long id,
        @NotBlank String description,
        @NotNull @Positive BigDecimal amount,
        @NotNull LocalDate dueDate,
        AccountStatus status
) {
    public PayableDTO(Payable entity) {
        this(entity.getId(), entity.getDescription(), entity.getAmount(),
                entity.getDueDate(), entity.getStatus());
    }
}
