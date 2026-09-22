package com.barbearia.controller;

import com.barbearia.dto.AppointmentDTO;
import com.barbearia.model.Appointment;
import com.barbearia.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @GetMapping
    public ResponseEntity<List<Appointment>> findAll(@RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.findAll(barbershopId));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Appointment> findById(
            @PathVariable Long id, @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.findById(id, barbershopId));
    }

    @PostMapping
    public ResponseEntity<Appointment> insert(
            @Valid @RequestBody AppointmentDTO dto, @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.insert(dto, barbershopId));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Appointment> update(
            @PathVariable Long id, @RequestBody Appointment obj,
            @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.update(id, obj, barbershopId));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id, @RequestParam Long barbershopId) {
        service.delete(id, barbershopId);
        return ResponseEntity.noContent().build();
    }
}