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

import com.epcafes.model.Propriedade;
import com.epcafes.model.Usuario;
import com.epcafes.service.PropriedadeService;
import com.epcafes.service.UsuarioService;


@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UsuarioService service;
    @Autowired
    private PropriedadeService propriedade;

   @GetMapping
   public String fazerLogin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/epcafes";
        }
        return "login";
    }

    @GetMapping("/propriedade")
    public String escolherPropriedade(@AuthenticationPrincipal Usuario userDetails, Model model){
        Long tenant = userDetails.getTenant().getId();
        List<Propriedade> propriedades = propriedade.findByTenantId(tenant);
        model.addAttribute("propriedades", propriedades);

        return "loginpropriedade";
    }

    @PostMapping("/propriedade")
    public String postPropriedade(Long propriedadeId, @AuthenticationPrincipal Usuario userDetails) {
        userDetails.setPropriedade(propriedade.findById(propriedadeId).get());
        service.changeProperty(userDetails);
        return "redirect:/epcafes";
    }
    

}
