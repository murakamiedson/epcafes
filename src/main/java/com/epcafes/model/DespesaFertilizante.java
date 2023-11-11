package com.epcafes.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
public class DespesaFertilizante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tenantId;

    @ManyToOne
    private Fertilizante fertilizante;

    @NotNull
    private LocalDate mesAno;

    @ManyToOne
    private NotaFiscal notaFiscal;

    @Positive
    private BigDecimal quantidade;

    @Positive
    private BigDecimal valorDespesa;

    @OneToMany
    private List<QuantidadeTalhao> qtdTalhao;

    @CreationTimestamp
    @Column(columnDefinition = "datetime")
    private OffsetDateTime dataCriacao;

    @UpdateTimestamp
    @Column(columnDefinition = "datetime")
    private OffsetDateTime dataModificacao;
}
