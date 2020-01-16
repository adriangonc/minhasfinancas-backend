package com.adr.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adr.minhasfinancas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	// nesse query method o spring data irá buscas na entidade Usuario um campo chamado
	// email e buscar, o parametro passado.
	/* Optional<Usuario> findByEmail(String email); */
	
	//Verifica no banco de email existe
	boolean existsByEmail(String email);
}
