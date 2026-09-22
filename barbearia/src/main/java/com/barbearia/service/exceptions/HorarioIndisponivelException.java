package com.barbearia.service.exceptions;

import java.time.LocalDateTime;

public class HorarioIndisponivelException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HorarioIndisponivelException(LocalDateTime dateTime) {
        super("Horario indisponivel. Ja existe um agendamento em: " + dateTime);
    }
}