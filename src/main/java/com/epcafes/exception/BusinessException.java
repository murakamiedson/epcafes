package com.epcafes.exception;

public class BusinessException extends CustomException {

	private static final long serialVersionUID = -5728330899236703196L;

	public BusinessException(String codigo, String mensagem) {
        super(codigo, mensagem);
    }
}
