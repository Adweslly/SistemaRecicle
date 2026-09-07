package com.aps.residuos.web.exception;

import org.springframework.http.HttpStatus;

/**
 * Excecao de negocio lancada quando uma regra da aplicacao e violada.
 * Carrega o status HTTP apropriado para a resposta padronizada.
 */
public class ApiException extends RuntimeException {

    private final HttpStatus status;

    public ApiException(HttpStatus status, String mensagem) {
        super(mensagem);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}