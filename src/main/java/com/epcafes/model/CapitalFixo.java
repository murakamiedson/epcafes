package com.epcafes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class CapitalFixo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private double valorBemNovo;
	
	@NotNull
	private double taxaPoupanca;
	
	@NotNull
	private int vidaHoras;
	
	@NotNull
	private int vidaAnos;
	
	@NotNull
	private int horasTrabalhadas;
	
	@Column(updatable=false, insertable=true)
	private double remuneracao = calculaRemuneracao(valorBemNovo, taxaPoupanca, vidaHoras, vidaAnos, horasTrabalhadas);
	
	private double calculaRemuneracao(double valorBemNovo, double taxaPoupanca, int vidaHoras, int vidaAnos, int horasTrabalhadas) {
		return (((valorBemNovo/2))*taxaPoupanca)/(vidaHoras*vidaAnos) * horasTrabalhadas;
	}
}
