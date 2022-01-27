package com.conversormoedas.cotacao.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.conversormoedas.cotacao.model.MoedaCotacao;
import com.conversormoedas.cotacao.model.Transacao;
import com.conversormoedas.cotacao.repository.TransacaoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class TransacaoController {
	
	private static final Logger LOG = LoggerFactory.getLogger( TransacaoController.class.getName() );
	
    @Autowired
    private TransacaoRepository service;

	@Value("${api.url}")
	private String urlBase;
	
	@Value("${api.key}")
    private String apiKey;
	
	@Value("${api.format}")
    private String apiFormat;

	@Value("${api.base}")
    private String apiBase;
	
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
    private Map<String, MoedaCotacao> cotacoes;
    
	@RequestMapping("/convertermoeda")
	public String form(Model model) {
		List<String> moedas = Arrays.asList("BRL", "USD", "EUR", "JPY");
		model.addAttribute("moedas", moedas );
		try {
			if(cotacoes.isEmpty()) {
				getUltimaCotacao();
				LOG.info("Cotações carregadas com sucesso");
			}
		} catch (IOException e) {
			LOG.error("Erro ao carregar as cotações: "+ e.getMessage());
			e.printStackTrace();
		}
		return "formTransacao"; 
	}
	
	@RequestMapping("/historicoconversoes")
	public String historico(Model model) {
		Flux<Transacao> transacaoLst = service.findAll();
		model.addAttribute("transacaoLst", transacaoLst);
		return "historicoConversoes";
	}

	@PostMapping("/")
    public String save(Transacao entity) {
		service.save(entity).subscribe();
		return "historicoConversoes";
    }

	@RequestMapping(value = "/calcular", method=RequestMethod.POST)
	public String formCalculo( Transacao tr	) {
		LOG.info("Método Calcular acessado- De:"+tr.getMoedaOrigem() + " Para: "+tr.getMoedaDestino() + " Valor: "+tr.getValorOrigem());
		Double txOrig = cotacoes.get(tr.getMoedaOrigem()).getCotacao();
		Double txDest = cotacoes.get(tr.getMoedaDestino()).getCotacao();
		Double vlrConvertido = 0.00; 
		if(txOrig > 0 && txDest > 0 && tr.getValorOrigem() > 0) {
			vlrConvertido = arredondar((tr.getValorOrigem() * txOrig ) / txDest); 
		}
		tr.setTaxaConversao(txOrig);
		tr.setValorDestino(vlrConvertido);
		return "detalhesTransacao"; 
	}
	
	@RequestMapping(value="/salvar", method=RequestMethod.POST)
    public String form(Transacao tr, Model model) {
		LOG.info("Antes de Salvar: " + tr);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date dataHora = null;
		try {
			dataHora = (Date) sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
        tr.setIdUsuario("1");
        tr.setDataHora(dataHora);

		service.save(tr).subscribe();
		LOG.info("Salvo sobre o ID: " + tr);
		
		Flux<Transacao> transacaoLst = service.findAll();
		model.addAttribute("transacaoLst", transacaoLst);
		return "historicoConversoes";
    }	
	
	@RequestMapping("/deleteTransacao/{id}")
	public String deleteTransacao(Model model, @PathVariable String id) {
		LOG.info("Chamada do metodo delete: "+ id);
		service.deleteById(id).subscribe();
		Flux<Transacao> transacaoLst = service.findAll();
		model.addAttribute("transacaoLst", transacaoLst);
		return "historicoConversoes";
	}

	public Mono<Long> count() {
    	return service.findAll().count();
    }

	@GetMapping("/transacao/list")
	public Flux<Transacao> getTransacaoLst() {
		return service.findAll();
	}
    
	@GetMapping("/transacao/list/{idUsuario}")
	public Flux<Transacao> getTransacaoId(@PathVariable("idUsuario") String idUsuario) {
		return service.findByIdUsuario(idUsuario);
	}
	
	public void getUltimaCotacao() throws IOException {
		String uri = String.format("/latest?access_key=%s&base=%s&format=%s", apiKey, apiBase, apiFormat );
		URL url = new URL(urlBase + uri);
		LOG.info("urlBase + uri: "+ urlBase + uri);
	    HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    con.setRequestMethod("GET");

	    BufferedReader in = new BufferedReader(new InputStreamReader(
	        con.getInputStream()));
	    
	    String jsonText = readAll(in);
		LOG.info("response: "+jsonText);

		JSONObject object = new JSONObject(jsonText).getJSONObject("rates");
		cotacoes = new HashMap<String, MoedaCotacao>();
		
        for(String sigla: object.getNames(object)) {
    		MoedaCotacao mc = new MoedaCotacao();
    		mc.setMoeda(sigla);
    		mc.setCotacao(object.getDouble(sigla));
    		cotacoes.put(sigla, mc);
        }
        
        for(String chave: cotacoes.keySet()) {
    		LOG.info(chave+":"+cotacoes.get(chave));
        }
        
	}
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	            sb.append((char) cp);
	    }
	    return sb.toString();
	}
	
	private static Double arredondar(double valor) {
		   DecimalFormat df = new DecimalFormat("0.00");
		   df.setRoundingMode(RoundingMode.HALF_UP);
		   return Double.parseDouble(df.format(valor));
	}	
	
}

