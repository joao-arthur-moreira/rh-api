package br.com.ibyte.api.domain.exception;

public class SetorNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public SetorNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public SetorNaoEncontradoException(Long id) {
		this(String.format("Não existe um cadastro de setor com o código %d", id));
	}

}
