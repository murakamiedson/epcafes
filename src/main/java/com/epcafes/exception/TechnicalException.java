package com.epcafes.exception;

public class TechnicalException extends CustomException {

    public TechnicalException(String codigo, String mensagem) {
        super(codigo, mensagem);
    }
}
