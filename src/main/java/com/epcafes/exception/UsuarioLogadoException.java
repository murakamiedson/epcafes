package com.epcafes.exception;

public class UsuarioLogadoException extends TechnicalException {
    public UsuarioLogadoException() {
        super("123", "Não há usuários logados");
    }
}
