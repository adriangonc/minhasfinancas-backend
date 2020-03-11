package com.adr.minhasfinancas.api.resource;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adr.minhasfinancas.api.dto.UsuarioDTO;
import com.adr.minhasfinancas.exception.AuthenticateErrorException;
import com.adr.minhasfinancas.exception.BusinessRuleException;
import com.adr.minhasfinancas.model.entity.Usuario;
import com.adr.minhasfinancas.service.LancamentoService;
import com.adr.minhasfinancas.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioResource {

	private final UsuarioService service;
	private final LancamentoService lancamentoService;

	@PostMapping("/autenticar")
	public ResponseEntity authenticate(@RequestBody UsuarioDTO dto) {
		try {
			Usuario authenticatedUser = service.authenticate(dto.getEmail(), dto.getSenha());
			return ResponseEntity.ok(authenticatedUser);
		} catch (AuthenticateErrorException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity save(@RequestBody UsuarioDTO dto) {

		Usuario user = Usuario.builder().nome(dto.getNome()).email(dto.getEmail()).senha(dto.getSenha()).build();

		try {
			Usuario savedUser = service.saveUser(user);
			return new ResponseEntity(savedUser, HttpStatus.CREATED);
		} catch (BusinessRuleException re) {
			return ResponseEntity.badRequest().body(re.getMessage());
		}
	}
	
	@GetMapping("{id}/saldo")
	public ResponseEntity getBalance(@PathVariable("id") Long id) {
		Optional<Usuario> user = service.userById(id);
		if(!user.isPresent()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		BigDecimal balance = lancamentoService.balanceByUser(id);
		return ResponseEntity.ok(balance);
	}

}
