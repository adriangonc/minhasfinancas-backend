package com.adr.minhasfinancas.service.imple;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adr.minhasfinancas.exception.AuthenticateErrorException;
import com.adr.minhasfinancas.exception.BusinessRuleException;
import com.adr.minhasfinancas.model.entity.Usuario;
import com.adr.minhasfinancas.model.repository.UsuarioRepository;
import com.adr.minhasfinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	private UsuarioRepository repository;
	
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Usuario authenticate(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);
		if(!usuario.isPresent()) {
			throw new AuthenticateErrorException("Usuário não encontrado.");
		}
		
		if(!usuario.get().getSenha().equals(senha)) {
			throw new AuthenticateErrorException("Senha inváliada.");
		}
		
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario saveUser(Usuario usuario) {
		
		emailIsValid(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public boolean emailIsValid(String email) {
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new BusinessRuleException("Este e-mail não está disponível.");
		}
		return true;
	}

}
