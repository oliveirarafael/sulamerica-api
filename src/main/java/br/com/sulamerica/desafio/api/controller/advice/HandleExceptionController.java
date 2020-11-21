package br.com.sulamerica.desafio.api.controller.advice;

import java.util.List;
import java.util.stream.Collectors;

import br.com.sulamerica.desafio.api.exception.ConflictException;
import br.com.sulamerica.desafio.api.exception.NotFoundException;
import br.com.sulamerica.desafio.api.model.Erro;
import br.com.sulamerica.desafio.api.model.ErroFormulario;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleExceptionController {
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public List<ErroFormulario> handle(MethodArgumentNotValidException exception) {
		return exception.getBindingResult()
						.getFieldErrors()
						.stream()
                        .map(error -> new ErroFormulario(error.getField(), error.getDefaultMessage(),
        		                            HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()))
                        .collect(Collectors.toList());

	}
		
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public Erro handleNotFound(NotFoundException exception){
		return new Erro(exception.getMessage(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
	}

	@ResponseStatus(code = HttpStatus.CONFLICT)
	@ExceptionHandler(ConflictException.class)
	public Erro handleConflict(ConflictException exception){
        return new Erro(exception.getMessage(), HttpStatus.CONFLICT.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AuthenticationException.class)
	public Erro handleAuth(AuthenticationException exception){
		return new Erro(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
	}

}
