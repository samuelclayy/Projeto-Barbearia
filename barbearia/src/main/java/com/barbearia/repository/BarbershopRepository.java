package com.barbearia.repository;

import com.barbearia.model.Barbershop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BarbershopRepository extends JpaRepository<Barbershop, Long> {

    // Vai ser usado no LOGIN (etapa 2)
    Optional<Barbershop> findByEmail(String email);

    boolean existsByEmail(String email);
}