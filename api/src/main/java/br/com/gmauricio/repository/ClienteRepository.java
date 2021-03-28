package br.com.gmauricio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gmauricio.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	public Optional<Cliente> findByEmail(String string);

	public Optional<Cliente> findByCpf(String cpf);

}
