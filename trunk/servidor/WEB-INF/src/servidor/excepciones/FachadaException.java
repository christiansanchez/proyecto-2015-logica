package servidor.excepciones;
/*
 * Clase encargada de modelar la excepciones de la Fachada.
 */
public class FachadaException extends GeneralException	{

	private static final long serialVersionUID = 1L;

	public FachadaException(){
		super();
	}

	public FachadaException(String message) {
		super(message);
	}

}
