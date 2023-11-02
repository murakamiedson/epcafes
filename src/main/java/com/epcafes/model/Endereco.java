package com.epcafes.model;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Endereco implements Cloneable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank(message="O endereço é obrigatório.")
	private String endereco;
	
	@NotNull(message="O número é obrigatório.")
	private Long numero;
	private String complemento;

	private String bairro;
	
	private String cep;
	
	private String municipio;
	
	@NotBlank(message="A UF é obrigatória.")
	private String uf;
	private String referencia;
	private String telefoneContato;
	
	@Override
	public Endereco clone() throws CloneNotSupportedException {
		return (Endereco) super.clone();
	}
	
	public String toString() {
		return endereco + ", " + numero + ". " + bairro + " - " + municipio + "/" + uf + ". CEP: " + cep;
	}	
	
	/*
	 * Datas de Criação e Modificação
	 */
	
	@CreationTimestamp	
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataCriacao;
	
	@UpdateTimestamp
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataModificacao;
}
