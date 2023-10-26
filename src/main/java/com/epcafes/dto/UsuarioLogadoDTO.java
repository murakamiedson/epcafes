package com.epcafes.dto;

import java.util.Date;

import com.epcafes.enums.UsuarioRole;

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
	
	private Long id;
	private String nome;
	private String email;
	@Enumerated(EnumType.STRING)
	private UsuarioRole usuarioRole;
	private Date ultimoAcesso;
}
