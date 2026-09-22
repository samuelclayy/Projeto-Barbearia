package com.barbearia.service;

import com.barbearia.dto.ClientDTO;
import com.barbearia.model.Barbershop;
import com.barbearia.model.Client;
import com.barbearia.repository.BarbershopRepository;
import com.barbearia.repository.ClientRepository;
import com.barbearia.service.exceptions.PhoneAlreadyExistsException;
import com.barbearia.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private BarbershopRepository barbershopRepository;



    public List<ClientDTO> findAll(Long barbershopId) {
        List<Client> list = repository.findByBarbershopId(barbershopId);
        return list.stream().map(ClientDTO::new).toList();
    }

    public ClientDTO findById(Long id, Long barbershopId) {
        Client entity = repository.findByIdAndBarbershopId(id, barbershopId)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return new ClientDTO(entity);
    }

    public ClientDTO insert(ClientDTO dto, Long barbershopId) {
        Barbershop shop = barbershopRepository.findById(barbershopId)
                .orElseThrow(() -> new ResourceNotFoundException(barbershopId));

        if (repository.existsByPhoneAndBarbershopId(dto.phone(), barbershopId)) {
            throw new PhoneAlreadyExistsException(dto.phone());
        }

        Client entity = new Client();
        entity.setName(dto.name());
        entity.setPhone(dto.phone());
        entity.setBarbershop(shop);
        entity = repository.save(entity);
        return new ClientDTO(entity);
    }

    public ClientDTO update(Long id, ClientDTO dto, Long barbershopId) {
        Client entity = repository.findByIdAndBarbershopId(id, barbershopId)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        repository.findByPhoneAndBarbershopId(dto.phone(), barbershopId)
                .filter(other -> !other.getId().equals(id))
                .ifPresent(other -> {
                    throw new PhoneAlreadyExistsException(dto.phone());
                });

        entity.setName(dto.name());
        entity.setPhone(dto.phone());
        entity = repository.save(entity);
        return new ClientDTO(entity);
    }

    public void deleteById(Long id, Long barbershopId) {
        Client entity = repository.findByIdAndBarbershopId(id, barbershopId)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        repository.delete(entity);
    }
}