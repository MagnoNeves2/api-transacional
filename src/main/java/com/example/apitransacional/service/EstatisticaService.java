package com.example.apitransacional.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.apitransacional.model.Estatistica;
import com.example.apitransacional.model.Transacao;

@Service
public class EstatisticaService {

	public Estatistica criar(List<Transacao> transacoes) {
		
		LocalDateTime horaTransacao = LocalDateTime.now().minusMinutes(1);
		
		for(int i = 0; i < transacoes.size(); i++) {
			if(horaTransacao.isAfter(transacoes.get(i).getDataHora())) {
				transacoes.remove(i);
			}
		}
		
		var estatisticas = new Estatistica();
		estatisticas.setCount(transacoes.stream().count());
		estatisticas.setAvg(BigDecimal.valueOf(transacoes.stream().mapToDouble(t -> t.getValor().doubleValue()).average().orElse(0.0)));
		estatisticas.setMin(BigDecimal.valueOf(transacoes.stream().mapToDouble(t -> t.getValor().doubleValue()).min().orElse(0.0)));
		estatisticas.setMax(BigDecimal.valueOf(transacoes.stream().mapToDouble(t -> t.getValor().doubleValue()).max().orElse(0.0)));
		estatisticas.setSum(BigDecimal.valueOf(transacoes.stream().mapToDouble(t -> t.getValor().doubleValue()).sum()));
		
		return estatisticas;
		
	}
	
}
