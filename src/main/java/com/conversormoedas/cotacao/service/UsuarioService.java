package com.conversormoedas.cotacao.service;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.conversormoedas.cotacao.repository.UsuarioRepository;

import reactor.core.publisher.Mono;

@Service
public class UsuarioService implements ReactiveUserDetailsService {

	private final UsuarioRepository rp;
	
	public UsuarioService(UsuarioRepository rp) {
		this.rp = rp;
	}

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		return rp.findByUsername(username)
				.cast(UserDetails.class);
	}

}
