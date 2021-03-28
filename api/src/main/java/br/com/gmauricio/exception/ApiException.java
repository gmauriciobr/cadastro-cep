package br.com.gmauricio.exception;

public class ApiException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ApiException(String erro) {
		super(erro);
	}

}
