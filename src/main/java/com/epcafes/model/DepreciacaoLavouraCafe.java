package com.epcafes.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class DepreciacaoLavouraCafe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long tenant_id;
	
	private String descricao;
	
	private BigDecimal custoImplantacao;
	
	private BigDecimal receitasObtidas;
	
	private Integer vidaUtilAnos;
	
	private BigDecimal valorDepreciacao;
	
	@ManyToOne
	private Propriedade propriedade;
}
