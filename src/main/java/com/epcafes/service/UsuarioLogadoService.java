package com.epcafes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import com.epcafes.dto.UsuarioLogadoDTO;
import com.epcafes.model.Usuario;

@Service
public class UsuarioLogadoService {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private TenantService tenantService;

    public List<UsuarioLogadoDTO> getUsuariosLogados() {
        List<UsuarioLogadoDTO> usuariosLogados = new ArrayList<>();

        Long codigo = 1L;

        for (Object principal : sessionRegistry.getAllPrincipals()) {
            Usuario usuario = (Usuario)principal;
            sessionRegistry.getAllSessions(principal, false).get(0);
            usuariosLogados.add(new UsuarioLogadoDTO(
                codigo,
                usuario.getLogin(),
                usuario.getLogin(),
                usuario.getRole(),
                sessionRegistry.getAllSessions(principal, false).get(0).getLastRequest(),
                tenantService.loadTenantById(usuario.getTenant().getId()).getNome()));
            
            codigo += 1;
        }
        
        if (usuariosLogados.size() == 0) {
            return null;
        }
        return usuariosLogados;
    }
}
