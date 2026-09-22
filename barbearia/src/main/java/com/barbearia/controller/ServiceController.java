package com.barbearia.controller;

import com.barbearia.dto.ServiceDTO;
import com.barbearia.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/services")
public class ServiceController {

    @Autowired
    private ServiceService service;

    @GetMapping
    public ResponseEntity<List<ServiceDTO>> findAll(@RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.findAll(barbershopId));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ServiceDTO> findById(
            @PathVariable Long id, @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.findById(id, barbershopId));
    }

    @PostMapping
    public ResponseEntity<ServiceDTO> insert(
            @Valid @RequestBody ServiceDTO dto, @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.insert(dto, barbershopId));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ServiceDTO> update(
            @PathVariable Long id, @Valid @RequestBody ServiceDTO dto,
            @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.update(id, dto, barbershopId));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id, @RequestParam Long barbershopId) {
        service.delete(id, barbershopId);
        return ResponseEntity.noContent().build();
    }
}