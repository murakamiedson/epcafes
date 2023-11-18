package com.epcafes.model;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@NamedQueries({
	@NamedQuery(name="Instalacao.buscarInstalacoes", query="select u from Instalacao u where u.tenant_id = :tenantId"),
	@NamedQuery(name="Instalacao.buscarPorUnidade", query="select u from Instalacao u where u.propriedade = :unidade "
			+ "and u.tenant_id = :tenantId")
})
public class Instalacao {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private Long tenant_id;
		
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@PositiveOrZero
	private float valor;
	@PositiveOrZero
	private int vidaUtilAnos;
	@PositiveOrZero
	private int valorResidual;
	@NotNull
	@ManyToOne
	@JoinColumn(nullable = false, name="codigo_propriedade")
	private Propriedade propriedade;

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
