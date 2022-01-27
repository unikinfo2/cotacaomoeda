package com.conversormoedas.cotacao;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CotacaoApplication {
	
	private static final Logger LOG = Logger.getLogger( CotacaoApplication.class.getName() );
	
	public static void main(String[] args) {
		//System.out.println(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("1234"));
		SpringApplication.run(CotacaoApplication.class, args);
		LOG.log(Level.FINE, "Aplicação iniciada com Sucesso !");
	}
}
