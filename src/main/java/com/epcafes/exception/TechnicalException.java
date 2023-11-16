package com.epcafes.exception;

public class TechnicalException extends CustomException {

	private static final long serialVersionUID = -4874466904481190104L;

	public TechnicalException(String codigo, String mensagem) {
        super(codigo, mensagem);
    }
}
