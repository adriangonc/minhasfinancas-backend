package com.adr.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adr.minhasfinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
