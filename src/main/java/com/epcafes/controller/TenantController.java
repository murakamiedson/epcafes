package com.epcafes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.epcafes.dto.TenantDTO;
import com.epcafes.model.Tenant;
import com.epcafes.service.TenantService;

@Controller
public class TenantController {
    @Autowired
    private TenantService tenantService;

    @GetMapping("/CadastrarTenant")
    public String registrar(Tenant tenant){
        return "CadastraTenant";
    }
    
    @PostMapping("/CadastrarTenant")
    public ResponseEntity<?> register(TenantDTO data){
        return tenantService.createTenant(data.nome());
    }

}
