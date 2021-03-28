package br.com.gmauricio.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.gmauricio.repository.ClienteRepository;


public class CpfValidator implements ConstraintValidator<CpfConstraint, String> {

	@Autowired
	private ClienteRepository clienteRepository; 

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        return cpf == null || !clienteRepository.findByCpf(cpf).isPresent();
    }
}
