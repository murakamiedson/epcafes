package com.epcafes.enums;

public enum TipoCombustivel {

	ENERGIA_ELETRICA("ENERGIA_ELETRICA"),
	DIESEL("DIESEL"),
	GASOLINA("GASOLINA"),
	ETANOL("ETANOL"),
	NENHUM("NENHUM");

	private final String tipoCombustivelCode;

	private TipoCombustivel(String tipoCombustivelCode) {
		this.tipoCombustivelCode = tipoCombustivelCode;

	}

	public String getTipoCombustivelCode() {
		return tipoCombustivelCode;
	}

}
