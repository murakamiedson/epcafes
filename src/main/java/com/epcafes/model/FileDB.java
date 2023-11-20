package com.epcafes.model;

import jakarta.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "files")
public class FileDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    private String tipo;

    private String url;

    private Long idFuncionario;

    public FileDB() {
    }

    public FileDB(String name, String path) {
        this.name = name;
        this.url = path;
    }

    public FileDB(String name, String type, String path) {
        this.name = name;
        this.type = type;
        this.url = path;
    }

    public FileDB(String name, String type, String path, Long idFuncionario) {
        this.name = name;
        this.type = type;
        this.url = path;
        this.idFuncionario = idFuncionario;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Long idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public void setId(Long id) {
        this.id = id;
    }
}