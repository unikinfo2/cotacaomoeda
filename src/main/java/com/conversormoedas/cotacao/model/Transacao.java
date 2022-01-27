package com.conversormoedas.cotacao.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

@Document
public class Transacao {
	
    @Id
	private String id;
    
	private String idUsuario;
	
	private Date dataHora;
	
	private String moedaOrigem;

	private Double valorOrigem;
	
	private Double taxaConversao;

	private String moedaDestino;
	
	private Double valorDestino;

	
	public Transacao() {
	}

	public Transacao(String idUsuario, Date dataHora, String moedaOrigem, Double valorOrigem, Double taxaConversao, String moedaDestino, Double valorDestino) {
		this.idUsuario     	= idUsuario; 
		this.dataHora 		= dataHora; 
		this.moedaOrigem 	= moedaOrigem;
		this.valorOrigem 	= valorOrigem; 
		this.taxaConversao 	= taxaConversao; 
		this.moedaDestino 	= moedaDestino; 
		this.valorDestino 	= valorDestino;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Date getDataHora() {
		return dataHora;
	}
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	public Double getValorOrigem() {
		return valorOrigem;
	}
	public void setValorOrigem(Double valorOrigem) {
		this.valorOrigem = valorOrigem;
	}
	public Double getTaxaConversao() {
		return taxaConversao;
	}
	public void setTaxaConversao(Double taxaConversao) {
		this.taxaConversao = taxaConversao;
	}

	public String getMoedaOrigem() {
		return moedaOrigem;
	}

	public void setMoedaOrigem(String moedaOrigem) {
		this.moedaOrigem = moedaOrigem;
	}

	public String getMoedaDestino() {
		return moedaDestino;
	}

	public void setMoedaDestino(String moedaDestino) {
		this.moedaDestino = moedaDestino;
	}

	public Double getValorDestino() {
		return valorDestino;
	}

	public void setValorDestino(Double valorDestino) {
		this.valorDestino = valorDestino;
	}

	@Override
	public String toString() {
		return "Transacao [id=" + id + ", idUsuario=" + idUsuario + ", dataHora=" + dataHora + ", moedaOrigem="
				+ moedaOrigem + ", valorOrigem=" + valorOrigem + ", taxaConversao=" + taxaConversao + ", moedaDestino="
				+ moedaDestino + ", valorDestino=" + valorDestino + "]";
	}
	
}
