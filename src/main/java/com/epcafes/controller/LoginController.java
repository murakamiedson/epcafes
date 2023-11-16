package com.epcafes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.epcafes.dto.RegistroDTO;
import com.epcafes.enums.TipoPropriedade;
import com.epcafes.enums.UsuarioRole;
import com.epcafes.model.Propriedade;
import com.epcafes.model.Tenant;
import com.epcafes.service.PropriedadeService;
import com.epcafes.service.TenantService;
import com.epcafes.service.UsuarioService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class LoginController {
    @Autowired
    private UsuarioService service;
    @Autowired
    private TenantService tenant;
    @Autowired
    private PropriedadeService propriedade;

    @GetMapping("/login")
    public String fazerLogin() {
        if (service.loadUserByUsername("admin@admin.com") == null) { // TEMPORÁRIO: CRIA USUARIO PADRAO SE NAO EXISTIR
            log.info("Rodando o if");
            RegistroDTO registroDTO = new RegistroDTO("admin@admin.com", "admin", "admin user", UsuarioRole.ADMIN);
            Tenant newTenant = tenant.createTenant("Edson Murakami");
            Propriedade newPropriedade = new Propriedade(newTenant.getId(), "Fazenda IFSP", "Edson Murakami",
                    TipoPropriedade.FAZENDA);
            propriedade.salvar(newPropriedade);
            service.createUser(registroDTO, newTenant, newPropriedade);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); // Checa se usuário já está logado
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/epcafes";
        }
        return "login";
    }

}
