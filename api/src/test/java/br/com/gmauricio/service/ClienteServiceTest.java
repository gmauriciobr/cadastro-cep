package br.com.gmauricio.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.gmauricio.client.viacep.ViaCepClient;
import br.com.gmauricio.exception.ApiException;
import br.com.gmauricio.factory.ClienteFactory;
import br.com.gmauricio.factory.ViaCepResponseFactory;
import br.com.gmauricio.repository.ClienteRepository;
import feign.FeignException;

@SpringBootTest
@ActiveProfiles("test")
public class ClienteServiceTest {

	@InjectMocks
	public ClienteService clienteService;
	
	@Mock
	public ClienteRepository clienteRepository;
	
	@Mock
	public ViaCepClient viaCepCliente;
	
	@Test
	public void testaCadastro() {
		when(viaCepCliente.consulta(any())).thenReturn(ViaCepResponseFactory.ENDERECO_OK);
		clienteService.cadastro(ClienteFactory.CLIENTE_FORM_OK);
		verify(clienteRepository, times(1)).save(any());
	}
	
	@Test
	public void testaApiException() {
		assertThrows(ApiException.class, () -> {
			clienteService.cadastro(ClienteFactory.CLIENTE_FORM_OK);
		});
	}
	
	@Test
	public void testaFeignException() {
		when(viaCepCliente.consulta(any())).thenThrow(FeignException.class);
		assertThrows(FeignException.class, () -> {
			clienteService.cadastro(ClienteFactory.CLIENTE_FORM_OK);
		});
	}
	
	@Test
	public void testaAtualiza() {
		when(viaCepCliente.consulta(any())).thenReturn(ViaCepResponseFactory.ENDERECO_OK);
		when(clienteRepository.getOne(any())).thenReturn(ClienteFactory.CLIENTE);
		clienteService.atualiza(1L, ClienteFactory.ATUALIZA_CLIENTE);
		verify(clienteRepository, times(1)).save(any());
	}

}
