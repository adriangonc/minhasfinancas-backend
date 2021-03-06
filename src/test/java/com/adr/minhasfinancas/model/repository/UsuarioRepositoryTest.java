package com.adr.minhasfinancas.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adr.minhasfinancas.model.entity.Usuario;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {

	private static final String EMAIL_TEST = "usuarioteste@email.com.br";
	
	
	@Autowired 
	UsuarioRepository repository;
	 
	
	@Autowired
	TestEntityManager entityManager;

	@Test
	public void shouldCheckIfEmailIsValid() {
		Usuario usuario = createUser();
		entityManager.persist(usuario);
		boolean result = repository.existsByEmail(EMAIL_TEST);
		repository.delete(usuario);
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void shouldReturnFalseIfEmailIsNotInTheDataBase() {
		boolean result = repository.existsByEmail(EMAIL_TEST);
		Assertions.assertThat(result).isFalse();
	}
	
	@Test
	public void shouldInsertTenUsersInDataBase() {
		int numberOfUsers = 10;
		for(int i = 0; i < numberOfUsers; i++) {
			Usuario user = Usuario.builder().nome("usuario").email("usuarioteste" + i + "@email.com.br").build();
			repository.save(user);
		}	
		boolean result = repository.findAll().size() == numberOfUsers;
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void shouldSaveAnUserInDataBase() {
		Usuario user = createUser();
		Usuario savedUser = repository.save(user);
		Assertions.assertThat(savedUser.getId()).isNotNull();
	}
	
	@Test
	public void shouldFindAUserByEmail() {
		Usuario usuario = createUser();
		entityManager.persist(usuario);
		
		Optional<Usuario> resultado = repository.findByEmail(EMAIL_TEST);
		
		Assertions.assertThat(resultado.isPresent()).isTrue();
	}

	@Test
	public void shouldReturnVoidWhenToSearchANotExistentUserInDataBase() {
		Optional<Usuario> result = repository.findByEmail(EMAIL_TEST);
		
		Assertions.assertThat(result.isPresent()).isFalse();
	}
	
	public static Usuario createUser() {
		return Usuario
				.builder()
				.nome("Usuario")
				.email(EMAIL_TEST)
				.senha("codigo")
				.build();
	}
}
