package br.com.gmauricio.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface EmailConstraint {
    
	public String message() default "'${validatedValue}' já cadastrado";

	public Class<?>[] groups() default {};
	
	public Class<? extends Payload>[] payload() default {};
    
}
