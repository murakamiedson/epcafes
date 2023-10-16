package com.epcafes.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class DespesaInsumoServico implements Serializable{

	@Id
	private long id;	

	private LocalDate mesAno;

	private double porcUtilizacao;

	private String notaFiscal;

	private long tenantId;

	private String descricao;

	@NotNull
	@ManyToOne
	private Propriedade propriedade;
	
	@Getter
	private int valor;

	public int getValor(){
		return valor;
	}

	public double getPorcUtilizacao(){
		return this.porcUtilizacao;
	}

	public String getNotaFiscal(){
		return this.notaFiscal;
	}

	public String getDescricao(){
		return this.descricao;
	}
}
