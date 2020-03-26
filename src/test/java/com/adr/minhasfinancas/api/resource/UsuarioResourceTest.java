package com.adr.minhasfinancas.api.resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.adr.minhasfinancas.api.dto.UsuarioDTO;
import com.adr.minhasfinancas.model.entity.Usuario;
import com.adr.minhasfinancas.service.LancamentoService;
import com.adr.minhasfinancas.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UsuarioResource.class)
@AutoConfigureMockMvc
public class UsuarioResourceTest {

	private static final String SENHA_USUARIO = "1532";
	private static final String EMAIL_USUARIO = "usuario@email.com";

	static final String API = "/api/usuarios";
	static final MediaType JASON = MediaType.APPLICATION_JSON;

	@Autowired
	MockMvc mvc;
	
	@MockBean
	UsuarioService service;
	
	@MockBean
	LancamentoService lancamentoService;

	@Test
	public void deveAutenticarUmUsuario() throws Exception {
		UsuarioDTO dto = UsuarioDTO.builder()
				.email(EMAIL_USUARIO)
				.senha(SENHA_USUARIO)
				.build();
		
		Usuario usuario = Usuario.builder()
				.id(1L).email(EMAIL_USUARIO)
				.senha(SENHA_USUARIO)
				.build();
		
		Mockito.when(service.authenticate(EMAIL_USUARIO, SENHA_USUARIO)).thenReturn(usuario);
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(API.concat("/autenticar"))
				.accept(JASON)
				.contentType(JASON)
				.content(json);
			
		mvc.perform(request)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("id").value(usuario.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("nome").value(usuario.getNome()))
				.andExpect(MockMvcResultMatchers.jsonPath("email").value(usuario.getEmail()));
	}

}
