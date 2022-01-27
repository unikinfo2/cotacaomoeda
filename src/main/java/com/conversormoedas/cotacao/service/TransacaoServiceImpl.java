package com.conversormoedas.cotacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conversormoedas.cotacao.model.Transacao;
import com.conversormoedas.cotacao.repository.TransacaoRepository;
import com.conversormoedas.cotacao.utils.AppUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransacaoServiceImpl implements TransacaoService {
	@Autowired
	TransacaoRepository rp;
	
	@Override
	public Flux<Transacao> findByIdUsuario(String idUsuario) {
		return rp.findAll(); //findByIdUsuario(idUsuario);
	}

	public Flux<Transacao> findAll() {
		return rp.findAll();
	}

	public Mono<Transacao> findById(String id) {
		return rp.findById(id);
	}

	public Mono<Transacao> save(Transacao transacao) {
		Mono<Transacao> salvo = rp.save(transacao); 
		return salvo;
	}

	public Mono<Void> deleteById(String id) {
		return rp.deleteById(id);
	}

	public Mono<Void> deleteAll() {
		return rp.deleteAll();
	}

	public Mono<Void> delete(Transacao transacao) {
		return rp.delete(transacao);
	}

}
