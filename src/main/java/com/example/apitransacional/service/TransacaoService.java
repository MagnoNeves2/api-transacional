package com.example.apitransacional.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.example.apitransacional.factory.TransacaoFactory;
import com.example.apitransacional.factory.impl.TransacaoFactoryImpl;
import com.example.apitransacional.model.Transacao;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TransacaoService {

	private TransacaoFactory factory;
	private List<Transacao> transacoes;
	private List<Transacao> listaValidacao;

	public void criarFactory() {
		if (factory == null) {
			factory = new TransacaoFactoryImpl();
		}
	}

	public void criarListaTransacao() {
		if (transacoes == null) {
			transacoes = new ArrayList<>();
		}
	}

	public void criarListaValidacao() {
		if (listaValidacao == null) {
			listaValidacao = new ArrayList<>();
		}
	}

	public boolean validadorJSON(String jsonInString) {
		try {
			return new ObjectMapper().readTree(jsonInString) != null;
		} catch (IOException error) {
			return false;
		}
	}

	private BigDecimal parseValor(JSONObject transacao) {
		return BigDecimal.valueOf((double) transacao.get("valor"));
	}

	private LocalDateTime parseDataTransacao(JSONObject transacao) {
		var dataTransacao = (String) transacao.get("dataHora");
		return ZonedDateTime.parse(dataTransacao).toLocalDateTime();
	}

	public boolean transacaoNoFuturo(Transacao transacao) {
		return transacao.getDataHora().isAfter(LocalDateTime.now());
	}

	private void definirValores(JSONObject jsonTransacao, Transacao transacao) {
		transacao.setValor(jsonTransacao.get("valor") != null ? parseValor(jsonTransacao) : transacao.getValor());
		transacao.setDataHora(
				jsonTransacao.get("dataHora") != null ? parseDataTransacao(jsonTransacao) : transacao.getDataHora());
	}

	public Transacao criar(JSONObject jsonTransacao) {

		criarFactory();

		Transacao transacao = factory.criarTransacao();
		definirValores(jsonTransacao, transacao);

		Transacao validarTransacao = new Transacao();
		validarTransacao.setDataHora(LocalDateTime.now());
		validarTransacao.setValor(transacao.getValor());

		criarListaValidacao();
		listaValidacao.add(validarTransacao);

		return transacao;
	}

	public List<Transacao> listaTransacao() {
		return listaValidacao;
	}

	public List<Transacao> procurar() {
		criarListaTransacao();
		return transacoes;
	}

	public void adicionar(Transacao transacao) {
		criarListaTransacao();
		transacoes.add(transacao);
	}

	public void apagar() {
		if (transacoes != null) {
			new Thread() {

				@Override
				public void run() {
					transacoes.clear();
					listaValidacao.clear();

				}
			}.start();
		}
	}

	public void limparObjetos() {
		transacoes = null;
		factory = null;
	}

}
