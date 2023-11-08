package com.epcafes.exception;

public class UsuarioLogadoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UsuarioLogadoException() {
        super("Não há usuários logados");
    }
}
