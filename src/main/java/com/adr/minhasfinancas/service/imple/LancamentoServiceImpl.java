package com.adr.minhasfinancas.service.imple;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adr.minhasfinancas.exception.BusinessRuleException;
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
	@Transactional
	public Lancamento save(Lancamento lancamento) {
		validate(lancamento);
		lancamento.setStatus(StatusLancamento.PENDENTE);
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public Lancamento update(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		validate(lancamento);
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public void delete(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		repository.delete(lancamento);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lancamento> search(Lancamento lancamentoFilter) {
		Example example = Example.of(lancamentoFilter, ExampleMatcher.matching()
									.withIgnoreCase()
									.withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}

	@Override
	public void updateStatus(Lancamento lancamento, StatusLancamento status) {
		lancamento.setStatus(status);
		update(lancamento);
	}

	@Override
	public void validate(Lancamento lancamento) {
		if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")) {
			throw new BusinessRuleException("Informe uma descrição válida!");
		}
		
		if(lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() > 12) {
			throw new BusinessRuleException("Informe um mês válido!");
		}
		
		if(lancamento.getAno() == null || lancamento.getAno().toString().length() != 4) {
			throw new BusinessRuleException("Informe um ano válido!");
		}
		
		if(lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null) {
			throw new BusinessRuleException("Informe um usuário!");
		}
		
		if(lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1) {
			throw new BusinessRuleException("Informe valor válido!");
		}
		
		if(lancamento.getTipo() == null) {
			throw new BusinessRuleException("Informe um tipo de lançamento!");
		}
	}

}
