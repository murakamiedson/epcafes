package com.epcafes.model;

import com.epcafes.model.enums.Grupo;
import com.epcafes.model.enums.Role;
import com.epcafes.model.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo;

    @NotBlank(message="O nome é obrigatório")
	private String nome;
    
    private String registroProfissional;
	
	@Email
	@Column(unique=true)
	private String email;

	private String senha;

    @Enumerated(EnumType.STRING)
	private Grupo grupo;
	
	@Enumerated(EnumType.STRING)
	private Role role;

    @ManyToOne
	@JoinColumn(name="codigo_unidade")
	private Unidade unidade;

    @ManyToOne
	@JoinColumn(name="tenant_id")
	private Tenant tenant;

    @Enumerated(EnumType.STRING)
	private Status status;
}
