package com.adr.minhasfinancas.model.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adr.minhasfinancas.exception.RegraNegocioException;
import com.adr.minhasfinancas.model.entity.Usuario;
import com.adr.minhasfinancas.model.repository.UsuarioRepository;
import com.adr.minhasfinancas.service.UsuarioService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

	private static final String EMAIL_TEST = "usuarioteste@email.com.br";

	@Autowired
	UsuarioService service;

	@Autowired
	UsuarioRepository repository;

	@Test()
	@Order(1)
	@DisplayName("Verifica se o email e válido")
	public void deveValidarEmailNaoDeveRetornarException() {
		// Cenario
		Optional<Usuario> usuario = repository.findByEmail(EMAIL_TEST);
		if (usuario.isPresent()) {
			repository.deleteById(usuario.get().getId());
		}
		// Ação
		Assertions.assertTrue(service.emailEhValido(EMAIL_TEST));
	}

	@Test()
	@Order(2)
	@DisplayName("Verifica se o email não e válido")
	public void deveValidarEmailDeveRetornarException() {
		// Cenario
		Usuario usuario = Usuario.builder().nome("usuario").email(EMAIL_TEST).build();
		repository.save(usuario);
		// Ação

		Assertions.assertThrows(RegraNegocioException.class, () -> {
			service.emailEhValido(EMAIL_TEST);
		});
	}
}
