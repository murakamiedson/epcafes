package com.epcafes.model;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.epcafes.enums.Status;
import com.epcafes.enums.UsuarioRole;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O nome é obrigatório")
	private String nome;

	private String registroProfissional;

	@Email
	@Column(unique = true)
	private String login;

	private String password;

	@Enumerated(EnumType.STRING)
	private UsuarioRole role;

	@Enumerated(EnumType.STRING)
	private Status status;

	@ManyToOne
	private Tenant tenant;

	@ManyToOne
	@JoinColumn(name = "codigo_unidade")
	private Propriedade propriedade;

	public Usuario(String login, String password, UsuarioRole role, Tenant tenant) {
		this.login = login;
		this.password = password;
		this.role = role;
		this.tenant = tenant;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.role == UsuarioRole.ADMIN)
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_GESTOR"),
					new SimpleGrantedAuthority("ROLE_TECNICO"));
		else if (this.role == UsuarioRole.GESTOR)
			return List.of(new SimpleGrantedAuthority("ROLE_GESTOR"), new SimpleGrantedAuthority("ROLE_TECNICO"));
		else
			return List.of(new SimpleGrantedAuthority("ROLE_TECNICO"));
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
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
