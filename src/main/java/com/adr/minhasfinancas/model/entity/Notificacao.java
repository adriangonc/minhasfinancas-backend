package com.adr.minhasfinancas.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.adr.minhasfinancas.model.enums.CategoriaLancamento;
import com.adr.minhasfinancas.model.enums.StatusLancamento;
import com.adr.minhasfinancas.model.enums.TipoLancamento;
import com.adr.minhasfinancas.model.enums.TipoNotificacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notificacoes", schema = "financas")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "id_notificacao")
	private String idNotificacao;

	@Column(name = "usuario")
	private Long usuario;
	
	@Column(name = "cod_referencia")
	private String codReferencia;

	@Column(name = "data_notificacao")
	//@Convert(converter = Jsr310JpaConverters.class) //O spring boot converte o localdate, pois essa versão do hybernate ainda não trabalha com LocalDate.
	private LocalDateTime dataNotificacao;
	
	@Column(name = "tipo_notificacao")
	@Enumerated(value = EnumType.STRING)
	private TipoNotificacao tipo;
	
	@Column(name = "fonte_pagamento")
	private String fontePagamento;

	@Column(name = "codigo_transacao")
	private String codigoTransacao;

	@Column(name = "status_transacao")
	private String statusTransacao;

	@Column(name = "valor_transacao")
	private BigDecimal valorTransacao;
	
}
