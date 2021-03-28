package br.com.gmauricio.client.viacep;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="via-cep", url= "${viacep.url}", decode404 = true)
public interface ViaCepClient {
	
	@GetMapping("/{cep}/json/")
	public ViaCepResponse consulta(@PathVariable String cep);

}
