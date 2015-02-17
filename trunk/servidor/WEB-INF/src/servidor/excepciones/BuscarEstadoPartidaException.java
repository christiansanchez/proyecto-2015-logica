package servidor.excepciones;

public class BuscarEstadoPartidaException extends GeneralException{
	
	private static final long serialVersionUID = 1L;

	public BuscarEstadoPartidaException()
	{
		super();
	}
	
	public BuscarEstadoPartidaException(String message) 
	{
		super(message);		
	}	
}