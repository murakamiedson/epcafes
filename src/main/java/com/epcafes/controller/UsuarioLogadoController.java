package com.epcafes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.epcafes.service.UsuarioLogadoService;

import lombok.extern.java.Log;

@Log
@Controller
public class UsuarioLogadoController {
    
    @Autowired
    private UsuarioLogadoService usuarioLogadoService;

    @GetMapping("/restricted/usuario/UsuarioLogado")
    public String usuariosLogados(Model model) {
        log.info("acessando página de usuários logados");

        model.addAttribute("usuariosLogados", usuarioLogadoService.getUsuariosLogados());
        return "restricted/usuario/UsuarioLogado";
    }
}
