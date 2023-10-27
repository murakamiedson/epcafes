package com.epcafes.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.epcafes.enums.UsuarioRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@SuppressWarnings("serial")
@Table(name="users")
@Entity(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails{
	
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private UsuarioRole role;
    private long tenant_id = 1;

    public Usuario(String login, String password, UsuarioRole role, long tenant_id){
        this.login = login;
        this.password = password;
        this.role = role;
        this.tenant_id = tenant_id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UsuarioRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_GESTOR"), new SimpleGrantedAuthority("ROLE_TECNICO"));
        else if (this.role == UsuarioRole.GESTOR) return List.of(new SimpleGrantedAuthority("ROLE_GESTOR"), new SimpleGrantedAuthority("ROLE_TECNICO"));
        else return List.of(new SimpleGrantedAuthority("ROLE_TECNICO"));
    }
    @Override
    public String getUsername() {
        return login;
    }
    public long getTenant_id(){
        return tenant_id;
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
}
