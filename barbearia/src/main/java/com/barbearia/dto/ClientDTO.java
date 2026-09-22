package com.barbearia.dto;

import com.barbearia.model.Client;
import jakarta.validation.constraints.NotBlank;

public record ClientDTO(
        Long id,
        @NotBlank String name,
        @NotBlank String phone
) {
    public ClientDTO(Client entity) {
        this(entity.getId(), entity.getName(), entity.getPhone());
    }
}
