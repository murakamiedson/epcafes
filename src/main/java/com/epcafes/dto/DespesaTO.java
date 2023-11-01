package com.epcafes.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.epcafes.enums.TipoCombustivel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DespesaTO {
    
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
	
	private LocalDate mesAno;
	private TipoCombustivel tipoCombustivel;
	private Long maquinaId;
	private String maquinaNome;
}
