package com.barbearia.repository;

import com.barbearia.model.Barber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BarberRepository extends JpaRepository<Barber, Long> {

    List<Barber> findByBarbershopId(Long barbershopId);

    Optional<Barber> findByIdAndBarbershopId(Long id, Long barbershopId);

    // Usado pra validar o limite do plano BASICO (so 1 barbeiro)
    long countByBarbershopId(Long barbershopId);
}