package com.epcafes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epcafes.dto.UsuarioLogadoDTO;
import com.epcafes.enums.Grupo;
import com.epcafes.enums.Role;
import com.epcafes.enums.Status;

@Service
public class UsuarioLogadoService {

    @Autowired
    // private UsuarioRepository usuarioRepository;

    public List<UsuarioLogadoDTO> getUsuariosLogados() {
        List<UsuarioLogadoDTO> usuariosLogados = new ArrayList<>();

        List<Long> idsUsuariosLogados = new ArrayList<>();
        idsUsuariosLogados.add(1L);
        idsUsuariosLogados.add(2L);
        idsUsuariosLogados.add(3L);

        for (Long id : idsUsuariosLogados) {
            // Usuario usuario = usuarioRepository.findById(id).orElse(new Usuario());
            usuariosLogados.add (new UsuarioLogadoDTO(
                    1L,
                    "Nome",
                    "nome@email.com",
                    Grupo.TECNICOS,
                    Role.TECNICO,
                    Status.ATIVO, "Propriedade A", "Tenant 1"));
            // usuariosLogados.add(new UsuarioLogadoDTO(
            //         usuario.getCodigo(),
            //         usuario.getNome(),
            //         usuario.getEmail(),
            //         usuario.getGrupo(),
            //         usuario.getRole(),
            //         usuario.getStatus(),
            //         usuario.getUnidade().getNome(),
            //         usuario.getTenant().getNome()));
        }
        
        return usuariosLogados;
    }
}
