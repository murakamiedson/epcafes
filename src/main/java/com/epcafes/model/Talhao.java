package com.epcafes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Talhao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long tenant_id;
	
	@NotNull
	@NotBlank
	private String nome;
	
	@NotNull
	private Float area;
	
	@ManyToOne
	private Propriedade propriedade;
	
	
}
