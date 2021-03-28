package br.com.gmauricio.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class AtualizaClienteForm {
	
	@NotEmpty
	private String nome;
	@NotEmpty
	@Pattern(regexp = "^[0-9]{8}")
	private String cep;
	
	public AtualizaClienteForm(String nome, String cep) {
		this.nome = nome;
		this.cep = cep;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	

}
