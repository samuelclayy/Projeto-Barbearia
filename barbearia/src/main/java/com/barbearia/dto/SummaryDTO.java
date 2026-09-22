package com.barbearia.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SummaryDTO(
        LocalDate date,
        BigDecimal total,
        Integer qtdClient,
        BigDecimal averageTicket
) {
}
