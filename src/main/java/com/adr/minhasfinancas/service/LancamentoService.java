package com.adr.minhasfinancas.service;

import java.util.List;

import com.adr.minhasfinancas.model.entity.Lancamento;
import com.adr.minhasfinancas.model.enums.StatusLancamento;

public interface LancamentoService {

	Lancamento save(Lancamento lancamento);
	
	Lancamento update(Lancamento lancamento);
	
	void delete(Lancamento lancamento);
	
	List<Lancamento> search(Lancamento lancamentoFilter);
	
	void updateStatus(Lancamento lancamento, StatusLancamento status);
	
	void validate(Lancamento lancamento);
}
