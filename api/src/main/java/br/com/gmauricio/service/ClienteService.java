package br.com.gmauricio.service;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gmauricio.client.viacep.ViaCepClient;
import br.com.gmauricio.client.viacep.ViaCepResponse;
import br.com.gmauricio.exception.ApiException;
import br.com.gmauricio.form.AtualizaClienteForm;
import br.com.gmauricio.form.CadastraClienteForm;
import br.com.gmauricio.model.Cliente;
import br.com.gmauricio.model.Endereco;
import br.com.gmauricio.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ViaCepClient viaCepClient;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente cadastro(CadastraClienteForm clienteForm) {
		Cliente cliente = CadastraClienteForm.parse(clienteForm, ViaCepResponse.parse(consultaCep(clienteForm.getCep())));
		return clienteRepository.save(cliente);	
	}

	public Cliente atualiza(Long id, @Valid AtualizaClienteForm atualizaClienteForm) {
		Cliente cliente = clienteRepository.getOne(id);
		cliente.setNome(atualizaClienteForm.getNome());
		Endereco endereco = cliente.getEndereco();
		BeanUtils.copyProperties(ViaCepResponse.parse(consultaCep(atualizaClienteForm.getCep())), endereco, "id");
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);
		return cliente;
	}
	
	private ViaCepResponse consultaCep(String cep) {
		ViaCepResponse response = viaCepClient.consulta(cep);
		if(response == null || response.getLogradouro() == null) {
			throw new ApiException("Endereco nao encontrado");
		}
		return response;
	}

}
