package com.example.apitransacional.factory.impl;

import com.example.apitransacional.factory.TransacaoFactory;
import com.example.apitransacional.model.Transacao;

public class TransacaoFactoryImpl implements TransacaoFactory {
	
	@Override
	public Transacao criarTransacao() {
		return new Transacao();
	}

}
