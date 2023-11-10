package com.epcafes.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DespesaCustoFixoDTO {
	
	private Long id = 1L;
	private BigDecimal valor = new BigDecimal(0);
	private LocalDate mesAno;
	private Float porcentagemUtilizacao;
	
	private Long idCustoFixo= 1L;
	private String nomeCustoFixo = "";
}
