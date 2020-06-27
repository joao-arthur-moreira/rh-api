package br.com.ibyte.api.domain.exception;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseErro {

	private final int status;
	private final List<ApiError> erros;
	
	static ResponseErro of(HttpStatus status, List<ApiError> erros) {
		return new ResponseErro(status.value(), erros);
	}
	
	static ResponseErro of(HttpStatus status, ApiError erro) {
		return of(status, Collections.singletonList(erro));
	}
	
	@Getter
	@Setter
	@RequiredArgsConstructor
	static class ApiError {
		private final String codigo;
		private final String mensagem;
	}

}
