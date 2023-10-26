package com.epcafes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.epcafes.model.Usuario;
import com.epcafes.repository.UsuarioRepository;

public class AuthProvider implements AuthenticationProvider {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private Usuario usuario;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        usuario = (Usuario) usuarioRepository.findByLogin(authentication.getName());
        if ((usuario==null)&&(passwordEncoder.matches(authentication.getCredentials().toString(),usuario.getPassword())==false)){
            throw new BadCredentialsException("Senha ou login incorretos");
        }
        else{
            return new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    
}
