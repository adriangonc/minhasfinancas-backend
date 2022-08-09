package com.adr.minhasfinancas.model.repository;

import com.adr.minhasfinancas.model.entity.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
}
