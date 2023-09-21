package com.epcafes.model;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;


public class DespesaInsumoServico implements Serializable{
	
	@Getter
	@Setter
	private LocalDate mesAno;

	@Getter
	@Setter
	private String notaFiscal;

	@Getter
	@Setter
	private long tenantId;

	@Getter
	@Setter
	private String descricao;

	@Getter
	@Setter
	private int valor;

}
