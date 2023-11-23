package com.epcafes.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepreciacaoTotalTO {
	
	private List<BigDecimal> valoresPorTalhao = new ArrayList<>();
	private BigDecimal total = new BigDecimal(0);;
}
