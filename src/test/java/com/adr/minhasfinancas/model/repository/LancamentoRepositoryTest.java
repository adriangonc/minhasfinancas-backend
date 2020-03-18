package com.adr.minhasfinancas.model.repository;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adr.minhasfinancas.model.entity.Lancamento;
import com.adr.minhasfinancas.model.enums.StatusLancamento;
import com.adr.minhasfinancas.model.enums.TipoLancamento;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LancamentoRepositoryTest {

	@Autowired
	LancamentoRepository repository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	public void deveSalvarUmLancamento() {
		Lancamento lancamento = criarLancamento();

		lancamento = repository.save(lancamento);

		assertThat(lancamento.getId()).isNotNull();
	}

	private Lancamento criarLancamento() {
		Lancamento lancamento = Lancamento.builder().ano(2020).mes(3).descricao("lançamento teste!")
				.valor(BigDecimal.valueOf(1532)).tipo(TipoLancamento.RECEITA).status(StatusLancamento.PENDENTE)
				.dataCadastro(LocalDate.now()).build();
		return lancamento;
	}

	@Test
	public void deveDeletarUmLancamento() {
		Lancamento lancamento = criarEPessistirUmLancamento();

		lancamento = entityManager.find(Lancamento.class, lancamento.getId());
		Long lancamentoId = lancamento.getId();
		repository.delete(lancamento);

		Lancamento lancamentoInexistente = entityManager.find(Lancamento.class, lancamentoId);
		assertThat(lancamentoInexistente).isNull();
	}

	private Lancamento criarEPessistirUmLancamento() {
		Lancamento lancamento = criarLancamento();
		entityManager.persist(lancamento);
		return lancamento;
	}

	@Test
	public void deveAtualizarUmLancamento() {
		Lancamento lancamento = criarEPessistirUmLancamento();
		lancamento.setAno(2019);
		lancamento.setDescricao("Teste atualização!");
		lancamento.setStatus(StatusLancamento.CANCELADO);
		repository.save(lancamento);

		Lancamento lancamentoAtualizado = entityManager.find(Lancamento.class, lancamento.getId());
		assertThat(lancamentoAtualizado.getAno()).isEqualTo(2019);
		assertThat(lancamentoAtualizado.getDescricao()).isEqualTo("Teste atualização!");
		assertThat(lancamentoAtualizado.getStatus()).isEqualTo(StatusLancamento.CANCELADO);
	}
	
	@Test
	public void deveBuscarUmLancamentoPorId() {
		Lancamento lancamento = criarEPessistirUmLancamento();
		
		Optional<Lancamento> lancamentoEncontrado = repository.findById(lancamento.getId());
		assertThat(lancamentoEncontrado.isPresent()).isTrue();
	}

}
