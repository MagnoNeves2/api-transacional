package com.example.apitransacional.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apitransacional.model.Estatistica;
import com.example.apitransacional.model.Transacao;
import com.example.apitransacional.service.EstatisticaService;
import com.example.apitransacional.service.TransacaoService;

@RestController
@CrossOrigin("*")
@RequestMapping("/estatistica")
public class EstatisticaController {
	
	@Autowired
	private TransacaoService transacaoService;

	@Autowired
	private EstatisticaService estatisticaService;
	
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<Estatistica> pegarEstatisticas() {
		try {
			List<Transacao> transacoes = transacaoService.listaTransacao();
			Estatistica estatisticas = estatisticaService.criar(transacoes);
			
			return ResponseEntity.status(HttpStatus.OK).body(estatisticas);
			
		} catch (Exception error) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
	}
}
