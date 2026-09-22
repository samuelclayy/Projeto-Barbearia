package com.barbearia.repository;

import com.barbearia.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByBarbershopId(Long barbershopId);

    Optional<Service> findByIdAndBarbershopId(Long id, Long barbershopId);

    Optional<Service> findByNameAndBarbershopId(String name, Long barbershopId);
}