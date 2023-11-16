package com.epcafes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import java.nio.file.Path;

@Entity
@Table(name = "files")
public class FileDB {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private String type;


    private String path;

    private Long idFuncionario;

    public FileDB() {
    }

    public FileDB(String name, String type, String path) {
        this.name = name;
        this.type = type;
        this.path = path;
    }

    public FileDB(String name, String type, String path, Long idFuncionario) {
        this.name = name;
        this.type = type;
        this.path = path;
        this.idFuncionario = idFuncionario;
    }

    public String getId() {
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}