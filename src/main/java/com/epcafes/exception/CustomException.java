package com.epcafes.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends Exception {
    private String codigo;
    private String mensagem;

    public CustomException(String codigo, String mensagem) {
        super();
        this.codigo = codigo;
        this.mensagem = mensagem;
    }
}
