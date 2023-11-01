package com.epcafes.exception;

public class UsuarioLogadoException extends RuntimeException {
    public UsuarioLogadoException() {
        super("Não há usuários logados");
    }
}
