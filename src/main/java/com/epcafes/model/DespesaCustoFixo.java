package com.epcafes.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class DespesaCustoFixo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long tenant_id;
	private BigDecimal valor;
	private LocalDate mesAno;
	
	@ManyToOne
	private CustoFixo custoFixo;
	
	public DespesaCustoFixo() {}

	public DespesaCustoFixo(Long tentant_id, BigDecimal valor, LocalDate mesAno, CustoFixo custoFixo) {
		this.tenant_id = tentant_id;
		this.valor = valor;
		this.mesAno = mesAno;
		this.custoFixo = custoFixo;
	}
}
