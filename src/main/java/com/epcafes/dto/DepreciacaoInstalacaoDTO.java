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
public class DepreciacaoInstalacaoDTO {
	
	private Long id = 1L;
	private Float porcentagemUtilizacao = 0.0F;
	private BigDecimal valorDepreciacao = new BigDecimal(0);
	
	private Long idInstalacao = 1L;
	private String nome = "";
	private Float valorBemNovo = 0.0F;
	private Integer valorResidual = 0;
	private Integer vidaUtilAnos = 0;
}
