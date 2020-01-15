package com.adr.minhasfinancas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adr.minhasfinancas.model.entity.Usuario;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

	private static final String EMAIL_TEST = "usuarioteste@email.com.br";
	
	@Autowired
	UsuarioRepository repository;

	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		Usuario usuario = Usuario.builder().nome("usuario").email(EMAIL_TEST).build();
		repository.save(usuario);
		boolean result = repository.existsByEmail(EMAIL_TEST);
		repository.delete(usuario);
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail() {
		Usuario usuario = Usuario.builder().nome("usuario").email(EMAIL_TEST).build();
		repository.save(usuario);
		repository.delete(usuario);
		boolean result = repository.existsByEmail(EMAIL_TEST);
		Assertions.assertThat(result).isFalse();
	}
	
	@Test
	public void deveEfetuarACargadeDezRegistros() {
		int quantidadeUsuario = 10;
		for(int i = 0; i < quantidadeUsuario; i++) {
			Usuario usuario = Usuario.builder().nome("usuario").email("usuarioteste" + i + "@email.com.br").build();
			//System.out.println(usuario.getEmail());
			repository.save(usuario);
		}
		
		boolean result = repository.findAll().size() == quantidadeUsuario;
		Assertions.assertThat(result).isTrue();
	}

}
