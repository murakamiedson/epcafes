package com.epcafes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.epcafes.dto.RegistroDTO;
import com.epcafes.enums.Status;
import com.epcafes.model.Propriedade;
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

    public boolean createUser(RegistroDTO usuario, Tenant tenant, Propriedade propriedade){
        if (this.repository.findByLogin(usuario.login())!= null)
            return false;
        else{
            String encryptedPassword = new BCryptPasswordEncoder().encode(usuario.password());
            Usuario newUser = new Usuario(usuario.nome(), usuario.login(), encryptedPassword, usuario.role(), Status.ATIVO, tenant, propriedade);
            this.repository.save(newUser);
            return true;
        }
    }
    
}
