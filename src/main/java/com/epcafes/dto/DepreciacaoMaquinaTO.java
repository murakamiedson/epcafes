package com.epcafes.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepreciacaoMaquinaTO {
	
	/* 
	 * Parte Talhão
	 */
	
	private BigDecimal valorTotalLavoura = new BigDecimal(0);
	private List<BigDecimal> valoresPorTalhao = new ArrayList<>();
	
	/* 
	 * Dados da DepreciacaoMaquina
	 */
	
	private String nome = "";
	private BigDecimal valorBemNovo = new BigDecimal(0);
	private Integer valorResidual = 0;
	private Integer vidaUtilHoras = 0;
	private BigDecimal horasTrabalhadas = new BigDecimal(0);
	private BigDecimal valorDepreciacao = new BigDecimal(0);
	private Float porcentagemUtilizacao = 0.0F;
}
