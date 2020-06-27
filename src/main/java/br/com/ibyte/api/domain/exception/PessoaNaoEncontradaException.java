package br.com.ibyte.api.domain.exception;

public class PessoaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public PessoaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public PessoaNaoEncontradaException(Long id) {
		this(String.format("Não existe um cadastro de pessoa com o código %d", id));
	}

}
