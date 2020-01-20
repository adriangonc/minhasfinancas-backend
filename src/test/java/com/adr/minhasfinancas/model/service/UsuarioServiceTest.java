package com.adr.minhasfinancas.model.service;


import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adr.minhasfinancas.exception.RegraNegocioException;
import com.adr.minhasfinancas.model.entity.Usuario;
import com.adr.minhasfinancas.model.repository.UsuarioRepository;
import com.adr.minhasfinancas.service.UsuarioService;
import com.adr.minhasfinancas.service.imple.UsuarioServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	private static final String EMAIL_TEST = "usuarioteste@email.com.br";

	UsuarioService service;

	@MockBean
	UsuarioRepository repository;

	public void setUp() {
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
	
	@Test
	@Order(3)
	@DisplayName("Deve autenticar um usuário com sucesso")
	public void deveAutenticarUmUsuarioComSucesso() {
		setUp();
		String senha = "Senha";
		
		Usuario usuario = Usuario.builder().email(EMAIL_TEST).senha(senha).id(1L).build();
		Mockito.when(repository.findByEmail(EMAIL_TEST)).thenReturn(Optional.of(usuario));
		
		Usuario result = service.autenticar(EMAIL_TEST, senha);
		boolean usuarioEstaAutenticado = false;
		if(result.getId() != null) {
			usuarioEstaAutenticado = true;
		}
		
		Assertions.assertTrue(usuarioEstaAutenticado);
	}
}
