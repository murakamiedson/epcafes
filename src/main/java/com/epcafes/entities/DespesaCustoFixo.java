package com.epcafes.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class DespesaCustoFixo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal valor;
	private LocalDate mesAno;
	
	@ManyToOne
	private CustoFixo custoFixo;
	
	public DespesaCustoFixo() {}

	public DespesaCustoFixo(BigDecimal valor, LocalDate mesAno, CustoFixo custoFixo) {
		this.valor = valor;
		this.mesAno = mesAno;
		this.custoFixo = custoFixo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDate getMesAno() {
		return mesAno;
	}

	public void setMesAno(LocalDate mesAno) {
		this.mesAno = mesAno;
	}

	public CustoFixo getCustoFixo() {
		return custoFixo;
	}

	public void setCustoFixo(CustoFixo custoFixo) {
		this.custoFixo = custoFixo;
	}
}
