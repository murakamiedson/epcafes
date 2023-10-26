package com.epcafes.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.epcafes.enums.TipoAuxiliarInsumos;
import com.epcafes.enums.TipoCombustivel;
import com.epcafes.enums.TipoInsumo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity
@Data
public class Maquina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tenantId;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    private String modelo;

    @PositiveOrZero
    private BigDecimal valor; // valor do bem

    @PositiveOrZero
    private BigDecimal potencia = new BigDecimal(0.0); // CV

    @PositiveOrZero
    private Integer vidaUtil;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCompra;

    @Enumerated(EnumType.STRING)
    private TipoInsumo tipoInsumo;

    @Enumerated(EnumType.STRING)
    private TipoCombustivel tipoCombustivel;

    @Enumerated(EnumType.STRING)
    private TipoAuxiliarInsumos tipo;

    @CreationTimestamp
    @Column(columnDefinition = "datetime")
    private OffsetDateTime dataCriacao;

    @UpdateTimestamp
    @Column(columnDefinition = "datetime")
    private OffsetDateTime dataModificacao;
}
