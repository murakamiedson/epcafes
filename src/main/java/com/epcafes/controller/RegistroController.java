package com.epcafes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.epcafes.dto.RegistroDTO;
import com.epcafes.model.Usuario;
import com.epcafes.service.UsuarioService;



@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registrar")
    public String registrar(Usuario usuario, @AuthenticationPrincipal Usuario userDetails){
        return "restricted/usuario/registrar";
    }
    
    @PostMapping("/registrar")
    public String postRegistrar(RegistroDTO data, @AuthenticationPrincipal Usuario userDetails){
        if (usuarioService.createUser(data, userDetails.getTenant(), userDetails.getPropriedade())) {
            return "redirect:/registrar?success";
        }
        else{
            return "redirect:/registrar?error";
        }
    }
}
