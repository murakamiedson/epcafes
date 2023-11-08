package com.epcafes.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class DepreciacaoMaquina {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long tenant_id;
	
	@NotNull
	private BigDecimal horasTrabalhadas;
	
	@NotNull
	private Float porcentagemUtilizacao;
	
	private BigDecimal valorDepreciacao;
	
	@ManyToOne
	private Maquina maquina;
}
