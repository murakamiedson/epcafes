package com.epcafes.exception;

public class BusinessException extends CustomException {

    public BusinessException(String codigo, String mensagem) {
        super(codigo, mensagem);
    }
}
