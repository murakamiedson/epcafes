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
	private Long id;	

	private LocalDate mesAno;

	private Double porcUtilizacao;

	private String notaFiscal;

	private Long tenantId;

	private String descricao;

	@NotNull
	@ManyToOne
	private Unidade unidade;
	
	@Getter
	private Integer valor;

	public Integer getValor(){
		return valor;
	}

	public Double getPorcUtilizacao(){
		return this.porcUtilizacao;
	}

	public String getNotaFiscal(){
		return this.notaFiscal;
	}

	public String getDescricao(){
		return this.descricao;
	}
}
