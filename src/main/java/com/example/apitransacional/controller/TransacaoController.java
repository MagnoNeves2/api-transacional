package com.example.apitransacional.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apitransacional.model.Transacao;
import com.example.apitransacional.service.TransacaoService;


@RestController
@CrossOrigin("*")
@RequestMapping("/transacao")
public class TransacaoController {

	@Autowired
	private TransacaoService transacaoService;
	
	@PostMapping(produces = { "application/json" })
	public ResponseEntity<Transacao> criar(@RequestBody JSONObject transacao) {
		try {
			Double valor = (double) transacao.get("valor");
			if(transacaoService.validadorJSON(transacao.toString()) && transacao.get("valor") != null && valor >= 0) {
				Transacao transacaoCriada = transacaoService.criar(transacao);
				
				if(transacaoService.transacaoNoFuturo(transacaoCriada)) {
					return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
				} else {
					transacaoService.adicionar(transacaoCriada);
					return ResponseEntity.status(HttpStatus.CREATED).body(null);
				}
			} else {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
			}
		} catch (Exception error) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}
	
	@DeleteMapping
	public ResponseEntity<Boolean> excluir() {
		try {
			transacaoService.apagar();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} catch (Exception error) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
}
