package com.adr.minhasfinancas.service;

import java.util.List;

import com.adr.minhasfinancas.api.dto.NotificacaoPagsDTO;
import com.adr.minhasfinancas.model.entity.Notificacao;
import org.springframework.http.HttpStatus;

public interface NotificacaoService {

    HttpStatus save(Long idUsuario, NotificacaoPagsDTO codNotificacao);

    List<Notificacao> findAll();

}
