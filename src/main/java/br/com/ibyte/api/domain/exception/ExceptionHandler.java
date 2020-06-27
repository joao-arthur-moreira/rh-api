package br.com.ibyte.api.domain.exception;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.ibyte.api.domain.exception.ResponseErro.ApiError;

@RestControllerAdvice
public class ExceptionHandler {
	
	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseErro> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, 
			Locale locale) {
		System.out.println("MethodArgumentNotValidException....");
		
		Stream<ObjectError> errors = exception.getBindingResult().getAllErrors().stream();
		
		List<ApiError> apiErrors = errors
				.map(ObjectError::getDefaultMessage)
				.map(code -> apiError("", code))
				.collect(Collectors.toList());
		
		ResponseErro responseErro = ResponseErro.of(HttpStatus.BAD_REQUEST, apiErrors);
		return ResponseEntity.badRequest().body(responseErro);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ResponseErro> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, Locale locale) {
		final String codigoErro = "generico-1";
		final HttpStatus status = HttpStatus.BAD_REQUEST;
		final ResponseErro errorResponse = ResponseErro.of(status, apiError(codigoErro, ""));
		return ResponseEntity.badRequest().body(errorResponse);		
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseErro> handleException(Exception exception, Locale locale) {
		final String codigoErro = "interno";
		final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		final ResponseErro errorResponse = ResponseErro.of(status, apiError(codigoErro, "Erro interno no servidor"));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);		
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(NegocioException.class)
	public ResponseEntity<ResponseErro> handleNegocioException(NegocioException exception, Locale locale) {
		final String codigoErro = "1";
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if (exception instanceof EntidadeEmUsoException) {
			status = HttpStatus.CONFLICT;
		}
		
		final ResponseErro errorResponse = ResponseErro.of(status, apiError(codigoErro, exception.getMessage()));
		return ResponseEntity.status(status).body(errorResponse);		
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<ResponseErro> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException exception, Locale locale) {
		final String codigoErro = "0";
		final HttpStatus status = HttpStatus.NOT_FOUND;
		
		final ResponseErro errorResponse = ResponseErro.of(status, apiError(codigoErro, exception.getMessage()));
		
		return ResponseEntity.status(status).body(errorResponse);		
	}
	
	private ApiError apiError(String codigo, String mensagem) {		
		return new ResponseErro.ApiError(codigo, mensagem);
	}

}
