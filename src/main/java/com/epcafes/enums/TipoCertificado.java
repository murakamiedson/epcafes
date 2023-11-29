package com.epcafes.enums;

public enum TipoCertificado {
    SUPERIOR("SUPERIOR"),
    MEDIO ("MEDIO"),
    FUNDAMENTAL("FUNDAMENTAL"),
    EXTENSAO("EXTENSAO"),
    OUTRO("OUTRO");

    private final String tipoCertificadoCode;

    private TipoCertificado(String tipoCertificadoCode) {
        this.tipoCertificadoCode = tipoCertificadoCode;

    }

    public String getTipoCertificadoCode() {
        return tipoCertificadoCode;
    }

}

