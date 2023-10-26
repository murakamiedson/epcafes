package com.epcafes.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UsuarioRole {
    ADMIN("admin"),
    GESTOR("gestor"),
    TECNICO("tecnico");

    private @Getter String role;

}
