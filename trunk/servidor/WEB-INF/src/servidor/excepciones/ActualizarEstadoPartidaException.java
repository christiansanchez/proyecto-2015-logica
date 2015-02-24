package servidor.excepciones;

public class ActualizarEstadoPartidaException extends GeneralException{
	
	private static final long serialVersionUID = 1L;

	public ActualizarEstadoPartidaException()
	{
		super();
	}
	
	public ActualizarEstadoPartidaException(String message) 
	{
		super(message);		
	}	
}
