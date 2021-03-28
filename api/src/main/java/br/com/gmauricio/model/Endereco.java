package br.com.gmauricio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "endereco")
public class Endereco {

	private Long id;
	private Cliente cliente;
	private String cep;
	private String logadouro;
	private String localidade;
	
	public Endereco() {
	}
	
	public Endereco(String cep, String logradouro, String localidade) {
		this.cep = cep;
		this.logadouro = logradouro;
		this.localidade = localidade;
	}
	
	@Id
	@Column(name = "id_endereco")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@OneToOne(mappedBy = "endereco")
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	@Column(name = "cep")
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	@Column(name = "logradouro")
	public String getLogadouro() {
		return logadouro;
	}
	public void setLogadouro(String logadouro) {
		this.logadouro = logadouro;
	}

	@Column(name = "localidade")
	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	
	@Transient
	public String getEnderecoCompleto() {
		return String.format("%s - %s - CEP: %s", logadouro, localidade, cep);
	}
	
	

}
