package com.epcafes.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class DespesaOutrosInsumoServico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate mesAno;
	private BigDecimal porcUtilizacao;
	private BigDecimal valor;
	private String notaFiscal;
	private Long tenantId;
	private String descricao;

	@NotNull
	@ManyToOne
	private Propriedade propriedade;	
	
}
