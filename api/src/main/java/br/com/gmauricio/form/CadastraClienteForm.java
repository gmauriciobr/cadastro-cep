package br.com.gmauricio.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import br.com.gmauricio.model.Cliente;
import br.com.gmauricio.model.Endereco;
import br.com.gmauricio.validation.CpfConstraint;
import br.com.gmauricio.validation.EmailConstraint;

public class CadastraClienteForm {

	@NotEmpty
	private String nome;
	@EmailConstraint
	@NotEmpty
	@Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
	private String email;
	@CpfConstraint
	@NotEmpty
	@Pattern(regexp = "^[0-9]{11}")
	private String cpf;
	@NotEmpty
	@Pattern(regexp = "^[0-9]{8}")
	private String cep;
	
	public CadastraClienteForm(String nome, String email, String cpf, String cep) {
		this.nome = nome;
		this.email = email.toLowerCase();
		this.cpf = cpf;
		this.cep = cep;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public static Cliente parse(CadastraClienteForm dto, Endereco endereco) {
		return new Cliente(dto.nome, dto.email, dto.cpf, endereco);
	}
		
}
