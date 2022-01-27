package com.conversormoedas.cotacao.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.conversormoedas.cotacao.model.Transacao;

import reactor.core.publisher.Flux;

@Repository
public interface TransacaoRepository extends ReactiveMongoRepository<Transacao, String> {
		
	//@Query("SELECT t FROM Transacao t WHERE t.idUsuario = :idUsuario")
	Flux<Transacao> findByIdUsuario(String idUsuario);
	
}
