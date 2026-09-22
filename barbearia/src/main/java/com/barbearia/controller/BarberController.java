package com.barbearia.controller;

import com.barbearia.model.Barber;
import com.barbearia.service.BarberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/barbers")
public class BarberController {

    @Autowired
    private BarberService service;

    @GetMapping
    public ResponseEntity<List<Barber>> findAll(@RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.findAll(barbershopId));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Barber> findById(
            @PathVariable Long id, @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.findById(id, barbershopId));
    }

    @PostMapping
    public ResponseEntity<Barber> insert(
            @RequestParam String name, @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.insert(name, barbershopId));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Barber> update(
            @PathVariable Long id, @RequestParam String name,
            @RequestParam Long barbershopId) {
        return ResponseEntity.ok(service.update(id, name, barbershopId));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id, @RequestParam Long barbershopId) {
        service.delete(id, barbershopId);
        return ResponseEntity.noContent().build();
    }
}