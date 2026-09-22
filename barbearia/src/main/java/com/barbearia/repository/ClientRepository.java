package com.barbearia.repository;

import com.barbearia.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByBarbershopId(Long barbershopId);

    Optional<Client> findByIdAndBarbershopId(Long id, Long barbershopId);

    Optional<Client> findByPhoneAndBarbershopId(String phone, Long barbershopId);

    boolean existsByPhoneAndBarbershopId(String phone, Long barbershopId);
}