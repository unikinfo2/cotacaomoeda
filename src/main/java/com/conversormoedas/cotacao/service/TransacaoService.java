package com.conversormoedas.cotacao.service;

import com.conversormoedas.cotacao.model.Transacao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransacaoService {
	
	Flux<Transacao> findByIdUsuario(String idUsuario);
	Flux<Transacao> findAll();
	Mono<Transacao> findById(String id);
	Mono<Transacao> save(Transacao transacao);
	Mono<Void> deleteById(String id);
	Mono<Void> deleteAll();
	Mono<Void> delete(Transacao transacao);

}
