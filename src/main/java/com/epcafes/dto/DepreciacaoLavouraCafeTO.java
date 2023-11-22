package com.epcafes.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepreciacaoLavouraCafeTO {
	
	/* 
	 * Parte Talhão
	 */
	
	private List<BigDecimal> valoresPorTalhao = new ArrayList<>();
	
	/* 
	 * Dados da DepreciacaoLavouraCafe
	 */
	
	private String descricao = "";
	private BigDecimal custoImplantacao = new BigDecimal(0);
	private BigDecimal receitasObtidas = new BigDecimal(0);
	private Integer vidaUtilAnos = 0;
	private BigDecimal valorDepreciacao = new BigDecimal(0);
}
