package com.epcafes.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends Exception {

	private static final long serialVersionUID = -333801766555883828L;
	private String codigo;
    private String mensagem;

    public CustomException(String codigo, String mensagem) {
        super();
        this.codigo = codigo;
        this.mensagem = mensagem;
    }
}
