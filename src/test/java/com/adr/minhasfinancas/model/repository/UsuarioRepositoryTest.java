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
	public void deveVerificarAExistenciaDeUmEmail() {
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		boolean result = repository.existsByEmail(EMAIL_TEST);
		repository.delete(usuario);
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail() {
		boolean result = repository.existsByEmail(EMAIL_TEST);
		Assertions.assertThat(result).isFalse();
	}
	
	@Test
	public void deveEfetuarACargadeDezRegistros() {
		int quantidadeUsuario = 10;
		for(int i = 0; i < quantidadeUsuario; i++) {
			Usuario usuario = Usuario.builder().nome("usuario").email("usuarioteste" + i + "@email.com.br").build();
			repository.save(usuario);
		}	
		boolean result = repository.findAll().size() == quantidadeUsuario;
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {
		Usuario usuario = criarUsuario();
		Usuario usuarioSalvo = repository.save(usuario);
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
	}
	
	@Test
	public void deveBuscarUmUsuarioPorEmail() {
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		Optional<Usuario> resultado = repository.findByEmail(EMAIL_TEST);
		
		Assertions.assertThat(resultado.isPresent()).isTrue();
	}

	@Test
	public void deveRetornarVazioAoBuscarUmUsuarioPorEmailQuandoNaoExisteNaBase() {
		Optional<Usuario> resultado = repository.findByEmail(EMAIL_TEST);
		
		Assertions.assertThat(resultado.isPresent()).isFalse();
	}
	
	public static Usuario criarUsuario() {
		return Usuario
				.builder()
				.nome("Usuario")
				.email(EMAIL_TEST)
				.senha("codigo")
				.build();
	}
}
