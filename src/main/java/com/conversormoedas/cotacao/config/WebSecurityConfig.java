package com.conversormoedas.cotacao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.conversormoedas.cotacao.service.UsuarioService;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {
	 
	@Bean
	protected SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http ) {
		http.httpBasic().disable()
        	.formLogin().disable()
        	.csrf().disable()
        	.logout().disable();
		
		http.csrf().disable()
			.authorizeExchange()
			.pathMatchers(HttpMethod.GET, "/").hasAnyRole("ADMIN")
			.pathMatchers(HttpMethod.GET, "/**").hasAnyRole("ADMIN")
			.pathMatchers(HttpMethod.POST, "/**").hasAnyRole("ADMIN")
			.pathMatchers(HttpMethod.OPTIONS)
			.permitAll()
			.anyExchange().authenticated()
			.and().formLogin()
			.and().httpBasic();

		return http.build();		
	}
	
	@Bean
	ReactiveAuthenticationManager authenticationManager(UsuarioService usuarioService) {
		return new UserDetailsRepositoryReactiveAuthenticationManager(usuarioService);
	}

	private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
        	System.out.println(">>> Autentica: ");
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        return "anonymous";
	}
	
	
}
