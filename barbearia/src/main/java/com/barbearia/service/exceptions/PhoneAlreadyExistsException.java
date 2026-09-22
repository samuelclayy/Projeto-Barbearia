package com.barbearia.service.exceptions;

public class PhoneAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PhoneAlreadyExistsException(String phone) {
        super("Ja existe um cliente com o telefone: " + phone);
    }
}
