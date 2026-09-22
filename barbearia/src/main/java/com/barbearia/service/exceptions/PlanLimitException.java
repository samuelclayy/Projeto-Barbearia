package com.barbearia.service.exceptions;

/**
 * Lancada quando a barbearia tenta fazer algo que o plano dela nao permite.
 * Ex: plano BASICO tentando cadastrar um 2o barbeiro.
 */
public class PlanLimitException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PlanLimitException(String msg) {
        super(msg);
    }
}