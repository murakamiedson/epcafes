package com.epcafes.enums;

public enum UsuarioRole {
    ADMIN("admin"),
    GESTOR("gestor"),
    TECNICO("tecnico");

    private String role;

    UsuarioRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
