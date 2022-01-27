package com.conversormoedas.cotacao.model;

public class MoedaCotacao {
	private String moeda;
	private Double cotacao;
	
	public MoedaCotacao() {
		
	}

	public MoedaCotacao(String moeda, Double cotacao) {
		this.moeda = moeda;
		this.cotacao = cotacao;
	}

	public String getMoeda() {
		return moeda;
	}

	public void setMoeda(String moeda) {
		this.moeda = moeda;
	}

	public Double getCotacao() {
		return cotacao;
	}

	public void setCotacao(Double cotacao) {
		this.cotacao = cotacao;
	}

	@Override
	public String toString() {
		return "MoedaCotacao [moeda=" + moeda + ", cotacao=" + cotacao + "]";
	}
	
}
