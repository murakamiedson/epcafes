package com.epcafes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.epcafes.repository.UsuarioRepository;

public class AuthProvider implements AuthenticationProvider {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UserDetails user;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        user = usuarioRepository.findByLogin(authentication.getName());
        if ((user==null)&&(passwordEncoder.matches(authentication.getCredentials().toString(),user.getPassword())==false)){
            throw new BadCredentialsException("Senha ou login incorretos");
        }
        else{
            return authentication;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    
}
