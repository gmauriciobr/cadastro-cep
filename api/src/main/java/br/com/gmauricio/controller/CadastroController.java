package br.com.gmauricio.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.gmauricio.dto.ClienteDto;
import br.com.gmauricio.form.AtualizaClienteForm;
import br.com.gmauricio.form.CadastraClienteForm;
import br.com.gmauricio.model.Cliente;
import br.com.gmauricio.repository.ClienteRepository;
import br.com.gmauricio.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("cadastro")
@Api(tags = { "Case Entity" }, protocols = "http", value = "API cara cadastro de clientes")
public class CadastroController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ClienteRepository clienteRepository;

	@PostMapping
	@ApiOperation(value = "Cadastro de cliente", response = ClienteDto.class)
	@ApiResponse(code = 201, message = "Cliente cadastrado som sucesso")
	public ResponseEntity<ClienteDto> cadastro(@RequestBody @Valid CadastraClienteForm clienteForm,
			UriComponentsBuilder uriBuilder) {
		Cliente cliente = clienteService.cadastro(clienteForm);
		URI uri = uriBuilder.path("/api/cadastro/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).body(new ClienteDto(cliente));
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Busca cadastro por ID", response = ClienteDto.class)
	public ResponseEntity<ClienteDto> busca(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.isPresent() ? ResponseEntity.ok(new ClienteDto(cliente.get()))
				: ResponseEntity.notFound().build();
	}

	@GetMapping("/find")
	@ApiOperation(value = "Busca cadastro por email", response = ClienteDto.class)
	public ResponseEntity<ClienteDto> buscaPorEmail(@RequestParam(value = "email", required = true) String email) {
		Optional<Cliente> cliente = clienteRepository.findByEmail(email);
		return cliente.isPresent() ? ResponseEntity.ok(new ClienteDto(cliente.get()))
				: ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualiza cadastro cliente", response = ClienteDto.class)
	public ResponseEntity<ClienteDto> atualiza(@PathVariable Long id, @RequestBody @Valid AtualizaClienteForm atualizaClienteForm) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if (cliente.isPresent()) {
			 return ResponseEntity.ok(new ClienteDto(clienteService.atualiza(id, atualizaClienteForm)));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
