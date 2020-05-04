package exceptions;

@SuppressWarnings("serial")
public class ValidacaoException extends Exception {
	public ValidacaoException(String erro) {
		super(erro+" invalido!");
	}
}
