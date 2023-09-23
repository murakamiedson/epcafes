package com.epcafes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tenant {
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO
    )
    private Long codigo;

    @NotBlank(message = "O nome do tenant/produtor é obrigatório.")
    private String nome;
}
