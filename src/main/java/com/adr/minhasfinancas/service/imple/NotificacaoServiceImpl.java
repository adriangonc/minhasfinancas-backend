package com.adr.minhasfinancas.service.imple;

import java.time.LocalDateTime;
import java.util.List;

import com.adr.minhasfinancas.api.dto.NotificacaoPagsDTO;
import com.adr.minhasfinancas.model.entity.Notificacao;
import com.adr.minhasfinancas.model.repository.NotificacaoRepository;
import com.adr.minhasfinancas.service.NotificacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoServiceImpl implements NotificacaoService {

    NotificacaoRepository repository;

    public NotificacaoServiceImpl(NotificacaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public HttpStatus save(Long idUsuario, NotificacaoPagsDTO dto) {
        Notificacao notificacao = new Notificacao();
        notificacao.setIdNotificacao(dto.getNotificationCode());
        notificacao.setUsuario(idUsuario);
        notificacao.setDataNotificacao(LocalDateTime.now());

        try {
            repository.save(notificacao);
        } catch(Exception e){
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.OK;
    }

    @Override
    public List<Notificacao> findAll() {
        return repository.findAll();
    }
}
