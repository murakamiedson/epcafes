package com.arquitetura.epcafe.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.arquitetura.epcafe.enums.FatorPotencia;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DespesaMaquina {
    
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tenant_id;

    private LocalDate mesAno;

    private BigDecimal horasTrabalhadas = new BigDecimal(0);
    
    @Enumerated(EnumType.STRING)
    private FatorPotencia fatorPotencia;

    @ManyToOne
    private Unidade unidade;

    @ManyToOne
    private Maquina maquina;

    private BigDecimal precoCombustivel;

    private BigDecimal valorTotal;
}
