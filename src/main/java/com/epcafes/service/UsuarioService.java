package com.epcafes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.epcafes.dto.RegistroDTO;
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

    public ResponseEntity<?> createUser(RegistroDTO usuario, long tenant_id){
        if (this.repository.findByLogin(usuario.login())!= null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(usuario.password());
        Usuario newUser = new Usuario(usuario.login(), encryptedPassword, usuario.role(), tenant_id);
        this.repository.save(newUser);
        return ResponseEntity.ok().build();
    }
    
}
