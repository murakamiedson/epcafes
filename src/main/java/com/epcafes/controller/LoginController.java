package com.epcafes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
            RegistroDTO registroDTO = new RegistroDTO("admin@admin.com", "admin", "admin user", UsuarioRole.ADMIN);
            service.createUser(registroDTO, tenant.createTenant("Edson Murakami"));
        }
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // Checa se usuário já está logado
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/epcafes";
        }
        return "login";
    }

}
