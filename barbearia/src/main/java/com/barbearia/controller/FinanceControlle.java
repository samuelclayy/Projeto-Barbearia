package com.barbearia.controller;

import com.barbearia.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/finance")
public class FinanceControlle {

    @Autowired
    private FinanceService service;

    @GetMapping(value = "/revenue")
    public ResponseEntity<BigDecimal> dailyRevenue(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.dailyRevenue(date, barbershopId));
    }

    @GetMapping(value = "/received")
    public ResponseEntity<BigDecimal> dailyReceived(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.dailyReceived(date, barbershopId));
    }

    @GetMapping(value = "/credit")
    public ResponseEntity<BigDecimal> dailyCredit(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.dailyCredit(date, barbershopId));
    }

    @GetMapping(value = "/owed")
    public ResponseEntity<BigDecimal> totalOwed(@RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.totalOwed(barbershopId));
    }

    @GetMapping(value = "/average-ticket")
    public ResponseEntity<BigDecimal> dailyAverageTicket(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.dailyAverageTicket(date, barbershopId));
    }

    @GetMapping(value = "/clients")
    public ResponseEntity<Integer> dailyClientCount(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.dailyClientCount(date, barbershopId));
    }

    @GetMapping(value = "/expenses")
    public ResponseEntity<BigDecimal> monthlyExpenses(
            @RequestParam int month, @RequestParam int year,
            @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.monthlyExpenses(month, year, barbershopId));
    }

    @GetMapping(value = "/profit")
    public ResponseEntity<BigDecimal> monthlyProfit(
            @RequestParam int month, @RequestParam int year,
            @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.monthlyProfit(month, year, barbershopId));
    }
}