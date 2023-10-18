package com.epcafes.dto;

import com.epcafes.enums.Grupo;
import com.epcafes.enums.Role;
import com.epcafes.enums.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioLogadoDTO {
	private Long codigo;

	private String nome;

	private String email;

	@Enumerated(EnumType.STRING)
	private Grupo grupo;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Enumerated(EnumType.STRING)
	private Status status;

	private String nomePropriedade;

	private String nomeTenant;
}
