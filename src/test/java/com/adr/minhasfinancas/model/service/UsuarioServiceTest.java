package com.adr.minhasfinancas.model.service;


import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adr.minhasfinancas.exception.AuthenticateErrorException;
import com.adr.minhasfinancas.exception.BusinessRuleException;
import com.adr.minhasfinancas.model.entity.Usuario;
import com.adr.minhasfinancas.model.repository.UsuarioRepository;
import com.adr.minhasfinancas.service.UsuarioService;
import com.adr.minhasfinancas.service.imple.UsuarioServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	private static final String EMAIL_TEST = "usuarioteste@email.com.br";

	//O spy faz com que o mock chame os métodos originais,
	//a menos que seja especificado um comportamento para o método.
	@SpyBean
	UsuarioServiceImpl service;

	@MockBean
	UsuarioRepository repository;
	
	@Test()
	@Order(1)
	@DisplayName("Verifica se o email e válido")
	public void shouldValidateIfEmailIsValid() {
		// Cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		// Ação
		Assertions.assertTrue(service.emailIsValid(EMAIL_TEST));
	}

	@Test()
	@Order(2)
	@DisplayName("Verifica se o email não e válido")
	public void shouldValidateEmailShouldReturnException() {
		// Cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenThrow(BusinessRuleException.class);
		
		// Ação
		Assertions.assertThrows(BusinessRuleException.class, () -> {
			service.emailIsValid(EMAIL_TEST);
		});
	}
	
	@Test
	@Order(3)
	@DisplayName("Deve autenticar um usuário com sucesso")
	public void shouldAuthenticateAUserWithSuccess() {
		String password = "Senha";
		
		Usuario user = Usuario.builder().email(EMAIL_TEST).senha(password).id(1L).build();
		Mockito.when(repository.findByEmail(EMAIL_TEST)).thenReturn(Optional.of(user));
		
		Usuario result = service.authenticate(EMAIL_TEST, password);
		boolean userIsAuthenticated = false;
		if(result.getId() != null) {
			userIsAuthenticated = true;
		}
		
		Assertions.assertTrue(userIsAuthenticated);
	}
	
	@Test
	@Order(4)
	@DisplayName("Deve lançar exception de autenticação quando não encontrar usuário cadastrado com o email informado")
	public void shouldThrowExceptionWhenNoFindRegisteredUserWhinInformedEmail() {
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		Throwable exception = Assertions.assertThrows(AuthenticateErrorException.class, () -> {
			service.authenticate(EMAIL_TEST, "codigo");
		});
		
		Assertions.assertTrue(exception.getMessage() == "Usuário não encontrado.");
	}
	
	@Test
	@Order(5)
	@DisplayName("Deve lançar exception de autenticação senha estiver incorreta")
	public void shouldThrowExceptionWhenPasswordDoesntHit() {
		String password = "senha";
		Usuario user = Usuario.builder().email(EMAIL_TEST).senha(password).build();
		
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
		
		Throwable exception = Assertions.assertThrows(AuthenticateErrorException.class, () -> {
			service.authenticate(EMAIL_TEST, "codigo");
		});
		
		Assertions.assertTrue(exception.getMessage() == "Senha inváliada.");
	}
	
	@Test
	@Order(6)
	@DisplayName("Deve salvar um usuário")
	public void shouldSaveAnUser() {
		//Para chamar o métodos reais com o spy deve se passar a instancia real dessa forma.
		Mockito.doReturn(true).when(service).emailIsValid(Mockito.anyString());
		Usuario user = Usuario.builder()
				.id(1L)
				.nome("Teste")
				.email(EMAIL_TEST)
				.senha("codigo").build();

		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(user);
		
		Usuario savedUser = service.saveUser(new Usuario());
		
		Assertions.assertTrue(savedUser.equals(user));
	}
	
	@Test
	@Order(7)
	@DisplayName("Não deve salvar um usuário com email já cadastrado")
	public void shouldNotSaveAnUser() {
		boolean notSaved = false;
		Usuario user = Usuario.builder()
				.email(EMAIL_TEST).build();
		
		try {
			Mockito.doThrow(BusinessRuleException.class).when(service).emailIsValid(EMAIL_TEST);
			service.saveUser(user);
			//Mockito.verify(repository, Mockito.never() ).save(user);
		} catch (Exception e) {
			notSaved = true;
			e.printStackTrace();
		}
		
		Assertions.assertTrue(notSaved);
	}
	
}
