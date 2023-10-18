package com.epcafes.model;

import com.epcafes.enums.UsuarioRole;

public record RegistroDTO(String login, String password, UsuarioRole role){
    
}
