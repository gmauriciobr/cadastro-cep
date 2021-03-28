package br.com.gmauricio.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.gmauricio.repository.ClienteRepository;


public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {

	@Autowired
	private ClienteRepository clienteRepository; 

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email == null || !clienteRepository.findByEmail(email.toLowerCase()).isPresent();
    }
}
