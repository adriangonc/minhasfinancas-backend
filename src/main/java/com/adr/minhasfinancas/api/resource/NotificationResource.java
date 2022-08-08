package com.adr.minhasfinancas.api.resource;

import java.util.List;

import com.adr.minhasfinancas.api.dto.LancamentoDTO;
import com.adr.minhasfinancas.api.dto.NotificacaoPagsDTO;
import com.adr.minhasfinancas.api.dto.NotificationDTO;
import com.adr.minhasfinancas.model.entity.Notificacao;
import com.adr.minhasfinancas.service.NotificacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationResource {

    private final NotificacaoService notificacao;

    @GetMapping
    public ResponseEntity getNotifications(){
        List<Notificacao> listaNotificacao = notificacao.findAll();
        return ResponseEntity.ok(listaNotificacao);
    }

    @PostMapping(path = "/pags", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<Object> notifications(@RequestParam("id_usuario") Long idUsuario, NotificacaoPagsDTO dto){
        notificacao.save(idUsuario, dto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
