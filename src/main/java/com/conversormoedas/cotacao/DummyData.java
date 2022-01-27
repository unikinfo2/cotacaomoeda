package com.conversormoedas.cotacao;

import java.util.Date;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.conversormoedas.cotacao.model.Transacao;
import com.conversormoedas.cotacao.repository.TransacaoRepository;

import reactor.core.publisher.Flux;

@Component
public class DummyData implements CommandLineRunner {
	
	private static final Logger LOG = LoggerFactory.getLogger( DummyData.class.getName() );
		
	private final TransacaoRepository transacaoRepository;
	
	DummyData(TransacaoRepository transacaoRepository){
		this.transacaoRepository = transacaoRepository;
	}
	
	@Override
	public void run(String... args) throws Exception {
		transacaoRepository.deleteAll()
				.thenMany(
				  Flux.just("BRL", "USD", "EUR", "JPY")
				  .map(moedaDestino -> new Transacao(
						  "1",
						  new Date(),
						  "BRL",
						  154.20,
						  5.56,
						  moedaDestino, //UUID.randomUUID().toString(), 
						  857.352
						  ))
				  .flatMap(transacaoRepository::save));
		LOG.info("Informações de teste inseridas com sucesso !!!");
	}

}
