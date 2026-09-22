package com.barbearia.service;

import com.barbearia.dto.TransactionDTO;
import com.barbearia.model.Barbershop;
import com.barbearia.model.Transaction;
import com.barbearia.repository.BarbershopRepository;
import com.barbearia.repository.TransactionRepository;
import com.barbearia.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private BarbershopRepository barbershopRepository;

    public List<TransactionDTO> findAll(Long barbershopId) {
        List<Transaction> list = repository.findByBarbershopId(barbershopId);
        return list.stream().map(TransactionDTO::new).toList();
    }

    public TransactionDTO findById(Long id, Long barbershopId) {
        Transaction entity = repository.findByIdAndBarbershopId(id, barbershopId)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return new TransactionDTO(entity);
    }

    public List<TransactionDTO> findByDate(LocalDate date, Long barbershopId) {
        return repository.findByDateAndBarbershopId(date, barbershopId).stream()
                .map(TransactionDTO::new).toList();
    }

    public List<TransactionDTO> findByPeriod(LocalDate start, LocalDate end, Long barbershopId) {
        return repository.findByDateBetweenAndBarbershopId(start, end, barbershopId).stream()
                .map(TransactionDTO::new).toList();
    }

    public TransactionDTO insert(TransactionDTO dto, Long barbershopId) {
        Barbershop shop = barbershopRepository.findById(barbershopId)
                .orElseThrow(() -> new ResourceNotFoundException(barbershopId));

        Transaction entity = new Transaction();
        entity.setType(dto.type());
        entity.setAmount(dto.amount());
        entity.setDate(dto.date());
        entity.setDescription(dto.description());
        entity.setBarbershop(shop);

        entity = repository.save(entity);
        return new TransactionDTO(entity);
    }

    public void delete(Long id, Long barbershopId) {
        Transaction entity = repository.findByIdAndBarbershopId(id, barbershopId)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        repository.delete(entity);
    }
}