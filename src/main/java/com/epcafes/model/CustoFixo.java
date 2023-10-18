package com.epcafes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CustoFixo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long tenant_id;
	
	@NotNull
	@NotBlank
	private String nome;
	
	@ManyToOne
	private Unidade unidade;
	
	public CustoFixo() {}
	
	public CustoFixo(String nome, Long tenant_id) {
		
		this.nome = nome;
		this.tenant_id = tenant_id;
	}
}
