package com.epcafes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.epcafes.dto.RegistroDTO;
import com.epcafes.enums.UsuarioRole;
import com.epcafes.service.TenantService;
import com.epcafes.service.UsuarioService;

@Controller
public class LoginController {
    @Autowired
    private UsuarioService service;
    @Autowired
    private TenantService tenant;

   @GetMapping("/login")
   public String fazerLogin(){
        if (service.loadUserByUsername("admin")==null){ //TEMPORÁRIO: CRIA USUARIO PADRAO SE NAO EXISTIR
            RegistroDTO registroDTO = new RegistroDTO("admin", "admin", UsuarioRole.ADMIN);
            service.createUser(registroDTO, tenant.createTenant("Edson Murakami"));
        }
        return "login";
    }
}
