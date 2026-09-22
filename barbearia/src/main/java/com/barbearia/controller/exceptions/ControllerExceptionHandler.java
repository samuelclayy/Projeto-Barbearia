package com.barbearia.controller.exceptions;

import com.barbearia.service.exceptions.HorarioIndisponivelException;
import com.barbearia.service.exceptions.PhoneAlreadyExistsException;
import com.barbearia.service.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(
            ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(
                Instant.now(), status.value(), "Recurso nao encontrado",
                e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(HorarioIndisponivelException.class)
    public ResponseEntity<StandardError> horarioIndisponivel(
            HorarioIndisponivelException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(
                Instant.now(), status.value(), "Horario indisponivel",
                e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(PhoneAlreadyExistsException.class)
    public ResponseEntity<StandardError> phoneAlreadyExists(
            PhoneAlreadyExistsException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(
                Instant.now(), status.value(), "Telefone ja cadastrado",
                e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
