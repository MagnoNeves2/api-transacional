package com.example.apitransacional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.apitransacional.model.Estatistica;
import com.example.apitransacional.model.Transacao;
import com.example.apitransacional.service.EstatisticaService;
import com.example.apitransacional.service.TransacaoService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TransacaoService.class, EstatisticaService.class })
@EnableConfigurationProperties
class ApiTransacionalApplicationTests {
	
	@Autowired
	private TransacaoService transacaoService;
	
	@Autowired
	private EstatisticaService estatisticaService;
	
	@Before
	public void config() {
		transacaoService.criarFactory();
		transacaoService.criarListaTransacao();
	}
	
	@Test
	public void deveRetornarNotNullTransacaoService() {
		assertNotNull(transacaoService);
	}
	
	@Test
	public void deveRetornarNotNullEstatisticaService() {
		assertNotNull(estatisticaService);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void deveRetornarTransacaoCriadaComSucesso() throws Exception {
		
		LocalDateTime agora = LocalDateTime.now();
		String dataLocal = agora.toString().concat("Z");
		
		JSONObject json = new JSONObject();
		json.put("valor", 100.50);
		json.put("dataHora", dataLocal);
		
		Transacao transacao = transacaoService.criar(json);
		
		assertNotNull(transacao);
		assertEquals(transacao.getValor().doubleValue(), json.get("valor"));
		assertEquals(dataLocal, json.get("dataHora"));
		
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void deveRetornarTransacaoCriadaNoFuturo() {
		
		JSONObject json = new JSONObject();
		json.put("valor", 100.50);
		json.put("dataHora", "2021-09-13T09:59:51.312Z");
		
		Transacao transacao = transacaoService.criar(json);
		boolean transacaoNoFuturo = transacaoService.transacaoNoFuturo(transacao);
		
		assertTrue(transacaoNoFuturo);
		
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void shouldReturnTransactionStatisticsCalculated() throws Exception {
		
		
		
		LocalDateTime now = LocalDateTime.now();
		String localDate = now.toString().concat("Z");
		
		JSONObject json1 = new JSONObject();
		json1.put("valor", 100.50);
		json1.put("dataHora", localDate);
		
		Transacao transacao = transacaoService.criar(json1);
		transacaoService.adicionar(transacao);
		
		JSONObject json = new JSONObject();
		json.put("valor", 220.70);
		json.put("dataHora", localDate);
		
		transacao = transacaoService.criar(json);
		transacaoService.adicionar(transacao);
		
		Estatistica estatistica = estatisticaService.criar(transacaoService.procurar());
		
		assertNotNull(estatistica);
		assertEquals(BigDecimal.valueOf(321.2), estatistica.getSum());
		assertEquals(BigDecimal.valueOf(160.60), estatistica.getAvg());
		assertEquals(BigDecimal.valueOf(100.50), estatistica.getMin());
		assertEquals(BigDecimal.valueOf(220.70), estatistica.getMax());
		assertEquals(2, estatistica.getCount());
		
	}
	
	@After
	public void limpar() {
		transacaoService.limparObjetos();
	}
	
	
}
