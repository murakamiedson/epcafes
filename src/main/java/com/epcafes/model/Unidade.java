package com.epcafes.model;

import com.epcafes.model.enums.TipoPropriedade;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Unidade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo;

    @NotBlank(message = "O nome da unidade é obrigatório.")
    private String nome;

    private String contato;

    @ManyToOne
	@JoinColumn(name="tenant_id")
	private Tenant tenant;

    @Enumerated(EnumType.STRING)
    private TipoPropriedade tipo;

    private String endereco;
}
