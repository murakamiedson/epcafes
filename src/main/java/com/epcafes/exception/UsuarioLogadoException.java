package com.epcafes.exception;

public class UsuarioLogadoException extends TechnicalException {
	private static final long serialVersionUID = 6869489007335953554L;

	public UsuarioLogadoException() {
        super("123", "Não há usuários logados");
    }
}
