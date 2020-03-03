package com.adr.minhasfinancas.service.imple;

import java.util.List;

import org.springframework.stereotype.Service;

import com.adr.minhasfinancas.model.entity.Lancamento;
import com.adr.minhasfinancas.model.enums.StatusLancamento;
import com.adr.minhasfinancas.model.repository.LancamentoRepository;
import com.adr.minhasfinancas.service.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService{
	
	private LancamentoRepository repository;
	
	public LancamentoServiceImpl(LancamentoRepository repository) {
		this.repository = repository;
	}

	@Override
	public Lancamento save(Lancamento lancamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lancamento update(Lancamento lancamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Lancamento lancamento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Lancamento> search(Lancamento lancamentoFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStatus(Lancamento lancamento, StatusLancamento status) {
		// TODO Auto-generated method stub
		
	}

}
