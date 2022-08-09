package com.adr.minhasfinancas.api.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private long id;

    private Long usuario;

    private String idNotificacao;

    private String codReferencia;

    private LocalDateTime dataNotificacao;

    private String tipoNotificacao;

    private String fontePagamento;

    private String codigoTransacao;

    private String statusTransacao;

    private String valorTransacao;
}
