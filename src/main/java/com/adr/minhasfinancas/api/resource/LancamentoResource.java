package com.adr.minhasfinancas.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.adr.minhasfinancas.api.dto.AtualizaStatusDTO;
import com.adr.minhasfinancas.api.dto.LancamentoDTO;
import com.adr.minhasfinancas.exception.BusinessRuleException;
import com.adr.minhasfinancas.model.entity.Lancamento;
import com.adr.minhasfinancas.model.entity.Usuario;
import com.adr.minhasfinancas.model.enums.StatusLancamento;
import com.adr.minhasfinancas.model.enums.TipoLancamento;
import com.adr.minhasfinancas.service.LancamentoService;
import com.adr.minhasfinancas.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/lancamentos")
@RequiredArgsConstructor
public class LancamentoResource {

	private final LancamentoService service;
	private final UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<Object> search(@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "ano", required = false) Integer ano, @RequestParam("usuario") Long idUsuario) {
		Lancamento filter = new Lancamento();
		filter.setDescricao(descricao);
		filter.setMes(mes);
		filter.setAno(ano);

		Optional<Usuario> user = usuarioService.userById(idUsuario);
		if (!user.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta!");
		} else {
			filter.setUsuario(user.get());
		}

		List<Lancamento> lancamentos = service.search(filter);
		return ResponseEntity.ok(lancamentos);
	}
	
	@GetMapping("{id}")
	public ResponseEntity findById( @PathVariable("id") Long id ) {
		return service.findById(id)
				.map( lancamento -> new ResponseEntity(converter(lancamento), HttpStatus.OK) )
				.orElseGet( () -> new ResponseEntity(HttpStatus.NOT_FOUND) );
	}
	
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody LancamentoDTO dto) {
		try {
			Lancamento lancamento = converter(dto);
			service.save(lancamento);
			return new ResponseEntity<Object>(lancamento, HttpStatus.CREATED);
		} catch (BusinessRuleException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity update(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto) {
		return service.findById(id).map(entity -> {
			try {
				Lancamento lancamento = converter(dto);
				lancamento.setId(entity.getId());
				service.update(lancamento);
				return ResponseEntity.ok(lancamento);
			} catch (BusinessRuleException e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<String>("Lançamento não encontrado!", HttpStatus.BAD_REQUEST));
	}
	
	@PutMapping("{id}/atualiza-status")
	public ResponseEntity updateStatus( @PathVariable Long id, @RequestBody AtualizaStatusDTO dto) {
		return service.findById(id).map( entity -> {
			StatusLancamento selectedState = StatusLancamento.valueOf(dto.getStatus());
			if(selectedState == null) {
				return ResponseEntity.badRequest().body("Impossível atualizar status de lançamento!");
			}
			try {
				entity.setStatus(selectedState);
				service.update(entity);
				return ResponseEntity.ok(entity);
			} catch (BusinessRuleException e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity<String>("Lançamento não encontrado!", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity delete(@PathVariable("id") Long id) {
		return service.findById(id).map(entity -> {
			service.delete(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> 
			new ResponseEntity<String>("Lançamento não encontrado! ", HttpStatus.BAD_REQUEST));
	}
	
	private Lancamento converter(LancamentoDTO dto) {
		Lancamento lancamento = new Lancamento();
		lancamento.setId(dto.getId());
		lancamento.setDescricao(dto.getDescricao());
		lancamento.setAno(dto.getAno());
		lancamento.setMes(dto.getMes());
		lancamento.setValor(dto.getValor());

		Usuario usuario = usuarioService.userById(dto.getUsuario()).orElseThrow(
				() -> new BusinessRuleException("Usuário não encontrado para o id: " + dto.getUsuario().toString()));
		
		lancamento.setUsuario(usuario);
		if(dto.getTipo() != null) {
			lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
		}		
		if(dto.getStatus() != null) {
			lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
		}
		
		return lancamento;
	}
	
	private LancamentoDTO converter(Lancamento lancamento) {
		return LancamentoDTO.builder()
				.id(lancamento.getId())
				.descricao(lancamento.getDescricao())
				.valor(lancamento.getValor())
				.mes(lancamento.getMes())
				.ano(lancamento.getAno())
				.status(lancamento.getStatus().name())
				.tipo(lancamento.getTipo().name())
				.usuario(lancamento.getUsuario().getId())
				.build();
	}
}
