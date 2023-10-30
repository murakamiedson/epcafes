package com.epcafes.model;

import jakarta.persistence.*;

@Entity
@Table(name = "funcionarioCertificados")
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;
    private Long funcionarioId;

    private String nomeOriginal;

    public FileInfo(String name, String url) {
        this.name = name;
        this.url = url;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}