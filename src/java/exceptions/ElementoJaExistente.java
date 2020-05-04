package exceptions;

@SuppressWarnings("serial")
public class ElementoJaExistente extends Exception {
	public ElementoJaExistente() {
		super("Elemento ja existente!");
	}
}
