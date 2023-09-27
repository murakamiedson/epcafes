package com.epcafes.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class DespesaInsumoServico implements Serializable{

	@Id
	private long id;	

	private LocalDate mesAno;

	private String notaFiscal;

	private long tenantId;

	private String descricao;
	
	@Getter
	private int valor;

	public int getValor(){
		return valor;
	}

}
