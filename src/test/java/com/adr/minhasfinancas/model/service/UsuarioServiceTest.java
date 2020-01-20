package com.adr.minhasfinancas.model.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adr.minhasfinancas.exception.RegraNegocioException;
import com.adr.minhasfinancas.model.repository.UsuarioRepository;
import com.adr.minhasfinancas.service.UsuarioService;
import com.adr.minhasfinancas.service.imple.UsuarioServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	private static final String EMAIL_TEST = "usuarioteste@email.com.br";

	UsuarioService service;

	UsuarioRepository repository;

	public void setUp() {
		repository = Mockito.mock(UsuarioRepository.class);
		service = new UsuarioServiceImpl(repository);
	}
	
	@Test()
	@Order(1)
	@DisplayName("Verifica se o email e válido")
	public void deveValidarEmailNaoDeveRetornarException() {
		// Cenario
		setUp();
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		// Ação
		Assertions.assertTrue(service.emailEhValido(EMAIL_TEST));
	}

	@Test()
	@Order(2)
	@DisplayName("Verifica se o email não e válido")
	public void deveValidarEmailDeveRetornarException() {
		// Cenario
		setUp();
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenThrow(RegraNegocioException.class);
		
		// Ação
		Assertions.assertThrows(RegraNegocioException.class, () -> {
			service.emailEhValido(EMAIL_TEST);
		});
	}
}
