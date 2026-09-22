package com.barbearia.dto;

import com.barbearia.model.Service;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ServiceDTO(
        Long id,
        @NotBlank String name,
        @NotNull @Positive BigDecimal price,
        @NotNull @Positive Integer durationMinutes
) {
    public ServiceDTO(Service entity) {
        this(entity.getId(), entity.getName(), entity.getPrice(), entity.getDurationMinutes());
    }
}
