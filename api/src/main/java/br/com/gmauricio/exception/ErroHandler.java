package br.com.gmauricio.exception;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.gmauricio.dto.ErroDto;
import br.com.gmauricio.dto.ErroFormDTO;
import feign.FeignException;

@RestControllerAdvice
public class ErroHandler {
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroFormDTO> handle(MethodArgumentNotValidException exception) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		List<ErroFormDTO> list = new ArrayList<>();
		fieldErrors.forEach(a -> {
			list.add(new ErroFormDTO(a.getField(), a.getDefaultMessage()));
		});
		return list;
	}
	
	@ExceptionHandler(FeignException.class)
    public ResponseEntity<ErroDto> feignException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErroDto(e.getMessage()));
    }
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ErroDto> onErro(ApiException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroDto(exception.getMessage()));
	} 

}
