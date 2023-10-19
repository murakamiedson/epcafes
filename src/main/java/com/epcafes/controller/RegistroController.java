package com.epcafes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.epcafes.dto.RegistroDTO;
import com.epcafes.model.Usuario;
import com.epcafes.repository.UsuarioRepository;



@Controller
public class RegistroController {
    @Autowired
    private UsuarioRepository userRepository;


    @GetMapping("/register")
    public String registrar(Usuario usuario){
        return "register";
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(RegistroDTO data){
        if (this.userRepository.findByLogin(data.login())!= null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuario newUser = new Usuario(data.login(), encryptedPassword, data.role());
        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }
}
