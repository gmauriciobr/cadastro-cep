package br.com.gmauricio.dto;

import br.com.gmauricio.model.Cliente;

public class ClienteDto {
	
	private Long id;
	private String nome;
	private String email;
	private String endereco;
	
	public ClienteDto(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
		this.endereco = cliente.getEndereco().getEnderecoCompleto();
	}
	
	public Long getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public String getEmail() {
		return email;
	}
	public String getEndereco() {
		return endereco;
	}


}
