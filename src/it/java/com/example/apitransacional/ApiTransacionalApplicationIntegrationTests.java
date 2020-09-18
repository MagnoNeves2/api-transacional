package com.example.apitransacional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.apitransacional.service.TransacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties
@AutoConfigureMockMvc
class ApiTransacionalApplicationIntegrationTests {

	@Autowired
	private TransacaoService transacaoService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Before
	public void config() {
		
		transacaoService.criarFactory();
		transacaoService.criarListaTransacao();
		assertNotNull(transacaoService);
		
		assertNotNull(mockMvc);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void deveRetornarTransacaoCriada() throws Exception {
		
		JSONObject map = objetoCriado();
		
		this.mockMvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(new ObjectMapper().writeValueAsString(map))).andExpect(status().isCreated());
		
	}
	
	@Test
	public void deveRetornarTodasTransacoes() throws Exception {
		this.mockMvc.perform(get("/estatistica")).andExpect(status().isOk());
	}
	
	@Test
	public void deveRetornarRemoverTodasTransacoes() throws Exception {
		this.mockMvc.perform(delete("/transacao")).andExpect(status().isNoContent());
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject objetoCriado() {
		
		String dataLocal = LocalDateTime.now().toString().concat("Z");
		
		JSONObject map = new JSONObject();
		map.put("valor", 100.50);
		map.put("dataHora", dataLocal);
		
		return map;
	}

}
