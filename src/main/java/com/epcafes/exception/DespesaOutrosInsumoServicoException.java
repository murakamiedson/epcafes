package com.epcafes.exception;

public class DespesaOutrosInsumoServicoException extends TechnicalException{
	
	private static final long serialVersionUID = -7454825897059078491L;

	private static String getExceptionCodeMessage(String code) {
		if(code.equals("1337")) return "Nenhuma propriedade achada para o TenantID do usuário logado.";
		else if(code.equals("413")) return "Id de despesa inexistente.";
		else return ("Exceção de DespesaOutrosInsumoServico com código " + code);
	}
	
	public DespesaOutrosInsumoServicoException(String code) {
		super(code, getExceptionCodeMessage(code));
	}
}