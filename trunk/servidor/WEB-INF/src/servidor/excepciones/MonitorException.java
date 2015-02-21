package servidor.excepciones;
/*
 * Clase encargada de modelar la excepciones de la Fachada.
 */
public class MonitorException extends GeneralException	{

	private static final long serialVersionUID = 1L;

	public MonitorException(){
		super();
	}

	public MonitorException(String message) {
		super(message);
	}

}
