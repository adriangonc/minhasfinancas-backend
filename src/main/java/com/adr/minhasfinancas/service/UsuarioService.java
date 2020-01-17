package com.adr.minhasfinancas.service;

import com.adr.minhasfinancas.model.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	boolean emailEhValido(String email);
}
