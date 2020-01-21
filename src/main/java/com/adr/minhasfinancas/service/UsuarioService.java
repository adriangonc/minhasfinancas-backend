package com.adr.minhasfinancas.service;

import com.adr.minhasfinancas.model.entity.Usuario;

public interface UsuarioService {

	Usuario authenticate(String email, String password);
	
	Usuario saveUser(Usuario user);
	
	boolean emailIsValid(String email);
}
