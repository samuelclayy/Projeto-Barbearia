package com.barbearia.controller;

import com.barbearia.dto.PayableDTO;
import com.barbearia.service.PayableService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/payables")
public class PayableController {

    @Autowired
    private PayableService service;

    @GetMapping
    public ResponseEntity<List<PayableDTO>> findAll(@RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.findAll(barbershopId));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PayableDTO> findById(
            @PathVariable Long id, @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.findById(id, barbershopId));
    }

    @PostMapping
    public ResponseEntity<PayableDTO> insert(
            @Valid @RequestBody PayableDTO dto, @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.insert(dto, barbershopId));
    }

    @PatchMapping(value = "/{id}/pay")
    public ResponseEntity<PayableDTO> markAsPaid(
            @PathVariable Long id, @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.markAsPaid(id, barbershopId));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id, @RequestParam Long barbershopId) {
        service.delete(id, barbershopId);
        return ResponseEntity.noContent().build();
    }
}