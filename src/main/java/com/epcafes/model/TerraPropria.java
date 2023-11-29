package com.epcafes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class TerraPropria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private double valorTerraNua;
	
	@NotNull
	private double taxaPoupanca;
	
	@NotNull
    @Min(value = 1, message = "O valor de safrasAnos deve ser maior que zero")
	private int safrasAnos;
	
	@NotNull
	private int percTerraPropria;
	
	private double remuneracao;
	
}
