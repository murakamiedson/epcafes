package com.epcafes.enums;

public enum TipoInsumo {

	MAQUINA("MAQUINA"),
	IMPLEMENTO("IMPLEMENTO");
	// FERTILIZANTE("FERTILIZANTE"),
	// FUNGICIDA("FUNGICIDA"),
	// INSETICIDA("INSETICIDA"),
	// HERBICIDA("HERBICIDA"),
	// ADJUVANTE("ADJUVANTE");

	private final String tipoInsumoCode;

	private TipoInsumo(String tipoInsumoCode) {
		this.tipoInsumoCode = tipoInsumoCode;

	}

	public String getTipoInsumoCode() {
		return tipoInsumoCode;
	}

}
