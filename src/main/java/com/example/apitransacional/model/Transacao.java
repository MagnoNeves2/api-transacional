package com.example.apitransacional.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Transacao {
	
	private BigDecimal valor;
	private LocalDateTime dataHora;
	
	public Transacao() {}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

}
