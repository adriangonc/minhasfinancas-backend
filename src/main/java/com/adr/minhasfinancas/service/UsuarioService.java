package com.adr.minhasfinancas.service;

import java.util.Optional;

import com.adr.minhasfinancas.model.entity.Usuario;

public interface UsuarioService {

	Usuario authenticate(String email, String password);
	
	Usuario saveUser(Usuario user);
	
	boolean emailIsValid(String email);
	
	Optional<Usuario> userById(Long id);
}
