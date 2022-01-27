package com.conversormoedas.cotacao.repository;

import org.springframework.stereotype.Repository;

import com.conversormoedas.cotacao.model.Usuario;

import reactor.core.publisher.Mono;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

@Repository
public interface UsuarioRepository extends ReactiveMongoRepository<Usuario, String> {

	Mono<Usuario> findByUsername(String username);
}
