package com.epcafes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epcafes.dto.RegistroDTO;
import com.epcafes.enums.TipoPropriedade;
import com.epcafes.enums.UsuarioRole;
import com.epcafes.model.Endereco;
import com.epcafes.model.Propriedade;
import com.epcafes.model.Tenant;
import com.epcafes.model.Usuario;
import com.epcafes.service.PropriedadeService;
import com.epcafes.service.TenantService;
import com.epcafes.service.UsuarioService;


@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UsuarioService service;
    @Autowired
    private PropriedadeService propriedade;
    @Autowired
    private TenantService tenant;

   @GetMapping
   public String fazerLogin(){
    if (service.loadUserByUsername("admin@admin.com")==null){ //TEMPORÁRIO: CRIA USUARIO PADRAO SE NAO EXISTIR
            RegistroDTO registroDTO = new RegistroDTO("admin@admin.com", "admin", "admin user", UsuarioRole.ADMIN);
            Tenant newTenant = tenant.createTenant("Edson Murakami");
            Endereco newEndereco = new Endereco("Av. dos Três Poderes", 375L, "SP");
            Propriedade newPropriedade = new Propriedade(newTenant.getId(), "Fazenda IFSP", "Edson Murakami", TipoPropriedade.FAZENDA, newEndereco);
            propriedade.salvar(newPropriedade);
            service.createUser(registroDTO, newTenant, newPropriedade);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/epcafes";
        }
        return "restricted/usuario/login";
    }

    @GetMapping("/propriedade")
    public String escolherPropriedade(@AuthenticationPrincipal Usuario userDetails, Model model){
        Long tenant = userDetails.getTenant().getId();
        List<Propriedade> propriedades = propriedade.findByTenantId(tenant);
        model.addAttribute("propriedades", propriedades);

        return "restricted/usuario/loginpropriedade";
    }

    @PostMapping("/propriedade")
    public String postPropriedade(Long propriedadeId, @AuthenticationPrincipal Usuario userDetails) {
        userDetails.setPropriedade(propriedade.findById(propriedadeId).get());
        service.changeProperty(userDetails);
        return "redirect:/epcafes";
    }
    

}
