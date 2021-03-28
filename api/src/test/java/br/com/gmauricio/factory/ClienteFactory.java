package br.com.gmauricio.factory;

import java.util.Optional;

import br.com.gmauricio.form.AtualizaClienteForm;
import br.com.gmauricio.form.CadastraClienteForm;
import br.com.gmauricio.model.Cliente;

public class ClienteFactory {
	
	public static final CadastraClienteForm CLIENTE_FORM_OK = new CadastraClienteForm("Jaao", "joao@email.com", "12345678900", "00000000");
	public static final CadastraClienteForm CLIENTE_FORM_FALTANDO_CPF = new CadastraClienteForm("Jaao", "joao@email.com", null, "00000000");
	
	public static final AtualizaClienteForm ATUALIZA_CLIENTE = new AtualizaClienteForm("Mario", "123445000");
	
	public static final Cliente CLIENTE = CadastraClienteForm.parse(CLIENTE_FORM_OK, EnderecoFactory.ENDERECO_OK);
	
	public static final Optional<Cliente> OPTINAL_CLIENTE = Optional.ofNullable(CadastraClienteForm.parse(CLIENTE_FORM_OK, EnderecoFactory.ENDERECO_OK));

}
