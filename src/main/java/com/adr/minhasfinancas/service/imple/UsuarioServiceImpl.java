package com.adr.minhasfinancas.service.imple;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adr.minhasfinancas.exception.ErroAutencitacaoException;
import com.adr.minhasfinancas.exception.RegraNegocioException;
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
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);
		if(usuario.isPresent()) {
			throw new ErroAutencitacaoException("Usuário não encontrado.");
		}
		
		if(usuario.get().getSenha().equals(senha)) {
			throw new ErroAutencitacaoException("Senha inváliad.");
		}
		
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		emailEhValido(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public boolean emailEhValido(String email) {
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Este e-mail não está disponível.");
		}
		return true;
	}

}
