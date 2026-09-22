package com.barbearia.service;

import com.barbearia.dto.PayableDTO;
import com.barbearia.enums.AccountStatus;
import com.barbearia.model.Barbershop;
import com.barbearia.model.Payable;
import com.barbearia.repository.BarbershopRepository;
import com.barbearia.repository.PayableRepository;
import com.barbearia.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayableService {

    @Autowired
    private PayableRepository repository;

    @Autowired
    private BarbershopRepository barbershopRepository;

    public List<PayableDTO> findAll(Long barbershopId) {
        List<Payable> list = repository.findByBarbershopId(barbershopId);
        return list.stream().map(PayableDTO::new).toList();
    }

    public PayableDTO findById(Long id, Long barbershopId) {
        Payable entity = repository.findByIdAndBarbershopId(id, barbershopId)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return new PayableDTO(entity);
    }

    public PayableDTO insert(PayableDTO dto, Long barbershopId) {
        Barbershop shop = barbershopRepository.findById(barbershopId)
                .orElseThrow(() -> new ResourceNotFoundException(barbershopId));

        Payable entity = new Payable();
        entity.setDescription(dto.description());
        entity.setAmount(dto.amount());
        entity.setDueDate(dto.dueDate());
        entity.setStatus(AccountStatus.PENDENTE);   // servidor define
        entity.setBarbershop(shop);                 // amarra na barbearia

        entity = repository.save(entity);
        return new PayableDTO(entity);
    }

    public PayableDTO markAsPaid(Long id, Long barbershopId) {
        Payable entity = repository.findByIdAndBarbershopId(id, barbershopId)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        entity.setStatus(AccountStatus.PAGO);
        entity = repository.save(entity);
        return new PayableDTO(entity);
    }

    public void delete(Long id, Long barbershopId) {
        Payable entity = repository.findByIdAndBarbershopId(id, barbershopId)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        repository.delete(entity);
    }
}