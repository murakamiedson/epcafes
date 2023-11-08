package com.epcafes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.epcafes.dto.RegistroDTO;
import com.epcafes.model.Tenant;
import com.epcafes.model.Usuario;
import com.epcafes.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }

    public boolean createUser(RegistroDTO usuario, Tenant tenant){
        if (this.repository.findByLogin(usuario.login())!= null)
            return false;
        else{
            String encryptedPassword = new BCryptPasswordEncoder().encode(usuario.password());
            Usuario newUser = new Usuario(usuario.login(), encryptedPassword, usuario.nome(), usuario.role(), tenant);
            this.repository.save(newUser);
            return true;
        }
    }
    
}
