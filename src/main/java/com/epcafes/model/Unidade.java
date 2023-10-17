package com.epcafes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Unidade{

	private long tenantId;

	private String nome;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codigo;

    public void setId(long l) {
    }
}
