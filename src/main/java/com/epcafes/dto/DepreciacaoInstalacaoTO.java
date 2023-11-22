package com.epcafes.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepreciacaoInstalacaoTO {
	
	/* 
	 * Parte Talhão
	 */
	
	private List<BigDecimal> valoresPorTalhao = new ArrayList<>();
	
	/* 
	 * Dados da DepreciacaoInstalacao
	 */
	
	private String nome = "";
	private Float valorBemNovo = 0.0F;
	private Integer valorResidual = 0;
	private Integer vidaUtilAnos = 0;
	private Float porcentagemUtilizacao = 0.0F;
	private BigDecimal valorDepreciacao = new BigDecimal(0);
}
