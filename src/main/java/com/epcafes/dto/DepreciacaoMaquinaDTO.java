package com.epcafes.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepreciacaoMaquinaDTO {
	
	private Long id = 1L;
	private BigDecimal horasTrabalhadas = new BigDecimal(0);
	private Float porcentagemUtilizacao = 0.0F;
	private BigDecimal valorDepreciacao = new BigDecimal(0);
	
	private Long idMaquina = 1L;
	private String nome = "";
	private BigDecimal valorBemNovo = new BigDecimal(0);
	private Integer valorResidual = 0;
	private Integer vidaUtilHoras = 0;
}
