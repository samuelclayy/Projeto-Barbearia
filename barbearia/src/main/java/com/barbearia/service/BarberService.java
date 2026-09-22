package com.barbearia.service;

import com.barbearia.enums.Plan;
import com.barbearia.model.Barber;
import com.barbearia.model.Barbershop;
import com.barbearia.repository.BarberRepository;
import com.barbearia.repository.BarbershopRepository;
import com.barbearia.service.exceptions.PlanLimitException;
import com.barbearia.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarberService {

    @Autowired
    private BarberRepository repository;

    @Autowired
    private BarbershopRepository barbershopRepository;


    public List<Barber> findAll(Long barbershopId) {
        return repository.findByBarbershopId(barbershopId);
    }


    public Barber findById(Long id, Long barbershopId) {
        return repository.findByIdAndBarbershopId(id, barbershopId)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }


    public Barber insert(String name, Long barbershopId) {

        Barbershop shop = barbershopRepository.findById(barbershopId)
                .orElseThrow(() -> new ResourceNotFoundException(barbershopId));


        validatePlanLimit(shop);

        Barber barber = new Barber();
        barber.setName(name);
        barber.setBarbershop(shop);

        return repository.save(barber);
    }

    public Barber update(Long id, String name, Long barbershopId) {
        Barber entity = findById(id, barbershopId);   // ja valida o tenant
        entity.setName(name);
        return repository.save(entity);
    }

    public void delete(Long id, Long barbershopId) {
        Barber entity = findById(id, barbershopId);   // ja valida o tenant
        repository.delete(entity);
    }


    private void validatePlanLimit(Barbershop shop) {
        if (shop.getPlan() == Plan.BASICO) {
            long count = repository.countByBarbershopId(shop.getId());
            if (count >= 1) {
                throw new PlanLimitException(
                        "O plano BASICO permite apenas 1 barbeiro. Faca upgrade para o PRO.");
            }
        }

    }
}