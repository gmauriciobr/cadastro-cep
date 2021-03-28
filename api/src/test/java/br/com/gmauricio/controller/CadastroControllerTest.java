package br.com.gmauricio.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gmauricio.exception.ApiException;
import br.com.gmauricio.factory.ClienteFactory;
import br.com.gmauricio.factory.EnderecoFactory;
import br.com.gmauricio.form.CadastraClienteForm;
import br.com.gmauricio.repository.ClienteRepository;
import br.com.gmauricio.service.ClienteService;
import feign.FeignException;

@WebMvcTest(CadastroController.class)
@ActiveProfiles("test")
public class CadastroControllerTest {

	@Autowired
	public MockMvc mvc;

	@MockBean
	public ClienteService clienteService;

	@MockBean
	public ClienteRepository clienteRepository;

	@Test
	public void testaFormularioValido() throws Exception {
		CadastraClienteForm clienteForm = ClienteFactory.CLIENTE_FORM_OK;
		when(clienteService.cadastro(any())).thenReturn(CadastraClienteForm.parse(clienteForm, EnderecoFactory.ENDERECO_OK));
		mvc.perform(post("/cadastro").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(clienteForm))).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.nome", is(clienteForm.getNome())));
	}

	@Test
	public void testaFormularioInvalido() throws Exception {
		CadastraClienteForm clienteForm = ClienteFactory.CLIENTE_FORM_FALTANDO_CPF;
		when(clienteService.cadastro(any())).thenReturn(ClienteFactory.CLIENTE);
		mvc.perform(post("/cadastro").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(clienteForm))).andExpect(status().isBadRequest());
	}

	@Test
	public void testaFormularioValidoCpfExistente() throws Exception {
		CadastraClienteForm clienteForm = ClienteFactory.CLIENTE_FORM_OK;
		when(clienteService.cadastro(any())).thenReturn(CadastraClienteForm.parse(clienteForm, EnderecoFactory.ENDERECO_OK));
		when(clienteRepository.findByCpf(any())).thenReturn(ClienteFactory.OPTINAL_CLIENTE);
		mvc.perform(post("/cadastro").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(clienteForm))).andExpect(status().isBadRequest());
	}

	@Test
	public void testaFormularioValidoEmailExistente() throws Exception {
		CadastraClienteForm clienteForm = ClienteFactory.CLIENTE_FORM_OK;
		when(clienteService.cadastro(any())).thenReturn(CadastraClienteForm.parse(clienteForm, EnderecoFactory.ENDERECO_OK));
		when(clienteRepository.findByEmail(any())).thenReturn(ClienteFactory.OPTINAL_CLIENTE);
		mvc.perform(post("/cadastro").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(clienteForm))).andExpect(status().isBadRequest());
	}

	@Test
	public void testaFormularioValidoFeignException() throws Exception {
		CadastraClienteForm clienteForm = ClienteFactory.CLIENTE_FORM_OK;
		when(clienteService.cadastro(any())).thenThrow(FeignException.class);
		mvc.perform(post("/cadastro").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(clienteForm))).andExpect(status().is5xxServerError());
	}

	@Test
	public void testaFormularioValidoApiException() throws Exception {
		CadastraClienteForm clienteForm = ClienteFactory.CLIENTE_FORM_OK;
		when(clienteService.cadastro(any())).thenThrow(ApiException.class);
		mvc.perform(post("/cadastro").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(clienteForm))).andExpect(status().isBadRequest());
	}

	@Test
	public void testaBuscaPorId() throws Exception {
		when(clienteRepository.findById(any())).thenReturn(ClienteFactory.OPTINAL_CLIENTE);
		mvc.perform(get("/cadastro/{id}", "123").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testaBuscaPorIdInvalido() throws Exception {
		when(clienteRepository.findById(any())).thenReturn(Optional.empty());
		mvc.perform(get("/cadastro/{id}", "123").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	@Test
	public void testaBuscaPorEmail() throws Exception {
		when(clienteRepository.findByEmail(any())).thenReturn(ClienteFactory.OPTINAL_CLIENTE);
		mvc.perform(get("/cadastro/find").queryParam("email", "email@email.com")).andExpect(status().isOk());
	}

	@Test
	public void testaBuscaPorEmailInvalido() throws Exception {
		when(clienteRepository.findByEmail(any())).thenReturn(Optional.empty());
		mvc.perform(get("/cadastro/find").queryParam("email", "email@email.com")).andExpect(status().isNotFound());
	}

	@Test
	public void testaAtualizarCadastroValido() throws Exception {
		CadastraClienteForm clienteForm = ClienteFactory.CLIENTE_FORM_OK;
		when(clienteRepository.findById(any())).thenReturn(ClienteFactory.OPTINAL_CLIENTE);
		when(clienteService.atualiza(any(), any())).thenReturn(ClienteFactory.CLIENTE);
		mvc.perform(put("/cadastro/{id}", "123").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(clienteForm))).andExpect(status().isOk());
	}
	
	@Test
	public void testaAtualizarCadastroInvalido() throws Exception {
		CadastraClienteForm clienteForm = ClienteFactory.CLIENTE_FORM_OK;
		when(clienteRepository.findById(any())).thenReturn(Optional.empty());
		mvc.perform(put("/cadastro/{id}", "123").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(clienteForm))).andExpect(status().isNotFound());
	}

}
