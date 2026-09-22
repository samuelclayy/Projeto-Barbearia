package com.barbearia.service;

import com.barbearia.dto.ServiceDTO;
import com.barbearia.model.Barbershop;
import com.barbearia.model.Service;
import com.barbearia.repository.BarbershopRepository;
import com.barbearia.repository.ServiceRepository;
import com.barbearia.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {

    @Autowired
    private ServiceRepository repository;

    @Autowired
    private BarbershopRepository barbershopRepository;

    public List<ServiceDTO> findAll(Long barbershopId) {
        List<Service> list = repository.findByBarbershopId(barbershopId);
        return list.stream().map(ServiceDTO::new).toList();
    }

    public ServiceDTO findById(Long id, Long barbershopId) {
        Service entity = repository.findByIdAndBarbershopId(id, barbershopId)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return new ServiceDTO(entity);
    }

    public ServiceDTO insert(ServiceDTO dto, Long barbershopId) {
        Barbershop shop = barbershopRepository.findById(barbershopId)
                .orElseThrow(() -> new ResourceNotFoundException(barbershopId));

        Service entity = new Service();
        copyDtoToEntity(dto, entity);
        entity.setBarbershop(shop);

        entity = repository.save(entity);
        return new ServiceDTO(entity);
    }

    public ServiceDTO update(Long id, ServiceDTO dto, Long barbershopId) {
        Service entity = repository.findByIdAndBarbershopId(id, barbershopId)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ServiceDTO(entity);
    }

    public void delete(Long id, Long barbershopId) {
        Service entity = repository.findByIdAndBarbershopId(id, barbershopId)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        repository.delete(entity);
    }

    private void copyDtoToEntity(ServiceDTO dto, Service entity) {
        entity.setName(dto.name());
        entity.setPrice(dto.price());
        entity.setDurationMinutes(dto.durationMinutes());
    }
}