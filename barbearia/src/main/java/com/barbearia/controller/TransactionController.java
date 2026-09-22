package com.barbearia.controller;

import com.barbearia.dto.TransactionDTO;
import com.barbearia.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> findAll(@RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.findAll(barbershopId));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransactionDTO> findById(
            @PathVariable Long id, @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.findById(id, barbershopId));
    }

    @GetMapping(value = "/by-date")
    public ResponseEntity<List<TransactionDTO>> findByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.findByDate(date, barbershopId));
    }

    @GetMapping(value = "/by-period")
    public ResponseEntity<List<TransactionDTO>> findByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.findByPeriod(start, end, barbershopId));
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> insert(
            @Valid @RequestBody TransactionDTO dto, @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.insert(dto, barbershopId));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id, @RequestParam Long barbershopId) {
        service.delete(id, barbershopId);
        return ResponseEntity.noContent().build();
    }
}