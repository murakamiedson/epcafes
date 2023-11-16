package com.epcafes.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DespesaCustoFixoTO {
	
	/* 
	 * Parte Por Mês
	 */
	
	private BigDecimal valorTotalJan = new BigDecimal(0);
	private BigDecimal valorTotalFev = new BigDecimal(0);
	private BigDecimal valorTotalMar = new BigDecimal(0);
	private BigDecimal valorTotalAbr = new BigDecimal(0);
	private BigDecimal valorTotalMai = new BigDecimal(0);
	private BigDecimal valorTotalJun = new BigDecimal(0);
	private BigDecimal valorTotalJul = new BigDecimal(0);
	private BigDecimal valorTotalAgo = new BigDecimal(0);
	private BigDecimal valorTotalSet = new BigDecimal(0);
	private BigDecimal valorTotalOut = new BigDecimal(0);
	private BigDecimal valorTotalNov = new BigDecimal(0);
	private BigDecimal valorTotalDez = new BigDecimal(0);
	private BigDecimal valorTotalAnual = new BigDecimal(0);
	
	/* 
	 * Parte Talhão
	 */
	
	private BigDecimal valorTotalLavoura = new BigDecimal(0);
	private List<BigDecimal> valoresPorTalhao = new ArrayList<>();
	private BigDecimal valorTotalPorTalhao = new BigDecimal(0);
	
	/* 
	 * Dados do CustoFixo
	 */
	
	private String nomeCustoFixo = "";
	private Float porcentagemUtilizacao = 0.0f;
}
