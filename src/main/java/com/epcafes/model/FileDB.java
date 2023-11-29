package com.epcafes.model;

import com.epcafes.enums.TipoCertificado;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "files")
public class FileDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String uuidRegistrado;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private Long idFuncionario;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private TipoCertificado tipoCertificado;

    public FileDB() {}

    public FileDB(String name, String uuidRegistrado, String type, String url, Long idFuncionario, TipoCertificado tipoCertificado) {
        this.name = name;
        this.uuidRegistrado = uuidRegistrado;
        this.type = type;
        this.url = url;
        this.idFuncionario = idFuncionario;
        this.tipoCertificado = tipoCertificado;
    }
}